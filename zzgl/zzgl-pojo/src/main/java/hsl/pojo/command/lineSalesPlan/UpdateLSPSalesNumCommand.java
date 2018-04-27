package hsl.pojo.command.lineSalesPlan;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：修改销售方案的已售数量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/5 14:12
 */
public class UpdateLSPSalesNumCommand  extends BaseCommand{
	private String planId;
	private Integer salesNum;
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Integer getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}
}
