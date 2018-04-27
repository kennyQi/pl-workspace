package lxs.api.v1.dto.order.line;

import java.util.Date;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderBaseInfoDTO extends BaseDTO {

	private String adultNO;

	private String adultUnitPrice;

	private String childNO;

	private String childUnitPrice;

	private String bargainMoney;

	private String salePrice;	

	private Date travelDate;

	public String getAdultNO() {
		return adultNO;
	}

	public void setAdultNO(String adultNO) {
		this.adultNO = adultNO;
	}

	public String getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(String adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public String getChildNO() {
		return childNO;
	}

	public void setChildNO(String childNO) {
		this.childNO = childNO;
	}

	public String getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(String childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public String getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(String bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

}
