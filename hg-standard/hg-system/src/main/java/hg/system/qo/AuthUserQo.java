package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

/**
 * @类功能说明：查询用户_Qo
 * @类修改者：zzb
 * @修改日期：2014年11月5日下午2:49:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日下午2:49:20
 */
@SuppressWarnings("serial")
public class AuthUserQo extends DwzBasePaginQo {

	/**
	 * 登陆名
	 */
	private String loginName;
	private Boolean loginNameLike;

	/**
	 * 显示名
	 */
	private String displayName;
	private Boolean displayNameLike;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getLoginNameLike() {
		return loginNameLike;
	}

	public void setLoginNameLike(Boolean loginNameLike) {
		this.loginNameLike = loginNameLike;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getDisplayNameLike() {
		return displayNameLike;
	}

	public void setDisplayNameLike(Boolean displayNameLike) {
		this.displayNameLike = displayNameLike;
	}

}
