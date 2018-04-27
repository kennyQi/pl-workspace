package hg.service.ad.command.ad;

import hg.service.ad.base.BaseCommand;

/**
 * @类功能说明：修改广告Command
 * @类修改者：
 * @修改日期：2014年12月11日下午4:43:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:43:18
 * 
 */
public class ChangeAdImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 广告的ID
	 */
	private String adId;

	/**
	 * 图片信息(json)
	 */
	private String imageInfo;

	/**
	 * 图片访问相对路径
	 */
	private String imageUrl;

	/**
	 * 图片服务返回的图片id
	 */
	private String imageId;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
