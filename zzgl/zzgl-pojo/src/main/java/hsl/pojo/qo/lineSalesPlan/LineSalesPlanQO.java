package hsl.pojo.qo.lineSalesPlan;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路销售方案
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:08
 */
public class LineSalesPlanQO extends BaseQo{

	private String planName;
	private String lineName;
	private Integer planType;
	private String beginDate;
	private String endDate;
	private Integer showStatus;
	private Integer[] statusArray;
	private Integer status;
	private boolean orderByPriority;
	private boolean fetchLine;
	/**
	 * 查询是否加锁--数据库悲观锁
	 */
	private boolean isLock;
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isOrderByPriority() {
		return orderByPriority;
	}

	public void setOrderByPriority(boolean orderByPriority) {
		this.orderByPriority = orderByPriority;
	}

	public boolean isFetchLine() {
		return fetchLine;
	}

	public void setFetchLine(boolean fetchLine) {
		this.fetchLine = fetchLine;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
	}
}
