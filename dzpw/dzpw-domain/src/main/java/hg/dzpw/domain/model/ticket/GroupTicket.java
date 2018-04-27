package hg.dzpw.domain.model.ticket;

import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @类功能说明：门票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:10:34
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX + "GROUP_TICKET")
public class GroupTicket extends Ticket {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 单票   门票政策下单个景区
	 */
	public final static Integer GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT = 1;
	
	/**
	 * 联票   门票政策下多个景区
	 */
	public final static Integer GROUP_TICKET_TYPE_MORE_SCENIC_SPOT = 2;
	
	/**
	 * 门票中包含的单票
	 */
	@OneToMany(mappedBy = "groupTicket", cascade = { CascadeType.ALL })
	private List<SingleTicket> singleTickets;

	/**
	 * 单票、联票状态
	 */
	@Embedded
	private GroupTicketStatus status;
	
	/**
	 * 门票类型  1 单景区门票    2多景区联票
	 */
	@Column(name = "TYPE", columnDefinition = M.NUM_COLUM)
	private Integer type;
	
	/**
	 * 对应景区 （联票时才有对应值）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;
	
	/**
	 * 经销商手续费  元/张
	 */
	@Column(name = "DEALER_SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double dealerSettlementFee = 0d;
	
	/**
	 * 支付流水号
	 * 同一订单下门票的支付流水号相同
	 */
	@Column(name = "PAY_TRADE_NO", length = 64)
	private String payTradeNo;
	
	/**
	 * 退款流水号
	 * 一张门票对应一个退款流水号
	 */
	@Column(name = "REFUND_BATCH_NO", length = 64)
	private String refundBatchNo;
	
	/**
	 * 门票二维码图片地址
	 */
	@Column(name = "QR_CODE_URL", length = 1024)
	private String qrCodeUrl;
	
	/**
	 * @方法功能说明：创建联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-20下午4:09:55
	 * @参数：@param ticketNo
	 * @参数：@param ticketOrder
	 * @参数：@param salePrice
	 * @参数：@param travelDate
	 * @参数：@param tourist
	 * @参数：@param ticketPolicy
	 * @参数：@param dateStandardPrice
	 * @参数：@throws groupTicketStatus
	 * @参数：@throws DZPWException
	 */
	public void create(String ticketNo, TicketOrder ticketOrder, Double salePrice, 
			           Date travelDate, Tourist tourist, TicketPolicy ticketPolicy,
					   Double dateStandardPrice, Integer groupTicketStatus, Integer singleTicketStatus, String qrCodeUrl) throws DZPWException {
		
		if (!(ticketPolicy.isGroup() || ticketPolicy.isSingle()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "门票政策不可用");
		
		super.create(ticketNo, ticketPolicy, travelDate, tourist, ticketOrder, salePrice, dateStandardPrice);
		
		setQrCodeUrl(qrCodeUrl);
		
		getStatus().setCurrent(groupTicketStatus);
		
		if (ticketPolicy.isSingle()) {
			setScenicSpot(ticketPolicy.getScenicSpot());
			setType(GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT);
		} else
			setType(GROUP_TICKET_TYPE_MORE_SCENIC_SPOT);
		
		int i = 0;
		for (TicketPolicy singlePolicy : ticketPolicy.getSingleTicketPolicies()) {
			SingleTicket singleTicket = new SingleTicket();
			
			singleTicket.create(String.format(ticketNo + "%03d", ++i),
								ticketOrder, salePrice, travelDate, tourist, singlePolicy,
								singlePolicy.isSingleInSingle() ? dateStandardPrice : null, this,
								//没有游客信息时singleTicket状态为待激活  否则待游玩
								singleTicketStatus);
			
			// 设经销商结算价
			// 联票下的singleTicket
			/**
			 * 说明： 单票时：singleTicket里的price/dealerPrice 等于groupTicket里的price
			 *     联票时: singleTicket里的dealerPrice之和
			 * */
			if (singlePolicy.isSingleInGroup()){
				singleTicket.getSettlementInfo().setDealerPrice(singlePolicy.getBaseInfo().getPlayPrice());
			}else{
				singleTicket.getSettlementInfo().setDealerPrice(salePrice);
			}
			
			getSingleTickets().add(singleTicket);
		}
	}
	
	public List<SingleTicket> getSingleTickets() {
		if (singleTickets == null)
			singleTickets = new ArrayList<SingleTicket>();
		return singleTickets;
	}

	public void setSingleTickets(List<SingleTicket> singleTickets) {
		this.singleTickets = singleTickets;
	}

	public GroupTicketStatus getStatus() {
		if (status == null)
			status = new GroupTicketStatus();
		return status;
	}

	public void setStatus(GroupTicketStatus status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	
}