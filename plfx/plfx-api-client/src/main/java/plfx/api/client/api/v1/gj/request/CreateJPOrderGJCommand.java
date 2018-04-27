package plfx.api.client.api.v1.gj.request;

import java.util.List;

import plfx.api.client.api.v1.gj.dto.GJJPOrderContacterInfoDTO;
import plfx.api.client.api.v1.gj.dto.GJPassengerBaseInfoDTO;
import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：创建订单
 * @类修改者：
 * @修改日期：2015-7-6下午5:26:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-6下午5:26:57
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_CreateJPOrder)
public class CreateJPOrderGJCommand extends BaseClientCommand {

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
