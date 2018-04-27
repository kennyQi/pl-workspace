package hsl.domain.model.coupon;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 消费订单快照
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:56:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:56:38
 *
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_COUPON+"COUPON_CSM_SN")
public class ConsumeOrderSnapshot extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单类型机票(1)
	 */
	public final static Integer ORDERTYPE_JP=1;
	/**
	 * 订单类型门票(2)
	 */
	public final static Integer ORDERTYPE_MP=2;
	/**
	 * 订单类型线路(3)
	 */
	public final static Integer ORDERTYPE_XL=3;
	/**
	 * 订单类型酒店(4)
	 */
	public final static Integer ORDERTYPE_JD=4;
	/**
	 * 订单类型 
	 */
	@Column(name="ORDERTYPE",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer orderType;
	/**
	 * 订单ID
	 */
	@Column(name="ORDERID", length = 64)
    private String orderId;
    /**
	 * 支付价格
	 */
	@Column(name="PAYPRICE",columnDefinition=M.DOUBLE_COLUM)
    private Double payPrice;
    /**
   	 * 详细
   	 */
	@Column(name="DETAIL",columnDefinition=M.TEXT_COLUM)
    private String detail;
	public String create(ConsumeCouponCommand command) {
		this.setId(UUIDGenerator.getUUID());
		this.setOrderId(command.getOrderId());
		this.setOrderType(command.getOrderType());
		this.setPayPrice(command.getPayPrice());
		this.setDetail(command.getDetail());
		return getId();
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

}