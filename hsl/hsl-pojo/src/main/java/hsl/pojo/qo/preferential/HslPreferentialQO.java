package hsl.pojo.qo.preferential;

import java.util.Date;

import hsl.pojo.qo.mp.HslADQO;
@SuppressWarnings("serial")
public class HslPreferentialQO extends HslADQO{
	/**
	 * 原价价格
	 */
	private Double price;
	/**
	 * 特价价格
	 */
	private Double specPrice;
	/**
	 * 广告ID
	 */
	private String adId;
	/**
	 * 发团日期
	 */
	
	private Date startDate;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSpecPrice() {
		return specPrice;
	}
	public void setSpecPrice(Double specPrice) {
		this.specPrice = specPrice;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
