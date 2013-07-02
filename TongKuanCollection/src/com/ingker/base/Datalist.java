package com.ingker.base;

import java.util.ArrayList;

import org.json.JSONObject;

import android.R.bool;
import android.content.Context;


public class Datalist extends Model {



	public int currentPage;//当前页
	public int totalRecord;//数据库中所有记录总数
	public ArrayList<Model> list = new ArrayList<Model>();//数据列表
	public boolean isReloading;//是否为重新加载数据
	
	public Datalist(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//返回list数据的个数
	public int count() {
		return list.size();
	}
	
	
	
	//返回对象
	public Model objectAtIndex(int index) {
		return list.get(index);
	}
	
	//移除所有对象
	public void removeAllObjects() {
		list.clear();
	}
	
	//移除某个对象
	public void removeObjectAtIndex(int index){
		list.remove(index);
	}
	
	@Override
	public void loadData(JSONObject data, String requestType) {
		// TODO Auto-generated method stub
		super.loadData(data, requestType);
		//如果是刷新数据则清空数据
		if (isReloading == true) {
			removeAllObjects();
			currentPage = 1;
			isReloading = false;
		}
		
		//如果列表中还没有元素则当前页为第一页,否则页码+1
		if (count() == 0) {
			currentPage = 1;
		}
		else {
			currentPage++;
		}
		
	}
	
}
