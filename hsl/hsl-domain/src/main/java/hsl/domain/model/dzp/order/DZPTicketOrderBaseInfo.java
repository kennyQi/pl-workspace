package hsl.domain.model.dzp.order;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单基本信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketOrderBaseInfo implements Serializable, HSLConstants.FromType {

	/**
	 * 直销订单编号
	 */
	@Column(name = "ORDER_NO", length = 64)
	private String orderNo;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 默认为PC
	 *
	 * @see HSLConstants.FromType
	 */
	@Column(name = "FROM_CLIENT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer fromType;

	/**
	 * 电子票务订单编号
	 */
	@Column(name = "DZPW_ORDER_NO", length = 64)
	private String dzpwOrderNo;

	/**
	 * 门票数量
	 */
	@Column(name = "TICKET_NUM", columnDefinition = M.NUM_COLUM)
	private Integer ticketNum;

	/**
	 * 用户下单时间
	 */
	@Column(name = "ORDER_DATE", columnDefinition = M.DATE_COLUM)
	private Date orderDate;

	/**
	 * 向电子票务下单成功的时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 64)
	private String linkMan;
	
	/**
	 * 联系电话
	 */
	@Column(name = "LINK_MOBILE", length = 64)
	private String linkMobile;

	/**
	 * 游玩日期（景区门票必传，联票不需要）
	 */
	@Column(name = "TRAVEL_DATE", columnDefinition = M.DATE_COLUM)
	private Date travelDate;

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDzpwOrderNo() {
		return dzpwOrderNo;
	}

	public void setDzpwOrderNo(String dzpwOrderNo) {
		this.dzpwOrderNo = dzpwOrderNo;
	}

	public Integer getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
}