package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 价格信息
 * @author guotx
 * 2015-07-07
 */
@XStreamAlias("priceInfo")
public class PriceInfo {
	/**
	 * 公布运价
	 */
	private String totalPrice;
	/**
	 * 税金
	 */
	private String totalTax;
	/**
	 * 乘客类型
	 * 成人 ADT,儿童 CNN，婴儿 INF
	 */
	private String passType;
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

}
