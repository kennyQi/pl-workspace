package hg.fx.domain;

import java.util.Date;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：商户注册
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-7-14上午11:48:15
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = O.FX_TABLE_PREFIX + "DISTRIBUTOR_REGISTER")
public class DistributorRegister extends BaseStringIdModel {

	/** 待审核 */
	public static final int DISTRIBUTOR_REGISTER_UNCHECK = 0;
	/** 已通过 */
	public static final int DISTRIBUTOR_REGISTER_CHECK_SUCC = 1;
	/** 已拒绝 */
	public static final int DISTRIBUTOR_REGISTER_CHECK_FAIL = 2;

	/**
	 * 登录名
	 */
	@Column(name = "LOGIN_NAME", length = 32)
	private String loginName;

	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", length = 128)
	private String passwd;

	/**
	 * 公司名称
	 */
	@Column(name = "COMPANY_NAME", length = 256)
	private String companyName;

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 32)
	private String linkMan;

	/**
	 * 联系电话(手机号)
	 */
	@Column(name = "MOBILE", length = 16)
	private String phone;

	/**
	 * 公司网址
	 */
	@Column(name = "WEB_SITE", length = 1024)
	private String webSite;

	/**
	 * 注册申请日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;

	/**
	 * 状态 0--待审核 1--已通过 2--已拒绝
	 */
	@Column(name = "STATUS", columnDefinition = O.NUM_COLUM)
	private Integer status;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusName() {
		switch (status.intValue()) {
		case DISTRIBUTOR_REGISTER_UNCHECK:

			return "待审核";
		case DISTRIBUTOR_REGISTER_CHECK_SUCC:

			return "已通过";
		case DISTRIBUTOR_REGISTER_CHECK_FAIL:

			return "已拒绝";

		default:
			return "";
		}
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
