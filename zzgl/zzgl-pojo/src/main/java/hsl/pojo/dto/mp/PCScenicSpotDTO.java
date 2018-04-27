package hsl.pojo.dto.mp;

import java.util.ArrayList;
import java.util.List;
/**
 * pc端景区的详细DTO
 * @author Administrator
 *
 */
public class PCScenicSpotDTO  {
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
	 * 景点详情
	 */
	private String detail;
	/**
	 * 景区图片
	 */
	private List<ImageSpecDTO> images = new ArrayList<ImageSpecDTO>();
	
	/**
	 * 预定信息
	 */
	private BookInfoDTO bookInfoDTO;
	
	/**
	 * 景点政策列表
	 */
	private List<PolicyDTO> plist ;

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public List<PolicyDTO> getPlist() {
		return plist;
	}

	public void setPlist(List<PolicyDTO> plist) {
		this.plist = plist;
	}

	public ScenicSpotsGeographyInfoDTO getScenicSpotGeographyInfo() {
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			ScenicSpotsGeographyInfoDTO scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}
	
}
