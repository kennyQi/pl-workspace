package zzpl.domain.model.hsluser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
//@Entity
//@Table(name=M.TABLE_PREFIX_HSL_USER+"BINDACCOUNT")
public class UserBindAccount extends BaseModel {
	
	/**
	 * 绑定的外部账户ID
	 */
	@Column(name="BIND_ACCOUNT_ID",length=64)
	private String bindAccountId;
	/**
	 * 绑定的外部帐户名
	 */
	@Column(name="BIND_ACCOUNT_NAME",length=64)
	private String bindAccountName;

	/**
	 * 是否在绑定状态（未解绑）
	 */
	@Type(type = "yes_no")
	@Column(name="BINDING")
	private Boolean binding;

	/**
	 * 绑定日期
	 */
	@Column(name="BINDING_DATE",columnDefinition=M.DATE_COLUM)
	private Date bindingDate;

	/**
	 * 帐号类型 1微信
	 */
	@Column(name="ACCOUNT_TYPE",columnDefinition=M.NUM_COLUM)
	private Integer accountType;

	/**
	 * 绑定的用户
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private HslUser user;

	public String getBindAccountName() {
		return bindAccountName;
	}

	public void setBindAccountName(String bindAccountName) {
		this.bindAccountName = bindAccountName;
	}

	public Boolean getBinding() {
		return binding;
	}

	public void setBinding(Boolean binding) {
		this.binding = binding;
	}

	public Date getBindingDate() {
		return bindingDate;
	}

	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public HslUser getUser() {
		return user;
	}

	public void setUser(HslUser user) {
		this.user = user;
	}
	
	public String getBindAccountId() {
		return bindAccountId;
	}

	public void setBindAccountId(String bindAccountId) {
		this.bindAccountId = bindAccountId;
	}

	
}
