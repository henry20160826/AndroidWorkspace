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
 * ��Ϣͨ�����ļ�����ʹ�ò�ͬ�˿�
 * 
 */
public class MyClient {
	public Socket socket;
	public PrintStream printStream;
	public Handler Handler;

	public MyClient(Handler handler) throws Exception {
		// TODO Auto-generated constructor stub
		this.Handler = handler;
		socket = new Socket(SERVER_IP, COMMUNICATION_PORT);// ����Socket
		new Thread(new ClientThread(socket, handler)).start();// ����һ���߳�ר������ͨ��
		printStream = new PrintStream(socket.getOutputStream());// ���������������Server��������
		System.out.println("���ӳɹ�");
		// String line = null;
		// new Thread(new ReceiveFile("copy1.txt")).start();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));// ���������������򻺴���������
		// while ((line = br.readLine()) != null) {
		// ps.println(line);// �����ݷ��͵�Server
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
