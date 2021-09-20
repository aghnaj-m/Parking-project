
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class MapPart2 {

	private WebView browser;
	private WebEngine webEngine;
	
	Connection con;
	Statement stmt;
	
	 JFrame frame;
	 JPanel Container;
	 JPanel MapPan;
	 JPanel sidePan,contenu,resultPan;
	 JPanel logoPan;
	 JPanel butPan;
	 JLabel logo;
	 JLabel lblResultats;
	 JComboBox<String> comboBox;
	 JScrollPane myScrollPane;
	 JButton zoomOutButton,zoomInButton,setMarkerButton;
	
	 Vector<Park> parkVector = new Vector<Park>();
	 User user;
	 Reserve reservation;
	
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;
	private static int zoomValue = 4;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public MapPart2(User user) {
		this.user = user;
		initialize();
		aboutMap();
		AjoutDesPark("PROXIMITÉ");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("OHPark : Map");
		frame.setBounds(0, 0, 1280, 725);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Container = new JPanel(); //le grand pane qui contient tous
		Container.setBounds(0, 0, 1280, 701);
		frame.getContentPane().add(Container);
		Container.setLayout(null);
		
		sidePan = new JPanel();	//toute la partie à gauche
		sidePan.setBackground(Color.WHITE);
		sidePan.setBounds(0, 0, 357, 695);
		Container.add(sidePan);
		sidePan.setLayout(null);
		
		logoPan = new JPanel(); //contient LOGO 
		logoPan.setBackground(new Color(209, 209, 209));
		logoPan.setBounds(0, 0, 357, 145);
		sidePan.add(logoPan);
		logoPan.setLayout(null);
		
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(this.getClass().getResource("/bar.jpg")));
		logo.setBounds(0, 0, 357, 60);
		logoPan.add(logo);
		
	    lblResultats = new JLabel("RESULTATS       TRIER PAR");
		lblResultats.setFont(new Font("Suez One", Font.PLAIN, 13));
		lblResultats.setBounds(6, 92, 176, 28);
		logoPan.add(lblResultats);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Suez One", Font.PLAIN, 13));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"PROXIMITÉ", "PRIX"}));
		comboBox.setBounds(208, 94, 130, 27);
		comboBox.addActionListener(e->{
			String choix = (String)comboBox.getSelectedItem();
			parkVector.clear();
			contenu.removeAll();
			AjoutDesPark(choix);
			contenu.revalidate();
			contenu.repaint();
		});
		logoPan.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(358, 127, -356, 12);
		logoPan.add(separator);
		
		
		
        myScrollPane = new JScrollPane();
        myScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollPane.setPreferredSize(new Dimension(357,551));
        myScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contenu = new JPanel(); // contient les parking resultants
        
        contenu.setPreferredSize(new Dimension(357,1500));
        FlowLayout fl_contenu = new FlowLayout();
        fl_contenu.setVgap(0);
        contenu.setLayout(fl_contenu);
		myScrollPane.setViewportView(contenu);

        
        resultPan = new JPanel();
        resultPan.setBounds(0, 144, 357, 551);
        sidePan.add(resultPan);
        resultPan.add(myScrollPane);		

     // panel du map		
		MapPan = new JPanel(); 
		MapPan.setBounds(358, 0, 916, 650);
		Container.add(MapPan);
		MapPan.setLayout(new BorderLayout(0, 0));
		
		butPan = new JPanel(); // panel des buttons en bas
		butPan.setBounds(358, 653, 926, 48);
		butPan.setBackground(new Color(238,238,238));
		Container.add(butPan);
		butPan.setLayout(null);
		
		 zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		zoomOutButton.setBackground(new Color(30, 144, 255));
		zoomOutButton.setBounds(220, 6, 124, 29);
		butPan.add(zoomOutButton);
		zoomInButton = new JButton("Zoom In");
		zoomInButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		zoomInButton.setBackground(new Color(30, 144, 255));
		zoomInButton.setBounds(417, 6, 124, 29);
		butPan.add(zoomInButton);
		
		setMarkerButton = new JButton("Ma position");
		setMarkerButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		setMarkerButton.setBackground(new Color(30, 144, 255));
		setMarkerButton.setBounds(606, 6, 124, 29);
		butPan.add(setMarkerButton);
		
		
		
	}
	
	private void aboutMap()
	{
		
        
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                	zoomIn();
                }
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                	zoomOut();
                }
            }
        });

        setMarkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               addMarker();
            }
        });
        
        
        initAndShowGUI(MapPan);
	}
	
	public void AjoutDesPark(String choix)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:8889/PARKING?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
			 stmt = con.createStatement();
			 stmt.executeUpdate("SET GLOBAL event_scheduler = ON");
			ResultSet query,nbrPlace;
			
			

			 
			if(choix.compareTo("PROXIMITÉ") == 0)
			query = stmt.executeQuery("select * from park order by distance");
			else
				query = stmt.executeQuery("select * from park order by price");
			while(query.next())
			{
				parkVector.add(new Park(query.getInt(1),query.getString(2),query.getInt(3),query.getString(4),query.getString(5),query.getFloat(6),query.getFloat(7))); 
			}
			
			for(int i=0;i<parkVector.size();i++)
			{
				nbrPlace = stmt.executeQuery("select count(*) from slots where etat = 'inactif' and parking_num ="+parkVector.get(i).getNumero());
				
			  JPanel Park1 = new JPanel();
			Park1.setPreferredSize(new Dimension(357, 93));
			Park1.setLayout(null);
			
			JLabel parkName = new JLabel(parkVector.get(i).getName());
			parkName.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			parkName.setBounds(6, 6, 105, 21);
			Park1.add(parkName);
			
			JLabel carIcon = new JLabel("");
			carIcon.setBounds(6, 30, 32, 25);
			carIcon.setIcon(new ImageIcon(this.getClass().getResource("/duration.png")));
			Park1.add(carIcon);
			
			JLabel durationLabel = new JLabel(parkVector.get(i).getDuration());
			durationLabel.setBounds(45, 30, 61, 18);
			Park1.add(durationLabel);
			Separator sep = new Separator();
			sep.setBounds(0, 0, 0, 0);
			Park1.add(sep);
			Park1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209),2));
			contenu.add(Park1);
			
			JLabel distanceIcon = new JLabel("");
			distanceIcon.setIcon(new ImageIcon(this.getClass().getResource("/distance.png")));
			distanceIcon.setBounds(6, 52, 32, 35);
			Park1.add(distanceIcon);
			
			JLabel distanceLabel = new JLabel(parkVector.get(i).getDistance());
			distanceLabel.setBounds(45, 60, 61, 16);
			Park1.add(distanceLabel);
			
			JButton labBut = new JButton("<html><center style=\"text-align:center; \"> RESERVER À<br><strong style=\"color: rgb(65,145,218); font-size : large;\">"+parkVector.get(i).getPrice()+" MAD</strong></center></html>");
			labBut.setHorizontalAlignment(SwingConstants.CENTER);
			//labBut.setBackground(new Color(65, 145, 218));
			//labBut.setContentAreaFilled(false);
			//labBut.setOpaque(true);
			labBut.setBounds(202, 15, 117, 52);
			int test = i;
		
			
			while(nbrPlace.next())
			{
				if(nbrPlace.getInt(1) == 0)
					labBut.setEnabled(false);
			}

			labBut.addActionListener(e->{
				ResultSet request;
				try {
					request = stmt.executeQuery("select count(*) from slots where reservedBy = '"+user.getMatricule()+"' and etat = 'actif'");
				

				int userExistance = 0;
			
					while(request.next())
					{
						userExistance = request.getInt(1);
					}
				
				
				if(userExistance > 0)
				{
					int JOptionResult =JOptionPane.showConfirmDialog(null, "Vous avez deja une reservation \nVoulez vous la supprimer et reserver à NOUVEAU ?");
					if(JOptionResult == JOptionPane.YES_OPTION)
					{
						
							stmt.executeUpdate("update slots set etat = 'inactif' where reservedBy = '"+user.getMatricule()+"'");
							stmt.executeUpdate("update slots set start_reservation = null where reservedBy = '"+user.getMatricule()+"'");
							stmt.executeUpdate("update slots set end_reservation = null where reservedBy = '"+user.getMatricule()+"'");
							stmt.executeUpdate("update slots set reservedBy = null where reservedBy = '"+user.getMatricule()+"'");
						reservation = new Reserve(parkVector.get(test),user);
						reservation.visible(true);
					}
				}
				else {
					reservation = new Reserve(parkVector.get(test),user);
					reservation.visible(true);
				}
				}catch(SQLException e3)
				{
					e3.printStackTrace();
				}
			});
			
			
			Park1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					toPark(test);
			}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					Park1.setBackground(new Color(65,145,218));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					Park1.setBackground(new Color(238,238,238));

				}
				
			});
			
			Park1.add(labBut);
			}
			
		}catch(Exception e)
		{
			//JOptionPane.showMessageDialog(null, "MAKE SURE THAT DATABASE SERVER IS RUNING", "ERROR", JOptionPane.ERROR_MESSAGE);
			//System.exit(0);
			e.printStackTrace();
		}
		
	}
	
    private  void initAndShowGUI(JPanel pan) {
        // This method is invoked on the EDT thread
        final JFXPanel fxPanel = new JFXPanel();
        pan.add(fxPanel);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private  void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private  Scene createScene() {
    	 browser = new WebView();
         webEngine = browser.getEngine();
  
         File file = new File("/Users/Mac/eclipse-workspace/GUI/src/map.html");
         URL url;
         try {
 		url = file.toURI().toURL();
 		System.out.println("Local URL: " + url.toString());
 	    webEngine.load(url.toString());
 		} catch (MalformedURLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 					}
                    
          
  
         StackPane root = new StackPane();
 		root.getChildren().addAll(browser);
  
         Scene scene = new Scene(root);

        return (scene);
    }
    
    public void addMarker()
    {
    	 Platform.runLater(new Runnable() {
             @Override
             public void run() {
            	 webEngine.executeScript(""
             	 		+ " var pinColor = \"0000FF\";\n" + 
                  		"    var pinImage = new google.maps.MarkerImage(\"http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|\" + pinColor,\n" + 
                  		"        new google.maps.Size(21, 34),\n" + 
                  		"        new google.maps.Point(0,0),\n" + 
                  		"        new google.maps.Point(10, 34));"
                  		+ "var myLatlng = new google.maps.LatLng(33.857508,-5.579532\n" + 
                  		");\n" +
                          "var marker = new google.maps.Marker({\n" +
                          "    position: myLatlng,\n" +
                          "    map: map,\n"
                          + "icon: pinImage," +
                          "    title: 'Position actuelle'\n" +
                          "});"
                          + "map.panTo(myLatlng);");
             }
        });
    	 
    }
    
    public void zoomIn() {
    	
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	webEngine.executeScript("map.setZoom(" + ++zoomValue + ")");
            }
       });
    }
    public void zoomOut()
    {
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.executeScript("map.setZoom(" + --zoomValue + ")");
            }
       });
    }
    
    public void toPark(int test)
    {
    	//int t = test;
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	 webEngine.executeScript("var loc = {lat: "+parkVector.get(test).getLatitude()+", lng: "+parkVector.get(test).getLongitude()+"};"
          	           + "map.panTo(loc);"
          	           + "test("+parkVector.get(test).getNumero()+",'"+parkVector.get(test).getDistance()+"','"+parkVector.get(test).getDuration()+"');"
          	         );
            }
       });
    }
    
   
			
    
}
