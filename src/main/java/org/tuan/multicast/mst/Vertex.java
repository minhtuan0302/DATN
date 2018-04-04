package org.tuan.multicast.mst;

import java.util.ArrayList;

import org.tuan.multicast.topology.Host;

public class Vertex {
	private int id;
	private String name;
	private ArrayList<Integer> lNextVertex;
	private ArrayList<String> listPort;
	private ArrayList<Host> listHost;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Integer> getlNextVertex() {
		return lNextVertex;
	}
	public void setlNextVertex(ArrayList<Integer> lNextVertex) {
		this.lNextVertex = lNextVertex;
	}
	public ArrayList<String> getListPort() {
		return listPort;
	}
	public void setListPort(ArrayList<String> listPort) {
		this.listPort = listPort;
	}
	public ArrayList<Host> getListHost() {
		return listHost;
	}
	public void setListHost(ArrayList<Host> listHost) {
		this.listHost = listHost;
	}
	public Vertex() {
		super();
	}
	
	
	
}
