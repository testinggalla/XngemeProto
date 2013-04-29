package com.example.myxngeme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Display extends Activity{
Button save;
TextView name,phone,country,email;
String tvname,tvphone,tvcountry,tvemail,tvpic;
Bitmap bmp;
ImageView profilepic;
SharedPreferences ss;
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
		save=(Button) findViewById(R.id.save);
		name=(TextView) findViewById(R.id.name);
		phone=(TextView) findViewById(R.id.phone);
		country=(TextView) findViewById(R.id.country);
		email=(TextView) findViewById(R.id.mail);
		profilepic=(ImageView) findViewById(R.id.profilepic);
		
		ss = getSharedPreferences("Androidsoft", 0);
		tvname=ss.getString("username", null);
		tvphone=ss.getString("phone", null);
		tvcountry=ss.getString("country", null);
		tvemail=ss.getString("emailid", null);
		tvpic=ss.getString("profilepic", null);
//		 Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
//		    name.setTypeface(font);
		
		name.setText(tvname);
		phone.setText(tvphone);
		country.setText(tvcountry);
		email.setText(tvemail);
		
		
		
		
		
		
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getBaseContext(),last.class);
				startActivity(in);
			}
		});
	}

}
