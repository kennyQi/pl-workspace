package plfx.mp.tcclient.tc.dto.order;

import java.util.Date;

/**
 * 景区同步单列表接口
 * @author zhangqy
 *
 */
public class SceneryOrderListDto extends OrderDto{
	/**
	 * 当前页
	 */
	private Integer page;
	/**
	 * 每页条数
	 */
	private Integer pageSize;
	/**
	 * 创建单开始时间
	 */
	private Date cStartDate;
	/**
	 * 创建单束时间
	 */
	private Date cEndDate;
	/**
	 * 旅游开始时间
	 */
	private Date tStartDate;
	/**
	 * 旅游结束时间
	 */
	private Date tEndDate;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单流水号
	 */
	private String serialId;
	/**
	 * 预订人
	 */
	private String bookingMan;
	/**
	 * 预订人电话
	 */
	private String bookingMobile;
	/**
	 * 游玩人
	 */
	private String guestName;
	/**
	 * 游玩人电话
	 */
	private String guestMobile;
	
	public SceneryOrderListDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.order.request.ParamSceneryOrderListOrder");
		this.setResult("slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderList");
		this.setServiceName("GetSceneryOrderList");
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Date getcStartDate() {
		return cStartDate;
	}
	public void setcStartDate(Date cStartDate) {
		this.cStartDate = cStartDate;
	}
	public Date getcEndDate() {
		return cEndDate;
	}
	public void setcEndDate(Date cEndDate) {
		this.cEndDate = cEndDate;
	}
	public Date gettStartDate() {
		return tStartDate;
	}
	public void settStartDate(Date tStartDate) {
		this.tStartDate = tStartDate;
	}
	public Date gettEndDate() {
		return tEndDate;
	}
	public void settEndDate(Date tEndDate) {
		this.tEndDate = tEndDate;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getBookingMan() {
		return bookingMan;
	}
	public void setBookingMan(String bookingMan) {
		this.bookingMan = bookingMan;
	}
	public String getBookingMobile() {
		return bookingMobile;
	}
	public void setBookingMobile(String bookingMobile) {
		this.bookingMobile = bookingMobile;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getGuestMobile() {
		return guestMobile;
	}
	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}
	
	
}
