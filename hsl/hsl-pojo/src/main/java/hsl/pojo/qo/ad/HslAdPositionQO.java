package hsl.pojo.qo.ad;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslAdPositionQO extends BaseQo{
	/**
	 * 广告位ID
	 */
	private String positionId;
	/**
	 * 工程ID
	 */
	private String projectId;
	/**
	 * 是否是通用广告
	 */
	private Boolean isCommon;
	/**
	 * 客户端类型
	 */
	private Integer clientType;
	
	public Boolean getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Boolean isCommon) {
		this.isCommon = isCommon;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
}
