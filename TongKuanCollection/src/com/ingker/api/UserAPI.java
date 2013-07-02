package com.ingker.api;

import android.content.Context;
import com.ingker.base.BaseAPI;
import com.ingker.common.ProjectConfig;
import com.ingker.model.UserModel;

public class UserAPI extends BaseAPI {

	public UserAPI(Context context, BaseAPIDelegate delegate) {
		super(context, delegate);
		// TODO Auto-generated constructor stub
	}

	//用户登陆
	public void login(String email,String password) {
		requestType = "login";
		requestUrl = ProjectConfig.RequestUrl+"mod=User&act=login&email="+email+"&password="+password;
		didStartAsyncRequest();
	}
	
	//用户注册
	public void register(String uname,String email,String password) {
		requestType = "register";
		requestUrl = ProjectConfig.RequestUrl+"mod=User&act=register&uname="+uname+"&email="+email+"&password="+password;
		didStartAsyncRequest();
	}
	
	//根据UID获取用户信息
	public void getUserInfo(String uid) {
		requestType = "getUserInfo";
		requestUrl = ProjectConfig.RequestUrl+"mod=User&act=getUserInfo&uid="+uid;
		didStartAsyncRequest();
	}
	
	//获取用户收藏
	public void getMyCollection() {
		requestType = "getMyCollection";
		requestUrl = ProjectConfig.RequestUrl+"mod=User&act=getMyCollection&uid="+UserModel.getUid(context);
		didStartAsyncRequest();
	}

}
