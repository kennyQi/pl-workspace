package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientCommand;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @类功能说明：创建门票订单
 * @类修改者：
 * @修改日期：2014-11-24上午11:09:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午11:09:57
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.CreateTicketOrder)
public class CreateTicketOrderCommand extends BaseClientCommand {

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderId;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;
	
	/**
	 * 门票政策版本
	 */
	private Integer ticketPolicyVersion;

	/**
	 * 游玩日期（独立单票必传）
	 */
	private Date travelDate;

	/**
	 * 游客
	 */
	private List<TouristDTO> tourists;
	
	/**
	 * 购买数量
	 */
	private Integer buyNum;

	/**
	 * 订单金额
	 */
	private Double price;
	
	/**
	 * 预订人姓名
	 */
	private String bookMan;
	/**
	 * 预订人手机号码
	 */
	private String bookManMobile;
	
	/**
	 * 预定人支付宝账号  （用于退款）
	 */
	private String bookManAliPayAccount;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public List<TouristDTO> getTourists() {
		if (tourists == null)
			tourists = new ArrayList<TouristDTO>();
		return tourists;
	}

	public void setTourists(List<TouristDTO> tourists) {
		this.tourists = tourists;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Integer getTicketPolicyVersion() {
		return ticketPolicyVersion;
	}

	public void setTicketPolicyVersion(Integer ticketPolicyVersion) {
		this.ticketPolicyVersion = ticketPolicyVersion;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

	public String getBookManMobile() {
		return bookManMobile;
	}

	public void setBookManMobile(String bookManMobile) {
		this.bookManMobile = bookManMobile;
	}

	public String getBookManAliPayAccount() {
		return bookManAliPayAccount;
	}

	public void setBookManAliPayAccount(String bookManAliPayAccount) {
		this.bookManAliPayAccount = bookManAliPayAccount;
	}

}
