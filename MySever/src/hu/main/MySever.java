package hu.main;
import static hu.dataclass.Content.COMMUNICATION_PORT;
import static hu.dataclass.Content.PATH;
import hu.network.ServerThread;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 信息通信使用端口30000
 * 文件传输使用端口3000
 */
public class MySever {
//	public static ArrayList<Socket> socketList = new ArrayList<Socket>();//记录所有的接入socket

	public static void main(String[] args) throws IOException{
		File dirname = new File(PATH); 
		if (!dirname.isDirectory()) 
		{ //目录不存在 
		     dirname.mkdir(); //创建目录
		} 
		ServerSocket ss=new ServerSocket(COMMUNICATION_PORT);
		while (true) {
			Socket s=ss.accept();//如果没有新的接入,程序会阻塞在此
//			socketList.add(s);//将新请求加入列表
			new Thread(new ServerThread(s)).start();//创建一个新线程
		}
	}
}
