package hu.network;

//��������
import static hu.dataclass.Content.*;

import java.net.*;
import java.io.*;

/**
 * Socketserver�ӿͻ��˶�ȡ�ļ�������Ϊ�����ļ���
 * 
 * 
 */
public class ReceiveFile implements Runnable {
	private static ServerSocket ss = null;
	private static Socket s = null;

	private static File f = null;
	private static RandomAccessFile fw = null;

	private String fileNameString;

	public ReceiveFile(String fileNameString) {
		// TODO Auto-generated constructor stub
		this.fileNameString = fileNameString;
	}

	public void run() {
		initServer(FILE_PORT);
		getFile();
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

			// ���ɾ�������ļ�
			deleteFile(PATH + fileNameString);

			// ����Ҫ������ļ�
			f = new File(PATH + fileNameString);
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
			ss.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param sPath
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}