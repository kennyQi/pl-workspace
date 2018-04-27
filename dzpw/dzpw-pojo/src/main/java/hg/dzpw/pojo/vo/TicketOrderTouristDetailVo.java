package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：门票订单里的用户视图对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:29:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:29:17
 */
public class TicketOrderTouristDetailVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 游客ID
	 */
	private String touristId;

	/**
	 * 门票ID
	 */
	private String ticketId;

	/**
	 * 订单ID
	 */
	private String ticketOrderId;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 游玩人（姓名）
	 */
	private String name;

	/**
	 * 证件类型
	 */
	private String cerType;

	/**
	 * 证件号码
	 */
	private String cerNo;

	/**
	 * 游玩状态
	 */
	private int status;

	/**
	 * 下单时间
	 */
	private Date orderDate;

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCerType() {
		return cerType;
	}

	public void setCerType(Integer cerType) {
		if (cerType != null)
			this.cerType = String.valueOf(cerType);
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

}
