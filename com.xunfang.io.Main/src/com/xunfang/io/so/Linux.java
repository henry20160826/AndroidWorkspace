package com.xunfang.io.so;

import android.util.Log;
/**
 * <p>Title��IO DEMO</p>
 * <p>Description��JNI</p>
 * <p>Company��������Ѷ��ͨ�ż������޹�˾ </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @version 1.0.0.0
 * @author sas
 */
public class Linux {
	static {
		try {
			System.loadLibrary("xf_io");
			Log.i("IO", "Trying to load libxf_io.so");
		} catch (UnsatisfiedLinkError ule) {
			Log.e("IO", "WARNING:could not load libxf_io.so");
		}
	}

	/**
	 * ��IO��
	 * 
	 * @return �ɹ�������>0;ʧ�ܣ�-1
	 */
	public static native int open(); 

	/**
	 * �ر�IO��
	 * 
	 * @return 0
	 */
	public static native int close();

	/**
	 * ��ȡio��״̬
	 * @return io��״̬���� int[]
	 */
	public static native byte[] read();
	
	/**
	 * ����io��״̬
	 * @param cmd io��״̬����
	 * @return �ɹ�������>0;ʧ�ܣ�-1
	 */
	public static native int write(byte[] cmd);
	
	/**
	 * ��������io��״̬
	 * @param arg io��
	 * @param cmd io��״̬
	 * @return 0
	 */
	public static native int ioctl(int arg,int cmd);
}
