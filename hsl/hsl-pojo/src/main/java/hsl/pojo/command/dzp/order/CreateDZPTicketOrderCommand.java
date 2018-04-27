package hsl.pojo.command.dzp.order;

import hg.common.component.BaseCommand;
import hsl.pojo.util.HSLConstants;

import java.util.Date;
import java.util.List;

/**
 * 创建电子票订单
 *
 * @author zhurz
 * @since 1.8
 */
@SuppressWarnings("serial")
public class CreateDZPTicketOrderCommand extends BaseCommand implements HSLConstants.FromType {

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 门票政策版本
	 */
	private Integer ticketPolicyVersion;

	/**
	 * 游玩日期（景区门票必传，联票不需要）
	 */
	private Date travelDate;

	/**
	 * 预定人姓名
	 */
	private String linkMan;

	/**
	 * 预定人手机号
	 */
	private String linkMobile;

	/**
	 * 用户的支付宝帐号（理财收益用，联票必传）
	 */
	private String alipayAccount;

	/**
	 * 游玩人
	 */
	private List<Tourist> tourists;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 默认为PC
	 *
	 * @see HSLConstants.FromType
	 */
	private Integer fromType = FROM_TYPE_PC;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Integer getTicketPolicyVersion() {
		return ticketPolicyVersion;
	}

	public void setTicketPolicyVersion(Integer ticketPolicyVersion) {
		this.ticketPolicyVersion = ticketPolicyVersion;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public List<Tourist> getTourists() {
		return tourists;
	}

	public void setTourists(List<Tourist> tourists) {
		this.tourists = tourists;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	/**
	 * 游玩人
	 */
	public static class Tourist {
		/**
		 * 姓名
		 */
		private String name;
		/**
		 * 身份证号
		 */
		private String idNo;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}
	}
}
