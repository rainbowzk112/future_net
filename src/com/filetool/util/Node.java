package com.filetool.util;

/**
 * ����Dijkstra�㷨�����ȶ�����
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
		return weight - o.weight;// �ӵ͵�������
	}

}
