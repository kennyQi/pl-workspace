package hg.service.ad.pojo.dto.ad;

import hg.service.ad.base.BaseDTO;

@SuppressWarnings("serial")
public class AdPositionBaseInfoDTO extends BaseDTO{
	/**
	 * 广告位名称
	 */
	private String name;
	/**
	 * 客户端类型
	 */
	private Integer clientType;
	/**
	 * 描述
	 */
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
