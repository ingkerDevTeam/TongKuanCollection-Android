package com.ingker.common;
/*项目参数配置类，维护参数设置*/

public class ProjectConfig {
	
	/****数据请求*****/
	//请求地址
	public static String RequestUrl = "http://tongkuan.yupage.com/index.php?app=api&";
	//时间轴每页数据
	public static int TimelineLimit = 3;
	//请求超时时间
	public static int RequestTimeOut = 5000;
	
	//用于输出
	public static void p(Object object) {
		System.out.println(object);
	}
	
}
