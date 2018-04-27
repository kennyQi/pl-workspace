package hsl.spi.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：卡券活动的状态常量
 * @类修改者：wuyg
 * @修改日期：2014年10月15日下午4:39:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午4:39:40
 *
 */
public class CouponActivityConstants {
	//卡券活动状态
	/**
	 * 1未审核
	 */
	public static final Integer COUPONACTIVITY_STATUS_UNCHECK=1;
	/**
	 * 2审核未通过
	 */
	public static final Integer COUPONACTIVITY_STATUS_CHECK_FAIL=2;
	/**
	 * 3审核成功
	 */
	public static final Integer COUPONACTIVITY_STATUS_CHECK_OK=3;
	/**
	 * 4发放中
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVE=4;
	/**
	 * 5名额已满
	 */
	public static final Integer COUPONACTIVITY_STATUS_QUOTA_FULL=5;
	/**
	 * 6活动结束
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVITY_OVER=6;
	/**
	 * 7取消
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVITY_CANCEL=7;
	
	@SuppressWarnings("serial")
	public static final Map<String, String> STATUSMAP=new HashMap<String, String>(){{
		put("1", "未审核");
		put("2", "审核未通过");
		put("3", "审核成功");
		put("4", "发放中");
		put("5", "名额已满");
		put("6", "活动结束");
		put("7", "取消");
	}};
	
	//卡券活动事件
	/**
	 * 1：创建
	 */
	public static final Integer EVENT_TYPE_CREATE=1;
	/**
	 * 2：修改基本信息
	 */
	public static final Integer EVENT_TYPE_MODIFY_BASEINFO=2;
	/**
	 * 3：修改发放信息
	 */
	public static final Integer EVENT_TYPE_MODIFY_ISSUEINFO=3;
	/**
	 * 4：修改消费信息
	 */
	public static final Integer EVENT_TYPE_MODIFY_CONSUMEINFO=4;
	/**
	 * 5：审核通过
	 */
	public static final Integer EVENT_TYPE_CHECK_OK=5;
	/**
	 * 6: 审核未通过
	 */
	public static final Integer EVENT_TYPE_CHECK_FAIL=6;
	/**
	 * 7：上线
	 */
	public static final Integer EVENT_TYPE_ONLINE=7;
	/**
	 * 8：结束
	 */
	public static final Integer EVENT_TYPE_OVER=8;
	/**
	 * 9：名额满
	 */
	public static final Integer EVENT_TYPE_QUTOA_FULL=9;
	/**
	 * 10：被取消
	 */
	public static final Integer EVENT_TYPE_CANCELED=10;
	
	@SuppressWarnings("serial")
	public static final Map<String, String> EVENTMAP=new HashMap<String, String>(){{
		put("1", "创建");
		put("2", "修改基本信息");
		put("3", "修改发放信息");
		put("4", "修改消费信息");
		put("5", "审核通过");
		put("6", "审核未通过");
		put("7", "上线");
		put("8", "结束");
		put("9", "名额满");
		put("10", "被取消");
	}};
}
