package hsl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslUserBindAccountQO  extends BaseQo{

	/**
	 * 绑定的外部账户ID
	 */
	private String bindAccountId;
	
	/**
	 * 绑定外部帐号名
	 */
	private String bindAccountName;

	/**
	 * 是否在绑定状态（未解绑）
	 */
	private Boolean binding;

	/**
	 * 绑定日期
	 */
	private Boolean bindingDate;

	/**
	 * 帐号类型 1微信
	 */
	private Integer accountType;

	/**
	 * 绑定的用户
	 */
	private HslUserQO userQO;


	public Boolean getBinding() {
		return binding;
	}

	public void setBinding(Boolean binding) {
		this.binding = binding;
	}

	public Boolean getBindingDate() {
		return bindingDate;
	}

	public void setBindingDate(Boolean bindingDate) {
		this.bindingDate = bindingDate;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public HslUserQO getUserQO() {
		return userQO;
	}

	public void setUserQO(HslUserQO userQO) {
		this.userQO = userQO;
	}

	public String getBindAccountId() {
		return bindAccountId;
	}

	public void setBindAccountId(String bindAccountId) {
		this.bindAccountId = bindAccountId;
	}

	public String getBindAccountName() {
		return bindAccountName;
	}

	public void setBindAccountName(String bindAccountName) {
		this.bindAccountName = bindAccountName;
	}


}
