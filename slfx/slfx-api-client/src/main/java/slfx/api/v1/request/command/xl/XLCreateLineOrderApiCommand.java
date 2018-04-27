package slfx.api.v1.request.command.xl;

import java.util.List;

import slfx.api.base.ApiPayload;
import slfx.xl.pojo.dto.line.LineSnapshotDTO;
import slfx.xl.pojo.dto.order.LineOrderBaseInfoDTO;
import slfx.xl.pojo.dto.order.LineOrderLinkInfoDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.dto.salepolicy.SalePolicySnapshotDTO;

/**
 * 
 * @类功能说明：api接口创建线路订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午6:07:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLCreateLineOrderApiCommand extends ApiPayload {
	/**
	 * 商城订单基本信息
	 */
	private LineOrderBaseInfoDTO baseInfo;
	
	/**
	 * 商城联系人信息
	 */
	private LineOrderLinkInfoDTO linkInfo;
	
	/**
	 * 商城游客信息列表
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
	
	/**
	 * 线路快照
	 */
	private LineSnapshotDTO lineSnapshot;
	
	//------------ ----------------

	/**
	 * 线路编号
	 */
	private String lineID;
	
	/**
	 * 经销商编号
	 */
	private String lineDealerID;
	
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

	public LineSnapshotDTO getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshotDTO lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineDealerID() {
		return lineDealerID;
	}

	public void setLineDealerID(String lineDealerID) {
		this.lineDealerID = lineDealerID;
	}
	
	
}
