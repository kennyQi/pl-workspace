package hsl.pojo.dto.yxjp.notify;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 退废票通知
 *
 * @author zhurz
 * @since 1.7
 */
public class RefundTicketNotify {

	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 机票票号
	 */
	private List<String> airIds;
	/**
	 * 实退金额
	 */
	private Double refundPrice;
	/**
	 * 退票状态
	 * 1—成功 2—拒绝退废票
	 */
	private Integer refundStatus;
	/**
	 * 拒绝退票理由
	 */
	private String refuseMemo;
	/**
	 * 退款手续费
	 */
	private Double procedures;

	public RefundTicketNotify() {
	}

	public RefundTicketNotify(JSONObject json) {
		//	dealerOrderCode	经销商订单号	String	64
		setDealerOrderCode(json.getString("dealerOrderCode"));
		//	airId	机票票号	String	500	票号之间用 ^分隔
		setAirIds(Arrays.asList(json.getString("airId").split("\\^")));
		//	refundPrice	实退金额	Double		支付方实际退款金额
		setRefundPrice(json.getDouble("refundPrice"));
		//	refundStatus	退票状态	Integer	1	1—成功 2—拒绝退废票
		setRefundStatus(json.getInteger("refundStatus"));
		//	refuseMemo	拒绝退票理由	String	100
		setRefuseMemo(json.getString("refuseMemo"));
		//	procedures	退款手续费	Double		退款时的手续费
		setProcedures(json.getDouble("procedures"));
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public List<String> getAirIds() {
		return airIds;
	}

	public void setAirIds(List<String> airIds) {
		this.airIds = airIds;
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
