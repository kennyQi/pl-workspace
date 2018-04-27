package hg.payment.domain.model.payorder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @类功能说明：支付人信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月22日下午3:38:48
 * 
 */
@Embeddable
public class PayerInfo {

	/**
	 * 客户端用户id
	 */
	@Column(name="PAYER_CLIENT_USER_ID")
	private String payerClientUserId;

	/**
	 * 用户真实姓名
	 */
	@Column(name="NAME")
	private String name;

	/**
	 * 用户身份证号
	 */
	@Column(name="ID_CARD_NO")
	private String idCardNo;

	/**
	 * 用户手机号
	 */
	@Column(name="MOBILE")
	private String mobile;
	
	/**
	 * 支付帐号
	 */
	@Column(name="PAYER_ACCOUNT")
	private String payerAccount;
	

	public String getPayerClientUserId() {
		return payerClientUserId;
	}

	public void setPayerClientUserId(String payerClientUserId) {
		this.payerClientUserId = payerClientUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	
	

}
