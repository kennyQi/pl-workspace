package slfx.xl.domain.model.line;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import slfx.xl.domain.model.M;

/**
 * @类功能说明：线路基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月8日上午9:27:32
 * @版本：V1.0
 */
@Embeddable
public class LineBaseInfo{
	
	/**
	 * 线路名称
	 */
	@Column(name="NAME", length=64)
	private String name;
	
	/**
	 * 线路编号
	 */
	@Column(name="NUMBER", length=64)
	private String number;
	
	/**
	 * 推荐指数
	 */
	@Column(name="RECOMMENDATION_LEVEL")
	private Integer recommendationLevel;

	public final static Integer RLEVEL_1 = 1;
	public final static Integer RLEVEL_2 = 2;
	public final static Integer RLEVEL_3 = 3;
	public final static Integer RLEVEL_4 = 4;
	public final static Integer RLEVEL_5 = 5;
	
	
	/**
	 * 线路类型
	 */
	@Column(name="TYPE")
	private Integer type;

	public final static Integer TYPE_WITH_GROUP = 1; // 跟团游
	public final static Integer TYPE_SELF_HELP = 2; // 自助游
	
	/**
	 * 线路类型中的城市编号
	 */
	@Column(name="CITY_OF_TYPE")
	private String cityOfType;
	
	/**
	 * 出发地城市编号
	 */
	@Column(name = "STARTING_CITY")
	private String starting;
	
	/**
	 * 目地的列表 ,目的地城市编号，用","隔开
	 */
	@Column(name = "DESTINATION_CITY")
	private String destinationCity;
	
	
	
	/**
	 * 线路特色
	 */
	@Column(name="FEATURE_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String featureDescription;
	
	/**
	 * 线路优惠
	 */
	@Column(name="FAVORABLE_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String favorableDescription;
	/**
	 * 提示信息
	 */
	@Column(name="NOTICE_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String noticeDescription;
	/**
	 * 交通信息
	 */
	@Column(name="TRAFFIC_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String trafficDescription;
	/**
	 * 费用说明
	 */
	@Column(name="FEE_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String feeDescription;
	/**
	 * 预订须知
	 */
	@Column(name="BOOK_DESCRIPTION", columnDefinition=M.TEXT_COLUM)
	private String bookDescription;
	
	/**
	 * 创建日期
	 */
	@Column(name="CREATE_DATE")
	private Date createDate;
	/**
	 * 销量
	 */
	@Column(name="SALES")
	private Integer sales;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}
	
	
}