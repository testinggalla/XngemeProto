package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Grid extends Activity {
	ImageView send;
	Activity act;
	EditText email;
	SharedPreferences ss;
	MainActivity main;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	boolean done = false;
	static Grid activityA;
	private SlideoutHelper mSlideoutHelper;
	TextView name, phone, add;
	ImageView profilepic, map;
	String tvpic, tvname, tvphone;
	Bitmap myBitmap;
	public Integer[] mThumbIds = { R.drawable.mfacebook, R.drawable.mtwitter,
			R.drawable.mlinkedin, R.drawable.mplus, R.drawable.msky,
			R.drawable.morkut, R.drawable.mtumblr, R.drawable.mbebo,
			R.drawable.mfacebook, R.drawable.mtwitter, R.drawable.mlinkedin,
			R.drawable.mplus, R.drawable.msky, R.drawable.morkut,
			R.drawable.mtumblr, R.drawable.mbebo, R.drawable.mfacebook,
			R.drawable.mtwitter, R.drawable.mlinkedin, R.drawable.mplus,
			R.drawable.msky, R.drawable.morkut, R.drawable.mtumblr,
			R.drawable.mbebo, R.drawable.mfacebook, R.drawable.mtwitter,
			R.drawable.mlinkedin, R.drawable.mplus, R.drawable.msky,
			R.drawable.morkut, R.drawable.mtumblr, R.drawable.mbebo, };
	public Integer[] tags = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			init();
			return true;
		case KeyEvent.KEYCODE_BACK:
			Display.getInstance().finish();
			finish();
			
			return true;
		}

		return false;
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
		activityA = this;
		main = new MainActivity();
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		ss = getSharedPreferences("Androidsoft", 0);
		send = (ImageView) findViewById(R.id.send);
		email = (EditText) findViewById(R.id.email1);
		email.setTypeface(font);
		mSlideoutHelper = new SlideoutHelper(this);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		View mainview = (View) findViewById(R.id.inner_content);
		new BackgroundAsyncTask().execute();
		// profilepic = (ImageView) findViewById(R.id.profile);
		// map = (ImageView) findViewById(R.id.map);
		profilepic = (ImageView) findViewById(R.id.profile);
		name = (TextView) findViewById(R.id.name);
		add = (TextView) findViewById(R.id.add);
		phone = (TextView) findViewById(R.id.phone);

		Intent in = getIntent();
		in.getStringArrayListExtra("links");
		Toast.makeText(getBaseContext(),
				"" + in.getStringArrayListExtra("links"), Toast.LENGTH_SHORT)
				.show();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(), Display.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				i.setClassName(getApplicationContext(),
						"com.example.myxngeme.Display");
				startActivity(i);
				finish();
			}
		});

		mSlideoutHelper = new SlideoutHelper(this);
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
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int w = dm.widthPixels;
		int w1 = dm.heightPixels;
		RelativeLayout l = (RelativeLayout) findViewById(R.id.viewlay);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) l
				.getLayoutParams();
		params.height = w / 4;
		params.width = w;
		// RelativeLayout l1=(RelativeLayout) findViewById(R.id.two);
		RelativeLayout l2 = (RelativeLayout) findViewById(R.id.one);
		RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) l2
				.getLayoutParams();

		params1.width = w / 2;

		l.setLayoutParams(params);
		l2.setLayoutParams(params1);

		TextView add = (TextView) findViewById(R.id.add);
		add.setHeight(w / 8);
		add.setWidth(w / 8);

		ImageView map1 = (ImageView) findViewById(R.id.map);
		map1.getLayoutParams().width = w / 8;
		map1.getLayoutParams().height = w / 8;

		ImageView prof = (ImageView) findViewById(R.id.profile);
		prof.getLayoutParams().width = w / 4;
		prof.getLayoutParams().height = w / 4;

		GridView gridView = (GridView) findViewById(R.id.gridview1);
		GridView gridView1 = (GridView) findViewById(R.id.gridview2);

		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapterForGrid1(this, w));
		gridView1.setAdapter(new ImageAdapterForGrid2(this, w));

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ImageView i = (ImageView) v;
				Toast.makeText(getBaseContext(), "grid " + v,
						Toast.LENGTH_SHORT).show();
				Log.v("Clicke", "at " + position + "--" + i.getTag());
				if (i.getTag() == null) {
					Log.v("View", "" + v);
					tags[position] = 1;
					i.setAlpha(0x66);
					i.setTag(1);
				} else if (i.getTag().toString().equals("1")) {
					i.setTag(null);
					tags[position] = 0;
					i.setAlpha(0xff);
				}

			}
		});
		gridView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				ImageView i = (ImageView) v;
				Log.v("Clicke", "at " + position + "--" + i.getTag());
				if (i.getTag() == null) {
					Log.v("View", "" + v);
					tags[position + 4] = 1;
					i.setAlpha(0x66);
					i.setTag(1);
				} else if (i.getTag().toString().equals("1")) {
					i.setTag(null);
					tags[position + 4] = 0;
					i.setAlpha(0xff);
				}

			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fb = ss.getString("fb", null);
				String twt = ss.getString("twt", null);
				String ln = ss.getString("ln", null);
				String gplus = ss.getString("gplus", null);
				String body = "XngeMe" + "\n" + "facebook :" + "\n" + fb + "\n"
						+ "twitter :" + "\n" + twt + "\n" + "linkedin :" + "\n"
						+ ln + "\n" + "googleplus :" + "\n" + gplus;
				String recp = email.getText().toString();
				Log.v("recp", "" + recp);
				sendGmail(Grid.this, "Hello from XngeMe!", body, recp);
			}
		});
	}

	public void sendGmail(Context activity, String subject, String text,
			String receipient) {
//		Intent gmailIntent = new Intent();
//		gmailIntent.setClassName("com.google.android.gm",
//				"com.google.android.gm.ComposeActivityGmail");
//		gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
//		gmailIntent.putExtra(android.content.Intent.EXTRA_UID, receipient);
//		gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
//		gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { receipient });
//		try {
//			startActivity(gmailIntent);
//			overridePendingTransition(R.anim.slide_in_up, 0);
//		} catch (ActivityNotFoundException ex) {
//			// handle error
//		}
		
		Intent email = new Intent(Intent.ACTION_SEND);
		
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { receipient });		  
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, text);
		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Choose an Email client :"));
		
		
	}

	public class ImageAdapterForGrid1 extends BaseAdapter {
		private Context mContext;

		int l;
		int l1;

		// Keep all Images in array

		// Constructor
		public ImageAdapterForGrid1(Context c, int w) {

			mContext = c;
			l = (w / 4);
			l1 = (w / 2);
		}

		@Override
		public int getCount() {
			return 4;

		}

		@Override
		public Object getItem(int position) {
			return mThumbIds[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(mThumbIds[position]);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(l, l));
			if (tags[position] == 0) {
				imageView.setAlpha(0xff);
			} else {
				imageView.setAlpha(0x66);
			}
			return imageView;
		}

	}

	public class ImageAdapterForGrid2 extends BaseAdapter {
		private Context mContext;

		int l;

		// Keep all Images in array

		// Constructor
		public ImageAdapterForGrid2(Context c, int w) {

			mContext = c;
			l = (w / 4);
		}

		@Override
		public int getCount() {
			return mThumbIds.length - 4;
		}

		@Override
		public Object getItem(int position) {
			return mThumbIds[position + 4];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(mThumbIds[position + 4]);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(l, l));
			if (tags[position + 4] == 0) {
				imageView.setAlpha(0xff);
			} else {
				imageView.setAlpha(0x66);
			}
			return imageView;
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

	public static Grid getInstance() {
		Log.v("instance", "called");
		return activityA;
	}

	public class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ss = getSharedPreferences("Androidsoft", 0);
			tvname = ss.getString("username", null);
			tvphone = ss.getString("phone", null);
			tvpic = ss.getString("profilepic", null);

			try {
				URL url = new URL(tvpic);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				myBitmap = BitmapFactory.decodeStream(input);
				Log.v("myBit", " " + myBitmap);

			} catch (IOException e) {
				e.printStackTrace();
				Log.v("catch", "exeption");
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			name.setText(tvname);
			phone.setText(tvphone);
			profilepic.setImageBitmap(myBitmap);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
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
