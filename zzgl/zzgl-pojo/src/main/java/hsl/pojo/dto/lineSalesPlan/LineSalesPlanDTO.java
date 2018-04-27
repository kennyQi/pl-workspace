package hsl.pojo.dto.lineSalesPlan;
import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.line.LineDTO;
/**
 * @类功能说明：线路销售方案
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 11:06
 */
public class LineSalesPlanDTO extends BaseDTO{
	/**
	 * 方案基本信息
	 */
	private  LineSalesPlanBaseInfoDTO baseInfo;
	/**
	 * 关联的线路（直接关联线路实体，分销修改后可以直接关联到活动中）
	 */
	private LineDTO line;
	/**
	 * 方案的销售信息
	 */
	private LineSalesPlanSalesInfoDTO lineSalesPlanSalesInfo;
	/**
	 * 方案的状态
	 */
	private LineSalesPlanStatusDTO lineSalesPlanStatus;

	public LineSalesPlanBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(LineSalesPlanBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
	}

	public LineSalesPlanSalesInfoDTO getLineSalesPlanSalesInfo() {
		return lineSalesPlanSalesInfo;
	}

	public void setLineSalesPlanSalesInfo(LineSalesPlanSalesInfoDTO lineSalesPlanSalesInfo) {
		this.lineSalesPlanSalesInfo = lineSalesPlanSalesInfo;
	}

	public LineSalesPlanStatusDTO getLineSalesPlanStatus() {
		return lineSalesPlanStatus;
	}

	public void setLineSalesPlanStatus(LineSalesPlanStatusDTO lineSalesPlanStatus) {
		this.lineSalesPlanStatus = lineSalesPlanStatus;
	}
}
