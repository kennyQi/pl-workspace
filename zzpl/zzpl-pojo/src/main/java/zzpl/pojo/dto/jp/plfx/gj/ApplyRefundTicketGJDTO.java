package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class ApplyRefundTicketGJDTO extends BaseDTO{
	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 申请成功的乘客
	 */
	private List<String> succPassengerNames;

	/**
	 * 申请失败的乘客
	 */
	private List<String> failPassengerNames;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public List<String> getSuccPassengerNames() {
		return succPassengerNames;
	}

	public void setSuccPassengerNames(List<String> succPassengerNames) {
		this.succPassengerNames = succPassengerNames;
	}

	public List<String> getFailPassengerNames() {
		return failPassengerNames;
	}

	public void setFailPassengerNames(List<String> failPassengerNames) {
		this.failPassengerNames = failPassengerNames;
	}

}