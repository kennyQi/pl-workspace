package hg.dzpw.domain.model.dealer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：经销商客户端信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:10:48
 * @版本：V1.0
 */
@Embeddable
@SuppressWarnings("serial")
public class DealerClientInfo implements Serializable {
	/**
	 * 经销商代码前缀，JX1002
	 */
	public static final String DEALER_KEY_PREFIX="JX";
	/**
	 * 经销商代码
	 */
	@Column(name = "DEALER_KEY", length = 64, unique = true)
	private String key;

	/**
	 * 密钥
	 */
	@Column(name = "SECRET_KEY", length = 128)
	private String secretKey;

	/**
	 * 经销商网址
	 */
	@Column(name = "DEALER_WEBSITE", length = 128)
	private String dealerWebsite;

	/**
	 * 消息推送地址
	 */
	@Column(name = "PUBLISH_URL", length = 384)
	private String publishUrl;

	/**
	 * 是否使用消息推送
	 */
	@Type(type = "yes_no")
	@Column(name = "PUBLISH_ABLE")
	private Boolean publishAble = true;
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getDealerWebsite() {
		return dealerWebsite;
	}

	public void setDealerWebsite(String dealerWebsite) {
		this.dealerWebsite = dealerWebsite;
	}

	public String getPublishUrl() {
		return publishUrl;
	}

	public void setPublishUrl(String publishUrl) {
		this.publishUrl = publishUrl;
	}

	public Boolean getPublishAble() {
		return publishAble;
	}

	public void setPublishAble(Boolean publishAble) {
		if (publishAble == null)
			return;
		this.publishAble = publishAble;
	}

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

}