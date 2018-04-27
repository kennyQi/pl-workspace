package hsl.domain.model.jp;
import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class JPOrderLinkInfo {

	/**
	 * 联系人姓名
	 */
	@Column(name = "LINK_NAME", length = 512)
	private String linkName;

	/**
	 * 联系人电话
	 */
	@Column(name = "LINK_TELEPHONE", length = 512)
	private String linkTelephone;

	/**
	 * 联系人邮箱
	 */
	@Column(name = "LINK_EMAIL", length = 512)
	private String linkEmail;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkTelephone() {
		return linkTelephone;
	}

	public void setLinkTelephone(String linkTelephone) {
		this.linkTelephone = linkTelephone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	
}
