package com.ingker.fragment;

import com.ingker.tongkuancollection.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
@SuppressLint("ValidFragment")
public class CategoryFragment extends Fragment {
    String text = null;

    public CategoryFragment() {
    }

    public CategoryFragment(String text) {
        Log.e("Krislq", text);
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("Krislq", "onCreate:"+text);
        getActivity().setTitle("分类");
        //hjk
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Krislq", "onCreateView:"+ text);
        
        View view1 = inflater.inflate(R.layout.fragment_content, container, false);
		View view = view1.findViewById(R.id.category);
		View view2 = view1.findViewById(R.id.timeline);
		view2.setVisibility(View.GONE);
		
		return view;
    }

    @Override
    public void onDestroy() {
        Log.e("Krislq", "onDestroy:"+ text);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("Krislq", "onDetach:"+ text);
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.e("Krislq", "onPause:"+ text);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e("Krislq", "onResume:"+ text);
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.e("Krislq", "onStart:"+ text);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e("Krislq", "onStop:"+ text);
        super.onStop();
    }
    
}

