package com.ingker.activity;
import com.ingker.base.BaseListAdapter;
import com.ingker.base.Datalist;
import com.ingker.model.CategoryModel;
import com.ingker.model.ProductModel;
import com.ingker.tongkuancollection.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryActivity extends BaseActivity{
	
	private Datalist categoryList;
	private ListView listView;
	
	/* ListView对应的适配器对象,用于填充数据 */
	private class CategoryViewAdapter extends BaseListAdapter {
		public CategoryViewAdapter(Context context, Datalist data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View cell;
			// 第一次加载

			if (null == convertView) {
				cell = layoutInflater.inflate(R.layout.cell_category, null);
			} else {
				cell = convertView;
			}
			


			TextView nameTextView = (TextView) cell.findViewById(R.id.textView);

			final CategoryModel categoryModel = (CategoryModel) datalist.objectAtIndex(position);

			nameTextView.setText(categoryModel.name);
			
			return cell;
		}
	}
	
	/* 条目监听内部类 */
	private class ListViewOnItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			//Toast.makeText(ContactActivity.this, "你选择了" + arg2+ " 个按键", Toast.LENGTH_LONG).show();
			final CategoryModel categoryModel = (CategoryModel) categoryList.objectAtIndex(arg2);
			Intent intent = new Intent();
			intent.putExtra("category_id", categoryModel.id);
			intent.putExtra("category_name", categoryModel.name);
			intent.setClass(CategoryActivity.this, TimelineActivity.class);
			startActivity(intent);
			
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		categoryList = new Datalist(this);
		
		//设置分类数据
		CategoryModel category1 = new CategoryModel(this);
		category1.id = "3";
		category1.name = "衣服";
		categoryList.list.add(category1);
		
		CategoryModel category2 = new CategoryModel(this);
		category2.id = "5";
		category2.name = "裙子";
		categoryList.list.add(category2);
		
		CategoryModel category3 = new CategoryModel(this);
		category3.id = "2";
		category3.name = "包包";
		categoryList.list.add(category3);
		
		CategoryModel category4 = new CategoryModel(this);
		category4.id = "4";
		category4.name = "鞋子";
		categoryList.list.add(category4);
		
		CategoryModel category5 = new CategoryModel(this);
		category5.id = "9";
		category5.name = "裤子";
		categoryList.list.add(category5);
		
		CategoryModel category6 = new CategoryModel(this);
		category6.id = "6";
		category6.name = "饰品";
		categoryList.list.add(category6);
		
		CategoryModel category7 = new CategoryModel(this);
		category7.id = "1";
		category7.name = "其它";
		categoryList.list.add(category7);
		
		findViews();
		
		
	}
	
	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		super.findViews();
		
		// TODO Auto-generated method stub
		// 适配器
		CategoryViewAdapter categoryViewAdapter = new CategoryViewAdapter(this, categoryList);
		// 配置Listview
		listView = (ListView) findViewById(R.id.listView);		
		listView.setAdapter(categoryViewAdapter);
		listView.setOnItemClickListener(new ListViewOnItemClickListener());

	}
}
