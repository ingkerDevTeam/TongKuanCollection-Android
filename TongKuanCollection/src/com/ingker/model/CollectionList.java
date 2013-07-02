package com.ingker.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ingker.base.Datalist;

public class CollectionList extends Datalist {

	public CollectionList(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadData(JSONObject data, String requestType) {
		// TODO Auto-generated method stub
		super.loadData(data, requestType);
		
		try {
			JSONArray jsonArray = data.getJSONArray("collection_list");
			for (int i = 0; i < jsonArray.length(); i++) {
				ProductModel productModel = new ProductModel(context);
				productModel.loadData((JSONObject)jsonArray.get(i), requestType);
				list.add(productModel);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
