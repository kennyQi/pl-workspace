package hg.system.command.imageSpec;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：图片附件_查询公网urlcommand
 * @类修改者：zzb
 * @修改日期：2014年9月17日上午9:16:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月19日上午9:16:22
 * 
 */
@SuppressWarnings("serial")
public class ImageSpecPubCommand extends BaseCommand {

	/**
	 * 图片id
	 */
	private String imageId;

	/**
	 * 图片附件key
	 */
	private String key;

	/**
	 * 公网链接
	 */
	private String pubUrl;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPubUrl() {
		return pubUrl;
	}

	public void setPubUrl(String pubUrl) {
		this.pubUrl = pubUrl;
	}

}
