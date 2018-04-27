package hg.dzpw.domain.model.ticket;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.policy.TicketPolicyUseCondition;
import hg.dzpw.domain.model.tourist.Tourist;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * @类功能说明：门票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:49:16
 * @版本：V1.0
 */
@MappedSuperclass
public class Ticket extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 票号
	 */
	@Column(name = "TICKET_NO")
	private String ticketNo;
	/**
	 * 有效期开始时间
	 */
	@Column(name = "USE_DATE_START", columnDefinition = M.DATE_COLUM)
	private Date useDateStart;
	/**
	 * 有效期结束时间
	 */
	@Column(name = "USE_DATE_END", columnDefinition = M.DATE_COLUM)
	private Date useDateEnd;
	/**
	 * 门票政策
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_ID")
	private TicketPolicy ticketPolicy;
	/**
	 * 门票政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_SNAPSHOT_ID")
	private TicketPolicySnapshot ticketPolicySnapshot;

	/**
	 * 关联订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ORDER_ID")
	private TicketOrder ticketOrder;
	/**
	 * 实际价格
	 * 1.在groupTicket表里是：门票实际价格
	 * 2.在singleTicket表里是：景区实际价格(与景区结算价格)
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price = 0d;
	/**
	 * 基准价(仅单票有值)
	 */
	@Column(name = "STANDARD_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double standardPrice = 0d;
	
	/**
	 * @方法功能说明：创建门票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午4:46:10
	 * @修改内容：
	 * @参数：@param ticketNo
	 * @参数：@param ticketPolicy
	 * @参数：@param travelDate				旅行时间(独立单票)
	 * @参数：@param tourist
	 * @参数：@param ticketOrder
	 * @参数：@param salePrice				联票或独立单票实际价格
	 * @参数：@param dateStandardPrice		当日基准价(为null时使用门票政策里的基准价)
	 * @return:void
	 * @throws
	 */
	protected void create(String ticketNo, TicketPolicy ticketPolicy,
						  Date travelDate, Tourist tourist, TicketOrder ticketOrder,
						  Double salePrice, Double dateStandardPrice) {
	
		setId(DigestUtils.md5Hex(ticketNo));
		setTicketNo(ticketNo);
		
		// 设置联票有效期
		if (ticketPolicy.isGroup()) {
			// 固定有效期
			if (ticketPolicy.getUseInfo().getValidUseDateType() == TicketPolicyUseCondition.VALID_USE_DATE_TYPE_FIXED.intValue()) {
				setUseDateStart(ticketPolicy.getUseInfo().getFixedUseDateStart());
				setUseDateEnd(ticketPolicy.getUseInfo().getFixedUseDateEnd());
			}
			//从出票起有效天
			/**等有支付后  联票非固定的有效期计算 要移到支付完成后*/
			else {
				Date begin = DateUtils.truncate(new Date(), Calendar.DATE);
				Date end = DateUtils.addSeconds(DateUtils.addDays(begin,
						ticketPolicy.getUseInfo().getValidDays()), -1);
				setUseDateStart(begin);
				setUseDateEnd(end);
			}
		}
		// 设置独立单票有效期
		else if (ticketPolicy.isSingle() || ticketPolicy.isSingleInSingle()) {
			Date travelDateBegin = DateUtils.truncate(travelDate, Calendar.DATE);
			
			Date travelDateEnd = DateUtils.addSeconds(DateUtils.addDays(
					travelDateBegin, ticketPolicy.getUseInfo().getValidDays()), -1);
			
			setUseDateStart(travelDateBegin);
			setUseDateEnd(travelDateEnd);
		}

		//基准价
		if (dateStandardPrice != null)
			setStandardPrice(dateStandardPrice);
		
		// 实际价
		if (ticketPolicy.isSingleInGroup()) {//联票时
			//与景区的结算价格
			setPrice(ticketPolicy.getBaseInfo().getSettlementPrice());
		} else
			setPrice(salePrice);

		setTicketPolicy(ticketPolicy);
		setTicketPolicySnapshot(ticketPolicy.getLastSnapshot());
		setTicketOrder(ticketOrder);
	}
	
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Date getUseDateStart() {
		return useDateStart;
	}

	public void setUseDateStart(Date useDateStart) {
		this.useDateStart = useDateStart;
	}

	public Date getUseDateEnd() {
		return useDateEnd;
	}

	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
	}

	public TicketPolicy getTicketPolicy() {
		return ticketPolicy;
	}

	public void setTicketPolicy(TicketPolicy ticketPolicy) {
		this.ticketPolicy = ticketPolicy;
	}

//	public Tourist getTourist() {
//		return tourist;
//	}
//
//	public void setTourist(Tourist tourist) {
//		this.tourist = tourist;
//	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public TicketPolicySnapshot getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(
			TicketPolicySnapshot ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
	}

	public TicketOrder getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(TicketOrder ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public Double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

}