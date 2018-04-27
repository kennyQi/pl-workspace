package hg.service.image.command.image.spec;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：删除图片规格
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:36:33
 */
public class DeleteImageToSpecCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片ID
	 */
	private String imageId;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId == null ? null : imageId.trim();
	}
}