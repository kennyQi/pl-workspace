package hg.dzpw.domain.model.scenicspot;

import hg.dzpw.domain.model.M;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @类功能说明：景区结算信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-10上午9:53:16
 * @版本：
 */
@Embeddable
public class ScenicSpotSettleInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 手续费 元/人次
	 */
	@Column(name = "SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double settlementFee = 0d;

	/**
	 * 开户行
	 */
	@Column(name = "DEPOSIT_BANK", length = 32)
	private String depositBank;

	/**
	 * 开户单位
	 */
	@Column(name = "DEPOSIT_ORG", length = 64)
	private String depositOrg;

	/**
	 * 账户号
	 */
	@Column(name = "DEPOSIT_ACCOUNT", length = 32)
	private String depositAccount;
	
//---------------一下三个暂不使用-----------------
	/**
	 * 汇金宝平台返回的企业用户编号
	 */
	@Column(name = "CST_NO", length = 64)
	private String cstNo;
	
	/**
	 * 汇金宝平台返回的操作员编号
	 */
	@Column(name = "OPERATOR_NO", length = 64)
	private String operatorNo;
	
	/**
	 * 账户类型  1-汇金宝支付平台账户   2-支付宝账户
	 */
	@Column(name = "ACCOUNT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer accountType;
	

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getDepositOrg() {
		return depositOrg;
	}

	public void setDepositOrg(String depositOrg) {
		this.depositOrg = depositOrg;
	}

	public String getDepositAccount() {
		return depositAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	

}
