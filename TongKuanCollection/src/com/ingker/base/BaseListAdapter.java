package com.ingker.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//列表适配器基类

public class BaseListAdapter extends BaseAdapter {
	
	// 上下文对象
	public Context context;

	// 数据集合
	public Datalist datalist;
	
	// 布局对象LayoutInflater
	public LayoutInflater layoutInflater;
	
	public BaseListAdapter(Context context,Datalist  data) {
		this.context = context;
		this.datalist = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.count();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return datalist.objectAtIndex(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
