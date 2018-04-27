package hsl.pojo.dto.coupon;


import java.util.Date;


/**
 * @author Administrator
 *
 */
public class CouponUseConditionInfoDTO {
	/**
	 * 使用条件
	 * 1、订单满多少可用
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
     * 使用种类（可以同时为机票和门票的时候为1,2）
     * 1代表机票
     * 2代表门票
     */
    private String usedKind;
    
    /**
	 * 同类型是否可以叠加
	 */
    private Boolean isShareSameKind;
    
    /**
	 * 不同类型是否可以叠加
	 */
    private Boolean isShareNotSameType;
    
 
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
	public String getUsedKind() {
		return usedKind;
	}
	public void setUsedKind(String usedKind) {
		this.usedKind = usedKind;
	}

	public Boolean getIsShareNotSameType() {
		return isShareNotSameType;
	}
	public void setIsShareNotSameType(Boolean isShareNotSameType) {
		this.isShareNotSameType = isShareNotSameType;
	}

	public Boolean getIsShareSameKind() {
		return isShareSameKind;
	}
	public void setIsShareSameKind(Boolean isShareSameKind) {
		this.isShareSameKind = isShareSameKind;
	}
	
}
