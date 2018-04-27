package plfx.mp.tcclient.tc.pojo.order.request;

import java.text.SimpleDateFormat;
import java.util.List;

import plfx.mp.tcclient.tc.domain.order.Guest;
import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.order.SubmitSceneryOrderDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;
import plfx.mp.tcclient.tc.pojo.Param;
/**
 * 提交订单接口请求
 * @author zhangqy
 *
 */
public class ParamSubmitSceneryOrder extends Param {
	/**
	 * 景区ID
	 */
	private String sceneryId;
	/**
	 * 预订人
	 */
	private String bMan;
	/**
	 * 预订人手机
	 */
	private String bMobile;
	/**
	 * 预订人地址
	 */
	private String bAddress;
	/**
	 * 预订人邮编
	 */
	private String bPostCode;
	/***
	 * 预订人邮箱
	 */
	private String bEmail;
	/**
	 * 取票人姓名
	 */
	private String tName;
	/**
	 * 取票人手机
	 */
	private String tMobile;
	/**
	 * 政策ID
	 */
	private String policyId;
	/**
	 * 预订票数
	 */
	private String tickets;
	/**
	 * 旅游日期
	 */
	private String travelDate;
	/**
	 * 预订人IP
	 */
	private String orderIP;
	/**
	 * 二代身份证
	 */
	private String idCard;
	/**
	 * 
	 */
	private List<Guest> otherGuest;	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SubmitSceneryOrderDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSubmitSceneryOrder)){
			throw new DtoErrorException();
		}
		SubmitSceneryOrderDto dto=(SubmitSceneryOrderDto)dto1; 
		ParamSubmitSceneryOrder param=(ParamSubmitSceneryOrder)param1; 
		param.setBAddress(dto.getbAddress());
		param.setBEmail(dto.getbEmail());
		param.setBMan(dto.getbMan());
		param.setBMobile(dto.getbMobile());
		param.setBPostCode(dto.getbPostCode());
		param.setIdCard(dto.getIdCard());
		param.setOrderIP(dto.getOrderIP());
		param.setOtherGuest(dto.getOtherGuest());
		param.setPolicyId(dto.getPolicyId()+"");
		param.setSceneryId(dto.getSceneryId());
		param.setTickets(dto.getTickets()+"");
		param.setTMobile(dto.gettMobile());
		param.setTName(dto.gettName());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		param.setTravelDate(sdf.format(dto.getTravelDate()));
		
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getBMan() {
		return bMan;
	}
	public void setBMan(String bMan) {
		this.bMan = bMan;
	}
	public String getBMobile() {
		return bMobile;
	}
	public void setBMobile(String bMobile) {
		this.bMobile = bMobile;
	}
	public String getBAddress() {
		return bAddress;
	}
	public void setBAddress(String bAddress) {
		this.bAddress = bAddress;
	}
	public String getBPostCode() {
		return bPostCode;
	}
	public void setBPostCode(String bPostCode) {
		this.bPostCode = bPostCode;
	}
	public String getBEmail() {
		return bEmail;
	}
	public void setBEmail(String bEmail) {
		this.bEmail = bEmail;
	}
	public String getTName() {
		return tName;
	}
	public void setTName(String tName) {
		this.tName = tName;
	}
	public String getTMobile() {
		return tMobile;
	}
	public void setTMobile(String tMobile) {
		this.tMobile = tMobile;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getOrderIP() {
		return orderIP;
	}
	public void setOrderIP(String orderIP) {
		this.orderIP = orderIP;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public List<Guest> getOtherGuest() {
		return otherGuest;
	}
	public void setOtherGuest(List<Guest> otherGuest) {
		this.otherGuest = otherGuest;
	}
	
}
