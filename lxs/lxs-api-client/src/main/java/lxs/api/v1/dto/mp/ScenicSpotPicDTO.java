package lxs.api.v1.dto.mp;

import lxs.api.v1.dto.BaseDTO;


@SuppressWarnings("serial")
public class ScenicSpotPicDTO extends BaseDTO{

	/**
	 * 图片地址
	 */
	private String url;

	/**
	 * 图片名称
	 */
	private String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
