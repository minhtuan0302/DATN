package org.tuan.multicast.topology;

public class Host{
	private String id;
	private String ip_address;
	private String mac_address;
	private String port_switch;
	private String switch_name;
	private boolean status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getPort_switch() {
		return port_switch;
	}
	public void setPort_switch(String port_switch) {
		this.port_switch = port_switch;
	}
	public String getSwitch_name() {
		return switch_name;
	}
	public void setSwitch_name(String switch_name) {
		this.switch_name = switch_name;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Host() {
		super();
	}
	public Host(String id, String address_ip, String address_mac, String port_switch, String switch_name, boolean status) {
		super();
		this.id = id;
		this.ip_address = address_ip;
		this.mac_address = address_mac;
		this.port_switch = port_switch;
		this.switch_name = switch_name;
		this.status = status;
	}
}
