package lxs.pojo.util.line;

import java.util.Map;
import java.util.TreeMap;

public class LineOrderConstants {
	
	/**
	 * 游客类型MAP
	 */
	public static final Map<String,String>	TTAVELER_TYPE_MAP = new TreeMap<String,String>();
	
	public final static Integer TYPE_ADULT = 1;
	public final static Integer TYPE_CHILD = 2;
	
	static{
		TTAVELER_TYPE_MAP.put(TYPE_ADULT+"", "成人");
		TTAVELER_TYPE_MAP.put(TYPE_CHILD+"", "儿童");
	}
	
	/**
	 * 游客证件类型MAP
	 */
	public static final Map<String,String>	TTAVELER_ID_TYPE_MAP = new TreeMap<String,String>();
	
	public final static Integer TTAVELER_ID_TYPE_SFZ= 1;
	public final static Integer TTAVELER_ID_TYPE_HZ= 2;
	
	static{
		TTAVELER_ID_TYPE_MAP.put(TTAVELER_ID_TYPE_SFZ+"", "身份证");
		TTAVELER_ID_TYPE_MAP.put(TTAVELER_ID_TYPE_HZ+"", "护照");
	}
	
	/**
	 * 订单支付类型MAP
	 */
	public static final Map<String,String> PAY_TYPE_MAP = new TreeMap<String,String>();
	public final static Integer PAY_TYPE_ALIPAY= 1;
	
	static{
		PAY_TYPE_MAP.put(PAY_TYPE_ALIPAY+"", "支付宝");
	}
	
	/**
	 * 订单付款类型MAP
	 * 1:定金;2:尾款;3:全款
	 */
	public static final Map<String,String> SHOP_PAY_TYPE_MAP = new TreeMap<String,String>();
	public final static Integer SHOP_PAY_TYPE_BARGAIN_MONEY= 1;
	public final static Integer SHOP_PAY_TYPE_TAIL_MONEY= 2;
	public final static Integer SHOP_PAY_TYPE_ALL_MONEY= 3;
	
	static{
		SHOP_PAY_TYPE_MAP.put(SHOP_PAY_TYPE_BARGAIN_MONEY+"", "定金");
		SHOP_PAY_TYPE_MAP.put(SHOP_PAY_TYPE_TAIL_MONEY+"", "尾款");
		SHOP_PAY_TYPE_MAP.put(SHOP_PAY_TYPE_ALL_MONEY+"", "全款");
	}

}

