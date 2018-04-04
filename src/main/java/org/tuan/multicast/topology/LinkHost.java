package org.tuan.multicast.topology;

public class LinkHost {
	private String output_port;
	private String ip_address;
	
	public String getOutput_port() {
		return output_port;
	}
	public void setOutput_port(String output_port) {
		this.output_port = output_port;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public LinkHost(String output_port, String ip_address) {
		super();
		this.output_port = output_port;
		this.ip_address = ip_address;
	}
	public LinkHost() {
		super();
	}	
//	public int getId() {
//		return output_port;
//	}
//
//	public void setId(int id) {
//		this.output_port = id;
//	}
//
//	public String getAddress() {
//		return ip_address;
//	}
//
//	public void setAddress(String address) {
//		this.ip_address = address;
//	}
//
//	public LinkHost(int id, String address) {
//		super();
//		this.output_port = id;
//		this.ip_address = address;
//	}
//
//	public LinkHost() {
//		super();
//	}

}
