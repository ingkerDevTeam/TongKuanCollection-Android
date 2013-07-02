package com.ingker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ingker.model.UserModel;
import com.ingker.tongkuancollection.R;

public class SettingActivity extends BaseActivity {
	TextView userInfo;
	UserModel userModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 用户信息
		userInfo = (TextView) findViewById(R.id.username);
		if (UserModel.getUid(this) == null) {
			userInfo.setText("未登录");
		} else {
			userInfo.setText(UserModel.getLocalUserInfo(this).uname);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (UserModel.getUid(this) == null) {
			userInfo.setText("未登录");
		} else {
			userInfo.setText(UserModel.getLocalUserInfo(this).uname);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	// 关于
	public void aboutButtonPressed(View v) {
		Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
		startActivity(intent);
	}

	// 显示本地空间清理并警告
	public void showCleanDiskAlertDialog(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("清除缓存");
		builder.setMessage("确定要清除缓存嘛？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 清理本地空间
				showDialogWithText("缓存清除成功！");

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		// 显示警告栏
		builder.show();
	}

	// 用户登录
	public void loginOrLogout(View v) {

		if (UserModel.getUid(getApplicationContext()) == null) {
			Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
			startActivityForResult(intent, 0);
		} else {
			AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(SettingActivity.this);
			alertDialogbBuilder.setTitle("警告");
			alertDialogbBuilder.setMessage("确定要退出登录吗");
			alertDialogbBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					UserModel.destoryUserInfo(getApplicationContext());
					userInfo.setText("未登录");
				}
			});
			alertDialogbBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			alertDialogbBuilder.create().show();
		}

	}

}