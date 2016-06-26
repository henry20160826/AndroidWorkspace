package com.hu.activity;

import static com.hu.dataclass.Content.*;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.order_dishes.R;
import com.hu.adapter.lvButtonAdapter;
import com.hu.udp.UDP;

public class DishesActivity extends Activity {

	private int tableNum, mode;
	private TextView tableNumTextView;
	private TextView dishNumTextView;
	private TextView allMoneyTextView;
	private TextView usernameTextView;
	private TextView stateTextView;
	private Button dishButton, drinkButton, zhushiButton;
	private ImageButton backButton, okButton;
	private GridView gridView;
	private ListView listView;
	private SimpleAdapter gridViewDishAdapter, gridViewDrinkAdapter,
			gridViewZhushiAdapter;
	private lvButtonAdapter listViewAdapter;
	private ArrayList<HashMap<String, Object>> gridViewDishArrayList,
			gridViewDrinkArrayList, gridViewZhushiArrayList;
	private ArrayList<HashMap<String, Object>> listViewArrayList;
	private String waiterName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_dishes);
		getViews();

	}

	private void getViews() {

		usernameTextView = (TextView) findViewById(R.id.tv_username);
		stateTextView = (TextView) findViewById(R.id.tv_state);
		tableNumTextView = (TextView) findViewById(R.id.tv_tablenum);
		dishNumTextView = (TextView) findViewById(R.id.tv_dishNum);
		allMoneyTextView = (TextView) findViewById(R.id.tv_allMoney);
		getWaiternameAndTableNum();
		backButton = (ImageButton) findViewById(R.id.bt_back);
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		okButton = (ImageButton) findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 跳转到BillActivity
				// Intent intent = new Intent();
				// intent.setClass(DishesActivity.this, BillActivity.class);
				// startActivity(intent);
				// if (!listViewArrayList.isEmpty()) {
				// // TODO 向服务器提交数据，修改此桌状态
				//
				// }
				// finish();
				/**
				 * 弹出对话框供用户选择 1.完成点菜 2.继续点菜 3.查看全部菜单
				 */
				new AlertDialog.Builder(DishesActivity.this).setTitle("提示")
						.setMessage("确定完成点菜？")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								// 发送菜单
								UDP udp = new UDP(null);
								String money = allMoneyTextView.getText()
										.toString();
								String dishes = null;
								udp.sendDishes(tableNum + "", money, dishes);
								finish();
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
			}
		});

		dishButton = (Button) findViewById(R.id.bt_dish);
		dishButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mode = DISH_MODE;
				gridView.setAdapter(gridViewDishAdapter);
			}
		});

		drinkButton = (Button) findViewById(R.id.bt_drink);
		drinkButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mode = DRINK_MODE;
				gridView.setAdapter(gridViewDrinkAdapter);
			}
		});

		zhushiButton = (Button) findViewById(R.id.bt_zhushi);
		zhushiButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mode = ZHUSHI_MODE;
				gridView.setAdapter(gridViewZhushiAdapter);
			}
		});

		getGridView();
		getListView();
	}

	private void getGridView() {
		gridView = (GridView) findViewById(R.id.gridView1);
		// 加载 菜 数据
		String[] nameOfDishString = getResources().getStringArray(
				R.array.nameOfDish);
		String[] tasteOfDishString = getResources().getStringArray(
				R.array.tasteOfDish);
		String[] priceOfDishString = getResources().getStringArray(
				R.array.priceOfDish);
		// 生成动态数组，并且转入数据
		int length = imagesOfDish.length;
		gridViewDishArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// 添加图像资源的ID
			map.put("image", imagesOfDish[i]);
			map.put("name", nameOfDishString[i]);
			map.put("taste", tasteOfDishString[i]);
			map.put("price", priceOfDishString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewDishArrayList.add(map);
		}

		// 加载 酒 数据
		String[] nameOfDrinkString = getResources().getStringArray(
				R.array.nameOfDrink);
		String[] tasteOfDrinkString = getResources().getStringArray(
				R.array.tasteOfDish);
		String[] priceOfDrinkString = getResources().getStringArray(
				R.array.priceOfDrink);
		// 生成动态数组，并且转入数据
		length = imagesOfDrink.length;
		gridViewDrinkArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// 添加图像资源的ID
			map.put("image", imagesOfDrink[i]);
			map.put("name", nameOfDrinkString[i]);
			map.put("taste", tasteOfDrinkString[i]);
			map.put("price", priceOfDrinkString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewDrinkArrayList.add(map);
		}
		// 加载 主食 数据
		String[] nameOfZhushiString = getResources().getStringArray(
				R.array.nameOfZhushi);
		String[] tasteOfZhushiString = getResources().getStringArray(
				R.array.tasteOfZhushi);
		String[] priceOfZhushiString = getResources().getStringArray(
				R.array.priceOfZhushi);
		// 生成动态数组，并且转入数据
		length = imagesOfZhushi.length;
		gridViewZhushiArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// 添加图像资源的ID
			map.put("image", imagesOfZhushi[i]);
			map.put("name", nameOfZhushiString[i]);
			map.put("taste", tasteOfZhushiString[i]);
			map.put("price", priceOfZhushiString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewZhushiArrayList.add(map);
		}

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		gridViewDishAdapter = new SimpleAdapter(this, // 没什么解释
				gridViewDishArrayList,// 数据来源
				R.layout.grid_item_dishes,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "image", "name", "taste", "price" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				// R.id.imageView1, R.id.tv_name, R.id.tv_taste,
				// R.id.tv_price
				new int[] { R.id.imageView1, R.id.tv_name, R.id.tv_taste,
						R.id.tv_price });
		// Drink
		gridViewDrinkAdapter = new SimpleAdapter(this, gridViewDrinkArrayList,
				R.layout.grid_item_dishes, new String[] { "image", "name",
						"taste", "price" }, new int[] { R.id.imageView1,
						R.id.tv_name, R.id.tv_taste, R.id.tv_price });

		// Zhushi
		gridViewZhushiAdapter = new SimpleAdapter(this,
				gridViewZhushiArrayList, R.layout.grid_item_dishes,
				new String[] { "image", "name", "taste", "price" }, new int[] {
						R.id.imageView1, R.id.tv_name, R.id.tv_taste,
						R.id.tv_price });
		// 添加并且显示
		mode = DISH_MODE;
		gridView.setAdapter(gridViewDishAdapter);
		// 添加消息处理
		gridView.setOnItemClickListener(new GridViewItemClickListener());
	}

	class GridViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			// HashMap<String, Object> item = (HashMap<String, Object>) arg0
			// .getItemAtPosition(arg2);
			// // 显示所选Item的ItemText
			// setTitle(arg2+1+"");
			int sum = 0;
			switch (mode) {
			case DISH_MODE:
				listViewArrayList.add(gridViewDishArrayList.get(arg2));
				sum = Integer.parseInt(allMoneyTextView.getText().toString());
				sum += Integer.parseInt(gridViewDishArrayList.get(arg2)
						.get("price").toString());
				break;
			case DRINK_MODE:
				listViewArrayList.add(gridViewDrinkArrayList.get(arg2));
				sum = Integer.parseInt(allMoneyTextView.getText().toString());
				sum += Integer.parseInt(gridViewDrinkArrayList.get(arg2)
						.get("price").toString());
				break;
			case ZHUSHI_MODE:
				listViewArrayList.add(gridViewZhushiArrayList.get(arg2));
				sum = Integer.parseInt(allMoneyTextView.getText().toString());
				sum += Integer.parseInt(gridViewZhushiArrayList.get(arg2)
						.get("price").toString());
				break;
			}

			listViewAdapter.notifyDataSetChanged();
			dishNumTextView.setText(listViewAdapter.getCount() + "");

			allMoneyTextView.setText(sum + "");
		}
	}

	private void getListView() {
		// 绑定Layout里面的ListView
		listView = (ListView) findViewById(R.id.listView1);

		// 生成动态数组，加入数据
		listViewArrayList = new ArrayList<HashMap<String, Object>>();
		// for (int i = 0; i < 10; i++) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// // map.put("ItemImage", R.drawable.checked);// 图像资源的ID
		// map.put("ItemTitle", "Level " + i);
		// map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");
		// listItem.add(map);
		// }
		// 生成适配器的Item和动态数组对应的元素
		listViewAdapter = new lvButtonAdapter(
				this,
				listViewArrayList,// 数据源
				R.layout.list_item_dishes,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "image", "name", "taste", "price", "ButtonClose" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.imageView1, R.id.tv_name, R.id.tv_taste,
						R.id.tv_price, R.id.ibt_delete });

		listViewAdapter.setTextView(allMoneyTextView, dishNumTextView);
		// 添加并且显示
		listView.setAdapter(listViewAdapter);
	}

	private void getWaiternameAndTableNum() {
		Intent intent = getIntent();
		tableNum = intent.getExtras().getInt(TABLE_NUM);
		waiterName = intent.getExtras().getString(WAITER_NAME);
		usernameTextView.setText(waiterName + SERVER_FOR_YOU);
		tableNumTextView.setText(tableNum + "号桌");
		stateTextView.setText(CONNECTION);
	}

}
