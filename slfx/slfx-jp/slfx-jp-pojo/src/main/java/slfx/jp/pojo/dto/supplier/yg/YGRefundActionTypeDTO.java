package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：易购退废票申请种类DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:00:59
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGRefundActionTypeDTO extends BaseJpDTO{
	
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
