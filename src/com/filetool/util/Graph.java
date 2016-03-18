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
	 * 根据指定的起点、终点，必须经过的点求出较短路径
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
		buf.deleteCharAt(buf.length() - 1);// 删除最后一个|
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
		// 建立并初始化标记集合
		Map<Integer, Boolean> mark = new HashMap<Integer, Boolean>();
		Iterator<Entry<Integer, EdgeSet>> vertexSetIterator = graph.entrySet()
				.iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			mark.put(entry.getKey(), UNVISITED);
		}

		// 建立一个map存放起点到各顶点的距离
		Map<Integer, Integer> distance = new HashMap<Integer, Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			distance.put(entry.getKey(), Integer.MAX_VALUE);
		}

		distance.put(vertex1, 0);// 起点距离标记为0
		mark.put(vertex1, VISITED);// 标记起点
		// 优先队列，用于求已更新的权重中的最小值
		Queue<Node> pq = new PriorityQueue<Node>();
		pq.offer(new Node(vertex1, 0));
		List<Integer> path = new ArrayList<Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (mark.get(vertex2) == UNVISITED) {
			Node newNode = pq.poll();// 取距离最短的顶点
			path.add(newNode.vertex);
			int lastestMarkedVertex = newNode.vertex;
			pq.clear();// 每次循环重新计算未标记的点的距离,避免以前的距离造成干扰
			Iterator<Entry<Integer, Weight>> edgeSetIterator = graph
					.get(newNode.vertex).entrySet().iterator();

			while (edgeSetIterator.hasNext()) {
				Entry<Integer, Weight> graphEntry = edgeSetIterator.next();
				int vertex = graphEntry.getKey();// 访问新增顶点的所有相邻顶点
				if (mark.get(vertex) == VISITED) {
					continue;// 跳过已访问过的点
				}
				int newDistance = getDistance(vertex1, lastestMarkedVertex)
						+ getDistance(lastestMarkedVertex, vertex);// 重新计算起点到该点的距离
				// 距离小于原有距离的点放入队列中
				if (newDistance < distance.get(vertex)) {
					distance.put(vertex, newDistance);// 距离被更新
					pq.offer(new Node(vertex, newDistance));// 更新了距离的点入队
				}
			}

		}
		return path;
	}

	/**
	 * 初始化图
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
			return weight - o.weight;// 从低到高排序
		}

	}
}
