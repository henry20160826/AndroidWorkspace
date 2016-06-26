package com.hu.udp;

import static com.hu.dataclass.Communication.*;
import android.os.Handler;

public class UDP extends UDPHelper {

	public UDP(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	public void sendNameAndPassword(String name, String password) {
		sendData(NAME_AND_PASSWORD + GET_KEYWORD + name + GET_INFO + password);
	}

	public void sendDishes(String tableNum, String price, String Dishes) {
		sendData(DISHES + GET_KEYWORD + tableNum + GET_INFO + price + GET_INFO
				+ Dishes);
		close();
	}
}
