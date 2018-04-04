package org.tuan.multicast.group;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.tuan.multicast.topology.*;

public class Group {
	private String username = "admin";
	private String password = "admin";
	private String node = "";
	
	public Group(String node) {
		super();
		this.node = node;
	}
	
	public void addGroup(ArrayList<Host> listHost, ArrayList<String> listPort) {
		int status = 0;
		while (status != 200) {
			HttpURLConnection conn = null;
//			http://localhost:8080/restconf/operations/sal-group:add-group
			String url = "http://localhost:8080/restconf/operations/sal-group:add-group";
			GroupXml f = new GroupXml();
			String body = f.createGroupXml(node, listHost, listPort);
			try {
				conn = (HttpURLConnection) (new URL(url)).openConnection();
				conn.setRequestMethod("POST");
				BasicAuthenication ba = new BasicAuthenication();
				conn.setRequestProperty("Authorization", "Basic" + " " + ba.Authen(this.username, this.password));
				conn.setRequestProperty("Content-Type", "application/xml");
				conn.setRequestProperty("Accept", "application/xml");
				conn.setDoOutput(true);
				conn.setReadTimeout(3000);
				conn.setConnectTimeout(3000);
				OutputStream os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();
				conn.disconnect();
				
				if (conn.getResponseCode() == 200) {
					status = 200;
					System.out.println("Add group to \"" + node + "\" response:	" + conn.getResponseMessage());
				}else {
					this.deleteGroup();
				}
				
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void deleteGroup() {
		HttpURLConnection conn = null;
//		http://localhost:8080/restconf/operations/sal-group:remove-group
		String url = "http://localhost:8080/restconf/operations/sal-group:remove-group";
		GroupXml f = new GroupXml();
		String body = f.deleteGroupXml(node);
		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			conn.setRequestMethod("POST");
			BasicAuthenication ba = new BasicAuthenication();
			conn.setRequestProperty("Authorization", "Basic" + " " + ba.Authen(this.username, this.password));
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setDoOutput(true);
			conn.setReadTimeout(3000);
			conn.setConnectTimeout(3000);
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			conn.disconnect();
			
			System.out.println("Delete group in \"" + node + "\" response:	" + conn.getResponseMessage());
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
