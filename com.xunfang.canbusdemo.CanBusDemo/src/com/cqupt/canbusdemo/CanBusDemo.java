package com.cqupt.canbusdemo;

import com.xunfang.canbusdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CanBusDemo extends Activity {
	/** Called when the activity is first created. */
	// ��������ͱ���
	private Button send_button;
	// private Button receive_button;
	private Button close_button;
	private Button clean_button;
	private TextView view;
	private EditText edit;
	private String message = null;
	public static final int MESSAGE_CANBUS_DATA = 1;
	private ImageView revert;// ����"��ť"
	private TextView title;// ����
	private Cardoor cardoor;
	private ImageView[] imageViews;
	private final Handler refreshHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String message = msg.getData().getString("canbus_data");
			view.append(message);
			view.append("\n");
			cardoor.getDoorChange("4");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ô��ڲ���

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.can);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_title);
		revert = (ImageView) findViewById(R.id.common_title_revert);

		title = (TextView) findViewById(R.id.common_title_title);

		title.setText(R.string.app_name);
		int flag = Linux.openUart(2);
		Log.d("uart", "�򿪴���-->" + flag);
		MyConfig.setUART2_STATE(flag);
		if (MyConfig.getUART2_STATE() > 0) {
			Linux.setUart(MyConfig.UART2_BAUD_RATE);
			Linux.setParity(MyConfig.DATABITS, MyConfig.STOPBITS,
					MyConfig.PARITY);
		}
		// ʵ�������
		view = (TextView) findViewById(R.id.canbus_view);
		edit = (EditText) findViewById(R.id.canbus_edit);
		send_button = (Button) findViewById(R.id.canbus_button4);
		// receive_button = (Button) findViewById(R.id.canbus_button0);
		close_button = (Button) findViewById(R.id.canbus_button3);
		clean_button = (Button) findViewById(R.id.canbus_button2);
		if (flag < 0) {
			title.setText(getText(R.string.app_name) + "�����ڴ�ʧ��");
			send_button.setClickable(false);
			// receive_button.setClickable(false);
			// finish();
		} else {
			title.setText(getText(R.string.app_name) + "�����ڴ򿪳ɹ�");
			MyConfig.setStop_receive(false);
			new Thread(new CanbusReceiveThread(CanBusDemo.this, refreshHandler))
					.start();
			send_button.setClickable(true);
			// receive_button.setClickable(true);
		}

		// Ϊ��ť���ü�����
		send_button.setOnClickListener(send_listener);
		// receive_button.setOnClickListener(receive_listener);
		close_button.setOnClickListener(close_listener);
		clean_button.setOnClickListener(clean_listener);
		revert.setOnClickListener(close_listener);
		getImageViews();
		cardoor = new Cardoor(imageViews);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("uart", "���ڹر�");
		if (MyConfig.getUART2_STATE() > 0) {
			Linux.closeUart(2);
			MyConfig.setUART2_STATE(-1);
			MyConfig.setStop_receive(true);
		}
	}

	private void getImageViews() {
		imageViews = new ImageView[] {
				(ImageView) findViewById(R.id.imageView1),
				(ImageView) findViewById(R.id.imageView2),
				(ImageView) findViewById(R.id.imageView3),
				(ImageView) findViewById(R.id.imageView4) };
	}

	private Button.OnClickListener send_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			message = edit.getText().toString();
			if ("".equals(message)) {
				Toast.makeText(CanBusDemo.this, "�������ݲ���Ϊ��", Toast.LENGTH_SHORT)
						.show();
			} else {
				int message_len = message.getBytes().length;
				byte[] msg = new byte[message_len + 4];
				msg[0] = (byte) 0xE1;
				msg[1] = (byte) message_len;
				msg[2] = 0x00;
				msg[3] = 0x00;
				System.arraycopy(message.getBytes(), 0, msg, 4, message_len);
				// ��������
				Linux.sendByteUart(msg);
				StringBuffer sBuffer = new StringBuffer();
				for (byte b : msg) {
					sBuffer.append(b).append(" ");
				}
				System.out.println("����-->" + sBuffer.toString());
				view.append("�������ݣ�" + message);
				view.append("\n");
			}
		}
	};

	private Button.OnClickListener receive_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			// ��������
			byte[] receive_msg = Linux.receiveByteUart();
			if (receive_msg != null) {
				int msg_len = receive_msg.length;
				StringBuffer sBuffer = new StringBuffer();
				for (byte b : receive_msg) {
					sBuffer.append(b).append(" ");
				}
				System.out.println("����--" + sBuffer.toString());//
				// *****************************************************
				// if (receive_msg[0] == (byte) 0xD1 && receive_msg[1] == 0x00
				// && receive_msg[2] == 0x00 && msg_len == 11) {
				if (receive_msg[0] == (byte) 0xD1 && msg_len == 11) {
					byte[] message = new byte[8];
					System.arraycopy(receive_msg, 3, message, 0, 8);
					if (message[7] == (byte) 0xFF) {
						int data_len = 0;
						for (int i = 0; i <= 8; i++) {
							if (message[i] == (byte) 0xFF) {
								data_len = i;
								break;
							}
						}
						if (data_len > 0) {
							byte[] data = new byte[data_len];
							System.arraycopy(message, 0, data, 0, data_len);
							StringBuffer sBuffer1 = new StringBuffer();
							for (byte b : data) {
								sBuffer1.append(b).append(" ");
							}
							System.out.println("����--" + sBuffer1.toString());
							view.append("�������ݣ�" + new String(data));
							view.append("\n");
						}
					} else {
						view.append("�������ݣ�" + new String(message));
						view.append("\n");
					}
				}
			}
		}
	};

	private Button.OnClickListener clean_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			view.setText(null);
		}
	};

	private Button.OnClickListener close_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					CanBusDemo.this);
			dialog.setIcon(android.R.drawable.ic_menu_help);
			dialog.setTitle("ȷ���˳���");
			dialog.setPositiveButton("ȷ��", new OnClickLiner_OK());
			dialog.setNegativeButton("ȡ��", new OnClickLiner_Cancel());
			dialog.show();
		}
	};

	/**
	 * �������ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �жϷ��ؼ��Ƿ񱻰���
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(android.R.drawable.ic_menu_help);
			dialog.setTitle("ȷ���˳���");
			dialog.setPositiveButton("ȷ��", new OnClickLiner_OK());
			dialog.setNegativeButton("ȡ��", new OnClickLiner_Cancel());
			dialog.show();
		}
		return false;
	}

	/**
	 * alertDialog�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class OnClickLiner_OK implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			// �ر�CanBus
			if (MyConfig.getUART2_STATE() > 0) {
				Linux.closeUart(2);
				MyConfig.setUART2_STATE(-1);
				MyConfig.setStop_receive(true);
			}
			// �ر�Activity
			finish();
		}

	}

	/**
	 * alertDialog�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class OnClickLiner_Cancel implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dialog.cancel();
		}

	}
}