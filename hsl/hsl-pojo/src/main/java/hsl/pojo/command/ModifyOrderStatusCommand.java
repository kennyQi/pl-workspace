package hsl.pojo.command;

import hsl.pojo.dto.mp.MPOrderStatusDTO;


/**
 * 修改订单状态
 * @author liujz
 *
 */
public class ModifyOrderStatusCommand {
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 订单状态
	 */
	private MPOrderStatusDTO mpOrderStatusDTO;
	
	/**
	 * 取消原因
	 */
	private String cancelRemark;

	public MPOrderStatusDTO getMpOrderStatusDTO() {
		return mpOrderStatusDTO;
	}

	public void setMpOrderStatusDTO(MPOrderStatusDTO mpOrderStatusDTO) {
		this.mpOrderStatusDTO = mpOrderStatusDTO;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}
	
	
}
