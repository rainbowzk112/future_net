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

		// 建立并初始化标记集合
		Map<Integer, Boolean> mark = new HashMap<Integer, Boolean>();
		Iterator<Entry<Integer, EdgeSet>> vertexSetIterator = graph.entrySet()
				.iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			mark.put(entry.getKey(), Graph.UNVISITED);
		}

		// 建立一个map存放起点到各顶点的距离
		Map<Integer, Integer> distance = new HashMap<Integer, Integer>();
		vertexSetIterator = graph.entrySet().iterator();
		while (vertexSetIterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = vertexSetIterator.next();
			distance.put(entry.getKey(), Integer.MAX_VALUE);
		}

		distance.put(vertex1, 0);// 起点距离标记为0
		mark.put(vertex1, Graph.VISITED);// 标记起点
		// 优先队列，用于求已更新的权重中的最小值
		Queue<Node> pq = new PriorityQueue<Node>();
		pq.offer(new Node(vertex1, 0));
		List<Integer> path = new ArrayList<Integer>();

		vertexSetIterator = graph.entrySet().iterator();
		while (mark.get(vertex2) == Graph.UNVISITED) {
			Node newNode = pq.poll();// 取距离最短的顶点
			mark.put(newNode.vertex, Graph.VISITED);
			System.out.println("newNode==null: " + (newNode == null));
			path.add(newNode.vertex);
			int lastestMarkedVertex = newNode.vertex;
			pq.clear();// 每次循环重新计算未标记的点的距离,避免以前的距离造成干扰
			Iterator<Entry<Integer, Weight>> edgeSetIterator = graph
					.get(newNode.vertex).entrySet().iterator();

			while (edgeSetIterator.hasNext()) {
				Entry<Integer, Weight> graphEntry = edgeSetIterator.next();
				int vertex = graphEntry.getKey();// 访问新增顶点的所有相邻顶点
				if (mark.get(vertex) == Graph.VISITED) {
					continue;// 跳过已访问过的点
				}
				int newDistance = graph.getDistance(vertex1,
						lastestMarkedVertex)
						+ graph.getDistance(lastestMarkedVertex, vertex);// 重新计算起点到该点的距离
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
	 * 对子图进行深度优先搜索
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
				dfs(graph,mark,vertex2);//递归
			}
		}

		
	}

	/**
	 * 对图的子图拓扑排序,用队列实现
	 * 
	 * @param vertexPassBy
	 * @return
	 */
	public static Queue<Integer> topoSort(Graph graph) {
		Map<Integer, Integer> mark = new HashMap<Integer, Integer>();
		// 初始化mark集合
		Iterator<Entry<Integer, EdgeSet>> iterator = graph.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<Integer, EdgeSet> entry = iterator.next();
			int vertex = entry.getKey();
			mark.put(vertex, 0);
		}
		// 统计每个结点的前置条件个数
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
