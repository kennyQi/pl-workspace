package hsl.pojo.dto.lineSalesPlan;
import hsl.pojo.dto.BaseDTO;

import javax.persistence.Column;
import java.util.Date;

/**
 * @类功能说明：线路销售方案销售详情
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 11:39
 */
public class LineSalesPlanSalesInfoDTO extends BaseDTO{
	/**
	 * 线路原始价格
	 */
	private Double originalPrice;
	/**
	 * 方案的价格
	 */
	private Double planPrice;
	/**
	 * 活动开始时间
	 */
	private Date beginDate;
	/**
	 * 活动结束时间
	 */
	private Date endDate;
	/**
	 * 出游时间
	 */
	private Date travelDate;
	/**
	 * 活动发放数量
	 */
	private Integer provideNum;
	/**
	 * 已售数量
	 */
	private Integer soldNum;
	/**
	 * 活动优先级（根据这个来判断显示排序顺序）
	 */
	private Integer priority;

	public Double getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Integer getProvideNum() {
		return provideNum;
	}

	public void setProvideNum(Integer provideNum) {
		this.provideNum = provideNum;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Integer soldNum) {
		this.soldNum = soldNum;
	}
}
