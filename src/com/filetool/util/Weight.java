package com.filetool.util;

import java.util.PriorityQueue;
import java.util.Queue;
/**
 * 储存两个顶点间的多个权重
 * @author Administrator
 *
 */
public class Weight {
	private Queue<Integer> weights;

	public Weight() {
		weights = new PriorityQueue<Integer>();
	}

	public int getMinWeight() {
		return weights.peek().intValue();
	}
	public boolean offer(int weight){
		return weights.offer(Integer.valueOf(weight));
	}
	public int size(){
		return weights.size();
	}
}
