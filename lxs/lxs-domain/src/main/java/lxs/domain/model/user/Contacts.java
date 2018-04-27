package lxs.domain.model.user;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lxs.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_USER + "Contacts")
public class Contacts extends BaseModel {
	/**
	 * 属于哪个用户
	 */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private LxsUser user;
	/**
	 * 联系人姓名
	 */
	@Column(name = "CONTACTS_NAME", length = 64)
	private String contactsName;
	/**
	 * 身份证号
	 */
	@Column(name = "IDCARD_NO", length = 18)
	private String iDCardNO;
	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", length = 11)
	private String mobile;
	/**
	 * 游客类型
	 */
	@Column(name = "TYPE", length = 11)
	private String type;
	/*成人 adult;
	儿童 child;*/
	/**
	 * 游客类型
	 */
	@Column(name = "CREATE_DATE", columnDefinition=M.DATE_COLUM)
	private Date createDate;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public LxsUser getUser() {
		return user;
	}

	public void setUser(LxsUser user) {
		this.user = user;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getiDCardNO() {
		return iDCardNO;
	}

	public void setiDCardNO(String iDCardNO) {
		this.iDCardNO = iDCardNO;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
