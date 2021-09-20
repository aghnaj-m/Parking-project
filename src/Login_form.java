import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class Login_form extends JFrame {
	Connection  conn;
	Statement st;
	 ResultSet rs;
	 
	 WebCamScanner wcs;
	 
	private JFrame frame;
	private JTextField Tusername;
	private JTextField TPassword;
	JLabel lblLogin ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_form window = new Login_form();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 */
	
	


		
	public Login_form() throws Exception {
	try {	String driver = "com.mysql.cj.jdbc.Driver";
		   String url = "jdbc:mysql://localhost:3306/PARKING?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		   String username = "root";
		   String password = "root";
		   Class.forName(driver);
		  
		    conn = DriverManager.getConnection(url,username,password);

		} catch ( CommunicationsException e4 )
				{
					e4.printStackTrace();
					//JOptionPane.showMessageDialog(null, "THE SQL SERVER IS NOT RUNNING", "ERROR",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				   
				   
	
	
		  // return conn;

		//getConnection();
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/logofinal.png")));
		Font font1 = new Font("Suez One", 0, 20);	
		frame.getContentPane().setLayout(null);
		
		 lblLogin = new JLabel("login");
		lblLogin.setIcon(new ImageIcon(this.getClass().getResource("/loginbtn1.png")));
		lblLogin.addMouseListener(new MouseAdapter() {	
			public void mouseEntered(MouseEvent arg0) {
				lblLogin.setIcon(new ImageIcon(this.getClass().getResource("/loginbtn2.png")));
			}	
			public void mouseExited(MouseEvent e) {
				lblLogin.setIcon(new ImageIcon(this.getClass().getResource("/loginbtn1.png")));
				
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {

                   pass_verify();
			
			}
			
			
		});
		
		JLabel lblInscription = new JLabel("");
		lblInscription.setIcon(new ImageIcon(this.getClass().getResource("/inscription2.png")));
		lblInscription.addMouseListener(new MouseAdapter() {	 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
				 new Register_form();
				 frame.setVisible(false);
				// Login_form.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblInscription.setIcon(new ImageIcon(this.getClass().getResource("/inscription3.png")));

			}
			public void mouseExited
			(MouseEvent arg0) {
				lblInscription.setIcon(new ImageIcon(this.getClass().getResource("/inscription2.png")));

			}
		});
		lblInscription.setBounds(24, 238, 169, 147);
		frame.getContentPane().add(lblInscription);
		
		JLabel lblLoginQr = new JLabel("");
		lblLoginQr.setIcon(new ImageIcon(this.getClass().getResource("/buttonqr1.png")));
		lblLoginQr.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				 wcs = new WebCamScanner();
					wcs.visible(true);

				//frame.setVisible(false);
				
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblLoginQr.setIcon(new ImageIcon(this.getClass().getResource("/button-qr2.png")));

			}
			public void mouseExited(MouseEvent arg0) {
				lblLoginQr.setIcon(new ImageIcon(this.getClass().getResource("/buttonqr1.png")));

			}
		});
		lblLoginQr.setBounds(262, 281, 142, 58);
		frame.getContentPane().add(lblLoginQr);
		lblLogin.setBounds(409, 281, 142, 58);
		frame.getContentPane().add(lblLogin);
        frame.setResizable(false);
        lblInscription.setFont(new Font("Suez One", Font.PLAIN, 15));
        lblInscription.setForeground(Color.GRAY);
		
		
		Tusername = new JTextField();
		//Tusername.setText("Email");

		Tusername.setBounds(83, 107, 333, 48);
		frame.getContentPane().add(Tusername);
	
		Tusername.setColumns(10);
		Tusername.setBorder(null);
		Tusername.setFont(font1);
		Tusername.setForeground(Color.GRAY);
	//	TPassword.setText("password");
		TPassword = new JPasswordField();
		TPassword.setForeground(Color.GRAY);
		TPassword.setFont(new Font("Suez One", Font.PLAIN, 20));
		TPassword.setColumns(10);
		TPassword.setBorder(null);
		TPassword.setBounds(83, 185, 333, 48);
		frame.getContentPane().add(TPassword);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/login.jpg")));
		lblNewLabel.setBounds(0, 0, 601, 416);
		frame.getContentPane().add(lblNewLabel);
		
		frame.setBounds(100, 100, 584	, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		visibility(true);
		frame.setTitle("OH PARK LOGIN");	

		/*getContentPane().setLayout(null);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\pistooki\\Documents\\PFE\\login.jpg"));
setSize(1000, 1000);
		getContentPane().add(background);*/
	}
	
	void pass_verify() {
		String username = Tusername.getText();
		String password = TPassword.getText();
		int log =1;
		try {
			 st = (Statement)conn.createStatement();
		      rs = st.executeQuery("select * from inscription");
		//ResultSet rs2 = st.executeQuery("select Nom_du_proteur from inscription where Email like'"+username+"'");
		while(rs.next())
		{
			
		if (rs.getString(2).equals(username) && rs.getString(5).equals(password))
		{
		log = 0 ;
		}
		if (log==0) {
		
			if(rs.getString("adminOption") != null)
			{
				JOptionPane.showMessageDialog(null, "WELCOME BACK ADMIN!   ","",JOptionPane.INFORMATION_MESSAGE);
				new AdminPanel(rs.getString(1),rs.getString(2));
				frame.setVisible(false);
				break;
			}
			else {	
		JOptionPane.showMessageDialog(null, "WELCOME BACK!   ","",JOptionPane.INFORMATION_MESSAGE);
		new Progress(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
		frame.setVisible(false);
		break;
		}}}
		if (log == 1)
		{JOptionPane.showMessageDialog(null, "WRONG USERNAME OR PASSWORD","Warning",JOptionPane.WARNING_MESSAGE);
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void matricule_verify(String matricule,WebCamScanner wcs) {
		int log =1;
		try {
			 st = (Statement)conn.createStatement();
		      rs = st.executeQuery("select * from inscription");
		//ResultSet rs2 = st.executeQuery("select Nom_du_proteur from inscription where Email like'"+username+"'");
		while(rs.next()) {
		   if (rs.getString(3).equals(matricule))
		{
		log = 0 ;
		}
		if (log==0) {
		JOptionPane.showMessageDialog(lblLogin, "WELCOME BACK!   ");
		wcs.closeIt();
		new Progress(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
		frame.setVisible(false);
		break;
		}}
		if (log == 1)
		{JOptionPane.showMessageDialog(null, "USER NOT SUBSCRIBED","Warning",JOptionPane.WARNING_MESSAGE);
		wcs.closeIt();
		frame.setVisible(false);

		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	void visibility(boolean b) {
		
		frame.setVisible(b);
	}
}
