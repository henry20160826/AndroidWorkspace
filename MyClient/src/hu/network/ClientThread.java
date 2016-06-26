package hu.network;

import static hu.dataclass.Content.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket s;

	BufferedReader br = null;

	public ClientThread(Socket s) throws IOException {
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));// ������������ȡSocket�е�����
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String content = null;
			while ((content = br.readLine()) != null) {
				System.out.println(content);// ��ӡ����Server�õ�����Ϣ
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

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
