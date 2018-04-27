package slfx.jp.pojo.dto.flight;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：航班舱位信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:51:08
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SlfxFlightClassDTO extends BaseJpDTO{
	
	/**
	 *  舱位代码
	 */
	private String classCode;
	
	/**
	 * 舱位类型 
	 */
	private String classType;
	
	/**
	 * 票面价
	 */
	private Double originalPrice;
	
	/**
	 * 折扣
	 */
	private Integer discount;
	
	/**
	 * 代理费
	 */
	private Double proxyCost;
	
	/**
	 * 奖励
	 */
	private Double awardCost;
	
	 /**
	  * 结算
	  */
	private Double settleAccounts;
	
	 /**
	  * 剩余座位
	  */
	private String lastSeat;
	
	/**
	 * 退改签政策KEY
	 */
	private String tgNoteKey;
	
	 /**
	  * 是否允许儿童预订
	  */
	private Boolean allowChildren;

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

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
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

	public String getLastSeat() {
		return lastSeat;
	}

	public void setLastSeat(String lastSeat) {
		this.lastSeat = lastSeat;
	}

	public Boolean getAllowChildren() {
		return allowChildren;
	}

	public void setAllowChildren(Boolean allowChildren) {
		this.allowChildren = allowChildren;
	}

	public Double getSettleAccounts() {
		return settleAccounts;
	}

	public void setSettleAccounts(Double settleAccounts) {
		this.settleAccounts = settleAccounts;
	}

	public String getTgNoteKey() {
		return tgNoteKey;
	}

	public void setTgNoteKey(String tgNoteKey) {
		this.tgNoteKey = tgNoteKey;
	}
	
}
