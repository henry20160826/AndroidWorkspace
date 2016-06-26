//��������

import java.net.*;
import java.io.*;

/**
 * Socketserver�ӿͻ��˶�ȡ�ļ�������Ϊ�����ļ���
 * 
 * 
 */
public class ReceiveFile {
	private static ServerSocket ss = null;
	private static Socket s = null;

	private static File f = null;
	private static RandomAccessFile fw = null;

	public static void main(String[] args) {
		ReceiveFile sk = new ReceiveFile();
		sk.initServer(3000);
		sk.getFile();
	}

	/**
	 * ��ʼ����������
	 * 
	 * @param port
	 *            ��������Ҫʹ�õĶ˿�
	 */
	public void initServer(int port) {
		try {
			ss = new ServerSocket(port);
			s = ss.accept();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// ��SocketClient��ȡ�ļ�
	public void getFile() {
		byte[] b = new byte[1024];

		try {
			// ������������s.getInputStream();
			InputStream in = s.getInputStream();
			DataInputStream din = new DataInputStream(new BufferedInputStream(
					in));

			// ����Ҫ������ļ�
			f = new File("copy.txt");
			fw = new RandomAccessFile(f, "rw");

			int num = din.read(b);
			while (num != -1) {
				// ���ļ���д��0~num���ֽ�
				fw.write(b, 0, num);
				// ����num���ֽ��ٴ�д���ļ�
				fw.skipBytes(num);
				// ��ȡnum���ֽ�
				num = din.read(b);
			}
			// �ر����룬�����
			din.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}