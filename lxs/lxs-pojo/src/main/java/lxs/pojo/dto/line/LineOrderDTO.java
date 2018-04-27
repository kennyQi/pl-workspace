package lxs.pojo.dto.line;

import java.util.List;
import java.util.Set;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderDTO extends BaseDTO{

	/**
	 * 订单基本信息
	 */
	private LineOrderBaseInfoDTO baseInfo;
	
	/**
	 * 联系人信息
	 */
	private LineOrderLinkInfoDTO linkInfo;
	
	/**
	 * 游客信息列表set
	 * */
	private Set<LineOrderTravelerDTO> travelers;
	
	/**
	 * 游客信息列表list
	 */
	private List<LineOrderTravelerDTO> travelerList;
	
	/**
	 * 成人类销售政策快照
	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
	 * 该值为下单当前有效的0和1类政策中优先度最高的
	 */
	private SalePolicySnapshotDTO adultSalePolicySnapshot;
	
	/**
	 * 儿童类销售政策快照
	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
	 * 该值为下单当前有效的0和2类政策中优先度最高的
	 */
	private SalePolicySnapshotDTO childSalePolicySnapshot;
	
	private LineOrderStatusDTO status;
	
	/**
	 * 线路下单用户
	 */
	private LineOrderUserDTO lineOrderUser;
	
	
	/**
	 * 线路快照
	 */
	private LineSnapshotDTO lineSnapshot;

	public LineOrderBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(LineOrderBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public LineOrderLinkInfoDTO getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(LineOrderLinkInfoDTO linkInfo) {
		this.linkInfo = linkInfo;
	}

	public Set<LineOrderTravelerDTO> getTravelers() {
		return travelers;
	}

	public void setTravelers(Set<LineOrderTravelerDTO> travelers) {
		this.travelers = travelers;
	}

	public List<LineOrderTravelerDTO> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LineOrderTravelerDTO> travelerList) {
		this.travelerList = travelerList;
	}



	public SalePolicySnapshotDTO getAdultSalePolicySnapshot() {
		return adultSalePolicySnapshot;
	}

	public void setAdultSalePolicySnapshot(
			SalePolicySnapshotDTO adultSalePolicySnapshot) {
		this.adultSalePolicySnapshot = adultSalePolicySnapshot;
	}

	public SalePolicySnapshotDTO getChildSalePolicySnapshot() {
		return childSalePolicySnapshot;
	}

	public void setChildSalePolicySnapshot(
			SalePolicySnapshotDTO childSalePolicySnapshot) {
		this.childSalePolicySnapshot = childSalePolicySnapshot;
	}

	public LineOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(LineOrderStatusDTO status) {
		this.status = status;
	}

	public LineOrderUserDTO getLineOrderUser() {
		return lineOrderUser;
	}

	public void setLineOrderUser(LineOrderUserDTO lineOrderUser) {
		this.lineOrderUser = lineOrderUser;
	}

	public LineSnapshotDTO getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshotDTO lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}
	
}
