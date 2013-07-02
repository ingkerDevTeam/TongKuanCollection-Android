package com.ingker.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.ingker.api.UserAPI;
import com.ingker.base.BaseAPI;
import com.ingker.base.BaseAPI.BaseAPIDelegate;
import com.ingker.common.ProjectConfig;
import com.ingker.model.UserModel;
import com.ingker.tongkuancollection.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements BaseAPIDelegate {

	private UserModel userModel;
	private UserAPI userAPI;
	private EditText unameEditText;
	private EditText emailEditText;
	private EditText passworeEditText;
	private EditText cpassworeEditText;
	private Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		
		findViews();
		setListeners();
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		super.findViews();
		unameEditText = (EditText) findViewById(R.id.uname);
		emailEditText = (EditText) findViewById(R.id.email);
		passworeEditText = (EditText) findViewById(R.id.password);
		cpassworeEditText = (EditText) findViewById(R.id.cpassword);
		registerButton = (Button) findViewById(R.id.reg_button);
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		super.setListeners();
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userModel = new UserModel(RegisterActivity.this);
				userModel.uname = unameEditText.getText().toString();
				userModel.email = emailEditText.getText().toString();
				userModel.password = passworeEditText.getText().toString();

				if (emailEditText.getText().length() == 0 || unameEditText.getText().length() == 0 || passworeEditText.getText().length() == 0
						|| cpassworeEditText.getText().length() == 0) {
					showBlankErrorDialog();
				} else if (!passworeEditText.getText().toString().equals(cpassworeEditText.getText().toString())) {
					showDialogWithText("两次输入的密码不一致");
				} else {
					userAPI = new UserAPI(RegisterActivity.this, RegisterActivity.this);
					userAPI.register(userModel.uname, userModel.email, userModel.password);
					showProgressDialogWithText("注册中...");
				}
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
		ProjectConfig.p(jsonObject);
		try {
			userModel.uid = jsonObject.getString("uid");
			userModel.uname = jsonObject.getString("uname");
			UserModel.saveUserInfo(userModel, RegisterActivity.this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}

	@Override
	public void BaseAPIDidFailedRequestWithError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		ProjectConfig.p(error);
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
