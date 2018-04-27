package plfx.jp.spi.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：机票实体枚举常量
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日上午10:36:29
 * @版本：V1.0
 *
 */
public class JpEnumConstants {

	/**
	 * 缓存KEY
	 * @author yuqz
	 */
	public final static class CacheKeyEnum {
		/** 航班信息 */
		public final static String CACHE_KEY_FLIGHT_INFO = "cache_key_flight_info";
	}
	
	/**
	 * 异常代码定义
	 * @author yuqz
	 */
	public final static class ExceptionCode {
		/** 下单前平台政策变动 */
		public final static String EXCEPTION_CODE_SALE_POLICY_CHANGE = "SLFX-JP-1001";
		public final static Map<String, String> MESSAGE_MAP = new HashMap<String, String>();
		static {
			MESSAGE_MAP.put(EXCEPTION_CODE_SALE_POLICY_CHANGE, "下单前平台政策变动");
		}
	}
	
	public final static class SalePolicySnapshotEnum {
		// 按供应商
		public final static Integer FILTER_TYPE_SUPPLIER = 1;
		// 按经销商
		public final static Integer FILTER_TYPE_AGENCY = 2;
		// 调价政策 - 增加
		public final static Integer MODIFY_PRICE_TYPE_ADD = 1;
		// 调价政策 - 减少
		public final static Integer MODIFY_PRICE_TYPE_SUB = 2;
		// 调价政策 - 按百分比增加
		public final static Integer MODIFY_PRICE_TYPE_ADD_PERCENT = 3;
		// 调价政策 - 按百分比减少
		public final static Integer MODIFY_PRICE_TYPE_SUB_PERCENT = 4;
		// 调价政策 - 隐藏资源
		public final static Integer MODIFY_PRICE_TYPE_HIDE = 5;
	}

	public final static class SalePolicySnapshotStatusEnum {
		// 未发布
		public final static Integer STATUS_UNDEPLOY = 1;
		// 已发布
		public final static Integer STATUS_DEPLOY = 2;
		// 未开始
		public final static Integer STATUS_UNSTART = 3;
		// 已开始
		public final static Integer STATUS_START = 4;
		// 已下架
		public final static Integer STATUS_PAST = 5;
		// 已取消
		public final static Integer STATUS_CANCEL = 6;
	}
	
	public final static class OrderCancelReason{
		
		public final static Map<Integer, String> cancelReasonMap=new HashMap<Integer, String>();
		
		 static {
			cancelReasonMap.put(1, "行程变更");
			cancelReasonMap.put(2, "通过其他更优惠的渠道预订了机票");
			cancelReasonMap.put(3, "对服务不满意");
			cancelReasonMap.put(4, "其他原因");
			cancelReasonMap.put(5, "信息错误重新预订");
			cancelReasonMap.put(17, "天气原因");
			cancelReasonMap.put(18, "重复订单");
		}
		 
		public final static Map<String, String> cancelReasonMap_2=new HashMap<String, String>();
			
		 static {
			 cancelReasonMap_2.put("1", "行程变更");
			 cancelReasonMap_2.put("2", "通过其他更优惠的渠道预订了机票");
			 cancelReasonMap_2.put("3", "对服务不满意");
			 cancelReasonMap_2.put("4", "其他原因");
			 cancelReasonMap_2.put("5", "信息错误重新预订");
			 cancelReasonMap_2.put("17", "天气原因");
			 cancelReasonMap_2.put("18", "重复订单");
		}
	}
	
	public final static class OrderErrorType{
		// 普通订单
		public final static String TYPE_FORMAL = "0";
		// 异常订单
		public final static String TYPE_ERROR = "1";
	}
	
}
