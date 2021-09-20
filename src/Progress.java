import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Color;



public class Progress extends JFrame {

	/**
	 * Create the panel.
	 */
	Thread t;
	JProgressBar progressBar;
	User user;
	public Progress(User user) {
		this.user = user;
		getContentPane().setLayout(null);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setBounds(0, 0, 600, 430);
		
		t =  new Thread(new Traitement());
		 progressBar = new JProgressBar();
		progressBar.setBounds(0, 382, 600, 20);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		getContentPane().add(progressBar);
		t.start();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/sscreenFinal.jpg")));
		lblNewLabel.setBounds(0, 0, 600, 417);
		getContentPane().add(lblNewLabel);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public class Traitement implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int val=0;val<100;val++)
			{
				progressBar.setValue(val);
				try {
				t.sleep(100);
				} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				}
			}
			
			//JOptionPane.showMessageDialog(null, "tout est bien", "Etat", JOptionPane.INFORMATION_MESSAGE);
			
			MapPart2 map = new MapPart2(user);
			map.frame.setVisible(true); 
			
			setVisible(false);
		}
		
	}
	
}
