import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.jdesktop.swingx.JXDatePicker;

public class Reserve {

	Connection con;
	Statement stm;
	
	private JFrame frame;
    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    JXDatePicker DateStartPicker ;
    JXDatePicker DateEndPicker;
    JSpinner startSpinner;
    JSpinner endSpinner;
    JButton btnNewButton;
    
    Park park;
    User user;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Reserve(Park park,User user) {
		this.park = park;
		this.user = user;
		initialize(park);		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Park park) {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Lucida Grande", Font.BOLD, 13));
		frame.setBounds(100, 100, 357, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel startPan = new JPanel();
		startPan.setBounds(67, 72, 174, 42);
		frame.getContentPane().add(startPan);
		startPan.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("<html><span style = \" font-size : large; font-weight: bold; color: rgb(65,145,218);\">"+park.getPrice()+"MAD</span></html>");
		label.setBounds(241, 219, 91, 47);
		frame.getContentPane().add(label);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(this.getClass().getResource("/bar.jpg")));
		logo.setBounds(0, 0, 357, 60);
		frame.getContentPane().add(logo);
		
		JLabel lblNewLabel = new JLabel("  Début :");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewLabel.setBounds(0, 85, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblFin = new JLabel("  Fin :");
		lblFin.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblFin.setBounds(0, 168, 61, 16);
		frame.getContentPane().add(lblFin);
		
		 btnNewButton = new JButton("Réserver ici");
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnNewButton.setBounds(6, 306, 345, 54);
		btnNewButton.addActionListener(e->{
			ReservationCard RC = dataBaseReserve();
			RC.visibility();
			frame.setVisible(false);
		});
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Prix pour 1h");
		lblNewLabel_2.setForeground(new Color(36,89,132));
		lblNewLabel_2.setBounds(215, 259, 91, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JPanel DurePan = new JPanel();
		DurePan.setLayout(null);
		DurePan.setBounds(0, 113, 357, 42);
		frame.getContentPane().add(DurePan);
		
		JLabel lblNewLabel_1 = new JLabel("Choisissez une durée");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(24, 6, 256, 30);
		DurePan.add(lblNewLabel_1);
		
		JLabel nbrHours = new JLabel("1 h");
		nbrHours.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		nbrHours.setBounds(266, 14, 61, 16);
		DurePan.add(nbrHours);
		
		
		   DateStartPicker = new JXDatePicker();
			  Calendar local = Calendar.getInstance();
			  local.setTime(new Date()); // sets calendar time/date
			    local.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			  // creates calendar
		  DateStartPicker.setDate(local.getTime());
		  DateStartPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));    
		  DateStartPicker.addActionListener(e->{
	        	Date oDate = DateStartPicker.getDate(); 
	        	Date currentDate = new Date();
	            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String szDate = oDateFormat.format(oDate);
	            String curr =   oDateFormat.format(currentDate);
	            String[] currTab = curr.split("-");
	            String[] tab = szDate.split("-");
	            
	            if(oDate.getYear() < currentDate.getYear() )
	            {
	            	DateStartPicker.setDate(Calendar.getInstance().getTime());
	            }else {
	            	if(oDate.getYear() == currentDate.getYear()){
	            	if(oDate.getMonth() < currentDate.getMonth())
		            	DateStartPicker.setDate(Calendar.getInstance().getTime());
	            	if(oDate.getMonth() == currentDate.getMonth())
	            	{
	            		if(Integer.parseInt(tab[0]) - Integer.parseInt(currTab[0]) < 0)
	            		{
			            	DateStartPicker.setDate(Calendar.getInstance().getTime());
	            		}
	            	}
	            	
	            }}
	            nbrHours.setText((int)HoursCalcule() + " h");
				label.setText("<html><span style = \" font-size : x-large; font-weight: bold; color: rgb(65,145,218);\">"+park.getPrice() * (int)HoursCalcule()+"MAD</span></html>");
				lblNewLabel_2.setText("Prix pour "+(int)HoursCalcule()+"h");
				if((int)HoursCalcule() <= 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);
	        });
		  
		  startPan.add(DateStartPicker, BorderLayout.CENTER);
		  
		  Calendar startcal = Calendar.getInstance(); // creates calendar
		    startcal.setTime(new Date()); // sets calendar time/date
		    //startcal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		  Date startDate =  startcal.getTime();

		  SpinnerDateModel sdmstart = new SpinnerDateModel(startDate,null,null,Calendar.HOUR_OF_DAY);
		   startSpinner = new JSpinner(sdmstart);
		  JSpinner.DateEditor StartDe = new JSpinner.DateEditor(startSpinner, "HH:mm");
		  startSpinner.setEditor(StartDe);
			startSpinner.setBounds(253, 81, 98, 26);
			frame.getContentPane().add(startSpinner);
			
			startSpinner.addChangeListener(e->{ // hada dyal sa3a d Start
				Date curr = new Date();
				Calendar cal;
				
				Date eDate = DateEndPicker.getDate(); 
	        	Date stDate = DateStartPicker.getDate();
	            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String startcalDate = oDateFormat.format(startDate);
	            String ssDate = oDateFormat.format(stDate);
	            String seDate =   oDateFormat.format(eDate);
	            String[] sTab = ssDate.split("-");
	            String[] eTab = seDate.split("-");
	            String[] startcalDateTab = startcalDate.split("-");
	            
	            
				if(Integer.parseInt(sTab[0]) == Integer.parseInt(eTab[0])) 
				{
					if(Integer.parseInt(sTab[0]) == Integer.parseInt(startcalDateTab[0])) {
				if(((Date)startSpinner.getValue()).getHours() < startDate.getHours())
				{
					 cal = Calendar.getInstance(); // creates calendar
				    cal.setTime(new Date()); // sets calendar time/date
				    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
				  Date sDate =  cal.getTime();
				  SpinnerDateModel sdm = new SpinnerDateModel(sDate,null,null,Calendar.HOUR_OF_DAY);
				  startSpinner.setModel(sdm);
							}
				if(((Date)startSpinner.getValue()).getHours() == startDate.getHours())
				{
					if(((Date)startSpinner.getValue()).getMinutes() < startDate.getMinutes()) {
					 cal = Calendar.getInstance(); // creates calendar
				    cal.setTime(new Date()); // sets calendar time/date
				    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
				  Date sDate =  cal.getTime();
				  SpinnerDateModel sdm = new SpinnerDateModel(sDate,null,null,Calendar.HOUR_OF_DAY);
				  startSpinner.setModel(sdm);
					}}
					}}
				
				nbrHours.setText((int)HoursCalcule() + " h");
				label.setText("<html><span style = \" font-size : x-large; font-weight: bold; color: rgb(65,145,218);\">"+park.getPrice() * (int)HoursCalcule()+"MAD</span></html>");
				lblNewLabel_2.setText("Prix pour "+(int)HoursCalcule()+"h");
				if((int)HoursCalcule() <= 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);
			});
			
				
			/*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
			
			
		JPanel endPan = new JPanel();
		endPan.setBounds(67, 156, 174, 42);
		frame.getContentPane().add(endPan);
		endPan.setLayout(new BorderLayout(0, 0));
		  DateEndPicker = new JXDatePicker();
	        
		  DateEndPicker.setDate(local.getTime());
		  DateEndPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));    
		  DateEndPicker.addActionListener(e->{
	        	Date oDate = DateEndPicker.getDate(); 
	        	Date currentDate = new Date();
	            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String szDate = oDateFormat.format(oDate);
	            String curr =   oDateFormat.format(currentDate);
	            String[] currTab = curr.split("-");
	            String[] tab = szDate.split("-");
	            
	            if(oDate.getYear() < currentDate.getYear() )
	            {
	            	DateEndPicker.setDate(Calendar.getInstance().getTime());
	            }else if(oDate.getYear() == currentDate.getYear()){
	            	if(oDate.getMonth() < currentDate.getMonth())
	            		DateEndPicker.setDate(Calendar.getInstance().getTime());
	            	if(oDate.getMonth() == currentDate.getMonth())
	            	{
	            		if(Integer.parseInt(tab[0]) - Integer.parseInt(currTab[0]) < 0)
	            			DateEndPicker.setDate(Calendar.getInstance().getTime());
	            	}else {
	            		
	            	}	
	            }
	        	
	            nbrHours.setText((int)HoursCalcule() + " h");
				label.setText("<html><span style = \" font-size : x-large; font-weight: bold; color: rgb(65,145,218);\">"+park.getPrice() * (int)HoursCalcule()+"MAD</span></html>");
				lblNewLabel_2.setText("Prix pour "+(int)HoursCalcule()+"h");
				if((int)HoursCalcule() <= 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);
	        });
		  endPan.add(DateEndPicker, BorderLayout.CENTER);
		  
		  Calendar endcal = Calendar.getInstance(); // creates calendar
		    endcal.setTime(new Date()); // sets calendar time/date
		    endcal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		Date endDate = endcal.getTime();
		  SpinnerDateModel sdmend = new SpinnerDateModel(endDate,null,null,Calendar.HOUR_OF_DAY);
		   endSpinner = new JSpinner(sdmend);
		  JSpinner.DateEditor endDe = new JSpinner.DateEditor(endSpinner, "HH:mm");
		  endSpinner.setEditor(endDe);
			endSpinner.setBounds(253, 167, 98, 26);
			frame.getContentPane().add(endSpinner);
			endSpinner.addChangeListener(e->{// HADA DYAL SA3A DL END
				Calendar cal;
				Date eDate = DateEndPicker.getDate(); 
	        	Date sDate = DateStartPicker.getDate();
	            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String endcalDate = oDateFormat.format(endDate);
	            String ssDate = oDateFormat.format(sDate);
	            String seDate =   oDateFormat.format(eDate);
	            String[] sTab = ssDate.split("-");
	            String[] eTab = seDate.split("-");
	            String[] endcalDateTab = endcalDate.split("-");
	            
				if(Integer.parseInt(sTab[0]) == Integer.parseInt(eTab[0])) 
				{
					if(Integer.parseInt(eTab[0]) == (Integer.parseInt(endcalDateTab[0]) -1)) 
					{
						if(((Date)endSpinner.getValue()).getHours() < startDate.getHours())
						{
						cal = Calendar.getInstance(); // creates calendar
						cal.setTime(new Date()); // sets calendar time/date
						cal.add(Calendar.HOUR_OF_DAY, 2); // adds one hour
						Date date =  cal.getTime();
						SpinnerDateModel sdm = new SpinnerDateModel(date,null,null,Calendar.HOUR_OF_DAY);
						endSpinner.setModel(sdm);
						}
					}
				}
				nbrHours.setText((int)HoursCalcule() + " h");
				label.setText("<html><span style = \" font-size : x-large; font-weight: bold; color: rgb(65,145,218);\">"+park.getPrice() * (int)HoursCalcule()+"MAD</span></html>");
				lblNewLabel_2.setText("Prix pour "+(int)HoursCalcule()+"h");
				if((int)HoursCalcule() <= 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);

			});
		
	}
	
	
	public void visible(boolean b)
	{
		frame.setVisible(b);
	}
	
	private float HoursCalcule()
	{
		String ssDate = format.format(DateStartPicker.getDate());
		String seDate = format.format(DateEndPicker.getDate());
		String[] ssDateTab =ssDate.split("-");
		String[] seDateTab =seDate.split("-");
		int ssHeure = ((Date)startSpinner.getValue()).getHours();
		float ssMinutes = ((float)(((Date)startSpinner.getValue()).getMinutes())/100);
		int seHeure  = ((Date)endSpinner.getValue()).getHours();
		float seMinutes = ((float)(((Date)endSpinner.getValue()).getMinutes())/100);
		
		int result =  ((Integer.parseInt(seDateTab[0]) - Integer.parseInt(ssDateTab[0])) * 24) - ssHeure + seHeure ;
		float Rexacte = result - ssMinutes + seMinutes;
		/*if((Rexacte % Math.floor(Rexacte)) >= 0.3 && (Rexacte % Math.floor(Rexacte)) <= 0.99)
			return  (float) ((Rexacte - (Rexacte % Math.floor(Rexacte))) + 1);
		else
			return  (float) (Rexacte - (Rexacte % Math.floor(Rexacte)));*/
		return ((int)Math.ceil(Rexacte));
		
	}
	
