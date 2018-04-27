package plfx.yl.ylclient.yl.command;

public class BaseCommand {
	/**
	 * 	合作伙伴用户名	Y	合作伙伴在易行天下的用户名
	 */
	private String userName;
	/**
	 * 签名	Y	所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
