package hu.tool;

import static hu.dataclass.Content.*;

import hu.dataclass.Content;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myphone.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyHandler extends Handler {

	private Context context;
	private ArrayList<HashMap<String, Object>> arrayList;
	private SimpleAdapter simpleAdapter;

	public MyHandler(Context context, SimpleAdapter simpleAdapter,
			ArrayList<HashMap<String, Object>> arrayList) {
		// TODO Auto-generated constructor stub
		// 将对象传入，用于更新数据
		this.context = context;
		this.simpleAdapter = simpleAdapter;
		this.arrayList = arrayList;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what) {
		case RESPOND_CATALOGUE:
			System.out.println(RESPOND_CATALOGUE);
			// Toast.makeText(context, RESPOND_CATALOGUE, Toast.LENGTH_LONG)
			// .show();
			// // 更新文件目录
			// arrayList.clear();
			// simpleAdapter.notifyDataSetChanged();
			// 将字符串解析到列表中
			String s = (String) msg.obj;
			arrayList.clear();

			if (s != null) {
				String nameString[] = s.split(SPLITE_NAME);
				// TODO 分析字符串第一个字符，选择是否要改变图标

				for (int i = 0; i < nameString.length; i++) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();

					if ('?' == nameString[i].charAt(0)) {
						hashMap.put(
								ITEM_TIITLE,
								nameString[i].substring(1,
										nameString[i].length()));
						hashMap.put(ITEM_IMAGE, R.drawable.folder);
						hashMap.put(ITEM_TEXT, FOLDER);
					} else {
						hashMap.put(ITEM_TIITLE, nameString[i]);
						hashMap.put(ITEM_IMAGE, R.drawable.file);
						hashMap.put(ITEM_TEXT, FILE);
					}

					arrayList.add(hashMap);
				}

			}
			simpleAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
}
