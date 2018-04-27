package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class RefundActionTypeDTO extends BaseDTO {
	/**
	 * 退废票申请种类编号
	 */
	private String refundType;

	/**
	 * 退废票申请种类编号
	 */
	private String actionTypeCode;

	/**
	 * 退废票申请种类描述
	 */
	private String actionTypeTxt;

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getActionTypeCode() {
		return actionTypeCode;
	}

	public void setActionTypeCode(String actionTypeCode) {
		this.actionTypeCode = actionTypeCode;
	}

	public String getActionTypeTxt() {
		return actionTypeTxt;
	}

	public void setActionTypeTxt(String actionTypeTxt) {
		this.actionTypeTxt = actionTypeTxt;
	}
}
