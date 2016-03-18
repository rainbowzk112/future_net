package com.filetool.util;

/**
 * 用于Dijkstra算法的优先队列中
 * @author Administrator
 *
 */
public class Node implements Comparable<Node> {
	Integer vertex;
	Integer weight;

	public Node() {

	}

	public Node(Integer vertex, Integer weight) {
		super();
		this.vertex = vertex;
		this.weight = weight;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	@Override
	public int compareTo(Node o) {
		return weight - o.weight;// 从低到高排序
	}

}
