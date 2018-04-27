package hsl.domain.model.lineSalesPlan;
import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
@Embeddable
public class LineSalesPlanSalesInfo{
	/**
	 * 线路原始价格
	 */
	@Column(name="ORIGINAL_PRICE",columnDefinition = M.DOUBLE_COLUM)
	private Double originalPrice;
	/**
	 * 方案的价格
	 */
	@Column(name="PLAN_PRICE",columnDefinition = M.DOUBLE_COLUM)
	private Double planPrice;
	/**
	 * 活动开始时间
	 */
	@Column(name="BEGINDATE",columnDefinition = M.DATE_COLUM)
	private Date beginDate;
	/**
	 * 活动结束时间
	 */
	@Column(name="ENDDATE",columnDefinition = M.DATE_COLUM)
	private Date endDate;
	/**
	 * 出游时间
	 */
	@Column(name="TRAVELDATE",columnDefinition = M.DATE_COLUM)
	private Date travelDate;
	/**
	 * 活动发放数量
	 */
	@Column(name="PROVIDE_NUM",columnDefinition = M.NUM_COLUM)
	private Integer provideNum;
	/**
	 * 已售数量
	 */
	@Column(name="SOLD_NUM",columnDefinition = M.NUM_COLUM)
	private Integer soldNum;
	/**
	 * 活动优先级（根据这个来判断显示排序顺序）
	 */
	@Column(name = "priority",columnDefinition = M.NUM_COLUM)
	private Integer priority;

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

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

	public Integer getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Integer soldNum) {
		this.soldNum = soldNum;
	}
}
