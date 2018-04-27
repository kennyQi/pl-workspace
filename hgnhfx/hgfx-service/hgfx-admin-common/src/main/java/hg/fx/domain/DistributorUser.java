package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：商户的账户,包括账户子账户
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午5:38:18
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "DISTRIBUTOR_USER")
public class DistributorUser extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/** 1--商户主账号  */
	public static final Integer DSTRIBUTOR_USER_TYPE_MAIN = 1;
	/** 2--商户子账号 */
	public static final Integer DSTRIBUTOR_USER_TYPE_SUB = 2;
	
	
	/**
	 * 登录名
	 */
	@Column(name = "LOGIN_NAME", length = 32)
	private String loginName;
	
	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 32)
	private String name;
	
	/**
	 * 账号类型
	 * 1--商户主账号     
	 * 2--商户子账号
	 */
	@Column(name = "STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer status;
	
	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", length = 128)
	private String passwd;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 用户所属经销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 是否被逻辑删除
	 */
	@Type(type = "yes_no")
	@Column(name = "REMOVED")
	private Boolean removed = false;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
	
}
