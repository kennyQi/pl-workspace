package hsl.pojo.util;

/**
 * @类功能说明：汇商旅常量
 * @类修改者：
 * @修改日期：2015-9-28下午5:12:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-28下午5:12:37
 */
public interface HSLConstants {
	
	/**
	 * @类功能说明：游客常量
	 * @类修改者：
	 * @修改日期：2015-9-28下午5:14:11
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-9-28下午5:14:11
	 */
	interface Traveler {
		/**
		 * 游客类型：成人
		 */
		Integer TYPE_ADULT = 1;
		/**
		 * 游客类型：儿童
		 */
		Integer TYPE_CHILD = 2;

		/**
		 * 证件类型：身份证
		 */
		Integer ID_TYPE_SFZ = 1;
	}
	
	/**
	 * @类功能说明：线路订单支付类型
	 * @类修改者：
	 * @修改日期：2015-10-12下午4:40:18
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-10-12下午4:40:18
	 */
	interface LineOrderPayType {
		/**
		 * 支付类型：微信支付
		 */
		Integer PAY_TYPE_WX = 1;
		
		/**
		 * 支付类型：支付宝
		 */
		Integer PAY_TYPE_ALIPAY = 2;

		/**
		 * 支付类型：免费
		 */
		Integer PAY_TYPE_FREE = 99;
	}

	/**
	 * 配置KEY
	 *
	 * @author zhurz
	 */
	interface HSLConfigKey {
		/**
		 * 微信自动回复
		 */
		String KEY_WX_REPLY = "WX_REPLY";

		/**
		 * 微信菜单
		 */
		String KEY_WX_MENU = "WX_MENU";
	}
	
}
