package hsl.pojo.qo.user;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HslMailCodeQO extends BaseQo {
	/**
	 * 验证码令牌
	 */
	private String token;

	/**
	 * 接收的验证码
	 */
	private String validCode;

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

}
