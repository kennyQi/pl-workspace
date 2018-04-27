package hsl.pojo.dto.yxjp.notify;

import com.alibaba.fastjson.JSONObject;

/**
 * 拒绝出票通知
 *
 * @author zhurz
 * @since 1.7
 */
public class OutTicketRefuseNotify {

	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 乘客姓名
	 */
	private String passengerName;
	/**
	 * 拒绝理由
	 */
	private String refuseMemo;


	public OutTicketRefuseNotify() {
	}

	public OutTicketRefuseNotify(JSONObject json) {
		//	dealerOrderCode	经销商订单号	String	64
		setDealerOrderCode(json.getString("dealerOrderCode"));
		//	passengerName	乘客姓名	String	500
		setPassengerName(json.getString("passengerName"));
		//	refuseMemo	拒绝理由	String	100
		setRefuseMemo(json.getString("refuseMemo"));
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}
}
