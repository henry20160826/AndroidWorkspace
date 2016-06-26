package hu.main;

import static hu.dataclass.Content.*;
import hu.network.ClientThread;
import hu.network.ReceiveFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 信息通信与文件传输使用不同端口
 * 
 */
public class MyClient {
	public static void main(String[] args) throws Exception {
		File dirname = new File(PATH); 
		if (!dirname.isDirectory()) 
		{ //目录不存在 
		     dirname.mkdir(); //创建目录
		} 
		Socket s = new Socket(SERVER_IP, COMMUNICATION_PORT);// 创建Socket
		new Thread(new ClientThread(s)).start();// 创建一个线程专门用于通信
		PrintStream ps = new PrintStream(s.getOutputStream());// 绑定输出流，用于向Server发送数据
		String line = null;
		//new Thread(new ReceiveFile("copy1.txt")).start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// 绑定输入流，键盘向缓存输入数据
		while ((line = br.readLine()) != null) {
			deal(line);
			ps.println(line);// 将数据发送到Server
		}
	}

	public static void deal(String string) {//预处理输入命令
		String[] manyString = string.split(GET_ASK);
		try {
			int keyword = Integer.parseInt(manyString[0]);
			if (ASK_FILE == keyword) {
				new Thread(new ReceiveFile(manyString[1])).start();
			} else if (SEND_FILE == keyword) {
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
