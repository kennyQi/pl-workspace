package hsl.pojo.dto.line;
import java.util.Date;

/**
 * 
 * 
 *@类功能说明：线路基本信息DTO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月8日上午9:11:29
 *
 */
public class LineBaseInfoDTO {

	/**
	 * 线路名称
	 */
	private String name;
	
	/**
	 * 线路编号
	 */
	private String number;
	
	/**
	 * 推荐指数
	 */
	private Integer recommendationLevel;
	
	/**
	 * 线路类型
	 */
	private Integer type;

	public final static Integer TYPE_WITH_GROUP = 1; // 跟团游
	public final static Integer TYPE_SELF_HELP = 2; // 自助游
	
	/**
	 * 线路类型中的城市编号
	 */
	private String cityOfType;
	
	/**
	 * 出发地城市编号
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
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 销量
	 */
	private Integer sales;
	
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
	public String getCityOfType() {
		return cityOfType;
	}
	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
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
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public static Integer getTypeWithGroup() {
		return TYPE_WITH_GROUP;
	}
	public static Integer getTypeSelfHelp() {
		return TYPE_SELF_HELP;
	}

}
