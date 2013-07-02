package com.ingker.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;

import com.ingker.base.Datalist;

public class Productlist extends Datalist {

	public int categoryId;//分类的ID
	public boolean isSearching;//是否为搜索列表
	
	public Productlist(Context context,int categoryId) {
		super(context);
		this.categoryId = categoryId;
		// TODO Auto-generated constructor stub
	}
	
	
	public void loadData(JSONObject data, String requestType) {
		// TODO Auto-generated method stub
		super.loadData(data, requestType);
		
		if (currentPage == 1 && categoryId == 0) {
			saveCache(data, "product_list");
		}
		
		try {
			JSONArray productlist = data.getJSONArray("product_list");
			totalRecord = data.getInt("count");
			
			for (int i = 0; i < productlist.length(); i++) {
				ProductModel productModel = new ProductModel(context);
				productModel.loadData((JSONObject)productlist.get(i), requestType);
				list.add(productModel);
			}
			
						
		} catch (JSONException e) {
			System.out.println("产生异常");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean loadCacheData() {
		// TODO Auto-generated method stub
		super.loadCacheData();
		JSONObject data = this.loadDataStringFromCache("product_list");
		
		if (data != null) {
			loadData(data, "getProductlist");
			return true;
		}
		else {
			return false;
		}
		
	}
	
}
