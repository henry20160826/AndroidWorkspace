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
 * ��Ϣͨ�����ļ�����ʹ�ò�ͬ�˿�
 * 
 */
public class MyClient {
	public static void main(String[] args) throws Exception {
		File dirname = new File(PATH); 
		if (!dirname.isDirectory()) 
		{ //Ŀ¼������ 
		     dirname.mkdir(); //����Ŀ¼
		} 
		Socket s = new Socket(SERVER_IP, COMMUNICATION_PORT);// ����Socket
		new Thread(new ClientThread(s)).start();// ����һ���߳�ר������ͨ��
		PrintStream ps = new PrintStream(s.getOutputStream());// ���������������Server��������
		String line = null;
		//new Thread(new ReceiveFile("copy1.txt")).start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// ���������������򻺴���������
		while ((line = br.readLine()) != null) {
			deal(line);
			ps.println(line);// �����ݷ��͵�Server
		}
	}

	public static void deal(String string) {//Ԥ������������
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
