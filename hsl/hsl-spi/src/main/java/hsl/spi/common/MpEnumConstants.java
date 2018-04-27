package hsl.spi.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 门票实体枚举常量
 * 
 * @author zhurz
 */
public class MpEnumConstants {

	/**
	 * 缓存配置KEY常量
	 * 
	 * @author zhurz
	 */
	public final static class KVConfigKey {
		/**
		 * 供应商同程的ID
		 */
		public final static String KEY_SUPPLIER_TC = "supplier.id.tc";
	}

	/**
	 * 缓存KEY
	 * 
	 * @author zhurz
	 */
	public final static class CacheKeyEnum {
		/** 价格日历 */
		public final static String CACHE_KEY_DATE_SALE_PRICE = "date_sale_price_cache";
		/** 景点政策 */
		public final static String CACHE_KEY_TC_SCENIC_SUPPLIER_POLICY = "tc_scenic_supplier_policy_cache";
		/** 供应商政策 */
		public final static String CACHE_KEY_TC_SUPPLIER_POLICY = "tc_supplier_policy_cache";
	}
	
	/**
	 * 异常代码定义
	 * 
	 * @author zhurz
	 */
	public final static class ExceptionCode {
		/** 下单前平台政策变动 */
		public final static String EXCEPTION_CODE_SALE_POLICY_CHANGE = "SLFX-MP-1001";
		/** 价格日历不存在 */
		public final static String EXCEPTION_CODE_SALE_POLICY_CALENDAR_NONE = "SLFX-MP-1002";
		/** 游玩日期的价格政策不存在 */
		public final static String EXCEPTION_CODE_SALE_POLICY_NONE = "SLFX-MP-1003";
		/** 与最新价格不符 */
		public final static String EXCEPTION_CODE_DATE_SALE_PRICE_NEQ = "SLFX-MP-1004";
		/** 取消景点门票订单失败 */
		public final static String EXCEPTION_CODE_CANCEL_SCENERY_ORDER_FAIL = "SLFX-MP-1005";
		/** 接口调用错误 */
		public final static String EXCEPTION_CODE_INTER_FAIL = "SLFX-MP-1006";
		/** 参数错误 */
		public final static String EXCEPTION_CODE_ARGS_ERROR = "SLFX-MP-9001";
		public final static Map<String, String> MESSAGE_MAP = new HashMap<String, String>();
		static {
			MESSAGE_MAP.put(EXCEPTION_CODE_SALE_POLICY_CHANGE, "下单前平台政策变动");
			MESSAGE_MAP.put(EXCEPTION_CODE_SALE_POLICY_CALENDAR_NONE, "价格日历不存在");
			MESSAGE_MAP.put(EXCEPTION_CODE_SALE_POLICY_NONE, "游玩日期的价格政策不存在");
			MESSAGE_MAP.put(EXCEPTION_CODE_DATE_SALE_PRICE_NEQ, "与最新价格不符");
			MESSAGE_MAP.put(EXCEPTION_CODE_CANCEL_SCENERY_ORDER_FAIL, "取消景点门票订单失败");
			MESSAGE_MAP.put(EXCEPTION_CODE_INTER_FAIL, "接口调用错误");
			MESSAGE_MAP.put(EXCEPTION_CODE_ARGS_ERROR, "参数错误");
		}
	}
	
	public final static class SalePolicySnapshotEnum {
		// 按省市区
		public final static Integer FILTER_TYPE_AREA = 1;
		// 按价格
		public final static Integer FILTER_TYPE_PRICE = 2;
		// 按景点
		public final static Integer FILTER_TYPE_SCENIC_SPOT = 3;
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
	
	/** 订单取消原因	 */
	public final static class OrderCancelReason{
		
		public final static Map<String, String>cancelReasonMap=new HashMap<String, String>();
		
		 static {
			cancelReasonMap.put("1", "行程变更");
			cancelReasonMap.put("2", "通过其他更优惠的渠道预订了景区");
			cancelReasonMap.put("3", "对服务不满意");
			cancelReasonMap.put("4", "其他原因");
			cancelReasonMap.put("5", "信息错误重新预订");
			cancelReasonMap.put("12", "景区不承认合作");
			cancelReasonMap.put("17", "天气原因");
			cancelReasonMap.put("18", "重复订单");
		}
	}
	
	/**  订单状态  **/
	public final static class OrderStatusEnum{

		// 新建（待游玩）
		public final static Integer ORDER_STATUS_PREPARED = 1;
		// 取消订单
		public final static Integer ORDER_STATUS_CANCEL = 2;
		// 游玩过订单
		public final static Integer ORDER_STATUS_USED = 3;
		// 预订未游玩订单(即过期)
		public final static Integer ORDER_STATUS_OUTOFDATE = 4;
		
		public final static Map<String, String>orderStatusMap=new HashMap<String, String>();
		
		static{
			orderStatusMap.put("1", "新建（待游玩）");
			orderStatusMap.put("2", "取消订单");
			orderStatusMap.put("3", "游玩过订单");
			orderStatusMap.put("4", "预订未游玩订单");
		}
	}
	
}
