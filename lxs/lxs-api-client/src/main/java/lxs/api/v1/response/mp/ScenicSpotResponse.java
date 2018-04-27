package lxs.api.v1.response.mp;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.ScenicSpotDTO;

public class ScenicSpotResponse extends ApiResponse {

	/**
	 * 每页数量
	 */
	private String pageSize;
	
	/**
	 * 页码
	 */
	private String pageNO;
	
	/**
	 * 是否为最后一页
	 */
	private String isTheLastPage;
	
	private List<ScenicSpotDTO> scenicSpotDTOs;

	public String getPageSize() {
		return pageSize;
	}



	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}



	public String getPageNO() {
		return pageNO;
	}



	public void setPageNO(String pageNO) {
		this.pageNO = pageNO;
	}



	public String getIsTheLastPage() {
		return isTheLastPage;
	}



	public void setIsTheLastPage(String isTheLastPage) {
		this.isTheLastPage = isTheLastPage;
	}



	public List<ScenicSpotDTO> getScenicSpotDTOs() {
		return scenicSpotDTOs;
	}



	public void setScenicSpotDTOs(List<ScenicSpotDTO> scenicSpotDTOs) {
		this.scenicSpotDTOs = scenicSpotDTOs;
	}



}
