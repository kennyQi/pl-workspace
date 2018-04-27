package hg.system.command.imageSpec;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：图片附件_上传command
 * @类修改者：zzb
 * @修改日期：2014年9月17日上午9:16:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月17日上午9:16:22
 * 
 */
@SuppressWarnings("serial")
public class ImageSpecUploadCommand extends BaseCommand {

	/**
	 * 图片id
	 */
	private String imageId;
	
	/**
	 * 本地文件路径
	 */
	private String filePath;

	/**
	 * 文件类型
	 */
	private String extName;

	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

}
