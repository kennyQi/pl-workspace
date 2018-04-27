package hsl.pojo.dto.lineSalesPlan;
import hsl.pojo.dto.BaseDTO;
/**
 * @类功能说明：线路销售方案状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 12:52
 */
public class LineSalesPlanStatusDTO extends BaseDTO{
	/**
	 * 销售方案的状态
	 * 1、未审核
	 * 2、已审核
	 * 3、未开始
	 * 4、进行中（活动进行中不能修改销售方案的信息）
	 * 5、活动结束
	 * 6、活动非正常结束（活动结束拼团失败）
	 */
	private  Integer status;
	/**
	 * 显示状态
	 * 1、隐藏
	 * 2、显示
	 */
	private Integer showStatus;

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
