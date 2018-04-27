package hsl.domain.model.lineSalesPlan.order;

import hsl.domain.model.M;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
/**
* @类功能说明：订单的支付状态
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:56:33
* @版本： V1.0
*/
@Embeddable
public class LSPOrderStatus implements Serializable {
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
	   public void createOrderStatus(Integer orderType) {
		   /**
			* 如果活动订单是判断团购订单，则订单状态修改成组团中
			*/
		   if(orderType.equals(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY)){
			   setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ING);
			   setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_NOPAY);
		   }else{
			   setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE);
			   setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_NOPAY);
		   }
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
