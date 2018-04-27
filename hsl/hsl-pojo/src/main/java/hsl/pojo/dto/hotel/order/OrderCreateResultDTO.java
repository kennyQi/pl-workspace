package hsl.pojo.dto.hotel.order;

import hsl.pojo.util.enumConstants.EnumCurrencyCode;

import java.io.Serializable;
import java.math.BigDecimal;


@SuppressWarnings("serial")
public class OrderCreateResultDTO implements Serializable{
	/**
	 * 订单编号
	 */
    protected long orderId;
    /**
     * 最晚取消时间
     */
    protected java.util.Date cancelTime;
    /**
     * 违约赔付金额
     */
    protected BigDecimal guaranteeAmount;
    /**
     * 货币类型
     */
    protected EnumCurrencyCode currencyCode;
    
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public java.util.Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(java.util.Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	public EnumCurrencyCode getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(EnumCurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}
    
}
