package hu.network;

//�ͻ���
import static hu.dataclass.Content.*;

import java.net.*;
import java.io.*;

import android.os.Environment;

/**
 * SocketServer��SocketClient�����ļ��������ļ����͵�SocketServer�˱��棡
 * 
 * 
 */
public class SendFile implements Runnable {
	private static File f = null;
	private static FileInputStream fr = null;
	private static DataOutputStream dout = null;
	private static Socket s = null;
	private String fileNameString;
	private String iP;
	private String SDPath;

	public SendFile(String iP, String fileNameString) {
		// TODO Auto-generated constructor stub
		this.iP = iP;
		SDPath = getSDPath();
		this.fileNameString =SDPath+ PATH + fileNameString;


	}

	public void run() {
		connectSocketServer(FILE_PORT);
	}

	/**
	 * ���ӷ�������
	 * 
	 * @param port
	 *            Ҫ���ӵķ�����SocketServer�˿�
	 */
	public void connectSocketServer(int port) {
		try {
			s = new Socket(iP, port);
			sendFile(fileNameString);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
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
			s.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();

	}

}