package hsl.pojo.command.lineSalesPlan;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：修改线路销售方案状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 14:59
 */
public class UpdateLineSalesPlanStatusCommand extends BaseCommand{
	/**
	 * 方案ID
	 */
	private String planId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 显示状态
	 */
	private Integer showStatus;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
}
