package hu.network;

import static hu.dataclass.Content.*;
import hu.tool.MyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Message;

public class ClientThread implements Runnable {

	private Socket s;
	private Handler handler;

	BufferedReader br = null;

	public ClientThread(Socket s, Handler handler) throws IOException {
		this.s = s;
		this.handler = handler;
		br = new BufferedReader(new InputStreamReader(s.getInputStream(),"GB2312"));// 绑定输入流，读取Socket中的数据
	}

	@TargetApi(9)
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String content = null;
			while ((content = br.readLine()) != null) {
				// // 将字符串转码
				// String strTarget = new String(content.getBytes(Charset
				// .forName(BIAN_MA)), BIAN_MA);
				// 打印出从Server得到的消息
				System.out.println(content);
				String[] manyString = content.split(GET_ASK);
				try {
					int keyword = Integer.parseInt(manyString[0]);
					if (ALLREADY == keyword) {
						SendFile sendFile = new SendFile(SERVER_IP, manyString[1]);
						sendFile.run();
						System.out.println(FILE_HAVE_BEEN_SEND);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				Message message = new Message();
				int order = Integer.parseInt(manyString[0]);
				message.what = order;
				message.obj = manyString[1];
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
