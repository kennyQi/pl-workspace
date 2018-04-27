package lxs.api.v1.dto.line;

import java.util.List;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class LineInfoDTO extends BaseDTO {
	/**
	 * 线路id
	 */
	private String lineID;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 线路编号
	 */
	private String lineNO;	
	/**
	 * 销量
	 */
	private String sales;
	/**
	 * 行程概况
	 */
	private String destinationCity;
	/**
	 * 线路类型
	 */
	private String type;
	/**
	 * 线路推荐
	 */
	private String recommendationLevel;
	/**
	 * 价格
	 */
	private String minPrice;
	
	/**
	 * 轮播图
	 */
	private List<String> uriList;
	/**
	 * 订金支付比例
	 */
	private String downPayment;

	/**
	 * 需付全款提前天数
	 */
	private String payTotalDaysBeforeStart;

	/**
	 * 最晚付款时间出发日期前
	 */
	private String lastPayTotalDaysBeforeStart;

	
	public String getLineNO() {
		return lineNO;
	}

	public void setLineNO(String lineNO) {
		this.lineNO = lineNO;
	}

	public String getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}

	public String getPayTotalDaysBeforeStart() {
		return payTotalDaysBeforeStart;
	}

	public void setPayTotalDaysBeforeStart(String payTotalDaysBeforeStart) {
		this.payTotalDaysBeforeStart = payTotalDaysBeforeStart;
	}

	public String getLastPayTotalDaysBeforeStart() {
		return lastPayTotalDaysBeforeStart;
	}

	public void setLastPayTotalDaysBeforeStart(
			String lastPayTotalDaysBeforeStart) {
		this.lastPayTotalDaysBeforeStart = lastPayTotalDaysBeforeStart;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecommendationLevel() {
		return recommendationLevel;
	}

	public void setRecommendationLevel(String recommendationLevel) {
		this.recommendationLevel = recommendationLevel;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public List<String> getUriList() {
		return uriList;
	}

	public void setUriList(List<String> uriList) {
		this.uriList = uriList;
	}
}
