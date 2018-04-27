package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：门票订单列表视图对象
 * @类修改者：
 * @修改日期：2015-2-28下午4:02:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-28下午4:02:20
 */
public class TicketOrderListVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 门票政策名称
	 */
	private String ticketPolicyName;
	
	/**
	 * 门票政策类型
	 */
	private Integer ticketPolicyType;

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderId;

	/**
	 * 经销商ID
	 */
	private String fromDealerId;

	/**
	 * 经销商名称
	 */
	private String fromDealerName;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 下单时间
	 */
	private Date orderDate;

	/**
	 * 支付金额
	 */
	private Double pay = 0d;
	
	/**
	 * 支付状态
	 */
	private Integer paid;

	/**
	 * 订单下的门票
	 */
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getFromDealerId() {
		return fromDealerId;
	}

	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
	}

	public String getFromDealerName() {
		return fromDealerName;
	}

	public void setFromDealerName(String fromDealerName) {
		this.fromDealerName = fromDealerName;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public List<Ticket> getTickets() {
		if (tickets == null)
			tickets = new ArrayList<TicketOrderListVo.Ticket>();
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		if (tickets == null) {
			tickets = new ArrayList<TicketOrderListVo.Ticket>();
			return;
		}
		this.tickets = tickets;
	}

	/**
	 * @类功能说明：订单下的门票
	 * @类修改者：
	 * @修改日期：2015-2-28下午3:57:31
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-2-28下午3:57:31
	 */
	public static class Ticket implements Serializable {
		private static final long serialVersionUID = 1L;
		/**
		 * 订单编号
		 */
		private String orderId;
		
		/**
		 * 门票政策ID
		 */
		private String ticketPolicyId;

		/**
		 * 门票政策名称
		 */
		private String ticketPolicyName;

		/**
		 * 门票政策类型（联票，独立单票）
		 */
		private Integer ticketPolicyType;

		/**
		 * 游客ID
		 */
		private String touristId;

		/**
		 * 游客姓名
		 */
		private String touristName;

		/**
		 * 结算价（元）
		 */
		private Double price;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getTicketPolicyId() {
			return ticketPolicyId;
		}

		public void setTicketPolicyId(String ticketPolicyId) {
			this.ticketPolicyId = ticketPolicyId;
		}

		public String getTicketPolicyName() {
			return ticketPolicyName;
		}

		public void setTicketPolicyName(String ticketPolicyName) {
			this.ticketPolicyName = ticketPolicyName;
		}

		public Integer getTicketPolicyType() {
			return ticketPolicyType;
		}

		public void setTicketPolicyType(Integer ticketPolicyType) {
			this.ticketPolicyType = ticketPolicyType;
		}

		public String getTouristId() {
			return touristId;
		}

		public void setTouristId(String touristId) {
			this.touristId = touristId;
		}

		public String getTouristName() {
			return touristName;
		}

		public void setTouristName(String touristName) {
			this.touristName = touristName;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

	}

}
