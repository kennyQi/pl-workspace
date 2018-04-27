package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.math.BigDecimal;

import plfx.jd.pojo.system.enumConstants.EnumCreditCardProcessType;
import plfx.jd.pojo.system.enumConstants.EnumCreditCardStatus;

public class CreditCardWithStatusDTO extends CreditCardDTO implements
		Serializable {

	/**
	 * 交易类型
	 */
	protected EnumCreditCardProcessType processType;
	/**
	 * 交易状态
	 */
	protected EnumCreditCardStatus status;
	/**
	 * 金额
	 */
	protected BigDecimal amount;

	public EnumCreditCardProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(EnumCreditCardProcessType processType) {
		this.processType = processType;
	}

	public EnumCreditCardStatus getStatus() {
		return status;
	}

	public void setStatus(EnumCreditCardStatus status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
