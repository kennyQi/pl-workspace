package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jd.domain.model.M;
/****
 * 
 * @类功能说明：酒店列表实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月15日下午1:56:55
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "HOTEL_LIST")
public class YLHotelList extends BaseModel {
	
	/***
	 * 酒店ID
	 */
	@Column(name = "HOTEL_ID")
	private String hotelId;
	/**
	 * 更新时间
	 */
	@Column(name = "UPDATED_TIME")
	private Date updatedTime;
	/***
	 * 更新内容
	 * 0-酒店、1-房型、2-图片；逗号分隔；仅反映最后一次更新的内容。
	 */
	@Column(name = "MODIFICATION")
	private String modification;
	/***
	 * 包含产品
	 * 0-现付、1-预付、2-今日特价、3-限时抢购、4-钟点房（酒店不可用时为空）
	 */
	@Column(name = "PRODUCTIONS")
	private String products;
	/***
	 * 可用状态
	 * 0--可用，1--不可用
	 */
	@Column(name = "STATUS")
	private int status;
	
	/***
	 * 是否更新(0代表没更新,1代表更新)
	 */
	@Column(name = "UPDATE_STATUS")
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
