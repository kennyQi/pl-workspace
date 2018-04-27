package hg.system.dto.auth;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：员工基本信息_DTO
 * @类修改者：
 * @修改日期：2014年11月5日上午10:09:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：Administrator
 * @创建时间：2014年11月5日上午10:09:58
 */
@SuppressWarnings("serial")
public class StaffBaseInfoDTO extends EmbeddDTO {

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 用户电子邮箱地址
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 电话号码
	 */
	private String tel;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
