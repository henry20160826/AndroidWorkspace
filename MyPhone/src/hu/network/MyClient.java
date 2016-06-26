package hu.network;

import static hu.dataclass.Content.*;
import hu.network.ClientThread;
import hu.network.ReceiveFile;
import hu.tool.MyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;

import android.os.Handler;

/**
 * 信息通信与文件传输使用不同端口
 * 
 */
public class MyClient {
	public Socket socket;
	public PrintStream printStream;
	public Handler Handler;

	public MyClient(Handler handler) throws Exception {
		// TODO Auto-generated constructor stub
		this.Handler = handler;
		socket = new Socket(SERVER_IP, COMMUNICATION_PORT);// 创建Socket
		new Thread(new ClientThread(socket, handler)).start();// 创建一个线程专门用于通信
		printStream = new PrintStream(socket.getOutputStream());// 绑定输出流，用于向Server发送数据
		System.out.println("连接成功");
		// String line = null;
		// new Thread(new ReceiveFile("copy1.txt")).start();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));// 绑定输入流，键盘向缓存输入数据
		// while ((line = br.readLine()) != null) {
		// ps.println(line);// 将数据发送到Server
		// }

	}

	public void sendASK_ROOT() {
		printStream.println(ASK_ROOT);
	}

	public void sendASK_FILE(String fileNameString) {
		printStream.println(ASK_FILE + GET_ASK + fileNameString);
	}

	public void sendASK_CATALOGUE(String calalogueString) {
		try {
			calalogueString = URLEncoder.encode(calalogueString, "iso-8859-1");
			printStream.println(ASK_CATALOGUE + GET_ASK + calalogueString);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

	}

	public void sendSEND_FILE(String fileNameString) {
		printStream.println(SEND_FILE + GET_ASK + fileNameString);
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
