package org.tuan.multicast.flow;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.tuan.multicast.topology.*;

public class Flow {
	private String username = "admin";
	private String password = "admin";
	private String node = "";
	
	public Flow(String node) {
		super();
		this.node = node;
	}

	public void addFlow(String ipv4_src, String ipv4_dst) {
		HttpURLConnection conn = null;
//		http://localhost:8080/restconf/operations/sal-flow:add-flow
		String url = "http://localhost:8080/restconf/operations/sal-flow:add-flow";
		FlowXml f = new FlowXml();
		String body = f.createFlowXml(node, ipv4_src, ipv4_dst);
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
			
			System.out.println("Add flow to \"" + node + "\" response:	" + conn.getResponseMessage());
			
			if (conn.getResponseCode()==500) {
				System.out.println("kiem tra xem da co group chua");
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
