package com.ai.showing.h2;

/**
 * 系统常量
 * 
 * @author Luorj
 * 
 */
public interface Constant {

	/** 系统缓存名 */
	String CACHE_SYSTEM = "SYSTEM";

	/** 系统缓存名 */
	String CACHE_SESSION = "SESSION";
	
	/**经度缓存名**/
	String LOGITUDE_USER_Y="LOGITUDE_USER_Y";
	
	/**纬度缓存名**/
	String LOGITUDE_USER_X="LOGITUDE_USER_X";

	/** Token cache */
	String CACHE_ACCESS_TOKEN = "ACCESS_TOKEN";

	/** 发送短信 */
	String SEND_SMS = "100000";

	/** 发送短信验证码 */
	String SEND_SMS_VCODE = "100001";

	/** 签到 */
	String BIND = "BIND";

	/** 签到 */
	String CHECK_IN = "CHECK_IN";

	/** 话费查询 */
	String QUERY_FEE = "QUERY_FEE";

	/** 流量查询 */
	String QUERY_FLOW = "QUERY_FLOW";

	String CHARSET_NAME = "GBK";

	public static class Give {
		//未处理
		public static final String WAITING = "0";
		//成功
		public static final String SUCCESS = "1";
		//失败
		public static final String FAILURE = "2";
		//一次处理的数目
		public static final int BATCHNUM = 500; 
	}
}
