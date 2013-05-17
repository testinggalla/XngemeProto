package com.example.myxngeme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	ImageView login;
	LinearLayout line;
	Boolean df = false;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// finishing the current activity
		Display.getInstance().finish();
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		df = true;
		SharedPreferences spf = getSharedPreferences("Sample", 0);
		SharedPreferences.Editor se = spf.edit();
		se.putBoolean("df", df);
		se.commit();
		login = (ImageView) findViewById(R.id.fblogin);
		line = (LinearLayout) findViewById(R.id.linear);
		line.getBackground().setAlpha(50);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(), FBvalues.class);
				startActivity(i);
				finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}