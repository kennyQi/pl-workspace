package slfx.xl.pojo.command.order;


import hg.common.component.BaseCommand;
@SuppressWarnings("serial")
public class ModifyPaymentLineOrderCommand extends BaseCommand{
	/**
	 * 订单编号
	 */
	private String lineOrderId;
		
	/**
	 * 游客ID，用","隔开
	 */
	private String travelerId;
	
	/**
	 * 单人全款金额
	 */
	private Double singleSalePrice;
	
	/**
	 * 备注
	 */
	private String remark;

	public Double getSingleSalePrice() {
		return singleSalePrice;
	}

	public void setSingleSalePrice(Double singleSalePrice) {
		this.singleSalePrice = singleSalePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLineOrderId() {
		return lineOrderId;
	}

	public void setLineOrderId(String lineOrderId) {
		this.lineOrderId = lineOrderId;
	}

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}
    
}
