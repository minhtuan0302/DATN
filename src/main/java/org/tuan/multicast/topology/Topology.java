package org.tuan.multicast.topology;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Topology {
	private String topo = "/home/tuan/multicast/network.json";
	private ArrayList<Host> list_host;
	private ArrayList<Switch> list_switch;
	private ArrayList<Link> list_link;
	

	public Topology() {
		super();
		list_host = new ArrayList<Host>();
		list_switch = new ArrayList<Switch>();
		list_link = new ArrayList<Link>();
	}

	public String getTopo() {
		return topo;
	}

	public void setTopo(String topo) {
		this.topo = topo;
	}

	public ArrayList<Host> getList_host() {
		return list_host;
	}

	public void setList_host(ArrayList<Host> list_host) {
		this.list_host = list_host;
	}

	public ArrayList<Switch> getList_switch() {
		return list_switch;
	}

	public void setList_switch(ArrayList<Switch> list_switch) {
		this.list_switch = list_switch;
	}
	
	public ArrayList<Link> getList_link() {
		return list_link;
	}

	public void setList_link(ArrayList<Link> list_link) {
		this.list_link = list_link;
	}

	public void getData() {
		JSONParser parser = new JSONParser();
		Host h;
		Switch sw;
		LinkSwitch l ;
		LinkHost ln;
		ArrayList<LinkSwitch>list_link=new ArrayList<LinkSwitch>();
		ArrayList<LinkHost>ArrLinkNode;
		ArrayList<LinkSwitch>ArrLinkSW ;
		try {
			Object object = parser.parse(new FileReader(this.topo));
			JSONObject json = (JSONObject) object;
			JSONObject network_topology = (JSONObject) json.get("network-topology");
			JSONArray topology = (JSONArray) network_topology.get("topology");
			for (Object i : topology) {
				JSONObject temp = (JSONObject) i;
				JSONArray node = (JSONArray) temp.get("node");
				JSONArray link = (JSONArray) temp.get("link");
				for (Object i1 : link) {
					JSONObject js=(JSONObject)i1;
					//System.out.println(js);
					String te=(String) js.get("link-id");

// lay link tu sw den sw bang cach loai dau /					
					if(te.contains("/")==false) {
						
// chia source-tp thanh 3 phan cach nhau boi dau :
						String [] te1 =te.split(":",3);
						String name_src =te1[0]+":"+te1[1];
						String port_src = te1[2];
						JSONObject tem=(JSONObject) js.get("destination");
//						System.out.println(jsA);
						String tem1=(String) tem.get("dest-tp");
//						System.out.println(tem1);
// chia destination-tp thanh 3 phan cach nhau boi dau :						
						String []tem2=tem1.split(":",3);
						String name_dest=tem2[0]+":"+tem2[1];
						String port_dest=tem2[2];
						 l=new LinkSwitch();
						 l.setSrc(name_src);
						 l=new LinkSwitch(name_src, Integer.parseInt(port_src), name_dest, Integer.parseInt(port_dest));
						 list_link.add(l);
						 
//lay danh sach link tu file network.json
						 Link sw_sw = new Link("", name_src, name_dest);
						 this.list_link.add(sw_sw);

						 
					}
				}
				for (Object j : node) {
					JSONObject temp1 = (JSONObject) j;
					if (((String) temp1.get("node-id")).contains("host") == true) {
						h = new Host();
						JSONArray temp2 = (JSONArray) temp1.get("host-tracker-service:addresses");
						for (Object k : temp2) {
							String ip = (String) ((JSONObject) k).get("ip");
							long id = (Long) ((JSONObject) k).get("id");
							String mac = (String) ((JSONObject) k).get("mac");
							h.setIp_address(ip);
							h.setId(Long.toString(id));
							h.setMac_address(mac);
							//System.out.println(ip+" "+id+" "+mac);
						}
						JSONArray temp3 = (JSONArray) temp1.get("host-tracker-service:attachment-points");
						for (Object k1 : temp3) {
							String k2 = ((String) ((JSONObject) k1).get("tp-id"));
							String[] k3 = k2.split(":", 3);
							boolean status = ((Boolean) ((JSONObject) k1).get("active"));
							// System.out.println(status);
							String switch_name = k3[0] + ":" + k3[1];
							h.setPort_switch(k3[2]);
							h.setSwitch_name(switch_name);
							h.setStatus(status);
							// System.out.println(k3[2]+" "+switch_name);
						}
						this.list_host.add(h);
					}
					}
				
				}
//				 for (Host m : this.list_host) {
//				 System.out.println(m.getId() + " ");
//				 }
			for(Object o :topology) {
				JSONObject oo = (JSONObject)o;
				JSONArray node1 = (JSONArray) oo.get("node");
				for(Object ob :node1) {
					sw =new Switch();
					String name_switch=(String)((JSONObject) ob).get("node-id");
					//System.out.println(name_switch);
					ArrLinkSW =new ArrayList<LinkSwitch>();
					ArrLinkNode =new ArrayList<LinkHost>();
					if (name_switch.contains("openflow")==true) {
					for (Host h1:this.list_host) {
						if(h1.getSwitch_name().equals(name_switch) && h1.isStatus()==true) {
							ln =new LinkHost();
							ln.setIp_address(h1.getIp_address());
							ln.setOutput_port(h1.getPort_switch());
							ArrLinkNode.add(ln);
						}
					}
					for (LinkSwitch ll :list_link) {
						if(ll.getSrc().equals(name_switch)) {
							ArrLinkSW.add(ll);
						}
					}
					sw.setName(name_switch);
					sw.setSwitch_to_host(ArrLinkNode);
					sw.setSwitch_to_switch(ArrLinkSW);
					this.list_switch.add(sw);
//					System.out.println(name_switch);
				}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sortSW(String ip_source) {
		String switchName = null;
		for (int i = 0; i < list_host.size(); i++) {
			if(list_host.get(i).getIp_address().equals(ip_source)) {
				switchName= list_host.get(i).getSwitch_name();
			}
		}
		//switch id la thu tu cua switch trong list switch
		int switchID = 0;
		for (int i = 0; i < list_switch.size(); i++) {
			if (list_switch.get(i).getName().equals(switchName)) {
				switchID = i;
			}
		}
		Switch temSW = list_switch.get(0);
		list_switch.set(0, list_switch.get(switchID));
		list_switch.set(switchID, temSW);
		
		for (int i = 0; i < list_switch.size(); i++) {
			System.out.println(String.valueOf(i) + "  " + list_switch.get(i).getName());
		}
	}

	public ArrayList<Integer> getListIDSW(ArrayList<String> listIP) {
		
		ArrayList<Integer> listID = new ArrayList<Integer>(); 
		listID.add(0);
		for (int i = 0; i < list_switch.size(); i++) {
			Switch sw = list_switch.get(i);
			ArrayList<LinkHost> listLinkHost = sw.getSwitch_to_host();
			for (int j = 0; j < listLinkHost.size(); j++) {
				String ip = listLinkHost.get(j).getIp_address();
				for ( int k = 0; k < listIP.size(); k++) {
					if (ip.equals(listIP.get(k))){
						listID.add(i);
						k = listIP.size();
						j = listLinkHost.size();
					}
				}
				
			}
		}
		return listID;
	}
}