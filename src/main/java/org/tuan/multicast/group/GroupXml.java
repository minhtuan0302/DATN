package org.tuan.multicast.group;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.tuan.multicast.topology.Host;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GroupXml {
	
	private Element createBucketXml(String bucketid, String outport, Document doc) {
		Element bucket = doc.createElement("bucket");
		Element bucket_id = doc.createElement("bucket-id");
		Element action = doc.createElement("action");
		Element order = doc.createElement("order");
		Element output_action = doc.createElement("output-action");
		Element output_node_connector = doc.createElement("output-node-connector");		
		
		bucket_id.appendChild(doc.createTextNode(bucketid));
		order.appendChild(doc.createTextNode("0"));
		output_node_connector.appendChild(doc.createTextNode(outport));
		
		bucket.appendChild(bucket_id);
		bucket.appendChild(action);
		action.appendChild(order);
		action.appendChild(output_action);
		output_action.appendChild(output_node_connector);
		
		return bucket;
	}
	
	private Element createBucketXml(String bucketid, String mac_dst, String ipv4_dst, String outport, Document doc) {
		Element bucket = doc.createElement("bucket");
		Element bucket_id = doc.createElement("bucket-id");
		Element action0 = doc.createElement("action");
		Element action1 = doc.createElement("action");
		Element action2 = doc.createElement("action");
		Element order0 = doc.createElement("order");
		Element set_field0 = doc.createElement("set-field");
		Element ethernet_match = doc.createElement("ethernet-match");
		Element ethernet_destination = doc.createElement("ethernet-destination");
		Element address = doc.createElement("address");
		
		Element order1 = doc.createElement("order");
		Element set_field1 = doc.createElement("set-field");
		Element ipv4_destination = doc.createElement("ipv4-destination");
		
		Element order2 = doc.createElement("order");
		Element output_action = doc.createElement("output-action");
		Element output_node_connector = doc.createElement("output-node-connector");		
		
		bucket_id.appendChild(doc.createTextNode(bucketid));
		order0.appendChild(doc.createTextNode("0"));
		order1.appendChild(doc.createTextNode("1"));
		order2.appendChild(doc.createTextNode("2"));
		address.appendChild(doc.createTextNode(mac_dst));
		ipv4_destination.appendChild(doc.createTextNode(ipv4_dst + "/32"));
		output_node_connector.appendChild(doc.createTextNode(outport));
		
		bucket.appendChild(bucket_id);
		
		bucket.appendChild(action0);
		action0.appendChild(order0);
		action0.appendChild(set_field0);
		set_field0.appendChild(ethernet_match);
		ethernet_match.appendChild(ethernet_destination);
		ethernet_destination.appendChild(address);
		
		bucket.appendChild(action1);
		action1.appendChild(order1);
		action1.appendChild(set_field1);
		set_field1.appendChild(ipv4_destination);
		
		bucket.appendChild(action2);
		action2.appendChild(order2);
		action2.appendChild(output_action);
		output_action.appendChild(output_node_connector);
		
		return bucket;

	}
	
	public String createGroupXml(String node_id, ArrayList<Host> listHost, ArrayList<String> listPort) {
		String s = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element input = doc.createElement("input");
			input.setAttribute("xmlns", "urn:opendaylight:group:service");
			Element barrier = doc.createElement("barrier");
			Element node = doc.createElement("node");
			node.setAttribute("xmlns:inv", "urn:opendaylight:inventory");
			Element group_id = doc.createElement("group-id");
			Element group_name = doc.createElement("group-name");
			Element group_type = doc.createElement("group-type");
			Element buckets = doc.createElement("buckets");
			
			String node_value = "/inv:nodes/inv:node[inv:id=\"" + node_id + "\"]";
			
			barrier.appendChild(doc.createTextNode("false"));
			node.appendChild(doc.createTextNode(node_value));
			
			group_id.appendChild(doc.createTextNode("1"));
			group_name.appendChild(doc.createTextNode("Mul"));
			group_type.appendChild(doc.createTextNode("group-all"));
			
			doc.appendChild(input);
			input.appendChild(barrier);
			input.appendChild(node);
			input.appendChild(group_id);
			input.appendChild(group_name);
			input.appendChild(group_type);
			input.appendChild(buckets);
			
//them bucket cho host
			for (int i=0; i<listHost.size(); i++) {
				Host host = listHost.get(i);
				Element bucket = this.createBucketXml(Integer.toString(i), host.getMac_address(), host.getIp_address(), host.getPort_switch(), doc);
				buckets.appendChild(bucket);
			}
			
//them bucket cho switch
			for (int i=0; i<listPort.size(); i++) {
				Element bucket = this.createBucketXml(Integer.toString(i+10), listPort.get(i), doc);
				buckets.appendChild(bucket);
			}

			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			s = writer.toString();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		debug group xml 
//		System.out.println(s);
		return s;
		}

	public String deleteGroupXml(String node_id) {
		String s = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element input = doc.createElement("input");
			input.setAttribute("xmlns", "urn:opendaylight:group:service");
			Element barrier = doc.createElement("barrier");
			Element node = doc.createElement("node");
			node.setAttribute("xmlns:inv", "urn:opendaylight:inventory");
			Element group_id = doc.createElement("group-id");
			Element group_type = doc.createElement("group-type");
			
			String node_value = "/inv:nodes/inv:node[inv:id=\"" + node_id + "\"]";
			
			barrier.appendChild(doc.createTextNode("false"));
			node.appendChild(doc.createTextNode(node_value));
			
			group_id.appendChild(doc.createTextNode("1"));
			group_type.appendChild(doc.createTextNode("group-all"));
			
			doc.appendChild(input);
			input.appendChild(barrier);
			input.appendChild(node);
			input.appendChild(group_id);
			input.appendChild(group_type);
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			s = writer.toString();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		debug group xml 
//		System.out.println(s);
		return s;
	}
}
