package slfx.jp.command.client;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：易购航程信息command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:07:26
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGFlightCommand extends BaseCommand {


	/**
	 * 航空公司代码：必填
	 */
	private String carrier;
	
	/**
	 * 航班号：必填
	 */
	private String flightNo;
	
	/**
	 * 离港机场代码：必填
	 */
	private String boardPoint;
	
	/**
	 * 到港机场代码：必填
	 */
	private String offPoint;
	
	/**
	 * 舱位代码：必填
	 */
	private String classCode;
	
	/**
	 * 离港日期：必填
	 */
	private String departureDate;

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

	public String getBoardPoint() {
		return boardPoint;
	}

	public void setBoardPoint(String boardPoint) {
		this.boardPoint = boardPoint;
	}

	public String getOffPoint() {
		return offPoint;
	}

	public void setOffPoint(String offPoint) {
		this.offPoint = offPoint;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	
}
