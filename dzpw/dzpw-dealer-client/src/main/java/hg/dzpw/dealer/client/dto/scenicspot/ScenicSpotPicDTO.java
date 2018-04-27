package hg.dzpw.dealer.client.dto.scenicspot;

import hg.dzpw.dealer.client.common.EmbeddDTO;

@SuppressWarnings("serial")
public class ScenicSpotPicDTO extends EmbeddDTO{

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
