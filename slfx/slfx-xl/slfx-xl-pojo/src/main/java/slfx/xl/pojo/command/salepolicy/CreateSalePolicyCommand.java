package slfx.xl.pojo.command.salepolicy;

import java.util.Date;
import java.util.List;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建价格政策    
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月24日上午9:59:23
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class CreateSalePolicyCommand extends BaseCommand{
	
	/**
	 * 政策名称
	 */
	private String name;
	
	/**
	 * 线路选择方式
	 */
	private Integer selectLineType;
	
	/*************************/
	
	/**
	 * 线路ID
	 */
	private List<String> lineIds;
	
	
	/*************************/
	
	/**
	 * 线路类型
	 */
	private Integer lineType;
	
	/**
	 * 线路类型城市
	 */
	private String cityOfLineType;
	
	
	/*************************/
	
	/**
	 * 最高成人价格
	 */
	private Double adultPriceMax;
	
	/**
	 * 最低成人价格
	 */
	private Double adultPriceMin;
	
	/*************************/
	
	/**
	 * 出发地城市市
	 */
	private String startingCityID;
	
	/*************************/
	
	
	
	
	/**
	 * 经销商ID
	 */
	private String dealerId;
	
	/**
	 * 优先级(默认为 1)
	 */
	private Integer priority = 1;
	
	/**
	 * 有效开始日期
	 */
	private Date startDate;
	
	/**
	 * 有效结束日期
	 */
	private Date endDate;
	
	/**
	 * 价格政策类型(1.成人价格 ； 2.儿童价格)
	 */
	private Integer priceType;
	
	/**
	 * 是否隐藏不销售(默认为不隐藏 false)
	 */
	private Boolean hide = false;
	
	/**
	 * 价格是否上涨
	 */
	private Boolean rise;

	/**
	 * 调整单位(1.百分比 ；2.元)
	 */
	private Integer unit;
	
	/**
	 * 价格政策调整幅度：如增加10元或者是增加10%
	 */
	private Double improvePrice;

	/**
	 * 备注
	 */
	private String description;
	/**
	 * 创建人
	 */
	private String  createName;
	/**
	 * 政策编号
	 */
	private String number;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLineIds() {
		return lineIds;
	}

	public void setLineIds(List<String> lineIds) {
		this.lineIds = lineIds;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public String getCityOfLineType() {
		return cityOfLineType;
	}

	public void setCityOfLineType(String cityOfLineType) {
		this.cityOfLineType = cityOfLineType;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}

	public Boolean getRise() {
		return rise;
	}

	public void setRise(Boolean rise) {
		this.rise = rise;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Double getImprovePrice() {
		return improvePrice;
	}

	public void setImprovePrice(Double improvePrice) {
		this.improvePrice = improvePrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getAdultPriceMax() {
		return adultPriceMax;
	}

	public void setAdultPriceMax(Double adultPriceMax) {
		this.adultPriceMax = adultPriceMax;
	}

	public Double getAdultPriceMin() {
		return adultPriceMin;
	}

	public void setAdultPriceMin(Double adultPriceMin) {
		this.adultPriceMin = adultPriceMin;
	}

	public String getStartingCityID() {
		return startingCityID;
	}

	public void setStartingCityID(String startingCityID) {
		this.startingCityID = startingCityID;
	}

	public Integer getSelectLineType() {
		return selectLineType;
	}

	public void setSelectLineType(Integer selectLineType) {
		this.selectLineType = selectLineType;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}