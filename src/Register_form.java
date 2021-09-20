import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class Register_form {

	private JFrame frame;
	private JTextField Nome_Porteur;
	private JTextField Ve;
	private JTextField Matricule;
	private JTextField Password;
	private JTextField Email;
	private JPasswordField Password_Confirm;
	private JTextField Phone_number;
	private JLabel Signup;

	/**
	 * Launch the application.
	 */


	
public Register_form() throws Exception  {
	String driver = "com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://localhost:3306/PARKING?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	   String username = "root";
	   String password = "root";
	   Class.forName(driver);
			   Connection  conn = DriverManager.getConnection(url,username,password);
			   System.out.println("Connected");
		Font font1 = new Font("Suez One", 0, 20);
		frame = new JFrame();
		frame.setBounds(100, 100, 1058, 722);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	    frame.setTitle("OH PARK ");
        frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/logofinal.png")));
		
		Nome_Porteur = new JTextField();
		Nome_Porteur.setForeground(Color.GRAY);
		Nome_Porteur.setText("Nom du porteur");
		Nome_Porteur.setBounds(111, 143, 406, 59);
		Nome_Porteur.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Nome_Porteur.setText("");
			}
		});
	
		frame.getContentPane().add(Nome_Porteur);
		Nome_Porteur.setColumns(10);
		Nome_Porteur.setBorder(null);
		Nome_Porteur.setFont(font1);
		
		Ve = new JTextField();
		Ve.setForeground(Color.GRAY);
		Ve.setBounds(627, 364, 406, 59);
		
		frame.getContentPane().add(Ve);
		Ve.setBorder(null);
		Ve.setFont(font1);
		Ve.setText("Vehicule");
		Ve.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Ve.setText("");
			}
		});

		Matricule = new JTextField();
		Matricule.setBounds(111, 364, 406, 59);
		frame.getContentPane().add(Matricule);
		Matricule.setBorder(null);
		Matricule.setFont(font1);
		Matricule.setText("Matricule");
		Matricule.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Matricule.setText("");
			}
		});
		Matricule.setForeground(Color.gray);
		
		Password = new JPasswordField();
		Password.setBounds(627, 143, 409, 59);
		frame.getContentPane().add(Password);
		Password.setColumns(10);
		Password.setBorder(null);
		Password.setFont(font1);
		Password.setForeground(Color.gray);
		Password.setText("Password");
		Password.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Password.setText("");
			}
		});
		
		Password_Confirm = new JPasswordField();
		Password_Confirm.setForeground(Color.GRAY);
		Password_Confirm.setFont(new Font("Suez One", Font.PLAIN, 20));
		Password_Confirm.setColumns(10);
		Password_Confirm.setBorder(null);
		Password_Confirm.setBounds(624, 253, 409, 59);
		Password_Confirm.setText("Password");
		Password_Confirm.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Password_Confirm.setText("");
			}
		});

		frame.getContentPane().add(Password_Confirm);
		
		Email = new JTextField();
		Email.setBounds(111, 253, 406, 59);
		frame.getContentPane().add(Email);
		Email.setColumns(10);
		Email.setBorder(null);
		Email.setFont(font1);
		Email.setText("Email");
		Email.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Email.setText("");
			}
		});
		Email.setForeground(Color.gray);
		
		Phone_number = new JTextField();
		Phone_number.setText("Numero de telephone");
		Phone_number.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Phone_number.setText("");
			}
		});
		Phone_number.setBounds(111, 474, 406, 59);
		frame.getContentPane().add(Phone_number);
		Phone_number.setColumns(10);
		Phone_number.setFont(font1);
		Phone_number.setForeground(Color.gray);
		Phone_number.setBorder(null);
	
	JLabel lblLogin = new JLabel("<html>si vous avez d\u00E9j\u00E0 un compte, <a href=\\\"#\\\" style=\" color : rgb(35,85,136);\">cliquez ici pour vous connecter</a></html>");
	lblLogin.setForeground(Color.GRAY);
	lblLogin.setFont(new Font("Suez One", Font.PLAIN, 15));
	lblLogin.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
