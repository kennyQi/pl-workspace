package zzpl.pojo.dto.jp.plfx.gn;

import zzpl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class PriceGNDTO extends BaseDTO{
	
	 /**
     * 票号类型   
     * 1--B2B，2--BSP
     */
    private int  tickType;
   
    /**
     * 票面价
     */
    private Double  ibePrice;
    /**
     * 机场建设费
     */
    private Double  buildFee;
    /**
     * 	燃油税
     */
    private Double  oilFee;
    /**
     * 政策id号
     */
    private int  plcid;
   
    /**
     * 备注
     */
    private String  memo;
    /**
     * 订单总支付价格((单张票面价 + 机建燃油) * 数量（向上取整）)
     */
    private Double  totalPrice;
    /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payType;
    /**
     * 单张票价
     */
    private Double tickPrice;
    /**
	 * 平台总的加价政策金额
	 */
	private Double platPolicyPirce;
	
	private Double platTotalPirce;
    
	public Double getTickPrice() {
		return tickPrice;
	}
	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}
	public Double getPlatTotalPirce() {
		return platTotalPirce;
	}
	public void setPlatTotalPirce(Double platTotalPirce) {
		this.platTotalPirce = platTotalPirce;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public int getTickType() {
		return tickType;
	}
	public void setTickType(int tickType) {
		this.tickType = tickType;
	}
	public Double getIbePrice() {
		return ibePrice;
	}
	public void setIbePrice(Double ibePrice) {
		this.ibePrice = ibePrice;
	}
	public Double getBuildFee() {
		return buildFee;
	}
	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}
	public Double getOilFee() {
		return oilFee;
	}
	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}
	public int getPlcid() {
		return plcid;
	}
	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}
	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}
}