package hg.dzpw.dealer.client.common.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DealerApiAction {

	/** 查询行政区 */
	public final static String QueryRegion = "QueryRegion";
	/** 查询景区 */
	public final static String QueryScenicSpot = "QueryScenicSpot";
	/** 查询门票政策 */
	public final static String QueryTicketPolicy = "QueryTicketPolicy";
	
	/** 创建订单 */
	public final static String CreateTicketOrder = "CreateTicketOrder";
	/** 支付订单 */
	public final static String PayToTicketOrder = "PayToTicketOrder";
	/** 关闭订单 */
	public final static String CloseTicketOrder = "CloseTicketOrder";
	
	/** 查询门票订单 */
	public final static String QueryTicketOrder = "QueryTicketOrder";
	/** 查询门票 */
	public final static String QueryGroupTicket = "QueryGroupTicket";
	/**入园记录查询*/
	public final static String QueryUseRecord = "QueryUseRecord";
	/**申请退票*/
	public final static String ApplyRefund = "ApplyRefund";

	/**
	 * @方法功能说明：对经销商接口名称
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午3:22:12
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String value();
	
}
