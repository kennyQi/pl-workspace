package hsl.pojo.dto.yxjp.notify;

import com.alibaba.fastjson.JSONObject;
import hsl.pojo.util.HSLConstants;

import java.util.Arrays;
import java.util.List;

/**
 * 分销申请退废票通知
 *
 * @author zhurz
 * @since 1.7
 */
public class ApplyRefundTicketNotify {

	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 乘客姓名
	 */
	private List<String> passengerNames;
	/**
	 * 票号，与姓名相对应
	 */
	private List<String> airIds;
	/**
	 * 申请种类
	 *
	 * @see HSLConstants.YXJPOrderRefundApply
	 */
	private Integer refundType;
	/**
	 * 申请理由
	 */
	private String refundMemo;
	/**
	 * 通知的json
	 */
	private transient JSONObject notifyJson;

	public ApplyRefundTicketNotify() {
	}

	public ApplyRefundTicketNotify(JSONObject json) {
		setNotifyJson(json);
		//	dealerOrderCode	经销商订单号	String	64
		setDealerOrderCode(json.getString("dealerOrderCode"));
		//	passengerName	乘客姓名	String	500	多个乘客之间用 ^ 分隔
		setPassengerNames(Arrays.asList(json.getString("passengerName").split("\\^")));
		//	airId	票号	String	500	票号之间用 ^分隔，并与姓名相对应
		setAirIds(Arrays.asList(json.getString("airId").split("\\^")));
		//	refundType	申请种类	String	20 1.当日作废 2.自愿退票 3.非自愿退票 4.差错退款 5.其他
		setRefundType(json.getInteger("refundType"));
		//	refundMemo	申请理由	text
		setRefundMemo(json.getString("refundMemo"));
	}

	public String passengerNames() {
		if (notifyJson == null) return "";
		return notifyJson.getString("passengerName");
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public List<String> getPassengerNames() {
		return passengerNames;
	}

	public void setPassengerNames(List<String> passengerNames) {
		this.passengerNames = passengerNames;
	}

	public List<String> getAirIds() {
		return airIds;
	}

	public void setAirIds(List<String> airIds) {
		this.airIds = airIds;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public JSONObject getNotifyJson() {
		return notifyJson;
	}

	public void setNotifyJson(JSONObject notifyJson) {
		this.notifyJson = notifyJson;
	}
}
