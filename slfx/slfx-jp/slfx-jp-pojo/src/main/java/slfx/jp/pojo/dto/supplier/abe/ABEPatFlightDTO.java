package slfx.jp.pojo.dto.supplier.abe;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：WebABE PAT 报价DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月19日下午3:31:12
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ABEPatFlightDTO extends BaseJpDTO{
	
   /** 运价序号 */
   private String fnno;
   
   /** 乘客类型 */
   private String passangerType;
   
   /** 票面价 */
   private Double facePar;
   
   /** 税款合计 */
   private Double taxAmount;
   
   /** 燃油费 */
   private Double fuelSurTax;
   
   /** 机场建设费 */
   private Double airportTax;
   
   /** 票价总额 */
   private Double amount;

	public String getFnno() {
		return fnno;
	}
	
	public void setFnno(String fnno) {
		this.fnno = fnno;
	}
	
	public String getPassangerType() {
		return passangerType;
	}
	
	public void setPassangerType(String passangerType) {
		this.passangerType = passangerType;
	}

	public Double getFacePar() {
		return facePar;
	}

	public void setFacePar(Double facePar) {
		this.facePar = facePar;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}
	
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	public Double getFuelSurTax() {
		return fuelSurTax;
	}
	
	public void setFuelSurTax(Double fuelSurTax) {
		this.fuelSurTax = fuelSurTax;
	}
	
	public Double getAirportTax() {
		return airportTax;
	}
	
	public void setAirportTax(Double airportTax) {
		this.airportTax = airportTax;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
   
}