package hg.fx.spi.qo;

import java.util.Date;

import hg.framework.common.base.BaseSPIQO;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class ReserveRecordSQO extends BaseSPIQO {

	/**
	 * 里程余额变更记录ID
	 */
	private String reserveRecordID;

	/**
	 * 商户ID
	 */
	private String distributorID;

	/**
	 * 审核状态 -1--已拒绝  1--待审核  0--已通过 type=3 此字段有值
	 */
	private Integer checkStatus;
	
	/**
	 * 按状态排序 
	 * 排序:>0升序，<0降序，=0不排序。（绝对值越大的排的越前）
	 */
	private Integer orderByStatus;
	/**
	 * 按申请时间排序
	 * 排序:>0升序，<0降序，=0不排序。（绝对值越大的排的越前）
	 */
	private Integer orderByApplyDate;
	/**
	 * 交易类型
	 * 1--交易  2--交易退款  3--后台备付金充值  4--在线备付金充值  5--月末返利
	 */
	private Integer type;
	
	/**
	 * 交易时间查询开始
	 */
	private Date tradeDateStart;
	
	/**
	 * 交易时间查询结束
	 */
	private Date tradeDateEnd;
	
	/**
	 * 排序:>0升序，<0降序，=0不排序。
	 */
	private Integer orderByTradeDate;
	
	private String prodName;
	
	private String tradeNo;
	
	/**
	状态
	 *  1--扣款成功  2--充值成功 3--退款成功
	 * */
	private Integer[] statusArray;
	

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderByStatus() {
		return orderByStatus;
	}

	public void setOrderByStatus(Integer orderByStatus) {
		this.orderByStatus = orderByStatus;
	}

	public Integer getOrderByApplyDate() {
		return orderByApplyDate;
	}

	public void setOrderByApplyDate(Integer orderByApplyDate) {
		this.orderByApplyDate = orderByApplyDate;
	}

	public String getReserveRecordID() {
		return reserveRecordID;
	}

	public void setReserveRecordID(String reserveRecordID) {
		this.reserveRecordID = reserveRecordID;
	}

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getTradeDateStart() {
		return tradeDateStart;
	}

	public void setTradeDateStart(Date tradeDateStart) {
		this.tradeDateStart = tradeDateStart;
	}

	public Date getTradeDateEnd() {
		return tradeDateEnd;
	}

	public void setTradeDateEnd(Date tradeDateEnd) {
		this.tradeDateEnd = tradeDateEnd;
	}

	public Integer getOrderByTradeDate() {
		return orderByTradeDate;
	}

	public void setOrderByTradeDate(Integer orderByTradeDate) {
		this.orderByTradeDate = orderByTradeDate;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}

}
