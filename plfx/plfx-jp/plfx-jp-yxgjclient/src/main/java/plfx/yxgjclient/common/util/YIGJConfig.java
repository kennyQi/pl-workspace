package plfx.yxgjclient.common.util;

/**
 * 易行国际接口配置类
 * 测试时直接使用静态类
 * 发布时使用spring注入属性
 * @author guotx
 * 2015-07-04 14:29:29
 * 
 * 2015-07-27 10:34:35 添加signUserName字段
 *
 */
public class YIGJConfig {
	/**
	 * 加密的key值
	 */
	private String key;
	/**
	 * 调用易行用户名
	 */
	private  String userName;
	/**
	 * 指定已签约的易行天下用户名,非必填字段
	 */
	private String signUserName;
	/**
	 * 易行国际接口地址
	 */
	private String interUrl;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSignUserName() {
		return signUserName;
	}

	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}

	public String getInterUrl() {
		return interUrl;
	}

	public void setInterUrl(String interUrl) {
		this.interUrl = interUrl;
	}
}
