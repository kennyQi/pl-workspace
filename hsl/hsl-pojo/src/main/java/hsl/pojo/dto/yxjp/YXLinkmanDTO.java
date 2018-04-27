package hsl.pojo.dto.yxjp;

import java.io.Serializable;

/**
 * 联系人DTO
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class YXLinkmanDTO implements Serializable {

	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人手机
	 */
	private String linkMobile;

	/**
	 * 联系人邮箱
	 */
	private String linkEmail;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}
}
