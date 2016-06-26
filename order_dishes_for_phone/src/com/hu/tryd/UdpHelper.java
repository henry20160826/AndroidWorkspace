package com.hu.tryd;

import static com.hu.dataclass.Communication.*;
import android.os.Handler;
import android.os.Message;

public class UdpHelper extends UdpThread {

	private Handler myHandler;

	public UdpHelper(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
		myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String s = (String) msg.obj;
				switch (msg.what) {
				case NAME_AND_PASSWORD:
					break;
				case DISHES:

					break;
				default:
					break;
				}
			}
		};
	}

	public void sendNameAndPassword(String s) {
		send(NAME_AND_PASSWORD + GET_KEYWORD + s);
	}

	public void sendDishes(String s) {
		send(DISHES + GET_KEYWORD + s);
	}

	public Handler getHandler() {
		return myHandler;

	}
}
