package org.tuan.multicast.flow;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FlowXml {

	private Element createMatchXml(String ipv4_source, String ipv4_destination, Document doc) {
		Element match = doc.createElement("match");
		Element ethernet_match = doc.createElement("ethernet-match");
		match.appendChild(ethernet_match);

//them ethernet type
		Element ethernet_type = doc.createElement("ethernet-type");
		Element type = doc.createElement("type");
		type.appendChild(doc.createTextNode("0x800"));
		ethernet_type.appendChild(type);
		ethernet_match.appendChild(ethernet_type);
		
//neu ipv4 nguon khac null thi them ipv4 nguon
		if (!ipv4_source.equals(null)) {
			Element ipv4_src = doc.createElement("ipv4-source");
			match.appendChild(ipv4_src);
			ipv4_src.appendChild(doc.createTextNode(ipv4_source+"/32"));
		}
//neu ipv4 dich khac null thi them ipv4 dich
		if (!ipv4_destination.equals(null)) {
			Element ipv4_dst = doc.createElement("ipv4-destination");
			match.appendChild(ipv4_dst);
			ipv4_dst.appendChild(doc.createTextNode(ipv4_destination+"/32"));
		}
		return match;
	}

	private Element createInstructionsXml(String gid, Document doc) {
		Element instructions = doc.createElement("instructions");
		Element instruction = doc.createElement("instruction");
		Element order_instruction = doc.createElement("order");
		Element apply_actions = doc.createElement("apply-actions");
		Element action = doc.createElement("action");
		Element order_action = doc.createElement("order");
		Element group_action = doc.createElement("group-action");
		Element group_id = doc.createElement("group-id");
		
		
		order_instruction.appendChild(doc.createTextNode("0"));
		order_action.appendChild(doc.createTextNode("0"));
		group_id.appendChild(doc.createTextNode(gid));
		
		instructions.appendChild(instruction);
		instruction.appendChild(order_instruction);
		instruction.appendChild(apply_actions);
		apply_actions.appendChild(action);
		action.appendChild(order_action);
		action.appendChild(group_action);
		group_action.appendChild(group_id);
		
		return instructions;

	}
	
	public String createFlowXml(String node_id, String ipv4_source, String ipv4_destination) {
	String s = null;
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		
		Element input = doc.createElement("input");
		input.setAttribute("xmlns", "urn:opendaylight:flow:service");
		Element barrier = doc.createElement("barrier");
		Element node = doc.createElement("node");
		node.setAttribute("xmlns:inv", "urn:opendaylight:inventory");
		Element cookie = doc.createElement("cookie");
		Element flags = doc.createElement("flags");
		Element hard_timeout = doc.createElement("hard-timeout");
		Element idle_timeout = doc.createElement("idle-timeout");
		Element installHw = doc.createElement("installHw");
		Element priority = doc.createElement("priority");
		Element strict = doc.createElement("strict");
		Element table_id = doc.createElement("table_id");
		Element match = this.createMatchXml(ipv4_source, ipv4_destination, doc);
		Element instructions = this.createInstructionsXml("1", doc);
		
		String node_value = "/inv:nodes/inv:node[inv:id=\"" + node_id + "\"]";
		
		barrier.appendChild(doc.createTextNode("false"));
		node.appendChild(doc.createTextNode(node_value));
		cookie.appendChild(doc.createTextNode("55"));
		flags.appendChild(doc.createTextNode("SEND_FLOW_REM"));
		hard_timeout.appendChild(doc.createTextNode("0"));
		idle_timeout.appendChild(doc.createTextNode("0"));
		installHw.appendChild(doc.createTextNode("false"));
		priority.appendChild(doc.createTextNode("25"));
		strict.appendChild(doc.createTextNode("false"));
		table_id.appendChild(doc.createTextNode("0"));
		
		doc.appendChild(input);
		input.appendChild(barrier);
		input.appendChild(node);
		input.appendChild(cookie);
		input.appendChild(flags);
		input.appendChild(hard_timeout);
		input.appendChild(idle_timeout);
		input.appendChild(installHw);
		input.appendChild(priority);
		input.appendChild(strict);
		input.appendChild(table_id);
		input.appendChild(match);
		input.appendChild(instructions);

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
//	debug flow xml 
//	System.out.println(s);
	return s;
	}

}
