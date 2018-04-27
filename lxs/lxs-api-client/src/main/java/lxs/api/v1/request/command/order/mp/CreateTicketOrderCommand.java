package lxs.api.v1.request.command.order.mp;

import java.util.Date;
import java.util.List;

import lxs.api.base.ApiPayload;
import lxs.api.v1.dto.mp.TouristDTO;


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
public class CreateTicketOrderCommand extends ApiPayload {

	/**
	 * 登录人ID
	 */
	private String userID;
	
	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;
	
	/**
	 * 游玩日期（独立单票必传）
	 */
	private Date travelDate;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	
	/**
	 * 游客
	 */
	private List<TouristDTO> tourists;
	
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public List<TouristDTO> getTourists() {
		return tourists;
	}

	public void setTourists(List<TouristDTO> tourists) {
		this.tourists = tourists;
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
