package com.example.myxngeme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myxngeme.Display.MyData;
import com.example.myxngeme.Display.MyViewHolder;

/**
 * This list adapter is derived from the "Efficient List Adapter"-Example of
 * API-Demos. It uses holder object to access the list items efficiently.
 * Additionally, click listeners are provided, which can be connected to the
 * arbitrary view items, e.g. customized checkboxes, or other clickable
 * Image/TextViews. Implement subclasses of them and add your listeners to your
 * "clickable" views.
 * 
 * @author poss3x
 */
public abstract class ClickableListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List mDataObjects; // our generic object list
	private int mViewId;
	DatabaseHelper myDbHelper;
	SharedPreferences ss;
	SharedPreferences.Editor ed;
	Cursor c = null;
	Cursor cr = null;
	ArrayList<String> al_img;
	ArrayList<String> al_link;
	ArrayList<String> al_id;
	DBxngeme db;
	Bitmap myBitmap;
	static ImageView img;
	Bitmap mIconEnabled;
	Bitmap mIconDisabled;

	/**
	 * This is the holder will provide fast access to arbitrary objects and
	 * views. Use a subclass to adapt it for your personal needs.
	 */
	public static class ViewHolder {
		// back reference to our list object
		public Object data;
	}

	/**
	 * The click listener base class.
	 */
	public static abstract class OnClickListener implements
			View.OnClickListener {

		private ViewHolder mViewHolder;

		/**
		 * @param holder
		 *            The holder of the clickable item
		 */
		public OnClickListener(ViewHolder holder) {
			mViewHolder = holder;
		}

		// delegates the click event
		public void onClick(View v) {

			onClick(v, mViewHolder);
		}

		/**
		 * Implement your click behavior here
		 * 
		 * @param v
		 *            The clicked view.
		 */
		public abstract void onClick(View v, ViewHolder viewHolder);
	};

	/**
	 * The long click listener base class.
	 */
	//

	/**
	 * @param context
	 *            The current context
	 * @param viewid
	 *            The resource id of your list view item
	 * @param objects
	 *            The object list, or null, if you like to indicate an empty
	 *            list
	 */
	public ClickableListAdapter(Context context, int viewid, List objects) {

		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		mDataObjects = objects;
		mViewId = viewid;
		myDbHelper = new DatabaseHelper(context);
		db = new DBxngeme(context);
		al_img = new ArrayList<String>();
		al_id = new ArrayList<String>();
		al_link = new ArrayList<String>();
		if (objects == null) {
			mDataObjects = new ArrayList<Object>();
		}
		// open the database and get the image links
		myDbHelper.openDataBase();
		ss = context.getSharedPreferences("Androidsoft", 0);
		ed = ss.edit();
		String tvname = ss.getString("username", null);
		if (tvname.equals("sriram")) {

			c = myDbHelper.sriram(tvname, null, null, null, null, null, null);

			if (c.moveToFirst()) {
				do {

					al_img.add(c.getString(3));
				} while (c.moveToNext());
			}

		} else if (tvname.equals("upendra")) {
			c = myDbHelper.sriram(tvname, null, null, null, null, null, null);

			if (c.moveToFirst()) {
				do {
					al_img.add(c.getString(3));
				} while (c.moveToNext());
			}

		}

		c.close();
		myDbHelper.close();

	}

	// async task for loading image links and displayed in listview
	class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		int pos;
		ImageView image;
		Bitmap bmp;

		public BackgroundAsyncTask(int position, ImageView img, Bitmap myBitmap) {
			pos = position;
			image = img;
			bmp = myBitmap;

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {

				URL url = new URL(al_img.get(pos));
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
			super.onPostExecute(result);

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

	/**
	 * The number of objects in the list.
	 */
	public int getCount() {
		return mDataObjects.size();
	}

	/**
	 * @return We simply return the object at position of our object list Note,
	 *         the holder object uses a back reference to its related data
	 *         object. So, the user usually should use {@link ViewHolder#data}
	 *         for faster access.
	 */
	public Object getItem(int position) {
		return mDataObjects.get(position);
	}

	/**
	 * We use the array index as a unique id. That is, position equals id.
	 * 
	 * @return The id of the object
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Make a view to hold each row. This method is instantiated for each list
	 * object. Using the Holder Pattern, avoids the unnecessary
	 * findViewById()-calls.
	 */
	public View getView(int position, View view, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid uneccessary
		// calls
		// to findViewById() on each row.
		ViewHolder holder;

		// When view is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the view supplied
		// by ListView is null.
		if (view == null) {

			view = mInflater.inflate(mViewId, null);
			// call the user's implementation
			holder = createHolder(view, position);

			// we set the holder as tag
			view.setTag(holder);

		} else {
			// get holder back...much faster than inflate
			holder = (ViewHolder) view.getTag();

		}

		img = (ImageView) view.findViewById(R.id.listitem);
		new BackgroundAsyncTask(position, img, myBitmap).execute();

		// we must update the object's reference
		holder.data = getItem(position);

		// call the user's implementation
		bindHolder(holder, position);
		db.open();

		cr = db.getAllContacts();
		if (cr.moveToFirst()) {
			do {
				if (position == cr.getInt(0)) {
					mIconEnabled = BitmapFactory.decodeResource(
							view.getResources(), R.drawable.p);
					mIconDisabled = BitmapFactory.decodeResource(
							view.getResources(), R.drawable.tick);
					MyViewHolder mvh = (MyViewHolder) holder;
					MyData mo = (MyData) mvh.data;
					int value = cr.getInt(2);
					if (value == 0) {
						mo.enable = false;
					} else {
						mo.enable = true;
					}
					ImageView icon = (ImageView) view
							.findViewById(R.id.listitem_icon);
					icon.setImageBitmap(mo.enable ? mIconEnabled
							: mIconDisabled);
				}
			} while (cr.moveToNext());
		}
		cr.close();
		db.close();

		return view;
	}

	/**
	 * Creates your custom holder, that carries reference for e.g. ImageView
	 * and/or TextView. If necessary connect your clickable View object with the
	 * PrivateOnClickListener, or PrivateOnLongClickListener
	 * 
	 * @param vThe
	 *            view for the new holder object
	 */
	protected abstract ViewHolder createHolder(View v, int position);

	/**
	 * Binds the data from user's object to the holder
	 * 
	 * @param h
	 *            The holder that shall represent the data object.
	 */
	protected abstract void bindHolder(ViewHolder h, int positon);
}