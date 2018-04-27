package plfx.gjjp.domain.model;

import hg.common.component.BaseModel;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import plfx.api.client.api.v1.gj.dto.GJPassengerBaseInfoDTO;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.command.admin.gj.ApplyRefundGJJPTicketCommand;
import plfx.jp.command.api.gj.GJRefundTicketCommand;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：乘客信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:03:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:03:39
 */
@Entity
@DynamicUpdate
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX_GJ + "PASSENGER")
public class GJPassenger extends BaseModel {

	/**
	 * 国际机票订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JP_ORDER_ID")
	private GJJPOrder jpOrder;

	/**
	 * 机票
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger", cascade = CascadeType.ALL)
	private List<GJJPTicket> tickets;

	/**
	 * 乘客基本信息
	 */
	@Embedded
	private GJPassengerBaseInfo baseInfo;

	/**
	 * 机票状态
	 */
	@Embedded
	private GJPassengerTicketStatus status;

	/**
	 * 价格明细
	 */
	@Embedded
	private GJPassengerTicketPriceDetail ticketPriceDetail;

	/**
	 * 退票详情(退票才有)
	 */
	@Embedded
	private GJPassengerTicketRefundDetail ticketRefundDetail;

	/**
	 * @方法功能说明：创建乘机人
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:59:53
	 * @修改内容：
	 * @参数：@param passengerBaseInfoDTO
	 * @参数：@param jpOrder
	 * @return:void
	 * @throws
	 */
	public void create(GJPassengerBaseInfoDTO passengerBaseInfoDTO, GJJPOrder jpOrder) {
		setId(UUIDGenerator.getUUID());
		setJpOrder(jpOrder);
		getBaseInfo().setPassengerName(passengerBaseInfoDTO.getPassengerName());
		getBaseInfo().setPassengerType(passengerBaseInfoDTO.getPassengerType());
		getBaseInfo().setSex(passengerBaseInfoDTO.getSex());
		getBaseInfo().setBirthday(passengerBaseInfoDTO.getBirthday());
		getBaseInfo().setNationality(passengerBaseInfoDTO.getNationality());
		getBaseInfo().setMobile(passengerBaseInfoDTO.getMobile());
		getBaseInfo().setIdType(passengerBaseInfoDTO.getIdType());
		getBaseInfo().setIdNo(passengerBaseInfoDTO.getIdNo());
		getBaseInfo().setExpiryDate(passengerBaseInfoDTO.getExpiryDate());
		getBaseInfo().setResiAddr(passengerBaseInfoDTO.getResiAddr());
		getBaseInfo().setResiPost(passengerBaseInfoDTO.getResiPost());
		getBaseInfo().setDstAddr(passengerBaseInfoDTO.getDstAddr());
		getBaseInfo().setDstPost(passengerBaseInfoDTO.getDstPost());
		getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_AUDIT_WAIT);
	}
	

	/**
	 * @方法功能说明：
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午6:01:28
	 * @修改内容：
	 * @参数：@param refundType		退票类型
	 * @参数：@param refundMemo		退票理由
	 * @return:void
	 * @throws
	 */
	private void refundSupplierAccept(Integer refundType, String refundMemo) {
		if (Integer.valueOf(1).equals(refundType)) {
			// 当日作废 0
			getTicketRefundDetail().setRefundType(0);
			getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_INVALID_WAIT);
		} else {
			// 退票1
			getTicketRefundDetail().setRefundType(1);
			getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_REFUND_WAIT);
		}
		Date now = new Date();
		getTicketRefundDetail().setSupplierReqRtnTime(now);
		getTicketRefundDetail().setReqRtnTime(now);
		getTicketRefundDetail().setRefundReason(refundMemo);
		getJpOrder().getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_REFUND_WAIT);
	
	}

	/**
	 * @方法功能说明：API退废票申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午6:00:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void apiRefundSupplierAccept(ApplyRefundTicketGJCommand command) {
		refundSupplierAccept(command.getRefundType(), command.getRefundMemo());
	}

	/**
	 * @方法功能说明：ADMIN退废票申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午6:00:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void adminRefundSupplierAccept(ApplyRefundGJJPTicketCommand command) {
		refundSupplierAccept(command.getRefundType(), command.getRefundMemo());
	}

	/**
	 * @方法功能说明：退废票申请结束
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午6:00:19
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void refundSupplierOver(GJRefundTicketCommand command) {
		Date now = new Date();
		GJPassengerTicketRefundDetail refundDetail = getTicketRefundDetail();
		if (command.isRefundSuccess()) {
			refundDetail.setSupplierRtnCompleteTime(now);
			refundDetail.setSupplierRefundPrice(command.getRefundPrice());
			refundDetail.setSupplierRefundFee(command.getRefundFee());
			refundDetail.setRtnCompleteTime(now);
			// 实际退还给经销商=供应商实退+差价
			refundDetail.setRefundPrice(MoneyUtil.round(command.getRefundPrice() + getTicketPriceDetail().getCommAmount(), 2));
			refundDetail.setRefundFee(command.getRefundFee());
			if (GJJPConstants.TICKET_STATUS_REFUND_WAIT.equals(getStatus().getCurrentValue()))
				getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_REFUND_ALREADY);
			else if (GJJPConstants.TICKET_STATUS_INVALID_WAIT.equals(getStatus().getCurrentValue()))
				getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_INVALID_ALREADY);
			// 判断是否全部退废票
			boolean hasAllRefund = true;
			for (GJPassenger passenger : getJpOrder().getPassengers()) {
				if (!(GJJPConstants.TICKET_STATUS_REFUND_ALREADY.equals(passenger.getStatus().getCurrentValue()) 
					|| GJJPConstants.TICKET_STATUS_INVALID_ALREADY.equals(passenger.getStatus().getCurrentValue()))) {
					hasAllRefund = false;
					break;
				}
			}
			if (hasAllRefund)
				getJpOrder().getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_REFUND_ALL);
			else
				getJpOrder().getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_REFUND_SOME);
		} else {
			refundDetail.setRefuseRefundReason(command.getRefuseMemo());
		}
	}

	public GJJPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(GJJPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

	public List<GJJPTicket> getTickets() {
		if (tickets == null)
			tickets = new ArrayList<GJJPTicket>();
		return tickets;
	}

	public void setTickets(List<GJJPTicket> tickets) {
		this.tickets = tickets;
	}

	public GJPassengerBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new GJPassengerBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(GJPassengerBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public GJPassengerTicketStatus getStatus() {
		if (status == null)
			status = new GJPassengerTicketStatus();
		return status;
	}

	public void setStatus(GJPassengerTicketStatus status) {
		this.status = status;
	}

	public GJPassengerTicketPriceDetail getTicketPriceDetail() {
		if (ticketPriceDetail == null)
			ticketPriceDetail = new GJPassengerTicketPriceDetail();
		return ticketPriceDetail;
	}

	public void setTicketPriceDetail(
			GJPassengerTicketPriceDetail ticketPriceDetail) {
		this.ticketPriceDetail = ticketPriceDetail;
	}

	public GJPassengerTicketRefundDetail getTicketRefundDetail() {
		if (ticketRefundDetail == null)
			ticketRefundDetail = new GJPassengerTicketRefundDetail();
		return ticketRefundDetail;
	}

	public void setTicketRefundDetail(
			GJPassengerTicketRefundDetail ticketRefundDetail) {
		this.ticketRefundDetail = ticketRefundDetail;
	}

}
