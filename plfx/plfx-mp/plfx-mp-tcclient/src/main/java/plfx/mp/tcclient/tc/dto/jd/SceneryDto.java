package plfx.mp.tcclient.tc.dto.jd;


public class SceneryDto  extends JdDto{
	/**
	 * 省份Id
	 */
	private String provinceId;
	/**
	 * 城市Id 根据城市Id查景点参数
	 */
	private String cityId;
	/**
	 * 行政区划（县）id
	 */
	private String countryId;
	/**
	 * 页数 不传默认为1
	 */
	private Integer page;
	/**
	 * 分页大小 不传默认为10，最大为100
	 */
	private String pageSize;
	/**
	 * 排序类型0-	点评由多到少
	 * 1-	景区级别
	 * 2-	同程推荐
	 * 3-	人气指数
	 */
	private String sortType;
	/**
	 *搜索关键词 用于模糊搜索
	 */
	private String keyword;
	/**
	 * 搜索字段当有keyword时必传入，多个用英文逗号隔开如：field1,field2 。有以下字段:LEGAL_REACH_STR
	 */
	private String searchFields;
	/**
	 * 星级Id 如1,2,3,4,5，可传多个，以英文逗号隔开
	 */
	private String gradeId;
	/**
	 * 主题Id 如1,2,3,4,5，可传多个，以英文逗号隔开
	 */
	private String themeId;
	/**
	 * 价格范围 如0,50，表示0到50
	 */
	private String priceRange;
	/**
	 * 坐标系统
	 */
	private String cs;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 半径 有经纬度时必传,单位:米
	 */
	private String radius;
	
	
	
	public SceneryDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamSceneryList");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultSceneryList");
		this.setServiceName("GetSceneryList");
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSearchFields() {
		return searchFields;
	}
	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	
}