try {
 new Login_form();
 frame.setVisible(false);
	
//	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
//lgf.Login_form();
		}
	});
	lblLogin.setBounds(46, 611, 439, 33);
	frame.getContentPane().add(lblLogin);
	
	Signup = new JLabel("");
	Signup.setIcon(new ImageIcon(this.getClass().getResource("/signupbutton.png")));
	
	Signup.addMouseListener(new MouseAdapter() {	
		@Override
		public void mouseEntered(MouseEvent e) {
			Signup.setIcon(new ImageIcon(this.getClass().getResource("/signupbuttonclicked.png")));

		}
		@Override
		public void mouseClicked(MouseEvent e) {	
		String Mt = Matricule.getText();
			if (Nome_Porteur.getText().equals("") || Nome_Porteur.getText().equals("Nom du porteur") ){
            JOptionPane.showMessageDialog(null, "\r\n" + 
            		"Ajouter un nom d'utilisateur");}
			else	if (Email.getText().equals("") || Email.getText().equals("Email")){
	            JOptionPane.showMessageDialog(null, "Add A Email");}
			else if (Matricule.getText().equals("") || Matricule.getText().equals("Matricule")){
	            JOptionPane.showMessageDialog(null, "Add A maricule");}
			
			
			
			else if (Phone_number.getText().equals("") || Phone_number.getText().equals("Numero de telephone")){
	            JOptionPane.showMessageDialog(null, "Add A phone number");}
			else if (Password.getText().equals("") || Password.getText().equals("Password")){
	            JOptionPane.showMessageDialog(null, "Pass error");}
			else if ( !Password.getText().equals(Password_Confirm.getText())) {
	            JOptionPane.showMessageDialog(null, "les mots de passe ne sont pas les memes\r\n" + 
	            		"");}
			else if (Ve.getText().equals("") || Ve.getText().equals("Vehicule") ){
	            JOptionPane.showMessageDialog(null, "add the name of the car");}
 
			
			else {String query = "insert into inscription values(?,?,?,?,?,?,?,?)";
			PreparedStatement Statment;
			try {
				Statment = conn.prepareStatement(query);
				Statment.setString(1,Nome_Porteur.getText());
				Statment.setString(2,Email.getText());
					Statment.setString(3,Matricule.getText());
					Statment.setString(4,Phone_number.getText());
					Statment.setString(5,Password.getText());
					Statment.setString(6,Password_Confirm.getText());
					Statment.setString(7,Ve.getText());
					Statment.setString(8, null);
					Statment.execute();
					
					JOptionPane.showMessageDialog(null," user regestred seccessfully","DONE",JOptionPane.INFORMATION_MESSAGE);
					new createQR(Mt);
			
			//new Login_form();
			//frame.setVisible(false);
            

			/*logintest zez = new logintest();
		zez.setVisible(true);
		zez.setBounds(100, 100, 450, 300);
		zez.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
			
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				// TODO Auto-generated catch block
JOptionPane.showMessageDialog(null,"Cette matricule existe deja  ","error",JOptionPane.ERROR_MESSAGE);
		}catch (com.mysql.cj.jdbc.exceptions.MysqlDataTruncation e2) {
			JOptionPane.showMessageDialog(null,"Le numero de Telephone est invalid  ","error",JOptionPane.ERROR_MESSAGE);

		} catch(Exception e3) {
			e3.printStackTrace();
		}
			
		
		}}
		public void mouseExited(MouseEvent e) {
			Signup.setIcon(new ImageIcon(this.getClass().getResource("/signupbutton.png")));

		}
	});
	
	Signup.setBounds(779, 592, 188, 75);	
	frame.getContentPane().add(Signup);
	
	JLabel background = new JLabel("");
	background.setIcon(new ImageIcon(this.getClass().getResource("/signup2.jpg")));
	background.setBounds(0, 0, 1054, 687);
	frame.getContentPane().add(background);
    frame.setVisible(true);

		}
}
