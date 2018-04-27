package hsl.pojo.command.line;
import hg.common.component.BaseCommand;
import hsl.pojo.dto.line.LineSnapshotDTO;
import hsl.pojo.dto.line.order.LineOrderBaseInfoDTO;
import hsl.pojo.dto.line.order.LineOrderLinkInfoDTO;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;
import hsl.pojo.dto.line.order.LineOrderUserDTO;
import java.util.List;
@SuppressWarnings("serial")
public class HslCreateLineOrderCommand extends BaseCommand{
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
	 * 使用账户余额金额
	 */
	private Double accountBalance;
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

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

}