	public ReservationCard dataBaseReserve()
	{
		
		String ssDate = format.format(DateStartPicker.getDate());
		String seDate = format.format(DateEndPicker.getDate());
		String[] ssDateTab =ssDate.split("-");
		String[] seDateTab =seDate.split("-");
		int ssHeure = ((Date)startSpinner.getValue()).getHours();
		int seHeure  = ((Date)endSpinner.getValue()).getHours();
		int ssMinutes = ((int)(((Date)startSpinner.getValue()).getMinutes()));
		int seMinutes = ((int)(((Date)endSpinner.getValue()).getMinutes()));
		int slotNum = 0;
		String RS=null, RE=null;

		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:8889/PARKING?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
			 stm = con.createStatement();
			 ResultSet query = stm.executeQuery("select numero from slots where parking_num ="+park.getNumero()+" and etat = 'inactif' limit 1");
			 while(query.next())
			 {
				 slotNum = query.getInt(1);
			 }
			 
			  RS = ssDateTab[2]+"-"+ssDateTab[1]+"-"+ssDateTab[0]+" "+ssHeure+":"+ssMinutes+":00";
			  RE = seDateTab[2]+"-"+seDateTab[1]+"-"+seDateTab[0]+" "+seHeure+":"+seMinutes+":00";
			 stm.executeUpdate("update slots set start_reservation='"+RS+"' where numero ="+slotNum+" and parking_num ="+park.getNumero());
			 stm.executeUpdate("update slots set end_reservation='"+RE+"'where numero ="+slotNum+" and parking_num ="+park.getNumero());
			 stm.executeUpdate("update slots set start_reservation='"+RS+"' where numero ="+slotNum+" and parking_num ="+park.getNumero());
			 stm.executeUpdate("update slots set etat = 'actif' where numero ="+slotNum+" and parking_num ="+park.getNumero());
			 stm.executeUpdate("update slots set reservedBy = '"+user.getMatricule()+"' where numero ="+slotNum+" and parking_num ="+park.getNumero());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReservationCard(slotNum, park.getName(), RS, RE,park.getPrice() * (int)HoursCalcule(),user);
		
	}
	
	
}
