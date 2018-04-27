package hg.dzpw.pojo.dto.order;

import hg.dzpw.pojo.common.BaseDTO;
import java.util.Date;

/**
 * @类功能说明: 订单支付信息DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午5:04:04 
 * @版本：V1.0
 */
public class TicketOrderPayInfo extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 支付时间
	 */
	private Date payDate;
	
	/**
	 * 金额
	 */
	private Double price;
	
	/**
	 * 支付状态(0,未支付；1,已支付；2,已收款)
	 */
	private int paid;

	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getPaid() {
		return paid;
	}
	public void setPaid(int paid) {
		this.paid = paid;
	}
}