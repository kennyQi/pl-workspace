package slfx.jp.command.admin.jp;

import hg.common.component.BaseCommand;

import java.util.List;

import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.order.JPOrderUserInfoDTO;

/**
 * 
 * @类功能说明：下单COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午5:06:24
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderCreateSpiCommand extends BaseCommand {

	/**
	 * 商城订单号
	 */
	private String dealerOrderId;
	
	/**
	 * 订单支付总价
	 */
	private Double totalPrice;

	/** 
	 * 航班号 
	 */
	private String flightNo;
	
	/**
	 * 航班日期 (例如：2008-03-16)
	 */
	private String date;
	
	/** 
	 * 舱位代码 
	 */
	private String classCode;
	
	/**
	 * 政策编号
	 */
	private String policyId;

	/** 
	 * 订单乘客信息 
	 */
	private List<FlightPassengerDTO> passangers;
	
	/** 
	 * 联系人信息 
	 */
	private JPOrderUserInfoDTO linker;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 航空公司大编码
	 */
	private String bigPNR;

	//20150123 caizhenghao  start	
	/** 起飞时间yyyy-MM-dd或者yyyy/MM/dd */
	private String startDateStr;

	private String fromClientKey;

	private String abeOrderId;
	
	private String comparePocilyId;
	/**
	 * 出票平台（001）
	 */
	private String platCode;
	
	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public List<FlightPassengerDTO> getPassangers() {
		return passangers;
	}

	public void setPassangers(List<FlightPassengerDTO> passangers) {
		this.passangers = passangers;
	}

	public JPOrderUserInfoDTO getLinker() {
		return linker;
	}

	public void setLinker(JPOrderUserInfoDTO linker) {
		this.linker = linker;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBigPNR() {
		return bigPNR;
	}

	public void setBigPNR(String bigPNR) {
		this.bigPNR = bigPNR;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getFromClientKey() {
		return fromClientKey;
	}

	public void setFromClientKey(String fromClientKey) {
		this.fromClientKey = fromClientKey;
	}

	public String getAbeOrderId() {
		return abeOrderId;
	}

	public void setAbeOrderId(String abeOrderId) {
		this.abeOrderId = abeOrderId;
	}

	public String getComparePocilyId() {
		return comparePocilyId;
	}

	public void setComparePocilyId(String comparePocilyId) {
		this.comparePocilyId = comparePocilyId;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	
}
