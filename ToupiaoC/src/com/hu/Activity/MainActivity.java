package com.hu.Activity;

import static com.hu.DataClass.Content.*;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hu.DataClass.NetInfo;
import com.hu.NetHelper.UDPHelper;
import com.hu.NetHelper.WifiHelper;
import com.hu.toupiaoc.R;

public class MainActivity extends Activity {

	private TextView tv_connect;
	private WifiHelper wifiHelper;
	private UDPHelper udpHelper;
	private Button connectButton;
	private Button sendButton;
	private ListView listView;
	private SimpleAdapter listAdapter;
	private ArrayList<HashMap<String, String>> arrayList;
	private ArrayList<String> winnerList;
	private Handler netHandler;
	private String gatewayString;
	private String[] name;
	private String imis;
	private int max;

	// private String phoneNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getInfo();
		getWidget();
		dealListview();
		setHandler();
		udpHelper = new UDPHelper(CLIENT_PORT, netHandler);
		tv_connect.setText("没有连接服务器！");

	}

	private void getWidget() {
		wifiHelper = new WifiHelper();
		tv_connect = (TextView) findViewById(R.id.tv_connect);
		connectButton = (Button) findViewById(R.id.bt_connect);
		connectButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gatewayString = wifiHelper.getGateWay(getApplication());
				// udpHelper.sendData("192.168.43.1", 4000, "yes");
				udpHelper.sendNetConnectHope(gatewayString, SERVEICE_PORT);

			}
		});
		sendButton = (Button) findViewById(R.id.bt_send);
		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (gatewayString == null) {
					Toast.makeText(MainActivity.this, "请连接服务器",
							Toast.LENGTH_LONG).show();
				} else if (0 == winnerList.size()) {
					Toast.makeText(MainActivity.this, "请投票后再发送",
							Toast.LENGTH_LONG).show();
				} else {

					String winners = winnerListToString();
					udpHelper.sendNetSendVote(gatewayString, SERVEICE_PORT,
							imis, winners);

				}
			}
		});

	}

	private String winnerListToString() {
		String winners = "";
		int length = winnerList.size();
		for (int i = 0; i < length; i++) {
			if (0 == i) {
				winners = winnerList.get(i);
			} else {
				winners = winners + SPLIT_INFO_SML + winnerList.get(i);
			}
		}
		return winners;
	}

	private void setHandler() {
		netHandler = new Handler() {
			// TODO netHandler
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				// 重新开始接收数据
				String all = (String) msg.obj;
				NetInfo netInfo = new NetInfo(all);
				Message message = new Message();
				message.what = netInfo.getTypeInt();
				switch (message.what) {
				case NET_CONNECT_OK:
					tv_connect.setText("连接成功！\n" + "服务器ip：" + gatewayString);
					new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							udpHelper.sendNetWantData(gatewayString,
									SERVEICE_PORT);
						}
					}.start();
					// connectButton.setText("刷新");
					// NET_CONNECT_OK;
					break;
				case NET_SEND_DATA:
					String infoBig = netInfo.getInfoBig();
					String s = netInfo.getInfoMidOne();
					max = Integer.parseInt(s);
					String temp = netInfo.getInfoMidTwo();
					name = temp.split(SPLIT_INFO_SML);
					reloadArrayList(name);
					// NET_SEND_VOTE;
					break;
				case NET_RECEIVED_OK:
					// TODO 需要处理收到的投票
					// NET_RECEIVED_OK
					Toast.makeText(MainActivity.this, "投票成功", Toast.LENGTH_LONG)
							.show();
					break;
				case NET_HAVE_VOTED:
					Toast.makeText(MainActivity.this, "请勿重复投票",
							Toast.LENGTH_LONG).show();
					break;
				}
			};
		};
	}

	private void reloadArrayList(String n[]) {
		arrayList.clear();
		int length = n.length;
		for (int i = 0; i < length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", n[i]);
			arrayList.add(map);
		}
		listAdapter.notifyDataSetChanged();
	}

	private void dealListview() {
		// TODO 加载并配置Listview控件
		listView = (ListView) findViewById(R.id.listView_student);
		arrayList = new ArrayList<HashMap<String, String>>();
		listAdapter = new SimpleAdapter(MainActivity.this, arrayList,
				R.layout.list_item, new String[] { "name" },
				new int[] { R.id.tv_name });

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 单击Listview条目时的处理函数
				int Maxtemp = winnerList.size();
				if (max > Maxtemp) {
					String name = arrayList.get(arg2).get("name");
					int existFlag = existInWinnerlist(name);
					if (existFlag >= 0) {
						arg1.setBackgroundColor(Color.WHITE);
						winnerList.remove(existFlag);
					} else {
						arg1.setBackgroundColor(Color.BLUE);
						winnerList.add(name);
					}
				} else {
					Toast.makeText(MainActivity.this, "所选人数达到上限",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		udpHelper.receiveRun();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		udpHelper.setreceiveRunFlag(false);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		udpHelper.close();
	}

	private void getInfo() {
		winnerList = new ArrayList<String>();
		gatewayString = null;
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		// phoneNum = telephonyManager.getLine1Number();
		// System.out.println("本机号码：" + phoneNum);
		// //SIM卡中没有手机号码信息，国内SIM卡中只有imis号

		imis = telephonyManager.getSubscriberId();
		System.out.println("imis:" + imis);
	}

	private int existInWinnerlist(String name) {
		int length = winnerList.size();
		for (int i = 0; i < length; i++) {
			if (winnerList.get(i).equals(name)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

}
