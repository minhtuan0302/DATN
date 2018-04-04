package org.tuan.multicast.topology;

import java.util.ArrayList;

public class Switch {
	private String name;
	private ArrayList<LinkHost> switch_to_host;
	private ArrayList<LinkSwitch> switch_to_switch;

	public Switch(String name, ArrayList<LinkHost> switch_to_host, ArrayList<LinkSwitch> switch_to_switch) {
		super();
		this.name = name;
		this.switch_to_host = switch_to_host;
		this.switch_to_switch = switch_to_switch;
	}

	public Switch() {
		super();
	}

	public Switch(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<LinkHost> getSwitch_to_host() {
		return switch_to_host;
	}

	public void setSwitch_to_host(ArrayList<LinkHost> switch_to_host) {
		this.switch_to_host = switch_to_host;
	}

	public ArrayList<LinkSwitch> getSwitch_to_switch() {
		return switch_to_switch;
	}

	public void setSwitch_to_switch(ArrayList<LinkSwitch> switch_to_switch) {
		this.switch_to_switch = switch_to_switch;
	}

}
