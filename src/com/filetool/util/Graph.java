package com.filetool.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	private Map<Integer, EdgeSet> graph;
	public static final boolean VISITED = true;
	public static final boolean UNVISITED = false;

	public Graph() {
		super();
		graph = new HashMap<Integer, EdgeSet>();
	}

	public Graph(String graphContext) {
		init(graphContext);
	}

	public int getDistance(int vertex1, int vertex2) {
		return vertex1 == vertex2 ? 0 : graph.get(vertex1).getDistance(vertex2);
	}

	public EdgeSet put(int vertex1, EdgeSet edgeSet) {
		return graph.put(vertex1, edgeSet);
	}

	public EdgeSet put(String vertex1, String vertex2, String weight) {
		return put(Integer.valueOf(vertex1), Integer.valueOf(vertex2),
				Integer.valueOf(weight));
	}

	public EdgeSet put(int vertex1, int vertex2, int weight) {
		EdgeSet edgeSet = graph.get(vertex1);
		if (edgeSet == null) {
			edgeSet = new EdgeSet();
			put(vertex1, edgeSet);
		}
		edgeSet.put(vertex2, weight);
		return graph.get(vertex1);
	}

	/**
	 * ����ָ������㡢�յ㣬���뾭���ĵ�����϶�·��
	 * 
	 * @param condition
	 * @return
	 */
	// TODO
	public List<Integer> getMinDistancePath(String condition) {
		String[] conditions = condition.split(",");
		String vertex1 = conditions[0];
		String vertex2 = conditions[1];
		String vertexsPassBy = conditions[2];
		List<Integer> path = null;
		List<Integer> path1=getMinDistance(vertex1, vertex2);
		
		List<Integer> path2=null;
		List<Integer> path3=null;
		return path;
	}

	public String getResult(String condition) {
		List<Integer> path = getMinDistancePath(condition);
		String result = pathToString(path);
		return result;
	}

	public String pathToString(List<Integer> path) {
		StringBuffer buf = new StringBuffer();
		for (Integer item : path) {
			buf.append(String.valueOf(item.intValue()));
			buf.append("|");
		}
		buf.deleteCharAt(buf.length() - 1);// ɾ�����һ��|
		return buf.substring(0);
	}

	public List<Integer> getMinDistance(String vertex1, String vertex2) {
		return getMinDistance(Integer.valueOf(vertex1),
				Integer.valueOf(vertex2));
	}

	/**
	 * use Dijkstra algorithm to calculate minimum distance of two vertex.
	 * 
	 * @param vertex1
	 * @param vertex2
	 * @return
	 */
	public List<Integer> getMinDistance(int vertex1, int vertex2) {
		// ��������ʼ����Ǽ���
		Map<Integer, Boolean> mark = new HashMap<Integer, Boolean>();
		Iterator<Entry<Integer, EdgeSet>> vertexSetIterator = graph.entrySet()
				.iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			mark.put(entry.getKey(), UNVISITED);
		}

		// ����һ��map�����㵽������ľ���
		Map<Integer, Integer> distance = new HashMap<Integer, Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			distance.put(entry.getKey(), Integer.MAX_VALUE);
		}

		distance.put(vertex1, 0);// ��������Ϊ0
		mark.put(vertex1, VISITED);// ������
		// ���ȶ��У��������Ѹ��µ�Ȩ���е���Сֵ
		Queue<Node> pq = new PriorityQueue<Node>();
		pq.offer(new Node(vertex1, 0));
		List<Integer> path = new ArrayList<Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (mark.get(vertex2) == UNVISITED) {
			Node newNode = pq.poll();// ȡ������̵Ķ���
			path.add(newNode.vertex);
			int lastestMarkedVertex = newNode.vertex;
			pq.clear();// ÿ��ѭ�����¼���δ��ǵĵ�ľ���,������ǰ�ľ�����ɸ���
			Iterator<Entry<Integer, Weight>> edgeSetIterator = graph
					.get(newNode.vertex).entrySet().iterator();

			while (edgeSetIterator.hasNext()) {
				Entry<Integer, Weight> graphEntry = edgeSetIterator.next();
				int vertex = graphEntry.getKey();// ��������������������ڶ���
				if (mark.get(vertex) == VISITED) {
					continue;// �����ѷ��ʹ��ĵ�
				}
				int newDistance = getDistance(vertex1, lastestMarkedVertex)
						+ getDistance(lastestMarkedVertex, vertex);// ���¼�����㵽�õ�ľ���
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
	 * ��ʼ��ͼ
	 * 
	 * @param graphContent
	 */
	public void init(String graphContent) {
		String[] lines = graphContent.split("\n");
		for (String line : lines) {
			String[] items = line.split(",");
			String linkID = items[0];
			String sourceID = items[1];
			String DestinationID = items[2];
			String cost = items[3];
			put(sourceID, DestinationID, cost);
		}
	}

	class Node implements Comparable<Node> {
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
}
