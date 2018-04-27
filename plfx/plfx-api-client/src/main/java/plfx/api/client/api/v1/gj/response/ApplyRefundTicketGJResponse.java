package plfx.api.client.api.v1.gj.response;

import java.util.List;

import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand.RefundPassengerInfo;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：申请退废票结果
 * @类修改者：
 * @修改日期：2015-7-7下午3:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:51:58
 */
@SuppressWarnings("serial")
public class ApplyRefundTicketGJResponse extends ApiResponse {
	
	/**
	 * 结果代码：订单不存在
	 */
	public final static String RESULT_NOT_EXIST = "-1";
	
	/**
	 * 结果代码：订单不能退废票
	 */
	public final static String RESULT_CANNOT_REFUND = "-2";

	/**
	 * 结果代码：无法当日废票
	 */
	public final static String RESULT_CANNOT_INVALID = "-3";

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 申请成功的乘客
	 */
	private List<RefundPassengerInfo> succPassengerInfos;

	/**
	 * 申请失败的乘客
	 */
	private List<RefundPassengerInfo> failPassengerInfos;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public List<RefundPassengerInfo> getSuccPassengerInfos() {
		return succPassengerInfos;
	}

	public void setSuccPassengerInfos(List<RefundPassengerInfo> succPassengerInfos) {
		this.succPassengerInfos = succPassengerInfos;
	}

	public List<RefundPassengerInfo> getFailPassengerInfos() {
		return failPassengerInfos;
	}

	public void setFailPassengerInfos(List<RefundPassengerInfo> failPassengerInfos) {
		this.failPassengerInfos = failPassengerInfos;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

}
