package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;
import hg.dzpw.pojo.common.util.StringFilterUtil;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @类功能说明：景区查询对象
 * @类修改者：
 * @修改日期：2014-11-12下午3:18:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12下午3:18:10
 */
public class ScenicSpotQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间(排序：>0 asc <0 desc)
	 */
	private int crateDateSort;

	/**
	 * 景区名称
	 */
	private String name;
	private boolean nameLike;

	/**
	 * 景区简称
	 */
	private String shortName;
	private boolean shortNameLike;

	/**
	 * 景区代码
	 */
	private String code;

	/**
	 * 景区级别
	 */
	private String[] level;

	/**
	 * 所在省
	 */
	private ProvinceQo provinceQo;
	private boolean provinceFetchAble;

	/**
	 * 所在市
	 */
	private CityQo cityQo;
	private boolean cityFetchAble;
	
	/**
	 * 所在地区
	 */
	private AreaQo areaQo;
	private boolean areaFetchAble;

	/**
	 * 创建时间
	 */
	private Date createDateBegin;
	private Date createDateEnd;

	/**
	 * 创建的管理者id
	 */
	private String createAdminId;

	/**
	 * 联票的默认有效天
	 */
	private Integer ticketDefaultValidDaysMin;
	private Integer ticketDefaultValidDaysMax;

	/**
	 * 联系人
	 */
	private String linkMan;
	private boolean linkManLike;

	/**
	 * 联系电话
	 */
	private String telephone;
	private boolean telephoneLike;

	/**
	 * 邮箱
	 */
	private String email;
	private boolean emailLike;

	/**
	 * 联系QQ
	 */
	private String qq;
	private boolean qqLike;

	/**
	 * 联系地址(模糊匹配)
	 */
	private String address;

	/**
	 * 登录帐号
	 */
	private String adminLoginName;
	private boolean adminLoginNameLike;

	/**
	 * 是否启用
	 */
	private Boolean activated;

	/**
	 * 是否被逻辑删除
	 */
	private Boolean removed = false;

	/**
	 * 套票数（联票数）
	 */
	private Integer groupTicketNumberMin;
	private Integer groupTicketNumberMax;

	/**
	 * 查询所有入园设备
	 */
	private boolean fetchAllDevices;
	/**
	 * 查询所有景区图片
	 */
	private boolean fetchAllPic;
	
	/**
	 * 修改时间开始
	 */
	private Date modifyDateBegin;

	/**
	 * 修改时间截止
	 */
	private Date modifyDateEnd;
	
	/**
	 * 景区密钥
	 */
	private String secretKey;
	/**
	 * 账户类型  1-汇金宝支付平台账户   2-支付宝账户
	 */
	private Integer accountType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : StringFilterUtil.reverseString(name
				.trim());
	}

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : StringFilterUtil
				.reverseString(shortName.trim());
	}

	public boolean isShortNameLike() {
		return shortNameLike;
	}

	public void setShortNameLike(boolean shortNameLike) {
		this.shortNameLike = shortNameLike;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : StringFilterUtil.reverseString(code
				.trim());
	}

	public String[] getLevel() {
		return level;
	}

	public void setLevel(String... level) {
		this.level = level;
	}

	public ProvinceQo getProvinceQo() {
		return provinceQo;
	}

	public void setProvinceQo(ProvinceQo provinceQo) {
		this.provinceQo = provinceQo;
	}

	public CityQo getCityQo() {
		return cityQo;
	}

	public void setCityQo(CityQo cityQo) {
		this.cityQo = cityQo;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		if (null != createDateBegin)
			createDateBegin = DateUtils.truncate(createDateBegin,
					Calendar.DAY_OF_MONTH);
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		if (null != createDateEnd)
			createDateEnd = DateUtils
					.addSeconds(DateUtils.ceiling(createDateEnd,
							Calendar.DAY_OF_MONTH), -1);
		this.createDateEnd = createDateEnd;
	}

	public String getCreateAdminId() {
		return createAdminId;
	}

	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId == null ? null : createAdminId
				.trim();
	}

	public Integer getTicketDefaultValidDaysMin() {
		return ticketDefaultValidDaysMin;
	}

	public void setTicketDefaultValidDaysMin(Integer ticketDefaultValidDaysMin) {
		this.ticketDefaultValidDaysMin = ticketDefaultValidDaysMin;
	}

	public Integer getTicketDefaultValidDaysMax() {
		return ticketDefaultValidDaysMax;
	}

	public void setTicketDefaultValidDaysMax(Integer ticketDefaultValidDaysMax) {
		this.ticketDefaultValidDaysMax = ticketDefaultValidDaysMax;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan == null ? null : StringFilterUtil
				.reverseString(linkMan.trim());
	}

	public boolean isLinkManLike() {
		return linkManLike;
	}

	public void setLinkManLike(boolean linkManLike) {
		this.linkManLike = linkManLike;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public boolean isTelephoneLike() {
		return telephoneLike;
	}

	public void setTelephoneLike(boolean telephoneLike) {
		this.telephoneLike = telephoneLike;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public boolean isEmailLike() {
		return emailLike;
	}

	public void setEmailLike(boolean emailLike) {
		this.emailLike = emailLike;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public boolean isQqLike() {
		return qqLike;
	}

	public void setQqLike(boolean qqLike) {
		this.qqLike = qqLike;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : StringFilterUtil
				.reverseString(address.trim());
	}

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName == null ? null : StringFilterUtil
				.reverseString(adminLoginName.trim());
	}

	public boolean isAdminLoginNameLike() {
		return adminLoginNameLike;
	}

	public void setAdminLoginNameLike(boolean adminLoginNameLike) {
		this.adminLoginNameLike = adminLoginNameLike;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public Integer getGroupTicketNumberMin() {
		return groupTicketNumberMin;
	}

	public void setGroupTicketNumberMin(Integer groupTicketNumberMin) {
		this.groupTicketNumberMin = groupTicketNumberMin;
	}

	public Integer getGroupTicketNumberMax() {
		return groupTicketNumberMax;
	}

	public void setGroupTicketNumberMax(Integer groupTicketNumberMax) {
		this.groupTicketNumberMax = groupTicketNumberMax;
	}

	public boolean isFetchAllDevices() {
		return fetchAllDevices;
	}

	public void setFetchAllDevices(boolean fetchAllDevices) {
		this.fetchAllDevices = fetchAllDevices;
	}

	public int getCrateDateSort() {
		return crateDateSort;
	}

	public void setCrateDateSort(int crateDateSort) {
		this.crateDateSort = crateDateSort;
	}

	public boolean isProvinceFetchAble() {
		return provinceFetchAble;
	}

	public void setProvinceFetchAble(boolean provinceFetchAble) {
		this.provinceFetchAble = provinceFetchAble;
		if (null == this.provinceQo)
			this.setProvinceQo(new ProvinceQo());
	}

	public boolean isCityFetchAble() {
		return cityFetchAble;
	}

	public void setCityFetchAble(boolean cityFetchAble) {
		this.cityFetchAble = cityFetchAble;
		if (null == this.cityQo)
			this.setCityQo(new CityQo());
	}

	public AreaQo getAreaQo() {
		return areaQo;
	}

	public void setAreaQo(AreaQo areaQo) {
		this.areaQo = areaQo;
	}

	public boolean isAreaFetchAble() {
		return areaFetchAble;
	}

	public void setAreaFetchAble(boolean areaFetchAble) {
		this.areaFetchAble = areaFetchAble;
		if (null == this.areaQo)
			this.setAreaQo(new AreaQo());
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public boolean isFetchAllPic() {
		return fetchAllPic;
	}

	public void setFetchAllPic(boolean fetchAllPic) {
		this.fetchAllPic = fetchAllPic;
	}
	
}