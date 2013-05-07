package com.example.myxngeme;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FBvalues extends Activity {
	/** Called when the activity is first created. */
	EditText email, password;
	// Button login;
	Cursor c = null;
	String username, phno, country, emailid, profilepic;
	int a;
	SharedPreferences sp;
	SharedPreferences.Editor ed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fbvalues);
		email = (EditText) findViewById(R.id.email);
		Typeface font = Typeface.createFromAsset(getAssets(), "verdana.ttf");
		email.setTypeface(font);
		password = (EditText) findViewById(R.id.password);
		password.setTypeface(font);
		sp = getSharedPreferences("Androidsoft", 0);
		ed = sp.edit();
		// login = (Button) findViewById(R.id.login);
		((Button) findViewById(R.id.login))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						DatabaseHelper myDbHelper = new DatabaseHelper(
								FBvalues.this);

						myDbHelper.openDataBase();

						// Toast.makeText(FBvalues.this, "Success",
						// Toast.LENGTH_SHORT).show();

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
								finish();
							} else {

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
