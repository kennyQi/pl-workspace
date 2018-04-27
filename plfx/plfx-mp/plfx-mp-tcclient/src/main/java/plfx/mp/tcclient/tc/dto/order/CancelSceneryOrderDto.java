package plfx.mp.tcclient.tc.dto.order;


/**
 * 取消订单接口
 * @author zhangqy
 *
 */
public class CancelSceneryOrderDto extends OrderDto{
	/**
	 * 订单流水号
	 */
	private String serialId;
	/**
	 * 取消原有
	 */
	private Integer cancelReason;
	
	public CancelSceneryOrderDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.order.request.ParamCancelSceneryOrder");
		this.setResult("slfx.mp.tcclient.tc.pojo.order.response.ResultCancelSceneryOrder");
		this.setServiceName("CancelSceneryOrder");
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public Integer getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}
}
