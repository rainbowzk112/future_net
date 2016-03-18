package com.filetool.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EdgeSet {
	private Map<Integer, Weight> edgeSet;

	public EdgeSet() {
		super();
		edgeSet = new HashMap<Integer, Weight>();
	}

	public int getDistance(int vertex2) {
		return edgeSet.get(vertex2).getMinWeight();
	}
	public Weight put(int vertex2,int weight){
		Weight value=edgeSet.get(vertex2);
		value.offer(weight);
		return edgeSet.put(vertex2,value );
	}
	public Set<Entry<Integer, Weight>> entrySet(){
		return edgeSet.entrySet();
	}
}
