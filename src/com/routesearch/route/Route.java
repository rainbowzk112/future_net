/**
 * 实现代码文件
 * 
 * @author XXX
 * @since 2016-3-4
 * @version V1.0
 */
package com.routesearch.route;

import com.filetool.util.Graph;

public final class Route {
	/**
	 * 你需要完成功能的入口
	 * 
	 * @author XXX
	 * @since 2016-3-4
	 * @version V1
	 */
	public static String searchRoute(String graphContent, String condition) {
		Graph graph = new Graph(graphContent,condition);
		graph.print();
		String result=graph.getResult(condition);
		System.out.println("result: "+result);
		return result;
	}

}