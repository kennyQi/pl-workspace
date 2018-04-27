/**
 * @文件名称：Jfjava
 * @类路径：hgtech.jfaccount
 * @描述：
 * @作者：xinglj
 * @时间：2014-9-24上午10:07:54
 */
package hgtech.jfaccount;

import hg.system.model.M;
import hgtech.jf.JfChangeApply;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;
import hgtech.jfaccount.service.AccountService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @类功能说明：积分流水。积分账户变动的所有流水
 * @类修改者：
 * @修改日期：2014-9-24上午10:07:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-24上午10:07:54
 * 
 */
@javax.persistence.Entity
@Table(name="JF_FLOW")
public class JfFlow implements Entity<StringUK>, JfChangeApply{
	/**
	 * 对方未应答
	 */
	public static final String STATUS_NOREPLY = "NOREPLY";
	/**
	 * 未应答已处理
	 */
	public static final String STATUS_NOREPLY_END = "NOREPLYEND";
	
	/**
	 *  
	 */
	public static final String NOR = "NOR";
	/**
	 *  
	 */
	public static final String ADJED = "ADJED";
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getParentOrgCode() {
		return parentOrgCode;
	}
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAwardlStatus() {
		return awardlStatus;
	}
	public void setAwardlStatus(String resultStatus) {
		this.awardlStatus = resultStatus;
	}

	/**
	 *  
	 */
	public static final String UNDO = "UNDO";

	public static final String ARRVING = "TODO";
	
	/**
	 * 在途积分已经生效。生效后插入新流水
	 */
	public static final String ARRIVED="ARRIVED";
	/**
	 *  
	 */
	public static final String EXP = "EXP";

	/**
	 * 已发货，对方未收获。请注意和 在途区别
	 */
	public static final String SEND ="SEND";
	public static final String TOSEND ="TOSEND";

	@Transient
	/**
	 * 在这个时刻到了之后再进行活动积分计算,防止和业务操作（如转入）发生账户表写冲突
	 */
	public long toCalTimeAt=-1;
	
	/**
	 * 积分状态
	 */
	public static Map statusMap=new HashMap();
	/**
	 * 对方货物或积分状态
	 */
	public static Map byStatusMap=new HashMap();
	/**
	 * 货物或积分的发送状态
	 */
	public static Map sendStatusMap=new HashMap();
	static{
		statusMap.put(EXP, "过期");
		statusMap.put(UNDO, "撤销");
		statusMap.put(NOR, "正常");
		statusMap.put(ADJED, "明细调整");
		statusMap.put(ARRVING, "在途");
		statusMap.put(ARRIVED, "已生效");
		statusMap.put(STATUS_NOREPLY, "处理中");
		statusMap.put(STATUS_NOREPLY_END, "交易失败");
		
		byStatusMap.put(UNDO,"处理失败");
		byStatusMap.put(NOR, "处理成功");
		byStatusMap.put(SEND, "处理中");
		byStatusMap.put(STATUS_NOREPLY, "处理中");
		
		sendStatusMap.put(TOSEND,"待发送");
		sendStatusMap.put(SEND,"已发送");
		sendStatusMap.put(NOR,"完成");
	}

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	public StringUK flowId;

	@ManyToOne
	@JoinColumn(name = "acct_id")
	public JfAccount account;

