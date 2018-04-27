package plfx.yxgjclient.pojo.param;

public class BaseParam {
	/**
	 * 合作伙伴用户名
	 */
	private String userName;
	/**
	 * 指定签约的易行用户名
	 */
	private String signUserName;
	/**
	 * 接口名
	 */
	private String serviceName;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
