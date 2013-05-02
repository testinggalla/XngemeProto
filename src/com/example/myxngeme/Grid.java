package com.example.myxngeme;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myxngeme.SlideoutActivity;
import com.example.myxngeme.SlideoutHelper;

public class Grid extends Activity {
	ImageView send;
	Activity act;
	EditText email;
	SharedPreferences ss;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	boolean done = false;
	private SlideoutHelper mSlideoutHelper;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        email.setText("");

    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		ss = getSharedPreferences("Androidsoft", 0);
		send = (ImageView) findViewById(R.id.send);
		email = (EditText) findViewById(R.id.email1);
		email.setTypeface(font);
		mSlideoutHelper = new SlideoutHelper(this);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		View mainview = (View) findViewById(R.id.inner_content);
		/* for sliding */
		// Set the touch listener for the main view to be our custom gesture
		// listener
		mainview.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		});
		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						init();

					}
				});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(Grid.this, MainActivity.class);
				// startActivity(intent);
				// overridePendingTransition(R.anim.slide_in_up, 0);

				String fb = ss.getString("fb", null);
				String twt = ss.getString("twt", null);
				String ln = ss.getString("ln", null);
				String gplus = ss.getString("gplus", null);
				String body = "XngeMe" + "\n" + "facebook :" + "\n" + fb + "\n"
						+ "twitter :" + "\n" + twt + "\n" + "linkedin :" + "\n"
						+ ln + "\n" + "googleplus :" + "\n" + gplus;
				String recp = email.getText().toString();
				Log.v("recp", "" + recp);
//				Toast.makeText(getBaseContext(), "" + recp, Toast.LENGTH_SHORT)
//						.show();
				sendGmail(Grid.this, "Hello from XngeMe!", body, recp);
			}
		});
	}

	public void sendGmail(Context activity, String subject, String text,
			String receipient) {
		Intent gmailIntent = new Intent();
		gmailIntent.setClassName("com.google.android.gm",
				"com.google.android.gm.ComposeActivityGmail");
		gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		gmailIntent.putExtra(android.content.Intent.EXTRA_UID, receipient);
		gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { receipient });
		try {
			startActivity(gmailIntent);
			overridePendingTransition(R.anim.slide_in_up, 0);
		} catch (ActivityNotFoundException ex) {
			// handle error
		}
	}

	/* for sliding */
	public void init() {

		int width = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 200, getResources()
						.getDisplayMetrics());
		Log.v("width", " " + width);
		SlideoutActivity.prepare(Grid.this, R.id.inner_content, width);
		startActivity(new Intent(Grid.this, MenuActivity.class));
		Log.v("hai", "done");
		overridePendingTransition(0, 0);
	}

	/* for sliding */
	class MyGestureDetector extends SimpleOnGestureListener {
		public boolean onFling(final MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			if (e1 == null) {
				// e1 = mLastOnDownEvent;
			}
			if (e1 == null || e2 == null) {
				return false;
			}

			final float dX = e2.getX() - e1.getX();
			float dY = e1.getY() - e2.getY();

			if (Math.abs(dY) < SWIPE_MAX_OFF_PATH
					&& Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY
					&& Math.abs(dX) >= SWIPE_MIN_DISTANCE) {
				if (dX > 0) {
					if (done == false) {
						init();
					} else {

					}

				} else {

				}
				return true;
			}
			return false;
		}

		// It is necessary to return true from onDown for the onFling event to
		// register
		public boolean onDown(MotionEvent e) {
			Log.v("ondown", "done");

			return true;
		}

	}
}