	@ManyToOne
	@JoinColumn(name = "acctDebit_id")
	public JfAccount accountDebit;
	
	
	/**
	 * 所属单位名称
	 */
	@Transient
	public String organName;
	
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}

	/**
	 * 积分用户名
	 */
	@Column(name = "user")
	public String user;

	@Column(name = "trade_amount")
	public int jf;
	/**
	 * 交易手续费
	 */
	@Column(name = "trade_fee", columnDefinition = "int(11) NOT NULL DEFAULT 0")
	public int fee = 0;

	@Column(name = "use_amount")
	public int usejf;
	
	@Column(name="trade_type")
	public JfTradeType tradeType;

	/**
	 * 原交易码
	 */
	@Column(name="old_trade_type")
	public JfTradeType oldTradeType;
	
	/**
	 * 当时账户余额
	 */
	@Column(name = "acct_jf")
	public Long acctJf;

	public String getDb_id() {
		return flowId.getS();
	}

	public void setDb_id(String db_id) {
		this.flowId = new StringUK(db_id);
	}

	@Column(name = "flow_time")
	public Date insertTime;

	@Column(name = "flow_date", columnDefinition = M.DATE_COLUM)
	Date insertDate;

	public Date getInsertDate() {
		return insertDate;
	}

	@Column(name = "update_time")
	public Date updateTime;
	@Column(name = "send_time")
	public Date sendTime;
	@Column(name = "hand_time")
	public Date handtime;
	/**
	 * 积分生效日
	 */
	@Column(name = "valid_date")
	public Date validDate;

	/**
	 * 积分失效效日
	 */
	@Column(name="invalid_date")
	private Date invalidDate;
	
	
	public Date getInvalidDate() {
		return invalidDate;
	}
	public String getInvalidDate2() {
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
		if(invalidDate==null||"3000-01-01".equals(sft.format(invalidDate))){
			return "永久";
		}
		sft = new SimpleDateFormat("yyyy.MM.dd");
		return sft.format(invalidDate);
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}



	@Column(name="batch_no")
	public String batchNo;

	@Column(name = "ref_flow_id")
	public String refFlowId;

	@Column(name = "trade_remark")
	public String detail;

	@Column(name = "status")
	public String status;

	/**
	 * 交易所在商户
	 */
	@Column(name = "buy_merchant")
	public String merchant;
	/**
	 * 交易的产品
	 */
	@Column(name = "buy_merchandise")
	public String merchandise;

	/**
	 * 交易的产品数量
	 */
	@Column(name = "buy_amount", columnDefinition = "int(11) NOT NULL DEFAULT 0")
	public int merchandiseAmount = 0;

	/**
	 * 货物送达状态短信通知的手机号
	 */
	@Column(name = "notice_mobile")
	public String noticeMobile;

	/**
	 * 货物送达状态 SEND->NOR|UNDO
	 */
	@Column(name = "buy_status")
	public String merchandiseStatus;
	/**
	 * 送达状态 TOSEND->SEND->NOR
	 */
	@Column(name = "send_status")
	public String sendStatus;
	/**
	 * 当时的费率
	 */
	@Column(name = "rate")
	public String rate;
	/**
	 * 商品对应的对象
	 */
	@Transient
	private Object merchandiseObj;
	
	@Column(name="flow_industrycode")
	private  String industryCode;//行业代码
	
	@Column(name="flow_industryname")
	private String industryName;//行业名称
	
	@Column(name="parentorg_code")
	private String parentOrgCode;//上级单位代码
	
	@Column(name="org_code")
	private String orgCode;//单位代码
	
	@Column(name="award_status")
	private String awardlStatus;//奖励积分完成状态
	
	@Column(name="rule")
	private String rule;
	
	@Transient
	private Object ruleObj;
	
	public Object getRuleObj() {
		return ruleObj;
	}
	public void setRuleObj(Object ruleObj) {
		this.ruleObj = ruleObj;
	}

	/**
	 * 是否可用来消费的流水
	 * 有期限积分：1:还可再使用，0：已将积分用完,
	 * 无期限积分：null
	 */
	@Column(name="can_consume")
	private Integer canConsume;
	/**
	 * 是否使用can_consume字段模式
	 */
	public static boolean canConsumeMode=true;
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#getUK()
	 */
	public StringUK readUK() {
		return flowId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hgtech.jf.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
		flowId = uk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return /*getUK().getkey() +":"+*/account.acct_type+" "+ tradeType+jf + " "+detail +" "+batchNo;
	}

	/**
	 * @return the flowId
	 */
	public StringUK getFlowId() {
		return flowId;
	}

	/**
	 * @param flowId
	 *            the flowId to set
	 */
	public void setFlowId(StringUK flowId) {
		this.flowId = flowId;
	}

	/**
	 * @return the account
	 */
	public JfAccount getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(JfAccount account) {
		this.account = account;
	}

	/**
	 * @return the jf
	 */
	public int getJf() {
		return jf;
	}

	/**
	 * @param jf
	 *            the jf to set
	 */
	public void setJf(int jf) {
		this.jf = jf;
	}

	/**
	 * @return the tradeType
	 */
	public JfTradeType getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType
	 *            the tradeType to set
	 */
	public void setTradeType(JfTradeType tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	
	public String getInsertTime2() {
		SimpleDateFormat sft = new SimpleDateFormat("yyyy.MM.dd");
		return sft.format(insertTime);
	}

	/**
	 * @param insertTime
	 *            the insertTime to set
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
		this.insertDate = insertTime;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 *            the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the refFlowId
	 */
	public String getRefFlowId() {
		return refFlowId;
	}

	/**
	 * @param refFlowId
	 *            the refFlowId to set
	 */
	public void setRefFlowId(String refFlowId) {
		this.refFlowId = refFlowId;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the validDate
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate
	 *            the validDate to set
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {

	}

	/**
	 * @return the usejf
	 */
	public int getUsejf() {
		return usejf;
	}

	/**
	 * @param usejf
	 *            the usejf to set
	 */
	public void setUsejf(int usejf) {
		this.usejf = usejf;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusMap.get(status).toString();
	}

	public String getMerchantdiseStatusText() {
		if (merchandiseStatus != null) {
			return byStatusMap.get(merchandiseStatus).toString();
		}
		return (String) byStatusMap.get(NOR);
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
		if(UNDO.equals(status)){
			sendStatus=status;
			merchandiseStatus=status;
		}
	}

	/**
	 * @return the merchant
	 */
	public String getMerchant() {
		return merchant;
	}

	/**
	 * @param merchant
	 *            the merchant to set
	 */
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	/**
	 * @return the merchandise
	 */
	public String getMerchandise() {
		return merchandise;
	}

	/**
	 * @param merchandise
	 *            the merchandise to set
	 */
	public void setMerchandise(String merchandise) {
		this.merchandise = merchandise;
	}

	/**
	 * @return the merchandiseObj
	 */
	public Object getMerchandiseObj() {
		return merchandiseObj;
	}

	/**
	 * @param merchandiseObj
	 *            the merchandiseObj to set
	 */
	public void setMerchandiseObj(Object merchandiseObj) {
		this.merchandiseObj = merchandiseObj;
	}

	/**
	 * @return the fee
	 */
	public int getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}

	/**
	 * @return the merchandiseAmount
	 */
	public int getMerchandiseAmount() {
		return merchandiseAmount;
	}

	/**
	 * @param merchandiseAmount
	 *            the merchandiseAmount to set
	 */
	public void setMerchandiseAmount(int merchandiseAmount) {
		this.merchandiseAmount = merchandiseAmount;
	}

	/**
	 * @return the acctJf
	 */
	public Long getAcctJf() {
		return acctJf;
	}

	/**
	 * @param acctJf
	 *            the acctJf to set
	 */
	public void setAcctJf(Long acctJf) {
		this.acctJf = acctJf;
	}

	/**
	 * @return the noticeMobile
	 */
	public String getNoticeMobile() {
		return noticeMobile;
	}

	/**
	 * @param noticeMobile
	 *            the noticeMobile to set
	 */
	public void setNoticeMobile(String noticeMobile) {
		this.noticeMobile = noticeMobile;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getHandtime() {
		return handtime;
	}

	public void setHandtime(Date handtime) {
		this.handtime = handtime;
	}

	/**
	 * @return the merchandiseStatus
	 */
	public String getMerchandiseStatus() {
		return merchandiseStatus;
	}

	/**
	 * @param merchandiseStatus
	 *            the merchandiseStatus to set
	 */
	public void setMerchandiseStatus(String merchandiseStatus) {
		this.merchandiseStatus = merchandiseStatus;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public String getSendStatusText(){
		if(sendStatus!=null){
	    return sendStatusMap.get(sendStatus).toString();
		}
		return (String) sendStatusMap.get(NOR);
	}



	public Integer getStatusCheck() {
		return statusCheck;
	}

	public void setStatusCheck(Integer statusCheck) {
		this.statusCheck = statusCheck;
	}

	/**
	 * 审核状态
	 */
	@Column(name = "STATUS_CHECK", columnDefinition = M.NUM_COLUM)
	private Integer statusCheck = STATUS_NO_CHECK;

	// 审核通过
	public static final int STATUS_CHECK_PASS = 300;

	// 审核不通过
	public static final int STATUS_CHECK_NO_PASS = 301;

	// 未审核
	public static final int STATUS_NO_CHECK = 302;

	public String canRedo(){
		String string =JfTradeType.in.equals(tradeType) && STATUS_NOREPLY.equals(status)?"1":"0";
		return string;
	}
	public String canUndo(){
		//转出可撤销
		String string = JfTradeType.out.equals(tradeType) && NOR.equals(status) && merchandise.contains("__") ?"1":"0";
		return string;
	}
	
	// implements JfChangeApply
	@Override
	public int getjf() {
		return jf;
	}
	@Override
	public boolean isArriving() {
		return validDate!=null;
	}
	@Override
	public Date getValidDay() {
		return validDate;
	}
	@Override
	public String getuserCode() {
		return user;
	}
	@Override
	public Serializable gettradeFlow() {
		return null;
	}
	@Override
	public String getId() {
		return flowId.getS();
	}
	@Override
	public String gettradeFlowId() {
		return refFlowId;
	}
	@Override
	public String getremark() {
		return detail;
	}
	@Override
	public String getbatchNo() {
		return batchNo;
	}
	@Override
	public Object getaccountType() {
		account.syncUK();
		return account.uk.type;
	}
	@Override
	public Object getAccountTypeForJfRate() {
		try {
			return account.uk.type;
		} catch (Exception e) {
			return null;
		}
		//return account.uk.type;
	}
	@Override
	public boolean isMerchandiseArriving() {
		return !NOR.equals(sendStatus);
	}
	@Override
	public String getFlowStatus() {
		return status;
	}
	@Override
	public void setFlowStatus(String flowStatus) {
		status=flowStatus;
	}
	public JfTradeType getOldTradeType() {
		return oldTradeType;
	}
	public void setOldTradeType(JfTradeType oldTradeType) {
		this.oldTradeType = oldTradeType;
	}
	
	/**
	 * 
	 * @param status
	 * @return 是否变动账务的状态
	 */
	public static boolean isStatusFluenceAccount(String status){
		return ! (JfFlow.ARRVING.equals(status) || JfFlow.ARRIVED.equals(status) || JfFlow.STATUS_NOREPLY.equals(status) ||  JfFlow.STATUS_NOREPLY_END.equals(status));
	}
	@Override
	public boolean isNewUser() {
		return false;
	}
	public Integer getCanConsume() {
		return canConsume;
	}
	
	/**
	 * 重新设置字段can_consume
	 */
	public void resetCanConsume() {
		if(jf>0 && invalidDate !=null && invalidDate.getTime()!=AccountService.DT_YEAR3000.getTime()
				&& ( status ==null || status.equals("NOR")   ))
		canConsume= (  jf- usejf>0  ?1:0 );
	}
	public void setCannotConsume()
	{
		if(invalidDate !=null && invalidDate.getTime()!=AccountService.DT_YEAR3000.getTime())
			canConsume=0;
	}
	
	/**
	 * 获取更新can——consume字段的sql
	 * @return
	 */
	public String getUpdateCanConsumeSql(){
		return "can_consume=if( trade_amount-use_amount>0,1,0)";
	}
	@Override
	public boolean isSavejf0() {
		return false;
	}
	
	@Override
	public Date getInValidDate() {
		return invalidDate;
	}
	@Override
	public String getAppId() {
		return null;
	}
	public long getToCalTimeAt() {
		return toCalTimeAt;
	}
	public void setToCalTimeAt(long toCalTimeAt) {
		this.toCalTimeAt = toCalTimeAt;
	}
}
