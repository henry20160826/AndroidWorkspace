package com.hu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.order_dishes.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

public class ListViewAdapter extends SimpleAdapter {

	private ArrayList<HashMap<String, Object>> mArrayList;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	public void removeItem(int position) {
		mArrayList.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		return super.getView(position, convertView, parent);

	}

	public ListViewAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {

		super(context, data, resource, from, to);
		mArrayList = (ArrayList<HashMap<String, Object>>) data;
		// TODO Auto-generated constructor stub
	}

	class DeleteButtonOnClickListener implements View.OnClickListener {
		private int position;

		DeleteButtonOnClickListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			removeItem(position);
		}

	}
}
