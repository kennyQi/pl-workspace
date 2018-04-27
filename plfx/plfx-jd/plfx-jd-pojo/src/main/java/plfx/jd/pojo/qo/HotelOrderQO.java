package plfx.jd.pojo.qo;
import hg.common.component.BaseQo;

/**
 *@类功能说明：酒店订单qo
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2015年01月27日下午3:10:49
 *
 */
@SuppressWarnings("serial")
public class HotelOrderQO extends BaseQo {
	/**
	 * 订单来源
	 */
	private String dealerProjectId;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 供应商平台
	 */
	private String supplierProjectId;
	/**
	 * 供应商平台订单号
	 */
	private String supplierOrderNo;
	/**
	 * 酒店编号
	 */
	private String hotelNo;
	/**
	 * 入住时间
	 */
	private String arrivalDateBegin;
	private String arrivalDateEnd;
	/**
	 * 离开时间
	 */
	private String departureDateBegin;
	private String departureDateEnd;
	/**
	 * 下单时间
	 */
	private String createDateBegin;
	private String createDateEnd;
	/**
	 * 联系人手机号
	 */
	private String contacts;
	/**
	 * 订单状态
	 */
	private String status;
	
	
	public String getDealerProjectId() {
		return dealerProjectId;
	}
	public void setDealerProjectId(String dealerProjectId) {
		this.dealerProjectId = dealerProjectId;
	}
	public String getSupplierProjectId() {
		return supplierProjectId;
	}
	public void setSupplierProjectId(String supplierProjectId) {
		this.supplierProjectId = supplierProjectId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}
	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}
	
	public String getHotelNo() {
		return hotelNo;
	}
	public void setHotelNo(String hotelNo) {
		this.hotelNo = hotelNo;
	}
	public String getArrivalDateBegin() {
		return arrivalDateBegin;
	}
	public void setArrivalDateBegin(String arrivalDateBegin) {
		this.arrivalDateBegin = arrivalDateBegin;
	}
	public String getArrivalDateEnd() {
		return arrivalDateEnd;
	}
	public void setArrivalDateEnd(String arrivalDateEnd) {
		this.arrivalDateEnd = arrivalDateEnd;
	}
	public String getDepartureDateBegin() {
		return departureDateBegin;
	}
	public void setDepartureDateBegin(String departureDateBegin) {
		this.departureDateBegin = departureDateBegin;
	}
	public String getDepartureDateEnd() {
		return departureDateEnd;
	}
	public void setDepartureDateEnd(String departureDateEnd) {
		this.departureDateEnd = departureDateEnd;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDateBegin() {
		return createDateBegin;
	}
	public void setCreateDateBegin(String createDateBegin) {
		this.createDateBegin = createDateBegin;
	}
	public String getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
	
}
