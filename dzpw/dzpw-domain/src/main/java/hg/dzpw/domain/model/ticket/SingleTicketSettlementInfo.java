package hg.dzpw.domain.model.ticket;

import hg.dzpw.domain.model.M;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：单票与商户（景区）结算信息
 * @类修改者：
 * @修改日期：2015-5-4下午6:03:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-4下午6:03:36
 */
@Embeddable
@SuppressWarnings("serial")
public class SingleTicketSettlementInfo implements Serializable {

	/**
	 * 结算时间
	 */
	@Column(name = "SETTLEMENT_DATE", columnDefinition = M.DATE_COLUM)
	private Date settlementDate;
	
	/**
	 * 景区手续费(累计金额， 核销入园时 加上一笔手续费)
	 */
	@Column(name = "SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double settlementFee = 0d;
	
	/**
	 * 与经销商结算价格
	 */
	@Column(name = "DEALER_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double dealerPrice;


	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}
	
	public Double getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(Double dealerPrice) {
		this.dealerPrice = dealerPrice;
	}
}
