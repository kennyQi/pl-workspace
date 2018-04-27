package zzpl.pojo.dto.jp;


/**
 * 航班舱位信息
 * DTO
 * @author tandeng
 *
 */
public class FlightClassInfoDTO {
	
   /** 票面价 */
   private Double originalPrice;
   
   /** 折扣% */
   private int discount;
   
   /** 代理费 */
   private Double proxyCost;
   
   /** 奖励 */
   private Double awardCost;
   
   /** 结算 */
   private Double settleAccounts;
   
   /** 剩余座位 */
   private String lastSeat;
   
   /** 舱位代码 */
   private String classCode;
   
   /** 舱位类型 */
   private String classType;
   
   /** 是否允许儿童预订 */
   private Boolean allowChildren;
   
   /** 退改规定key */
   private String cancelRuleKey;
   
   /** 舱位政策key */
   private String classPolicyKey;

	public Double getOriginalPrice() {
		return originalPrice;
	}
	
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public Double getProxyCost() {
		return proxyCost;
	}
	
	public void setProxyCost(Double proxyCost) {
		this.proxyCost = proxyCost;
	}
	
	public Double getAwardCost() {
		return awardCost;
	}
	
	public void setAwardCost(Double awardCost) {
		this.awardCost = awardCost;
	}
	
	public Double getSettleAccounts() {
		return settleAccounts;
	}
	
	public void setSettleAccounts(Double settleAccounts) {
		this.settleAccounts = settleAccounts;
	}
	
	public String getLastSeat() {
		return lastSeat;
	}
	
	public void setLastSeat(String lastSeat) {
		this.lastSeat = lastSeat;
	}
	
	public String getClassCode() {
		return classCode;
	}
	
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
	public String getClassType() {
		return classType;
	}
	
	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	public Boolean getAllowChildren() {
		return allowChildren;
	}
	
	public void setAllowChildren(Boolean allowChildren) {
		this.allowChildren = allowChildren;
	}
	
	public String getCancelRuleKey() {
		return cancelRuleKey;
	}
	
	public void setCancelRuleKey(String cancelRuleKey) {
		this.cancelRuleKey = cancelRuleKey;
	}
	
	public String getClassPolicyKey() {
		return classPolicyKey;
	}
	
	public void setClassPolicyKey(String classPolicyKey) {
		this.classPolicyKey = classPolicyKey;
	}
   
}