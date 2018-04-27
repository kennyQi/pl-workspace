package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseNightlyRateDTO implements Serializable{
	/**
	 * 时间
	 */
	protected java.util.Date date;
	/**
	 * 价格
	 */
	protected BigDecimal rate;

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
