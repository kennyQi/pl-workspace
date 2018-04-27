package hsl.pojo.dto.preferential;

import java.util.Date;

import hsl.pojo.dto.ad.HslAdDTO;
/**
 * 
 * @类功能说明：特惠专区DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015年4月27日上午10:32:21
 *
 */

@SuppressWarnings("serial")
public class PreferentialDTO extends HslAdDTO{
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
