package hsl.admin.common;

import org.springframework.stereotype.Component;

/**
 *  基金公司信息配置
 * @author hg
 *
 */
@Component
public class FundCompanyConfig {
	
	/**
	 * 收款方账号
	 */
	private String payeeAccountNumber;
	
	/**
	 * 收款人名称
	 */
	private String payeeName;
	
	/**
	 * 开户行
	 */
	private String openBankName;

	public String getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(String payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
}
