package com.ingker.tongkuancollection;

import com.ingker.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class AppStart extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appstart);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
		getActionBar().hide();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent;
				// if (UserModel.getToken(AppStart.this) != null) {
				intent = new Intent(AppStart.this, MainActivity.class);
				// }
				// else {
				// intent = new Intent(AppStart.this,WelcomePager.class);
				// }

				// Intent intent = new
				// Intent(AppStart.this,LoginActivity.class);

				startActivity(intent);
				AppStart.this.finish();
			}
		}, 2000);
	}

}
