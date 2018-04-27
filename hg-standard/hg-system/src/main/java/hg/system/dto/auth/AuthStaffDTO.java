package hg.system.dto.auth;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：员工_DTO
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:02:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:02:50
 */
@SuppressWarnings("serial")
public class AuthStaffDTO extends EmbeddDTO {

	/**
	 * 员工id
	 */
	private String id;
	
	private AuthUserDTO authUser;

	/**
	 * 员工基本信息
	 */
	private StaffBaseInfoDTO info;

	/**
	 * 登录错误次数
	 */
	private Integer pwdErrorNum;

	/**
	 * 最后登录错误时间（毫秒数）
	 */
	private Long lastErrorTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public AuthUserDTO getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUserDTO authUser) {
		this.authUser = authUser;
	}

	public StaffBaseInfoDTO getInfo() {
		return info;
	}

	public void setInfo(StaffBaseInfoDTO info) {
		this.info = info;
	}

	public Integer getPwdErrorNum() {
		return pwdErrorNum;
	}

	public void setPwdErrorNum(Integer pwdErrorNum) {
		this.pwdErrorNum = pwdErrorNum;
	}

	public Long getLastErrorTime() {
		return lastErrorTime;
	}

	public void setLastErrorTime(Long lastErrorTime) {
		this.lastErrorTime = lastErrorTime;
	}

}