package hu.dataclass;

public class Content {
	// 服务器ip
	public final static String SERVER_IP = "127.0.0.1";

	// // 编码方式
	// public final static String BIAN_MA = "utf-8";

	// 端口
	public final static int COMMUNICATION_PORT = 3333;
	public final static int FILE_PORT = 3000;

	// 通信参数
	public final static int ASK_ROOT = 111;
	public final static int ASK_FILE = 222;
	public final static int ASK_CATALOGUE = 333;
	public final static int SEND_FILE = 444;
	public final static int RESPOND_CATALOGUE = 555;
	public final static int ALLREADY = 666;

	public final static String ROOT_CATALOG = "e://MyServer//";
	public final static String GET_ASK = "#";

	public final static String FILE_HAVE_BEEN_SEND = "文件已发送";
	public final static String CANNOT_FIND_THIS_ORDER = "找不到此命令";

	public final static String PATH = "e://MyClient//";
}
