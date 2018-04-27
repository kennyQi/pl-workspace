package hsl.pojo.qo.user;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HslSMSCodeQO extends BaseQo {
	/**
	 * 验证码令牌
	 */
	private String token;

	/**
	 * 接收的验证码
	 */
	private String validCode;
	/**
	 * 发送时间
	 */
	private String sendDate;
	/**
	 * 发送状态
	 */
	private Integer status;
	/**
	 * 手机号
	 */
	private String mobile;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
