package org.tuan.multicast.mst;

public class Edge {
	private int src_id;
	private int dst_id;
	private int weight;
	public int getSrc_id() {
		return src_id;
	}
	public void setSrc_id(int src_id) {
		this.src_id = src_id;
	}
	public int getDst_id() {
		return dst_id;
	}
	public void setDst_id(int dst_id) {
		this.dst_id = dst_id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Edge(int src_id, int dst_id, int weight) {
		super();
		this.src_id = src_id;
		this.dst_id = dst_id;
		this.weight = weight;
	}
	public Edge() {
		super();
	}
	
	
}
