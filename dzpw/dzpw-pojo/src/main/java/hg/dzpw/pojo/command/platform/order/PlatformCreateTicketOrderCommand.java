package hg.dzpw.pojo.command.platform.order;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.Date;
import java.util.List;

/**
 * @类功能说明: 创建联票订单
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-21 下午1:42:40 
 * @版本：V1.0
 */
public class PlatformCreateTicketOrderCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票数量
	 */
	private Integer ticketNo;
	
	/**
	 * 订单来源经销商
	 */
	private String dealerId;
	
	/**
	 * 下单时间
	 */
	private Date createDate;
	
	/**
	 * 经销商订单id
	 */
	private String dealerOrderId;
	
	/**
	 * 当前状态(0,未出票；1,已出票；2,交易关闭)
	 */
	private Integer currentValue;
	
	/**
	 * 分销价格政策(快照)
	 */
	private String saleId;
	
	/**
	 * 支付时间
	 */
	private Date payDate;
	
	/**
	 * 金额
	 */
	private Double price;
	
	/**
	 * 支付状态(0,未支付；1,已支付；2,已收款)
	 */
	private Integer paid;
	
	/**
	 * 门票政策
	 */
	private String policyId;
	
	/**
	 * 单票
	 */
	private List<String> singleIds;
	
	/**
	 * 套票
	 */
	private List<String> groupIds;

	public Integer getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(Integer ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId == null ? null : dealerId.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId == null ? null : dealerOrderId.trim();
	}

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId == null ? null : saleId.trim();
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId == null ? null : policyId.trim();
	}

	public List<String> getSingleIds() {
		return singleIds;
	}

	public void setSingleIds(List<String> singleIds) {
		this.singleIds = (null == singleIds || singleIds.size() < 1)?null:singleIds;
	}

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = (null == groupIds || groupIds.size() < 1)?null:groupIds;
	}
}