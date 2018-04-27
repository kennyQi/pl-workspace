package hsl.pojo.command.jp.plfx;
import hg.common.component.BaseCommand;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.dto.jp.PassengerGNDTO;
import hsl.pojo.dto.jp.PassengerInfoGNDTO;
import java.util.Date;
import java.util.List;

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

	/****
	 * 舱位代码
	 */
	private String cabinCode;

	/***
	 * 舱位名称
	 */
	private String cabinName;

	public JPBookTicketGNCommand(){
	}
	/**
	 * 将本地预定机票转化为 分销需要的 Command
	 * @类名：JPBookTicketGNCommand.java
	 * @@param command
	 */
	public JPBookTicketGNCommand(BookGNFlightCommand command){
		this.encryptString=command.getEncryptString();
		this.dealerOrderId=command.getOrderNO();
		PassengerInfoGNDTO passengerInfoGNDTO=new PassengerInfoGNDTO();
		passengerInfoGNDTO.setName(command.getLinkName());
		passengerInfoGNDTO.setTelephone(command.getLinkTelephone());
		List<PassengerGNDTO> passengerGNDTOs=command.getPassengers();
		passengerInfoGNDTO.setPassengerList(passengerGNDTOs);
		this.setFlightNo(command.getFlightNO());
		this.setStartDate(command.getStartTime());
		this.setPassengerInfoGNDTO(passengerInfoGNDTO);
		this.setCabinName(command.getCabinName());
		this.setCabinCode(command.getCabinCode());
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
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


}
