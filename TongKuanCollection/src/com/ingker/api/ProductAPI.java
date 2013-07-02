package com.ingker.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;

import com.ingker.base.BaseAPI;
import com.ingker.common.ProjectConfig;
import com.ingker.model.UserModel;

public class ProductAPI extends BaseAPI {
	
	//构造方法
	public ProductAPI(Context context, BaseAPIDelegate delegate) {
		super(context, delegate);
		// TODO Auto-generated constructor stub
	}

	//获取商品列表
	public void getProductlist(int page,int categoryId){
		requestType = "getProductlist";
		requestUrl = ProjectConfig.RequestUrl+"mod=Product&act=getProductlist&limit="+ProjectConfig.TimelineLimit+"&page="+page+"&category_id="+categoryId;
		if (UserModel.getUid(context) != null) {
			requestUrl = requestUrl + "&uid=" + UserModel.getUid(context);
		}
		didStartAsyncRequest();
	}
	
	//获取商品详情
	public void getProductDetail(String productId){
		requestType = "getProductDetail";
		requestUrl = ProjectConfig.RequestUrl+"mod=Product&act=getProductInfo&product_id="+productId;
		if (UserModel.getUid(context) != null) {
			requestUrl = requestUrl + "&uid=" + UserModel.getUid(context);
		}
		didStartAsyncRequest();
	}
	
	//收藏商品
	public void collectProduct(String feedId){
		requestType = "collectProduct";
		requestUrl = ProjectConfig.RequestUrl+"mod=Product&act=collect&feed_id="+feedId+"&uid="+UserModel.getUid(context);
		didStartAsyncRequest();
	}
	
	//取消收藏商品
	public void unCollectProduct(String feedId){
		requestType = "unCollectProduct";
		requestUrl = ProjectConfig.RequestUrl+"mod=Product&act=uncollect&feed_id="+feedId+"&uid="+UserModel.getUid(context);
		didStartAsyncRequest();
	}
	
	//搜索商品
	public void searchProduct(String key,int page){
		requestType = "searchProduct";
		
		String keyString = null;
		try {
			keyString = URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestUrl = ProjectConfig.RequestUrl+"mod=Product&act=searchProduct&limit="+ProjectConfig.TimelineLimit+"&page="+page+"&searchkey="+keyString;
		if (UserModel.getUid(context) != null) {
			requestUrl = requestUrl + "&uid=" + UserModel.getUid(context);
		}
		didStartAsyncRequest();
	}

}
