package plfx.api.client.api.v1.gj.dto;

import java.util.List;

import plfx.api.client.common.BaseDTO;

/**
 * @类功能说明：国际机票订单
 * @类修改者：
 * @修改日期：2015-6-29下午4:59:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午4:59:25
 */
@SuppressWarnings("serial")
public class GJJPOrderDTO extends BaseDTO {

	/**
	 * 订单基础信息
	 */
	private GJJPOrderBaseInfoDTO baseInfo;

	/**
	 * 异常订单信息
	 */
	private GJJPOrderExceptionInfoDTO exceptionInfo;

	/**
	 * 航段信息
	 */
	private GJJPOrderSegmentInfoDTO segmentInfo;

	/**
	 * 各乘机人信息
	 */
	private List<GJPassengerDTO> passengers;

	/**
	 * 联系人信息
	 */
	private GJJPOrderContacterInfoDTO contacterInfo;

	/**
	 * 支付信息
	 */
	private GJJPOrderPayInfoDTO dealerPayInfo;

	/**
	 * 订单状态
	 */
	private GJJPOrderStatusDTO status;

	public GJJPOrderBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(GJJPOrderBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public GJJPOrderExceptionInfoDTO getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(GJJPOrderExceptionInfoDTO exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public GJJPOrderSegmentInfoDTO getSegmentInfo() {
		return segmentInfo;
	}

	public void setSegmentInfo(GJJPOrderSegmentInfoDTO segmentInfo) {
		this.segmentInfo = segmentInfo;
	}

	public List<GJPassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<GJPassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public GJJPOrderContacterInfoDTO getContacterInfo() {
		return contacterInfo;
	}

	public void setContacterInfo(GJJPOrderContacterInfoDTO contacterInfo) {
		this.contacterInfo = contacterInfo;
	}

	public GJJPOrderPayInfoDTO getDealerPayInfo() {
		return dealerPayInfo;
	}

	public void setDealerPayInfo(GJJPOrderPayInfoDTO dealerPayInfo) {
		this.dealerPayInfo = dealerPayInfo;
	}

	public GJJPOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(GJJPOrderStatusDTO status) {
		this.status = status;
	}

}
