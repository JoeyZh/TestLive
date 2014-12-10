package com.taixin.boxassistant.live.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taixin.android.utils.Constants;
import com.taixin.android.utils.Log;
import com.taixin.android.utils.SaveInstance;
import com.taixin.boxassistant.live.MainActivity;
import com.taixin.boxassistant.live.widget.SlideView;
import com.taixin.test.R;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ChannelAdapter extends SimpleAdapter {

	private List<? extends Map<String, ?>> mMapList;
	private Context mContext;
	private SlideView.OnSlideListener mSlideListener;
	private OnClickListener mOnClickListener;
	private DownloadTask mDownLoadTask = new DownloadTask();
	private String [] from;
	private int [] to;

	public ChannelAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		mContext = context;
		mMapList = data;
		this.from = from;
		this.to = to;
	}

	public void setOnSlideListenter(SlideView.OnSlideListener l) {
		mSlideListener = l;
	}

	public void setOnClickListener(OnClickListener l) {
		mOnClickListener = l;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = (SlideView) convertView;
		if (view == null) {
			view = new SlideView(mContext);
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View viewContent = inflater.inflate(R.layout.channel_item, null,
					true);
			((SlideView) view).setContentView(viewContent);
			((SlideView) view).setOnSlideListener(mSlideListener);

		}
		if (view.getTag() == null) {
			ViewHolder holder = new ViewHolder();

			holder.mFavView = view.findViewById(R.id.fav_view);
			holder.mEpgView = view.findViewById(R.id.epg_view);
			holder.mMoveView = view.findViewById(R.id.move_view);
			holder.mDragView = view.findViewById(R.id.drag_view);
			holder.mFavView.setOnClickListener(mOnClickListener);
			holder.mEpgView.setOnClickListener(mOnClickListener);
			holder.mMoveView.setOnClickListener(mOnClickListener);
			holder.mDragView.setOnClickListener(mOnClickListener);
			holder.position = position;
			view.setTag(holder);

		}
		Map map = mMapList.get(position);
		for(int i = 0;i<to.length;i++)
		{
			View current = view.findViewById(to[i]);
			if(from.length<=i)
				break;
			if(current instanceof TextView)
				((TextView)current).setText((String)map.get(from[i]));
			else if(current instanceof ImageView)
				((ImageView)current).setImageResource((Integer)map.get(from[i]));
				
		}
		return view;
	}

	public void changeData(List<? extends Map<String, ?>> data) {
		mMapList = data;
		if (data == null)
			return;
		for (int i = 0; i < data.size(); i++) {
			((HashMap<String, Object>) data.get(i)).put("flag", false);
		}
		notifyDataSetChanged();
		if (data != null)
			Log.i("", "data.size = " + data.size());
		mDownLoadTask.clear();
	}

	private class ViewHolder {
		View mFavView;
		View mEpgView;
		View mMoveView;
		View mDragView;
		int position;
	}

	private class GetCurrentEpgTask extends
			AsyncTask<Integer, Integer, String[]> {

		private int tsId;
		private int serviceId;
		private int position;

		@Override
		protected void onPostExecute(String result[]) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mMapList.size() <= position)
				return;
			HashMap<String, Object> item = (HashMap<String, Object>) mMapList
					.get(position);
			if (item == null || item.get("tsId") == null
					|| item.get("serviceId") == null)
				return;
			if (tsId != (Integer) item.get("tsId")
					|| serviceId != (Integer) item.get("serviceId"))
				return;
			if (!item.get("epg").equals(result[0])) {
				if (!result[0].equals("")) {
					item.put("epg", result[0]);
					item.put("next", result[1]);
					item.put("flag", true);
					notifyDataSetChanged();
				}
			}
			mDownLoadTask.poll();
		}

		@Override
		protected synchronized String[] doInBackground(final Integer... params) {
			// TODO Auto-generated method stub
			String epgStrings[] = { "", "" };
			if (params.length < 3) {
				Log.i("", "params.length = " + params.length);
				return epgStrings;
			}
			position = params[0];
			tsId = params[1];
			serviceId = params[2];
			String areaCode = SaveInstance.getInstance().getString("areacode",
					"");
			// TODO Auto-generated method stub
			// Log.i("", "Get EPG content from network");
			if (areaCode == "") {
				Log.d("", "区域获取失败");
				return epgStrings;
			}
			// Log.i("", "areaCode = " + areaCode);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date(System.currentTimeMillis());
			String time = dateFormat.format(date);
			time = time.substring(0, time.length() - 2);
			String URLGetEPG = Constants.IP_URL
					+ "egpManageAction.do?method=queryTimeChannel&tsID=" + tsId
					+ "&serviceID=" + serviceId + "&version=1&areaCode="
					+ areaCode + "&time=" + time;
			// Log.i("", "URLGetEPGCurrent:" + URLGetEPG);

			String ChanelResult = "";

			BufferedReader in = null;

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(URLGetEPG);
			HttpResponse response;

			try {
				response = client.execute(request);

				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				// String NL = System.getProperty("line.separator");

				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();

				ChanelResult = sb.toString();
				Log.i("", "Get EPG String========" + ChanelResult);
				try {
					JSONObject jsonrow = new JSONObject(ChanelResult);
					JSONArray array = jsonrow.getJSONArray("rows");
					for (int i = 0; i < ((array.length() < 2) ? array.length()
							: 2); i++) {
						JSONObject jsonObject = array.getJSONObject(i);
						String dateStr[] = jsonObject.getString("beginTime")
								.split(" ");
						String epg = jsonObject.getString("eventName_zh");
						epgStrings[i] = "";
						if (dateStr.length > 1 && dateStr[1].length() > 5) {
							epgStrings[i] = dateStr[1].substring(0, 5) + " "
									+ epg;
						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				// 网络请求失败，则提醒用户，网络连接出现异常
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				e.printStackTrace();
				Log.e("", "区域码非法：+" + areaCode);
			}
			return epgStrings;
		}
	}

	private class DownloadTask {
		
		private Queue<GetCurrentEpgTask> mDownloadTaskQueue = new LinkedList<GetCurrentEpgTask>();

		private int mMax = Integer.MAX_VALUE;

		public void setMax(int max) {
			mMax = max;
		}

		public void clear() {
			while (!mDownloadTaskQueue.isEmpty()) {
				poll();
			}
		}

		public void addTask(int position,int tsId,int serviceId) {
			GetCurrentEpgTask task = new GetCurrentEpgTask();
			while (mDownloadTaskQueue.size() > mMax) {
				poll();
			}
			mDownloadTaskQueue.add(task);
			task.execute(position,tsId,serviceId);
		}

		public GetCurrentEpgTask poll() {
			GetCurrentEpgTask item = mDownloadTaskQueue.poll();
			if (item != null)
				item.cancel(true);
			return item;
		}

		public GetCurrentEpgTask peek() {
			GetCurrentEpgTask item = mDownloadTaskQueue.peek();
			return item;
		}
	}

	public interface OnHideItemClickListener {
		static public final int FAV_TAG = 0;
		static public final int EPG_TAG = 1;
		static public final int MOVE_TAG = 2;
		static public final int DRAG_TAG = 3;

		void onClick(View v);
	}

}
