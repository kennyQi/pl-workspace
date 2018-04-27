package plfx.jp.system;

import java.util.HashMap;
import java.util.Map;

public class PayBalancesConstants {
	public final static String PAY_BALANCES_TYPE = "0";
	
	public final static String PAY_BALANCES_TYPE_STRING = "国内机票";
	
	/**
	 * 预警类型type
	 */
	public final static Map<String,String> PAY_BALANCES_TYPE_MAP = new HashMap<String,String>();
	static {
		PAY_BALANCES_TYPE_MAP.put( PAY_BALANCES_TYPE, PAY_BALANCES_TYPE_STRING);
	}
}
