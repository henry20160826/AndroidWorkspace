package hu.main;

import static hu.dataclass.Content.*;
import hu.network.ReceiveFile;
import hu.network.SendFile;

import java.io.File;

public class InfoProcesser {

	private String iP;

	public InfoProcesser(String iP) {
		// TODO Auto-generated constructor stub
		this.iP = iP;
	}

	public String run(String string) {
		String[] manyString = string.split(GET_ASK);
		try {
			int keyword = Integer.parseInt(manyString[0]);
			if (ASK_ROOT == keyword) {
				String returnString = getFilesName(ROOT_CATALOG);
				return returnString;
			} else if (ASK_FILE == keyword) {
				SendFile sendFile = new SendFile(iP, manyString[1]);
				sendFile.run();
				return FILE_HAVE_BEEN_SEND;
			} else if (ASK_CATALOGUE == keyword) {
				String returnString = getFilesName(PATH+manyString[1]);
				return returnString;
			} else if (SEND_FILE == keyword) {
				new Thread(new ReceiveFile(manyString[1])).start();
				return ALLREADY + GET_ASK + manyString[1];
			}
		} catch (Exception e) {
			// TODO: handle exception
			return CANNOT_FIND_THIS_ORDER;
		}
		return CANNOT_FIND_THIS_ORDER;

	}

	public String getFilesName(String fileName) {
		File file = new File(fileName);
		File[] files = file.listFiles();
		String filesNameString = RESPOND_CATALOGUE + GET_ASK;
		// 不同文件以逗号（,）分隔开，文件名开头字符为？表示此文件为文件夹
		// 文件名中不能俺有问号（?）等控制字符，不会出现误判情况
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					filesNameString += files[i].getName() + ",";
				} else {
					filesNameString += "?" + files[i].getName() + ",";
				}
			}
		}
		filesNameString.substring(0, filesNameString.length() - 1);// 减去字符串中最后一个逗号
		return filesNameString;
	}

}
