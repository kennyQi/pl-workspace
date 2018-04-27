package hsl.pojo.qo.hotel;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HotelOrderQO extends BaseQo {
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;
	
	/**
	 * 预付时间
	 */
	private Date creationDate;
	
	/**
	 * 下单区间 开始时间
	 */
	private String beginDateTime;
	
	/**
	 * 下单区间 结束时间
	 */
	private String endDateTime;
	
	/**
	 * 订单状态
	 */
	private String  showStatus;
	
	/**
	 * 页码
	 */
	private Integer pageNo=1;

	/**
	 * 返回条数
	 */
	private Integer pageSize=5;

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
}
