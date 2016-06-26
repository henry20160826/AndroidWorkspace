package hu.activity;

import static hu.dataclass.Content.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.myphone.R;

import hu.network.MyClient;
import hu.network.ReceiveFile;
import hu.tool.MyHandler;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MyClient myClient;
	private ListView listView;
	private MyHandler mhandler;
	private SimpleAdapter simpleAdapter;
	private ArrayList<HashMap<String, Object>> arrayList;
	private String fileName;
	private TextView catalogueTextView;
	private Button button;
	private int mode = 0;
	private String SDPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getViews();
		connect();
		SDPath = getSDPath();
		File dirname = new File(SDPath + PATH);
		if (!dirname.isDirectory()) { // Ŀ¼������
			dirname.mkdir(); // ����Ŀ¼
		}
		// new Thread(new ReceiveFile(SDPath + "/copy1.txt")).start();
	}

	public void connect() {
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					myClient = new MyClient(mhandler);
					myClient.sendASK_ROOT();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.toString());
					// �޷����ӵ�������
					String string = getResources().getString(
							R.string.cannot_connect);
					System.out.println(string);
					// Toast.makeText(MainActivity.this, string,
					// Toast.LENGTH_LONG)
					// .show();
				}
			}

		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void getViews() {

		getListView();
		mhandler = new MyHandler(this, simpleAdapter, arrayList);
		catalogueTextView = (TextView) findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (0 == mode) {
					mode = 1;
					button.setText("����Ŀ¼");
					String s = getFilesName(SDPath + PATH);
					Message message = new Message();
					message.what = RESPOND_CATALOGUE;
					String manyString[] = s.split(GET_ASK);
					if (manyString.length==2) {
						message.obj = manyString[1];
					
					}else{
						message.obj = null;
					}
					mhandler.sendMessage(message);
				} else {
					mode = 0;
					button.setText("�ƶ�Ŀ¼");
					myClient.sendASK_ROOT();
				}
			}

		});
	}

	public void getListView() {
		listView = (ListView) findViewById(R.id.listView1);
		arrayList = new ArrayList<HashMap<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, arrayList,// ����Դ
				R.layout.item_listview,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { ITEM_IMAGE, ITEM_TIITLE, ITEM_TEXT },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.imageView1, R.id.tv_filename });
		listView.setAdapter(simpleAdapter);// ��������
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arrayList.get(arg2).get(ITEM_TEXT).equals(FOLDER)) {
					// ���Ŀ��Ϊ�ļ���
					// �����ļ���

					String folderString = arrayList.get(arg2).get(ITEM_TIITLE)
							.toString();
					catalogueTextView.append(folderString + "//");
					myClient.sendASK_CATALOGUE(folderString);

				} else {
					// ���Ŀ���ļ�
					fileName = arrayList.get(arg2).get(ITEM_TIITLE).toString();
					// �����Ի���
					if (0 == mode) {

						Dialog dialog = new AlertDialog.Builder(
								MainActivity.this)
								.setTitle("��ʾ")
								.setMessage("�Ƿ�Ҫ���ش��ļ���")
								.setPositiveButton("ȷ��", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// Toast.makeText(MainActivity.this,
										// "�Һ�ϲ�����ĵ�Ӱ��", Toast.LENGTH_LONG)
										// .show();
										new Thread(new ReceiveFile(fileName))
												.start();
										String s = catalogueTextView.getText()
												.toString();
										myClient.sendASK_FILE(s + fileName);
										// try {
										// saveToSDCard("���廪", "���廪");
										// } catch (Exception e) {
										// // TODO Auto-generated catch block
										// e.printStackTrace();
										// }

									}
								})
								.setNegativeButton("ȡ��", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// Toast.makeText(MainActivity.this,
										// "�Ҳ�ϲ�����ĵ�Ӱ��", Toast.LENGTH_LONG)
										// .show();
									}
								}).create();
						dialog.show();
					} else {
						Dialog dialog = new AlertDialog.Builder(
								MainActivity.this)
								.setTitle("��ʾ")
								.setMessage("�Ƿ�Ҫ�ϴ����ļ���")
								.setPositiveButton("ȷ��", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// Toast.makeText(MainActivity.this,
										// "�Һ�ϲ�����ĵ�Ӱ��", Toast.LENGTH_LONG)
										// .show();
										myClient.sendSEND_FILE(fileName);
										// try {
										// saveToSDCard("���廪", "���廪");
										// } catch (Exception e) {
										// // TODO Auto-generated catch block
										// e.printStackTrace();
										// }

									}
								})
								.setNegativeButton("ȡ��", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// Toast.makeText(MainActivity.this,
										// "�Ҳ�ϲ�����ĵ�Ӱ��", Toast.LENGTH_LONG)
										// .show();
									}
								}).create();
						dialog.show();
					}
				}
			}
		});

	}

	public String getFilesName(String fileName) {
		File file = new File(fileName);
		File[] files = file.listFiles();
		String filesNameString = RESPOND_CATALOGUE + GET_ASK;
		// ��ͬ�ļ��Զ��ţ�,���ָ������ļ�����ͷ�ַ�Ϊ����ʾ���ļ�Ϊ�ļ���
		// �ļ����в��ܰ����ʺţ�?���ȿ����ַ�����������������
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					filesNameString += files[i].getName() + ",";
				} else {
					filesNameString += "?" + files[i].getName() + ",";
				}
			}
		}
		filesNameString.substring(0, filesNameString.length() - 1);// ��ȥ�ַ��������һ������
		return filesNameString;
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();

	}
	// public void saveToSDCard(String filename, String content) throws
	// Exception {
	// File file = new File("/mnt/sdcard", filename);
	// OutputStream out = new FileOutputStream(file);
	// out.write(content.getBytes());
	// out.close();
	// }
}
