package slfx.jp.command.client;

import hg.common.component.BaseCommand;

import java.util.List;

import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.order.JPOrderUserInfoDTO;

/**
 * 
 * @类功能说明：平台创建订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:05:52
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SPIJPOrderCreateCommand extends BaseCommand {

	
	private String fromClientKey;
	
	/**
	 * 来源标识：0 mobile , 1  pc
	 * 经销商所属用户KEY(规则 = 来源标识_key)
	 */
	private String clientUserKey;
	
	/**
	 * 商城订单号
	 */
	private String dealerOrderId;
	/**
	 * 订单支付总价
	 */
	private Double balanceMoney;

	/** 航班号 */
	private String flightNo;
	
	
	/** 起飞时间yyyy-MM-dd或者yyyy/MM/dd */
	private String startDateStr;

	/** 舱位代码 */
	private String classCode;
	
	/**
	 * 国内/国际(D/I) 
	 */
	private String isDomc;
	/**
	 *  舱位价格   必填
	 */
	private Double classPrice;
	
	/**
	 * 航空公司大编码
	 */
	private String bigPNR;
	
	/**
	 * 政策编号
	 */
	private String policyId;
	
	/**
	 * 出票平台（001）
	 */
	private String platCode;
	
	/**
	 * P:自有平台
	 * Y、A、S: 快捷采购
	 */
	private String platformType;
	
	/**
	 * 是否是平台VIP帐号
	 */
	private String isVip;
	
	/**
	 * 账号级别
	 */
	private String accountLevel;
	
	/**
	 * 下单人
	 */
	private String userId;

	/**
	 * 出票时限日期
	 */
	private String ticketLimitDate;
	
	/**
	 * 出票时限时间
	 */
	private String ticketLimitTime;

	/** 订单乘客信息 */
	private List<FlightPassengerDTO> passangers;
	
	/** 联系人信息 */
	private JPOrderUserInfoDTO linker;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	
	
	
	/**
	 * 便于操作日志记录的字段
	 * 
	 */
	
	private String abeOrderId;
	
	private String comparePocilyId;
	
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
	
	
	public SPIJPOrderCreateCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIsDomc() {
		return isDomc;
	}
	public Double getClassPrice() {
		return classPrice;
	}

	public void setClassPrice(Double classPrice) {
		this.classPrice = classPrice;
	}
	public void setIsDomc(String isDomc) {
		this.isDomc = isDomc;
	}
	
	public String getBigPNR() {
		return bigPNR;
	}
	public void setBigPNR(String bigPNR) {
		this.bigPNR = bigPNR;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getTicketLimitDate() {
		return ticketLimitDate;
	}

	public void setTicketLimitDate(String ticketLimitDate) {
		this.ticketLimitDate = ticketLimitDate;
	}

	public String getTicketLimitTime() {
		return ticketLimitTime;
	}

	public void setTicketLimitTime(String ticketLimitTime) {
		this.ticketLimitTime = ticketLimitTime;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
