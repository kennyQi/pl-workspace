package hsl.domain.model.coupon;

import hsl.domain.model.M;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * 卡券使用条件信息
 *
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:52:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:52:22
 */
@Embeddable
public class CouponUseConditionInfo {

	/**
	 * 使用种类：机票
	 */
	public static final String USED_KIND_JP = "1";
	/**
	 * 使用种类：门票
	 */
	public static final String USED_KIND_MP = "2";
	/**
	 * 使用种类：线路
	 */
	public static final String USED_KIND_XL = "3";

	/**
	 * 使用条件
	 * 1、订单满多少可用
	 */
	@Column(name = "CONDITION_", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer condition;
	/**
	 * 要求最小订单金额
	 */
	@Column(name = "MINORDERPRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double minOrderPrice;
	/**
	 * 使用起始日期
	 */
	@Column(name = "BEGINDATE", columnDefinition = M.DATE_COLUM)
	private Date beginDate;
	/**
	 * 使用结束日期
	 */
	@Column(name = "ENDDATE", columnDefinition = M.DATE_COLUM)
	private Date endDate;
	/**
	 * 使用种类（可以同时为机票和门票的时候为1,2）
	 * 1代表机票
	 * 2代表门票
	 * 3代表线路
	 */
	@Column(name = "USEDKIND", length = 64)
	private String usedKind;
	/**------1.4.2新添加的字段--start1---***/
	
	/**
	 * 同类型是否可以叠加
	 */
	@Column(name = "ISSHARESAMEKIND")
	@Type(type = "yes_no")
	private Boolean isShareSameKind;
	/**
	 * 不同类型是否可以叠加
	 */
	@Column(name = "ISSHARENOTSAMETYPE")
	@Type(type = "yes_no")
	private Boolean isShareNotSameType;
	
	/**
	 * ------1.4.2新添加的字段--end---
	 ***/
	public Integer getCondition() {
		if (condition == null)
			condition = 0;
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public java.lang.Double getMinOrderPrice() {
		if (minOrderPrice == null)
			minOrderPrice = 0d;
		return minOrderPrice;
	}

	public void setMinOrderPrice(Double minOrderPrice) {
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

	public String getUsedKind() {
		if (usedKind == null)
			usedKind = "";
		return usedKind;
	}

	public void setUsedKind(String usedKind) {
		this.usedKind = usedKind;
	}

	public Boolean getIsShareSameKind() {
		if (isShareSameKind == null)
			isShareSameKind = false;
		return isShareSameKind;
	}

	public void setIsShareSameKind(Boolean isShareSameKind) {
		this.isShareSameKind = isShareSameKind;
	}

	public Boolean getIsShareNotSameType() {
		if (isShareNotSameType == null)
			isShareNotSameType = false;
		return isShareNotSameType;
	}

	public void setIsShareNotSameType(Boolean isShareNotSameType) {
		this.isShareNotSameType = isShareNotSameType;
	}
}