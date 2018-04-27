package hg.dzpw.dealer.client.common;

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
}