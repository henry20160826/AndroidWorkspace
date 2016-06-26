package com.hu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	/* ���յ�����ʱ���ͻᴥ���˷��� */
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive");
		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int i = 0; i < messages.length; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			System.out.println(smsMessage[i].getDisplayMessageBody().toString());
		}
	
		
		
		// ����һ��Toast
		// Toast toast = Toast.makeText(context,
		// "��������: " + smsMessage[0].getMessageBody(),
		// Toast.LENGTH_LONG);
//		textView.setText("��������: " + smsMessage[0].getMessageBody());
		// ����toast��ʾ��λ��
		// toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 200);
		// ��ʾ��Toast
		// toast.show();
	}
}
