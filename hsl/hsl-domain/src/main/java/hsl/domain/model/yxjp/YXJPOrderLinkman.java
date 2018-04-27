package hsl.domain.model.yxjp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 易行机票订单联系人
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderLinkman implements Serializable {

	/**
	 * 联系人姓名
	 */
	@Column(name = "LINK_NAME", length = 64)
	private String linkName;

	/**
	 * 联系人手机
	 */
	@Column(name = "LINK_MOBILE", length = 64)
	private String linkMobile;

	/**
	 * 联系人邮箱
	 */
	@Column(name = "LINK_EMAIL", length = 128)
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
