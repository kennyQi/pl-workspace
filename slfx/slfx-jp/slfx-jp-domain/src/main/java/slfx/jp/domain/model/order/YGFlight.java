package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：易购航班信息 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年7月31日下午4:29:12
 * @版本：V1.0
 *
 */
public class YGFlight extends BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 航空公司代码
	 */
	private String carrier;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 离港机场代码
	 */
	private String boardPoint;
	
	/**
	 * 到港机场代码
	 */
	private String offPoint;
	
	/**
	 * 舱位代码
	 */
	private String classCode;
	
	/**
	 * 离港日期
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
