package hsl.pojo.qo.dzp.scenicspot;

import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 景区图片封面QO
 * Created by qijie on 2016/3/7.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpScenicSpotPicDAO")
public class DZPScenicSpotPicQO extends BaseQo {

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 图片地址
	 */
	private String url;

	/**
	 * 图片名称
	 */
	private String name;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

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
