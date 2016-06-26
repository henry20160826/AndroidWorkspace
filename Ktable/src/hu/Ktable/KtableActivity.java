package hu.Ktable;

import static hu.Ktable.MyConfig.*;
import hu.Ktable.MyConfig.Class_info;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class KtableActivity extends Activity implements OnGestureListener,
		OnTouchListener {

	private GestureDetector detector;
	private ViewFlipper flipper;
	private Calendar calendar;
	private String[] wek;
	private int weekday, style;
	private TextView week1, week2;
	private TextView clas11, clas12, clas13, clas14, clas15, clas16, clas21,
			clas22, clas23, clas24, clas25, clas26;
	private int id_textview;
	boolean firstopen = true, first_page = true;

	// firstopen �Ƿ�Ϊ��һ�δ�
	// page�Ƿ�Ϊflipper��һҳ

	/**
	 * 
	 * @author Kamui
	 * 
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ر���
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);// ����ȫ��
		 */
		super.onCreate(savedInstanceState);

		dbhelper = new DatabaseHelper(KtableActivity.this, "user", null, 1);
		dbW = dbhelper.getWritableDatabase();
		style = dbhelper.readstyle(dbW);
		if (4 != style) {
			set_activity_style();
			getItem();
			detector = new GestureDetector(this);
			setTextviewOntouchListener();
			wek = getResources().getStringArray(R.array.week);

			classmap = new boolean[TIME_NUMBER];
			ke = new Class_info[DAY_NUMBER][TIME_NUMBER];
			StrKe = new String[DAY_NUMBER][TIME_NUMBER];
			Locale.setDefault(Locale.CHINA);
			calendar = Calendar.getInstance();
			weekday = calendar.get(Calendar.DAY_OF_WEEK);
			weekday -= 2;
			if (weekday > DAY_NUMBER - 2 || weekday < 0) {
				weekday = 0;
			}
			// ke=getResources().getStringArray(R.array.clas);
			// ��ȡ�ļ�***************************************************************************************
			// read();
			// ��ʾ����***************************************************************************************
			new_class_info();
			try {
				readExcel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("123", e.toString());
			}
			combination();
			write_to_textview();
		} else {
			set_activity_style();
		}

	}

	private void setTextviewOntouchListener() {
		clas11.setOnTouchListener(this);
		clas12.setOnTouchListener(this);
		clas13.setOnTouchListener(this);
		clas14.setOnTouchListener(this);
		clas15.setOnTouchListener(this);
		clas16.setOnTouchListener(this);
		clas21.setOnTouchListener(this);
		clas22.setOnTouchListener(this);
		clas23.setOnTouchListener(this);
		clas24.setOnTouchListener(this);
		clas25.setOnTouchListener(this);
		clas26.setOnTouchListener(this);
		// flipper.setOnTouchListener(this);
	}

	/**
	 * activity��������
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (false == firstopen) {
			style = dbhelper.readstyle(dbW);
			if (4 != style) {
				set_activity_style();
				getItem();
				write_to_textview();
				detector = new GestureDetector(this);
				setTextviewOntouchListener();
			} else {
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		first_page = true;
		firstopen = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (change) {
			int r = 0;
			int i, j, time, k;
			r = getclassmap();
			r = r * BOX_NUMBER + TIME_NUMBER + 1 - r;
			try {
				String SDPath = Environment.getExternalStorageDirectory()
						.getPath();
				String filename = SDPath + "//kebiao.xls";
				File file = new File(filename);
				InputStream is = new FileInputStream(file);
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook book = new HSSFWorkbook(fs);// �򿪿��ļ�����
				Sheet sheet = book.getSheetAt(book.getSheetIndex("Sheet1"));
				if (sheet == null) {
					sheet = book.createSheet("Sheet1");
				}
				/*
				 * CellStyle green=book.createCellStyle();
				 * green.setBorderBottom(CellStyle.BORDER_THIN);
				 * green.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				 * green.setBorderLeft(CellStyle.BORDER_THIN);
				 * green.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				 * green.setBorderRight(CellStyle.BORDER_THIN);
				 * green.setRightBorderColor(IndexedColors.BLACK.getIndex());
				 * green.setBorderTop(CellStyle.BORDER_THIN);
				 * green.setTopBorderColor(IndexedColors.BLACK.getIndex());
				 * green.setFillPattern(CellStyle.SOLID_FOREGROUND);
				 * green.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				 */
				Row row;
				Cell cell;
				for (i = 0; i < TIME_NUMBER; i++) {
					row = sheet.createRow(i);
					for (j = 0; j < BOX_NUMBER; j++) {
						row.createCell(j);
					}
				}
				time = 0;
				k = 0;
				for (j = 1; j < BOX_NUMBER; j++) {
					for (i = 1; i < r;) {
						cell = sheet.getRow(i).getCell(j);
						if (null != ke[j - 1][time].data) {
							cell.setCellValue(ke[j - 1][time].data[k]);
							k++;
							time += k / BOX_NUMBER;
							k = k % BOX_NUMBER;
							time = time % TIME_NUMBER;
							i++;
						} else {
							if (true == classmap[time]) {
								i += BOX_NUMBER;
							} else {
								i++;
							}
							time++;
							time = time % TIME_NUMBER;
						}
					}
				}
				for (i = 1; i < BOX_NUMBER; i++) {
					cell = sheet.getRow(0).getCell(i);
					cell.setCellValue(wek[i - 1]);
					/* cell.setCellStyle(green); */
				}
				String num[] = getResources().getStringArray(R.array.num);
				i = 1;
				for (j = 0; j < DAY_NUMBER; j++) {
					cell = sheet.getRow(i).getCell(0);
					if (true == classmap[j]) {
						i += BOX_NUMBER;
					} else {
						i++;
					}
					cell.setCellValue(num[j]);
				}
				FileOutputStream fileOut = new FileOutputStream(filename);
				book.write(fileOut);
				fileOut.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print(e + "1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print(e);
			}

		}
		dbhelper.close();
		dbW.close();
		super.finish();

	}

	private int getclassmap() {
		int day, time, num = 0;
		for (time = 0; time < TIME_NUMBER; time++) {
			classmap[time] = false;
		}
		for (time = 0; time < TIME_NUMBER; time++) {
			for (day = 0; day < DAY_NUMBER; day++) {
				if (null != ke[day][time].data) {
					break;
				}
			}
			if (day != DAY_NUMBER) {
				classmap[time] = true;
				num++;
			}
		}
		return num;
	}

	// 4ȫ��ģʽ
	// 0 Ĭ��Ƥ��
	// 1 ��ɫ�ſ�
	// 2 ��ɫ����
	// 3 ��ɫ����
	/**
	 * ����Ƥ��
	 */
	private void set_activity_style() {
		switch (style) {
		case 1:// ��ɫŮ��
			setContentView(R.layout.k_pink);
			flipper = (ViewFlipper) findViewById(R.id.ViewFlipper00);
			break;
		case 2:// ��ɫ���
			setContentView(R.layout.k_green);
			flipper = (ViewFlipper) findViewById(R.id.ViewFlipper00);
			break;
		case 3:// ��ɫ���
			setContentView(R.layout.k_blue);
			flipper = (ViewFlipper) findViewById(R.id.ViewFlipper00);
			break;
		case 4:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.quaju);
			break;
		default:// Ĭ��Ƥ��
			setContentView(R.layout.k_shuimo);
			flipper = (ViewFlipper) findViewById(R.id.ViewFlipper00);
			break;
		}
	}

	/**
	 * �õ�activity�е����пؼ�
	 */
	private void getItem() {
		week1 = (TextView) findViewById(R.id.week1);
		clas11 = (TextView) findViewById(R.id.clas11);
		clas12 = (TextView) findViewById(R.id.clas12);
		clas13 = (TextView) findViewById(R.id.clas13);
		clas14 = (TextView) findViewById(R.id.clas14);
		clas15 = (TextView) findViewById(R.id.clas15);
		clas16 = (TextView) findViewById(R.id.clas16);
		week2 = (TextView) findViewById(R.id.week2);
		clas21 = (TextView) findViewById(R.id.clas21);
		clas22 = (TextView) findViewById(R.id.clas22);
		clas23 = (TextView) findViewById(R.id.clas23);
		clas24 = (TextView) findViewById(R.id.clas24);
		clas25 = (TextView) findViewById(R.id.clas25);
		clas26 = (TextView) findViewById(R.id.clas26);
	}

	/**
	 * private void read() { int ai = 0, j = 0, k; boolean hasSD = false; String
	 * SDPath; BufferedReader reader = null; try { hasSD =
	 * Environment.getExternalStorageState().equals(
	 * android.os.Environment.MEDIA_MOUNTED); if (false == hasSD) { ke[x][0] =
	 * ("�Ҳ���SD��"); } else {
	 * 
	 * SDPath = Environment.getExternalStorageDirectory().getPath(); //
	 * ke[x][0]=SDPath; File file = new File(SDPath + "//kebiao.txt");
	 * FileInputStream fis = new FileInputStream(file); BufferedInputStream in =
	 * new BufferedInputStream(fis); reader = new BufferedReader(new
	 * InputStreamReader(in, "GBK")); String data = null; for (ai = 0; ai < 5;
	 * ai++) { for (j = 0; j < 6; j++) { for (k = 0; k < 2; k++) { data =
	 * reader.readLine(); if (0 == k && data.equals("")) { break; } else { if (0
	 * == k) { ke[ai][j] = data; } else { ke[ai][j] += "\n" + data; } } } } }
	 * reader.close(); } } catch (FileNotFoundException e) { ke[x][0] +=
	 * ("�ļ���ʧ��" + e); return; } catch (IOException e) { ke[x][0] += ("�ļ���ȡ�쳣" +
	 * e); return; } }
	 */
	/**
	 * ��ȡexcel
	 */
	void new_class_info() {
		int i, j;
		for (i = 0; i < DAY_NUMBER; i++) {
			for (j = 0; j < TIME_NUMBER; j++) {
				ke[i][j] = new Class_info();
			}

		}
	}

	public void readExcel() throws IOException {
		try {
			/**
			 * ������������,����Excel�����ͼƬ�Լ������������͵Ķ�ȡ
			 **/
			// boolean hasSD = Environment.getExternalStorageState().equals(
			// android.os.Environment.MEDIA_MOUNTED);
			String SDPath = Environment.getExternalStorageDirectory().getPath();
			File file = new File(SDPath + "//kebiao.xls");

			InputStream is = new FileInputStream(file);
			HSSFWorkbook book = new HSSFWorkbook(is);
			book.getNumberOfSheets();
			// ��õ�һ������������
			Sheet sheet = book.getSheetAt(book.getSheetIndex("sheet1"));
			int Rows = sheet.getLastRowNum();
			int Cols = 8;
			int knum = 0, day = 0;
			int k;
			System.out.println("������:" + Rows);
			System.out.println("������:" + Cols);
			for (int i = 1; i < Cols; i++) {
				for (int j = 1; j <= Rows;) {
					String[] m1 = new String[8];
					if ("" == sheet.getRow(j).getCell(i).toString()) {
						k = j + BOX_NUMBER;
						for (j++; j < k && j <= Rows; j++) {
							if ("" != sheet.getRow(j).getCell(i).toString()) {
								break;
							}
						}
						if (j == k) {
							k = 1;
						} else {
							k = j - k + BOX_NUMBER;
						}
						k += knum;
						for (; knum < k; knum++) {
							ke[day][knum] = new Class_info();
						}
						if (knum >= TIME_NUMBER) {
							day++;
							knum = 0;
						}
					} else {
						for (k = 0; k < BOX_NUMBER; k++) {
							m1[k] = sheet.getRow(k + j).getCell(i).toString();
						}
						j += BOX_NUMBER + 1;
						ke[day][knum].setdata(m1);
						knum++;
						if (knum >= TIME_NUMBER) {
							day++;
							knum = 0;
						}
					}
				}
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			String SDPath = Environment.getExternalStorageDirectory().getPath();
			String filename = SDPath + "//kebiao.xls";
			HSSFWorkbook wb = new HSSFWorkbook();
			wb.createSheet("sheet1");
			FileOutputStream fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
			Toast.makeText(getApplicationContext(), e.toString() + "sss",
					Toast.LENGTH_LONG).show();
		}
	}

	private void combination() {
		int i, j, k;
		String[] something = new String[8];
		something[0] = "\n";
		something[1] = "\t";
		something[2] = "\t";
		something[3] = "\t";
		something[4] = "\n";
		something[5] = "\t";
		something[6] = "\t";
		something[7] = "";
		try {
			for (i = 0; i < 7; i++) {
				for (j = 0; j < 6; j++) {
					for (k = 0; k < BOX_NUMBER; k++) {
						if (ke[i][j].data == null) {
							StrKe[i][j] = "";
							break;
						} else {
							if (0 == k) {
								StrKe[i][j] = ke[i][j].data[k];
							} else {
								StrKe[i][j] += something[k - 1]
										+ ke[i][j].data[k];
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StrKe[0][0] = "���ļ�����";
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * ��TextView��д������
	 */
	private void write_to_textview() {
		if (true == first_page) {
			week1.setText(wek[weekday]);
			clas11.setText(StrKe[weekday][0]);
			clas12.setText(StrKe[weekday][1]);
			clas13.setText(StrKe[weekday][2]);
			clas14.setText(StrKe[weekday][3]);
			clas15.setText(StrKe[weekday][4]);
			clas16.setText(StrKe[weekday][5]);
		} else {
			week2.setText(wek[weekday]);
			clas21.setText(StrKe[weekday][0]);
			clas22.setText(StrKe[weekday][1]);
			clas23.setText(StrKe[weekday][2]);
			clas24.setText(StrKe[weekday][3]);
			clas25.setText(StrKe[weekday][4]);
			clas26.setText(StrKe[weekday][5]);
		}
	}

	/**
	 * ����activity����
	 * */
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > 120) {
			weekday++;
			if (weekday > DAY_NUMBER - 2) {
				weekday = 0;
			}
			first_page = !first_page;
			write_to_textview();
			flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.turnpage_right_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.turnpage_left_out));
			flipper.showNext();
		} else if (e1.getX() - e2.getX() < -120) {
			weekday--;
			if (weekday < 0) {
				weekday = DAY_NUMBER - 3;
			}
			first_page = !first_page;
			write_to_textview();
			flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.turnpage_left_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.turnpage_right_out));
			flipper.showPrevious();
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(KtableActivity.this, EditActivity.class);
		switch (id_textview) {
		case R.id.clas11:
		case R.id.clas21:
			intent.putExtra("time", 0);
			break;
		case R.id.clas12:
		case R.id.clas22:
			intent.putExtra("time", 1);
			break;
		case R.id.clas13:
		case R.id.clas23:
			intent.putExtra("time", 2);
			break;
		case R.id.clas14:
		case R.id.clas24:
			intent.putExtra("time", 3);
			break;
		case R.id.clas15:
		case R.id.clas25:
			intent.putExtra("time", 4);
			break;
		case R.id.clas16:
		case R.id.clas26:
			intent.putExtra("time", 5);
			break;
		}
		intent.putExtra("week", weekday);
		startActivity(intent);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	/**
	 * ���ز˵�
	 * */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater a = getMenuInflater();
		a.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * �����˵�
	 * */
	public boolean onOptionsItemSelected(MenuItem Item) {
		Intent open = new Intent();
		switch (Item.getItemId()) {
		case R.id.about:
			open.setClass(KtableActivity.this, about.class);
			startActivity(open);
			Toast.makeText(this, "�򿪹���", Toast.LENGTH_LONG).show();
			break;
		case R.id.help:
			open.setClass(KtableActivity.this, help.class);
			startActivity(open);
			Toast.makeText(this, "�򿪰���", Toast.LENGTH_LONG).show();
			break;
		case R.id.set:
			open.setClass(KtableActivity.this, set.class);
			startActivity(open);
			Toast.makeText(this, "������", Toast.LENGTH_LONG).show();
			break;
		case R.id.model:
			/*
			 * if (style != 4) { preStyle = style; style = 4; ContentValues b =
			 * new ContentValues(); b.put("data", 4); dbW.update("user", b,
			 * "id=?", new String[] { "4" }); set_activity_style(); } else {
			 * style = preStyle; ContentValues b = new ContentValues();
			 * b.put("data", preStyle); dbW.update("user", b, "id=?", new
			 * String[] { "before" }); }
			 */
			open.setClass(KtableActivity.this, online.class);
			startActivity(open);
			break;
		default:
			Toast.makeText(this, "δ֪����", Toast.LENGTH_LONG).show();
			break;

		}
		return true;
	}

	/**
	 * click������
	 */
	class TextListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(KtableActivity.this, EditActivity.class);
			switch (v.getId()) {
			case R.id.clas11:
			case R.id.clas21:
				intent.putExtra("time", 0);
				break;
			case R.id.clas12:
			case R.id.clas22:
				intent.putExtra("time", 1);
				break;
			case R.id.clas13:
			case R.id.clas23:
				intent.putExtra("time", 2);
				break;
			case R.id.clas14:
			case R.id.clas24:
				intent.putExtra("time", 3);
				break;
			case R.id.clas15:
			case R.id.clas25:
				intent.putExtra("time", 4);
				break;
			case R.id.clas16:
			case R.id.clas26:
				intent.putExtra("time", 5);
				break;
			}
			intent.putExtra("week", weekday);
			startActivity(intent);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		id_textview = v.getId();
		// click û�л���
		return false;

	}
}