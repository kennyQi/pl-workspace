package hg.fx.command.abnormalRule;

import hg.framework.common.base.BaseSPICommand;


/**
 * 检查订单是否合规
 * */
@SuppressWarnings("serial")
public class CheckAbnormalRuleCommand  extends BaseSPICommand{
	
	/**
	 * 订单里程数
	 */
	private Long mileageNum;
	
	/**
	 * 充值卡号
	 * */
	private String cardNo;
	
	/**
	 * 购买的商品ID
	 */
	private String prodId;

	public Long getMileageNum() {
		return mileageNum;
	}

	public void setMileageNum(Long mileageNum) {
		this.mileageNum = mileageNum;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
}
