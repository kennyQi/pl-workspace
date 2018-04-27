package hg.dzpw.domain.model.dealer;

import hg.dzpw.domain.model.M;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：经销商基础信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:11:06
 * @版本：V1.0
 */
@Embeddable
@SuppressWarnings("serial")
public class DealerBaseInfo implements Serializable {
	
	/**
	 * 名称
	 */
	@Column(name = "DEALER_NAME", length = 128)
	private String name;

	/**
	 * 简介
	 */
	@Column(name = "INTRO", length = 1024)
	private String intro;

	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE", length = 32)
	private String telephone;

	/**
	 * 联系邮箱
	 */
	@Column(name = "EMAIL", length = 128)
	private String email;

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 64)
	private String linkMan;

	// 联系地址
	/**
	 * 省
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVINCE_ID")
	private Province province;
	/**
	 * 市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private City city;
	/**
	 * 区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA_ID")
	private Area area;
	/**
	 * 联系人地址
	 */
	@Column(name = "ADDRESS", length = 64)
	private String address;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATE_ADMIN_ID", length = 64)
	private String createAdminId;

	/**
	 * 是否禁用 1启用 0禁用 添加经销商默认为启用状态
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer status;
	
	/**
	 * 是否被逻辑删除
	 */
	@Type(type = "yes_no")
	@Column(name = "REMOVED")
	private Boolean removed = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		this.createAdminId = createAdminId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

}