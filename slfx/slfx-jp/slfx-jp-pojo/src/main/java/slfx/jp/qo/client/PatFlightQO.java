package slfx.jp.qo.client;

import slfx.jp.pojo.dto.flight.SlfxFlightDTO;

/**
 * 
 * @类功能说明：易购验价QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:16:12
 * @版本：V1.0
 *
 */
public class PatFlightQO {
	
	public PatFlightQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PatFlightQO(SlfxFlightDTO dto) {
		this.startPort=dto.getStartPort();
		this.endPort=dto.getEndPort();
		this.carrier=dto.getCarrier();
		this.flightNo=dto.getFlightNo();
		this.startDate=dto.getStartDate();
		this.startTime=dto.getStartTime();
	}

	/**
	 * 起飞机场代码  必填
	 */
	private String startPort;
	 
	/**
	 * 目的机场代码  必填
	 */
	private String endPort;
	
	/**
	 * 航空公司二字码  必填
	 */
	private String carrier;
	
	/**
	 * 航班号  必填
	 */
	private String flightNo;
	
	/**
	 * 起飞日期  必填    格式：2014-06-05
	 */
	private String startDate;
	
	/**
	 * 起飞时间  必填  格式：09:30
	 */
	private String startTime;
	
	/**
	 * 舱位代码  必填
	 */
	private String classCode;

	public String getStartPort() {
		return startPort;
	}

	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}

	public String getEndPort() {
		return endPort;
	}

	public void setEndPort(String endPort) {
		this.endPort = endPort;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
}
