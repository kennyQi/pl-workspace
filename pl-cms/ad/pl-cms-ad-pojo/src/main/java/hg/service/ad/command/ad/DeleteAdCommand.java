package hg.service.ad.command.ad;

import hg.service.ad.base.BaseCommand;

/**
 * 
 * @类功能说明：删除广告command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年12月15日下午2:21:57
 * @版本：V1.0
 *
 */
public class DeleteAdCommand extends BaseCommand{
	private static final long serialVersionUID = -6203342810400378120L;
	/**
	 * 广告id
	 */
	private String adId;
	/**
	 * 图片id
	 */
	private String imageId;
	
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}
