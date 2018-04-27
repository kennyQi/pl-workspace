package slfx.mp.pojo.dto.platformpolicy;

import slfx.mp.pojo.dto.BaseMpDTO;

@SuppressWarnings("serial")
public class DatePriceDTO extends BaseMpDTO{

	private String saleDate;
	
	private String salePrice;
	
	private Integer num;

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
