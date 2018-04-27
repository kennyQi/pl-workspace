package hsl.pojo.qo.coupon;

import hg.common.component.BaseQo;

/**
 * @类功能说明：卡券活动QO
 * @类修改者：wuyg
 * @修改日期：2014年10月15日下午2:08:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午2:08:40
 * 
 */
public class HslCouponActivityQO extends BaseQo {
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1850523045399968389L;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 卡券类型 0、全部 1、代金券
	 */
	private int couponType;
	/**
	 * 发放方式 0、全部 1、注册发放 2、订单满送
	 */
	private int issueWay;
	/**
	 * 当前活动状态 0全部 1未审核 2审核未通过 3审核成功 4发放中 5名额已满 6活动结束
	 */
	private int currentValue;
	/**
	 * 订单满的数值
	 */
	private double issueNumLine;
	/**
	 * 是否查询大于订单满，false查询相等
	 */
	private boolean isGreater;
	/**
	 * 是否按照优先级排序
	 */
	private boolean isOrderbyPriority;
	/**
	 * 活动状态数组可以查询多个状态
	 */
	private Object[] statusTypes={};
	
	public Object[] getStatusTypes() {
		return statusTypes;
	}

	public void setStatusTypes(Object[] statusTypes) {
		this.statusTypes = statusTypes;
	}

	public boolean isOrderbyPriority() {
		return isOrderbyPriority;
	}

	public void setOrderbyPriority(boolean isOrderbyPriority) {
		this.isOrderbyPriority = isOrderbyPriority;
	}

	public double getIssueNumLine() {
		return issueNumLine;
	}

	public void setIssueNumLine(double issueNumLine) {
		this.issueNumLine = issueNumLine;
	}

	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

	public int getIssueWay() {
		return issueWay;
	}

	public void setIssueWay(int issueWay) {
		this.issueWay = issueWay;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

}
