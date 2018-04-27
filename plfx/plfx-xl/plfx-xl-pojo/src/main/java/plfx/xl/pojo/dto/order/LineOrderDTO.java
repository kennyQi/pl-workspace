package plfx.xl.pojo.dto.order;

import java.util.Set;

import plfx.xl.pojo.dto.BaseXlDTO;
import plfx.xl.pojo.dto.line.LineSnapshotDTO;
import plfx.xl.pojo.dto.salepolicy.SalePolicySnapshotDTO;

/**
 * 
 * @类功能说明：线路订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日下午4:55:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineOrderDTO extends BaseXlDTO {
	/**
	 * 订单基本信息
	 */
	private LineOrderBaseInfoDTO baseInfo;
	
	/**
	 * 联系人信息
	 */
	private LineOrderLinkInfoDTO linkInfo;
	
	/**
	 * 游客信息列表
	 * */
	private Set<LineOrderTravelerDTO> travelers;
	 
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

	public LineSnapshotDTO getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshotDTO lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}

}