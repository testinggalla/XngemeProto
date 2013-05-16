package com.example.myxngeme;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public class MenuActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSlideoutHelper = new SlideoutHelper(this);
		mSlideoutHelper.activate();
		getSupportFragmentManager()
				.beginTransaction()
				.add(com.example.myxngeme.R.id.slideout_placeholder,
						new MenuFragment(), "menu").commit();
		//open sliding
		mSlideoutHelper.open();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//close sliding on click of back button
			mSlideoutHelper.close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public SlideoutHelper getSlideoutHelper() {
		return mSlideoutHelper;

	}

	private SlideoutHelper mSlideoutHelper;

}