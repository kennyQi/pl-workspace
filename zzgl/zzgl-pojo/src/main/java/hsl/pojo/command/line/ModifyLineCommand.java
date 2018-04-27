package hsl.pojo.command.line;
import java.util.Date;
import hg.common.component.BaseCommand;
/**
 * @类功能说明：修改线路command
 * @类修改者：
 * @修改日期：2015年1月30日上午9:26:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年1月30日上午9:26:10
 *
 */
@SuppressWarnings("serial")
public class ModifyLineCommand extends BaseCommand {

	/**
	 * 线路ID
	 */
	private String lineID;

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
	 * 线路类型中的城市所在省份编号
	 */
	private String provinceOfType;

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
	 * 开始城市编号
	 */
	private String starting;

	/**
	 * 出发省份id
	 */
	private String startProvince;

	/**
	 * 目地的列表 ,目的地城市编号，用","隔开
	 */
	private String destinationCity;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 人均价格
	 */
	private Double averPrice;

	/**
	 * 订金支付比例
	 */
	private Double downPayment;

	/**
	 * 需付全款提前天数
	 */
	private Integer payTotalDaysBeforeStart;

	/**
	 * 最晚付款时间出发日期前
	 */
	private Integer lastPayTotalDaysBeforeStart;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
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

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}

	public String getProvinceOfType() {
		return provinceOfType;
	}

	public void setProvinceOfType(String provinceOfType) {
		this.provinceOfType = provinceOfType;
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

	public String getStartProvince() {
		return startProvince;
	}

	public void setStartProvince(String startProvince) {
		this.startProvince = startProvince;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getAverPrice() {
		return averPrice;
	}

	public void setAverPrice(Double averPrice) {
		this.averPrice = averPrice;
	}

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Integer getPayTotalDaysBeforeStart() {
		return payTotalDaysBeforeStart;
	}

	public void setPayTotalDaysBeforeStart(Integer payTotalDaysBeforeStart) {
		this.payTotalDaysBeforeStart = payTotalDaysBeforeStart;
	}

	public Integer getLastPayTotalDaysBeforeStart() {
		return lastPayTotalDaysBeforeStart;
	}

	public void setLastPayTotalDaysBeforeStart(
			Integer lastPayTotalDaysBeforeStart) {
		this.lastPayTotalDaysBeforeStart = lastPayTotalDaysBeforeStart;
	}

}
