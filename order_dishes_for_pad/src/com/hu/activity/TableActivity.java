package com.hu.activity;

import static com.hu.dataclass.Content.*;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_dishes.R;
import com.hu.adapter.TableAdapter;
import com.hu.udp.UDP;

public class TableActivity extends Activity {

	private GridView gridView;
	private ImageButton backButton;
	private TextView usernameTextView, stateTextView;
	private ArrayList<HashMap<String, Object>> tableGridArraList;
	private TableAdapter tableAdapter;
	private String waiterName;
	private Handler myHandler;
	private UDP udp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables);
		getViews();
		// updateGridView();
	}

	private void getViews() {
		usernameTextView = (TextView) findViewById(R.id.tv_username);
		stateTextView = (TextView) findViewById(R.id.tv_state);

		waiterName = getIntent().getExtras().getString(WAITER_NAME);
		usernameTextView.setText(waiterName + SERVER_FOR_YOU);
		stateTextView.setText(CONNECTION);
		backButton = (ImageButton) findViewById(R.id.bt_back);
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		getGridView();
		// myHandler = new Handler() {
		// @Override
		// public void handleMessage(Message msg) {
		// // TODO Auto-generated method stub
		// super.handleMessage(msg);
		// switch (msg.what) {
		// case 2:
		//
		// break;
		// default:
		// // Toast.makeText(TableActivity.this, "�û������������",
		// // Toast.LENGTH_SHORT).show();
		// break;
		// }
		// }
		// };
		// udp = new UDP(myHandler);
		// udp.receiveRun();
	}

	private void getGridView() {
		gridView = (GridView) findViewById(R.id.gridView1);

		// ���ɶ�̬���飬����ת������
		tableGridArraList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// ���ͼ����Դ��ID
			map.put("tablenum", String.valueOf(i + 1) + "����");// �������ItemText
			if (i == 5) {
				map.put("tablestate", STATE_IN_USE);
			} else {
				map.put("tablestate", STATE_FREE);
			}
			tableGridArraList.add(map);
		}

		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		tableAdapter = new TableAdapter(this, // ûʲô����
				tableGridArraList,// ������Դ
				R.layout.grid_item_tables,// XMLʵ��

				// ��̬������ImageItem��Ӧ������
				new String[] { "tablenum", "tablestate" },

				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.tv_tablenum, R.id.tv_tablestate });
		// ��Ӳ�����ʾ
		gridView.setAdapter(tableAdapter);
		// �����Ϣ����
		gridView.setOnItemClickListener(new ItemClickListener());
	}

	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			// HashMap<String, Object> item = (HashMap<String, Object>) arg0
			// .getItemAtPosition(arg2);
			// // ��ʾ��ѡItem��ItemText
			// setTitle(arg2+1+"");
			TextView stateTextView = (TextView) arg1
					.findViewById(R.id.tv_tablestate);
			String stateString = stateTextView.getText().toString();
			Bundle bundle = new Bundle();
			bundle.putInt(TABLE_NUM, arg2 + 1);
			bundle.putString(WAITER_NAME, waiterName);
			Intent intent = new Intent();
			intent.putExtras(bundle);
			if (stateString.equals(STATE_IN_USE)) {
				intent.setClass(TableActivity.this, BillActivity.class);
				startActivity(intent);

			} else if (stateString.equals(STATE_FREE)) {

				intent.setClass(TableActivity.this, DishesActivity.class);
				startActivity(intent);
			} else if (stateString.equals(STATE_ORDER)) {

			}
		}
	}
}
