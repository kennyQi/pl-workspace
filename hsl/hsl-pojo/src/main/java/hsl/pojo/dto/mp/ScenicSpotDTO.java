package hsl.pojo.dto.mp;

import hsl.pojo.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游景点
 */
@SuppressWarnings("serial")
public class ScenicSpotDTO extends BaseDTO{

	private String id;
	/**
	 * 景点基本信息
	 */
	private ScenicSpotsBaseInfoDTO scenicSpotBaseInfo;
	/**
	 * 景点地理信息(省市区经纬度等)
	 */
	private ScenicSpotsGeographyInfoDTO scenicSpotGeographyInfo;
	/**
	 * 景区图片
	 */
	private List<ImageSpecDTO> images = new ArrayList<ImageSpecDTO>();
	
	/**
	 * 预定信息
	 */
	private BookInfoDTO bookInfoDTO;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ScenicSpotsBaseInfoDTO getScenicSpotBaseInfo() {
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(ScenicSpotsBaseInfoDTO scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public ScenicSpotsGeographyInfoDTO getScenicSpotGeographyInfo() {
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			ScenicSpotsGeographyInfoDTO scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}

	public List<ImageSpecDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageSpecDTO> images) {
		this.images = images;
	}

	public BookInfoDTO getBookInfoDTO() {
		return bookInfoDTO;
	}

	public void setBookInfoDTO(BookInfoDTO bookInfoDTO) {
		this.bookInfoDTO = bookInfoDTO;
	}


}