package plfx.xl.pojo.dto.order;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：线路订单状态DTO
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
public class XLOrderStatusDTO extends BaseXlDTO{

   /** 
    * 订单状态
    */
   private Integer status;
   
   /** 
    * 支付状态
    */
   private Integer payStatus;
   
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