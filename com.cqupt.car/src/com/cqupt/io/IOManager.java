package com.cqupt.io;

import com.cqupt.io.Linux;

public class IOManager extends Linux {

	/**
	 * @param args
	 */
	public static byte readOneIO(int IOnum) {
		//IO�� 1,3,5,7 ��ӦIOnum 0,1,2,3
		byte result[] = read();
		if (IOnum > -1 && IOnum < 4) {
			return result[IOnum];
		}
		return 2;
	}
}
