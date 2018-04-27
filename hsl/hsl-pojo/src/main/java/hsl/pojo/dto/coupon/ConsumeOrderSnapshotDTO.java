package hsl.pojo.dto.coupon;
import hsl.pojo.dto.BaseDTO;
/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午1:51:58
 *
 */
@SuppressWarnings("serial")
public class ConsumeOrderSnapshotDTO extends BaseDTO{
	
	/**
	 * 订单ID
	 */
    private String orderId;
    /**
	 * 支付价格
	 */
    private double payPrice;
    /**
   	 * 详细
   	 */
    private String detail;
    /**
     * 订单类型
     * */
    private String orderType;
     
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	
}
