import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javafx.scene.text.Font;

public class AdminPanel {

	private JFrame frame;
	private JTable userTable,parkTable,slotTable;
	
	private Connection  conn;
	private Statement stm;
	private JTextField textField;
	DefaultTableModel userModel;
	DefaultTableModel parkModel;
	DefaultTableModel slotModel;
	JTabbedPane tabbedPane;
	
	String adminMail=null,adminName=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanel window = new AdminPanel("anass","elkarfianass@gmail.com");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminPanel(String name,String email) {
		this.adminMail = email;
		this.adminName= name;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1058, 765);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String driver = "com.mysql.cj.jdbc.Driver";
		   String url = "jdbc:mysql://localhost:3306/PARKING?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		   String username = "root";
		   String password = "root";
		   try {
			Class.forName(driver);
			     conn = DriverManager.getConnection(url,username,password);
			     stm = conn.createStatement();
		
		     ResultSet userSelect;  
		     userSelect = stm.executeQuery("select Nom_du_proteur , Vehicule ,Matricule from inscription");
		
		     JPanel deletePanel = new JPanel();
				deletePanel.setBackground(Color.BLACK);
				deletePanel.setBounds(0, 431, 245, 54);
				frame.getContentPane().add(deletePanel);
				deletePanel.setLayout(null);
				deletePanel.setVisible(false);
				
				JLabel deleteLabel = new JLabel("");
				deleteLabel.setBounds(0, 0, 245, 54);
				deletePanel.add(deleteLabel);
				deleteLabel.setIcon(new ImageIcon(this.getClass().getResource("/delete.png")));
				deleteLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						try {
					String s="";
					int jopRes;
						if(tabbedPane.getSelectedIndex() == 0) {
						 s = userTable.getValueAt(userTable.getSelectedRow(), 2).toString();
						jopRes =	JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer cet abonné ?" , "Attention", JOptionPane.YES_NO_OPTION );
							if(jopRes == JOptionPane.YES_OPTION)
						 stm.executeUpdate("delete from inscription where Matricule = \""+s+"\"");}
						else if(tabbedPane.getSelectedIndex() == 1) {
						jopRes = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer ce park ?" , "Attention", JOptionPane.YES_NO_OPTION );
						 s = parkTable.getValueAt(parkTable.getSelectedRow(), 0).toString();
						if(jopRes == JOptionPane.YES_OPTION)
						 stm.executeUpdate("delete from park where name = \""+s+"\"");
							}
						else if(tabbedPane.getSelectedIndex() == 2) {
						 s = slotTable.getValueAt(slotTable.getSelectedRow(), 5).toString();
						jopRes =  JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer cette reservation ?" , "Attention", JOptionPane.YES_NO_OPTION );
						if(jopRes == JOptionPane.YES_OPTION)
						 stm.executeUpdate("update  slots set etat = 'inactif', start_reservation= null,end_reservation = null, reservedBy = null  where reservedBy = \""+s+"\"");
							}
						}catch(SQLException e1)
						{
							e1.printStackTrace();
						}
						
					}
				});
		     
		     
		 	textField = new JTextField();
			textField.setBounds(449, 146, 342, 44);
			frame.getContentPane().add(textField);
			textField.setColumns(10);
			textField.setBorder(null);


			
			JLabel searchLabel = new JLabel("");
			searchLabel.setBounds(795, 146, 55, 44);
			searchLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					userSearch();
					parkSearch();
					slotSearch();
				}
				
			
			});
			frame.getContentPane().add(searchLabel);
		
			textField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					textField.setText("");
					deletePanel.setVisible(false);
				}
			});
		     
	    userModel =   new DefaultTableModel();      
		userTable = new JTable(userModel);
		//userTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		userTable.setBounds(247, 278, 811, 465);
        userTable.setRowHeight(30);
        userTable.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		// TODO Auto-generated method stub
        		super.mouseEntered(e);
        		textField.setText("Chercher par nom..");
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		// TODO Auto-generated method stub
        		super.mouseClicked(e);
        		deletePanel.setVisible(true);
        	}
		});

		userModel.addColumn(new String("<html><strong>NOM</strong></html>"));
		userModel.addColumn(new String("<html><strong>VEHICULE</strong></html>"));
		userModel.addColumn(new String("<html><strong>MATRICULE</strong></html>"));

		while(userSelect.next())
		{
			userModel.addRow(new Object[] {userSelect.getString("Nom_du_proteur"),userSelect.getString("Vehicule"),userSelect.getString("Matricule")});
		}
			
		 tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(247, 234, 811, 509);
		tabbedPane.addTab("table d'utilisateurs", new JScrollPane(userTable));
		/****************************************************************************/
		   ResultSet parkSelect  = stm.executeQuery("select name,price,latitude,longitude from park;");
			
		     parkModel =   new DefaultTableModel();      
			parkTable = new JTable(parkModel);
			//userTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			parkTable.setBounds(247, 278, 811, 465);
	        parkTable.setRowHeight(30);
			parkModel.addColumn(new String("<html><strong>NOM</strong></html>"));
			parkModel.addColumn(new String("<html><strong>PRIX</strong></html>"));
			parkModel.addColumn(new String("<html><strong>LATITUDE</strong></html>"));
			parkModel.addColumn(new String("<html><strong>LONGITUDE</strong></html>"));
			 parkTable.addMouseListener(new MouseAdapter() {
		        	@Override
		        	public void mouseEntered(MouseEvent e) {
		        		// TODO Auto-generated method stub
		        		super.mouseEntered(e);
		        		textField.setText("Chercher par prix..");
		        	}
		        	
		        	@Override
		        	public void mouseClicked(MouseEvent e) {
		        	// TODO Auto-generated method stub
		        	super.mouseClicked(e);
	        		deletePanel.setVisible(true);
		        	}
				});

			while(parkSelect.next())
			{
				parkModel.addRow(new Object[] {parkSelect.getString("name"),parkSelect.getInt("price"),parkSelect.getString("latitude"),parkSelect.getString("longitude")});
			}
				
			tabbedPane.addTab("table des parks", new JScrollPane(parkTable));
		
			/**************************************************************************/
			ResultSet slotSelect  = stm.executeQuery("select * from slots");
			
		     slotModel =   new DefaultTableModel();      
			 slotTable = new JTable(slotModel);
			//userTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			slotTable.setBounds(247, 278, 811, 465);
	        slotTable.setRowHeight(30);
			slotModel.addColumn(new String("<html><strong>NUMERO</strong></html>"));
			slotModel.addColumn(new String("<html><strong>ETAT</strong></html>"));
			slotModel.addColumn(new String("<html><strong>DEBUT RESERVAT..</strong></html>"));
			slotModel.addColumn(new String("<html><strong>FIN RESERVATION</strong></html>"));
			slotModel.addColumn(new String("<html><strong>PARK PARENT</strong></html>"));
			slotModel.addColumn(new String("<html><strong>OCCUPANT</strong></html>"));
			 slotTable.addMouseListener(new MouseAdapter() {
		        	@Override
		        	public void mouseEntered(MouseEvent e) {
		        		// TODO Auto-generated method stub
		        		super.mouseEntered(e);
		        		textField.setText("Chercher par état..");
		        	}
		        	@Override
		        	public void mouseClicked(MouseEvent e) {
		        	// TODO Auto-generated method stub
		        	super.mouseClicked(e);
	        		deletePanel.setVisible(true);
		        	}
		        	
		        	
				});

			while(slotSelect.next())
			{
				slotModel.addRow(new Object[] {slotSelect.getInt(1),slotSelect.getString(2),slotSelect.getDate(3),slotSelect.getDate(4),slotSelect.getInt(5),slotSelect.getString(6)});
			}
				
			tabbedPane.addTab("table des places", new JScrollPane(slotTable));
		
		frame.getContentPane().add(tabbedPane);
		
		JPanel Ajoutpanel = new JPanel();
		Ajoutpanel.setBounds(0, 374, 245, 54);
		frame.getContentPane().add(Ajoutpanel);
		Ajoutpanel.setLayout(null);
		
		JLabel ajoutLable = new JLabel("");
		ajoutLable.setBounds(0, 0, 245, 54);
		Ajoutpanel.add(ajoutLable);
		ajoutLable.setIcon(new ImageIcon("/Users/Mac/eclipse-workspace/GUI/PImages/ajouterAdmin.jpg"));
		ajoutLable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				try {
					new Register_form();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel adminNameLabel = new JLabel(adminName.toUpperCase());
		adminNameLabel.setFont(new java.awt.Font("Lucida Grande", java.awt.Font.PLAIN, 17));
		adminNameLabel.setForeground(Color.WHITE);
		adminNameLabel.setBounds(86, 109, 91, 16);
		frame.getContentPane().add(adminNameLabel);
		
		JLabel adminMailLabel = new JLabel(adminMail);
		adminMailLabel.setForeground(Color.WHITE);
		adminMailLabel.setFont(new java.awt.Font("Lucida Grande", java.awt.Font.BOLD, 15));
		adminMailLabel.setBounds(19, 160, 219, 19);
		frame.getContentPane().add(adminMailLabel);
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/Mac/eclipse-workspace/GUI/PImages/Panelfinal copie.jpg"));
		lblNewLabel.setBounds(0, 0, 1058, 743);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(6, 84, 61, 54);
		frame.getContentPane().add(lblNewLabel_1);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				deletePanel.setVisible(false);
			}
		});
		
		
		
	
		
		
		
		
		
		   } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		frame.setVisible(true);
	}
	
	public void userSearch()
	{
		userModel.setRowCount(0);
		try {
			ResultSet userS = stm.executeQuery("select Nom_du_proteur , Vehicule ,Matricule from inscription where Nom_du_proteur like \""+textField.getText()+"%\"");
			while(userS.next())
			{
				userModel.addRow(new Object[] {userS.getString("Nom_du_proteur"),userS.getString("Vehicule"),userS.getString("Matricule")});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parkSearch()
	{
		parkModel.setRowCount(0);
		try {
			ResultSet parkS;
			if(textField.getText().compareTo("") == 0)
				parkS = stm.executeQuery("select * from park");
				else
			parkS = stm.executeQuery("select * from park where price ="+textField.getText());
			while(parkS.next())
			{
				parkModel.addRow(new Object[] {parkS.getString("name"),parkS.getInt("price"),parkS.getString("latitude"),parkS.getString("longitude")});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void slotSearch()
	{
		slotModel.setRowCount(0);
		ResultSet slotS;
		try {
			if(textField.getText().compareTo("") == 0)
				slotS = stm.executeQuery("select * from slots");
			else
			 slotS = stm.executeQuery("select * from slots where etat = \""+textField.getText()+"\"");
			while(slotS.next())
			{
				slotModel.addRow(new Object[] {slotS.getInt(1),slotS.getString(2),slotS.getDate(3),slotS.getDate(4),slotS.getInt(5),slotS.getString(6)});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
