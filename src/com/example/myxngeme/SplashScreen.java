package com.example.myxngeme;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Splash screen activity
 * 
 * @author Catalin Prata
 */

public class SplashScreen extends Activity {

	// used to know if the back button was pressed in the splash screen activity
	// and avoid opening the next activity
	private boolean mIsBackButtonPressed;
	private static final int SPLASH_DURATION = 2000; // 2 seconds
	Cursor c = null;
	Cursor c1 = null;
	SharedPreferences mSharedPreferences;
	Editor e;
	Boolean io;
	int b;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscr);
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		final boolean previouslyStarted = prefs.getBoolean(
				getString(R.string.hello_world), false);

		SharedPreferences spf = getSharedPreferences("Sample", 0);

		io = spf.getBoolean("boolean", false);
		b = spf.getInt("a", 0);
		DatabaseHelper myDbHelper = new DatabaseHelper(SplashScreen.this);
		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			// throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {
			Log.v("catch", "exeption");

			throw sqle;

		}
		myDbHelper.close();
		Handler handler = new Handler();

		// run a thread after 2 seconds to start the home screen
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				// make sure we close the splash screen so the user won't come
				// back when it presses back key

				finish();

				if (!mIsBackButtonPressed) {
					// start the home screen if the back button wasn't pressed
					// already
					if (!previouslyStarted) {
						Log.v("first installation", " called");

						SharedPreferences.Editor edit = prefs.edit();
						edit.putBoolean(getString(R.string.hello_world),
								Boolean.TRUE);
						edit.commit();
						Intent intent = new Intent(SplashScreen.this,
								MainActivity.class);
						SplashScreen.this.startActivity(intent);
						finish();
					} else if (io == true) {

						Log.v("io", " " + io);
						Log.v("ofterlogout", " called");

						Intent inten = new Intent(getBaseContext(), Grid.class);
						startActivity(inten);
						b = 0;
						finish();

					} else if (io == false) {
						Log.v("without logout function", "called");
						Intent inten = new Intent(SplashScreen.this,
								MainActivity.class);
						startActivity(inten);
						finish();
					}

				}

			}

		}, SPLASH_DURATION); // time in milliseconds (1 second = 1000
								// milliseconds) until the run() method will be
								// called

	}

	@Override
	public void onBackPressed() {

		// set the flag to true so the next activity won't start up
		mIsBackButtonPressed = true;
		super.onBackPressed();

	}
}