package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myxngeme.ClickableListAdapter.ViewHolder;

public class Display extends Activity {

	Button save;
	TextView name, phone, country, email, addnetwork;
	String tvname, tvphone, tvcountry, tvemail, tvpic;
	ImageView profilepic;
	SharedPreferences ss;
	Cursor c = null;
	String table;
	ArrayList<String> al;
	Bitmap myBitmap;
	SharedPreferences.Editor ed;
	ProgressBar progressBar;
	DatabaseHelper myDbHelper;
	ListView list;
	ArrayList<String> images;
	ArrayList<Integer> imagesgrid;
	ArrayList<String> names;
	ArrayList<Integer> posi;
	Bitmap mIconEnabled;
	Bitmap mIconDisabled;
	List<MyData> mObjectList;
	DBxngeme dbc;
	static Display ds;
	ProgressBar bar;
	int myProgress;
	RelativeLayout re;

	/**
	 * Our data class. This data will be bound to MyViewHolder, which in turn is
	 * used for the ListView.
	 */
	static class MyData {
		public MyData(String t, boolean e) {
			text = t;
			enable = e;
		}

		String text;
		boolean enable;
	}

	/**
	 * Our very own holder referencing the view elements of our ListView layout
	 */
	static class MyViewHolder extends ViewHolder {

		public MyViewHolder(TextView t, ImageView i) {
			text = t;
			icon = i;

		}

		TextView text;
		ImageView icon;
	}

	@Override
	public void onBackPressed() {
		// Do nothing
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.display);

		SharedPreferences spf = getSharedPreferences("Sample", 0);

		SharedPreferences.Editor se = spf.edit();

		se.putBoolean("dff", true);
		se.commit();

