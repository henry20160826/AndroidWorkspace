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
				// ��ת��BillActivity
				// Intent intent = new Intent();
				// intent.setClass(DishesActivity.this, BillActivity.class);
				// startActivity(intent);
				// if (!listViewArrayList.isEmpty()) {
				// // TODO ��������ύ���ݣ��޸Ĵ���״̬
				//
				// }
				// finish();
				/**
				 * �����Ի����û�ѡ�� 1.��ɵ�� 2.������� 3.�鿴ȫ���˵�
				 */
				new AlertDialog.Builder(DishesActivity.this).setTitle("��ʾ")
						.setMessage("ȷ����ɵ�ˣ�")
						.setPositiveButton("ȷ��", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								// ���Ͳ˵�
								UDP udp = new UDP(null);
								String money = allMoneyTextView.getText()
										.toString();
								String dishes = null;
								udp.sendDishes(tableNum + "", money, dishes);
								finish();
							}
						}).setNegativeButton("ȡ��", new OnClickListener() {

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
		// ���� �� ����
		String[] nameOfDishString = getResources().getStringArray(
				R.array.nameOfDish);
		String[] tasteOfDishString = getResources().getStringArray(
				R.array.tasteOfDish);
		String[] priceOfDishString = getResources().getStringArray(
				R.array.priceOfDish);
		// ���ɶ�̬���飬����ת������
		int length = imagesOfDish.length;
		gridViewDishArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// ���ͼ����Դ��ID
			map.put("image", imagesOfDish[i]);
			map.put("name", nameOfDishString[i]);
			map.put("taste", tasteOfDishString[i]);
			map.put("price", priceOfDishString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewDishArrayList.add(map);
		}

		// ���� �� ����
		String[] nameOfDrinkString = getResources().getStringArray(
				R.array.nameOfDrink);
		String[] tasteOfDrinkString = getResources().getStringArray(
				R.array.tasteOfDish);
		String[] priceOfDrinkString = getResources().getStringArray(
				R.array.priceOfDrink);
		// ���ɶ�̬���飬����ת������
		length = imagesOfDrink.length;
		gridViewDrinkArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// ���ͼ����Դ��ID
			map.put("image", imagesOfDrink[i]);
			map.put("name", nameOfDrinkString[i]);
			map.put("taste", tasteOfDrinkString[i]);
			map.put("price", priceOfDrinkString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewDrinkArrayList.add(map);
		}
		// ���� ��ʳ ����
		String[] nameOfZhushiString = getResources().getStringArray(
				R.array.nameOfZhushi);
		String[] tasteOfZhushiString = getResources().getStringArray(
				R.array.tasteOfZhushi);
		String[] priceOfZhushiString = getResources().getStringArray(
				R.array.priceOfZhushi);
		// ���ɶ�̬���飬����ת������
		length = imagesOfZhushi.length;
		gridViewZhushiArrayList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.ic_action_search);// ���ͼ����Դ��ID
			map.put("image", imagesOfZhushi[i]);
			map.put("name", nameOfZhushiString[i]);
			map.put("taste", tasteOfZhushiString[i]);
			map.put("price", priceOfZhushiString[i]);
			map.put("ButtonClose", R.drawable.bt_delete);
			gridViewZhushiArrayList.add(map);
		}

		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		gridViewDishAdapter = new SimpleAdapter(this, // ûʲô����
				gridViewDishArrayList,// ������Դ
				R.layout.grid_item_dishes,// night_item��XMLʵ��

				// ��̬������ImageItem��Ӧ������
				new String[] { "image", "name", "taste", "price" },

				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
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
		// ��Ӳ�����ʾ
		mode = DISH_MODE;
		gridView.setAdapter(gridViewDishAdapter);
		// �����Ϣ����
		gridView.setOnItemClickListener(new GridViewItemClickListener());
	}

	class GridViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			// HashMap<String, Object> item = (HashMap<String, Object>) arg0
			// .getItemAtPosition(arg2);
			// // ��ʾ��ѡItem��ItemText
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
		// ��Layout�����ListView
		listView = (ListView) findViewById(R.id.listView1);

		// ���ɶ�̬���飬��������
		listViewArrayList = new ArrayList<HashMap<String, Object>>();
		// for (int i = 0; i < 10; i++) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// // map.put("ItemImage", R.drawable.checked);// ͼ����Դ��ID
		// map.put("ItemTitle", "Level " + i);
		// map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");
		// listItem.add(map);
		// }
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		listViewAdapter = new lvButtonAdapter(
				this,
				listViewArrayList,// ����Դ
				R.layout.list_item_dishes,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "image", "name", "taste", "price", "ButtonClose" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.imageView1, R.id.tv_name, R.id.tv_taste,
						R.id.tv_price, R.id.ibt_delete });

		listViewAdapter.setTextView(allMoneyTextView, dishNumTextView);
		// ��Ӳ�����ʾ
		listView.setAdapter(listViewAdapter);
	}

	private void getWaiternameAndTableNum() {
		Intent intent = getIntent();
		tableNum = intent.getExtras().getInt(TABLE_NUM);
		waiterName = intent.getExtras().getString(WAITER_NAME);
		usernameTextView.setText(waiterName + SERVER_FOR_YOU);
		tableNumTextView.setText(tableNum + "����");
		stateTextView.setText(CONNECTION);
	}

}
