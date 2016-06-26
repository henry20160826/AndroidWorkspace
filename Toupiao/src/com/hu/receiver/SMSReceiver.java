package com.hu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	/* 当收到短信时，就会触发此方法 */
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive");
		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int i = 0; i < messages.length; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			System.out.println(smsMessage[i].getDisplayMessageBody().toString());
		}
	
		
		
		// 产生一个Toast
		// Toast toast = Toast.makeText(context,
		// "短信内容: " + smsMessage[0].getMessageBody(),
		// Toast.LENGTH_LONG);
//		textView.setText("短信内容: " + smsMessage[0].getMessageBody());
		// 设置toast显示的位置
		// toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 200);
		// 显示该Toast
		// toast.show();
	}
}
