package com.hu.activity;

import static com.hu.DataClass.Content.ADD;
import static com.hu.DataClass.Content.EDIT;
import static com.hu.DataClass.Content.ELECTION;
import static com.hu.DataClass.Content.STUDENT;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.hu.DataClass.All;
import com.hu.toupiao.R;
import com.hu.ui.SpecialAdapter;

public class ListviewActivity extends Activity {

	private ArrayList<HashMap<String, String>> arrayList;
	private SimpleAdapter listAdapter;
	private ListView listView;
	public All all;
	private int editnum;
	private LinearLayout layout_lookup;
	private String studentE[];
	private Spinner spinner1;
	private Spinner spinner2;
	private EditText editText1;
	private EditText editText2;
	private Button btnLookup;
	private Button btnShowall;
	private Button btnGone;
	private int item1;
	private int item2;
	private int info;

	private String[] electionE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO ����Activity
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ر���
		setContentView(R.layout.activity_listview);
		all = (All) getApplication();// �õ�ȫ�ֱ���
		studentE = getResources().getStringArray(R.array.studentE);
		electionE = getResources().getStringArray(R.array.electionE);// �õ��ַ���

		Intent intent = getIntent();
		info = intent.getExtras().getInt("info");
		dealListview();
		Cursor cursor;
		if (STUDENT == info) {
			cursor = all.dbManager.queryAll("student");
			setTitle("ѧ����Ϣ");
		} else {
			cursor = all.dbManager.queryAll("election");
			setTitle("ѡ����Ϣ");
		}
		reloadArrayList(cursor);
		cursor.close();// �ͷ���Դ
		dealWidget();
	}

	private void dealWidget() {
		// TODO ����Activity����Ŀؼ�����������Listview��Listview�ؼ��Ƚϸ��ӣ�����һ�������е�������
		layout_lookup = (LinearLayout) findViewById(R.id.layout_lookup);
		dealSpinner();
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		btnLookup = (Button) findViewById(R.id.btn_lookup);
		btnLookup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO ���Ұ�ť������
				String string1 = editText1.getText().toString();
				String string2 = editText2.getText().toString();
				// Toast.makeText(SLookupActivity.this, string1+"#"+string2,
				// Toast.LENGTH_LONG).show();
				String sqlstudent = "select * from student where ";
				String sqlelection = "select * from election where ";
				boolean flag = false;
				if (STUDENT == info) {
					if (!string1.equals("")) {
						sqlstudent = sqlstudent + studentE[item1] + " like "
								+ "'%" + string1 + "%'";
						flag = true;
					}
					if (!string2.equals("")) {
						if (true == flag) {
							sqlstudent = sqlstudent + " and " + studentE[item2]
									+ " like " + "'%" + string2 + "%'";
						} else {
							sqlstudent = sqlstudent + studentE[item2]
									+ " like " + "'%" + string2 + "%'";
						}
						flag = true;
					}
					if (true == flag) {
						Cursor cursor = all.dbManager.query(sqlstudent);
						reloadArrayList(cursor);
					} else {
						Toast.makeText(ListviewActivity.this, "�������������",
								Toast.LENGTH_LONG).show();
					}
					flag = false;
				} else {
					if (!string1.equals("")) {
						sqlelection = sqlelection + electionE[item1] + " like "
								+ "'%" + string1 + "%'";
						flag = true;
					}
					if (!string2.equals("")) {
						if (true == flag) {
							sqlelection = sqlelection + " and "
									+ electionE[item2] + " like " + "'%"
									+ string2 + "%'";
						} else {
							sqlelection = sqlelection + electionE[item2]
									+ " like " + "'%" + string2 + "%'";
						}
						flag = true;
					}
					if (true == flag) {
						Cursor cursor = all.dbManager.query(sqlelection);
						reloadArrayList(cursor);
					} else {
						Toast.makeText(ListviewActivity.this, "�������������",
								Toast.LENGTH_LONG).show();
					}
					flag = false;
				}
			}
		});
		btnGone = (Button) findViewById(R.id.btn_yincang);
		btnGone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ���ذ�ť������
				layout_lookup.setVisibility(View.GONE);
			}
		});
		btnShowall = (Button) findViewById(R.id.btn_showall);
		btnShowall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ȫ����ʾ��ť������
				Cursor cursor;
				if (STUDENT == info) {
					cursor = all.dbManager.queryAll("student");
				} else {
					cursor = all.dbManager.queryAll("election");
				}
				reloadArrayList(cursor);
			}
		});
	}

	public void dealSpinner() {
		// ����spinner
		ArrayAdapter<CharSequence> arrayAdapter;
		if (STUDENT == info) {
			arrayAdapter = ArrayAdapter.createFromResource(this,
					R.array.studentC, android.R.layout.simple_spinner_item);
		} else {
			arrayAdapter = ArrayAdapter.createFromResource(this,
					R.array.electionC, android.R.layout.simple_spinner_item);
		}
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setAdapter(arrayAdapter);
		spinner1.setPrompt("ѡ��");
		spinner1.setOnItemSelectedListener(new SpinnerListener());
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setAdapter(arrayAdapter);
		spinner2.setPrompt("ѡ��");
		spinner2.setOnItemSelectedListener(new SpinnerListener());
	}

	public class SpinnerListener implements OnItemSelectedListener {
		// TODO �����˵�������
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO ѡ��ĳ��
			if (arg0.getId() == spinner1.getId()) {
				item1 = arg2;
			} else if (arg0.getId() == spinner2.getId()) {
				item2 = arg2;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO û�ж�����ѡ��

		}
	}

	private void reloadArrayList(Cursor cursor) {
		// TODO ˢ��Listview�е�����
		arrayList.clear();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			if (STUDENT == info) {
				map.put("row0", cursor.getString(cursor.getColumnIndex("name")));
				map.put("row1",
						cursor.getString(cursor.getColumnIndex("classPost")));
				map.put("row2", cursor.getString(cursor.getColumnIndex("sid")));
			} else {
				map.put("row0", cursor.getString(cursor.getColumnIndex("eid")));
				map.put("row1", cursor.getString(cursor.getColumnIndex("name")));
				map.put("row2", cursor.getString(cursor.getColumnIndex("date")));
			}
			arrayList.add(map);
		}
		listAdapter.notifyDataSetChanged();
		if (cursor.getCount() == 0) {
			Toast.makeText(ListviewActivity.this, "δ�ҵ���������������",
					Toast.LENGTH_LONG).show();
		}
	}

	private void dealListview() {
		// TODO ���ز�����Listview�ؼ�
		listView = (ListView) findViewById(R.id.listView_student);
		arrayList = new ArrayList<HashMap<String, String>>();
		listAdapter = new SpecialAdapter(this, arrayList,
				R.layout.list_iteminfo,
				new String[] { "row0", "row1", "row2", }, new int[] {
						R.id.tv_0, R.id.tv_1, R.id.tv_2 });
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO ����Listview��Ŀʱ�Ĵ�����
				Intent intent = new Intent();
				if (STUDENT == info) {
					intent.setClass(ListviewActivity.this,
							StudentInfoActivity.class);
					intent.putExtra("mode", EDIT);
					intent.putExtra("sid", arrayList.get(arg2).get("row2"));
					// Bundle bundle = new Bundle();
					// bundle.putSerializable("student",
					// all.students.get(arg2));
					// intent.putExtras(bundle);
				} else {
					intent.setClass(ListviewActivity.this,
							ElectionInfoActivity.class);
					intent.putExtra("mode", EDIT);
					intent.putExtra("eid", arrayList.get(arg2).get("row0"));
				}
				editnum = arg2;
				// startActivity(intent);
				startActivityForResult(intent, 0);
			}
		});
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO ����Listview��Ŀʱ�Ĵ�����
				new AlertDialog.Builder(ListviewActivity.this)
						.setTitle("��ʾ")
						.setMessage("�Ƿ�Ҫɾ����Ϣ��")
						.setPositiveButton("ɾ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO ����ɾ������
										deleteItem(arg2);
									}

								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO ȡ��ɾ��

									}
								}).show();
				return false;
			}
		});
	}

	private void deleteItem(int arg2) {
		// TODO ɾ��������ListView�е�һ����Ϣ
		if (STUDENT == info) {
			all.dbManager.deleteStudent(arrayList.get(arg2).get("row2"));
			all.dbManager
					.deleteSidinfoInWinner(arrayList.get(arg2).get("row2"));

		} else {
			all.dbManager.deleteElection(arrayList.get(arg2).get("row0"));
		}
		arrayList.remove(arg2);
		listAdapter.notifyDataSetChanged();
		// all.students.remove(arg2);
	}

	private HashMap<String, String> getMap(Intent data) {
		Bundle bundle = data.getExtras();
		HashMap<String, String> map = new HashMap<String, String>();
		if (STUDENT == info) {
			String row0String = bundle.getString("name");
			String row1String = bundle.getString("classPost");
			String row2String = bundle.getString("sid");
			map.put("row0", row0String);
			map.put("row1", row1String);
			map.put("row2", row2String);

		} else {
			String row0String = bundle.getString("eid");
			String row1String = bundle.getString("name");
			String row2String = bundle.getString("date");
			map.put("row0", row0String);
			map.put("row1", row1String);
			map.put("row2", row2String);
		}
		return map;

	}

	/*
	 * (���ز˵�)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO ���ز˵�
		MenuInflater a = getMenuInflater();
		a.inflate(R.menu.menu_listviewactivity, menu);
		return true;
	}

	/*
	 * (�˵�����) �˵�����
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �˵���ť
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.add:
			if (STUDENT == info) {
				intent.setClass(ListviewActivity.this,
						StudentInfoActivity.class);
				// startActivity(intent);
			} else {
				intent.setClass(ListviewActivity.this,
						ElectionInfoActivity.class);
			}
			intent.putExtra("mode", ADD);
			startActivityForResult(intent, 0);
			break;
		case R.id.lookup:
			// intent.setClass(StudentActivity.this, SLookupActivity.class);
			// intent.putExtra("mode", LOOKUP);
			// startActivity(intent);
			// startActivityForResult(intent, 0);
			layout_lookup.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
		return true;
	}

	/* ����Activity����ֵ */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO ����Intent���ص�����
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			if (resultCode == ADD) {
				HashMap<String, String> map = getMap(data);
				arrayList.add(map);
				listAdapter.notifyDataSetChanged();
			} else if (resultCode == EDIT) {
				//Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
				HashMap<String, String> map = getMap(data);
				arrayList.set(editnum, map);
				listAdapter.notifyDataSetChanged();
			} else {
				// Toast.makeText(this, resultCode, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "����ֵ����", Toast.LENGTH_SHORT).show();
		}
	}
}
