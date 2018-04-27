package hsl.pojo.command;
import hg.common.component.BaseCommand;
/**
 * 
 * @类功能说明：用户注册模块使用的Comd
 * @类修改者：
 * @修改日期：2014年9月19日上午9:28:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月19日上午9:28:08
 *
 */
public class UserRegisterCommand extends BaseCommand {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 校验验证码的令牌，来自发送验证码接口返回
	 */
	private String validToken;
	/**
	 * 短信验证码
	 */
	private String validCode;
	
	/**
	 * 类型 1为个人 2为企业
	 */
	private Integer type;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 公司名称或个人真实姓名
	 */
	private String name;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getValidToken() {
		return validToken;
	}
	public void setValidToken(String validToken) {
		this.validToken = validToken;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}