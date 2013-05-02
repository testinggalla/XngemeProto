package com.example.myxngeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuFragment  extends Fragment {
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */

	ImageView logout;
	View view;


	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {


		view = inflater.inflate(R.layout.logout, container, false);
		logout=(ImageView) view.findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "log", Toast.LENGTH_SHORT).show();
				Log.v("log", "out");
				Intent i=new Intent();
				i.setClass(getActivity(), FBvalues.class);
			}
		});


		return view;

		

	}

}