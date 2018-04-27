package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;
import hg.dzpw.pojo.common.util.StringFilterUtil;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;

import java.util.Date;

/**
 * @类功能说明：经销商Qo
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午5:03:19
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class DealerQo extends BaseQo {

	/**
	 * 是否降序排序
	 */
	private boolean isOrderDesc;
	/**
	 * 经销商名称
	 */
	private String name;

	/**
	 * 经销商名称模糊查询
	 */
	private Boolean nameLike = false;

	private Date createDateBegin;

	private Date createDateEnd;

	/**
	 * 经销商状态 0禁用 1使用
	 */
	private Integer status;

	/**
	 * 联系人
	 */
	private String linkMan;

	/**
	 * 联系人模糊查询
	 */
	private Boolean linkManLike = false;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 联系邮箱
	 */
	private String email;

	/**
	 * 经销商代码
	 */
	private String key;

	/**
	 * 省
	 */
	private ProvinceQo provinceQo;

	/**
	 * 市
	 */
	private CityQo cityQo;

	/**
	 * 区
	 */
	private AreaQo areaQo;
	/**
	 * 登录帐号
	 */
	private String adminLoginName;
	private Boolean adminLoginNameLike;
	/**
	 * 是否逻辑删除
	 */
	private Boolean removed = false;

	/**
	 * 经销商设置（景区后台查询必须带上）
	 */
	private DealerScenicspotSettingQo scenicspotSettingQo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? 
				null : StringFilterUtil.reverseString(name
						.trim());
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public Boolean getLinkManLike() {
		return linkManLike;
	}

	public void setLinkManLike(Boolean linkManLike) {
		this.linkManLike = linkManLike;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public AreaQo getAreaQo() {
		return areaQo;
	}

	public void setAreaQo(AreaQo areaQo) {
		this.areaQo = areaQo;
	}

	public DealerScenicspotSettingQo getScenicspotSettingQo() {
		return scenicspotSettingQo;
	}

	public void setScenicspotSettingQo(
			DealerScenicspotSettingQo scenicspotSettingQo) {
		this.scenicspotSettingQo = scenicspotSettingQo;
	}

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
	}


	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public Boolean getAdminLoginNameLike() {
		return adminLoginNameLike;
	}

	public void setAdminLoginNameLike(Boolean adminLoginNameLike) {
		this.adminLoginNameLike = adminLoginNameLike;
	}

	public boolean isOrderDesc() {
		return isOrderDesc;
	}

	public void setOrderDesc(boolean isOrderDesc) {
		this.isOrderDesc = isOrderDesc;
	}

}
