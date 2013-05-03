package com.example.myxngeme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	FBvalues fb;
	Display dsp;
	Grid gd;
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
		fb=new FBvalues();
		dsp=new Display();
		gd=new Grid();
		

		view = inflater.inflate(R.layout.logout, container, false);
		logout=(ImageView) view.findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(), MainActivity.class);
//				i.setClass(getActivity(), MainActivity.class);
				getActivity().startActivity(i);
				getActivity().finish();
				fb.finishActivity(0);
				dsp.finishActivity(0);
				gd.finishActivity(0);
			}
		});


		return view;

		

	}

}