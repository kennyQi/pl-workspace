package hsl.pojo.dto.mp;

/**
 * 经销产品价格
 * 
 * @author yuxx
 */
public class DateSalePriceDTO {

	/**
	 * 平台价格
	 */
	private Double salePrice;

	/**
	 * 销售日期 YYYY-MM-DD
	 */
	private String saleDate;

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

}