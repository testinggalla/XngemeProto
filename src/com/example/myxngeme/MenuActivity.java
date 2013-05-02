package com.example.myxngeme;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.myxngeme.SlideoutHelper;

public class MenuActivity extends FragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mSlideoutHelper = new SlideoutHelper(this);
	    mSlideoutHelper.activate();
	    getSupportFragmentManager().beginTransaction().add(com.example.myxngeme.R.id.slideout_placeholder, new MenuFragment(), "menu").commit();
	    mSlideoutHelper.open();
	    Log.v("oncreate","done");
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
//			mSlideoutHelper.close();
			 Log.v("onkeydown","done");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	public SlideoutHelper getSlideoutHelper(){
		 Log.v("slideout","done");
		return mSlideoutHelper;
		
	}
	
	private SlideoutHelper mSlideoutHelper;
	

}
