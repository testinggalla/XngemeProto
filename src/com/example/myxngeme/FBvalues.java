package com.example.myxngeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FBvalues extends Activity {
	/** Called when the activity is first created. */
	EditText email, password;
	Cursor c = null;
	String username, phno, country, emailid, profilepic;
	int a;
	SharedPreferences sp;
	SharedPreferences.Editor ed;
	ImageView face;
	Boolean bp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fbvalues);
		face = (ImageView) findViewById(R.id.facebook);
		email = (EditText) findViewById(R.id.email);
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		email.setTypeface(font);
		password = (EditText) findViewById(R.id.password);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int wwidth = displaymetrics.widthPixels;
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) face
				.getLayoutParams();
		params.setMargins(0, height / 6, 0, height / 16); // substitute
															// parameters for
															// left, top, right,
															// bottom
		face.setLayoutParams(params);
		password.setTypeface(font);
		sp = getSharedPreferences("Androidsoft", 0);
		ed = sp.edit();
		((Button) findViewById(R.id.login))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						DatabaseHelper myDbHelper = new DatabaseHelper(
								FBvalues.this);
						// getting username and
						// password,phno,country,emailid,pic from database
						myDbHelper.openDataBase();
						c = myDbHelper.query("fblogin", null, null, null, null,
								null, null);
						if (c.moveToFirst()) {
							do {
								username = c.getString(3);
								String passw = c.getString(2);
								phno = c.getString(5);
								country = c.getString(7);
								emailid = c.getString(1);
								profilepic = c.getString(8);
								// comparing entered username and password equal
								// or not
								if (email.getText().toString().equals(username)
										&& password.getText().toString()
												.equals(passw)) {
									a = 0;
									break;

								}
								a = 1;
							} while (c.moveToNext());
							if (a == 0) {
								Intent i = new Intent(getBaseContext(),
										Display.class);
								ed.putString("username", username);
								ed.putString("phone", phno);
								ed.putString("country", country);
								ed.putString("emailid", emailid);
								ed.putString("profilepic", profilepic);
								ed.commit();
								startActivity(i);
								// sending the boolean values for login through shared preferences
								bp = true;

								SharedPreferences spf = getSharedPreferences(
										"Sample", 0);
								SharedPreferences.Editor se = spf.edit();
								se.putBoolean("boolean", bp);
								se.commit();
								finish();
							} else {
								// creating dialog for authentication failure
								AlertDialog.Builder alert = new AlertDialog.Builder(
										FBvalues.this);

								alert.setTitle("Error");
								alert.setMessage("Authentication Failure");

								alert.setPositiveButton("ok",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												dialog.dismiss();

											}
										});

								alert.show();
							}
						}
						myDbHelper.close();
					}
				});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(getBaseContext(), MainActivity.class);
		startActivity(i);

	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		password.setText("");
		email.setText("");

	}
}