package plfx.jd.pojo.qo;

import hg.common.component.BaseQo;

import java.util.Date;
@SuppressWarnings("serial")
public class YLHotelListQO extends BaseQo{

	/***
	 * 酒店id
	 */
	private String hotelId;
	/***
	 * 更新时间
	 */
	private Date updatedTime; 
	
	/***
	 * 可用状态(0可用,1不可用)
	 */
	private Integer status;

	/***
	 * 是否更新(0代表没更新,1代表更新)
	 */
	private String updateStatus;
	
	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
