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
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));// 用于得到Client发送的数据
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			String content = null;
			while ((content = readFromClient()) != null) {
				// 将信息广播给每个接入的Client
				// for (Socket s : MySever.socketList) {
				// PrintStream ps = new PrintStream(s.getOutputStream());
				// ps.println(content);
				// }

				// PrintStream可自动刷新
				// PrintWrite不带自动刷新，需要代码刷新

				// 老版本
				// PrintStream ps = new PrintStream(s.getOutputStream());
				// InfoProcesser infoProcesser = new InfoProcesser(s
				// .getInetAddress().getHostAddress());
				// String Sendstring = infoProcesser.run(content);
				//
				// ps.println(Sendstring);
				System.out.println(content);
				// 新版本
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
			return br.readLine();// 返回Client发送的数据
		} catch (Exception e) {
			s.close();
			// TODO: handle exception
			// MySever.socketList.remove(s);// 无法连接，将接入的Clinet socket从记录中清除
		}
		return null;

	}

}
