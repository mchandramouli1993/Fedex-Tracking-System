package tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import pojo.Address;
import pojo.Packet;
import utils.DatabaseController;
import utils.SaveToDB;

public class FedX {
	static LinkedList<Packet> packetList = new LinkedList<>();
	static LinkedList<Address> addrList = new LinkedList<>();

	public FedX() throws NumberFormatException, IOException, SQLException, ClassNotFoundException {
		
		File file = new File("packetsinfo.txt");
		File f2 = new File("locations.txt");
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br1 = new BufferedReader(new FileReader(f2));
		String st1,st;
		/*Reading the contents from file, parsing based on spaces and adding to the linked list*/
		  while ((st1 = br1.readLine()) != null) {
			  String[] s= st1.split("\\s*,\\s*");
			  Address addr=new Address(s[0],s[1]);
			  addrList.add(addr);
		    
		  } 
		/*Reading the contents from file, parsing based on spaces and adding to the linked list*/
		  while ((st = br.readLine()) != null) {
			  String[] s= st.split("\\s*,\\s*");
			  Packet packet=new Packet(s[0],Long.parseLong(s[1]),s[2],s[3],Integer.parseInt(s[4]),s[5],s[6],s[7],s[8]);
			  packetList.add(packet);
		    
		  }
		  Connection con = null;
		  PreparedStatement ps;
		  con = DatabaseController.getConnection();
		  Statement stmt = con.createStatement();
		  String sql = "DELETE FROM trackinginfo";
		  stmt.executeUpdate(sql);
		  SaveToDB s=new SaveToDB();
		  s.save(packetList);
		  Tracker t[] = new Tracker[packetList.size()];
		  for(int i=0;i<packetList.size();i++) {
		  t[i] = new Tracker(packetList.get(i));
		  t[i].start();
		  }
		  //FedXUI.main(null);
	}
	 static class Tracker extends Thread {
		 	Packet np;
		 	int x,y;
		   public Tracker(Packet p) {
		       np=p;
		       
		   }
		   
		   public void run() {
			   SaveToDB s=new SaveToDB();
			   for(int i=0;i<addrList.size();i++) {
				   Address px=addrList.get(i);
				   if(np.getFrom().equals(px.getCity())) {
					   x=i;
				   }
				   if(np.getTo().equals(px.getCity())) {
					   y=i;
				   }
			   }
			   if(y>x) {
				   for(int i=x;i<=y;i++) {
					   Packet newp=new Packet(np.getSendername(),np.getUid(),np.getWeight(),np.getDimensions()
							   ,np.getPieces(),addrList.get(i).getCity(),np.getFromstate(),np.getTo(),np.getTostate());
					   try {
						s.savePacket(newp);
						TimeUnit.SECONDS.sleep(5);
					} catch (ClassNotFoundException | SQLException | InterruptedException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  // System.out.println("Package from "+np.getSendername()+" is at "+addrList.get(i).getCity());
				   }
			   }
			   else {
				   for(int i=x;i>=y;i--) {
					   Packet newp=new Packet(np.getSendername(),np.getUid(),np.getWeight(),np.getDimensions()
							   ,np.getPieces(),addrList.get(i).getCity(),np.getFromstate(),np.getTo(),np.getTostate());
					   try {
						s.savePacket(newp);
						TimeUnit.SECONDS.sleep(5);
					} catch (ClassNotFoundException | SQLException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  // System.out.println("Package from "+np.getSendername()+" is at "+addrList.get(i).getCity());
				   }
			   }
			   
				 
			}

		   
		}
	 public LinkedList<Packet> getPacketList() {
		 return packetList;
	 }
	

}
