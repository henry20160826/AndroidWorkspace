package hu.network;

import hu.main.InfoProcesser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

	Socket s = null;
	BufferedReader br = null;

	public ServerThread(Socket s) throws IOException {
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));// ���ڵõ�Client���͵�����
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			String content = null;
			while ((content = readFromClient()) != null) {
				// ����Ϣ�㲥��ÿ�������Client
				// for (Socket s : MySever.socketList) {
				// PrintStream ps = new PrintStream(s.getOutputStream());
				// ps.println(content);
				// }

				// PrintStream���Զ�ˢ��
				// PrintWrite�����Զ�ˢ�£���Ҫ����ˢ��

				// �ϰ汾
				// PrintStream ps = new PrintStream(s.getOutputStream());
				// InfoProcesser infoProcesser = new InfoProcesser(s
				// .getInetAddress().getHostAddress());
				// String Sendstring = infoProcesser.run(content);
				//
				// ps.println(Sendstring);
				System.out.println(content);
				// �°汾
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				InfoProcesser infoProcesser = new InfoProcesser(s
						.getInetAddress().getHostAddress());

				String Sendstring = infoProcesser.run(content);
				pw.println(Sendstring);
				pw.flush();

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private String readFromClient() throws IOException {
		try {
			return br.readLine();// ����Client���͵�����
		} catch (Exception e) {
			s.close();
			// TODO: handle exception
			// MySever.socketList.remove(s);// �޷����ӣ��������Clinet socket�Ӽ�¼�����
		}
		return null;

	}

}
