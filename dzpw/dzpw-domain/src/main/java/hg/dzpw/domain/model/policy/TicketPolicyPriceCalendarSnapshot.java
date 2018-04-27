package hg.dzpw.domain.model.policy;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.dealer.Dealer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：门票政策价格日历快照
 * @类修改者：
 * @修改日期：2015-3-3下午4:03:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-3下午4:03:12
 */
@Entity
@Table(name = M.TABLE_PREFIX + "PRICE_CALENDAR_SNAPSHOT")
public class TicketPolicyPriceCalendarSnapshot extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属门票政策快照（价格日历只和单票政策关联）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_SNAPSHOT_ID")
	private TicketPolicySnapshot ticketPolicySnapshot;

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
	 * @方法功能说明：创建门票政策价格日历快照
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-6上午10:25:37
	 * @修改内容：
	 * @参数：@param calendar				价格日历
	 * @参数：@param policySnapshot		门票政策快照
	 * @return:void
	 * @throws
	 */
	public void createSnapshot(TicketPolicyPriceCalendar calendar, TicketPolicySnapshot policySnapshot) {
		setId(UUIDGenerator.getUUID());
		setTicketPolicySnapshot(policySnapshot);
		setStandardPrice(calendar.getStandardPrice());
		setPriceJson(calendar.getPriceJson());
		setDealer(calendar.getDealer());
	}

	public TicketPolicySnapshot getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(
			TicketPolicySnapshot ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
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
