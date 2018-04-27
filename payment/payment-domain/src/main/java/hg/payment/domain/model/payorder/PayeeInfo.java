package hg.payment.domain.model.payorder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * 
 *@类功能说明：收款人信息
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月20日上午8:38:50
 *
 */
@Embeddable
public class PayeeInfo {
	
	/**
	 * 收款方的帐号
	 */
	@Column(name="PAYEE_ACCOUNT")
	private String payeeAccount;

	/**
	 * 收款户名
	 */
	@Column(name="PAYEE_NAME")
	private String payeeName;

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	
	

}
