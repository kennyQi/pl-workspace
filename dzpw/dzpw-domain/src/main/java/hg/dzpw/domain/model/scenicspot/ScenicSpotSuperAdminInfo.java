package hg.dzpw.domain.model.scenicspot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明: 景区超级管理员帐号信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:07:19
 * @版本：V1.0
 */
@Embeddable
public class ScenicSpotSuperAdminInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录帐号
	 */
	@Column(name = "ADMIN_LOGINNAME", length = 64)
	private String adminLoginName;

	/**
	 * 登录密码
	 */
	@Column(name = "ADMIN_PASSWORD", length = 64)
	private String adminPassword;

	/**
	 * 景区密钥
	 */
	@Column(name = "SECRET_KEY", length = 128)
	private String secretKey;

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName == null ? null : adminLoginName
				.trim();
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword == null ? null : adminPassword
				.trim();
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}