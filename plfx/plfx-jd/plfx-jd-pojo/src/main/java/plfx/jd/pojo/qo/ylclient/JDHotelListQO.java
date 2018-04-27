package plfx.jd.pojo.qo.ylclient;

import java.io.Serializable;

import plfx.jd.pojo.dto.ylclient.hotel.PositionDTO;
import plfx.jd.pojo.system.enumConstants.EnumPaymentType;
import plfx.jd.pojo.system.enumConstants.EnumProductProperty;
import plfx.jd.pojo.system.enumConstants.EnumQueryType;
import plfx.jd.pojo.system.enumConstants.EnumSortType;

@SuppressWarnings("serial")
public class JDHotelListQO implements Serializable {
	/**
	 * 入住日期
	 */
	protected java.util.Date arrivalDate;
	/**
	 * 离店日期
	 */
	protected java.util.Date departureDate;
	/**
	 * 城市编码
	*/
	protected String cityId;
	/**
	 * 查询关键词
	 */
	protected String queryText;
	/**
	 * 查询类型
	 */
	protected EnumQueryType queryType;
	/**
	 * 支付方式
	 */
	protected EnumPaymentType paymentType;
	/**
	 * 产品类型
	 */
	protected EnumProductProperty productProperties;
	/**
	 * 设施
	 */
	protected String facilities;
	/**
	 * 星级
	 */
	protected String starRate;
	/**
	 * 品牌编码
	 */
	protected String brandId;
	/**
	 * 酒店集团编码
	 */
	protected int groupId;
	/**
	 * 最小价格
	 */
	protected int lowRate;
	/**
	 * 最大价格
	 */
	protected int highRate;
	/**
	 * 地区编码
	 */
	protected String districtId;
	/**
	 * 地标编码
	 */
	protected String locationId;
	/**
	 * 位置查询
	 */
	protected PositionDTO position;
	/**
	 * 排序类型
	 */
	protected EnumSortType sort;
	/**
	 * 页码
	 */
	protected int pageIndex;
	/**
	 * 每页记录数
	 */
	protected int pageSize;
	/**
	 * 宾客类型
	 */
	protected String customerType;
	/**
	 * 主题
	 */
	protected String themeIds;
	/**
	 * 返回信息类型
	 */
	protected String resultType;

	public java.util.Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(java.util.Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public java.util.Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(java.util.Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public EnumQueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(EnumQueryType queryType) {
		this.queryType = queryType;
	}

	public EnumPaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(EnumPaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public EnumProductProperty getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(EnumProductProperty productProperties) {
		this.productProperties = productProperties;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getStarRate() {
		return starRate;
	}

	public void setStarRate(String starRate) {
		this.starRate = starRate;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getLowRate() {
		return lowRate;
	}

	public void setLowRate(int lowRate) {
		this.lowRate = lowRate;
	}

	public int getHighRate() {
		return highRate;
	}

	public void setHighRate(int highRate) {
		this.highRate = highRate;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public PositionDTO getPosition() {
		return position;
	}

	public void setPosition(PositionDTO position) {
		this.position = position;
	}

	public EnumSortType getSort() {
		return sort;
	}

	public void setSort(EnumSortType sort) {
		this.sort = sort;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getThemeIds() {
		return themeIds;
	}

	public void setThemeIds(String themeIds) {
		this.themeIds = themeIds;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

}
