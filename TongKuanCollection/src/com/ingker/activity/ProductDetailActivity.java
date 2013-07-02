package com.ingker.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.sharesdk.BaiduShareException;
import com.baidu.sharesdk.BaiduSocialShare;
import com.baidu.sharesdk.ShareContent;
import com.baidu.sharesdk.ShareListener;
import com.baidu.sharesdk.SocialShareLogger;
import com.baidu.sharesdk.Utility;
import com.baidu.sharesdk.ui.BaiduSocialShareUserInterface;
import com.ingker.api.ProductAPI;
import com.ingker.base.BaseAPI;
import com.ingker.base.BaseAPI.BaseAPIDelegate;
import com.ingker.model.ProductModel;
import com.ingker.share.BaiduSocialShareConfig;
import com.ingker.tongkuancollection.R;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends BaseActivity implements BaseAPIDelegate {

	ProductAPI productAPI;
	ProductModel productModel;

	// View
	ImageView imageView1;
	ImageView imageView2;
	TextView priceTextView;
	TextView collectCountTextView;
	TextView descriptionTextView;
	Button buyButton;
	ImageView share;
	// share
	private BaiduSocialShare socialShare;
	private BaiduSocialShareUserInterface socialShareUi;
	private final static String appKey = BaiduSocialShareConfig.mbApiKey;
	private ShareContent pageContent;
	final Handler handler = new Handler(Looper.getMainLooper());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetail);

		productAPI = new ProductAPI(this, this);
		productModel = new ProductModel(this);

		Intent intent = getIntent();
		productModel.productId = intent.getStringExtra("product_id");
		productModel.productName = intent.getStringExtra("product_name");
		productModel.price = intent.getStringExtra("price");
		productModel.collectionCount = intent.getStringExtra("collect_count");

		productAPI.getProductDetail(productModel.productId);

		findViews();
		setListeners();
		//初始化分享实例
		initShare();

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		super.findViews();
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);

		TextView titleTextView = (TextView) findViewById(R.id.navigationbarTitle);
		titleTextView.setText(productModel.productName);

		priceTextView = (TextView) findViewById(R.id.priceTextView);
		priceTextView.setText("￥"+productModel.price);

		collectCountTextView = (TextView) findViewById(R.id.collectCountTextView);
		collectCountTextView.setText(productModel.collectionCount);

		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

		buyButton = (Button) findViewById(R.id.buyButton);
		buyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(productModel.buyUrl));
				startActivity(intent);
			}
		});

		share = (ImageView) findViewById(R.id.share);
		share.setOnClickListener(new ShareOnClickListener());
		share.setVisibility(View.INVISIBLE);
	}

	public class ShareOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			page_share_box_style(null);

		}

	};

	// 初始化分享实例
	private void initShare() {
		// TODO Auto-generated method stub
		// 实例化baidu社会化分享，传入appkey
		socialShare = BaiduSocialShare.getInstance(this, appKey); // BaiduSocialShare.getInstance(this,
																	// appKey);
		// 设置支持腾讯微博单点登录的appid
		socialShare.supportQQSso(BaiduSocialShareConfig.QQ_SSO_APP_KEY);
		// 设置支持新浪微博单点登录的appid
		socialShare.supportWeiBoSso(BaiduSocialShareConfig.SINA_SSO_APP_KEY);
		// 获取社会化分享UI的实例对象 当自定义UI时使用
		socialShareUi = socialShare.getSocialShareUserInterfaceInstance();
		SocialShareLogger.on();
		// UI接口 分享网页
		pageContent = new ShareContent();
		//pageContent.setContent(productModel.productName.toString());
		// pageContent.setTitle("百度开发中心");
		//pageContent.setUrl(productModel.buyUrl.toString());
		/*
		 * pageContent .setImageUrl(
		 * "http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"
		 * );
		 */
	}

	
	//分享网页   使用系统原生风格的样式菜单
		public void page_share_box_style(View v) {
			socialShareUi.showShareMenu(this, pageContent, Utility.SHARE_BOX_STYLE,
					new ShareListener() {
						@Override
						public void onAuthComplete(Bundle values) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onApiComplete(String responses) {
						    final String msg = responses;
	                        // TODO Auto-generated method stub
	                        handler.post(new Runnable() {
	                            @Override
	                            public void run() {
	                                Utility.showAlert(ProductDetailActivity.this, "分享成功");
	                            	
	                            }
	                        });
						}

						@Override
						public void onError(BaiduShareException e) {
							// TODO Auto-generated method stub

						}

					});
		}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		super.setListeners();

	}

	@Override
	public void BaseAPIDidStartedRequest(BaseAPI api) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BaseAPIDidFinishedRequestWithResponeData(BaseAPI api, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		productModel.loadData(jsonObject.optJSONObject("product"), api.requestType);
		
		//分享的内容
		pageContent.setContent(productModel.productName.toString());
		pageContent.setUrl(productModel.buyUrl.toString());
		//分享的按钮显示
		share.setVisibility(View.VISIBLE);
		
		descriptionTextView.setText(productModel.description);
		priceTextView.setText("￥"+productModel.price);
		collectCountTextView.setText(productModel.collectionCount);

		for (int i = 0; i < productModel.imagelist.size(); i++) {
			String imageString = productModel.imagelist.get(i);
			System.out.println(imageString);

			ImageView imageView;

			if (i == 0) {
				imageView = imageView1;
			} else {
				imageView = imageView2;
			}

			Picasso.with(ProductDetailActivity.this).load(imageString).into(imageView);

		}

	}

	@Override
	public void BaseAPIDidFailedRequestWithError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showDialogWithText("该商品可能已被删除！");
	}

	@Override
	public void BaseAPIDidReceivedNetworkError(BaseAPI api, String error) {
		// TODO Auto-generated method stub
		showNetworkErrorDialog();
	}

	@Override
	public void BaseAPIOnFinished(BaseAPI api) {
		// TODO Auto-generated method stub

	}

}
