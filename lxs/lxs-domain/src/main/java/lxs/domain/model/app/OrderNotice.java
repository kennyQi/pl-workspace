package lxs.domain.model.app;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 
 * @类功能说明： 订单通知
 * @类修改者：
 * @修改日期：2015-10-26 上午11:11:20
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:11:20
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_APP + "ORDERNOTICE")
public class OrderNotice extends BaseModel {

	/**
	 * 联系人
	 */

	@Column(name = "contactperson", length = 50)
	private String contactPerson;

	/**
	 * 手机号
	 */

	@Column(name = "phononumber")
	private String phonoNumber;

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhonoNumber() {
		return phonoNumber;
	}

	public void setPhonoNumber(String phonoNumber) {
		this.phonoNumber = phonoNumber;
	}

	
}
