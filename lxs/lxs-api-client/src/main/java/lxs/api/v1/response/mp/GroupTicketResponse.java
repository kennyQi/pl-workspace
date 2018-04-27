package lxs.api.v1.response.mp;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.GroupTicketDTO;

public class GroupTicketResponse extends ApiResponse {

	private List<GroupTicketDTO> groupTicketDTOs;

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
	
	public List<GroupTicketDTO> getGroupTicketDTOs() {
		return groupTicketDTOs;
	}

	public void setGroupTicketDTOs(List<GroupTicketDTO> groupTicketDTOs) {
		this.groupTicketDTOs = groupTicketDTOs;
	}

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

	
}
