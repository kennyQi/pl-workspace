package slfx.mp.tcclient.tc.pojo.order.request;

import java.text.SimpleDateFormat;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderListDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;
/**
 * 提交订单接口请求
 * @author zhangqy
 *
 */
public class ParamSceneryOrderListOrder extends Param {
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
	private String cStartDate;
	/**
	 * 创建单束时间
	 */
	private String cEndDate;
	/**
	 * 旅游开始时间
	 */
	private String tStartDate;
	/**
	 * 旅游结束时间
	 */
	private String tEndDate;
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
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryOrderListDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryOrderListOrder)){
			throw new DtoErrorException();
		}
		SceneryOrderListDto dto=(SceneryOrderListDto)dto1; 
		ParamSceneryOrderListOrder param=(ParamSceneryOrderListOrder)param1; 
		param.setBookingMan(dto.getBookingMan());
		param.setBookingMobile(dto.getBookingMobile());
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		param.setCEndDate(dto.getcEndDate()==null?null:sdf.format(dto.getcEndDate()));
		param.setCStartDate(dto.getcStartDate()==null?null:sdf.format(dto.getcStartDate()));
		param.setGuestMobile(dto.getGuestMobile());
		param.setGuestName(dto.getGuestName());
		param.setOrderStatus(dto.getOrderStatus());
		param.setPage(dto.getPage());
		param.setPageSize(dto.getPageSize());
		param.setSerialId(dto.getSerialId());
		param.setTEndDate(dto.gettEndDate()==null?null:sdf.format(dto.gettEndDate()));
		param.setTStartDate(dto.gettStartDate()==null?null:sdf.format(dto.gettStartDate()));
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
	public String getCStartDate() {
		return cStartDate;
	}
	public void setCStartDate(String cStartDate) {
		this.cStartDate = cStartDate;
	}
	public String getCEndDate() {
		return cEndDate;
	}
	public void setCEndDate(String cEndDate) {
		this.cEndDate = cEndDate;
	}
	public String getTStartDate() {
		return tStartDate;
	}
	public void setTStartDate(String tStartDate) {
		this.tStartDate = tStartDate;
	}
	public String getTEndDate() {
		return tEndDate;
	}
	public void setTEndDate(String tEndDate) {
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
