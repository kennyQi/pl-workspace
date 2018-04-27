package plfx.jd.pojo.dto.plfx.hotel;

import java.util.List;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;


public class YLHotelDTO extends BaseJDDTO{
	private static final long serialVersionUID = 7565788930746814402L;

	/***
	 * 酒店id
	 */
	
	private String hotelId;
	
	/***
	 * 酒店详情
	 */

	private YLHotelDetailDTO ylHotelDetailDTO;
	
	/***
	 * 酒店图片
	 */
	
	private List<YLHotelImageDTO> imageList;
	
	/***
	 * 酒店房型
	 */

	
	private List<YLHotelRoomDTO> roomList;
	
	/***
	 * 点评
	 */
	
	private YLReviewDTO ylReviewDTO;
	
	/***
	 * 供应商
	 */
	
	private List<YLSupplierDTO> supplierList;
	
	
	/***
	 * 酒店设施信息
	 */

	private YLFacilitiesV2DTO facilitiesV2DTO;
	
	/**
	 * 酒店服务指数
	 */

	private YLServiceRankDTO serviceRankDTO;
		
	public List<YLSupplierDTO> getSupplierList() {
		return supplierList;
	}

	
	

	public YLHotelDetailDTO getYlHotelDetailDTO() {
		return ylHotelDetailDTO;
	}




	public void setYlHotelDetailDTO(YLHotelDetailDTO ylHotelDetailDTO) {
		this.ylHotelDetailDTO = ylHotelDetailDTO;
	}




	public YLReviewDTO getYlReviewDTO() {
		return ylReviewDTO;
	}




	public void setYlReviewDTO(YLReviewDTO ylReviewDTO) {
		this.ylReviewDTO = ylReviewDTO;
	}




	public YLFacilitiesV2DTO getFacilitiesV2DTO() {
		return facilitiesV2DTO;
	}




	public void setFacilitiesV2DTO(YLFacilitiesV2DTO facilitiesV2DTO) {
		this.facilitiesV2DTO = facilitiesV2DTO;
	}




	public YLServiceRankDTO getServiceRankDTO() {
		return serviceRankDTO;
	}




	public void setServiceRankDTO(YLServiceRankDTO serviceRankDTO) {
		this.serviceRankDTO = serviceRankDTO;
	}




	public void setSupplierList(List<YLSupplierDTO> supplierList) {
		this.supplierList = supplierList;
	}




	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	public List<YLHotelImageDTO> getImageList() {
		return imageList;
	}

	public void setImageList(List<YLHotelImageDTO> imageList) {
		this.imageList = imageList;
	}

	public List<YLHotelRoomDTO> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<YLHotelRoomDTO> roomList) {
		this.roomList = roomList;
	}

	
}
