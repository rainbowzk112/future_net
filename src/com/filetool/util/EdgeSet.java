package com.filetool.util;

import java.util.HashMap;
import java.util.Map;

public class EdgeSet {
	private Map<Integer, Integer> edgeSet;

	public EdgeSet() {
		super();
		edgeSet = new HashMap<Integer, Integer>();
	}

	public int getDistance(int vertex2) {
		return edgeSet.get(vertex2).intValue();
	}
	public int put(int vertex2,int weight){
		return edgeSet.put(vertex2, weight);
	}
}
