package pojo;

public class Packet {
	
	@Override
	public String toString() {
		return "Packet [sendername=" + sendername + ", uid=" + uid + ", weight=" + weight + ", dimensions=" + dimensions
				+ ", pieces=" + pieces + ", from=" + from + ", fromstate=" + fromstate + ", to=" + to + ", tostate="
				+ tostate + "]";
	}
	private String sendername;
	private long uid;
	private String weight;
	private String dimensions;
	private int pieces;
	private String from;
	private String fromstate;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dimensions == null) ? 0 : dimensions.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((fromstate == null) ? 0 : fromstate.hashCode());
		result = prime * result + pieces;
		result = prime * result + ((sendername == null) ? 0 : sendername.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((tostate == null) ? 0 : tostate.hashCode());
		result = prime * result + (int) (uid ^ (uid >>> 32));
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Packet other = (Packet) obj;
		if (dimensions == null) {
			if (other.dimensions != null)
				return false;
		} else if (!dimensions.equals(other.dimensions))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (fromstate == null) {
			if (other.fromstate != null)
				return false;
		} else if (!fromstate.equals(other.fromstate))
			return false;
		if (pieces != other.pieces)
			return false;
		if (sendername == null) {
			if (other.sendername != null)
				return false;
		} else if (!sendername.equals(other.sendername))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (tostate == null) {
			if (other.tostate != null)
				return false;
		} else if (!tostate.equals(other.tostate))
			return false;
		if (uid != other.uid)
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromstate() {
		return fromstate;
	}
	public void setFromstate(String fromstate) {
		this.fromstate = fromstate;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTostate() {
		return tostate;
	}
	public void setTostate(String tostate) {
		this.tostate = tostate;
	}
	private String to;
	private String tostate;
	
	public Packet(String sendername, long uid, String weight, String dimensions, int pieces, String from,
			String fromstate, String to, String tostate) {
		super();
		this.sendername = sendername;
		this.uid = uid;
		this.weight = weight;
		this.dimensions = dimensions;
		this.pieces = pieces;
		this.from = from;
		this.fromstate = fromstate;
		this.to = to;
		this.tostate = tostate;
	}
	public Packet() {
		super();
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	

}
