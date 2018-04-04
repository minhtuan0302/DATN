package org.tuan.multicast;

import java.util.ArrayList;

import org.tuan.multicast.flow.*;
import org.tuan.multicast.group.*;
import org.tuan.multicast.mst.Path;
import org.tuan.multicast.mst.Vertex;
import org.tuan.multicast.topology.*;

public class Multicast {
	private ArrayList<Host> listHost = new ArrayList<Host>();
	private ArrayList<Switch> listSwitch = new ArrayList<Switch>();
	private ArrayList<Vertex> listVertex = new ArrayList<Vertex>();
	private int numberVertex;
	
	public Multicast(Topology topo, ArrayList<Path> listPath, ArrayList<String> listIP) {
		super();
		listSwitch = topo.getList_switch();
		listHost = topo.getList_host();
		
// lay so sw tham gia nghe multicast		
		ArrayList<Integer> listID = new ArrayList<Integer>();
		listID.add(0);
		for (int i = 0; i < listPath.size(); i++) {
			Path path = listPath.get(i);
			for (int j = 0; j < path.getPath().size(); j++) {
				int n = 0;
				for (int k = 0; k < listID.size(); k++) {
					if(listID.get(k) == path.getPath().get(j)) {
						n++;
					}
				}	
				if (n==0) {
					listID.add(path.getPath().get(j));
				}
			}
		}
		numberVertex = listID.size();
// debug xac dinh so sw tham gia multicast
//		System.out.println(String.valueOf("So sw tham gia multicast la " + numberVertex));
		
// tao cac dinh tham gia multicast
		for (int i = 0; i < numberVertex; i++) {
			Switch sw = listSwitch.get(listID.get(i));
			Vertex vertex = new Vertex();
			
			vertex.setId(listID.get(i));
			vertex.setName(sw.getName());
//debug xem danh sach sw tham gia multicast
//			System.out.println(vertex.getId());

// set list host	
			ArrayList<Host> lH  = new ArrayList<Host>();
    		for (int j = 0; j < listHost.size(); j++ ) {
    			Host host = listHost.get(j);
    			for (int k = 0; k < listIP.size(); k++) {
    				if(host.getIp_address().equals(listIP.get(k)) && host.getSwitch_name().equals(sw.getName())) {
    					lH.add(host);
    				}
    			}
    		}  		
    		vertex.setListHost(lH);
    		
// set list next vertex    		
    		ArrayList<Integer> listNextVer = new ArrayList<Integer>();
    		for(int j = 0; j < listPath.size(); j++) {
    			ArrayList<Integer> path = listPath.get(j).getPath();
 
    			int index = path.indexOf(vertex.getId());
    			if(index !=-1 && index+1 != path.size()) {
    				int nextVertex = path.get(index+1);
    				int no = 0;
    				for (int k = 0; k < listNextVer.size(); k++) {
    					if (nextVertex == listNextVer.get(k)) {
    						no++;
    					}
    				}
    				if (no == 0) {
    					listNextVer.add(path.get(index+1));
    				}
    			}
    		}
    		vertex.setlNextVertex(listNextVer);
    		
// set list port
    		ArrayList<String> lP = new ArrayList<String>();
    		for (int j = 0; j < vertex.getlNextVertex().size(); j++) {
    			int dst = vertex.getlNextVertex().get(j);
    			lP.add(getPort(vertex.getId(), dst));
    		}
    		vertex.setListPort(lP);	
    		
// add vertext to list
    		listVertex.add(vertex);
		}
		
	}
	
	private String getPort(int srcID, int dstID) {
		Switch swSrc = listSwitch.get(srcID);
		Switch swDst = listSwitch.get(dstID);
		String dstName = swDst.getName();
		int port = 1000;
		for (int i = 0; i < swSrc.getSwitch_to_switch().size(); i++) {
			if (swSrc.getSwitch_to_switch().get(i).getDest().equals(dstName)) {
				port = swSrc.getSwitch_to_switch().get(i).getPort_src();
			}
		}
		return String.valueOf(port);
	}
	
	public void createMulticast(String ipv4_src, String ipv4_dst) {
		for (int i=0; i < numberVertex; i++) {
			System.out.println("");
			Vertex vertex = listVertex.get(i);
			Group group = new Group(vertex.getName());
			group.addGroup(vertex.getListHost(), vertex.getListPort());
			
			Flow flow = new Flow(vertex.getName());
			flow.addFlow(ipv4_src, ipv4_dst);
		}	
	}
}
