package com.ingker.fragment;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
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
import com.ingker.tongkuancollection.R;
import com.squareup.picasso.Picasso;


public class TimelineFragment extends BaseFragment implements BaseAPIDelegate {
	
	private ProductAPI productAPI ;
	private Productlist productlist;
	private ListView listView;
	private ProgressBar footerView;
	private TimelineViewAdapter timelineViewAdapter;
	private MenuItem refreshItem;
	
	/* ListView��Ӧ������������,���������� */
	private class TimelineViewAdapter extends BaseListAdapter {
		public TimelineViewAdapter(Context context, Datalist data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View cell;
			// ��һ�μ���
			
			System.out.println();
			
			if (null == convertView) {
				cell = layoutInflater.inflate(R.layout.cell_timeline, null);
			} else {
				cell = convertView;
			}
			
			TextView nameTextView = (TextView) cell.findViewById(R.id.nameTextView);
			TextView priceTextView = (TextView) cell.findViewById(R.id.priceTextView);
			TextView collectCountTextView = (TextView) cell.findViewById(R.id.collectCountTextView);
			ImageView imageView = (ImageView)cell.findViewById(R.id.imageView);
			
			imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_view_background));
			// ������
			
			ProductModel productModel = (ProductModel) datalist.objectAtIndex(position);
			
			nameTextView.setText(productModel.productName);
			priceTextView.setText("��"+productModel.price);
			collectCountTextView.setText(productModel.collectionCount);
			Picasso.with(getActivity()).load(productModel.imageUrl).into(imageView);
			
			return cell;
		}
	}
	
	
	 @Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	                             Bundle savedInstanceState) { 
		 super.onCreateView(inflater, container, savedInstanceState);
		 // Inflate the layout for this fragment 
		 FrameLayout view1 = (FrameLayout) inflater.inflate(R.layout.fragment_content, null);
		 view = view1.findViewById(R.id.timeline);
		 view1.removeView(view);
	     return view;  
	 }
	 
	 @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	 
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		productlist = new Productlist(getActivity(),0);
		productAPI = new ProductAPI(getActivity(),this);
		productAPI.getProductlist(1, 0);

		
		//���ø÷�������ʹActionbar���item
		setHasOptionsMenu(true);
	}
	 
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		//����ˢ�°�ť
		refreshItem = menu.add(0, 1, 0, "refresh");
		refreshItem.setIcon(R.drawable.navigation_refresh);
		refreshItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		refreshItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				productlist.removeAllObjects();
				timelineViewAdapter.notifyDataSetChanged();
				listView.removeFooterView(footerView);
				listView.addFooterView(footerView);
				productAPI.stopRequest();
				productAPI.getProductlist(1, 0);
				refreshItem.setEnabled(false);

				return false;
			}
		});
	}
	 
	 @Override
	public void findViews() {
		// TODO Auto-generated method stub
		super.findViews();
		
		
		//������
		timelineViewAdapter = new TimelineViewAdapter(getActivity(), productlist);
		
		//����Listview
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setDividerHeight(0);

		//����listview��footer
		footerView = new ProgressBar(getActivity());
		listView.addFooterView(footerView);

		//setAdapter������addFooter֮�󣬷���footer������ʾ
		listView.setAdapter(timelineViewAdapter);

		//�����Ƿ�������ײ�
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				 if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				      //�ж��Ƿ�������ײ�
				      if (view.getLastVisiblePosition() == view.getCount() - 1) {
				    	  System.out.println("�����ײ�"); 
				    	  
				    	  if (productAPI.requestState == 0) {
							if (productlist.count() < productlist.totalRecord) {
								productAPI.getProductlist(productlist.currentPage+1, productlist.categoryId);
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
	public void BaseAPIDidFinishedRequestWithResponeData(BaseAPI api,
			JSONObject jsonObject) {
		// TODO Auto-generated method stub
		productlist.loadData(jsonObject, api.requestType);
		timelineViewAdapter.notifyDataSetChanged();

		//����ݼ������������footer
		if (productlist.count() == productlist.totalRecord) {
			//System.out.println("�������");
			listView.removeFooterView(footerView);
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
	}

	@Override
	public void BaseAPIOnFinished(BaseAPI api) {
		// TODO Auto-generated method stub
		refreshItem.setEnabled(true);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getActivity().setTitle("同款精选");
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("destroy");
	}
}
