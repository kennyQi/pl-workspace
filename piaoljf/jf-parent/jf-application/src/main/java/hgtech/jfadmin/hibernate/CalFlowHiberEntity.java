package hgtech.jfadmin.hibernate;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：计算结果。无论成功与否，都会产生计算结果<br>
 * 从CalResult类复制来的。CalResult没有hibernate的注解
 * @类修改者：
 * @修改日期：2014-9-22下午1:50:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22下午1:50:06
 *
 */
@Entity
@Table(name= "CAL_FLOW")
public class CalFlowHiberEntity{
	@Id
	@Column(name="cal_id")
	public String calId;
	/**
	 * 算出的积分
	 */
	@Column(name="jf")
	public int out_jf;
	/**
	 * 计算结果代码，为Y/N
	 */
	@Column(name="result_code")
	public String out_resultCode;
	/**
	 * 计算结果详细说明
	 */
	@Column(name="result_text")
	public String out_resultText;

	/**
	 * 用户id
	 */
	@Column(name="user")
	public String in_userCode;
	/**
	 * 规则
	 */
	@Column(name="rule")
	public String in_rulecode;
	@Transient
	/**
	 * 不保存
	 */
	public Rule in_rule;
	
	/**
	 * 计算时间
	 */
	@Column(name="cal_time")
	public Date calTime;
	/**
	 * 输入的交易流水对象
	 */
	@Column(name="trade_flow")
	private String in_tradeFlowJson;
	
	@Transient
	PiaolTrade trade;
	
	public PiaolTrade getTrade() {
		return trade;
	}

	/**
	 * 是否是计算，1-是，0-否
	 */
	@Column(name="is_cal")
	public int isCal;
	/**
	 * 是计算操作
	 */
	public static int CONS_IS_CAL=0; 
	/**
	 * 账户变化的操作
	 */
	public static int CONS_IS_CAL_ACCOUNT=1; 
		
	/**
	 * 输入的交易流水对象id,不保存
	 */
	@Column(name="trade_flow_id")
	public String in_tradeFlowId;
	
	//以下为 收货人信息
	@Column(length=11)
	public String receiverMobile;
	@Column(length=32)
	public String receiverName;
	@Column(length=300)
	public String receiverAddress;
	
	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * 交易备注,不保存
	 */
	@Transient
	public String in_remark;
	/**
	 * 交易批次,不保存
	 */
	@Transient
	public String in_batchNo;
	
	/**
	 * 不保存
	 */
	@Transient
	public String flowText;
 
	/**
	 * 交易流水详细展示,不保存
	 */
	@Transient
	public String detail_resultText;
	@Transient
	public CalResult calResult;

	
	public String getDetail_resultText() {
		return detail_resultText;
	}

	public void setDetail_resultText(String detail_resultText) {
		this.detail_resultText = detail_resultText;
	}
	
	public String getFlowText() {
		return flowText;
	}

	public void setFlowText(String flowText) {
		this.flowText = flowText;
	}

	@Override
	public String toString() {
		return in_userCode + " "+out_jf;
	}

	/**
	 * @return the out_jf
	 */
	public int getOut_jf() {
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

	/**
	 * @return the in_userCode
	 */
	public String getIn_userCode() {
		return in_userCode;
	}
	public String getIn_userCode2() {
		if(in_userCode!=null&&in_userCode.length()==11){
			return in_userCode.substring(0,3)+"****"+in_userCode.substring(7,11);
		};
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


	public String getIn_tradeFlowJson() {
		return in_tradeFlowJson;
	}

	public void setIn_tradeFlowJson(String in_tradeFlowJson) {
		if(in_tradeFlowJson.length()>512){
			System.err.println("too long:"+in_tradeFlowJson);
			in_tradeFlowJson = in_tradeFlowJson.substring(0,512);
		}
			
		this.in_tradeFlowJson = in_tradeFlowJson;
		

	}
	public void parseTradeJson(){
		//parse to obj
		if(in_tradeFlowJson.endsWith("}")){
			try {
				trade = JSON.parseObject(in_tradeFlowJson, PiaolTrade.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
	}

	public String getIn_rulecode() {
		return in_rulecode;
	}

	public void setIn_rulecode(String in_rulecode) {
		this.in_rulecode = in_rulecode;
	}

	/**
	 * @return the in_tradeFlowId
	 */
	public String getIn_tradeFlowId() {
		return in_tradeFlowId;
	}

	/**
	 * @param in_tradeFlowId the in_tradeFlowId to set
	 */
	public void setIn_tradeFlowId(String in_tradeFlowId) {
		this.in_tradeFlowId = in_tradeFlowId;
	}

	/**
	 * @return the in_remark
	 */
	public String getIn_remark() {
		return in_remark;
	}

	/**
	 * @param in_remark the in_remark to set
	 */
	public void setIn_remark(String in_remark) {
		this.in_remark = in_remark;
	}

	/**
	 * @return the in_batchNo
	 */
	public String getIn_batchNo() {
		return in_batchNo;
	}

	/**
	 * @param in_batchNo the in_batchNo to set
	 */
	public void setIn_batchNo(String in_batchNo) {
		this.in_batchNo = in_batchNo;
	}

	/**
	 * @return the isCal
	 */
	public int getIsCal() {
	    return isCal;
	}

	/**
	 * @param isCal the isCal to set
	 */
	public void setIsCal(int isCal) {
	    this.isCal = isCal;
	}
}
