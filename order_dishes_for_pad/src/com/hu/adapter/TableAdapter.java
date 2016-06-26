package com.hu.adapter;

import static com.hu.dataclass.Content.STATE_FREE;
import static com.hu.dataclass.Content.STATE_IN_USE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.order_dishes.R;

public class TableAdapter extends SimpleAdapter {

	public ArrayList<HashMap<String, Object>> tableGridArraList;

	public TableAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = super.getView(position, convertView, parent);

		TextView tableStateTextView = (TextView) view
				.findViewById(R.id.tv_tablestate);
		String stateString = tableStateTextView.getText().toString();
		RelativeLayout relativeLayout = (RelativeLayout) view
				.findViewById(R.id.rl_background);
		if (stateString.equals(STATE_FREE)) {
			relativeLayout.setBackgroundColor(Color.rgb(33, 181, 229));
		} else if (stateString.equals(STATE_IN_USE)) {
			relativeLayout.setBackgroundColor(Color.rgb(99, 204, 00));
		} else
		{
			relativeLayout.setBackgroundColor(Color.rgb(66, 99, 00));
		}
		return view;
	
	}

}
