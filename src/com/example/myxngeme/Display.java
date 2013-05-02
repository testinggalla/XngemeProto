package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity {
	Button save;
	TextView name, phone, country, email, fb, twt, gplus, ln,addnetwork;
	String tvname, tvphone, tvcountry, tvemail, tvpic;
	Bitmap bmp;
	ImageView profilepic;
	SharedPreferences ss;
	Cursor c = null;
	int a;
	String table;
	ArrayList<String> al;
	Bitmap myBitmap;
	SharedPreferences.Editor ed;
//	@Override
//	public void onBackPressed() {
//	    // Do nothing
//	    return;
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		al = new ArrayList<String>();
		DatabaseHelper myDbHelper = new DatabaseHelper(Display.this);
		addnetwork=(TextView) findViewById(R.id.addnetwork);
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
		fb = (TextView) findViewById(R.id.fbname);
		fb.setTypeface(font);
		twt = (TextView) findViewById(R.id.twtname);
		twt.setTypeface(font);
		ln = (TextView) findViewById(R.id.lnname);
		ln.setTypeface(font);
		gplus = (TextView) findViewById(R.id.gplusname);
		gplus.setTypeface(font);

		ss = getSharedPreferences("Androidsoft", 0);
		ed = ss.edit();
		tvname = ss.getString("username", null);
		tvphone = ss.getString("phone", null);
		tvcountry = ss.getString("country", null);
		tvemail = ss.getString("emailid", null);
		tvpic = ss.getString("profilepic", null);
		
		try {
	        URL url = new URL(tvpic);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	       myBitmap = BitmapFactory.decodeStream(input);
	        Log.v("myBit"," "+myBitmap);
	        
	       
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.v("catch","exeption");
	    }
		Bitmap output = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap
	            .getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, myBitmap.getWidth(), myBitmap.getHeight());
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

			c = myDbHelper.sriram(tvname, null, null, null, null, null, null);

			if (c.moveToFirst()) {
				do {
					String link = c.getString(2);
					Log.v("links", "" + c.getString(2));
					al.add(c.getString(2));

				} while (c.moveToNext());
			}
			fb.setText(al.get(0));
			twt.setText(al.get(1));
			ln.setText(al.get(2));
			gplus.setText(al.get(3));
			ed.putString("fb",al.get(0));
			ed.putString("twt",al.get(1));
			ed.putString("ln",al.get(2));
			ed.putString("gplus",al.get(3));
			ed.commit();
		} else if (tvname.equals("upendra")) {
			c = myDbHelper.sriram(tvname, null, null, null, null, null, null);

			if (c.moveToFirst()) {
				do {
					String link = c.getString(2);
					Log.v("links", "" + c.getString(2));
					al.add(c.getString(2));

				} while (c.moveToNext());
			}
			fb.setText(al.get(0));
			twt.setText(al.get(1));
			ln.setText(al.get(2));
			gplus.setText(al.get(3));
			ed.putString("fb",al.get(0));
			ed.putString("twt",al.get(1));
			ed.putString("ln",al.get(2));
			ed.putString("gplus",al.get(3));
			ed.commit();
		}
		// Toast.makeText(Display.this,""+al,Toast.LENGTH_LONG).show();
		// Log.v("al",""+al);
		// Toast.makeText(Display.this,""+al.get(0),Toast.LENGTH_LONG).show();
		// Toast.makeText(Display.this,""+al.get(1),Toast.LENGTH_LONG).show();
		// Toast.makeText(Display.this,""+al.get(2),Toast.LENGTH_LONG).show();
		// Toast.makeText(Display.this,""+al.get(3),Toast.LENGTH_LONG).show();

		// myDbHelper.openDataBase();
		// c = myDbHelper.query1();
		//
		// if (c.moveToFirst()) {
		// do {
		// Log.v("id",
		// " " + c.getString(0));
		//
		// if(tvname.equals(c.getString(0))) {
		//
		// table=c.getString(0);
		// Log.v("table","name"+table);
		// a=0;
		// break;
		// }
		// } while (c.moveToNext());
		// }
		// if(a==0) {
		// myDbHelper.openDataBase();
		// c = myDbHelper.query(table, null, null, null, null,
		// null, null);
		// if (c.moveToFirst()) {
		// do {
		// // Log.v("links","lnks"+c.getString(0)+"  "+c.getString(1));
		// } while (c.moveToNext());
		// }
		// }

		name.setText(tvname);
		phone.setText(tvphone);
		country.setText(tvcountry);
		email.setText(tvemail);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getBaseContext(), Grid.class);
				startActivity(in);
			}
		});
	}
//public Bitmap fbh(Bitmap p){
//	Bitmap bitmap;
//	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
//            .getHeight(), Config.ARGB_8888);
//    Canvas canvas = new Canvas(output);
//
//    final int color = 0xff424242;
//    final Paint paint = new Paint();
//    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//    final RectF rectF = new RectF(rect);
//    final float roundPx = pixels;
//
//    paint.setAntiAlias(true);
//    canvas.drawARGB(0, 0, 0, 0);
//    paint.setColor(color);
//    canvas.drawRoundRect(recstF, roundPx, roundPx, paint);
//
//    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//    canvas.drawBitmap(bitmap, rect, rect, paint);
//
//    return output;
//}
	
}
