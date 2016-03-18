package com.filetool.util;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	private Map<Integer, EdgeSet> vertexSet;

	public Graph() {
		super();
		vertexSet = new HashMap<Integer, EdgeSet>();
	}

	public int getDistance(int vertex1, int vertex2) {
		return vertexSet.get(vertex1).getDistance(vertex2);
	}
	public EdgeSet put(int vertex1,EdgeSet edgeSet){
		return vertexSet.put(vertex1, edgeSet);
	}
}
