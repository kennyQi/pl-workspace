package hsl.pojo.command.coupon;

import java.util.Date;

/**
 * @类功能说明：创建卡券活动command
 * @类修改者：wuyg
 * @修改日期：2014年10月15日上午10:41:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日上午10:41:26
 */
public class CreateCouponActivityCommand {
	/**
	 * 活动编号
	 */
	private String id;
	// CouponActivityBaseInfo的字段
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动规则说明
	 */
	private String ruleIntro;
	/**
	 * 面值
	 */
	private double faceValue;
	/**
	 * 卡券类型
	 */
	private int couponType;

	// CouponIssueConditionInfo的字段
	/**
	 * 发放方式 1、注册发放 2、订单满送
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

	// CouponUseConditionInfo的字段
	/**
	 * 使用条件 1、订单满多少可用
	 */
	private int condition;
	/**
	 * 要求最小订单金额
	 */
	private double minOrderPrice;
	/**
	 * 使用起始日期
	 */
	private Date beginDate;
	/**
	 * 使用结束日期
	 */
	private Date endDate;
	/**
	 * 使用优先级
	 */
	private int priority;
	/**
	 * 使用种类（可以同时为机票和门票的时候为1,2） 1代表机票 2代表门票
	 */
	private String usedKind;
	/**
    * 订单满的数值
    */
   private int issueNumLine;

	public int getIssueNumLine() {
		return issueNumLine;
	}

	public void setIssueNumLine(int issueNumLine) {
		this.issueNumLine = issueNumLine;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuleIntro() {
		return ruleIntro;
	}

	public void setRuleIntro(String ruleIntro) {
		this.ruleIntro = ruleIntro;
	}

	public double getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

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

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public double getMinOrderPrice() {
		return minOrderPrice;
	}

	public void setMinOrderPrice(double minOrderPrice) {
		this.minOrderPrice = minOrderPrice;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUsedKind() {
		return usedKind;
	}

	public void setUsedKind(String usedKind) {
		this.usedKind = usedKind;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

}
