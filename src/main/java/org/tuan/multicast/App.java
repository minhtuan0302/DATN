package org.tuan.multicast;

import java.util.ArrayList;
import org.tuan.multicast.mst.*;
import org.tuan.multicast.topology.*;

public class App 
{
    public static void main( String[] args )
    {
//danh sach dia chi ip cac host tham gia multicast
    	ArrayList<String> listIP = new ArrayList<String>();
    	listIP.add("10.0.0.4");
    	listIP.add("10.0.0.6");
    	listIP.add("10.0.0.8");
    	listIP.add("10.0.0.10");
    	listIP.add("10.0.0.12");
//dia chi ip host phat multicast    	
    	String ip_source = "10.0.0.2";
//quet topology mang luu vao file network.json    	
    	ScanTopology scan = new ScanTopology();
     	scan.writeFileTopo();
//lay du lieu topo mang tu file network.json     	
     	Topology topo = new Topology();
     	topo.getData();  	
//xac dinh sw nguon phat multicast
     	topo.sortSW(ip_source);
//danh sach cac sw dki nghe multicast     	
     	ArrayList<Integer> listIDSW = new ArrayList<Integer>();
     	listIDSW = topo.getListIDSW(listIP);
//chuyen doi topo mang thanh canh do thi     	
     	Convert convert = new Convert();
     	convert.convertLink(topo);
     	
     	ArrayList<Edge> listEdge = new ArrayList<Edge>();
     	listEdge = convert.getListEdge();
     	
     	int num_SW = topo.getList_switch().size();
     	int num_Link = topo.getList_link().size();
//khoi tao thuat toan PRIM - minimum spanning tree		
     	MST tree = new MST(num_SW);
		  
// tao ma tran (sw x sw)
		  int graph[][] = new int[num_SW][num_SW];
		                             
		  for (int i = 0; i < num_SW; i++) {
			  for (int j = 0; j < num_SW; j++) {
				  graph[i][j] = 0;
			  }
		  }
// set trong so cac canh 		  
		  for (int i = 0; i < num_Link; i++) {
			  Edge e = new Edge();
			  e = listEdge.get(i);
			  graph[e.getSrc_id()][e.getDst_id()] = 50;
// cac canh mac dinh bang 50, canh cac sw nghe multicast bang 5
			  for (int j = 0; j < listIDSW.size(); j++) {
				  for (int k = 0; k < listIDSW.size(); k++) {
					  if (e.getSrc_id()==listIDSW.get(j) && e.getDst_id()==listIDSW.get(k)) {
						  graph[e.getSrc_id()][e.getDst_id()] = 5;
					  }
				  }
			  }		  
		  }
		  
// in ma tran cac canh
		  System.out.println("Ma tran");
		  for (int i = 0; i < num_SW; i++) {
			  for (int j = 0; j < num_SW; j++) {
				  System.out.print(String.valueOf(graph[i][j] + "	"));
			  }
			  System.out.println("");
			  System.out.println("");
		  }
// thuc hien thuat toan
      tree.primMST(graph);
      
// tim duong tu sw bien ve root
      ArrayList<Path> listPath = new ArrayList<Path>();
      System.out.println("");
      System.out.println("Path multicast");
      for (int i = 1; i < listIDSW.size(); i++) {
    	  Path path = new Path(tree.path(listIDSW.get(i)));
    	  listPath.add(path);
    	  System.out.println("");
      }
      
// in duong dan multicast
      System.out.println("");
      System.out.println("Duong dan multicast");
      for (int i = 0; i < listPath.size(); i++) {
    	  Path path = listPath.get(i);
    	  for (int j = 0; j < path.getPath().size(); j++) {
    		  System.out.print(topo.getList_switch().get(path.getPath().get(j)).getName() + "	");
    	  }
    	  System.out.println("");
      }

// them cac flow va group cho sw
      Multicast mul = new Multicast(topo, listPath, listIP);
      mul.createMulticast(ip_source, "224.0.0.1");
      
      System.out.println("");
      System.out.println("Hello Multicast!");
    }
}
