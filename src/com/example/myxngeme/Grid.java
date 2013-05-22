package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class Grid extends Activity {
	ImageView send;
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
	TextView name, phone;

	ImageView profilepic, map, add;
	String tvpic, tvname, tvphone;
	Bitmap myBitmap, myBitmap1, myBitmap2;
	ArrayList<String> mThumbIds;
	ArrayList<String> links;
	ArrayList<String> names;
	ArrayList<String> sel_links, sel_names;
	DBxngeme dbc;
	Cursor c;
	GridView gridView, gridView1;
	ProgressDialog dialog;
	int w;

	// tags for each image in gridview
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
			SharedPreferences spf = getSharedPreferences("Sample", 0);
			Boolean check = spf.getBoolean("df", false);
			Boolean check1 = spf.getBoolean("dff", false);
			if (check == true || check1 == true) {
				Display.getInstance().finish();
			} else {
				// nothing
			}
			// finishing the current activity
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
		dbc = new DBxngeme(this);
		mThumbIds = new ArrayList<String>();
		links = new ArrayList<String>();
		names = new ArrayList<String>();
		sel_links = new ArrayList<String>();
		sel_names = new ArrayList<String>();
		activityA = this;
		main = new MainActivity();
		// applying font for texts
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		ss = getSharedPreferences("Androidsoft", 0);
		send = (ImageView) findViewById(R.id.send);
		email = (EditText) findViewById(R.id.email1);
		email.setTypeface(font);
		mSlideoutHelper = new SlideoutHelper(this);
		gestureDetector = new GestureDetector(getBaseContext(),
				new MyGestureDetector());
		View mainview = (View) findViewById(R.id.inner_content);
		new BackgroundAsyncTask().execute();
		profilepic = (ImageView) findViewById(R.id.profile);
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
		add = (ImageView) findViewById(R.id.add);
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

		// getting the images links and links from database table
		dbc.open();
		c = dbc.getAllContacts();
		if (c.moveToFirst()) {
			do {
				mThumbIds.add(c.getString(3));
				links.add(c.getString(1));
				names.add(c.getString(4));
			} while (c.moveToNext());
		}
		c.close();
		dbc.close();

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
		// getting the screen height and width
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		w = dm.widthPixels;
		RelativeLayout l = (RelativeLayout) findViewById(R.id.viewlay);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) l
				.getLayoutParams();
		params.height = w / 4;
		params.width = w;
		RelativeLayout l2 = (RelativeLayout) findViewById(R.id.one);
		RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) l2
				.getLayoutParams();

		params1.width = w / 2;

		l.setLayoutParams(params);
		l2.setLayoutParams(params1);

		add.getLayoutParams().width = w / 8;
		add.getLayoutParams().height = w / 8;

		ImageView map1 = (ImageView) findViewById(R.id.map);
		map1.getLayoutParams().width = w / 8;
		map1.getLayoutParams().height = w / 8;

		ImageView prof = (ImageView) findViewById(R.id.profile);
		prof.getLayoutParams().width = w / 4;
		prof.getLayoutParams().height = w / 4;

		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) name
				.getLayoutParams();
		param.setMargins(w / 50, w / 40, 0, 0); // substitute

		// parameters
		// for
		// left,
		// top,
		// right,
		// bottom
		name.setLayoutParams(param);
		RelativeLayout.LayoutParams param1 = (RelativeLayout.LayoutParams) add
				.getLayoutParams();

		add.setPadding(w / 30, w / 30, w / 30, w / 30);
		add.setLayoutParams(param1);
		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) phone
				.getLayoutParams();
		params2.setMargins(0, 0, 0, w / 50); // substitute
												// parameters for
												// left, top, right,
												// bottom
		phone.setLayoutParams(params2);

		gridView = (GridView) findViewById(R.id.gridview1);
		gridView1 = (GridView) findViewById(R.id.gridview2);

		progress();

	}

	/*
	 * ******************************************************************************************************************************************
	 */
	private void progress() {
		// TODO Auto-generated method stub

		// dialog = ProgressDialog.show(Grid.this, "",
		// "Loading from server...");
		// dialog.setCancelable(false);
	}

	/*
	 * ******************************************************************************************************************************************
	 */
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
			if (mThumbIds.size() < 4) {
				return mThumbIds.size();
			} else {
				return 4;
			}

		}

		@Override
		public Object getItem(int position) {
			return mThumbIds.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			new BackgroundAsyncTask1(position, imageView, myBitmap1).execute();
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(l, l));
			if (tags[position] == 0) {

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					imageView.setImageAlpha(0xff);
				}

				else {
					imageView.setAlpha(0xff);
				}

			} else {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					imageView.setImageAlpha(0x66);
				}

				else {
					imageView.setAlpha(0x66);
				}
			}
			return imageView;
		}

	}

	// async task for loading image links
	class BackgroundAsyncTask1 extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		int pos;
		ImageView image;
		Bitmap bmp;
		ProgressDialog progressdialog;

		public BackgroundAsyncTask1(int position, ImageView imageView,
				Bitmap myBitmap1) {
			pos = position;
			image = imageView;
			bmp = myBitmap1;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(mThumbIds.get(pos));

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				bmp = BitmapFactory.decodeStream(input);
			} catch (IOException e) {
				e.printStackTrace();
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

			image.setImageBitmap(bmp);
			if (mThumbIds.size() <= 4) {

				if (mThumbIds.size() == 1) {
					if (pos == 0) {
						dialog.dismiss();
					}
				} else if (mThumbIds.size() == 2) {
					if (pos == 1) {
						dialog.dismiss();
					}
				} else if (mThumbIds.size() == 3) {
					if (pos == 2) {
						dialog.dismiss();
					}
				} else if (mThumbIds.size() == 4) {
					if (pos == 3) {
						dialog.dismiss();
					}
				}

			}
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

			return mThumbIds.size() - 4;
		}

		@Override
		public Object getItem(int position) {
			return mThumbIds.get(position + 4);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);
			new BackgroundAsyncTask2(position, imageView, myBitmap2).execute();
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(l, l));
			if (tags[position + 4] == 0) {

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					imageView.setImageAlpha(0xff);
				}

				else {
					imageView.setAlpha(0xff);
				}

			} else {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					imageView.setImageAlpha(0x66);
				}

				else {
					imageView.setAlpha(0x66);
				}
			}

			return imageView;
		}

	}

	// async task for loading image links
	class BackgroundAsyncTask2 extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		int pos;
		ImageView image;
		Bitmap bmp;

		public BackgroundAsyncTask2(int position, ImageView imageView,
				Bitmap myBitmap2) {
			pos = position;
			image = imageView;
			bmp = myBitmap2;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {

				URL url = new URL(mThumbIds.get(pos + 4));
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				bmp = BitmapFactory.decodeStream(input);

			} catch (IOException e) {
				e.printStackTrace();
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

			image.setImageBitmap(bmp);
			// dialog.dismiss();

			super.onPostExecute(result);
			if (mThumbIds.size() > 4) {
				int pos1 = pos + 4;
				if (pos1 == mThumbIds.size() - 1) {

					dialog.dismiss();
				}
			}

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

			//

		}
	}

	/* for sliding */
	public void init() {
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
				(w - (w / 3)), getResources().getDisplayMetrics());
		SlideoutActivity.prepare(Grid.this, R.id.inner_content, width);
		startActivity(new Intent(Grid.this, MenuActivity.class));
		overridePendingTransition(0, 0);

	}

	// creating statc object of current activity
	public static Grid getInstance() {
		return activityA;
	}

	// async task for loading profile pic
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

			} catch (IOException e) {
				e.printStackTrace();
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
			dialog.dismiss();
			dialog = ProgressDialog.show(Grid.this, "",
					"Loading from server...");
			dialog.setCancelable(false);
			gridView.setAdapter(new ImageAdapterForGrid1(Grid.this, w));
			gridView1.setAdapter(new ImageAdapterForGrid2(Grid.this, w));

			/**
			 * On Click event for Single Gridview Item
			 * */
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					ImageView i = (ImageView) v;

					if (i.getTag() == null) {
						sel_names.add(names.get(position));
						sel_links.add(links.get(position));
						tags[position] = 1;

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							i.setImageAlpha(0x66);
						}

						else {
							i.setAlpha(0x66);
						}

						i.setTag(1);
					} else if (i.getTag().toString().equals("1")) {
						i.setTag(null);
						tags[position] = 0;
						sel_names.remove(names.get(position));
						sel_links.remove(links.get(position));

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							i.setImageAlpha(0xff);
						}

						else {
							i.setAlpha(0xff);
						}
					}

				}
			});
			gridView1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					ImageView i = (ImageView) v;
					if (i.getTag() == null) {
						sel_names.add(names.get(position + 4));
						sel_links.add(links.get(position + 4));
						tags[position + 4] = 1;

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							i.setImageAlpha(0x66);
						}

						else {
							i.setAlpha(0x66);
						}
						i.setTag(1);
					} else if (i.getTag().toString().equals("1")) {
						sel_names.remove(names.get(position + 4));
						sel_links.remove(links.get(position + 4));
						i.setTag(null);
						tags[position + 4] = 0;
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							i.setImageAlpha(0xff);
						}

						else {
							i.setAlpha(0xff);
						}
					}

				}
			});

			send.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stubLog/
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < sel_links.size(); i++) {
						sb.append(sel_names.get(i));
						sb.append(":");
						sb.append("\n");
						sb.append(sel_links.get(i));
						sb.append("\n");
					}
					StringBuilder sel_cat = sb;
					String recp = email.getText().toString();
					sendGmail(Grid.this, "Hello from XngeMe!", "XngeMe" + "\n"
							+ sel_cat, recp);

				}
			});

			// new setGrids().execute();

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
			dialog = ProgressDialog.show(Grid.this, "",
					"Loading Profile picture...");
			dialog.setCancelable(false);
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
			return true;
		}

	}
}