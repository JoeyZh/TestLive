package com.taixin.boxassistant.home.adapter;


import com.taixin.test.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private String[] mMenuListString;
	private int mResourceIdArray[];

	private Context mContext;

	public MenuAdapter(Context context, String text[], int resid[]) {
		mContext = context;
		mMenuListString = text;
		mResourceIdArray = resid;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mMenuListString==null)
			return 0;
		else 
			return mMenuListString.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		Log.i("", "");
		LayoutInflater inflater = LayoutInflater.from(mContext);
		try {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.home_menu_item, null);

				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.home_menu_item_textview);
				holder.icon = (ImageView) convertView.findViewById(R.id.home_menu_item_icon);
				convertView.setTag(holder);
				holder.text.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}
			if (position >= mMenuListString.length)
				return convertView;
			holder.text.setText(mMenuListString[position]);
			if ((mResourceIdArray != null)
					&& (position < mResourceIdArray.length))
				holder.icon.setImageResource(mResourceIdArray[position]);
		} catch (Exception e) {
			Log.e("", "error = " + e.getMessage());
		}
		return convertView;

	}

	class ViewHolder {
		TextView text;
		ImageView icon;
	}

}
