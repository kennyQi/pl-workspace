package hsl.domain.model.dzp.scenicspot;

import hsl.domain.model.M;
import hsl.domain.model.dzp.meta.DZPArea;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.domain.model.dzp.meta.DZPProvince;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * 景区基本信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPScenicSpotBaseInfo implements Serializable {

	/**
	 * 景区名称
	 */
	@Column(name = "NAME", length = 128)
	private String name;

	/**
	 * 景区简称
	 */
	@Column(name = "SHORT_NAME", length = 64)
	private String shortName;
	
	/**
	 * 景区别名
	 */
	@Column(name = "ALIAS_NAME", length = 64)
	private String aliasName;

	/**
	 * 景区代码
	 */
	@Column(name = "CODE", length = 64)
	private String code;

	/**
	 * 景区级别
	 * <pre>
	 * 电子票务：(AAAAA级、AAAA级、AAA级、AA级、A级)，
	 * 直销用数字表示，5就是5A级。
	 * </pre>
	 */
	@Column(name = "LEVEL", columnDefinition = M.NUM_COLUM)
	private Integer level;

	/**
	 * 建筑部级别(国家级风景名胜区，省级风景名胜区，其他)
	 */
	@Column(name = "BUILD_LEVEL", length = 64)
	private String buildLevel;
	
	/**
	 * 景区分类
	 */
	@Column(name = "CLASSIFY", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer classify;
	
	/**
	 * 景区特色
	 */
	@Column(name = "FEATURE", length = 64)
	private String feature;
	
	/**
	 * 开放时间(每天9:30:00-17:30:00)
	 */
	@Column(name = "OPEN_TIME", length = 64)
	private String openTime;

	/**
	 * 景区网址
	 */
	@Column(name = "WEB_SITE", length = 128)
	private String webSite;

	/**
	 * 景区介绍
	 */
	@Column(name = "INTRO", columnDefinition = M.TEXT_COLUM)
	private String intro;

	/**
	 * 预定须知
	 */
	@Column(name = "PRE_NOTICE", columnDefinition = M.TEXT_COLUM)
	private String preNotice;
	
	/**
	 * 所在省
	 */
	@ManyToOne
	@JoinColumn(name = "PROVINCE_ID")
	private DZPProvince province;

	/**
	 * 所在市
	 */
	@ManyToOne
	@JoinColumn(name = "CITY_ID")
	private DZPCity city;

	/**
	 * 所在区
	 */
	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private DZPArea area;

	/**
	 * 街道地址
	 */
	@Column(name = "STREET", length = 128)
	private String street;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 修改时间
	 */
	@Column(name = "MODIFY_DATE", columnDefinition = M.DATE_COLUM)
	private Date modifyDate;

	/**
	 * 门票的默认有效天
	 */
	@Column(name = "TICKET_DEFAULT_VALID_DAYS", columnDefinition = M.NUM_COLUM)
	private Integer ticketDefaultValidDays = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(String buildLevel) {
		this.buildLevel = buildLevel;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPreNotice() {
		return preNotice;
	}

	public void setPreNotice(String preNotice) {
		this.preNotice = preNotice;
	}

	public DZPProvince getProvince() {
		return province;
	}

	public void setProvince(DZPProvince province) {
		this.province = province;
	}

	public DZPCity getCity() {
		return city;
	}

	public void setCity(DZPCity city) {
		this.city = city;
	}

	public DZPArea getArea() {
		return area;
	}

	public void setArea(DZPArea area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getTicketDefaultValidDays() {
		return ticketDefaultValidDays;
	}

	public void setTicketDefaultValidDays(Integer ticketDefaultValidDays) {
		this.ticketDefaultValidDays = ticketDefaultValidDays;
	}
}