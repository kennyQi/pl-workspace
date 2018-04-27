package zzpl.pojo.command.jp.plfx.gj;

import hg.common.component.BaseCommand;

import java.util.List;

import zzpl.pojo.dto.jp.plfx.gj.GJJPOrderContacterInfoDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJPassengerBaseInfoDTO;


@SuppressWarnings("serial")
public class CreateJPOrderGJCommand extends BaseCommand{
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 航班舱位组合和对应政策token
	 */
	private String flightAndPolicyToken;

	/**
	 * 各乘机人信息
	 */
	private List<GJPassengerBaseInfoDTO> passengerInfos;

	/**
	 * 联系人信息
	 */
	private GJJPOrderContacterInfoDTO contacterInfo;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlightAndPolicyToken() {
		return flightAndPolicyToken;
	}

	public void setFlightAndPolicyToken(String flightAndPolicyToken) {
		this.flightAndPolicyToken = flightAndPolicyToken;
	}

	public List<GJPassengerBaseInfoDTO> getPassengerInfos() {
		return passengerInfos;
	}

	public void setPassengerInfos(List<GJPassengerBaseInfoDTO> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}

	public GJJPOrderContacterInfoDTO getContacterInfo() {
		return contacterInfo;
	}

	public void setContacterInfo(GJJPOrderContacterInfoDTO contacterInfo) {
		this.contacterInfo = contacterInfo;
	}

}