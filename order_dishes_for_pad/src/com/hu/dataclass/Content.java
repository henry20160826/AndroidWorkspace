package com.hu.dataclass;

import com.example.order_dishes.R;

public class Content {
	// TODO 
	public static final String TABLE_NUM = "52345";
	public static final String WAITER_NAME = "fw";
	public static final String CONNECTION = "已连接";
	
	//餐桌状态
	public static final String TABLE_STATE="TABLE_STATE";
	public static final String STATE_FREE = "空闲";
	public static final String STATE_IN_USE = "使用中";
	public static final String STATE_ORDER = "预约";
	
	// public static final String STATE_NEED_CLEAN="需要清理";
	//菜单显示状态
	public static final int DISH_MODE = 65464;
	public static final int DRINK_MODE = 34213;
	public static final int ZHUSHI_MODE = 23544;

	// TODO 颜色
	public static final int GREEN = 43532;
	public static final int RED = 55625;
	public static final int DARK_GREEN = 65436;
	public static final int BLUE = 75657;

	public static final int ERROR = 4356433;


	// TODO 网络信息分割常量
	public static final String SPLIT_TYPE_INFO = "#";
	public static final String SPLIT_INFO_BIG = "@";
	public static final String SPLIT_INFO_MID = ";";
	public static final String SPLIT_INFO_SML = ",";

	// TODO 图片id
	public static final int[] imagesOfDish = { R.drawable.dongporou,
			R.drawable.duojiaoyutou, R.drawable.goubaojiding,
			R.drawable.hongshaorou, R.drawable.huiguorou, R.drawable.kelejichi,
			R.drawable.koushuiji, R.drawable.laziji, R.drawable.mapodoufu,
			R.drawable.shuizhuroupian, R.drawable.tangculiji,
			R.drawable.youmendaxia, R.drawable.yuxiangqiezi,
			R.drawable.yuxiangrousi };

	public static final int[] imagesOfZhushi = { R.drawable.mantou,
			R.drawable.chaomantou, R.drawable.baozi, R.drawable.miantiao,
			R.drawable.mifan, R.drawable.shaobing, R.drawable.guotie,
			R.drawable.shuijiao };

	public static final int[] imagesOfDrink = { R.drawable.maotai,
			R.drawable.wuliangye, R.drawable.jinliufu, R.drawable.jiannanchun,
			R.drawable.langjiu, R.drawable.fenjiu, R.drawable.luzhoulaojiao,
			R.drawable.yinliao };

	
	//文字提示
	public static final String CAN_NOT_EMPTY = "用户名密码不能为空";
	public static final String SERVER_FOR_YOU = "  竭诚为您服务";
	public static final String ALERT = "提示";
	public static final String HAVE_FINISHED_ORDER = "确定完成点餐？";
	public static final String OK = "确定";
	public static final String CANCEL = "取消";

}
