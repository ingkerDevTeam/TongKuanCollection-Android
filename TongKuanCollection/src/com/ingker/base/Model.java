package com.ingker.base;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

/*模型类的基类*/

public class Model {
	
	public Context context;
	
	public Model(Context context) {
		this.context = context;
	}
	
	
	//通过请求加载数据
	public void loadData(JSONObject data,String requestType) {
		
	}
	
	//整形转Boolean
	public boolean intToBoolean(int number) {
		if (number != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//缓存数据
	public void saveCache(JSONObject data,String cacheId){
		String storeName = "cache_data";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(cacheId, data.toString());
		editor.commit();
	}
	
	//通过缓存加载数据
	public JSONObject loadDataStringFromCache(String cacheId){
		String storeName = "cache_data";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		String dataString = sharedPreferences.getString(cacheId, null);
		
		JSONObject data;
		try {
			data = new JSONObject(dataString);
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//加载缓存里面的数据
	public boolean loadCacheData() {
		return true;
	}
}
