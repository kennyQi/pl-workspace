package plfx.yeexing.pojo.command.order;

@SuppressWarnings("serial")
public class JPOrderRefundCommand extends JPBaseCommand{
	/**
	 * 机票票号
	 * 票号之间用 ^分隔
	 */
	private String airId;
	
	/**
	 * 实退金额
	 * 支付方实际退款金额
	 */
	private Double refundPrice;

	/**
	 * 退票状态
	 * 1—成功 2—拒绝退废票
	 */
	private Integer refundStatus;
	/**
	 * 拒绝退票理由
	 * 获取时使用urldecode解密
	 */
	private String refuseMemo;
	/**
	 * 退款手续费
	 * 退款时的手续费
	 */
	private Double procedures;
	public String getAirId() {
		return airId;
	}
	public void setAirId(String airId) {
		this.airId = airId;
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
	public Double getProcedures() {
		return procedures;
	}
	public void setProcedures(Double procedures) {
		this.procedures = procedures;
	}
	
}
