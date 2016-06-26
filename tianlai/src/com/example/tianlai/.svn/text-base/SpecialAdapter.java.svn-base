package com.example.tianlai;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class SpecialAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

	public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		int colorPos = position % colors.length;
		if (colorPos == 1) {
			view.setBackgroundColor(Color.argb(8, 8, 8, 8));
		} else {
			view.setBackgroundColor(Color.argb(250, 255, 255, 255));
		}
		return view;
	}

}
