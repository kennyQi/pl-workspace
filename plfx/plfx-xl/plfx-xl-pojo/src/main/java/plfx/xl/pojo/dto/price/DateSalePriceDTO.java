package plfx.xl.pojo.dto.price;

import java.util.Date;

import plfx.xl.pojo.dto.BaseXlDTO;
import plfx.xl.pojo.dto.line.LineDTO;

/**
 *@类功能说明：价格日历中的一天DTO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月10日上午10:28:38
 */
@SuppressWarnings("serial")
public class DateSalePriceDTO extends BaseXlDTO{
	
	
	/**
	 * 属于哪个线路
	 */
	private LineDTO line;

	/**
	 * 属于哪天
	 */
	private Date saleDate;

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


	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

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

	
	
	

}
