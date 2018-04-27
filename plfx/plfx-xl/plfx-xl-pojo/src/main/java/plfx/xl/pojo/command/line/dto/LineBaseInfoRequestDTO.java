package plfx.xl.pojo.command.line.dto;

import plfx.xl.pojo.dto.EmbeddDTO;

/**
 *@类功能说明：线路基本信息请求DTO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月8日上午9:11:29
 */
@SuppressWarnings("serial")
public class LineBaseInfoRequestDTO extends EmbeddDTO{

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 编号
	 */
	private String number;
	
	/**
	 * 推荐级别
	 */
	private Integer recommendationLevel;
	
	/**
	 * 线路类别
	 */
	private Integer type;
	
	/**
	 * 线路类型中的城市编号
	 */
	private String cityOfType;
	
	/**
	 * 始发城市编号
	 */
	private String starting;
	
	/**
	 * 目地的列表 ,目的地城市编号，用","隔开
	 */
	private String destinationCity;
	
	/**
	 * 线路特色
	 */
	private String featureDescription;
	
	/**
	 * 线路优惠
	 */
	private String favorableDescription;
	
	/**
	 * 提示信息
	 */
	private String noticeDescription;
	
	/**
	 * 交通信息
	 */
	private String trafficDescription;
	
	/**
	 * 费用说明
	 */
	private String feeDescription;
	
	/**
	 * 预订须知
	 */
	private String bookDescription;
	
	/***
	 * 线路类型中的线路级别(1国内线,2国外线)
	 */
	private String  typeGrade;
		
	/**
	 * 出发地线路级别(1国内线,2国外线)
	 */
	private String  startGrade;
	

	public String getTypeGrade() {
		return typeGrade;
	}

	public void setTypeGrade(String typeGrade) {
		this.typeGrade = typeGrade;
	}

	public String getStartGrade() {
		return startGrade;
	}

	public void setStartGrade(String startGrade) {
		this.startGrade = startGrade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getRecommendationLevel() {
		return recommendationLevel;
	}

	public void setRecommendationLevel(Integer recommendationLevel) {
		this.recommendationLevel = recommendationLevel;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFeatureDescription() {
		return featureDescription;
	}

	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}

	public String getFavorableDescription() {
		return favorableDescription;
	}

	public void setFavorableDescription(String favorableDescription) {
		this.favorableDescription = favorableDescription;
	}

	public String getNoticeDescription() {
		return noticeDescription;
	}

	public void setNoticeDescription(String noticeDescription) {
		this.noticeDescription = noticeDescription;
	}

	public String getTrafficDescription() {
		return trafficDescription;
	}

	public void setTrafficDescription(String trafficDescription) {
		this.trafficDescription = trafficDescription;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}


	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}

	
	
	
	
	
	
}
