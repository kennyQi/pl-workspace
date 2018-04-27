package hsl.pojo.dto.jp;
public class DealerReturnInfoDTO {
	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 总支付金额
	 */
	private Double totalPrice;
	
	/**
	 * 单张票价
	 */
	private Double tickPrice;

	/**
	 * 经销商返回的备注
	 */
	private String memo;

	/**
	 * 经销商返回的备注
	 */
	private String refuseMemo;
	
	/**
	 * 政策ID
	 */
	private String plcid;

	/**
	 * 票号类型
	 */
	private Integer tickType;
	/**
	 * 退费手续费
	 */
	private Double procedureFee;

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

	public String getPlcid() {
		return plcid;
	}

	public Double getTickPrice() {
		return tickPrice;
	}

	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}

	public void setPlcid(String plcid) {
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
	
}
