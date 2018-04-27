package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;

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
     * 订单总支付价格
     */
    private Double  totalPrice;
    
    /**
     * 单张票价
     */
    private Double tickPrice;
    /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payType;
    
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
	
	
	public Double getTickPrice() {
		return tickPrice;
	}
	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
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
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
    
    
    
	
}
