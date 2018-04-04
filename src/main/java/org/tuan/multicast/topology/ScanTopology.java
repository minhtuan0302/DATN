package org.tuan.multicast.topology;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ScanTopology {
	private String IPController = "localhost";
	private String url = "http://" + this.IPController + ":8181/restconf/operational/network-topology:network-topology";
	private String username = "admin";
	private String password = "admin";

	public String getIPController() {
		return IPController;
	}

	public void setIPController(String iPController) {
		IPController = iPController;
	}

	public ScanTopology(String url) {
		this.url = url;
	}

	public ScanTopology() {
	}

	public ScanTopology(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJSON() {
		HttpURLConnection conn = null;
		String temp = null;
		try {
			conn = (HttpURLConnection) (new URL(this.url)).openConnection();
			conn.setRequestMethod("GET");
			BasicAuthenication ba = new BasicAuthenication();
			conn.setRequestProperty("Authorization", "Basic" + " " + ba.Authen(this.username, this.password));
			conn.setRequestProperty("Content-Type", "application/yang.data+json");
			conn.setReadTimeout(3000);
			conn.setConnectTimeout(3000);
			conn.connect();
			if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
				System.out.println("Lay topology " + conn.getResponseMessage());
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				temp = sb.toString();
			}
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (temp == null) {
			System.out.println("Don't return");
		}
		conn.disconnect();
		return temp;
	}

	public boolean writeFileTopo() {
		boolean bol = true;
		File folder = new File("/home/tuan/multicast");
		if (folder.exists() == false)
			bol = folder.mkdirs();
		if (bol == false) {
			System.out.println("Error Directory");
			return bol;
		} else {
			File file = new File("/home/tuan/multicast/network.json");
			if (file.exists() == true) {
				file.delete();
				try {
					file.createNewFile();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				BufferedWriter wr = new BufferedWriter(new FileWriter(file));
				wr.write(this.getJSON());
				wr.flush();
				wr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Luu topology vao file network.json");
			return bol;
		}
	}

}
