package hsl.pojo.command;
/**
 * 
 * @类功能说明：校验手机验证码command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年1月28日上午9:55:11
 * @版本：V1.0
 *
 */
public class CheckValidCodeCommand {
	/**
	 * 验证码
	 */
	private String validCode;
	/**
	 * 校验验证码的令牌，来自发送验证码接口返回
	 */
	private String validToken;
	
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public String getValidToken() {
		return validToken;
	}
	public void setValidToken(String validToken) {
		this.validToken = validToken;
	}
	
}
