package lxs.api.v1.response.mp;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.ScenicSpotPicDTO;
import lxs.api.v1.dto.mp.TicketPolicyDTO;

public class ScenicSpotInfoResponse extends ApiResponse {

	/**
	 * 景区ID
	 */
	private String scenicSpotID;

	/**
	 * 景区图片
	 */
	private List<ScenicSpotPicDTO> scenicSpotPicDTOs;

	/**
	 * 景区名称
	 */
	private String name;

	/**
	 * 景区简称
	 */
	private String shortName;

	private String intro;

	/**
	 * 开放时间(每天9:30:00-17:30:00)
	 */
	private String openTime;

	/**
	 * 包含景点(冗余字段)
	 */
	private String scenicSpotNameStr;

	/**
	 * 政策
	 */
	private List<TicketPolicyDTO> ticketPolicyDTOs;

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public List<ScenicSpotPicDTO> getScenicSpotPicDTOs() {
		return scenicSpotPicDTOs;
	}

	public void setScenicSpotPicDTOs(List<ScenicSpotPicDTO> scenicSpotPicDTOs) {
		this.scenicSpotPicDTOs = scenicSpotPicDTOs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public List<TicketPolicyDTO> getTicketPolicyDTOs() {
		return ticketPolicyDTOs;
	}

	public void setTicketPolicyDTOs(List<TicketPolicyDTO> ticketPolicyDTOs) {
		this.ticketPolicyDTOs = ticketPolicyDTOs;
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