		Log.v("on", "create");
		ds = this;
		dbc = new DBxngeme(this);
		posi = new ArrayList<Integer>();
		new BackgroundAsyncTask().execute();
		imagesgrid = new ArrayList<Integer>();
		names = new ArrayList<String>();
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		al = new ArrayList<String>();
		myDbHelper = new DatabaseHelper(Display.this);
		addnetwork = (TextView) findViewById(R.id.addnetwork);
		addnetwork.setTypeface(font);
		save = (Button) findViewById(R.id.save);
		save.setTypeface(font);
		name = (TextView) findViewById(R.id.name);
		name.setTypeface(font);
		phone = (TextView) findViewById(R.id.phone);
		phone.setTypeface(font);
		country = (TextView) findViewById(R.id.country);
		country.setTypeface(font);
		email = (TextView) findViewById(R.id.mail);
		email.setTypeface(font);
		re = (RelativeLayout) findViewById(R.id.relative1);
		profilepic = (ImageView) findViewById(R.id.profilepic);
		// getting the screen height and width
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int wwidth = displaymetrics.widthPixels;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) re
				.getLayoutParams();
		re.getLayoutParams().width = height / 1;
		re.getLayoutParams().height = height / 4;
		profilepic.getLayoutParams().width = height / 6;
		profilepic.getLayoutParams().height = height / 6;
		params.setMargins(wwidth / 24, wwidth / 20, wwidth / 20, wwidth / 24); // substitute
																				// parameters
																				// for
																				// left,
																				// top,
																				// right,
																				// bottom
		re.setLayoutParams(params);
		RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) name
				.getLayoutParams();
		params1.setMargins(wwidth / 20, wwidth / 100, 0, wwidth / 30); // substitute
																		// parameters
																		// for
																		// left,
																		// top,
																		// right,
																		// bottom
		name.setLayoutParams(params1);
		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) phone
				.getLayoutParams();
		params2.setMargins(wwidth / 20, 0, 0, wwidth / 30); // substitute
															// parameters for
															// left, top, right,
															// bottom
		phone.setLayoutParams(params2);
		RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) country
				.getLayoutParams();
		params4.setMargins(wwidth / 20, 0, 0, 0); // substitute parameters for
													// left, top, right, bottom
		phone.setLayoutParams(params2);
		RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) email
				.getLayoutParams();
		params3.setMargins(height / 6, 0, 0, 0); // substitute parameters for
													// left, top, right, bottom
		email.setLayoutParams(params3);
		list = (ListView) findViewById(R.id.list);
		mObjectList = new ArrayList<MyData>();

		mIconEnabled = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.p);
		mIconDisabled = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.tick);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbc.open();
				c = dbc.getAllContacts();

				if (c.getCount() == 0) {

					AlertDialog.Builder alert = new AlertDialog.Builder(
							Display.this);

					alert.setTitle("Error");
					alert.setMessage("Please select atleast one link");

					alert.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.dismiss();

								}
							});

					alert.show();
				} else {
					Intent in = new Intent(getBaseContext(), Grid.class);
					startActivity(in);
				}
			}
		});
	}

	// create static object to current activity
	public static Display getInstance() {
		return ds;
	}

	// async task for loading links and image links
	public class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		ProgressDialog progressdialog;

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				ss = getSharedPreferences("Androidsoft", 0);
				ed = ss.edit();
				tvname = ss.getString("username", null);
				tvphone = ss.getString("phone", null);
				tvcountry = ss.getString("country", null);
				tvemail = ss.getString("emailid", null);
				tvpic = ss.getString("profilepic", null);
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
			Bitmap output = Bitmap.createBitmap(myBitmap.getWidth(),
					myBitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, myBitmap.getWidth(),
					myBitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = 10;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(myBitmap, rect, rect, paint);
			profilepic.setImageBitmap(output);
			myDbHelper.openDataBase();
			// check the username entered
			c = myDbHelper.sriram(tvname, null, null, null, null, null,
					null);
			images = new ArrayList<String>();
			if (c.moveToFirst()) {
				do {
					imagesgrid.add(c.getInt(0));
					names.add(c.getString(1));
					al.add(c.getString(2));
					images.add(c.getString(3));
				} while (c.moveToNext());
			}


			name.setText(tvname);
			phone.setText(tvphone);
			country.setText(tvcountry);
			email.setText(tvemail);
			myDbHelper.close();
			for (int i = 0; i < al.size(); ++i) {
				mObjectList.add(new MyData("" + al.get(i), true));
			}
			list.setAdapter(new MyClickableListAdapter(getBaseContext(),
					R.layout.adapter, mObjectList));
			progressdialog.dismiss();
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
			progressdialog = new ProgressDialog(Display.this);
			progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressdialog.setMessage("Loading...");
			progressdialog.setCancelable(true);
			progressdialog.show();
		}
	}

	// Baseadapter
	/**
	 * The implementation of ClickableListAdapter
	 */
	private class MyClickableListAdapter extends ClickableListAdapter {

		public MyClickableListAdapter(Context context, int viewid,
				List<MyData> objects) {
			super(context, viewid, objects);

			// nothing to do
		}

		protected void bindHolder(ViewHolder h, int position) {
			// Binding the holder keeps our data up to date.
			// In contrast to createHolder this method is called for all items
			// So, be aware when doing a lot of heavy stuff here.
			// we simply transfer our object's data to the list item
			// representatives
			MyViewHolder mvh = (MyViewHolder) h;
			MyData mo = (MyData) mvh.data;

			mvh.icon.setImageBitmap(mo.enable ? Display.this.mIconEnabled
					: Display.this.mIconDisabled);
			mvh.text.setText(mo.text);
			mvh.icon.setTag(position);

		}

		int p;

		protected ViewHolder createHolder(View v, int position) {

			// createHolder will be called only as long, as the ListView is not
			// filled
			// entirely. That is, where we gain our performance:
			// We use the relatively costly findViewById() methods and
			// bind the view's reference to the holder objects.
			TextView text = (TextView) v.findViewById(R.id.listitem_text);

			ImageView icon = (ImageView) v.findViewById(R.id.listitem_icon);
			ViewHolder mvh = new MyViewHolder(text, icon);
			p = position;
			// Additionally, we make some icons clickable
			// Mind, that item becomes clickable, when adding a click listener
			// (see API)
			// so, it is not necessary to use the android:clickable attribute in
			// XML
			icon.setOnClickListener(new ClickableListAdapter.OnClickListener(
					mvh) {

				public void onClick(View v, ViewHolder viewHolder) {

					// we toggle the enabled state and also switch the icon
					MyViewHolder mvh = (MyViewHolder) viewHolder;
					MyData mo = (MyData) mvh.data;
					mo.enable = !mo.enable;

					ImageView icon = (ImageView) v;
					icon.setImageBitmap(mo.enable ? Display.this.mIconEnabled
							: Display.this.mIconDisabled);
					boolean value = posi.contains(icon.getTag());
					if (mo.enable == true) {
						value = true;
					} else {
						value = false;
					}
					// check the condition if images are enable or not
					if (mo.enable == false && value == false) {
						posi.add((Integer) icon.getTag());

						dbc.open();
						dbc.insertContact(
								(imagesgrid.get((Integer) icon.getTag())),
								(al.get((Integer) icon.getTag())), false,
								(images.get((Integer) icon.getTag())),
								(names.get((Integer) icon.getTag())));
						dbc.close();
					}
					if (mo.enable == true && value == true) {
						posi.remove((Integer) icon.getTag());
						dbc.open();
						dbc.delete(imagesgrid.get((Integer) icon.getTag()));

						dbc.close();
					}

				}
			});

			return mvh; // finally, we return our new holder
		}

	}
}