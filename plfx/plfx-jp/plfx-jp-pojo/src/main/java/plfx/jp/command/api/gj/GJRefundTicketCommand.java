package plfx.jp.command.api.gj;

import hg.common.component.BaseCommand;

import java.util.List;

/**
 * @类功能说明：退废票操作（供应商通知）
 * @类修改者：
 * @修改日期：2015年7月19日下午9:26:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015年7月19日下午9:26:43
 */
@SuppressWarnings("serial")
public class GJRefundTicketCommand extends BaseCommand {

	/**
	 * 分销平台国际机票订单号
	 */
	private String platformOrderId;

	/**
	 * 乘客姓名
	 */
	private String passengerName;

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
	
	/**
	 * @方法功能说明：是否退废票成功
	 * @修改者名字：zhurz
	 * @修改时间：2015年7月19日下午9:46:19
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isRefundSuccess() {
		if (Integer.valueOf(1).equals(refundStatus))
			return true;
		return false;
	}

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
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
