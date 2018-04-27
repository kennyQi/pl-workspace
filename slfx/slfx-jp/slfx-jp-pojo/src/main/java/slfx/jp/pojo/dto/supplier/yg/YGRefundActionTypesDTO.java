package slfx.jp.pojo.dto.supplier.yg;

import java.util.List;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：YG退废票申请种类DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:01:14
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGRefundActionTypesDTO extends BaseJpDTO{
	
	/**
	 * 所属平台代码
	 */
	private String platCode;
	
	/**
	 * 接口错误代码  0表示成功
	 */
	private String errorCode;
	
	/**
	 * 错误描述
	 */
	private String errorMsg;
	
	/**
	 * 退废票申请种类集合
	 */
	private List<YGRefundActionTypeDTO> actionTypeList;

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<YGRefundActionTypeDTO> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<YGRefundActionTypeDTO> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}
}
