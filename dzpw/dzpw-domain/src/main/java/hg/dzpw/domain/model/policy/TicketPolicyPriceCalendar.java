package hg.dzpw.domain.model.policy;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.pojo.command.merchant.policy.DatePrice;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotSetSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformSetGroupTicketPolicyPriceCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：门票政策价格日历
 * @类修改者：
 * @修改日期：2015-3-3下午4:03:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-3下午4:03:12
 */
@Entity
@Table(name = M.TABLE_PREFIX + "PRICE_CALENDAR")
public class TicketPolicyPriceCalendar extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属门票政策（价格日历只和单票政策关联）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_ID")
	private TicketPolicy ticketPolicy;

	/**
	 * 是否为门票基准价，与经销商订的价格都为false
	 */
	@Type(type = "yes_no")
	@Column(name = "STANDARD_PRICE")
	private Boolean standardPrice = false;

	/**
	 * 对应经销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEALER_ID")
	private Dealer dealer;

	/**
	 * 每日价格JSON
	 */
	@Column(name = "PRICE_JSON", columnDefinition = M.TEXT_COLUM)
	private String priceJson;

	/**
	 * 转化priceJson
	 */
	@Transient
	private List<TicketPolicyDatePrice> prices;

	/**
	 * @throws DZPWException 
	 * @方法功能说明：设置价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-3下午5:29:46
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotSetSingleTicketPolicyPrice(
			ScenicspotSetSingleTicketPolicyPriceCommand command,
			TicketPolicy ticketPolicy, Dealer dealer) throws DZPWException {

		if (ticketPolicy == null)
			throw new DZPWException(DZPWException.TICKET_POLICY_IS_NULL,
					"缺少门票政策");

		if (!command.getStandardPrice() && dealer == null)
			throw new DZPWException(DZPWException.DEALER_NOT_EXISTS, "缺少经销商");

		if (getId() == null)
			if (command.getStandardPrice())
				// 基准价的价格日历ID与门票政策相同
				setId(generateId(ticketPolicy.getId(), null));
			else
				// 针对经销商的价格日历ID为门票政策ID+经销商ID的MD5
				setId(generateId(ticketPolicy.getId(), dealer.getId()));

		setDealer(dealer);
		setTicketPolicy(ticketPolicy);
		setStandardPrice(command.getStandardPrice());

		prices = new ArrayList<TicketPolicyDatePrice>();
		List<DatePrice> datePrices = command.getDatePrices();

		for (DatePrice dp : datePrices)
			prices.add(new TicketPolicyDatePrice(dp.getDate(), dp.getPrice(), dp.getOpen()));

		setPriceJson(JSON.toJSONString(prices));
	}
	
	/**
	 * @方法功能说明：生成ID
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午2:38:51
	 * @修改内容：
	 * @参数：@param ticketPolicyId
	 * @参数：@param dealerId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String generateId(String ticketPolicyId, String dealerId) {
		if (StringUtils.isBlank(dealerId))
			return ticketPolicyId;
		return DigestUtils.md5Hex(ticketPolicyId + dealerId);
	}

	/**
	 * @方法功能说明：运营端设置价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午4:48:07
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param ticketPolicy
	 * @参数：@param dealer
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void platformSetGroupTicketPolicyPrice(
			PlatformSetGroupTicketPolicyPriceCommand command,
			TicketPolicy ticketPolicy, Dealer dealer) throws DZPWException {

		if (ticketPolicy == null)
			throw new DZPWException(DZPWException.TICKET_POLICY_IS_NULL,
					"缺少门票政策");

		if (dealer == null)
			throw new DZPWException(DZPWException.DEALER_NOT_EXISTS, "缺少经销商");

		if (getId() == null)
			setId(DigestUtils.md5Hex(ticketPolicy.getId() + dealer.getId()));

		setDealer(dealer);
		setTicketPolicy(ticketPolicy);
		setStandardPrice(false);

		prices = new ArrayList<TicketPolicyDatePrice>();
		List<hg.dzpw.pojo.command.platform.policy.DatePrice> datePrices = command.getDatePrices();

		for (hg.dzpw.pojo.command.platform.policy.DatePrice dp : datePrices)
			prices.add(new TicketPolicyDatePrice(dp.getDate(), dp.getPrice(), dp.getOpen()));

		setPriceJson(JSON.toJSONString(prices));
	}

	public TicketPolicy getTicketPolicy() {
		return ticketPolicy;
	}

	public void setTicketPolicy(TicketPolicy ticketPolicy) {
		this.ticketPolicy = ticketPolicy;
	}

	public Boolean getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Boolean standardPrice) {
		this.standardPrice = standardPrice;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getPriceJson() {
		return priceJson;
	}

	public void setPriceJson(String priceJson) {
		this.priceJson = priceJson;
	}

	public List<TicketPolicyDatePrice> getPrices() {
		if (prices == null && StringUtils.isNotBlank(priceJson))
			try {
				prices = JSON.parseArray(priceJson, TicketPolicyDatePrice.class);
			} catch (Exception e) {
				prices = new ArrayList<TicketPolicyDatePrice>();
			}
		return prices;
	}
	
	public void setPrices(List<TicketPolicyDatePrice> prices) {
		this.prices = prices;
	}

}
