package pay.record.pojo.dto;


/***
 * 
 * @类功能说明：支付记录DTO
 * @类修改者：yaosanfeng
 * @修改日期：2015年12月3日15:24
 * @修改说明：增加属性
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月3日下午3:24:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class AirPayRecordDTO extends BasePayRecordDTO{
	/**
	 * 航空公司
	 */
	private String airCompName;
	
	/**
	 * 航班号 string ZH9814
	 */
	private String flightNo;
	
	/** 
	 * 舱位代码
	 */
	private String classCode;
	
	/**
	 * 舱位名称
	 */
	private String cabinName;
	
	/**
	 * 订单pnr
	 */
	private String pnr;

	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
}
