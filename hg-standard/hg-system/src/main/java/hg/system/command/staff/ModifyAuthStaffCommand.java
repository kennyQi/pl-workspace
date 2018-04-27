package hg.system.command.staff;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：编辑角色_command
 * @类修改者：zzb
 * @修改日期：2014年11月4日下午2:16:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月4日下午2:16:46
 */
@SuppressWarnings("serial")
public class ModifyAuthStaffCommand extends BaseCommand {

	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 角色ids
	 */
	private String roleIds;

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

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

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
