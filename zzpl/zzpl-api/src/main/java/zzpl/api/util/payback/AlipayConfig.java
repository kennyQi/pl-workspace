package zzpl.api.util.payback;

import hg.common.util.SysProperties;

public class AlipayConfig {
	
	public static String partner = SysProperties.getInstance().get("partner");//"2088701074577516";
	
	public static String key = SysProperties.getInstance().get("MD5key");//"gfu3gpkuiam1idh45do66ay0se4a83jv";

	public static String notify_url = SysProperties.getInstance().get("ZZPLPayBackNotify");
	
	public static String log_path = "/main/apps/logs/" + System.currentTimeMillis()+".txt";

	public static String input_charset = "utf-8";
	
	public static String sign_type = "MD5";
	
	public static String transport = "http";

}
