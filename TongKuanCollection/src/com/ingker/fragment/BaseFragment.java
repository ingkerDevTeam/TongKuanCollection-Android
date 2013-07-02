package com.ingker.fragment;
import com.ingker.tongkuancollection.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	
	public LayoutInflater inflater;
	public ViewGroup container;
	public View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	 @Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	                             Bundle savedInstanceState) {  
		 // Inflate the layout for this fragment 
		 this.inflater = inflater;
		 this.container = container;
	     return null;  
	 }
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		findViews();
		setListeners();
	}
	
	//界面绑定
	public void findViews(){
		
	}
	
	//监听设置
	public void setListeners(){
		
	}
	
	//显示网络错误对话框
	public void showNetworkErrorDialog() {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(getActivity());
		alertDialogbBuilder.setTitle("网络连接有问题");
		alertDialogbBuilder.setMessage("请检查您的网络连接");
		alertDialogbBuilder.setPositiveButton("明白了", null);
		alertDialogbBuilder.create().show();
	}

}
