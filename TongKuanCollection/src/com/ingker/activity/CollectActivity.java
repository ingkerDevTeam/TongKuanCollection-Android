package com.ingker.activity;

import org.json.JSONObject;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.ingker.api.UserAPI;
import com.ingker.base.BaseAPI;
import com.ingker.base.BaseAPI.BaseAPIDelegate;
import com.ingker.base.BaseListAdapter;
import com.ingker.base.Datalist;
import com.ingker.common.ProjectConfig;
import com.ingker.model.CollectionList;
import com.ingker.model.ProductModel;
import com.ingker.model.UserModel;
import com.ingker.tongkuancollection.R;
import com.squareup.picasso.Picasso;

public class CollectActivity extends BaseActivity implements BaseAPIDelegate {
	private UserAPI userAPI;
	private CollectionList collectionlist;
	private ListView listView;
	private CollectViewAdapter collectViewAdapter;
	private ImageButton refreshButton;

	/* ListView对应的适配器对象,用于填充数据 */
	private class CollectViewAdapter extends BaseListAdapter {
		public CollectViewAdapter(Context context, Datalist data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View cell;
			// 第一次加载

			if (null == convertView) {
				cell = layoutInflater.inflate(R.layout.cell_collect, null);
			} else {
				cell = convertView;
			}

			TextView nameTextView = (TextView) cell.findViewById(R.id.nameTextView);
			TextView priceTextView = (TextView) cell.findViewById(R.id.priceTextView);
			ImageView imageView = (ImageView) cell.findViewById(R.id.imageView);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(CollectActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			});

			imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_view_background));
			// 填充数据

			ProductModel productModel = (ProductModel) datalist.objectAtIndex(position);

			nameTextView.setText(productModel.productName);
			priceTextView.setText("¥" + productModel.price);
			Picasso.with(CollectActivity.this).load(productModel.imageUrl).into(imageView);

			return cell;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_collect);
		collectionlist = new CollectionList(this);
		decideIfLoadData();
		
		findViews();
		setListeners();
	}

	public void decideIfLoadData() {
		final TabHost tabhost = ((TabActivity) getParent()).getTabHost();
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if (tabId.equals("收藏")) {

					// 如果用户没有登录,弹出登录窗口
					if (!UserModel.userIsLogin(getApplicationContext())) {
						Intent intent = new Intent(CollectActivity.this, LoginActivity.class);
						startActivityForResult(intent, 0);
					} else {
						if (collectionlist.count() == 0) {
							getCollectData();
							}
						}
						
				} else {
					// 记录当前的tab
					String storeName = "user_info";
					SharedPreferences sharedPreferences = getSharedPreferences(storeName, 0);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("currentTabTag", tabhost.getCurrentTabTag());
					editor.commit();

				}
			}
		});
	}

	public void getCollectData() {
		userAPI = new UserAPI(CollectActivity.this, CollectActivity.this);
		userAPI.getMyCollection();
		showProgressDialogWithText("加载中...");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		final TabHost tabhost = ((TabActivity) getParent()).getTabHost();

		// 如果登录失败,跳回原来的tab
		if (!UserModel.userIsLogin(getApplicationContext())) {
			String storeName = "user_info";
			SharedPreferences sharedPreferences = getSharedPreferences(storeName, 0);
			System.out.println(sharedPreferences.getString("currentTabTag", "同款精选"));
			tabhost.setCurrentTabByTag(sharedPreferences.getString("currentTabTag", "同款精选"));
		} else {

			// 登录成功后记录当前的tab
			String storeName = "user_info";
			SharedPreferences sharedPreferences = getSharedPreferences(storeName, 0);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("currentTabTag", tabhost.getCurrentTabTag());
			editor.commit();
			
			//刷新列表
			getCollectData();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		super.findViews();
		// 适配器
		collectViewAdapter = new CollectViewAdapter(this, collectionlist);

		// 配置Listview
		listView = (ListView) findViewById(R.id.listView);

		listView.setDividerHeight(1);

		// setAdapter必须在addFooter之后，否则footer不能显示
		listView.setAdapter(collectViewAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CollectActivity.this, ProductDetailActivity.class);
				
				ProductModel productModel = new ProductModel(CollectActivity.this);
				productModel = (ProductModel) collectionlist.objectAtIndex(arg2);
				intent.putExtra("product_name", productModel.productName);
				intent.putExtra("product_id", productModel.productId);
				intent.putExtra("price", productModel.price);
				intent.putExtra("collect_count", productModel.collectionCount);
				startActivity(intent);
			}
		});
		
		refreshButton = (ImageButton)findViewById(R.id.refreshButton);
		refreshButton.setOnTouchListener(this.refreshButtonOnTouchListener);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				collectionlist.removeAllObjects();
				collectViewAdapter.notifyDataSetChanged();

				showProgressDialogWithText("加载中...");
				userAPI.stopRequest();
				userAPI.getMyCollection();
				refreshButton.setEnabled(false);
			}
		});

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	public void BaseAPIDidStartedRequest(BaseAPI api) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BaseAPIDidFinishedRequestWithResponeData(BaseAPI api, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		ProjectConfig.p(jsonObject);
		collectionlist.loadData(jsonObject, api.requestType);
		collectViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void BaseAPIDidFailedRequestWithError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
	}

	@Override
	public void BaseAPIDidReceivedNetworkError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showNetworkErrorDialog();
	}

	@Override
	public void BaseAPIOnFinished(BaseAPI api) {
		
		refreshButton.setEnabled(true);
		hideProgressDialog();
	}
}
