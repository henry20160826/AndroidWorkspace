package com.cqupt.canbusdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * <p>
 * Title：3G物联网开发终端canbus实验
 * </p>
 * <p>
 * Description：接收canbus消息的线程
 * </p>
 * <p>
 * Company：深圳市讯方通信技术有限公司
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @version 1.0.0.0
 * @author 3G终端应用开发组
 * 
 */
public class CanbusReceiveThread implements Runnable {

	private Context context;
	private Handler refreshHandler;

	public CanbusReceiveThread(Context context, Handler refreshHandler) {
		this.context = context;
		this.refreshHandler = refreshHandler;
	}

	/**
	 * 接收串口消息并处理
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("开始接收canbus信息" + MyConfig.isStop_receive());
		while (!MyConfig.isStop_receive()) {
			if (MyConfig.getUART2_STATE() > 0) {
				byte[] receive_msg = Linux.receiveByteUart();
				if (receive_msg != null && receive_msg.length >= 8) {
					int msg_len = receive_msg.length;
					StringBuffer sBuffer = new StringBuffer();
					for (byte b : receive_msg) {
						sBuffer.append(b).append(" ");
					}
					//System.out.println("接收--" + sBuffer.toString());
					String temp = sBuffer.toString();
					String temps[] = temp.split("-47");
					for(int i = 1; i < temps.length; i++){
						String info[] = temps[i].split(" ");
						if(info.length < 7){
							continue;
						}
						if(info[1].equals("5") && info[2].equals("-35")){
							System.out.println("接收--" + temps[i]);
							Message message=new Message();
							Bundle bundle=new Bundle();
							bundle.putString("canbus_data", temps[i]);
							message.setData(bundle);
							refreshHandler.sendMessage(message);	
						}
		
					}
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

}
