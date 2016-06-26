package com.hu.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.order_dishes.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class lvButtonAdapter extends BaseAdapter {
	private class buttonViewHolder {
		ImageView dishImage;
		TextView dishName;
		TextView dishtaste;
		TextView dishPrice;
		ImageButton buttonClose;
	}

	private TextView allMoneyTextView, dishNumTextView;
	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private buttonViewHolder holder;

	public lvButtonAdapter(Context c,
			ArrayList<HashMap<String, Object>> appList, int resource,
			String[] from, int[] to) {
		mAppList = appList;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];// 得到传过来的数据
		valueViewID = new int[to.length];// 得到传过来的ID
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
	}

	public void setTextView(TextView allMoney, TextView dishNum) {
		allMoneyTextView = allMoney;
		dishNumTextView = dishNum;
	}

	public int getCount() {
		return mAppList.size();
	}

	public Object getItem(int position) {
		return mAppList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void removeItem(int position) {
		mAppList.remove(position);
		this.notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			// 优化listview性能，使滑动更加流畅
			holder = (buttonViewHolder) convertView.getTag();

		} else {
			convertView = mInflater.inflate(R.layout.list_item_dishes, null);
			// 得到布局文件
			holder = new buttonViewHolder();
			holder.dishImage = (ImageView) convertView
					.findViewById(valueViewID[0]);
			holder.dishName = (TextView) convertView
					.findViewById(valueViewID[1]);
			holder.dishtaste = (TextView) convertView
					.findViewById(valueViewID[2]);
			holder.dishPrice = (TextView) convertView
					.findViewById(valueViewID[3]);
			holder.buttonClose = (ImageButton) convertView
					.findViewById(valueViewID[4]);
			convertView.setTag(holder);
		}

		HashMap<String, Object> appInfo = mAppList.get(position);
		if (appInfo != null) {
			int imageId = (Integer) appInfo.get(keyString[0]);
			String name = (String) appInfo.get(keyString[1]);
			String taste = (String) appInfo.get(keyString[2]);
			String price = (String) appInfo.get(keyString[3]);
			holder.dishImage.setImageDrawable(holder.dishImage.getResources()
					.getDrawable(imageId));
			holder.dishName.setText(name);
			holder.dishtaste.setText(taste);
			holder.dishPrice.setText(price);
			holder.buttonClose
					.setOnClickListener(new lvButtonListener(position));
		}
		return convertView;
	}

	class lvButtonListener implements OnClickListener {
		private int position;

		lvButtonListener(int pos) {
			position = pos;
		}

		public void onClick(View v) {
//			int vid = v.getId();
			// if (vid == holder.buttonClose.getId())
			int sum=Integer.parseInt(allMoneyTextView.getText().toString());
			sum-=Integer.parseInt(mAppList.get(position).get("price").toString());
			allMoneyTextView.setText(sum+"");
			removeItem(position);
			dishNumTextView.setText(getCount()+"");

		}
	}
}
