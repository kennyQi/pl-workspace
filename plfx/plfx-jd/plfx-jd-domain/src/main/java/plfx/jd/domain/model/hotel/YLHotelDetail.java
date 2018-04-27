package plfx.jd.domain.model.hotel;

import javax.persistence.Column;



/****
 * 
 * @类功能说明：酒店详情
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月15日上午9:18:06
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YLHotelDetail{

	/***
	 * 名称
	 */
	@Column(name = "NAME")
	private String name;
	/***
	 * 地址
	 */
	@Column(name = "ADDRESS")
	private String address;
	/***
	 * 挂牌星级
	 */
	@Column(name = "STARRATE")
	private String starRate;
	/***
	 * 艺龙推荐星级
	 */
	@Column(name = "CATEGORY")
	private String category;
	/***
	 * 电话
	 */
	@Column(name = "PHONE")
	private String phone;
	/***
	 * 传真
	 */
	@Column(name = "FAX")
	private String fax;
	/***
	 * 开业时间
	 */
	@Column(name = "ESTABLISHMENTDATE")
	private String establishmentDate;
	/***
	 * 装修时间
	 */
	@Column(name = "RENOVATIONDATE")
	private String renovationDate;
	/***
	 * 集团编号
	 */
	@Column(name = "GROUPID")
	private String groupId;
	/***
	 * 品牌编号
	 */
	@Column(name = "BRANDID")
	private String brandId;
	/***
	 * 是否经济型
	 */
	@Column(name = "ISECONOMIC")
	private String isEconomic;
	/***
	 * 是否是公寓
	 */
	@Column(name = "ISAPARTMENT")
	private String isApartment;
	
	/***
	 * google纬度
	 */
	@Column(name = "GOOGLETLAT")
	private String googleLat;
	/***
	 * google经度
	 */
	@Column(name = "GOOGLELON")
	private String googleLon;
	/***
	 * baidu纬度
	 */
	@Column(name = "BAIDULAT")
	private String baiduLat;
	/***
	 * baidu经度
	 */
	@Column(name = "BAIDULON")
	private String baiduLon;
	/***
	 * 城市编码
	 */
	@Column(name = "CITYID")
	private String cityId;
	/***
	 * 商圈编码
	 */
	@Column(name = "BUSINESSZONE")
	private String businessZone;
	/***
	 * 行政区编码
	 */
	@Column(name = "DISTRICT")
	private String district;
	/****
	 * 酒店支持的信用卡
	 */
	@Column(name = "CREDITCARDS")
	private String 	creditCards;
	/***
	 * 介绍信息
	 */
	@Column(name = "INTROEDITOR",columnDefinition ="TEXT")
	private String introEditor;
	/***
	 * 描述
	 */
	@Column(name = "DESCRIPTION",columnDefinition ="TEXT")
	private String description;
	/***
	 * 服务设施
	 */
	@Column(name = "GENERALAMENITIE",columnDefinition ="TEXT")
	private String generalAmenities;
	/**
	 * 房间设施
	 */
	@Column(name = "ROOMAMEMITIES",columnDefinition ="TEXT")
	private String roomAmenities;
	/**
	 * 休闲设施
	 */
	@Column(name = "RECREATIONAMENITIES",columnDefinition ="TEXT")
	private String recreationAmenities;
	
	/***
	 * 会议设施
	 */
	@Column(name = "CONFERENCEAMENITIES",columnDefinition ="TEXT")
	private String conferenceAmenities;
	/***
	 * 餐饮设施
	 */
	@Column(name = "DININGAMENITIES",columnDefinition ="TEXT")
	private String diningAmenities;
	/**
	 * 周边交通
	 */
	@Column(name = "TRAFFIC",columnDefinition ="TEXT")
	private String traffic;
	
	/***
	 * 特色信息
	 */
	@Column(name = "FEATURES",columnDefinition ="TEXT")
	private String features;
	/***
	 * 设施列表
	 */
	@Column(name = "FACILITIES",columnDefinition ="TEXT")
	private String facilities;
	
	/***
	 * 是否允许返现
	 */
	@Column(name = "HASCOUPON")
	private String hasCoupon;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStarRate() {
		return starRate;
	}

	public void setStarRate(String starRate) {
		this.starRate = starRate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(String establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getRenovationDate() {
		return renovationDate;
	}

	public void setRenovationDate(String renovationDate) {
		this.renovationDate = renovationDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getIsEconomic() {
		return isEconomic;
	}

	public void setIsEconomic(String isEconomic) {
		this.isEconomic = isEconomic;
	}

	public String getIsApartment() {
		return isApartment;
	}

	public void setIsApartment(String isApartment) {
		this.isApartment = isApartment;
	}

	public String getGoogleLat() {
		return googleLat;
	}

	public void setGoogleLat(String googleLat) {
		this.googleLat = googleLat;
	}

	public String getGoogleLon() {
		return googleLon;
	}

	public void setGoogleLon(String googleLon) {
		this.googleLon = googleLon;
	}

	public String getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(String baiduLat) {
		this.baiduLat = baiduLat;
	}

	public String getBaiduLon() {
		return baiduLon;
	}

	public void setBaiduLon(String baiduLon) {
		this.baiduLon = baiduLon;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getBusinessZone() {
		return businessZone;
	}

	public void setBusinessZone(String businessZone) {
		this.businessZone = businessZone;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(String creditCards) {
		this.creditCards = creditCards;
	}

	public String getIntroEditor() {
		return introEditor;
	}

	public void setIntroEditor(String introEditor) {
		this.introEditor = introEditor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGeneralAmenities() {
		return generalAmenities;
	}

	public void setGeneralAmenities(String generalAmenities) {
		this.generalAmenities = generalAmenities;
	}

	public String getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(String roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	public String getRecreationAmenities() {
		return recreationAmenities;
	}

	public void setRecreationAmenities(String recreationAmenities) {
		this.recreationAmenities = recreationAmenities;
	}

	public String getConferenceAmenities() {
		return conferenceAmenities;
	}

	public void setConferenceAmenities(String conferenceAmenities) {
		this.conferenceAmenities = conferenceAmenities;
	}

	public String getDiningAmenities() {
		return diningAmenities;
	}

	public void setDiningAmenities(String diningAmenities) {
		this.diningAmenities = diningAmenities;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	
	
	
}
