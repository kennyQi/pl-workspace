package hg.system.command.imageSpec;

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
public class ImageSpecDeleteCommand extends BaseCommand {

	/**
	 * 图片id
	 */
	private String imageId;
	
	/**
	 * 图片附件id
	 */
	private String imageSpecId;

	/**
	 * 图片附件ids
	 */
	private List<String> imageSpecIds;

	
	public String getImageSpecId() {
		return imageSpecId;
	}

	public void setImageSpecId(String imageSpecId) {
		this.imageSpecId = imageSpecId;
	}

	public List<String> getImageSpecIds() {
		return imageSpecIds;
	}

	public void setImageSpecIds(List<String> imageSpecIds) {
		this.imageSpecIds = imageSpecIds;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
