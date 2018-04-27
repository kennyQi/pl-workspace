package lxs.pojo.dto.user.user;

import java.util.Date;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class UserBindAccountDTO extends BaseDTO {

	/**
	 * 绑定的外部帐户名
	 */
	private String bindAccountName;

	/**
	 * 绑定日期
	 */
	private Date bindingDate;

	/**
	 * 帐号类型 1微信
	 */
	private Integer accountType;

	/**
	 * 绑定的用户
	 */
	private UserDTO user;

	public String getBindAccountName() {
		return bindAccountName;
	}

	public void setBindAccountName(String bindAccountName) {
		this.bindAccountName = bindAccountName;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
