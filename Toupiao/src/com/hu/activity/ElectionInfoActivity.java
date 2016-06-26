package com.hu.activity;

import static com.hu.DataClass.Content.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hu.DataClass.All;
import com.hu.DataClass.Election;
import com.hu.DataClass.Student;
import com.hu.toupiao.R;
import com.hu.ui.SpecialAdapter;

public class ElectionInfoActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private ArrayList<HashMap<String, String>> arrayList;
	private TextView winnerNumTextView;
	private EditText et_eid;
	private EditText et_name;
	private EditText et_date;
	private Button bt_ok, bt_cancel, bt_editAndOK;
	private Election election;
	private int mode, winnerNum;
	private String oldEid;
	private ListView listView;
	private SimpleAdapter listAdapter;
	private RelativeLayout relativeLayout;
	private ArrayList<Student> winneridArrayList;
	private Handler handler;
	public All all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setTitle("选举信息");
		setContentView(R.layout.activity_electioninfo);
		all = (All) getApplication();
		getWidget();
		dealListview();
		getInfo();
		showTextInEt();
	}

	private void getInfo() {
		Intent intent = getIntent();
		mode = intent.getExtras().getInt("mode");
		// Bundle bundle=intent.getExtras();
		if (ADD == mode) {
			oldEid = all.dbManager.getMaxEid() + 1 + "";
			// winneridArrayList = new ArrayList<Student>();
			election = new Election(oldEid);
		}
		if (EDIT == mode) {
			// student = (Student) intent.getSerializableExtra("student");
			oldEid = intent.getExtras().getString("eid");
			election = all.dbManager.queryElection(oldEid);
		}
		//TODO get Winners
		if (NETVOTE == mode) {
			arrayList= (ArrayList<HashMap<String,String>>)intent.getSerializableExtra("winner");
			
		}
		winneridArrayList = all.dbManager.getStudentInWinner(election.eid + "");
	}

	private Election getElection() {
		Election election = new Election();
		election.eid = Integer.parseInt(oldEid);
		election.name = et_name.getText().toString();
		election.date = et_date.getText().toString();
		return election;
	}

	private void getWidget() {
		handler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					freshlistview();
					break;

				default:
					break;
				}
			}

		};
		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		winnerNumTextView = (TextView) findViewById(R.id.tv_winnerNum);
		et_eid = (EditText) findViewById(R.id.et_eid);
		et_name = (EditText) findViewById(R.id.et_name);
		et_date = (EditText) findViewById(R.id.et_date);
		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 判断sid合法性
				// 1、将数据加入数据库2、更新ListView
				// Toast.makeText(SInfoActivity.this, mode, Toast.LENGTH_LONG)
				// .show();
				Election election = getElection();
				if (ADD == mode) {
					all.dbManager.addElection(election);
					Intent intent = new Intent();
					intent.putExtra("name", election.name);
					intent.putExtra("date", election.date);
					intent.putExtra("eid", oldEid);
					setResult(ADD, intent);
					finish();
				} else if (EDIT == mode) {
					all.dbManager.updateElection(election);
					Intent intent = new Intent();
					intent.putExtra("eid", oldEid);
					intent.putExtra("name", election.name);
					intent.putExtra("date", election.date);
					setResult(EDIT, intent);
					finish();
				}
			}
		});
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// intent.putExtra("return", edit.getText().toString());
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		bt_editAndOK = (Button) findViewById(R.id.bt_editandok);
		bt_editAndOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (View.VISIBLE == relativeLayout.getVisibility()) {
					// TODO 当前显示编辑，转入编辑模式，可以编辑当选人
					relativeLayout.setVisibility(View.GONE);
					bt_editAndOK.setText("确定");
					Cursor cursor = all.dbManager.queryAll("student");
					reloadArrayList(cursor);
					new Thread(new Runnable() {
						public void run() {
							// try {
							// Thread.sleep(500);
							// } catch (InterruptedException e) {
							// // TODO Auto-generated catch block
							// e.printStackTrace();
							// }
							Message msg = new Message();
							msg.what = 0;
							handler.sendMessage(msg); // 告诉主线程执行任务
						}
					}).start();

				} else {
					// TODO 当前显示确定，转入查看模式，不能编辑当选人，但可以编辑其他选举信息
					relativeLayout.setVisibility(View.VISIBLE);
					all.dbManager.deleteEidinfoInWinner(election.eid + "");
					all.dbManager.addWinner(election.eid + "",
							winneridArrayList);
					bt_editAndOK.setText("编辑");
					ArrayList<Student> studentArrayList = all.dbManager
							.getStudentInWinner(election.eid + "");
					reloadArrayList(studentArrayList);
				}
			}
		});
		et_eid.setEnabled(false);
		et_eid.setFocusable(false);
		// et_name.setFocusable(false);
		// et_date.setFocusable(false);
	}

	private void showTextInEt() {
		if (ADD == mode) {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			String date = calendar.get(Calendar.YEAR) + "-" + month + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
			et_date.setText(date);
			et_eid.setText(oldEid);
		} else {
			et_eid.setText(election.eid + "");
			et_name.setText(election.name);
			et_date.setText(election.date);
			reloadArrayList(winneridArrayList);
			winnerNum = winneridArrayList.size();
			winnerNumTextView.setText(winnerNum + "");
		}
	}

	private void reloadArrayList(ArrayList<Student> studentArrayList) {
		// TODO 刷新Listview中的数据
		arrayList.clear();
		int length = studentArrayList.size();
		for (int i = 0; i < length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("row0", studentArrayList.get(i).name);
			map.put("row1", studentArrayList.get(i).classPost);
			map.put("row2", studentArrayList.get(i).sid);
			arrayList.add(map);
		}
		listAdapter.notifyDataSetChanged();
		// if (length == 0) {
		// Toast.makeText(ElectionInfoActivity.this, "未找到符合条件的内容",
		// Toast.LENGTH_LONG).show();
		// }
	}

	private void reloadArrayList(Cursor cursor) {
		// TODO 刷新Listview中的数据
		arrayList.clear();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("row0", cursor.getString(cursor.getColumnIndex("name")));
			map.put("row1",
					cursor.getString(cursor.getColumnIndex("classPost")));
			map.put("row2", cursor.getString(cursor.getColumnIndex("sid")));
			arrayList.add(map);
		}
		listAdapter.notifyDataSetChanged();
		if (cursor.getCount() == 0) {
			Toast.makeText(ElectionInfoActivity.this, "未找到符合条件的内容",
					Toast.LENGTH_LONG).show();
		}
	}

	private void dealListview() {
		// TODO 加载并配置Listview控件
		listView = (ListView) findViewById(R.id.listView_dangxuan);
		arrayList = new ArrayList<HashMap<String, String>>();
		listAdapter = new SpecialAdapter(this, arrayList, R.layout.list_item,
				new String[] { "row0", "row1", "row2", }, new int[] {
						R.id.tv_0, R.id.tv_1, R.id.tv_2 });
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (View.GONE == relativeLayout.getVisibility()) {
					// 非编辑模式下执行
					String sid = arrayList.get(arg2).get("row2");
					int num = ifInWinner(sid);
					if (-1 == num) {
						winneridArrayList.add(all.dbManager.queryStudent(sid));
						arg1.setBackgroundResource(R.drawable.listitem_red_down);
					} else {
						winneridArrayList.remove(num);
						if (0 == arg2 % 2) {
							arg1.setBackgroundResource(R.drawable.listitem_redstyle);
						} else {
							arg1.setBackgroundResource(R.drawable.listitem_bluestyle);
						}
					}
					winnerNumTextView.setText(winneridArrayList.size() + "");
				}
			}
		});
	}

	private int ifInWinner(String sid) {
		int length = winneridArrayList.size();
		for (int i = 0; i < length; i++) {
			if (winneridArrayList.get(i).sid.equals(sid)) {
				return i;// 返回值表明sid在arrayList中的位置
			}
		}
		return -1;// 返回-1说明没有找到
	}

	private void freshlistview() {
		int length = listView.getChildCount();
		int flag;
		for (int i = 0; i < length; i++) {
			String sid = arrayList.get(i).get("row2");
			flag = ifInWinner(sid);
			if (-1 != flag) {
				// View view = listAdapter.getView(i, null, listView);
				// view.setBackgroundResource(R.drawable.listitem_red_down);
				View view = listView.getChildAt(i);
				view.setBackgroundResource(R.drawable.listitem_red_down);
			}
		}
	}
}
