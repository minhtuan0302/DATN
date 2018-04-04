package org.tuan.multicast.topology;

public class Link {
	private String id;
	private String source;
	private String destination;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Link(String id, String source, String destination) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
	}
	
	public Link() {
		super();
	}
	
}
