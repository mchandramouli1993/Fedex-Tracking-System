package tracker;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;

import pojo.Packet;
import utils.SaveToDB;

public class FedXUI extends JPanel { 
	FedX f;
	static JPanel details = new JPanel();
	static JPanel status = new JPanel();
	
  private JLabel a;
  private JTextField t1;
  private JButton b1;
  private BufferedImage image;
  
  public FedXUI() throws NumberFormatException, ClassNotFoundException, IOException, SQLException {
	  f=new FedX();
	  setLayout(new FlowLayout());
	  BufferedImage i=ImageIO.read(new File("./src/fedx2.jpg"));
	  JLabel img=new JLabel(new ImageIcon(i)); 
	  a=new JLabel("Enter your unique tracking id:");
	  t1=new JTextField(15);
	  Icon ic=new ImageIcon(getClass().getResource("../refedx.png"));
	  b1=new JButton("Find my package",ic);
	  t1.setToolTipText("Enter tracking number on your invoice");
	  add(img);
	  add(a);
	  add(t1);
	  add(b1);
	  theHandler h=new theHandler();
	  t1.addActionListener(h);
	  b1.addActionListener(h);
	  
  }
  static class ImagePanel extends JPanel{
	  

	    private BufferedImage image;
	    private Image simage;

	    public ImagePanel() throws NumberFormatException, ClassNotFoundException, IOException, SQLException {
	       try {                
	         
	          image = ImageIO.read(this.getClass().getResource("../back.jpg"));
	          simage=image.getScaledInstance(1920,1194, Image.SCALE_SMOOTH);
	       } catch (IOException ex) {
	            ex.printStackTrace();
	       }
	       
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(simage, 0, 0, this); // see javadoc for more info on the parameters            
	    }
	}
  public static void  main(String[] args) throws NumberFormatException, ClassNotFoundException, IOException, SQLException {
	  
	  FedXUI t = new FedXUI();
	  ImagePanel Ip=new ImagePanel();
	  JFrame f=new JFrame("FedX Tracker");
	  f.setLayout(new BorderLayout());
	  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  f.setSize(500, 500);
	  JPanel container = new JPanel();
	  container.setLayout(new GridBagLayout());
	  container.setOpaque(false);
	  details.setLayout(new BoxLayout(details, BoxLayout.PAGE_AXIS));
	  details.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	  details.setOpaque(false);
	  JScrollPane scrollPane=new JScrollPane(details);
	  scrollPane.setPreferredSize(new Dimension(150,400));
	  scrollPane.setBorder(null);
	  scrollPane.setOpaque(false);
	  scrollPane.getViewport().setOpaque(false);
	  status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
	  status.setOpaque(false);
	  t.setOpaque(false);
	  t.setAlignmentX(Component.LEFT_ALIGNMENT);
	  GridBagConstraints c = new GridBagConstraints();
	  c.fill=GridBagConstraints.HORIZONTAL;
	  c.gridx = 0;
	  c.gridy = 0;
	  c.gridwidth = 2;
	  container.add(t);
	  c.fill=GridBagConstraints.HORIZONTAL;
	  c.gridx = 1;
	  c.gridy = 0;
	  c.gridwidth = 1;
	  container.add(status);
	  c.fill=GridBagConstraints.HORIZONTAL;
	  c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		container.add(scrollPane);
		f.add(Ip);
      Ip.add(container);
      container.setLocation(350, 700);
      f.setLocationRelativeTo(null);
      f.pack();
	  f.setVisible(true);
	  
  }
  private class theHandler implements ActionListener{
	  String str="";
	  
	  public void actionPerformed(ActionEvent e) {
		  Object s = e.getSource();
		  
		  Packet packet=new Packet();
		  if(s==t1) {
			  str=String.format("Entered id is %s", e.getActionCommand());
			  JOptionPane.showMessageDialog(null,str);
		  }
		  else if (s==b1) {
			  long uid=Long.parseLong(t1.getText());
			  SaveToDB sb=new SaveToDB();
				LinkedList<Packet> lp = null;
				
				try {
					lp = sb.retrieve();
				} catch (ClassNotFoundException | SQLException em) {
					// TODO Auto-generated catch block
					em.printStackTrace();
				}
				LinkedList<Packet> plist=new LinkedList<>();
				plist=f.getPacketList();
				for(Packet p:plist) {
					if(p.getUid()==uid) {
						packet=p;
						p.toString();
					}
				}
				
				JLabel j,titleforfacts,titleforstatus,sname,dimensions,weight,tid,from,to;
				titleforfacts=new JLabel("Shipment Facts:");
				tid=new JLabel("Tracking no: "+String.valueOf(packet.getUid()));
				sname=new JLabel("From: "+packet.getSendername());
				from=new JLabel("Delivered from: "+packet.getFrom()+", "+packet.getFromstate());
				to=new JLabel("Destination : "+packet.getTo()+", "+packet.getTostate());
				dimensions=new JLabel("Dimensions: "+packet.getDimensions());
				weight=new JLabel("Weight: "+packet.getWeight());
				status.removeAll();
				details.removeAll();
				titleforfacts=new JLabel("Shipment Facts:");
				titleforstatus=new JLabel("Package status");
				titleforstatus.setAlignmentX(Component.CENTER_ALIGNMENT);
				status.add(titleforfacts);
				status.add(tid);
				status.add(sname);
				status.add(from);
				status.add(to);
				status.add(dimensions);
				status.add(weight);
				details.add(titleforstatus);
				for(Packet p:lp) {
					if(uid==p.getUid()) {
						j=new JLabel(p.getFrom());
						//j.setAlignmentX();
						j.setAlignmentX(Component.CENTER_ALIGNMENT);
						details.add(j);
						BasicArrowButton bab=new BasicArrowButton(BasicArrowButton.SOUTH);
						bab.setAlignmentX(Component.CENTER_ALIGNMENT);
						bab.setOpaque(false);
						bab.setContentAreaFilled(false);
						bab.setBorderPainted(false);
						details.add(bab);
						if(p.getFrom().equals(packet.getTo())) {
							JLabel jab=new JLabel("Delivered");
							jab.setForeground(new Color(51,145,8));
							jab.setAlignmentX(Component.CENTER_ALIGNMENT);
							details.add(jab);
						}
						//details.add(new BasicArrowButton(BasicArrowButton.SOUTH), BorderLayout.SOUTH);
						details.revalidate();
						details.repaint();
						status.revalidate();
						status.repaint();
					}
				}
			
		  }  
	  }
	  
  }
}
