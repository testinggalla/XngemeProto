package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.myxngeme.ClickableListAdapter;
import com.example.myxngeme.Display;
import com.example.myxngeme.ClickableListAdapter.ViewHolder;
import com.example.myxngeme.Display.MyData;
import com.example.myxngeme.Display.MyViewHolder;

import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity {
	Button save;
	TextView name, phone, country, email, fb, twt, gplus, ln, addnetwork,list_item;
	String tvname, tvphone, tvcountry, tvemail, tvpic;
	Bitmap bmp;
	ImageView profilepic;
	SharedPreferences ss;
	Cursor c = null;
	int a;
	String table;
	ArrayList<String> al;
	ArrayList<Integer> al1;
	Bitmap myBitmap;
	SharedPreferences.Editor ed;
	ProgressBar progressBar;
	DatabaseHelper myDbHelper;
	ListView list;
	// ArrayAdapter<Integer> ad;
	ArrayList<Integer> images;
	ArrayList<String> sel_links;
	ArrayList<Integer> posi;
	Bitmap mIconEnabled;
	Bitmap mIconDisabled;
	List<MyData> mObjectList;
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

//	@Override
//	public void onBackPressed() {
//		// Do nothing
////		return;
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		posi=new ArrayList<Integer>();
		images = new ArrayList<Integer>();
		
		images.add(R.drawable.fb_icon);
		images.add(R.drawable.tw_icon);
		images.add(R.drawable.ln_icon);
		images.add(R.drawable.gp_icon);
		new BackgroundAsyncTask().execute();
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		al = new ArrayList<String>();
		sel_links = new ArrayList<String>();
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
		profilepic = (ImageView) findViewById(R.id.profilepic);
		list = (ListView) findViewById(R.id.list);
		
		mObjectList = new ArrayList<MyData>();
		
		

		mIconEnabled = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.p);
		mIconDisabled = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.tick);
		// al1.add(1);
		// al1.add(2);
		// ad=new
		// ArrayAdapter<Integer>(getBaseContext(),android.R.layout.simple_list_item_1,al1);
		// list.setAdapter(ad);
		// twt = (TextView) findViewById(R.id.twtname);
		// twt.setTypeface(font);
		// ln = (TextView) findViewById(R.id.lnname);
		// ln.setTypeface(font);
		// gplus = (TextView) findViewById(R.id.gplusname);
		// gplus.setTypeface(font);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), ""+posi, Toast.LENGTH_SHORT).show();
//				Toast.makeText(getBaseContext(), ""+sel_links, Toast.LENGTH_SHORT).show();
				
//				for(int i=0;i<posi.size();i++) {
//					Toast.makeText(getBaseContext(), ""+posi.get(i), Toast.LENGTH_SHORT).show();
////					Log.v("links",""+al.get(i));
//					
////					Toast.makeText(getBaseContext(), ""+al.get(i), Toast.LENGTH_SHORT).show();
//				}
				Intent in = new Intent(getBaseContext(), Grid.class);
				in.putStringArrayListExtra("links", sel_links);
				startActivity(in);
				finish();
			}
		});
	}

	public class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

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
			if (tvname.equals("sriram")) {

				c = myDbHelper.sriram(tvname, null, null, null, null, null,
						null);

				if (c.moveToFirst()) {
					do {
						Log.v("links", "" + c.getString(2));
//						mObjectList.add(new MyData("Some Text " + c.getString(2), true));
//						Log.v("links", "" + mObjectList);
//						al.add(new MyData("" +c.getString(2) , true));
						al.add(c.getString(2));
//						Log.v("al", "" + al);
					} while (c.moveToNext());
				}
				// fb.setText(al.get(0));
				// twt.setText(al.get(1));
				// ln.setText(al.get(2));
				// gplus.setText(al.get(3));
				ed.putString("fb", al.get(0));
				ed.putString("twt", al.get(1));
				ed.putString("ln", al.get(2));
				ed.putString("gplus", al.get(3));
				ed.commit();
			} else if (tvname.equals("upendra")) {
				c = myDbHelper.sriram(tvname, null, null, null, null, null,
						null);

				if (c.moveToFirst()) {
					do {
//						Log.v("links", "" + c.getString(2));
//						mObjectList.add(new MyData("Some Text " + c.getString(2), true));
						
						al.add(c.getString(2));
//						Log.v("al", "" + al);
					} while (c.moveToNext());
				}
				// fb.setText(al.get(0));
				// twt.setText(al.get(1));
				// ln.setText(al.get(2));
				// gplus.setText(al.get(3));
				ed.putString("fb", al.get(0));
				ed.putString("twt", al.get(1));
				ed.putString("ln", al.get(2));
				ed.putString("gplus", al.get(3));
				ed.commit();
			}

			name.setText(tvname);
			phone.setText(tvphone);
			country.setText(tvcountry);
			email.setText(tvemail);
			myDbHelper.close();
			for (int i = 0; i < al.size(); ++i) {
				mObjectList.add(new MyData("" +al.get(i), true));
				Log.v("mob", "" + mObjectList);
			}
			list.setAdapter(new MyClickableListAdapter(getBaseContext(), R.layout.adapter,
					mObjectList));
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
			// Log.v("setting tags", ""+mvh.icon.getTag());

		}

		int p;

		protected ViewHolder createHolder(View v, int position) {
			
			// createHolder will be called only as long, as the ListView is not
			// filled
			// entirely. That is, where we gain our performance:
			// We use the relatively costly findViewById() methods and
			// bind the view's reference to the holder objects.
			TextView text = (TextView) v.findViewById(R.id.listitem_text);
			ImageView img = (ImageView) v.findViewById(R.id.listitem);
//			text.setText(""+al.get(position));
			img.setBackgroundResource(images.get(position));
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
					mo.enable = !mo.enable; // toggle
					ImageView icon = (ImageView) v;

					icon.setImageBitmap(mo.enable ? Display.this.mIconEnabled
							: Display.this.mIconDisabled);
					Log.v("clicked tags", "" + icon.getTag());
					if (mo.enable == false && (!(posi.contains(icon.getTag())))) {
						posi.add((Integer) icon.getTag());
						
						al.get((Integer) icon.getTag());
						sel_links.add(al.get((Integer) icon.getTag()));
//						Toast.makeText(getBaseContext(), ""+al.get((Integer) icon.getTag()), Toast.LENGTH_SHORT).show();
						Log.v("al","alfrm"+al);
						Log.v("positins", "" + posi);
					} else if (mo.enable == true
							&& posi.contains(icon.getTag())) {
						posi.remove((Integer) icon.getTag());
						sel_links.remove(al.get((Integer) icon.getTag()));
						Log.v("positins removed", "" + posi);
					}

				}
			});

			return mvh; // finally, we return our new holder
		}

	}
}
