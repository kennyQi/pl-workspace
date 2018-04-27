package plfx.jd.pojo.dto.plfx.hotel;

import hg.common.component.BaseModel;



@SuppressWarnings("serial")
public class YLSupplierDTO extends BaseModel {

	/***
	 * 供应商ID
	 */

	private String supplierId;

	/***
	 * 有效状态
	 */

	private String status;
	/***
	 * 酒店编码
	 */

	private String hotelCode;
	/***
	 * 星期开始设置
	 */

	private String weekendStart;
	
	/***
	 * 星期结束设置
	 */

	private String weekendEnd;

	/***
	 * 提示
	 */

	private YLHelpfulTipsDTO helpfulTipsDTO;
    /***
     * 特殊政策
     */

	private YLAvailPolicyDTO availPolicyDTO;


	private YLHotelDTO ylHotelDTO;


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


	public YLHelpfulTipsDTO getHelpfulTipsDTO() {
		return helpfulTipsDTO;
	}


	public void setHelpfulTipsDTO(YLHelpfulTipsDTO helpfulTipsDTO) {
		this.helpfulTipsDTO = helpfulTipsDTO;
	}


	public YLAvailPolicyDTO getAvailPolicyDTO() {
		return availPolicyDTO;
	}


	public void setAvailPolicyDTO(YLAvailPolicyDTO availPolicyDTO) {
		this.availPolicyDTO = availPolicyDTO;
	}


	public YLHotelDTO getYlHotelDTO() {
		return ylHotelDTO;
	}


	public void setYlHotelDTO(YLHotelDTO ylHotelDTO) {
		this.ylHotelDTO = ylHotelDTO;
	}




	

}
