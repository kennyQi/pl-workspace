package hgtech.jfcal.model;

import hgtech.jf.JfChangeApply;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @类功能说明：计算结果。无论成功与否，都会产生计算结果
 * @类修改者：
 * @修改日期：2014-9-22下午1:50:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22下午1:50:06
 *
 */
public class CalResult /*extends DataRow*/ implements JfChangeApply{
	/**
	 * 算出的积分
	 */
	public int out_jf;
	/**
	 * 计算结果代码，为Y/N
	 */
	public String out_resultCode;
	public String flowStatus;
	/**
	 * 计算结果详细说明or  交易备注
	 */
	public String out_resultText;
	/**
	 * 用户id
	 */
	public String in_userCode;
	/**
	 * 应用id
	 * 
	 */
	public String in_appId;
	public String getIn_appId() {
		return in_appId;
	}

	public void setIn_appId(String in_appId) {
		this.in_appId = in_appId;
	}

	/**
	 * 规则
	 */
	public Rule in_rule;
	 
	/**
	 * 计算时间
	 */
	public Date calTime;
	/**
	 * 输入的交易流水对象
	 */
	public Serializable in_tradeFlow;
	/**
	 * 输入的交易流水对象id
	 */
	public String in_tradeFlowId;
	/**
	 * 自己的id
	 */
	public String id;
	/**
	 * 交易备注
	 */
	//public String in_remark;
	/**
	 * 交易批次
	 */
	public String in_batchNo;
	public String merchandise;
	public String merchant;
	public int merchandiseAmount;
	private boolean newUser;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return in_userCode + "  "+out_jf;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getOut_jf()
	 */
	@Override
	public int getjf() {
		return out_jf;
	}

	/**
	 * @param out_jf the out_jf to set
	 */
	public void setOut_jf(int out_jf) {
		this.out_jf = out_jf;
	}

	/**
	 * @return the out_resultCode
	 */
	public String getOut_resultCode() {
		return out_resultCode;
	}

	/**
	 * @param out_resultCode the out_resultCode to set
	 */
	public void setOut_resultCode(String out_resultCode) {
		this.out_resultCode = out_resultCode;
	}

	/**
	 * @return the out_resultText
	 */
	public String getOut_resultText() {
		return out_resultText;
	}

	/**
	 * @param out_resultText the out_resultText to set
	 */
	public void setOut_resultText(String out_resultText) {
		this.out_resultText = out_resultText;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getIn_userCode()
	 */
	@Override
	public String getuserCode() {
		return in_userCode;
	}

	/**
	 * @param in_userCode the in_userCode to set
	 */
	public void setIn_userCode(String in_userCode) {
		this.in_userCode = in_userCode;
	}

	/**
	 * @return the in_rule
	 */
	public Rule getIn_rule() {
		return in_rule;
	}

	/**
	 * @param in_rule the in_rule to set
	 */
	public void setIn_rule(Rule in_rule) {
		this.in_rule = in_rule;
	}

	/**
	 * @return the calTime
	 */
	public Date getCalTime() {
		return calTime;
	}

	/**
	 * @param calTime the calTime to set
	 */
	public void setCalTime(Date calTime) {
		this.calTime = calTime;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getIn_tradeFlow()
	 */
	@Override
	public Serializable gettradeFlow() {
		return in_tradeFlow;
	}

	/**
	 * @param in_tradeFlow the in_tradeFlow to set
	 */
	public void setIn_tradeFlow(Serializable in_tradeFlow) {
		this.in_tradeFlow = in_tradeFlow;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getIn_tradeFlowId()
	 */
	@Override
	public String gettradeFlowId() {
		return in_tradeFlowId;
	}

	/**
	 * @param in_tradeFlowId the in_tradeFlowId to set
	 */
	public void setIn_tradeFlowId(String in_tradeFlowId) {
		this.in_tradeFlowId = in_tradeFlowId;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getIn_remark()
	 */
	@Override
	public String getremark() {
		if(out_resultText!=null && out_resultText.trim().length()>0)
			return out_resultText;
		else
			return in_rule.name;
	}


	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getIn_batchNo()
	 */
	@Override
	public String getbatchNo() {
		return in_batchNo;
	}

	/**
	 * @param in_batchNo the in_batchNo to set
	 */
	public void setIn_batchNo(String in_batchNo) {
		this.in_batchNo = in_batchNo;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeResult#getAccountType()
	 */
	@Override
	public Object getaccountType() {
			return   in_rule.getAccountType();
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#isArriving()
	 */
	@Override
	public boolean isArriving() {
		//  
		return false;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchandise()
	 */
	@Override
	public String getMerchandise() {
		//  
		return merchandise;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchant()
	 */
	@Override
	public String getMerchant() {
		return merchant;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchandiseAmount()
	 */
	@Override
	public int getMerchandiseAmount() {
	    return merchandiseAmount;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getFee()
	 */
	@Override
	public int getFee() {
	    return 0;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getValidDay()
	 */
	@Override
	public Date getValidDay() {
	    return null;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#isMerchandiseArriving()
	 */
	@Override
	public boolean isMerchandiseArriving() {
	    return false;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getNoticeMobile()
	 */
	@Override
	public String getNoticeMobile() {
	    return null;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getAccountTypeForJfRate()
	 */
	@Override
	public Object getAccountTypeForJfRate() {
	    return null;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchandiseStatus()
	 */
	@Override
	public String getMerchandiseStatus() {
	    return null;
	}

/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getSendStatus()
	 */
	@Override
	public String getSendStatus() {
	    return "NOR";
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getFlowStatus() {
		return "NOR";
	}

	@Override
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	@Override
	public String getRule() {
		return in_rule.code;
	}

	@Override
	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	@Override
	public boolean isSavejf0() {
		return savejf0;
	}
	
	/**
	 * 是否保存积分为0 的流水，默认为否
	 */
	public boolean savejf0=false;
	public Date out_invalidDay;

	public Date getOut_invalidDay() {
		return out_invalidDay;
	}

	public void setOut_invalidDay(Date out_invalidDay) {
		this.out_invalidDay = out_invalidDay;
	}

	@Override
	public Date getInValidDate() {
		return out_invalidDay;
	}

	@Override
	public String getAppId() {
		return in_appId;
	}
	 
}
