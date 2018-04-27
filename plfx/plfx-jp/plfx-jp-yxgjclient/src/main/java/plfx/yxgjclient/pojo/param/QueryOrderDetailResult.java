package plfx.yxgjclient.pojo.param;

import java.util.List;
/**
 * 查询订单信息返回参数列表
 * @author guotx
 * 2015-07-06
 */
public class QueryOrderDetailResult {
	/**
	 * 订单信息
	 */
	private OrderInfo orderInfo;
	/**
	 * 航段信息
	 */
	private List<SegmentInfo> segmentInfos;
	/**
	 * 乘客信息
	 */
	private List<PassengerInfo> passengerInfos;
	/**
	 * 价格明细
	 */
	private List<PriceDetailInfo> priceDetailInfos;
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public List<SegmentInfo> getSegmentInfos() {
		return segmentInfos;
	}
	public void setSegmentInfos(List<SegmentInfo> segmentInfos) {
		this.segmentInfos = segmentInfos;
	}
	public List<PriceDetailInfo> getPriceDetailInfos() {
		return priceDetailInfos;
	}
	public void setPriceDetailInfos(List<PriceDetailInfo> priceDetailInfos) {
		this.priceDetailInfos = priceDetailInfos;
	}
	public List<PassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}
	public void setPassengerInfos(List<PassengerInfo> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}
}
