package hg.service.image.command.image.spec;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：删除图片规格
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:36:33
 */
public class DeleteImageSpecCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	/**
	 * 默认的图片规格的KEY
	 */
	public final static String DEFAULT_KEY = "default";

	/**
     * 所属图片明细ID(当imageSpecId为null时其他条件不能为空,当ID不为null时忽略其他条件)
     */
	private String imageSpecId;

	/**
	 * 图片ID
	 */
	private String imageId;

	/**
	 * 图片规格的KEY(key不能为default)
	 */
	private String imageSpecKey;
	
	public DeleteImageSpecCommand(){}
	public DeleteImageSpecCommand(String projectId,String envName){
		super(projectId, envName);
	}

	public String getImageSpecId() {
		return imageSpecId;
	}
	public void setImageSpecId(String imageSpecId) {
		this.imageSpecId = imageSpecId == null ? null : imageSpecId.trim();
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId == null ? null : imageId.trim();
	}
	public String getImageSpecKey() {
		return imageSpecKey;
	}
	public void setImageSpecKey(String imageSpecKey) {
		this.imageSpecKey = (imageSpecKey == null || DeleteImageSpecCommand.DEFAULT_KEY.equals(imageSpecKey)) ? null : imageSpecKey.trim();
	}
}