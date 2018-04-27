package zzpl.pojo.dto.jp;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CancelOrderTicketReasonDTO extends BaseDTO {
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 退废票申请种类集合
	 */
	private List<RefundActionTypeDTO> actionTypeList;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RefundActionTypeDTO> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<RefundActionTypeDTO> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}
}
