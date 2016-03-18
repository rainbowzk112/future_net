package com.filetool.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph {
	private Map<Integer, EdgeSet> graph;
	private Set<Integer> vertexPassBy;
	public static final boolean VISITED = true;
	public static final boolean UNVISITED = false;

	public Graph() {
		super();
		graph = new HashMap<Integer, EdgeSet>();
		vertexPassBy = new HashSet<Integer>();
	}

	public Graph(String graphContext, String condition) {
		this();
		init(graphContext, condition);
	}

	/**
	 * @return the vertexPassBy
	 */
	public Set<Integer> getVertexPassBy() {
		return vertexPassBy;
	}

	/**
	 * @param vertexPassBy
	 *            the vertexPassBy to set
	 */
	public void setVertexPassBy(Set<Integer> vertexPassBy) {
		this.vertexPassBy = vertexPassBy;
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
		EdgeSet edgeSet = null;
		System.out.println(vertex1 == 0);
		edgeSet = graph.get(vertex1);// 顶点肯定全部存在
		edgeSet.put(vertex2, weight);
		return edgeSet;
	}

	/**
	 * 根据指定的起点、终点，必须经过的点求出较短路径
	 * 
	 * @param condition
	 * @return
	 */
	public List<Integer> getMinDistancePath(String condition) {
		String[] conditions = condition.split(",");
		String vertex1 = conditions[0];
		String vertex2 = conditions[1];
		System.out.println(" vertex1= " + vertex1 + "  vertex2=" + vertex2);

		List<Integer> path2 = getTopoPath();// 必经路径
		if (isConnected(path2)) {
			String firstIndexPassBy = String.valueOf(path2.get(0));// 必经路径第一个结点
			List<Integer> path1 = getMinDistance(vertex1, firstIndexPassBy);// 左端路径
			String lastIndexPassBy = String
					.valueOf(path2.get(path2.size() - 1));// 必经路径最后一个结点
			List<Integer> path3 = getMinDistance(lastIndexPassBy, vertex2);// 右端路径

			path1.addAll(path2);
			path1.addAll(path3);
			return path1;
		} else {
			return null;
		}
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
		return getMinDistance(this, Integer.valueOf(vertex1),
				Integer.valueOf(vertex2));
	}

	/**
	 * use Dijkstra algorithm to calculate minimum distance of two vertex.
	 * 
	 * @param vertex1
	 * @param vertex2
	 * @return
	 */
	public List<Integer> getMinDistance(Graph graph, int vertex1, int vertex2) {

		return Algorithm.dijkstra(graph, vertex1, vertex2);
	}

	/**
	 * 初始化图
	 * 
	 * @param graphContent
	 * @param condition
	 */
	public void init(String graphContent, String condition) {
		String[] lines = graphContent.split("\n");
		System.out.println("condition= " + condition);
		String[] conditions = condition.split(",");
		System.out.println("conditions[2]=" + conditions[2]);
		String[] keyVertex = conditions[2].split("\\|");//|为特殊字符，必须转义
		System.out.println("keyVertex size= " + keyVertex.length);
		Set<String> set = new HashSet<String>();
		// 找出所有顶点
		for (String line : lines) {
			String[] items = line.split(",");
			String sourceID = items[1];
			set.add(sourceID);
			String DestinationID = items[2];
			set.add(DestinationID);

		}
		// 添加必经结点
		for (String item : keyVertex) {
			System.out.println("item = " + item);
			vertexPassBy.add(Integer.valueOf(item));
		}
		// 初始化图
		for (String item : set) {
			graph.put(Integer.valueOf(item), new EdgeSet());// 值全部初始化为空集，以后若有邻边，则添加进去
		}
		// 建图
		for (String line : lines) {
			String[] items = line.split(",");
			String linkID = items[0];// 暂时无用
			String sourceID = items[1];
			String DestinationID = items[2];
			String cost = items[3];
			put(sourceID, DestinationID, cost);
		}
	}

	/**
	 * 使用深度优先搜索作标记,判断是否可到达,得到可达的点集,用于剪枝
	 * 
	 * @return
	 */
	public Set<Integer> canReach(int startVertex) {
		Map<Integer, Boolean> mark = new HashMap<Integer, Boolean>();
		Algorithm.dfs(this, mark, startVertex);
		Set<Integer> canReach = new HashSet<Integer>();
		Iterator<Entry<Integer, Boolean>> iterator = mark.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Boolean> entry = iterator.next();
			int key = entry.getKey();
			boolean value = entry.getValue();
			if (value == true) {
				canReach.add(key);
			}
		}
		return canReach;
	}

	/**
	 * 判断必经结点间是否连通
	 * 
	 * @return
	 */
	public List<Integer> getTopoPath() {
		Graph subGraph = subGraph(vertexPassBy);
		return new ArrayList<Integer>(Algorithm.topoSort(subGraph));
	}

	public boolean isConnected(List<Integer> topoResult) {
		return topoResult.size() == vertexPassBy.size();// 所有顶点都能按序访问，则是连通的
	}

	/**
	 * 求子图，复制一份出来，原来的不删掉
	 * 
	 * @param vertexPassBy
	 * @return
	 */
	public Graph subGraph(Set<Integer> vertexPassBy) {
		Graph subGraph = new Graph();
		for (Integer item : vertexPassBy) {
			subGraph.put(item, new EdgeSet());
		}
		Iterator<Entry<Integer, EdgeSet>> subGraphIterator = subGraph
				.entrySet().iterator();
		while (subGraphIterator.hasNext()) {
			Entry<Integer, EdgeSet> subGraphEntry = subGraphIterator.next();
			int vertex1 = subGraphEntry.getKey();
			EdgeSet smallEdgeSet = subGraphEntry.getValue();// 新创建的边集
			EdgeSet largeEdgeSet = graph.get(vertex1);// 大图中的边集
			Iterator<Entry<Integer, Weight>> edgeIterator = largeEdgeSet
					.entrySet().iterator();
			while (edgeIterator.hasNext()) {
				Entry<Integer, Weight> edgeEntry = edgeIterator.next();
				int vertex2 = edgeEntry.getKey();
				Weight weight = edgeEntry.getValue();
				if (vertexPassBy.contains(vertex2)) {
					smallEdgeSet.put(vertex2, weight);// 两点间的多重边也加入
					subGraph.put(vertex1, smallEdgeSet);
				}
			}
		}
		return subGraph;
	}

	public Set<Map.Entry<Integer, EdgeSet>> entrySet() {
		return graph.entrySet();
	}

	public EdgeSet get(int vertex1) {
		return graph.get(vertex1);
	}

	/**
	 * 打印图
	 */
	public void print() {
		Iterator<Entry<Integer, EdgeSet>> iterator = graph.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = iterator.next();
			int vertex1 = entry.getKey();
			System.out.println("vertex1= " + vertex1);
			EdgeSet edgeSet = entry.getValue();
			edgeSet.print();
		}
	}

	public Set<Integer> keySet() {
		return graph.keySet();
	}

}
