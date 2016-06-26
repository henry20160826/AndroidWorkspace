package com.cqupt.canbus;



public class MyConfig {
	
	/**
	 * 串口2状态
	 */
	private static int UART2_STATE = -1;
	/**
	 * 串口2波特率
	 */
	public static final int UART2_BAUD_RATE = 115200;
	/**
	 * 数据长度
	 */
	public static final int DATABITS = 8;
	/**
	 * 停止位
	 */
	public static final int STOPBITS = 1;
	/**
	 * 校验类型
	 */
	public static final int PARITY = 'N';

	/**
	 * 是否停止接收串口消息
	 */
	private static boolean stop_receive = false;

	public static int getUART2_STATE() {
		return UART2_STATE;
	}

	public static void setUART2_STATE(int uART2STATE) {
		UART2_STATE = uART2STATE;
	}

	public static boolean isStop_receive() {
		return stop_receive;
	}

	public static void setStop_receive(boolean stopReceive) {
		stop_receive = stopReceive;
	}
	
}
