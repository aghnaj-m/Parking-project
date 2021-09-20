import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class WebCamScanner extends JFrame implements Runnable, ThreadFactory {

	private static final long serialVersionUID = 6441489157408381878L;

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
	private WebcamPanel panel = null;
//	private JTextArea textarea = null;

	public WebCamScanner() {
		super();
		setLayout(new FlowLayout());
		setTitle("OH PARK : Login With QR CODE ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	webcam.close();
		    }
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/logofinal.png")));
		Dimension size = WebcamResolution.QVGA.getSize();

		webcam = Webcam.getWebcams().get(0);
		webcam.setViewSize(size);

		panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);
		panel.setFPSDisplayed(true);
	
		//textarea = new JTextArea();
		//textarea.setEditable(false);
		//textarea.setPreferredSize(size);

		add(panel);
		//add(textarea);

		pack();

		executor.execute(this);
	}
	
	public void closeIt() { webcam.close();}
	public void openIt() { webcam.open();}
	public void visible(boolean b)
	{
		setVisible(b);
	}

	@Override
	public void run() {
		
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;

			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {
					result = new MultiFormatReader().decode(bitmap);
				} catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
				}
			}

			if (result != null) {
			//	textarea.setText(result.getText());
				//JOptionPane.showMessageDialog(null, "Voila le CODE :\n"+result.getText(), "QR CODE", JOptionPane.INFORMATION_MESSAGE);
				
			//JOptionPane.showMessageDialog(null,"WELCOME BACK ");

				//new MapPart();
		
		try {
			Login_form lf = new Login_form();
			lf.matricule_verify(result.getText(),this);
			lf.visibility(false);
			setVisible(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			}

		} while (true);
		
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

	
}