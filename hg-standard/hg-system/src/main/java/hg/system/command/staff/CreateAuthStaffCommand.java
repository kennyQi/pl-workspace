package hg.system.command.staff;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建员工_command
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:25:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:25:50
 */
@SuppressWarnings("serial")
public class CreateAuthStaffCommand extends BaseCommand {

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 角色ids
	 */
	private String roleIds;

	/**
	 * 登陆名
	 */
	private String loginName;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * email
	 */
	private String email;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
