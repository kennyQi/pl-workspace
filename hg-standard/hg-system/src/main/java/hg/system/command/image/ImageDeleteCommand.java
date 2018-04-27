package hg.system.command.image;

import hg.common.component.BaseCommand;

import java.util.List;

/**
 * 
 * @类功能说明：图片_删除command
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午11:07:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午11:07:40
 * 
 */
@SuppressWarnings("serial")
public class ImageDeleteCommand extends BaseCommand {

	/**
	 * 图片id
	 */
	private String imageId;

	/**
	 * 图片ids
	 */
	private String imageIdsStr;

	/**
	 * 图片ids
	 */
	private List<String> imageIds;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageIdsStr() {
		return imageIdsStr;
	}

	public void setImageIdsStr(String imageIdsStr) {
		this.imageIdsStr = imageIdsStr;
	}

	public List<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<String> imageIds) {
		this.imageIds = imageIds;
	}

}
