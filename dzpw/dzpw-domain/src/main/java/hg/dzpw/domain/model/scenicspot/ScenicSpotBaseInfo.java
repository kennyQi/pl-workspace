package hg.dzpw.domain.model.scenicspot;

import hg.dzpw.domain.model.M;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;


/**
 * @类功能说明:景区基本信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:08:06
 * @版本：V1.0
 */
@Embeddable
public class ScenicSpotBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Map<String, String> THEME_TYPE;
	/**
	 * 景区代码前缀，JQ1001
	 */
	public static final String SCEN_CODE_PREFIX="JQ";
	static {
		THEME_TYPE = new HashMap<String, String>();
		THEME_TYPE.put("1", "名胜古迹");
		THEME_TYPE.put("2", "古镇");
		THEME_TYPE.put("3", "博物馆");
		THEME_TYPE.put("4", "自然风光");
		THEME_TYPE.put("5", "滑雪");
		THEME_TYPE.put("6", "漂流");
		THEME_TYPE.put("7", "主题公园");
		THEME_TYPE.put("8", "动物园");
		THEME_TYPE.put("9", "植物园");
		THEME_TYPE.put("10", "海洋馆");
		THEME_TYPE.put("11", "城市观光");
		THEME_TYPE.put("12", "影视基地");
		THEME_TYPE.put("13", "温泉");
		THEME_TYPE.put("14", "游船");
		THEME_TYPE.put("15", "科技馆");
		THEME_TYPE.put("16", "展览");
	}

	/**
	 * 景区名称
	 */
	@Column(name = "NAME", length = 128)
	private String name;

	/**
	 * 景区简称
	 */
	@Column(name = "SHORTNAME", length = 64)
	private String shortName;

	/**
	 * 景区别名
	 */
	@Column(name = "ALIASNAME", length = 64)
	private String aliasName;
	/**
	 * 景区代码
	 */
	@Column(name = "CODE", length = 64)
	private String code;

	/**
	 * 景区级别(AAAAA级、AAAA级、AAA级、AA级、A级) 质量等级
	 */
	@Column(name = "LEVEL", length = 32)
	private String level;

	/**
	 * 建筑部级别(国家级风景名胜区，省级风景名胜区，其他)
	 */
	@Column(name = "BUILD_LEVEL", length = 32)
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
	 * 景区主题
	 */
	@Column(name = "THEME", length = 64)
	private String theme;

	/**
	 * 保存景区主题值,非数据库字段 存储静态数据THEME_TYPE的key
	 */
	@Transient
	private String[] themeValue;

	/**
	 * 开放时间(每天9:30:00-17:30:00)
	 */
	@Column(name = "OPEN_TIME", length = 64)
	private String openTime;

	/**
	 * 景区网址
	 */
	@Column(name = "WEB_SITE", length = 512)
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVINCE_ID")
	private Province province;

	/**
	 * 所在市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private City city;

	/**
	 * 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA_ID")
	private Area area;

	/**
	 * 街道地址
	 */
	@Column(name = "STREET", length = 512)
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
	 * 创建的管理者id
	 */
	@Column(name = "CREATE_ADMIN_ID", length = 64)
	private String createAdminId;

	/**
	 * 门票的默认有效天
	 */
	@Column(name = "TICKET_DEFAULT_VALID_DAYS", columnDefinition = M.NUM_COLUM)
	private Integer ticketDefaultValidDays = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite == null ? null : webSite.trim();
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateAdminId() {
		return createAdminId;
	}

	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId == null ? null : createAdminId
				.trim();
	}

	public Integer getTicketDefaultValidDays() {
		return ticketDefaultValidDays;
	}

	public void setTicketDefaultValidDays(Integer ticketDefaultValidDays) {
		this.ticketDefaultValidDays = ticketDefaultValidDays;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(String buildLevel) {
		this.buildLevel = buildLevel;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String[] getThemeValue() {
		if (StringUtils.isNotBlank(this.theme)) {
			JSONArray themeArray=JSONArray.parseArray(this.theme);
			themeValue=new String[themeArray.size()];
			for (int i = 0; i < themeArray.size(); i++) {
				themeValue[i]=themeArray.getString(i);
			}
		}
		return themeValue;
	}

	public void setThemeValue(String[] themeValue) {
		this.themeValue = themeValue;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getPreNotice() {
		return preNotice;
	}

	public void setPreNotice(String preNotice) {
		this.preNotice = preNotice;
	}

	public static Map<String, String> getThemeType() {
		return THEME_TYPE;
	}

}