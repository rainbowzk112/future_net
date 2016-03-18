package com.filetool.util;

import java.util.HashMap;
import java.util.Iterator;
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
	public Weight put(int key, Weight value){
		return edgeSet.put(key, value);
	}
	public Weight put(int vertex2, int weight) {
		Weight value = null;
		if (!edgeSet.containsKey(vertex2)) {
			value = new Weight();
		} else {
			value = edgeSet.get(vertex2);
		}
		//System.out.println("value: " + (value == null));
		
		value.offer(weight);
		return edgeSet.put(vertex2, value);
	}

	public Set<Entry<Integer, Weight>> entrySet() {
		return edgeSet.entrySet();
	}
	public boolean containsKey(int vertex2){
		return edgeSet.containsKey(vertex2);
	}
	
	public void print(){
		Iterator<Entry<Integer, Weight>> iterator=edgeSet.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Integer, Weight> entry=iterator.next();
			int key=entry.getKey();
			int value=entry.getValue().getMinWeight();
			int size=entry.getValue().size();
			System.out.println(" vertex2="+key+" minvalue="+value+" valuesize="+size);
		}
	}
}
