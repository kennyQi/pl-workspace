package hsl.api.v1.request.command.jp;

import hsl.api.base.ApiPayload;
import hsl.pojo.dto.jp.FlightPassangerDto;
import hsl.pojo.dto.user.UserDTO;

import java.util.List;

@SuppressWarnings("serial")
public class JPOrderCreateCommand extends ApiPayload {

	/**
	 * 订单支付总价
	 */
	private Double totalPrice;

	/** 航班号 */
	private String flightNo;
	
	/**
	 * 航班日期 (例如：2008-03-16)
	 */
	private String date;

	/** 舱位代码 */
	private String classCode;
	
	/** 政策id */
	private String policyId;

	/** 订单乘客信息 */
	private List<FlightPassangerDto> passangers;

	/** 联系人信息 */
	private UserDTO linker;
	
	/** 支付方式  1：支付宝， */
	private Integer payType;
	
	
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

	public List<FlightPassangerDto> getPassangers() {
		return passangers;
	}

	public void setPassangers(List<FlightPassangerDto> passangers) {
		this.passangers = passangers;
	}

	public UserDTO getLinker() {
		return linker;
	}

	public void setLinker(UserDTO linker) {
		this.linker = linker;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

}
