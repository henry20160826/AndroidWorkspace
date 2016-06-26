package com.hu.activity;

import static com.hu.DataClass.Content.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hu.DataClass.All;
import com.hu.DataClass.NetInfo;
import com.hu.NetHelper.UDPHelper;
import com.hu.toupiao.R;
import com.hu.toupiao.R.color;
import com.hu.ui.SpecialAdapter;

public class NewElectionActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private TextView tv_num;
	private Button bt_add, bt_sub, bt_ok, bt_start;
	private UDPHelper udpHelper;
	private Handler netHandler, UIHandler;
	private String studentsName;
	private ListView listView;
	private All all;
	private Boolean flagOfBt;
	private ArrayList<HashMap<String, String>> arrayList;
	private SimpleAdapter listAdapter;
	private ArrayList<String> voter, winner;
	private int winnerNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 创建Activity
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newelection);
		all = (All) getApplication();
		studentsName = new String();
		studentsName = all.dbManager.queryAllStudentName();
		getWidget();
		getInfo();

		setHandler();
		// SMSReceiver receiver = new SMSReceiver();
		// getSmsInfo();
		udpHelper = new UDPHelper(SERVEICE_PORT, netHandler);
		dealListview();
		Cursor cursor = all.dbManager.queryAll("student");
		reloadArrayList(cursor);
	}

	private void getInfo() {
		voter = new ArrayList<String>();
		winner = new ArrayList<String>();
		String temp = tv_num.getText().toString();
		winnerNum = Integer.parseInt(temp);
	}

	private boolean existInVoter(String name) {
		int length = voter.size();
		for (int i = 0; i < length; i++) {
			if (voter.get(i).equals(name)) {
				return true;
			}
		}
		return false;
	}

	// public void getSmsInfo() {
	// // TODO 获得未读的Sms
	// String[] projection = new String[] { "_id", "address", "person",
	// "body", "date", "type" };
	// Uri uri = Uri.parse(SMS_URI_ALL);
	// String selection = "type = 1 and read = 0";// 短信而且未读
	// Cursor cursor = managedQuery(uri, projection, selection, null,
	// "date desc");
	// int nameColumn = cursor.getColumnIndex("person");
	// int phoneNumberColumn = cursor.getColumnIndex("address");
	// int smsbodyColumn = cursor.getColumnIndex("body");
	// int dateColumn = cursor.getColumnIndex("date");
	// int typeColumn = cursor.getColumnIndex("type");
	// if (0 != cursor.getCount()) {
	// SmsInfo smsinfo = new SmsInfo();
	// while (cursor.moveToNext()) {
	// smsinfo.setName(cursor.getString(nameColumn));
	// smsinfo.setDate(cursor.getString(dateColumn));
	// smsinfo.setPhoneNumber(cursor.getString(phoneNumberColumn));
	// smsinfo.setSmsbody(cursor.getString(smsbodyColumn));
	// smsinfo.setType(cursor.getString(typeColumn));
	// break;
	// }
	// textView.setText(smsinfo.getPhoneNumber());
	// cursor.close();
	// } else {
	// textView.setText("没有未读信息");
	// }
	//
	// }

	public boolean checkSms(Cursor cursor) {
		// TODO 鉴权，判断是否有投票权限
		// 不能在Cursor中删除对象，所以一条一条处理短信
		return false;
	}

	public void analysisSms(Cursor cursor) {
		// TODO 解析短信
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		udpHelper.setReceiveRunFlag(false);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		udpHelper.close();
	}

	public void addVote(String phoneNum, String vote) {
		// TODO 将新的票添加到显示
		voter.add(phoneNum);
		String name[] = vote.split(SPLIT_INFO_SML);
		int lengthOfArrayList = arrayList.size();
		int lengthOfVote = name.length;
		int lengthOfmin = winnerNum < lengthOfVote ? winnerNum : lengthOfVote;
		for (int i = 0; i < lengthOfArrayList; i++) {
			String nameString = arrayList.get(i).get("row0").toString();
			for (int j = 0; j < lengthOfmin; j++) {
				if (nameString.equals(name[j])) {
					int num = Integer.parseInt(arrayList.get(i).get("row1")
							.toString());
					num++;
					arrayList.get(i).put("row1", num + "");
				}
			}
		}
		sortArraylist();
		listAdapter.notifyDataSetChanged();
		Message message = new Message();
		message.what = UPDATE_LISTVIEW;
		UIHandler.sendMessage(message);
	}

	private void getWidget() {
		// TODO 得到Activity所需的控件
		tv_num = (TextView) findViewById(R.id.tv_num);
		// button = (Button) findViewById(R.id.button1);
		// button.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// System.out.println("button down");
		// Intent intent = new Intent();
		// intent.setAction(Intent.ACTION_EDIT);
		// NewElectionActivity.this.sendBroadcast(intent);
		// }
		// });
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_add.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				flagOfBt = true;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							while (flagOfBt) {
								try {
									sleep(200);
									Message msg = new Message();
									msg.what = NUM_ADD;
									UIHandler.sendMessage(msg);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();

				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					flagOfBt = false;
				}
				return false;
			}
		});
		bt_sub = (Button) findViewById(R.id.bt_sub);
		bt_sub.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				flagOfBt = true;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							while (flagOfBt) {
								try {
									sleep(200);
									Message msg = new Message();
									msg.what = NUM_SUB;
									UIHandler.sendMessage(msg);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					flagOfBt = false;
				}
				return false;
			}
		});
		bt_start = (Button) findViewById(R.id.bt_start);
		bt_start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (false == udpHelper.getReceiveRunFlag()) {
					bt_add.setEnabled(false);
					bt_sub.setEnabled(false);
					udpHelper.receiveRun();// 开始监听
					bt_start.setText("停止");
				} else {
					bt_add.setEnabled(true);
					bt_sub.setEnabled(true);
					udpHelper.setReceiveRunFlag(false);
					bt_start.setText("开始");
				}
			}
		});
		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(NewElectionActivity.this)
						.setTitle("提示")
						.setMessage("是否结束选举进入保存？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (winner.isEmpty()) {
										} else {
											Intent intent = new Intent();
											intent.setClass(
													NewElectionActivity.this,
													ElectionInfoActivity.class);
											Bundle bundle = new Bundle();
											bundle.putSerializable("winner",
													winner);
											intent.putExtras(bundle);
											intent.putExtra("mode", NETVOTE);
											startActivity(intent);
										}
										finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();
			}
		});
	}

	private void setHandler() {
		netHandler = new Handler() {
			// TODO netHandler
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);

				// textviewHandler.sendMessage(msg);
				String all = (String) msg.obj;
				NetInfo netInfo = new NetInfo(all);
				int type = netInfo.getTypeInt();
				String ip = udpHelper.getSourceIP();
				switch (type) {
				case NET_CONNECT_HOPE:

					udpHelper.sendNetConnectOk(ip, CLIENT_PORT);
					// NET_CONNECT_OK;
					break;
				case NET_WANT_DATA:

					udpHelper.sendNetSendData(ip, CLIENT_PORT, winnerNum + "",
							studentsName);
					// NET_SEND_VOTE;
					break;
				case NET_SEND_VOTE:
					// TODO 需要处理收到的投票
					// NET_RECEIVED_OK
					String phoneNum = netInfo.getInfoMidOne();
					String vote = netInfo.getInfoMidTwo();
					if (!existInVoter(phoneNum)) {
						addVote(phoneNum, vote);
						udpHelper.sendNetReceivedOk(ip, CLIENT_PORT);
					} else {

						udpHelper.sendNetHaveVoted(ip, CLIENT_PORT);
					}
					// 处理收到的投票
					break;
				}
			};
		};
		UIHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case NUM_ADD:
					tvNumAdd();
					break;
				case NUM_SUB:
					tvNumSub();
					break;
				case UPDATE_LISTVIEW:
					updateListview();
					break;
				}
			}
		};
	}

	private void updateListview() {
		int length = arrayList.size();
		int min = winnerNum < length ? winnerNum : length;
		for (int i = 0; i < min; i++) {
			listView.getChildAt(i).setBackgroundColor(color.androidblue);
		}
	}

	private void dealListview() {
		// TODO 加载并配置Listview控件
		listView = (ListView) findViewById(R.id.listView1);
		arrayList = new ArrayList<HashMap<String, String>>();
		listAdapter = new SpecialAdapter(this, arrayList, R.layout.list_item,
				new String[] { "row0", "row1", }, new int[] { R.id.tv_0,
						R.id.tv_2 });
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 单击Listview条目时的处理函数
			}
		});
	}

	private void reloadArrayList(Cursor cursor) {
		// TODO 刷新Listview中的数据
		arrayList.clear();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("row0", cursor.getString(cursor.getColumnIndex("name")));
			map.put("row1", "0");
			arrayList.add(map);
		}
		listAdapter.notifyDataSetChanged();
	}

	private void tvNumAdd() {
		if (winnerNum < 99) {
			winnerNum++;
		}
		tv_num.setText(winnerNum + "");
	}

	private void tvNumSub() {
		if (winnerNum > 1) {
			winnerNum--;
		}
		tv_num.setText(winnerNum + "");
	}

	private void sortArraylist() {

		// add Data.....

		if (!arrayList.isEmpty()) {
			// Collections.sort(mArrayList, new Comparator<Map<String,
			// Object>>() {
			// @Override
			// public int compare(Map<String, Object> object1,
			// Map<String, Object> object2) {
			// //我把obejct1 和obejct2写倒了，这样写的用处就是反向排序。
			// int i = object2.get("LEVEL").toString()</pre>
			// .compareTo(object1.get("LEVEL").toString());
			// if (i == 0) { //如果"LEVEL"字段相同，再根据下一字段排序
			// object1.get("TITLE").toString()
			//
			// .compareTo(object2.get("TITLE").toString());
			// }
			// return i;
			// }
			// });
			Collections.sort(arrayList,
					new Comparator<HashMap<String, String>>() {

						@Override
						public int compare(HashMap<String, String> lhs,
								HashMap<String, String> rhs) {
							// TODO Auto-generated method stub
							// int i=lhs.get("row1").toString().to;
							int l = Integer
									.parseInt(lhs.get("row1").toString());
							int r = Integer
									.parseInt(rhs.get("row1").toString());
							return l > r ? -1 : 1;
						}
					});

		}
	}

}
