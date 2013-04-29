package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
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
	TextView name, phone, country, email, fb, twt, gplus, ln;
	String tvname, tvphone, tvcountry, tvemail, tvpic;
	Bitmap bmp;
	ImageView profilepic;
	SharedPreferences ss;
	Cursor c = null;
	int a;
	String table;
	ArrayList<String> al;
	Bitmap myBitmap;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		al = new ArrayList<String>();
		DatabaseHelper myDbHelper = new DatabaseHelper(Display.this);
		save = (Button) findViewById(R.id.save);
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
		country = (TextView) findViewById(R.id.country);
		email = (TextView) findViewById(R.id.mail);
		profilepic = (ImageView) findViewById(R.id.profilepic);
		fb = (TextView) findViewById(R.id.fbname);
		twt = (TextView) findViewById(R.id.twtname);
		ln = (TextView) findViewById(R.id.lnname);
		gplus = (TextView) findViewById(R.id.gplusname);

		ss = getSharedPreferences("Androidsoft", 0);
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
		// Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		// name.setTypeface(font);
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
				Intent in = new Intent(getBaseContext(), last.class);
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
