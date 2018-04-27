package hsl.pojo.command;
import hg.common.component.BaseCommand;
@SuppressWarnings("serial")
public class CheckRegisterMailCommand extends BaseCommand {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 校验验证码的令牌，来自发送验证码接口返回
	 */
	private String validToken;
	/**
	 * 验证码
	 */
	private String identify;
	
	public String getValidToken() {
		return validToken;
	}
	public void setValidToken(String validToken) {
		this.validToken = validToken;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
