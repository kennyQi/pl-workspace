package hsl.domain.model.coupon;

import hsl.domain.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 卡券发放条件信息
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:45:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:45:45
 * 
 */
@Embeddable
public class CouponIssueConditionInfo {
	/**
	 * 发放方式 1、注册发放 2、订单满送 3、福利发放 4、转赠
	 */
	@Column(name = "ISSUEWAY", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer issueWay;
	/**
	 * 开始发放日期
	 */
	@Column(name = "ISSUEBEGINDATE", columnDefinition = M.DATE_COLUM)
	private Date issueBeginDate;
	/**
	 * 发放结束日期
	 */
	@Column(name = "ISSUEENDDATE", columnDefinition = M.DATE_COLUM)
	private Date issueEndDate;
	/**
	 * 发放数量
	 */
	@Column(name = "ISSUENUMBER", columnDefinition = M.DOUBLE_COLUM)
	private Long issueNumber;
	/**
	 * 每人发放数量
	 */
	@Column(name = "PERISSUENUMBER", columnDefinition = M.NUM_COLUM)
	private Long perIssueNumber;
	/**
	 * 订单满的数值
	 */
	@Column(name = "ISSUENUMLINE", columnDefinition = M.DOUBLE_COLUM)
	private Double issueNumLine;
	/**
	 * 使用优先级
	 */
	@Column(name = "PRIORITY", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer priority;

	public java.lang.Integer getIssueWay() {
		return issueWay;
	}

	public void setIssueWay(java.lang.Integer issueWay) {
		this.issueWay = issueWay;
	}

	public java.util.Date getIssueBeginDate() {
		return issueBeginDate;
	}

	public void setIssueBeginDate(java.util.Date issueBeginDate) {
		this.issueBeginDate = issueBeginDate;
	}

	public java.util.Date getIssueEndDate() {
		return issueEndDate;
	}

	public void setIssueEndDate(java.util.Date issueEndDate) {
		this.issueEndDate = issueEndDate;
	}

	public java.lang.Long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(java.lang.Long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public java.lang.Long getPerIssueNumber() {
		return perIssueNumber;
	}

	public void setPerIssueNumber(java.lang.Long perIssueNumber) {
		this.perIssueNumber = perIssueNumber;
	}

	public Double getIssueNumLine() {
		return issueNumLine;
	}

	public void setIssueNumLine(Double issueNumLine) {
		this.issueNumLine = issueNumLine;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}