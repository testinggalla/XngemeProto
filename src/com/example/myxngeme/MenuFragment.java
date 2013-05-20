package com.example.myxngeme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
	ImageView logout, settings;
	TextView settext, logtext;
	View view;
	DBxngeme db;
	Cursor cr;

	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {
		// creating inflater layout
		view = inflater.inflate(R.layout.logout, container, false);
		db = new DBxngeme(getActivity());
		logout = (ImageView) view.findViewById(R.id.logout);
		settings = (ImageView) view.findViewById(R.id.setting);
		settext = (TextView) view.findViewById(R.id.settext);
		logtext = (TextView) view.findViewById(R.id.logtext);
		// getting the screen height and width
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) logout
				.getLayoutParams();
		params.setMargins(height / 20, height / 14, 0, 0); // substitute
															// parameters for
															// left, top, right,
															// bottom
		logout.setLayoutParams(params);
		LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) settings
				.getLayoutParams();
		params1.setMargins(height / 20, height / 14, 0, 0); // substitute
															// parameters for
															// left, top, right,
															// bottom
		settings.setLayoutParams(params1);
		LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) settext
				.getLayoutParams();
		params2.setMargins(height / 20, height / 50, 0, 0); // substitute
															// parameters for
		// left, top, right, bottom
		settext.setLayoutParams(params2);
		LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) logtext
				.getLayoutParams();
		params3.setMargins(height / 16, height / 50, 0, 0); // substitute
															// parameters for
		// left, top, right, bottom
		logtext.setLayoutParams(params3);
		logout.setOnClickListener(new OnClickListener() {

			public void onClick(View v)

			{
				// open database to delete all the user data
				db.open();
				cr = db.getAllContacts();
				db.deleteallContacts();
				db.close();
				bp = false;
				int a = 1;
				Intent myIntent = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(myIntent);
				// send the value to splashscreen activity through shared
				// preference
				SharedPreferences spf = getActivity().getSharedPreferences(
						"Sample", 0);

				SharedPreferences.Editor se = spf.edit();

				se.putBoolean("boolean", bp);
				se.putInt("a", a);
				se.commit();
				Boolean check = spf.getBoolean("df", false);

				Boolean check1 = spf.getBoolean("dff", false);

				getActivity().finish();
				// finishing the grid,display activties
				Grid.getInstance().finish();
				if (check == true || check1 == true) {
					Display.getInstance().finish();
				} else {

				}
			}

		});

		return view;

	}

}