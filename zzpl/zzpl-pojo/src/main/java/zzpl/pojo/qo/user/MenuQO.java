package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class MenuQO extends BaseQo{

	/**
	 * 权限
	 * 1：平台级
	 * 2：企业级
	 */
	private Integer authority;

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	
}
