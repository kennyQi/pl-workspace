package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;


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