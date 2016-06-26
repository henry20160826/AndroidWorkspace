package com.xunfang.io.so;

import android.util.Log;
/**
 * <p>Title：IO DEMO</p>
 * <p>Description：JNI</p>
 * <p>Company：深圳市讯方通信技术有限公司 </p>
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
	 * 打开IO口
	 * 
	 * @return 成功：返回>0;失败：-1
	 */
	public static native int open(); 

	/**
	 * 关闭IO口
	 * 
	 * @return 0
	 */
	public static native int close();

	/**
	 * 读取io口状态
	 * @return io口状态数组 int[]
	 */
	public static native byte[] read();
	
	/**
	 * 设置io口状态
	 * @param cmd io口状态数组
	 * @return 成功：返回>0;失败：-1
	 */
	public static native int write(byte[] cmd);
	
	/**
	 * 单个设置io口状态
	 * @param arg io口
	 * @param cmd io口状态
	 * @return 0
	 */
	public static native int ioctl(int arg,int cmd);
}
