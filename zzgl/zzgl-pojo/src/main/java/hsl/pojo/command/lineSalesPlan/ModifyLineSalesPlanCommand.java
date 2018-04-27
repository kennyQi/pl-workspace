package hsl.pojo.command.lineSalesPlan;

import hg.common.component.BaseCommand;

import java.util.Date;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 15:43
 */
public class ModifyLineSalesPlanCommand extends BaseCommand {
	/**
	 * 方案ID
	 */
	private String planId;
	/**
	 * 方案名称
	 */
	private String planName;
	/**
	 * 方案类型
	 */
	private Integer planType;
	/**
	 * 方案关联的线路ID
	 */
	private String lineId;
	/**
	 * 活动图片URI
	 */
	private String  imageUri;
	/**
	 * 方案规则
	 */
	private String planRule;
	/**
	 * 线路原始价格
	 */
	private Double originalPrice;
	/**
	 * 方案价格
	 */
	private Double planPrice;
	/**
	 * 方案开始时间
	 */
	private Date beginDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 出游时间
	 */
	private Date travelDate;
	/**
	 * 出游时间
	 */
	private Integer provideNum;
	/**
	 * 优先级
	 */
	private Integer priority;
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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getPlanRule() {
		return planRule;
	}

	public void setPlanRule(String planRule) {
		this.planRule = planRule;
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

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
}
