package hg.common.util;

public class OpenSms {
	/**
	 * 接收号码
	 */
	private String mobile;
	
	/**
	 * 发送文本   长度不超过400个字符
	 */
	private String content;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
