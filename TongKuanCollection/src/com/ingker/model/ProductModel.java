package com.ingker.model;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ingker.base.Model;

//商品模型类

public class ProductModel extends Model {

	public String productId;//商品ID
	public String feedId;//动态ID
	public String productName;//商品名称
	public String description;//描述
	public String price;//价格
	public String collectionCount;//收藏总数
	public boolean isCollected;//用户是否收藏
	public String imageUrl;//大图连接
	
	public String buyUrl;//购买链接
	public ArrayList<String> imagelist;//图片列表

	public ProductModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadData(JSONObject data, String requestType) {
		// TODO Auto-generated method stub
		super.loadData(data, requestType);
		
		if (requestType.equals("getMyCollection")) {
			try {
				productId = data.getString("product_id");
				feedId = data.getString("feed_id");
				productName = data.getString("product_name");
				price = data.getString("price");
				imageUrl = data.getString("image_url");
				//System.out.println("图片链接"+imageUrl);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		else {
			productId = data.optString("product_id");
			feedId = data.optString("feed_id");
			productName = data.optString("product_name");
			description = data.optString("description");
			collectionCount = data.optString("collection_count");
			price = data.optString("price");
			isCollected = intToBoolean(data.optInt("is_collected"));
			imageUrl = data.optString("image_url");
			
			
			if (requestType.equals("getProductDetail")) {
				buyUrl = data.optString("buy_url");
				
				JSONArray imageJsonArray= data.optJSONArray("imagelist");
				imagelist = new ArrayList<String>();
				for (int i = 0; i < imageJsonArray.length(); i++) {
					
					imagelist.add(imageJsonArray.optString(i));
				}
				
			}
		}
	}
}
