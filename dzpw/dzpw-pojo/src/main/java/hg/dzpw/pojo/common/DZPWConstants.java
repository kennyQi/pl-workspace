package hg.dzpw.pojo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：电子票务常量
 * @类修改者：
 * @修改日期：2014-11-20下午5:35:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午5:35:23
 */
public class DZPWConstants {
	
	// --------------------- 证件类型 ---------------------
	/** 证件类型 1、 身份证 */
	public final static String CER_TYPE_IDENTITY = "1";
	/** 证件类型 2、 军官证 */
	public final static String CER_TYPE_OFFICER = "2";
	/** 验票方式 3、 驾驶证 */
	public final static String CER_TYPE_DRIVING = "3";
	/** 证件类型 4、 护照 */
	public final static String CER_TYPE_PASSPORT = "4";
	
	public final static Map<String, String> CER_TYPE_NAME = new HashMap<String, String>();

	static {
		CER_TYPE_NAME.put(CER_TYPE_IDENTITY, "身份证");
		CER_TYPE_NAME.put(CER_TYPE_OFFICER, "军官证");
		CER_TYPE_NAME.put(CER_TYPE_DRIVING, "驾驶证");
		CER_TYPE_NAME.put(CER_TYPE_PASSPORT, "护照");
	}
	
	// --------------------- 性别 ---------------------
	/** 性别  0男 */
	public final static String GENDER_M = "0";
	/** 性别 1女 */
	public final static String GENDER_F = "1";
	
	public final static Map<String, String> GENDER_NAME = new HashMap<String, String>();
	static {
		GENDER_NAME.put(GENDER_M, "男");
		GENDER_NAME.put(GENDER_F, "女");
	}

	// --------------------- 套票（套票）状态 ---------------------
	
	/** 套票（套票）状态：未入园 */
	public final static Integer GROUP_TICKET_STATUS_USE_NO = 0;
	/** 套票（套票）状态：部分入园 */
	public final static Integer GROUP_TICKET_STATUS_USE_SOME = 1;
	/** 套票（套票）状态：全部入园 */
	public final static Integer GROUP_TICKET_STATUS_USE_ALL = 2;
	
	public final static Map<Integer, String> GROUP_TICKET_STATUS_NAME = new HashMap<Integer, String>();

	static {
		GROUP_TICKET_STATUS_NAME.put(GROUP_TICKET_STATUS_USE_NO, "未入园");
		GROUP_TICKET_STATUS_NAME.put(GROUP_TICKET_STATUS_USE_SOME, "部分入园");
		GROUP_TICKET_STATUS_NAME.put(GROUP_TICKET_STATUS_USE_ALL, "全部入园");
	}
	
	// --------------------- 景区分类 ---------------------
	/** 景区分类：自然风景 */
	public final static Integer SCENICSPOT_CLASSIFY_NATURAL = 1;
	/** 景区分类：人工开发 */
	public final static Integer SCENICSPOT_CLASSIFY_MANUALLY = 2;
	/** 景区分类：其他 */
	public final static Integer SCENICSPOT_CLASSIFY_OTHER = 99;
	
	public final static Map<Integer, String> SCENICSPOT_TYPE_NAME = new HashMap<Integer, String>();
	static {
		SCENICSPOT_TYPE_NAME.put(SCENICSPOT_CLASSIFY_NATURAL, "自然风景");
		SCENICSPOT_TYPE_NAME.put(SCENICSPOT_CLASSIFY_MANUALLY, "人工开发");
		SCENICSPOT_TYPE_NAME.put(SCENICSPOT_CLASSIFY_OTHER, "其他");
	}

	// --------------------- 单位 ---------------------
	/**
	 * 单位：人民币（元）
	 */
	public final static Integer UNIT_RMB_YUAN = 1;
	/**
	 * 单位：百分比
	 */
	public final static Integer UNIT_PERCENT = 2;
	
	public final static Map<Integer, String> UNIT_NAME = new HashMap<Integer, String>();
	static {
		UNIT_NAME.put(UNIT_RMB_YUAN, "人民币（元）");
		UNIT_NAME.put(UNIT_PERCENT, "百分比");
	}
	
}
