package org.tuan.multicast.mst;

import java.util.ArrayList;

import org.tuan.multicast.topology.Link;
import org.tuan.multicast.topology.Switch;
import org.tuan.multicast.topology.Topology;

public class Convert {
	private Topology topo = new Topology();
	private ArrayList<Edge> listEdge = new ArrayList<Edge>();
	
	public ArrayList<Edge> getListEdge() {
		return listEdge;
	}

	public void setListEdge(ArrayList<Edge> listEdge) {
		this.listEdge = listEdge;
	}

	public void convertLink(Topology t) {
		this.topo = t;
		ArrayList<Link> tListLink = topo.getList_link();
     	ArrayList<Switch> tListSW = topo.getList_switch();
     		
     	for (int i = 0; i < tListLink.size(); i++) {
			Link link = tListLink.get(i);
			String src = link.getSource();
			String dst = link.getDestination();
			int src_id = 0;
			int dst_id = 0;
			for (int j = 0; j < tListSW.size(); j++) {
				if(tListSW.get(j).getName().equals(src)) {
					src_id =  j;
				}
				if(tListSW.get(j).getName().equals(dst)) {
					dst_id =  j;
				}
       	 	}
			
			Edge edge = new Edge(src_id, dst_id, 1);
			listEdge.add(edge);
			
     	}
	}
}
