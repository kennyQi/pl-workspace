package hsl.domain.model.xl.order;

import hsl.domain.model.M;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;

import javax.persistence.Column;
import javax.persistence.Embeddable;


import java.io.Serializable;

/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-01-27 14:30:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-01-27 14:30:59
 */
@Embeddable
public class LineOrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单状态
	 */
	@Column(name = "ORDER_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer orderStatus;

	/**
	 * 支付状态
	 */
	@Column(name = "PAY_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer payStatus;

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	 /**
	  * 
	  * @方法功能说明：创建线路订单时状态
	  * @修改者名字：renfeng
	  * @修改时间：2015年4月3日下午3:27:26
	  * @修改内容：
	  * @参数：@return
	  * @return:XLOrderStatus
	  * @throws
	  */
	   public void createOrderStatus() {
		  
		 setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE));
		 setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY));
	      
	   }
	   
	  	   
	  /**
	   * 
	   * @方法功能说明：改变订单时状态
	   * @修改者名字：renfeng
	   * @修改时间：2015年4月3日下午3:31:08
	   * @修改内容：
	   * @参数：@return
	   * @return:XLOrderStatus
	   * @throws
	   */
	   public void changeOrderStatus(Integer orderStatus,Integer payStatus){
		  
		   this.setOrderStatus(orderStatus);
		   this.setPayStatus(payStatus);
		  
	   }
	
}
