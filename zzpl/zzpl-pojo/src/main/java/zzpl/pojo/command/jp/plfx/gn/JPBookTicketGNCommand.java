package zzpl.pojo.command.jp.plfx.gn;

import java.util.Date;

import hg.common.component.BaseCommand;
import zzpl.pojo.dto.jp.plfx.gn.PassengerInfoGNDTO;

/****
 * 
 * @类功能说明：生成订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日上午9:41:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPBookTicketGNCommand extends BaseCommand {

	
	/**
	 * 加密字符串
	 * 来自于QueryAirpolicy的结果
	 */
	private String encryptString;
	
	/***
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	/****
	 * 舱位代码
	 */
	private String cabinCode;
	
	/***
	 * 舱位名称
	 */
	private String cabinName;
	
	/***
	 * 乘客信息对象 
	 */
	private PassengerInfoGNDTO passengerInfoGNDTO;
		
	//-----------查缓存需要用到------------------------------
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 起飞日期 格式：2012-05-10
	 */
	private Date startDate;
	
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getCabinCode() {
		return cabinCode;
	}
	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}
	public String getCabinName() {
		return cabinName;
	}
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public PassengerInfoGNDTO getPassengerInfoGNDTO() {
		return passengerInfoGNDTO;
	}

	public void setPassengerInfoGNDTO(PassengerInfoGNDTO passengerInfoGNDTO) {
		this.passengerInfoGNDTO = passengerInfoGNDTO;
	}

	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	
	
}
