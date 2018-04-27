package plfx.xl.domain.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.xl.domain.model.M;
import plfx.xl.pojo.system.XLOrderStatusConstant;

/**
 * 
 * @类功能说明：线路订单状态model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日上午10:01:07
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Embeddable
public class XLOrderStatus implements  Serializable{

	public XLOrderStatus() {
		super();
	}
	
	public XLOrderStatus(Integer status) {
		setStatus(status);
	}
	
	public XLOrderStatus(Integer status,Integer payStatus) {
		super();
		setStatus(status);
		setPayStatus(payStatus);
	}

   /** 
    * 订单状态
    */
   @Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
   private Integer status;
   
   /** 
    * 支付状态
    */
   @Column(name = "PAY_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
   private Integer payStatus;
   
   /**
    * 
    * @方法功能说明：创建线路订单时状态
    * @修改者名字：tandeng
    * @修改时间：2014年12月16日上午10:04:11
    * @修改内容：
    * @参数：@return
    * @return:XLOrderStatus
    * @throws
    */
   public XLOrderStatus createOrderStatus() {
	  XLOrderStatus os = new XLOrderStatus();
	  os.setStatus(0);
	  os.setPayStatus(0);
      return os;
   }
   
   /**
    * 
    * @方法功能说明：取消线路订单时状态
    * @修改者名字：tandeng
    * @修改时间：2014年12月16日上午10:04:11
    * @修改内容：
    * @参数：@return
    * @return:XLOrderStatus
    * @throws
    */
   public XLOrderStatus cancelOrderStatus() {
	   XLOrderStatus os = new XLOrderStatus();
	   os.setStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_ORDER_CANCEL));
	   //订单支付状态为待支付订金则不变，其他状态则改为等待退款
	   if(!XLOrderStatusConstant.SLFX_WAIT_PAY_BARGAIN_MONEY.equals(getPayStatus() + "")){
		   os.setPayStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_WAIT_REFUND));
	   }else{
		   os.setPayStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_WAIT_PAY_BARGAIN_MONEY));
	   }
	   return os;
   }
   
   /**
    * 
    * @方法功能说明：更改订单状态时状态
    * @修改者名字：luoyun
    * @修改时间：2014年12月23日下午4:01:31
    * @修改内容：
    * @参数：@return
    * @return:XLOrderStatus
    * @throws
    */
   public XLOrderStatus changeOrderStatus(){
	   XLOrderStatus os = new XLOrderStatus();
	   //更改至下单成功已锁定，等待支付尾款
	   os.setStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_LOCK_SEAT));
	   os.setPayStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_WAIT_PAY_BALANCE_MONEY));
	   return os;
   }
   
   /**
    * 
    * @方法功能说明：更改订单状态为->预定成功  全额付款
    * @修改者名字：yuqz
    * @修改时间：2015年5月15日上午10:02:23
    * @修改内容：
    * @参数：@return
    * @return:XLOrderStatus
    * @throws
    */
   public XLOrderStatus changeOrderStatusPayAll(){
	   XLOrderStatus os = new XLOrderStatus();
	   //更改至预定成功，支付全款
	   os.setStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_RESERVE_SUCCESS));
	   os.setPayStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_PAY_SUCCESS));
	   return os;
   }

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
  
}