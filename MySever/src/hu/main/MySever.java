package hu.main;
import static hu.dataclass.Content.COMMUNICATION_PORT;
import static hu.dataclass.Content.PATH;
import hu.network.ServerThread;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * ��Ϣͨ��ʹ�ö˿�30000
 * �ļ�����ʹ�ö˿�3000
 */
public class MySever {
//	public static ArrayList<Socket> socketList = new ArrayList<Socket>();//��¼���еĽ���socket

	public static void main(String[] args) throws IOException{
		File dirname = new File(PATH); 
		if (!dirname.isDirectory()) 
		{ //Ŀ¼������ 
		     dirname.mkdir(); //����Ŀ¼
		} 
		ServerSocket ss=new ServerSocket(COMMUNICATION_PORT);
		while (true) {
			Socket s=ss.accept();//���û���µĽ���,����������ڴ�
//			socketList.add(s);//������������б�
			new Thread(new ServerThread(s)).start();//����һ�����߳�
		}
	}
}
