package hsl.pojo.dto.coupon;

import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class CouponIssueConditionInfoDTO {
	/**
	 * 发放方式 1、注册发放 2、订单满送3、福利发放 4、转赠
	 */
	private int issueWay;
	/**
	 * 开始发放日期
	 */
	private Date issueBeginDate;
	/**
	 * 发放结束日期
	 */
	private Date issueEndDate;
	/**
	 * 发放数量
	 */
	private long issueNumber;
	/**
	 * 每人发放数量
	 */
	private long perIssueNumber;
	/**
	 * 订单满的数值
	 */
	private double issueNumLine;
	/**
	 * 使用优先级
	 */
	private int priority;

	public int getIssueWay() {
		return issueWay;
	}

	public void setIssueWay(int issueWay) {
		this.issueWay = issueWay;
	}

	public Date getIssueBeginDate() {
		return issueBeginDate;
	}

	public void setIssueBeginDate(Date issueBeginDate) {
		this.issueBeginDate = issueBeginDate;
	}

	public Date getIssueEndDate() {
		return issueEndDate;
	}

	public void setIssueEndDate(Date issueEndDate) {
		this.issueEndDate = issueEndDate;
	}

	public long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public long getPerIssueNumber() {
		return perIssueNumber;
	}

	public void setPerIssueNumber(long perIssueNumber) {
		this.perIssueNumber = perIssueNumber;
	}

	public double getIssueNumLine() {
		return issueNumLine;
	}

	public void setIssueNumLine(double issueNumLine) {
		this.issueNumLine = issueNumLine;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
