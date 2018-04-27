package hg.system.dto.auth;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：用户_DTO
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:07:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:07:48
 */
@SuppressWarnings("serial")
public class AuthUserDTO extends EmbeddDTO {
	
	/**
	 * 登陆名
	 */
	private String loginName;
	
	/**
	 * 密码
	 */
	private String passwd;
	
	/**
	 * 显示名
	 */
	private String displayName;
	
	/**
	 * 是否可用
	 */
	private Short enable;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}
	
}
