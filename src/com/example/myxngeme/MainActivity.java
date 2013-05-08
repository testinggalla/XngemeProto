package com.example.myxngeme;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	ImageView login;
	LinearLayout line;
	FBvalues fb;
	Display dsp;
	Grid gd;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		fb.finishActivity(0);
		dsp.finishActivity(0);
		gd.finishActivity(0);
//		finish();
		// moveTaskToBack(true);
		 Intent startMain = new Intent(Intent.ACTION_MAIN);
		 startMain.addCategory(Intent.CATEGORY_HOME);
		 startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 startActivity(startMain);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		login = (ImageView) findViewById(R.id.fblogin);
		line = (LinearLayout) findViewById(R.id.linear);
		line.getBackground().setAlpha(50);
		fb = new FBvalues();
		dsp = new Display();
		gd = new Grid();
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
