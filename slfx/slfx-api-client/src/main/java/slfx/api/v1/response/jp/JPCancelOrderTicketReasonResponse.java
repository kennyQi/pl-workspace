package slfx.api.v1.response.jp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;

/**
 * 
 * @类功能说明：取消订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:43:28
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPCancelOrderTicketReasonResponse extends ApiResponse{
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 退废票申请种类集合
	 */
	private List<YGRefundActionTypeDTO> actionTypeList;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<YGRefundActionTypeDTO> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<YGRefundActionTypeDTO> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}
	
}
