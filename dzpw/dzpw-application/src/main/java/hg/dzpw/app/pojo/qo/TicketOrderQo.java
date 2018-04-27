package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;
import java.util.Date;

/**
 * @类功能说明: 联票订单QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午5:10:11
 * @版本：V1.0
 */
public class TicketOrderQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 经销商订单id
	 */
	private String dealerOrderId;

	/**
	 * 经销商Id
	 */
	private String dealerId;

	/**
	 * 下单时间-开始
	 */
	private Date createBeginDate;

	/**
	 * 下单时间-结束
	 */
	private Date createEndDate;

	/**
	 * 联票名称
	 */
	private String ticketName;

	/**
	 * 订单状态(0,等待支付；1,支付成功；2,出票成功；3,交易成功；4,交易关闭)
	 */
	private Integer status;

	/**
	 * 支付状态(0,未支付；1,已支付；2,已收款)
	 */
	private Integer paid;
	
	/**
	 * 票务类型
	 */
	private Integer ticketPolicyType;

	/**
	 * 门票
	 */
	private TicketQo ticketQo;

	/**
	 * 门票政策
	 */
	private TicketPolicyQo ticketPolicyQo;
	
	/**
	 * 预定人
	 */
	private String linkMan;
	
	/**
	 * 经销商
	 */
	private DealerQo dealerQo;
	
	private TicketPolicySnapshotQo ticketPolicySnapshotQo;

	private String createBeginDateStr;
	private String createEndDateStr;
	private Integer pageNum;
	private Integer createDateSort;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId == null ? null : dealerOrderId
				.trim();
	}

	public Date getCreateBeginDate() {
		return createBeginDate;
	}

	public void setCreateBeginDate(Date createBeginDate) {
		this.createBeginDate = createBeginDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName == null ? null : ticketName.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public String getCreateBeginDateStr() {
		return createBeginDateStr;
	}

	public void setCreateBeginDateStr(String createBeginDateStr) {
		this.createBeginDateStr = createBeginDateStr;
	}

	public String getCreateEndDateStr() {
		return createEndDateStr;
	}

	public void setCreateEndDateStr(String createEndDateStr) {
		this.createEndDateStr = createEndDateStr;
	}

	public Integer getCreateDateSort() {
		return createDateSort;
	}

	public void setCreateDateSort(Integer createDateSort) {
		this.createDateSort = createDateSort;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId == null ? null : dealerId.trim();
	}

	public TicketQo getTicketQo() {
		return ticketQo;
	}

	public void setTicketQo(TicketQo ticketQo) {
		this.ticketQo = ticketQo;
	}

	public TicketPolicySnapshotQo getTicketPolicySnapshotQo() {
		return ticketPolicySnapshotQo;
	}

	public void setTicketPolicySnapshotQo(
			TicketPolicySnapshotQo ticketPolicySnapshotQo) {
		this.ticketPolicySnapshotQo = ticketPolicySnapshotQo;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public TicketPolicyQo getTicketPolicyQo() {
		return ticketPolicyQo;
	}

	public void setTicketPolicyQo(TicketPolicyQo ticketPolicyQo) {
		this.ticketPolicyQo = ticketPolicyQo;
	}

	public DealerQo getDealerQo() {
		return dealerQo;
	}

	public void setDealerQo(DealerQo dealerQo) {
		this.dealerQo = dealerQo;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
}