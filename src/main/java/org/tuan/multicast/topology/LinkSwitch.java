package org.tuan.multicast.topology;

public class  LinkSwitch {

	private String src;
	private int port_src;
	private String dest;
	private int port_dest;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getPort_src() {
		return port_src;
	}

	public void setPort_src(int port_src) {
		this.port_src = port_src;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public int getPort_dest() {
		return port_dest;
	}

	public void setPort_dest(int port_dest) {
		this.port_dest = port_dest;
	}

	public LinkSwitch() {
		super();
	}

	public LinkSwitch(String src, int port_src, String dest, int port_dest) {
		super();
		this.src = src;
		this.port_src = port_src;
		this.dest = dest;
		this.port_dest = port_dest;
	}

}
