package com.example.myxngeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBxngeme {

	public static final String KEY_LINKS = "links";
	public static final String KEY_IMG = "img";
	public static final String DATA_NAME = "MYDB";
	public static final String DATA_TABLE = "MR";
	public static final int DATA_VERSION = 5;
	public static final String DATA_CREATE = "create table MR(id integer primary key ,links String,enable boolean,img String,name String)";
	private final Context con;
	private DatabaseHelper dhelp;
	private SQLiteDatabase db;

	public DBxngeme(Context c) {
		this.con = c;
		dhelp = new DatabaseHelper(con);
	}

	public static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String TAG = null;

		public DatabaseHelper(Context cont) {
			super(cont, DATA_NAME, null, DATA_VERSION);
		}

		// create tables
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATA_CREATE);

		}

		// upgrade table values
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS MR");
			onCreate(db);
		}

	}

	// open database
	public DBxngeme open() {
		db = dhelp.getWritableDatabase();
		return this;
	}

	// close database
	public void close() {
		dhelp.close();
	}

	// inserting values in to MR table
	public long insertContact(int id, String links, Boolean enable, String img,
			String name) {
		ContentValues invalue = new ContentValues();
		invalue.put("id", id);
		invalue.put("links", links);
		invalue.put("img", img);
		invalue.put("enable", enable);
		invalue.put("name", name);
		return db.insert(DATA_TABLE, null, invalue);
	}

	// geting all contacts from MR table
	public Cursor getAllContacts() {
		return db.query(DATA_TABLE, new String[] { "id", "links", "enable",
				"img", "name" }, null, null, null, null, null);
	}

	// deleting no.of rows from MR table
	public void delete(long rowid) {
		db.delete(DATA_TABLE, "id=" + rowid, null);

	}

	// deleting all rows from MR table
	public void deleteallContacts() {
		db.delete(DATA_TABLE, null, null);

	}

}