package com.ingker.base;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ingker.common.ProjectConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;



public class BaseAPI {
	
	public BaseAPI(Context context,BaseAPIDelegate delegate){
		this.context = context;
		this.delegate = delegate;
	}
	
	//请求类接口
	public interface BaseAPIDelegate {
		//请求开始
		public void BaseAPIDidStartedRequest(BaseAPI api);
		//请求成功
		public void BaseAPIDidFinishedRequestWithResponeData(BaseAPI api, JSONObject jsonObject);
		//请求失败
		public void BaseAPIDidFailedRequestWithError(BaseAPI api,String error);
		//服务器或网络错误
		public void BaseAPIDidReceivedNetworkError(BaseAPI api,String error);
		//请求执行的操作完成
		public void BaseAPIOnFinished(BaseAPI api);
	}
	
	public BaseAPIDelegate delegate;//委托
	private static AsyncHttpClient request = new AsyncHttpClient();//请求类
	public String requestUrl;//请求的地址
	public String requestType;//请求类型
	public int requestState;//请求的状态，0,//请求未开始或已经结束  1,//正在请求
	public boolean isDownloadingFile;//是否正在下载
	public Context context;
	
	//返回的数据
	public String code;//请求返回的代码
	public String message;//请求返回的信息
	public String error;//错误信息
	public JSONObject responeData;//请求返回的数据
	
	//开始异步请求
	public void didStartAsyncRequest() {
		System.out.println(requestUrl);
		
		requestState = 1;
		request.setTimeout(ProjectConfig.RequestTimeOut);
		request.get(requestUrl, new AsyncHttpResponseHandler() {  
			//请求成功
            @Override  
            public void onSuccess(String response) {  
        		System.out.println("请求完成");
                System.out.println(response);
            	//判断是否为下载请求
            	if (isDownloadingFile) {
					
				}
            	else {
               		try {
    					JSONObject jsonObject = new JSONObject(response);
    					code = jsonObject.getString("code");
    					message = jsonObject.getString("message");
    					//请求成功
    					if (code.equals("200")) {
    						delegate.BaseAPIDidFinishedRequestWithResponeData(BaseAPI.this,jsonObject);
    					}
    					//请求完成，但请求不成功
    					else if (code.equals("400")) {
    						error = jsonObject.getString("error");
    						delegate.BaseAPIDidFailedRequestWithError(BaseAPI.this,error);
    					}
    					else if (code.equals("401")) {
    						error = jsonObject.getString("error");
    						delegate.BaseAPIDidFailedRequestWithError(BaseAPI.this,error);
    					}
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}
 
            }
            
          //请求失败
            @Override
            public void onFailure(Throwable error, String content) {
        		System.out.println("请求失败");
        		delegate.BaseAPIDidReceivedNetworkError(BaseAPI.this,"network error");
            }
            
            
            //请求开始
            @Override  
            public void onStart() {  
                super.onStart();  
                System.out.println("onStart");
                delegate.BaseAPIDidStartedRequest(BaseAPI.this);
            }  
            
            //请求完成
            @Override  
            public void onFinish() { 
            	requestState = 0;
                super.onFinish();  
                delegate.BaseAPIOnFinished(BaseAPI.this);
                System.out.println("onFinish");  
            }  
              
        });
    }
	
	//请求终止
	public void stopRequest(){
    	requestState = 0;
		request.cancelRequests(context, true);
		
	}
	
}
