package org.tuan.multicast.mst;

import java.util.ArrayList;

public class Path {
	private ArrayList<Integer> path = new ArrayList<Integer>();

	public ArrayList<Integer> getPath() {
		return path;
	}

	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}

	public Path(ArrayList<Integer> path) {
		super();
		this.path = path;
	}
	
	
}
