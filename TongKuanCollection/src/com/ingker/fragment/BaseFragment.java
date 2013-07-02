package com.ingker.fragment;
import com.ingker.tongkuancollection.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	
	public LayoutInflater inflater;
	public ViewGroup container;
	public View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	 @Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	                             Bundle savedInstanceState) {  
		 // Inflate the layout for this fragment 
		 this.inflater = inflater;
		 this.container = container;
	     return null;  
	 }
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		findViews();
		setListeners();
	}
	
	//�����
	public void findViews(){
		
	}
	
	//��������
	public void setListeners(){
		
	}
	
	//��ʾ�������Ի���
	public void showNetworkErrorDialog() {
		AlertDialog.Builder alertDialogbBuilder = new AlertDialog.Builder(getActivity());
		alertDialogbBuilder.setTitle("��������������");
		alertDialogbBuilder.setMessage("����������������");
		alertDialogbBuilder.setPositiveButton("������", null);
		alertDialogbBuilder.create().show();
	}

}
