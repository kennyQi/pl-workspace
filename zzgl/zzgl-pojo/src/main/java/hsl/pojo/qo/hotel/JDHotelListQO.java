package hsl.pojo.qo.hotel;
import java.util.Date;
import plfx.jd.pojo.dto.ylclient.hotel.PositionDTO;
import plfx.jd.pojo.system.enumConstants.EnumPaymentType;
import plfx.jd.pojo.system.enumConstants.EnumProductProperty;
import plfx.jd.pojo.system.enumConstants.EnumQueryType;
import plfx.jd.pojo.system.enumConstants.EnumSortType;

public class JDHotelListQO  {
	/**
	 * 入住日期 大于等于昨天。当凌晨到店的搜索传入昨天的日期
	 * （必填）
	 */
	protected Date arrivalDate;
	/**
	 * 离店日期 至少晚于到店时间1天，不多于20天
	 * （必填）
	 */
	protected Date departureDate;
	/**
	 * 城市编码 使用位置查询时候可为空，其他查询需要传值全文检索，可以是酒店名、位置或品牌等。使用本参数的时候，需要输入CityId或DistictId
	 * （必填）
	 */
	protected String cityId;
	/**
	 * 查询类型
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
	 * 页码 从1开始 取值访问：1-20
	 */
	protected int pageIndex;
	/**
	 * 每页记录数
	 */
	protected int pageSize;
	/**
	 * 宾客类型
	 * （必填）
	 */
	protected String customerType;
	/**
	 * 主题    (98 家庭旅馆     99    青年旅     100    精品酒店（设计师酒店） 101    情侣酒店 ....)

	 */
	protected String themeIds;
	/**
	 * 返回信息类型
	 */
	protected String resultType;
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
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
