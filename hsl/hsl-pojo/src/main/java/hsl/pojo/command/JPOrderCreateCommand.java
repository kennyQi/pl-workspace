package hsl.pojo.command;

import java.util.List;

import hg.common.component.BaseCommand;
import hsl.pojo.dto.jp.FlightPassangerDto;
import hsl.pojo.dto.jp.JPOrderUserDTO;
/**
 * 
 * @类功能说明：下单COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月9日下午8:58:00
 *
 */
@SuppressWarnings("serial")
public class JPOrderCreateCommand  extends  BaseCommand{
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
	private List<FlightPassangerDto> passangers;
	
	/** 
	 * 联系人信息 
	 */
	private JPOrderUserDTO linker;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 航空公司大编码
	 */
	private String bigPNR;
	
	/**
	 * 经销商ID
	 */
	private String fromClientKey;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 经销商所属用户KEY(规则 = 来源标识_key)
	 */
	private String clientUserKey;

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

	public List<FlightPassangerDto> getPassangers() {
		return passangers;
	}

	public void setPassangers(List<FlightPassangerDto> passangers) {
		this.passangers = passangers;
	}

	public JPOrderUserDTO getLinker() {
		return linker;
	}

	public void setLinker(JPOrderUserDTO linker) {
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

	public String getFromClientKey() {
		return fromClientKey;
	}

	public void setFromClientKey(String fromClientKey) {
		this.fromClientKey = fromClientKey;
	}

	public String getClientUserKey() {
		return clientUserKey;
	}

	public void setClientUserKey(String clientUserKey) {
		this.clientUserKey = clientUserKey;
	}
}
