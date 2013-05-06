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

public class MenuFragment extends Fragment {
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */
	SharedPreferences mSharedPreferences, sp, pref;
	Editor e;
	Boolean io;

	Boolean bp = false;
	ImageView logout;
	View view;

	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.logout, container, false);

		logout = (ImageView) view.findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {

			public void onClick(View v)

			{
				bp = false;
				int a = 1;
				Intent myIntent = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(myIntent);
				SharedPreferences spf = getActivity().getSharedPreferences(
						"Sample", 0);

				SharedPreferences.Editor se = spf.edit();

				se.putBoolean("boolean", bp);
				se.putInt("a", a);
				se.commit();
				getActivity().finish();
				Grid.getInstance().finish();
			}

		});

		return view;

	}

}