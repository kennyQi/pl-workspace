package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "YL_HOTEL")
public class YLHotel extends BaseModel{
 
	/***
	 * 酒店id
	 */
	@Column(name = "HOTEL_ID")
	private String hotelId;
	
	/***
	 * 酒店详情
	 */
	@Embedded
	private YLHotelDetail ylHotelDetail;
	
	/***
	 * 酒店图片
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ylHotel",cascade={CascadeType.ALL})
	private List<YLHotelImage> imageList;
	
	/***
	 * 酒店房型
	 */

	@OneToMany(fetch=FetchType.LAZY, mappedBy="ylHotel",cascade={CascadeType.ALL})
	private List<YLHotelRoom> roomList;
	
	/***
	 * 点评
	 */
	@Embedded
	private YLReview ylReview;
	
	/***
	 * 供应商
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="ylHotel",cascade={CascadeType.ALL})
	private List<YLSupplier> supplierList;
	
	
	/***
	 * 酒店设施信息
	 */
	@Embedded
	private YLFacilitiesV2 facilitiesV2;
	
	/**
	 * 酒店服务指数
	 */
	@Embedded
	private YLServiceRank serviceRank;
		
	public List<YLSupplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<YLSupplier> supplierList) {
		this.supplierList = supplierList;
	}

	public YLFacilitiesV2 getFacilitiesV2() {
		return facilitiesV2;
	}

	public void setFacilitiesV2(YLFacilitiesV2 facilitiesV2) {
		this.facilitiesV2 = facilitiesV2;
	}

	public YLServiceRank getServiceRank() {
		return serviceRank;
	}

	public void setServiceRank(YLServiceRank serviceRank) {
		this.serviceRank = serviceRank;
	}

	public YLReview getYlReview() {
		return ylReview;
	}

	public void setYlReview(YLReview ylReview) {
		this.ylReview = ylReview;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public YLHotelDetail getYlHotelDetail() {
		return ylHotelDetail;
	}

	public void setYlHotelDetail(YLHotelDetail ylHotelDetail) {
		this.ylHotelDetail = ylHotelDetail;
	}

	public List<YLHotelImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<YLHotelImage> imageList) {
		this.imageList = imageList;
	}

	public List<YLHotelRoom> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<YLHotelRoom> roomList) {
		this.roomList = roomList;
	}

	
	
}
