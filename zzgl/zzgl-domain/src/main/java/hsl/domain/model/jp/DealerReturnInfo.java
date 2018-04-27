package hsl.domain.model.jp;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class DealerReturnInfo {
	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 总支付金额
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPrice;
	
	/**
	 * 单张票价
	 */
	@Column(name = "TICK_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double tickPrice;

	/**
	 * 经销商返回的备注
	 */
	@Column(name = "MEMO", columnDefinition = M.TEXT_COLUM)
	private String memo;

	/**
	 * 经销商拒绝返回的备注
	 */
	@Column(name = "REFUSE_MEMO", columnDefinition = M.TEXT_COLUM)
	private String refuseMemo;
	/**
	 * 退费手续费
	 */
	@Column(name = "PROCEDURE_FEE", columnDefinition = M.DOUBLE_COLUM)
	private Double procedureFee;
	
	/**
	 * 政策ID
	 */
	@Column(name = "PLCID", columnDefinition=M.NUM_COLUM)
	private int plcid;
	
	/**
	 * 票号类型B2B
	 */
	public static final Integer JP_ORDER_TICKTYPE_B2B=1;
	/**
	 * 票号类型BSP
	 */
	public static final Integer JP_ORDER_TICKTYPE_BSP=2;
	/**
	 * 票号类型All
	 */
	public static final Integer JP_ORDER_TICKTYPE_ALL=3;
	/**
	 * 票号类型
	 * 1--B2B，2—BSP，3—所有
	 */
	@Column(name = "TICK_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer tickType;

	 /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
	@Column(name = "PAYPLATFORM",columnDefinition=M.NUM_COLUM)
    private Integer  payPlatform;
	
	public Double getTickPrice() {
		return tickPrice;
	}

	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public Integer getTickType() {
		return tickType;
	}

	public void setTickType(Integer tickType) {
		this.tickType = tickType;
	}

	public Double getProcedureFee() {
		return procedureFee;
	}

	public void setProcedureFee(Double procedureFee) {
		this.procedureFee = procedureFee;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}
	
}
