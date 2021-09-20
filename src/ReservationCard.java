import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class ReservationCard  {


	private static final long serialVersionUID = 1L;
	private JFrame frame;
	User user;
	Thread t;


	
	public ReservationCard(int slotnum,String parkName,String start_reserve,String end_reserve,int price,User user) {
		this.user = user;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 365);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		
		JLabel cardClose = new JLabel("");
		cardClose.setBounds(549, 0, 51, 55);
		cardClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(cardClose);
		
		JLabel ParkNameLabel = new JLabel(parkName);
		ParkNameLabel.setBounds(440, 109, 139, 33);
		frame.getContentPane().add(ParkNameLabel);
		
		JLabel DDebutLabel = new JLabel(start_reserve);
		DDebutLabel.setBounds(403, 165, 176, 33);
		frame.getContentPane().add(DDebutLabel);
		
		JLabel DFinLabel = new JLabel(end_reserve);
		DFinLabel.setBounds(403, 220, 176, 26);
		frame.getContentPane().add(DFinLabel);
		
		JLabel PrixLabel = new JLabel(String.valueOf(price)+" DH");
		PrixLabel.setBounds(510, 284, 69, 33);
		frame.getContentPane().add(PrixLabel);
		
		JLabel SLOTNUM = new JLabel("<html><span style=\"font-size: 60; texte-align: center; font-weight: bold;\">"+String.valueOf(slotnum)+"</span></html>");
		SLOTNUM.setBounds(156, 173, 51, 91);
		frame.getContentPane().add(SLOTNUM);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/Mac/Downloads/cart.png"));
		lblNewLabel.setBounds(0, 0, 600, 343);
		frame.getContentPane().add(lblNewLabel);
		
		t =  new Thread(new Traitement());
		t.start();
		
	}
	
	public void visibility() {
		frame.setVisible(true);
	}
	
	public class Traitement implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int val=0;val<100;val++)
			{
				try {
				t.sleep(30);
				} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				}
			}

			 Point pos = frame.getLocationOnScreen();
			  Dimension size = frame.getSize();
			  Rectangle screenRect = new Rectangle(pos.x, pos.y, size.width, size.height);
			File docfile = new File(user.getMatricule()+".png");
			BufferedImage capture;
			try {
				capture = new Robot().createScreenCapture(screenRect);
				ImageIO.write(capture, "png", docfile);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new sendMail(user);
		}
		
	}
}
