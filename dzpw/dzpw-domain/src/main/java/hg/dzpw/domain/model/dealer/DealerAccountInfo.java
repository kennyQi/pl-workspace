package hg.dzpw.domain.model.dealer;

import hg.dzpw.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：经销商结算信息
 * @类修改者：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-5-4下午3:54:38
 * @版本：V1.0
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class DealerAccountInfo {
	
	/**
	 * 账户类型  1-汇金宝支付平台账户   2-支付宝账户
	 */
	@Column(name = "ACCOUNT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer accountType;
	
	/**
	 * 结算账户
	 */
	@Column(name = "ACCOUNT_NUMBER", length = 64)
	private String accountNumber;
	
	/**
	 * 商户ID
	 */
	@Column(name="BUSINESS_ID", length = 64)
	private String businessId;
	
	/**
	 * 应用Key
	 */
	@Column(name="BUSINESS_KEY", length = 128)
	private String businessKey;
	
	/**
	 * 手续费 元/张
	 */
	@Column(name = "SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double settlementFee = 0d;
	
	
	/**
	 * 预付款
	 */
	@Column(name = "ADVANCE_PAYMENT", columnDefinition = M.MONEY_COLUM)
	private Double advancePayment;
	
	/**
	 * 警戒阀值
	 */
	@Column(name = "WARN_BALANCE", columnDefinition = M.MONEY_COLUM)
	private Double warnBalance;
	
	/**
	 * 汇金宝平台的操作员编号
	 * */
	@Column(name = "OPERATOR_NO", length = 64)
	private String operatorNo;
	
	/**
	 * 汇金宝平台的用户编号
	 * */
	@Column(name = "CST_NO", length = 64)
	private String cstNo;

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public Double getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(Double advancePayment) {
		this.advancePayment = advancePayment;
	}

	public Double getWarnBalance() {
		return warnBalance;
	}

	public void setWarnBalance(Double warnBalance) {
		this.warnBalance = warnBalance;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	

}
