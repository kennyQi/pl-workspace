package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 价格明细
 * @author guotx
 * 2015-07-10
 */
@XStreamAlias("priceDetailInfo")
public class PriceDetailInfo {
	/**
	 * 旅客类型
	 */
	private String passengerType;
	/**
	 * 每种旅客对应的人数
	 */
	private String psgNum;
	/**
	 * 票面价(单张运价)
	 */
	private String ordDetAirportFee;
	/**
	 * 基础返点
	 */
	private String ordBasicDisc;
	/**
	 * 奖励返点
	 */
	private String ordGDisc;
	/**
	 * 单张税费
	 */
	private String ordDetTax;
	/**
	 * 单张支付总价
	 */
	private String ordDetPrice;
	/**
	 * 开票费
	 */
	private String ordDetOutTickMoney;
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getPsgNum() {
		return psgNum;
	}
	public void setPsgNum(String psgNum) {
		this.psgNum = psgNum;
	}
	public String getOrdDetAirportFee() {
		return ordDetAirportFee;
	}
	public void setOrdDetAirportFee(String ordDetAirportFee) {
		this.ordDetAirportFee = ordDetAirportFee;
	}
	public String getOrdBasicDisc() {
		return ordBasicDisc;
	}
	public void setOrdBasicDisc(String ordBasicDisc) {
		this.ordBasicDisc = ordBasicDisc;
	}
	public String getOrdGDisc() {
		return ordGDisc;
	}
	public void setOrdGDisc(String ordGDisc) {
		this.ordGDisc = ordGDisc;
	}
	public String getOrdDetTax() {
		return ordDetTax;
	}
	public void setOrdDetTax(String ordDetTax) {
		this.ordDetTax = ordDetTax;
	}
	public String getOrdDetPrice() {
		return ordDetPrice;
	}
	public void setOrdDetPrice(String ordDetPrice) {
		this.ordDetPrice = ordDetPrice;
	}
	public String getOrdDetOutTickMoney() {
		return ordDetOutTickMoney;
	}
	public void setOrdDetOutTickMoney(String ordDetOutTickMoney) {
		this.ordDetOutTickMoney = ordDetOutTickMoney;
	}
}
