package com.taixin.boxassistant.live;

import java.util.ArrayList;
import java.util.List;

import com.taixin.boxassistant.live.widget.ListViewCompat;
import com.taixin.boxassistant.live.widget.SlideView;
import com.taixin.boxassistant.live.widget.SlideView.OnSlideListener;
import com.taixin.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener {

    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;
    SlideAdapter slideAdapter;
    private List<MessageItem> mMessageItems = new ArrayList<MainActivity.MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (ListViewCompat) findViewById(R.id.list);

        for (int i = 0; i < 10; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.drawable.default_qq_avatar;
                item.title = "腾讯新闻";
                item.msg = "青岛爆炸满月：大量鱼虾死亡";
                item.time = "晚上18:18";
            } else {
                item.iconRes = R.drawable.wechat_icon;
                item.title = "微信团队";
                item.msg = "欢迎你使用微信";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        
//        footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
//        mListView.addFooterView(footer);
        slideAdapter = new SlideAdapter();
        mListView.setAdapter(slideAdapter);
        mListView.setOnItemClickListener(this);
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(MainActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(MainActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.reset();
            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.leftHolder.setOnClickListener(MainActivity.this);
            holder.rightHolder.setOnClickListener(MainActivity.this);
            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup leftHolder;
        public ViewGroup rightHolder;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            leftHolder = (ViewGroup)view.findViewById(R.id.left_holder);
            rightHolder = (ViewGroup)view.findViewById(R.id.right_holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Log.e(TAG, "onItemClick position=" + position);
        SlideView slideView =  mMessageItems.get(position).slideView;
        if(slideView.ismIsMoveClick()){//如果是滑动中触发的点击事件，不做处理
        	return;
        }
        if (slideView.close() && slideView.getScrollX() == 0) {
			if (mLastSlideViewWithStatusOn == null || mLastSlideViewWithStatusOn.getScrollX() == 0) {
				//此处添加item的点击事件
				Toast.makeText(this, "onItemClick position=" + position, 0).show();
			}
		}
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
    	if (v.getId() == R.id.left_holder) {
            Log.e(TAG, "onClick v=" + v);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示").setIcon(R.drawable.ic_launcher).setMessage("确定删此条目？")  
					.setNegativeButton("取消", null);
			builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mMessageItems.remove(mListView.getPosition());
					slideAdapter.notifyDataSetChanged();
				}
			});
			builder.show();
        }else if (v.getId() == R.id.right_holder) {
            Log.e(TAG, "onClick v=" + v);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示").setIcon(R.drawable.ic_launcher).setMessage("确定删此条目？")  
					.setNegativeButton("取消", null);
			builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mMessageItems.remove(mListView.getPosition());
					slideAdapter.notifyDataSetChanged();
				}
			});
			builder.show();
        }
    }
}
