package com.hu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.order_dishe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class TableActivity extends Activity {

	private GridView gridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables);
		getViews();
	}

	private void getViews() {
		gridView = (GridView) findViewById(R.id.gridView1);
		// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("ItemImage", R.drawable.ic_action_search);// ���ͼ����Դ��ID
			map.put("tablenum", String.valueOf(i)+"����");// �������ItemText
			lstImageItem.add(map);
		}
		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, // ûʲô����
				lstImageItem,// ������Դ
				R.layout.grid_item_tables,// night_item��XMLʵ��

				// ��̬������ImageItem��Ӧ������
				new String[] { "tablenum" },

				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.tv_tableNum });
		// ��Ӳ�����ʾ
		gridView.setAdapter(simpleAdapter);
		// �����Ϣ����
		gridView.setOnItemClickListener(new ItemClickListener());
	}

	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// ��ʾ��ѡItem��ItemText
			setTitle((String) item.get("ItemText"));
		}

	}
}
