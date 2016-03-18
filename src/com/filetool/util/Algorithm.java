package com.filetool.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.Set;

public class Algorithm {
	public Algorithm() {
		super();
	}

	public static List<Integer> dijkstra(Graph graph,int vertex1, int vertex2) {

		// ��������ʼ����Ǽ���
		Map<Integer, Boolean> mark = new HashMap<Integer, Boolean>();
		Iterator<Entry<Integer, EdgeSet>> vertexSetIterator = graph.entrySet()
				.iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			mark.put(entry.getKey(), Graph.UNVISITED);
		}

		// ����һ��map�����㵽������ľ���
		Map<Integer, Integer> distance = new HashMap<Integer, Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			distance.put(entry.getKey(), Integer.MAX_VALUE);
		}

		distance.put(vertex1, 0);// ��������Ϊ0
		mark.put(vertex1, Graph.VISITED);// ������
		// ���ȶ��У��������Ѹ��µ�Ȩ���е���Сֵ
		Queue<Node> pq = new PriorityQueue<Node>();
		pq.offer(new Node(vertex1, 0));
		List<Integer> path = new ArrayList<Integer>();

		vertexSetIterator = graph.entrySet().iterator();
		while (mark.get(vertex2) == Graph.UNVISITED) {
			Node newNode = pq.poll();// ȡ������̵Ķ���
			mark.put(newNode.vertex, Graph.VISITED);
			System.out.println("newNode==null: " + (newNode == null));
			path.add(newNode.vertex);
			int lastestMarkedVertex = newNode.vertex;
			pq.clear();// ÿ��ѭ�����¼���δ��ǵĵ�ľ���,������ǰ�ľ�����ɸ���
			Iterator<Entry<Integer, Weight>> edgeSetIterator = graph
					.get(newNode.vertex).entrySet().iterator();

			while (edgeSetIterator.hasNext()) {
				Entry<Integer, Weight> graphEntry = edgeSetIterator.next();
				int vertex = graphEntry.getKey();// ��������������������ڶ���
				if (mark.get(vertex) == Graph.VISITED) {
					continue;// �����ѷ��ʹ��ĵ�
				}
				int newDistance = graph.getDistance(vertex1,
						lastestMarkedVertex)
						+ graph.getDistance(lastestMarkedVertex, vertex);// ���¼�����㵽�õ�ľ���
				// ����С��ԭ�о���ĵ���������
				if (newDistance < distance.get(vertex)) {
					distance.put(vertex, newDistance);// ���뱻����
					pq.offer(new Node(vertex, newDistance));// �����˾���ĵ����
				}
			}

		}
		return path;
	}

	/**
	 * ����ͼ���������������
	 * 
	 * @return
	 */
	public static void dfs(Graph graph,Map<Integer,Boolean>mark,int vertex1) {
		mark.put(vertex1, Graph.VISITED);
		Iterator<Entry<Integer, Weight>> iterator=graph.get(vertex1).entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Integer, Weight> entry=iterator.next();
			int vertex2=entry.getKey();
			if(mark.get(vertex2)==Graph.UNVISITED){
				dfs(graph,mark,vertex2);//�ݹ�
			}
		}

		
	}

	/**
	 * ��ͼ����ͼ��������,�ö���ʵ��
	 * 
	 * @param vertexPassBy
	 * @return
	 */
	public static Queue<Integer> topoSort(Graph graph) {
		Map<Integer, Integer> mark = new HashMap<Integer, Integer>();
		// ��ʼ��mark����
		Iterator<Entry<Integer, EdgeSet>> iterator = graph.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = iterator.next();
			int vertex = entry.getKey();
			mark.put(vertex, 0);
		}
		// ͳ��ÿ������ǰ����������
		iterator = graph.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = iterator.next();
			EdgeSet neighbour = entry.getValue();
			Iterator<Entry<Integer, Weight>> edgeIterator = neighbour
					.entrySet().iterator();
			while (edgeIterator.hasNext()) {
				Entry<Integer, Weight> edgeEntry = edgeIterator.next();
				int vertex = edgeEntry.getKey();
				int oldInDegree = mark.get(vertex);
				mark.put(vertex, oldInDegree + 1);
			}
		}
		Queue<Integer> topoQueue = new ArrayDeque<Integer>(graph.keySet().size());
		iterator = graph.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = iterator.next();
			int vertex = entry.getKey();
			if (mark.get(vertex) == 0) {
				topoQueue.offer(vertex);
			}
		}
		while (!topoQueue.isEmpty()) {
			int vertex1 = topoQueue.poll();
			Iterator<Entry<Integer, Weight>> edgeIterator = graph.get(vertex1)
					.entrySet().iterator();
			while (edgeIterator.hasNext()) {
				Entry<Integer, Weight> edgeEntry = edgeIterator.next();
				int vertex2 = edgeEntry.getKey();
				int oldInDegree = mark.get(vertex2);
				mark.put(vertex2, oldInDegree - 1);
				if (mark.get(oldInDegree) == 0) {
					topoQueue.offer(vertex2);
				}
			}
		}

		return topoQueue;

	}
}
