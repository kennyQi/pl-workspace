package hsl.domain.model.jp;
import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.dto.jp.PassengerGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JP + "FLIGHT_ORDER")
public class FlightOrder extends BaseModel {

	/**
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 512)
	private String orderNO;
	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;
	/**
	 * 订单的pnr
	 */
	@Column(name = "PNR",length=64)
	private String pnr;
	/**
	 * 订单状态
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer status;

	/** 支付状态 */
	@Column(name = "PAYSTATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer payStatus;
	
	/**
	 * 支付帐号
	 */
	@Column(name = "BUYEREMAIL", length=64)
	private String buyerEmail;
	/**
	 * 支付订单号
	 */
	@Column(name = "PAYTRADENO", length=128)
	private String payTradeNo;
	/**
	 * 订单种类
	 * 1：正常订单
	 * 2：取消订单
	 * 3：退款订单
	 * 4:记录订单
	 */
	@Column(name = "ORDER_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private String orderType;
	/**
	 *1：正常订单
	 */
	public final  static  String ORDERTYPE_NORMAL="1";
	/**
	 *2：取消订单
	 */
	public final  static  String ORDERTYPE_CANCEL="2";
	/**
	 *3：退款订单
	 */
	public final  static  String ORDERTYPE_REFUND="3";
	/**
	 *4:记录订单
	 */
	public final  static  String ORDERTYPE_RECORD="4";
	/**
	 * 备注
	 */
	@Column(name = "NOTE", columnDefinition = M.TEXT_COLUM)
	private String note;
	/**
	 * 乘客集合
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade={CascadeType.ALL})
	private List<Passenger> passengers;
	/**
	 * 下单用户
	 */
	@Embedded
	private JPOrderUser jpOrderUser;
	/**
	 * 订单联系人信息
	 */
	@Embedded
	private JPOrderLinkInfo jpLinkInfo;
	/**
	 * 航班基本信息
	 */
	@Embedded
	private FlightBaseInfo flightBaseInfo;
	/**
	 * 航班价格信息
	 */
	@Embedded
	private FlightPriceInfo flightPriceInfo;
	/**
	 * 经销商返回信息
	 */
	@Embedded
	private DealerReturnInfo dealerReturnInfo;
	/**
	 * @方法功能说明：创建机票订单
	 * @修改者名字：chenxy
	 * @修改时间：2015年7月30日下午3:05:04
	 * @修改内容：
	 * @参数：@param bookGNFlightCommand
	 * @return:void
	 * @throws
	 */
	public void  createFlightOrder(BookGNFlightCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setOrderNO(command.getOrderNO());
		this.setCreateTime(new Date());
		this.setNote(command.getNote());
		this.setOrderType("1");
		this.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));
		this.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
		
		jpOrderUser=new JPOrderUser();
		jpOrderUser.setLoginName(command.getLoginName());
		jpOrderUser.setUserId(command.getUserID());
		jpOrderUser.setMobile(command.getUserMobilePhone());
		jpLinkInfo=new JPOrderLinkInfo();
		jpLinkInfo.setLinkName(command.getLinkName());
		jpLinkInfo.setLinkEmail(command.getLinkEmail());
		jpLinkInfo.setLinkTelephone(command.getLinkTelephone());
		flightBaseInfo=new FlightBaseInfo();
		flightBaseInfo.setStopNumber(command.getStopNumber());
		flightBaseInfo.setAirCompanyName(command.getAirCompanyName());
		flightBaseInfo.setAirComp(command.getAirComp());
		flightBaseInfo.setArrivalAirport(command.getArrivalAirport());
		flightBaseInfo.setArrivalTerm(command.getArrivalTerm());
		flightBaseInfo.setCabinDiscount(command.getCabinDiscount());
		flightBaseInfo.setCabinName(command.getCabinName());
		flightBaseInfo.setDepartAirport(command.getDepartAirport());
		flightBaseInfo.setDepartTerm(command.getDepartTerm());
		flightBaseInfo.setDstCity(command.getDstCity());
		flightBaseInfo.setEncryptString(command.getEncryptString());
		flightBaseInfo.setEndTime(command.getEndTime());
		flightBaseInfo.setFlightNO(command.getFlightNO());
		flightBaseInfo.setOrgCity(command.getOrgCity());
		flightBaseInfo.setPlaneType(command.getPlaneType());
		flightBaseInfo.setStartTime(command.getStartTime());
		flightPriceInfo=new FlightPriceInfo();
		flightPriceInfo.setBuildFee(command.getFlightPriceInfoDTO().getBuildFee());
		flightPriceInfo.setIbePrice(command.getFlightPriceInfoDTO().getIbePrice());
		flightPriceInfo.setOilFee(command.getFlightPriceInfoDTO().getOilFee());
		flightPriceInfo.setPayAmount(command.getFlightPriceInfoDTO().getPayAmount());
		flightPriceInfo.setPayCash(command.getFlightPriceInfoDTO().getPayCash());
		flightPriceInfo.setPayBalance(command.getFlightPriceInfoDTO().getPayBalance());
		flightPriceInfo.setSinglePlatTotalPrice(command.getFlightPriceInfoDTO().getSinglePlatTotalPrice());
		passengers=new ArrayList<Passenger>();
		for(PassengerGNDTO passengerDTO:command.getPassengers()){
			Passenger passenger=new Passenger();
			passenger.setId(UUIDGenerator.getUUID());
			passenger.setIdNo(passengerDTO.getIdNo());
			passenger.setPassengerName(passengerDTO.getPassengerName());
			passenger.setPassengerType(passengerDTO.getPassengerType());
			passenger.setMobile(passengerDTO.getMobile());
			passenger.setOrder(this);
			passenger.setIdType(passengerDTO.getIdType());
			passengers.add(passenger);
		}
		dealerReturnInfo=new DealerReturnInfo();
		dealerReturnInfo.setMemo(command.getMemo());
		dealerReturnInfo.setTickType(command.getTickType());
		dealerReturnInfo.setTickPrice(command.getTickPrice());
		dealerReturnInfo.setTotalPrice(command.getTotalPrice());
		dealerReturnInfo.setPlcid(command.getPlcid());
		dealerReturnInfo.setRefuseMemo(command.getRefuseMemo());
		dealerReturnInfo.setPayPlatform(command.getPayPlatform());
	}
	//取消订单
	public void cancelFlightOrder(FlightOrder order,String[] psgName){
		this.setId(UUIDGenerator.getUUID());
		this.setOrderNO(order.getOrderNO());
		this.setCreateTime(order.getCreateTime());
		this.setNote(order.getNote());
		this.setPayTradeNo(order.getPayTradeNo());
		this.setBuyerEmail(order.getBuyerEmail());
		
		this.setOrderType("2");
		this.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_CANCEL));
		this.setPayStatus(order.getPayStatus());
		this.setJpOrderUser(order.getJpOrderUser());
		this.setJpLinkInfo(order.getJpLinkInfo());
		this.setFlightBaseInfo(order.getFlightBaseInfo());
		
		this.setFlightPriceInfo(BeanMapperUtils.map(order.getFlightPriceInfo(), FlightPriceInfo.class));
		this.setDealerReturnInfo(BeanMapperUtils.map(order.getDealerReturnInfo(), DealerReturnInfo.class));
		
		//乘客信息
		passengers=new ArrayList<Passenger>();
		for(String name:psgName){
			for(Passenger psg:order.getPassengers()){
				if(name.equals(psg.getPassengerName())){
					psg.setOrder(this);
					passengers.add(psg);
				}
			}
		}
		
	   this.setPassengers(passengers);			
		
	}
	//退废订单
	public void refundFlightOrder(FlightOrder order,String[] psgName){
		this.setId(UUIDGenerator.getUUID());
		this.setOrderNO(order.getOrderNO());
		this.setCreateTime(order.getCreateTime());
		this.setNote(order.getNote());
		this.setPayTradeNo(order.getPayTradeNo());
		this.setBuyerEmail(order.getBuyerEmail());

		this.setOrderType(FlightOrder.ORDERTYPE_REFUND);
		this.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_DEALING));
		this.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_WAIT));
		this.setJpOrderUser(order.getJpOrderUser());
		this.setJpLinkInfo(order.getJpLinkInfo());
		this.setFlightBaseInfo(order.getFlightBaseInfo());
		this.setFlightPriceInfo(BeanMapperUtils.map(order.getFlightPriceInfo(), FlightPriceInfo.class));
		this.setDealerReturnInfo(BeanMapperUtils.map(order.getDealerReturnInfo(), DealerReturnInfo.class));
		
		//乘客信息
		passengers=new ArrayList<Passenger>();
		for(String name:psgName){
			for(Passenger psg:order.getPassengers()){
				if(name.equals(psg.getPassengerName())){
					psg.setOrder(this);
					passengers.add(psg);
				}
		    }
		}
	   this.setPassengers(passengers);
		
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	public JPOrderUser getJpOrderUser() {
		return jpOrderUser;
	}
	public void setJpOrderUser(JPOrderUser jpOrderUser) {
		this.jpOrderUser = jpOrderUser;
	}
	public JPOrderLinkInfo getJpLinkInfo() {
		return jpLinkInfo;
	}
	public void setJpLinkInfo(JPOrderLinkInfo jpLinkInfo) {
		this.jpLinkInfo = jpLinkInfo;
	}
	public FlightBaseInfo getFlightBaseInfo() {
		return flightBaseInfo;
	}
	public void setFlightBaseInfo(FlightBaseInfo flightBaseInfo) {
		this.flightBaseInfo = flightBaseInfo;
	}
	public FlightPriceInfo getFlightPriceInfo() {
		return flightPriceInfo;
	}
	public void setFlightPriceInfo(FlightPriceInfo flightPriceInfo) {
		this.flightPriceInfo = flightPriceInfo;
	}
	public DealerReturnInfo getDealerReturnInfo() {
		return dealerReturnInfo;
	}
	public void setDealerReturnInfo(DealerReturnInfo dealerReturnInfo) {
		this.dealerReturnInfo = dealerReturnInfo;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
