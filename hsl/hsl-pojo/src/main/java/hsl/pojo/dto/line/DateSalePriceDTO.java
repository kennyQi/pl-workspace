package hsl.pojo.dto.line;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;

public class DateSalePriceDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**
	 * 属于哪天
	 */
	private Date saleDate;
	
	/**
	 * 属于那一天(字符串格式)
	 */
	private String saleDateStr;

	/**
	 * 成人价
	 */
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	private Double childPrice;

	/**
	 * 剩余人数
	 */
	private Integer number;

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSaleDateStr() {
		return saleDateStr;
	}

	public void setSaleDateStr(String saleDateStr) {
		this.saleDateStr = saleDateStr;
	}

}
