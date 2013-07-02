package com.ingker.model;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.ingker.base.Model;

public class UserModel extends Model {
	
	public UserModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public String uid;//用户ID
	public String email;//邮箱
	public String password;//密码
	public String uname;//用户名
	

	
	@Override
	public void loadData(JSONObject data,String requestType){
		super.loadData(data, requestType);
		
	}
	
	//判断用户是否登陆
	public static boolean userIsLogin(Context context) {
		if (UserModel.getUid(context) != null) {
			return true;
		}
		else 
			return false;
	}
	
	//获取用户ID
	public static String getUid(Context context) {
		String storeName = "user_info";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		String uid = sharedPreferences.getString("uid",null);
		return uid;
	}
	
	
	//保存用户信息
	public static void saveUserInfo(UserModel user,Context context) {
		String storeName = "user_info";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("uname", user.uname);
		editor.putString("uid", user.uid);
		editor.putString("email", user.email);
		editor.commit();
	}
	
	//获取本地用户信息
	public static UserModel getLocalUserInfo(Context context) {
		UserModel userModel = new UserModel(context);
		String storeName = "user_info";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		userModel.email = sharedPreferences.getString("email", "");
		userModel.uid = sharedPreferences.getString("uid", "0");
		userModel.uname = sharedPreferences.getString("uname", "");
		return userModel;
	}
	
	//销毁用户信息
	public static void destoryUserInfo(Context context) {
		String storeName = "user_info";
		SharedPreferences sharedPreferences = context.getSharedPreferences(storeName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove("uname");
		editor.remove("uid");
		editor.remove("email");
		editor.commit();
	}
	
}
