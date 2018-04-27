package hg.dzpw.domain.model.order;

import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.dealer.Dealer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @类功能说明：订单基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:02:03
 * @版本：V1.0
 */
@Embeddable
public class TicketOrderBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 门票数量
	 */
	@Column(name = "TICKET_NO", columnDefinition = M.NUM_COLUM)
	private int ticketNo;
	
	/**
	 * 订单来源经销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEALER_ID")
	private Dealer fromDealer;

	/**
	 * 下单时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 经销商订单id
	 */
	@Column(name = "DEALER_ORDER_ID", length = 64)
	private String dealerOrderId;
	
	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 64)
	private String linkMan;
	
	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE", length = 32)
	private String telephone;

	public int getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}
	public Dealer getFromDealer() {
		return fromDealer;
	}
	public void setFromDealer(Dealer fromDealer) {
		this.fromDealer = fromDealer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}