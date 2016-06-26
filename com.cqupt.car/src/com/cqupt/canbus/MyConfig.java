package com.cqupt.canbus;



public class MyConfig {
	
	/**
	 * ����2״̬
	 */
	private static int UART2_STATE = -1;
	/**
	 * ����2������
	 */
	public static final int UART2_BAUD_RATE = 115200;
	/**
	 * ���ݳ���
	 */
	public static final int DATABITS = 8;
	/**
	 * ֹͣλ
	 */
	public static final int STOPBITS = 1;
	/**
	 * У������
	 */
	public static final int PARITY = 'N';

	/**
	 * �Ƿ�ֹͣ���մ�����Ϣ
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
