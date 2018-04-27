/**
 * @文件名称：AdjustBean.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月2日下午3:04:28
 */
package hgtech.jfaccount;

import java.io.Serializable;

/**
 * @修改日期：2014年11月2日下午3 :04:28
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月2日下午3 :04:28
 */
public class AdjustBean {
	/**
	 * 算出的积分
	 */
	public int jf;
	/**
	 * 用户id
	 */
	public String userCode;
	/**
	 * 应用id
	 */
	public String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 
	 */
	public String accountTypeId;
	/**
	 * 输入的交易流水对象
	 */
	public Serializable tradeFlow;
	/**
	 * 输入的交易流水对象id
	 */
	public String tradeFlowId;
	/**
	 * 交易备注
	 */
	public String remark;
	/**
	 * 交易批次
	 */
	public String batchNo;

	/**
	 * @类名：AdjustBean.java
	 * @描述：TODO
	 * @
	 */
	public AdjustBean() {
	}

	/**
	 * @return the jf
	 */
	public int getJf() {
		return jf;
	}

	/**
	 * @param jf the jf to set
	 */
	public void setJf(int jf) {
		this.jf = jf;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the accountTypeId
	 */
	public String getAccountTypeId() {
		return accountTypeId;
	}

	/**
	 * @param accountTypeId the accountTypeId to set
	 */
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	/**
	 * @return the tradeFlow
	 */
	public Serializable getTradeFlow() {
		return tradeFlow;
	}

	/**
	 * @param tradeFlow the tradeFlow to set
	 */
	public void setTradeFlow(Serializable tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	/**
	 * @return the tradeFlowId
	 */
	public String getTradeFlowId() {
		return tradeFlowId;
	}

	/**
	 * @param tradeFlowId the tradeFlowId to set
	 */
	public void setTradeFlowId(String tradeFlowId) {
		this.tradeFlowId = tradeFlowId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}