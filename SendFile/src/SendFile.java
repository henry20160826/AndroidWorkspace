//�ͻ���

import java.net.*;
import java.io.*;

/**
 * SocketServer��SocketClient�����ļ��������ļ����͵�SocketServer�˱��棡
 * 
 * 
 */
public class SendFile {
	private static File f = null;
	private static FileInputStream fr = null;
	private static DataOutputStream dout = null;
	private static Socket s = null;

	public static void main(String[] args) {
		SendFile sc = new SendFile();
		sc.connectSocketServer(3000);
		sc.sendFile("lizhi.txt");
	}

	/**
	 * ���ӷ�������
	 * 
	 * @param port
	 *            Ҫ���ӵķ�����SocketServer�˿�
	 */
	public void connectSocketServer(int port) {
		try {
			s = new Socket(InetAddress.getLocalHost(), port);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// �����ļ�
	public void sendFile(String str) {

		byte[] b = new byte[1024];

		f = new File(str);
		try {
			// ���������
			dout = new DataOutputStream(new BufferedOutputStream(
					s.getOutputStream()));

			// �ļ�������
			fr = new FileInputStream(f);
			int n = fr.read(b);
			while (n != -1) {
				// ��������д������
				dout.write(b, 0, n);
				dout.flush();
				// �ٴζ�ȡn�ֽ�
				n = fr.read(b);
			}

			// �ر���
			fr.close();
			dout.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}