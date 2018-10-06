package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import pojo.Packet;

public class SaveToDB {
	Connection con = null;
	  PreparedStatement ps;
	public void save(LinkedList<Packet> packetList) throws ClassNotFoundException, SQLException {
		
		  con = DatabaseController.getConnection();
		ps = con.prepareStatement("insert into trackinginfo(sendername,uid,weight,dimensions,pieces,fromcity,fromstate,tocity,tostate) values(?,?,?,?,?,?,?,?,?)");
		  for(Packet p:packetList) {   
			  ps.setString(1, p.getSendername());
			  ps.setLong(2, p.getUid());
			  ps.setString(3, p.getWeight());
			  ps.setString(4, p.getDimensions());
			  ps.setInt(5, p.getPieces());
			  ps.setString(6, p.getFrom());
			  ps.setString(7, p.getFromstate());
			  ps.setString(8, p.getTo());
			  ps.setString(9, p.getTostate());
			  ps.execute();
		 }
	}
	public void savePacket(Packet p) throws ClassNotFoundException, SQLException {
		synchronized(this) {
		
		  con = DatabaseController.getConnection();
		ps = con.prepareStatement("insert into trackinginfo(sendername,uid,weight,dimensions,pieces,fromcity,fromstate,tocity,tostate) values(?,?,?,?,?,?,?,?,?)");
		ps.setString(1, p.getSendername());
		  ps.setLong(2, p.getUid());
		  ps.setString(3, p.getWeight());
		  ps.setString(4, p.getDimensions());
		  ps.setInt(5, p.getPieces());
		  ps.setString(6, p.getFrom());
		  ps.setString(7, p.getFromstate());
		  ps.setString(8, p.getTo());
		  ps.setString(9, p.getTostate());
		  ps.execute();
		}
	}
	public LinkedList<Packet> retrieve() throws ClassNotFoundException, SQLException {
		synchronized(this) {
		con = DatabaseController.getConnection();
		ps=con.prepareStatement("select * from trackinginfo");
		ResultSet rs = ps.executeQuery();
		Set<Packet> s= new LinkedHashSet<Packet>();
		while(rs.next())
		{
			Packet p=new Packet();
			p.setSendername(rs.getString("sendername"));
			p.setUid(rs.getLong("uid"));
			p.setWeight(rs.getString("weight"));
			p.setDimensions(rs.getString("dimensions"));
			p.setPieces(rs.getInt("pieces"));
			p.setFrom(rs.getString("fromcity"));
			p.setFromstate(rs.getString("fromstate"));
			p.setTo(rs.getString("tocity"));
			p.setTostate(rs.getString("tostate"));
			s.add(p); 			
		}
		LinkedList<Packet> l=new LinkedList<>();
		l.addAll(s);
		return l;
		}
	}
	

}
