package com.ingker.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ingker.api.UserAPI;
import com.ingker.base.BaseAPI;
import com.ingker.base.BaseAPI.BaseAPIDelegate;
import com.ingker.model.UserModel;
import com.ingker.tongkuancollection.R;

public class LoginActivity extends BaseActivity implements BaseAPIDelegate {

	private EditText emialEditText;
	private EditText passworeEditText;
	private Button loginButton;
	private Button registerbButton;
	private UserModel userModel;
	private UserAPI userAPI;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setResult(0, getIntent());
		
		setContentView(R.layout.activity_login);
		findViews();
		setListeners();
	}

	@Override
	public void findViews() {
		emialEditText = (EditText) findViewById(R.id.email);
		passworeEditText = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login_button);
		registerbButton = (Button) findViewById(R.id.reg_button);
	}

	@Override
	public void setListeners() {
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userModel = new UserModel(LoginActivity.this);
				userModel.email = emialEditText.getText().toString();
				userModel.password = passworeEditText.getText().toString();

				if (userModel.email.length() == 0 || userModel.password.length() == 0) {
					showBlankErrorDialog();
				} else {
					userAPI = new UserAPI(LoginActivity.this, LoginActivity.this);
					userAPI.login(userModel.email, userModel.password);
					showProgressDialogWithText("登录中...");
				}
			}
		});

		registerbButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void BaseAPIDidStartedRequest(BaseAPI api) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BaseAPIDidFinishedRequestWithResponeData(BaseAPI api, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		System.out.println(jsonObject);
		try {
			userModel.uid = jsonObject.getString("uid");
			userModel.uname = jsonObject.getString("uname");
			UserModel.saveUserInfo(userModel, LoginActivity.this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getIntent().putExtra("state", "loginSuccess");
		
		finish();
	}

	@Override
	public void BaseAPIDidFailedRequestWithError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showDialogWithText(error);
	}

	@Override
	public void BaseAPIDidReceivedNetworkError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showNetworkErrorDialog();
	}

	@Override
	public void BaseAPIOnFinished(BaseAPI api) {
		// TODO Auto-generated method stub
		hideProgressDialog();
	}
}
