package com.hu.Activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.passwordmanager.R;
import com.hu.DataClass.All;
import com.hu.encode.Cipher;

import static com.hu.DataClass.Content.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.app.ActionBar;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListviewActivity extends Activity {

	private ListView listView;
	private ArrayList<HashMap<String, String>> arrayList;
	private SimpleAdapter adapter;
	private All all;
	private ImageButton addButton, deleteButton, goneButton, editButton;
	private int mode = LOOK;
	private ArrayList<String> deleteArrayList;
	private ActionBar actionBar;
	private TextView usernameTextView, passwordTextView, remarkTextView;
	private String pkeyString;
	private RelativeLayout infoLayout;
	private int pkeyLocation = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		actionBar = getActionBar();
		actionBar.show();
		getOverflowMenu();
		all = (All) getApplication();
		dealViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reloadArrayList();
	}

	private void dealViews() {
		usernameTextView = (TextView) findViewById(R.id.tv_username);
		passwordTextView = (TextView) findViewById(R.id.tv_password);
		remarkTextView = (TextView) findViewById(R.id.tv_remark);
		infoLayout = (RelativeLayout) findViewById(R.id.layout_info);
		infoLayout.setVisibility(View.GONE);
		dealListview();
		dealButtons();
	}

	private void dealButtons() {
		// addButton = (ImageButton) findViewById(R.id.ibt_add);
		// addButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// toNewItem();
		// }
		// });
		deleteButton = (ImageButton) findViewById(R.id.bt_delete);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteItems();
				reloadArrayList();
				relistbackground();
				mode = LOOK;
				deleteButton.setVisibility(View.GONE);
			}
		});
		goneButton = (ImageButton) findViewById(R.id.bt_gone);
		goneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				infoLayout.setVisibility(View.GONE);
			}
		});
		editButton = (ImageButton) findViewById(R.id.bt_edit);
		editButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ListviewActivity.this, EditActivity.class);

				intent.putExtra("mode", EDIT);
				intent.putExtra("pkey", pkeyString);

				startActivityForResult(intent, 0);
				// editnum = arg2;
				// startActivity(intent);
			}
		});
	}

	private void toNewItem() {
		Intent intent = new Intent();
		intent.setClass(ListviewActivity.this, EditActivity.class);
		intent.putExtra("mode", NEW);
		startActivity(intent);
	}

	private void toSettings() {
		Intent intent = new Intent();
		intent.setClass(ListviewActivity.this, SettingsActivity.class);
		startActivity(intent);
	}

	private void toAbout() {
		Intent intent = new Intent();
		intent.setClass(ListviewActivity.this, AboutActivity.class);
		startActivity(intent);
	}

	private void deleteItems() {
		int length = deleteArrayList.size();
		for (int i = 0; i < length; i++) {
			all.dbManager.deleteOneItem(deleteArrayList.get(i));
		}
	}

	private void dealListview() {
		// TODO 加载并配置ListView控件
		listView = (ListView) findViewById(R.id.listView1);
		arrayList = new ArrayList<HashMap<String, String>>();
		// adapter = new SimpleAdapter(this, arrayList, R.layout.listitem,
		// new String[] { "place", "name", "p", }, new int[] {
		// R.id.tv_place, R.id.tv_name, R.id.tv_p });
		adapter = new SimpleAdapter(this, arrayList, R.layout.listitem,
				new String[] { "place", "pkey" }, new int[] { R.id.tv_place,
						R.id.tv_pkey });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 单击ListView条目时的处理函数
				if (LOOK == mode) {
					// Intent intent = new Intent();
					// intent.setClass(ListviewActivity.this,
					// EditActivity.class);
					//
					// intent.putExtra("mode", EDIT);
					// intent.putExtra("pkey", arrayList.get(arg2).get("pkey"));
					//
					// startActivityForResult(intent, 0);
					// // editnum = arg2;
					// // startActivity(intent);
					if (View.GONE == infoLayout.getVisibility()
							|| arg2 != pkeyLocation) {
						pkeyLocation = arg2;
						pkeyString = arrayList.get(arg2).get("pkey");
						String[] strings = all.dbManager.getOneItem(pkeyString);
						setTextViewinfo(strings);
						showInfoLayout();
					} else {
						goneInfoLayout();
					}
				}
				if (EDIT == mode) {
					String pkey = arrayList.get(arg2).get("pkey");
					int num = existInDeleteArrayList(pkey);
					if (-1 == num) {
						arg1.setBackgroundColor(getResources().getColor(
								R.color.blue));
						deleteArrayList.add(pkey);
					} else {
						arg1.setBackgroundColor(Color.TRANSPARENT);
						deleteArrayList.remove(num);
					}
				}
			}

			private void goneInfoLayout() {
				Animation animation = (Animation) AnimationUtils.loadAnimation(
						ListviewActivity.this, R.animator.anim_gone_infolayout);
				LayoutAnimationController lac = new LayoutAnimationController(
						animation);
				lac.setOrder(LayoutAnimationController.ORDER_REVERSE);
				lac.setDelay(0);
				infoLayout.setLayoutAnimation(lac);
				infoLayout.setVisibility(View.GONE);
			}

			private void showInfoLayout() {
				Animation animation = (Animation) AnimationUtils.loadAnimation(
						ListviewActivity.this, R.animator.anim_show_infolayout);
				LayoutAnimationController lac = new LayoutAnimationController(
						animation);
				lac.setOrder(LayoutAnimationController.ORDER_REVERSE);
				lac.setDelay(0);
				infoLayout.setLayoutAnimation(lac);
				infoLayout.setVisibility(View.VISIBLE);
			}
		});
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO 长按ListView条目时的处理函数
				// new AlertDialog.Builder(ListviewActivity.this)
				// .setTitle("提示")
				// .setMessage("是否要删除信息？")
				// .setPositiveButton("删除",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// // TODO 调用删除函数
				// // deleteItem(arg2);
				// }
				//
				// })
				// .setNegativeButton("取消",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// // TODO 取消删除
				//
				// }
				// }).show();
				if (LOOK == mode) {
					mode = EDIT;
					deleteButton.setVisibility(View.VISIBLE);
					deleteArrayList = new ArrayList<String>();
					arg1.setBackgroundColor(getResources().getColor(
							R.color.androidblue));
					deleteArrayList.add(arrayList.get(arg2).get("pkey"));
					return true;
				}
				return false;
			}
		});
	}

	private void setTextViewinfo(String[] string) {
		usernameTextView.setText(string[1]);
		passwordTextView.setText(string[2]);
		remarkTextView.setText(string[3]);
	}

	private int existInDeleteArrayList(String string) {
		int length = deleteArrayList.size();
		for (int i = 0; i < length; i++) {
			if (deleteArrayList.get(i).equals(string)) {
				return i;
			}
		}
		return -1;
	}

	private void reloadArrayList() {
		// TODO 刷新ListView中的数据
		arrayList.clear();
		Cursor cursor = all.dbManager.getPasswords();
		String placeString = null;
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			placeString = Cipher.decode(cursor.getString(cursor
					.getColumnIndex("place")));
			map.put("place", placeString);
			map.put("pkey", cursor.getString(cursor.getColumnIndex("pkey")));
			// map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			// map.put("p", cursor.getString(cursor.getColumnIndex("p")));
			arrayList.add(map);
		}
		adapter.notifyDataSetChanged();
		if (cursor.getCount() == 0) {
			Toast.makeText(ListviewActivity.this, "数据为空", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void relistbackground() {
		int length = listView.getChildCount();
		for (int i = 0; i < length; i++) {
			listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && EDIT == mode) {
			mode = LOOK;
			relistbackground();
			deleteButton.setVisibility(View.GONE);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overflow, menu);
		super.onCreateOptionsMenu(menu);
		// 添加菜单项
		// MenuItem add = menu.add(0, 0, 0, "add");
		// MenuItem del = menu.add(0, 0, 0, "del");
		// MenuItem save = menu.add(0, 0, 0, "save");
		// MenuItem save1 = menu.add(0, 0, 0, "save");
		// MenuItem save2 = menu.add(0, 0, 0, "save");
		// MenuItem save3 = menu.add(0, 0, 0, "save");
		// MenuItem item=menu.add(groupId, itemId, order, title)
		// 绑定到ActionBar
		// add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// save1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// save2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// save3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.add:
			toNewItem();
			break;
		case R.id.settings:
			toSettings();
			break;
		case R.id.about:
			toAbout();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
