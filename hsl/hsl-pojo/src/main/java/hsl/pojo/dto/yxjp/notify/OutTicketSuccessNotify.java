package hsl.pojo.dto.yxjp.notify;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 出票成功通知
 *
 * @author zhurz
 * @since 1.7
 */
public class OutTicketSuccessNotify {

	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 乘客姓名
	 */
	private List<String> passengerNames;
	/**
	 * 票号之间用，并与姓名相对应
	 */
	private List<String> airIds;
	/**
	 * 新pnr
	 */
	private String newPnr;

	public OutTicketSuccessNotify() {
	}

	public OutTicketSuccessNotify(JSONObject json) {
		//	dealerOrderCode	经销商订单号	String	64
		setDealerOrderCode(json.getString("dealerOrderCode"));
		//	passengerName	乘客姓名	String	500	多个乘客之间用 ^ 分隔
		setPassengerNames(Arrays.asList(json.getString("passengerName").split("\\^")));
		//	airId	票号	String	500	票号之间用 ^分隔，并与姓名相对应
		setAirIds(Arrays.asList(json.getString("airId").split("\\^")));
		//	newPnr	新pnr	String	40	换编码出票后的pnr，如没有，则为空
		setNewPnr(json.get("newPnr") == null ? "" : json.getString("newPnr"));
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

	public String getNewPnr() {
		return newPnr;
	}

	public void setNewPnr(String newPnr) {
		this.newPnr = newPnr;
	}
}
