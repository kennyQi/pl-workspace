package hg.service.image.command.image;

import hg.service.image.base.BaseCommand;
import java.util.List;

/**
 * @类功能说明：还原图片
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午3:31:22
 */
public class RestoreImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片ID
	 */
	private List<String> imageIds;

	public RestoreImageCommand(){}
	public RestoreImageCommand(String projectId,String envName){
		super(projectId, envName);
	}
	
	public List<String> getImageIds() {
		return imageIds;
	}
	public void setImageIds(List<String> imageIds) {
		this.imageIds = (imageIds == null || imageIds.size() < 1)?null:imageIds;
	}
}