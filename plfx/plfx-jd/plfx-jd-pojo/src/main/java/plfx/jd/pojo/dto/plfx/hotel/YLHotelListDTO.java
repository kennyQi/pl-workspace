package plfx.jd.pojo.dto.plfx.hotel;

import java.util.Date;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;

public class YLHotelListDTO extends BaseJDDTO{
	private static final long serialVersionUID = 2180846743019297074L;
	/***
	 * 酒店ID
	 */
	
	private String hotelId;
	/**
	 * 更新时间
	 */

	private Date updatedTime;
	/***
	 * 更新内容
	 * 0-酒店、1-房型、2-图片；逗号分隔；仅反映最后一次更新的内容。
	 */

	private String modification;
	/***
	 * 包含产品
	 * 0-现付、1-预付、2-今日特价、3-限时抢购、4-钟点房（酒店不可用时为空）
	 */

	private String products;
	/***
	 * 可用状态
	 * 0--可用，1--不可用
	 */

	private int status;
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getModification() {
		return modification;
	}
	public void setModification(String modification) {
		this.modification = modification;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	
	

}
