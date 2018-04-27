package plfx.api.client.api.v1.gj.event;

import java.util.List;

import plfx.api.client.common.PlfxApiConstants.GJ;
import plfx.api.client.common.publish.PublishEventMessage;

/**
 * @类功能说明：国际机票退废票事件消息
 * @类修改者：
 * @修改日期：2015-7-8下午3:17:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午3:17:31
 */
public class GJRefundTicketEventMessage extends PublishEventMessage {

	/**
	 * 分销平台国际机票订单号
	 */
	private String platformOrderId;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 乘客姓名
	 */
	private String passengerName;

	/**
	 * 证件类型
	 * 
	 * @see GJ#IDTYPE_MAP
	 */
	private Integer idType;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 机票票号
	 */
	private List<String> eticketNo;

	/**
	 * 实退金额
	 */
	private Double refundPrice;

	/**
	 * 退票状态
	 * 
	 * 1—成功2—拒绝退废票
	 */
	private Integer refundStatus;

	/**
	 * 拒绝退票理由
	 */
	private String refuseMemo;

	/**
	 * 退款手续费
	 */
	private Double refundFee;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public List<String> getEticketNo() {
		return eticketNo;
	}

	public void setEticketNo(List<String> eticketNo) {
		this.eticketNo = eticketNo;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	public Double getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

}
