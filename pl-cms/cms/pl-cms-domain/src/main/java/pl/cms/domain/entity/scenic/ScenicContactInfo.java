package pl.cms.domain.entity.scenic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：景区联系信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月14日下午3:40:14
 */
@Embeddable
@SuppressWarnings("serial")
public class ScenicContactInfo implements Serializable {

	/**
	 * 联系人
	 */
	@Column(name = "CONTACT_LINK_MAN", length = 64)
	private String linkMan;

	/**
	 * 联系电话
	 */
	@Column(name = "CONTACT_TEL", length = 32)
	private String tel;

	/**
	 * 邮箱
	 */
	@Column(name = "CONTACT_EMAIL", length = 128)
	private String email;

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
