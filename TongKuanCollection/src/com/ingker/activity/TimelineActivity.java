package com.ingker.activity;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ingker.api.ProductAPI;
import com.ingker.base.BaseAPI;
import com.ingker.base.BaseAPI.BaseAPIDelegate;
import com.ingker.base.BaseListAdapter;
import com.ingker.base.Datalist;
import com.ingker.model.ProductModel;
import com.ingker.model.Productlist;
import com.ingker.model.UserModel;
import com.ingker.tongkuancollection.MainActivity;
import com.ingker.tongkuancollection.R;
import com.ingker.tongkuancollection.R.drawable;
import com.squareup.picasso.Picasso;

public class TimelineActivity extends BaseActivity implements BaseAPIDelegate {

	private ProductAPI productAPI;
	private Productlist productlist;
	private ListView listView;
	private ProgressBar footerView;
	private TimelineViewAdapter timelineViewAdapter;
	private ImageButton refreshButton;

	/* ListView对应的适配器对象,用于填充数据 */
	private class TimelineViewAdapter extends BaseListAdapter {
		public TimelineViewAdapter(Context context, Datalist data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View cell;
			// 第一次加载

			if (null == convertView) {
				cell = layoutInflater.inflate(R.layout.cell_timeline, null);
			} else {
				cell = convertView;
			}

			TextView nameTextView = (TextView) cell.findViewById(R.id.nameTextView);
			TextView priceTextView = (TextView) cell.findViewById(R.id.priceTextView);
			TextView collectCountTextView = (TextView) cell.findViewById(R.id.collectCountTextView);
			ImageView imageView = (ImageView) cell.findViewById(R.id.imageView);
			ImageButton loveButton = (ImageButton) cell.findViewById(R.id.loveButton);
			final ImageView loveImage = (ImageView) cell.findViewById(R.id.loveImage);

			imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_view_background));

			// 填充数据

			final ProductModel productModel = (ProductModel) datalist.objectAtIndex(position);

			nameTextView.setText(productModel.productName);
			priceTextView.setText("¥" + productModel.price);
			collectCountTextView.setText(productModel.collectionCount);
			Picasso.with(TimelineActivity.this).load(productModel.imageUrl).into(imageView);

			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(TimelineActivity.this, ProductDetailActivity.class);
					intent.putExtra("product_name", productModel.productName);
					intent.putExtra("product_id", productModel.productId);
					intent.putExtra("price", productModel.price);
					intent.putExtra("collect_count", productModel.collectionCount);
					startActivity(intent);
				}
			});

			if (productModel.isCollected) {
				loveImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_nav_follow_on));
			} else {
				loveImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_nav_follow));
			}

			loveButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (UserModel.getUid(getApplicationContext()) == null) {
						Intent intent = new Intent(TimelineActivity.this, LoginActivity.class);
						startActivityForResult(intent, 0);
					} else {
						if (productModel.isCollected) {
							loveImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_nav_follow));
							productModel.isCollected = false;
							productAPI.unCollectProduct(productModel.feedId);
						} else {
							loveImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_nav_follow_on));
							productModel.isCollected = true;
							productAPI.collectProduct(productModel.feedId);
						}
					}

				}
			});

			// DisplayMetrics displayMetrics = new DisplayMetrics();
			// getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			//
			// RelativeLayout.LayoutParams linearParams =
			// (RelativeLayout.LayoutParams) imageView.getLayoutParams();
			// linearParams.height = displayMetrics.widthPixels-40;
			// linearParams.width = displayMetrics.widthPixels-40;
			//
			// System.out.println(linearParams.height);
			// linearParams.leftMargin = 20;
			// linearParams.topMargin = 30;
			// imageView.setLayoutParams(linearParams);

			return cell;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data.getExtras() != null) {
			reloadData();
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_timeline);

		Intent intent = getIntent();
		String categoryId = intent.getStringExtra("category_id");
		if (categoryId == null) {
			categoryId = "0";
		}

		productlist = new Productlist(this, Integer.parseInt(categoryId));
		productAPI = new ProductAPI(this, this);
		productAPI.getProductlist(1, productlist.categoryId);

		findViews();
		setListeners();
	}

	@Override
	public void findViews() {
		super.findViews();

		Intent intent = getIntent();
		String categoryName = intent.getStringExtra("category_name");
		if (categoryName == null) {
			categoryName = "同款精选";
		}

		TextView titleTextView = (TextView) findViewById(R.id.navigationbarTitle);
		titleTextView.setText(categoryName);

		refreshButton = (ImageButton) findViewById(R.id.refreshButton);
		refreshButton.setOnTouchListener(this.refreshButtonOnTouchListener);
		refreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reloadData();
			}
		});

		// TODO Auto-generated method stub
		// 适配器
		timelineViewAdapter = new TimelineViewAdapter(this, productlist);

		// 配置Listview
		listView = (ListView) findViewById(R.id.listView);

		listView.setDividerHeight(0);

		// 设置listview的footer
		footerView = new ProgressBar(this);

		listView.addFooterView(footerView);

		// setAdapter必须在addFooter之后，否则footer不能显示
		listView.setAdapter(timelineViewAdapter);

		// 监听是否滚动到底部
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断是否滚动到底部
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						System.out.println("滚动底部");

						if (productAPI.requestState == 0) {
							if (productlist.count() < productlist.totalRecord) {
								productAPI.getProductlist(productlist.currentPage + 1, productlist.categoryId);
							}
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void reloadData() {
		productlist.removeAllObjects();
		timelineViewAdapter.notifyDataSetChanged();
		listView.removeFooterView(footerView);
		listView.addFooterView(footerView);
		productAPI.stopRequest();
		productAPI.getProductlist(1, productlist.categoryId);
		refreshButton.setEnabled(false);
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

		if (api.requestType.equals("getProductlist")) {
			productlist.loadData(jsonObject, api.requestType);

			timelineViewAdapter.notifyDataSetChanged();

			// 若数据加载完成则隐藏footer
			if (productlist.count() == productlist.totalRecord) {
				// System.out.println("加载完成");
				listView.removeFooterView(footerView);
			}
		} else {
			System.err.println("collect");
		}

	}

	@Override
	public void BaseAPIDidFailedRequestWithError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
	}

	@Override
	public void BaseAPIDidReceivedNetworkError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showNetworkErrorDialog();
		listView.removeFooterView(footerView);
		timelineViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void BaseAPIOnFinished(BaseAPI api) {
		// TODO Auto-generated method stub
		refreshButton.setEnabled(true);
	}
}
