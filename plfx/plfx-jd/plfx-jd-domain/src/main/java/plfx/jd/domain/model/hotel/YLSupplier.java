package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "YLSUPPLIER")
public class YLSupplier extends BaseModel {

	/***
	 * 供应商ID
	 */
	@Column(name = "SUPPLIERID")
	private String supplierId;

	/***
	 * 有效状态
	 */
	@Column(name = "STATUS")
	private String status;
	/***
	 * 酒店编码
	 */
	@Column(name = "HOTELCODE")
	private String hotelCode;
	/***
	 * 星期开始设置
	 */
	@Column(name = "WEEKENDSTART")
	private String weekendStart;
	
	/***
	 * 星期结束设置
	 */
	@Column(name = "WEEKENDENDS")
	private String weekendEnd;

	/***
	 * 提示
	 */
	@Embedded
	private YLHelpfulTips helpfulTips;
    /***
     * 特殊政策
     */
	@Embedded
	private YLAvailPolicy availPolicy;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "YL_HOTEL_ID")
	private YLHotel ylHotel;


	public String getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getHotelCode() {
		return hotelCode;
	}


	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}


	public String getWeekendStart() {
		return weekendStart;
	}


	public void setWeekendStart(String weekendStart) {
		this.weekendStart = weekendStart;
	}


	public String getWeekendEnd() {
		return weekendEnd;
	}


	public void setWeekendEnd(String weekendEnd) {
		this.weekendEnd = weekendEnd;
	}


	public YLHelpfulTips getHelpfulTips() {
		return helpfulTips;
	}


	public void setHelpfulTips(YLHelpfulTips helpfulTips) {
		this.helpfulTips = helpfulTips;
	}


	public YLAvailPolicy getAvailPolicy() {
		return availPolicy;
	}


	public void setAvailPolicy(YLAvailPolicy availPolicy) {
		this.availPolicy = availPolicy;
	}


	public YLHotel getYlHotel() {
		return ylHotel;
	}


	public void setYlHotel(YLHotel ylHotel) {
		this.ylHotel = ylHotel;
	}



	

}
