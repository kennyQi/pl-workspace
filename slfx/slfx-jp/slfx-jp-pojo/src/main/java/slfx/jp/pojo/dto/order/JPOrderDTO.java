package slfx.jp.pojo.dto.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import slfx.jp.pojo.dto.BaseJpDTO;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;

/**
 * 
 * @类功能说明：机票平台订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:55:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderDTO extends BaseJpDTO {
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 易购单号
	 */
	private String ygOrderNo;					

	/**
	 * 供应商订单号
	 */
	private String supplierOrderNo;
	/**
	 * abe订单
	 */
	private String abeOrderId;
	
	/** 经销商名字 */
	private String dealerName;
	
	private String pnr;	
	
	private String classCode;	
	
	/**
	 * 下单人
	 */
	private String userId;
	
	/**
	 * 下单登录名
	 */
	private String loginName;
	
	/**
	 * 票号
	 */
	private String[] tktNo;		
	
	/**
	 * 订单来源
	 */
	private String orderSource;	
	
	/**
	 * 乘机人列表：商城端用
	 */
	private List<FlightPassengerDTO> passangers=new ArrayList<FlightPassengerDTO>();	
	
	/**
	 * 乘机人列表：admin端用
	 */
	private Set<Passenger> passangerList;

	/**
	 * 仓位
	 */
	private String classNo;
	/**
	 * 航程	
	 */
	private String flightay;
	/**
	 * 航班号 string ZH9814
	 */
	private String flightNo;				
	
	/**
	 * 航空公司二字码
	 */
	private String carrier;	
	
	/**
	 * 出发城市代码:admin端用
	 */
	private String startCityCode;
	/**
	 * 出发城市名称
	 */
	private String startCityName;
	
	
	/**
	 * 目的城市代码：admin端用
	 */
	private String endCityCode;
	
	/**
	 * 到达城市名称
	 */
	private String endCityName;
	/**
	 * 起飞时间
	 */
	private String departureTime;
	
	/**
	 * 支付金额
	private Double payAmount;
	 */
	/**
	 * 支付金额 - 最新字段
	 */
	private Double userPayAmount;
	
	/**
	 * 单人票面价
	 */
	private Double ticketPrice;
	
	/**
	 * 乘机人数
	 */
	private Integer passangerCount;
	
	/**
	 * 票面价金额
	 */
	private Double tktAmount;
	
	private Double tktPrice;
	
	/**
	 *单人税费
	 */
	private Double tktTax;	
	
	/** 平台赚取的返点：用户支付总价的百分比 */
	private Double commRate;
	
	/** 平台所得佣金 ：用户支付总价的百分比计算后的具体所得 */
	private Double commAmount;
	
	private Double suppPrice;
	
	//--- 单张票的相关参数
	private Double oneUserPayAmount;
	private Double oneTktAmount;
	private Double oneTktTax;
	private Double onePlatPolicyPirce;  
	//--
	
	/**
	 * 商家信息
	 */
	private String agencyName;
	
	/**
	 * 商家联系方式
	private String agencyTel;
	 */
	
	private SlfxFlightDTO flightDTO;
	
	/**
	 * 订单创建时间 
	 */
	private Date createDate;	
	
	
	/**
	 * 经销商订单号 
	 */
	private String dealerOrderCode;
	
	/**
	 * 供应商支付方式 
	 */
	private String supplierPayType;

	/**
	 * 航班信息快照
	 */
	private String flightSnapshotJSON;
	
	/**
	 * 出票平台
	 */
	private String platCode;
	
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 订单状态:admin端用
	 */
	private JPOrderStatusConstant orderStatus;
		
	/**
	 * /异常订单为1，普通订单为0，默认为0；
	 */
	private String type;				
	
	private Double adjust;
	
	/**
	 * 平台政策价格 （自己平台加价部分）
	 */
	private Double platPolicyPirce;  
	private String paltPolicyId;
	
	/** 异常订单调整 金额 admin端用*/
	private Double adjustAmount;
	
	/**
	 * 异常订单调整凭证 admin端用
	 */
	private String voucherPicture;
	
	
	
	/**
	 * 异常订单调整原因 admin端用
	 */
	private String adjustReason;
	
	/**
	 * 工作时间
	 */

	private String workTime;
	
	/**
	 * 退票时间
	 */
	private String refundWorkTime;
	
	/**
	 * 航班废票截至时间
	 */
	private Date wastWorkTime;
	
	/** 易购退票订单号  */
	private String ygRefundOrderNo;
	
	/** 退废票平台订单号  */
	private String refundPlatformOrderNo;

	/** 
	 * 退废类型：详情参考 JPOrderStatus.CATEGORY_REFUND 
	 */
	private Integer backCategory;
	
	private String customerPaySerialNumber;
	
	private String platBackSerialNumber;
	
	private String platPaySerialNumber;
	
	private String suppBackSerialNumber;
	
	/**
	 * 进十差价
	 */
	private Double disparity;
	
	/**
	 * 支付宝手续费
	 */	
	private Double serviceCharge;
	
	/**
	 * T:退票单
	 * F:废票单
	 */
	private String refundType;
	
	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	
	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getDisparity() {
		return disparity;
	}

	public void setDisparity(Double disparity) {
		this.disparity = disparity;
	}

	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getYgOrderNo() {
		return ygOrderNo;
	}

	public void setYgOrderNo(String ygOrderNo) {
		this.ygOrderNo = ygOrderNo;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getAbeOrderId() {
		return abeOrderId;
	}

	public void setAbeOrderId(String abeOrderId) {
		this.abeOrderId = abeOrderId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String[] getTktNo() {
		return tktNo;
	}

	public void setTktNo(String[] tktNo) {
		this.tktNo = tktNo;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public List<FlightPassengerDTO> getPassangers() {
		return passangers;
	}

	public void setPassangers(List<FlightPassengerDTO> passangers) {
		this.passangers = passangers;
	}

	public Set<Passenger> getPassangerList() {
		return passangerList;
	}

	public void setPassangerList(Set<Passenger> passangerList) {
		this.passangerList = passangerList;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getFlightay() {
		return flightay;
	}

	public void setFlightay(String flightay) {
		this.flightay = flightay;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}

	public String getEndCityCode() {
		return endCityCode;
	}

	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
	}

	public String getEndCityName() {
		return endCityName;
	}

	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/*public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	 */
	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public Double getOneUserPayAmount() {
		if (null == oneUserPayAmount) {
			oneUserPayAmount = userPayAmount / passangerCount;
		}
		return oneUserPayAmount;
	}

	public void setOneUserPayAmount(Double oneUserPayAmount) {
		this.oneUserPayAmount = oneUserPayAmount;
	}

	public Double getOneTktAmount() {
		if (null == oneTktAmount ) {
			if (null != tktPrice) {
				oneTktAmount = tktPrice / passangerCount;
			} else {
				oneTktAmount = 0.0;
			}
			
		}
		return oneTktAmount;
	}

	public void setOneTktAmount(Double oneTktAmount) {
		this.oneTktAmount = oneTktAmount;
	}

	public Double getOneTktTax() {
		if (null == oneTktTax) {
			if (null != tktTax) {
				oneTktTax = tktTax / passangerCount;
			} else {
				oneTktTax = 0.0;
			}
			
		}
		return oneTktTax;
	}

	public void setOneTktTax(Double oneTktTax) {
		this.oneTktTax = oneTktTax;
	}

	public Double getOnePlatPolicyPirce() {
		if (null == onePlatPolicyPirce) {
			if (null != platPolicyPirce) {
				onePlatPolicyPirce = platPolicyPirce / passangerCount;
			} else {
				onePlatPolicyPirce = 0.0;
			}
		}
		return onePlatPolicyPirce;
	}

	public void setOnePlatPolicyPirce(Double onePlatPolicyPirce) {
		this.onePlatPolicyPirce = onePlatPolicyPirce;
	}

	public String getPaltPolicyId() {
		return paltPolicyId;
	}

	public void setPaltPolicyId(String paltPolicyId) {
		this.paltPolicyId = paltPolicyId;
	}

	public Double getTktAmount() {
		return tktAmount;
	}

	public void setTktAmount(Double tktAmount) {
		this.tktAmount = tktAmount;
	}

	public Double getTktPrice() {
		return tktPrice;
	}

	public void setTktPrice(Double tktPrice) {
		this.tktPrice = tktPrice;
	}

	public Double getTktTax() {
		return tktTax;
	}

	public void setTktTax(Double tktTax) {
		this.tktTax = tktTax;
	}

	public Double getCommRate() {
		return commRate;
	}

	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public Double getSuppPrice() {
		return suppPrice;
	}

	public void setSuppPrice(Double suppPrice) {
		this.suppPrice = suppPrice;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public SlfxFlightDTO getFlightDTO() {
		return flightDTO;
	}

	public void setFlightDTO(SlfxFlightDTO flightDTO) {
		this.flightDTO = flightDTO;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(String supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public String getFlightSnapshotJSON() {
		return flightSnapshotJSON;
	}

	public void setFlightSnapshotJSON(String flightSnapshotJSON) {
		this.flightSnapshotJSON = flightSnapshotJSON;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public JPOrderStatusConstant getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(JPOrderStatusConstant orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAdjust() {
		return adjust;
	}

	public void setAdjust(Double adjust) {
		this.adjust = adjust;
	}

	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}

	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundWorkTime() {
		return refundWorkTime;
	}

	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
	}

	public Date getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(Date wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public String getYgRefundOrderNo() {
		return ygRefundOrderNo;
	}

	public void setYgRefundOrderNo(String ygRefundOrderNo) {
		this.ygRefundOrderNo = ygRefundOrderNo;
	}

	public String getRefundPlatformOrderNo() {
		return refundPlatformOrderNo;
	}

	public void setRefundPlatformOrderNo(String refundPlatformOrderNo) {
		this.refundPlatformOrderNo = refundPlatformOrderNo;
	}

	public Integer getBackCategory() {
		return backCategory;
	}

	public void setBackCategory(Integer backCategory) {
		this.backCategory = backCategory;
	}

	public Integer getPassangerCount() {
		return passangerCount;
	}

	public void setPassangerCount(Integer passangerCount) {
		this.passangerCount = passangerCount;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getCustomerPaySerialNumber() {
		return customerPaySerialNumber;
	}

	public void setCustomerPaySerialNumber(String customerPaySerialNumber) {
		this.customerPaySerialNumber = customerPaySerialNumber;
	}

	public String getPlatBackSerialNumber() {
		return platBackSerialNumber;
	}

	public void setPlatBackSerialNumber(String platBackSerialNumber) {
		this.platBackSerialNumber = platBackSerialNumber;
	}

	public String getPlatPaySerialNumber() {
		return platPaySerialNumber;
	}

	public void setPlatPaySerialNumber(String platPaySerialNumber) {
		this.platPaySerialNumber = platPaySerialNumber;
	}

	public String getSuppBackSerialNumber() {
		return suppBackSerialNumber;
	}

	public void setSuppBackSerialNumber(String suppBackSerialNumber) {
		this.suppBackSerialNumber = suppBackSerialNumber;
	}
}