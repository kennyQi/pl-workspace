package slfx.mp.tcclient.tc.pojo.order.request;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.order.CancelSceneryOrderDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;
/**
 * 提交订单接口请求
 * @author zhangqy
 *
 */
public class ParamCancelSceneryOrder extends Param {
	/**
	 * 订单流水号
	 */
	private String serialId;
	/**
	 * 取消原有
	 */
	private Integer cancelReason;
	
	public static final Integer CANCEL_REASON_CHANGE=1;//行程变更
	public static final Integer CANCEL_REASON_OTHER=2;//通过其他更优惠的渠道预订了景区
	public static final Integer CANCEL_REASON_CONTENT=3;//对服务不满意
	public static final Integer CANCEL_REASON_REASON=4;//其他原因
	public static final Integer CANCEL_REASON_ERROR=5;//信息错误重新预订
	public static final Integer CANCEL_REASON_COMPANY=6;//景区不承认合作
	public static final Integer CANCEL_REASON_WEATHER=7;//天气原因
	public static final Integer CANCEL_REASON_REPEAT=6;//重复订单
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof CancelSceneryOrderDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamCancelSceneryOrder)){
			throw new DtoErrorException();
		}
		CancelSceneryOrderDto dto=(CancelSceneryOrderDto)dto1; 
		ParamCancelSceneryOrder param=(ParamCancelSceneryOrder)param1; 
		param.setCancelReason(dto.getCancelReason());
		param.setSerialId(dto.getSerialId());
		
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
