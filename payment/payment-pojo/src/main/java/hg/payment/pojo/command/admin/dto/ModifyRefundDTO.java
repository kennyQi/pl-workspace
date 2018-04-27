package hg.payment.pojo.command.admin.dto;

import javax.persistence.Column;

import hg.payment.pojo.dto.BaseDTO;

/**
 * 
 * 
 *@类功能说明：修改退款信息基本数据
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月27日下午2:54:19
 *
 */
public class ModifyRefundDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**
	 * 退款帐号
	 */
	private String refundAccount;
	
	/**
	 * 退款备注
	 */
	private String refundRemark;
	
	/**
	 * 退款状态
	 */
	private Integer refundSuccess;
	
	/**
	 * 第三方交易号
	 */
	private String thirdPartyTradeNo;
	
	/**
	 * 退款收取的服务费
	 */
	private Double serviceCharge;

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public Integer getRefundSuccess() {
		return refundSuccess;
	}

	public void setRefundSuccess(Integer refundSuccess) {
		this.refundSuccess = refundSuccess;
	}

	public String getThirdPartyTradeNo() {
		return thirdPartyTradeNo;
	}

	public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
		this.thirdPartyTradeNo = thirdPartyTradeNo;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	
	

}
