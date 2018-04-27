package lxs.pojo.command.line;
import hg.common.component.BaseCommand;

import java.util.List;

import lxs.pojo.dto.line.LineOrderBaseInfoDTO;
import lxs.pojo.dto.line.LineOrderLinkInfoDTO;
import lxs.pojo.dto.line.LineOrderTravelerDTO;
import lxs.pojo.dto.line.LineOrderUserDTO;
import lxs.pojo.dto.line.LineSnapshotDTO;
@SuppressWarnings("serial")
public class CreateLineOrderCommand extends BaseCommand{
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
	 * 线路快照
	 */
	private LineSnapshotDTO lineSnapshot;
	
	//------------ ----------------

	/**
	 * 线路编号
	 */
	private String lineID;
	
	/**
	 * 订单来源 0来自移动端，1来自pc端；
	 */
	private int source;
	
	/**
	 * 线路下单用户
	 */
	private LineOrderUserDTO lineOrderUser;
	
	/**
	 * 保险金额
	 */
	private Integer insurancePrice;
	
	/**
	 * 是否购买保险
	 * 1：购买
	 * 0：未购买
	 */
	private Integer isBuyInsurance;
	
	
	
	public Integer getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(Integer insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Integer getIsBuyInsurance() {
		return isBuyInsurance;
	}

	public void setIsBuyInsurance(Integer isBuyInsurance) {
		this.isBuyInsurance = isBuyInsurance;
	}

	public LineOrderUserDTO getLineOrderUser() {
		return lineOrderUser;
	}

	public void setLineOrderUser(LineOrderUserDTO lineOrderUser) {
		this.lineOrderUser = lineOrderUser;
	}

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

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public LineSnapshotDTO getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshotDTO lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}

}
