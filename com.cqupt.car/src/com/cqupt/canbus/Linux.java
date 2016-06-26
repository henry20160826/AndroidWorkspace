package com.cqupt.canbus;


import android.util.Log;
public class Linux {
	static {
		try {
			System.loadLibrary("uart");
			Log.i("JNI", "Trying to load libuart.so");
		} catch (UnsatisfiedLinkError ule) {
			Log.e("JNI", "WARNING:could not load libuart.so");
		}
	}

	/**
	 * �򿪴���
	 * 
	 * @param i
	 *            ���ں�
	 * @return �ɹ���fd>0;ʧ�ܣ�-1
	 */
	public static native int openUart(int i); // �򿪴���

	/**
	 * �رմ���
	 * 
	 * @param i
	 */
	public static native void closeUart(int i);// �رմ���

	/**
	 * ���ô��ڲ�����
	 * 
	 * @param i
	 *            ������ ���磺38400��115200
	 * @return �ɹ���0;ʧ�ܣ�-1
	 */
	public static native int setUart(int i);// ���ô��ڲ�����

	/**
	 * ��������
	 * 
	 * @param msg
	 *            ����
	 * @return
	 */
	public static native int sendMsgUart(String msg);// ������Ϣ

	/**
	 * ��������
	 * 
	 * @return
	 */
	public static native String receiveMsgUart();// ������Ϣ

	/**
	 * 
	 * @param databits
	 *            ���ݳ���
	 * @param stopbits
	 *            ֹͣλ
	 * @param parity
	 *            У�����ͣ�(int)n/N ��У�飻(int)o/O ��У��;(int)e/E żУ�飩
	 * @return
	 */
	public static native int setParity(int databits, int stopbits, int parity);// ������żУ��

	/**
	 * �����ֽ���������
	 * 
	 * @param msg
	 *            �ֽ���������
	 * @return int
	 */
	public static native int sendByteUart(byte[] msg);

	/**
	 * �����ֽ���������
	 * 
	 * @return
	 */
	public static native byte[] receiveByteUart();
}
