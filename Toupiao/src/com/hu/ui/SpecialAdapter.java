package com.hu.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.hu.toupiao.R;

public class SpecialAdapter extends SimpleAdapter {
//	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

//	public SpecialAdapter(Context context, List<HashMap<String, String>> items,
//			int resource, String[] from, int[] to) {
//		super(context, items, resource, from, to);
//	}

	public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		// int colorPos = position % colors.length;
//		if (0 == position % 2) {
//			view.setBackgroundResource(R.drawable.listitem_redstyle);
//		} else {
//			view.setBackgroundResource(R.drawable.listitem_bluestyle);
//		}
		return view;
	}
}