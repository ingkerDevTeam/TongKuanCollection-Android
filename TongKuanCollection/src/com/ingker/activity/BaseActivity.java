package com.ingker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class BaseActivity extends Activity {
	
	ProgressDialog progressDialog;
	OnTouchListener refreshButtonOnTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View button, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN){       
                //更改为按下时的背景图片       
				button.setBackgroundColor(0x1FF07F00);
			}else if(event.getAction() == MotionEvent.ACTION_UP){       
                //改为抬起时的图片       
				button.setBackgroundColor(0x00000000);
			}  
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//隐藏ActionBar
		if (getActionBar() != null) {
			getActionBar().hide();
		}
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
			
	}
	
	

	// 界面绑定
	public void findViews() {

	}

	// 监听设置
	public void setListeners() {

	}

	// 显示网络错误对话框
	public void showNetworkErrorDialog() {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(this);
		alertDialogbBuilder.setTitle("网络连接有问题");
		alertDialogbBuilder.setMessage("请检查您的网络连接");
		alertDialogbBuilder.setPositiveButton("明白了", null);
		alertDialogbBuilder.create().show();
	}
	
	public void showProgressDialogWithText(String text) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle(text);
		progressDialog.show();
	}
	
	public void hideProgressDialog() {
		progressDialog.hide();
	}
	
	public void showLoginErrorDialog() {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(this);
		alertDialogbBuilder.setTitle("登录失败");
		alertDialogbBuilder.setMessage("email或密码错误");
		alertDialogbBuilder.setPositiveButton("明白了", null);
		alertDialogbBuilder.create().show();
	}
	
	public void showBlankErrorDialog() {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(this);
		alertDialogbBuilder.setTitle("email和密码不能为空");
		//alertDialogbBuilder.setMessage("email和密码不能为空");
		alertDialogbBuilder.setPositiveButton("明白了", null);
		alertDialogbBuilder.create().show();
	}
	
	public void showDialogWithText(String text) {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(this);
		alertDialogbBuilder.setTitle(text);
		//alertDialogbBuilder.setMessage("email和密码不能为空");
		alertDialogbBuilder.setPositiveButton("明白了", null);
		alertDialogbBuilder.create().show();
	}

}
