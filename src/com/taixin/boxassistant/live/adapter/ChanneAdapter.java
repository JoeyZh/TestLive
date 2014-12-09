package com.taixin.boxassistant.live.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class ChanneAdapter extends SimpleAdapter{
	
	private List<? extends Map<String, ?>> mMapList;
	private Context mContext;
	public ChanneAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		mContext = context;
		mMapList = data;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
//		if(view !=null)
//		{
//			view.findViewById(R.id.)
//		}
		
		return view;
	}


	public interface OnHideItemClickListener
	{
		static public final int FAV_TAG = 0;
		static public final int EPG_TAG = 1;
		static public final int MOVE_TAG = 2;
		void onClick(View v);
	}

}
