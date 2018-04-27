package hg.system.command.image;

import hg.common.component.BaseCommand;
import hg.system.command.imageSpec.ImageSpecCreateCommand;

import java.util.List;

/**
 * 
 * @类功能说明：图片_添加command
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午10:23:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午10:23:58
 * 
 */
@SuppressWarnings("serial")
public class ImageCreateTempCommand extends BaseCommand {

	private String title;

	/**
	 * 归属者 (必传)
	 */
	private String ownerId;

	/**
	 * 类型 (必传)
	 */
	private Integer useType;

	// /**
	// * FdfsFileInfo JSON
	// */
	// private ImageSpecCreateCommand imageSpec;

	// ============imageSpec所需字段==============
	private ImageSpecCreateCommand defaultImageSpec;

	private List<ImageSpecCreateCommand> imageSpecList;

	// /**
	// * 该规格图片在同一张图中的规格key
	// */
	// private String key;
	//
	// /**
	// * FdfsFileInfo JSON
	// */
	// private String fileInfo;
	//

	/**
	 * 图片附件key所有值
	 */
	private String imageSpecKeys;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<ImageSpecCreateCommand> getImageSpecList() {
		return imageSpecList;
	}

	public void setImageSpecList(List<ImageSpecCreateCommand> imageSpecList) {
		this.imageSpecList = imageSpecList;
	}

	// public String getKey() {
	// return key;
	// }
	//
	// public void setKey(String key) {
	// this.key = key;
	// }
	//
	// public String getFileInfo() {
	// return fileInfo;
	// }
	//
	// public void setFileInfo(String fileInfo) {
	// this.fileInfo = fileInfo;
	// }
	//

	public String getImageSpecKeys() {
		return imageSpecKeys;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImageSpecKeys(String imageSpecKeys) {
		this.imageSpecKeys = imageSpecKeys;
	}

	public ImageSpecCreateCommand getDefaultImageSpec() {
		return defaultImageSpec;
	}

	public void setDefaultImageSpec(ImageSpecCreateCommand defaultImageSpec) {
		this.defaultImageSpec = defaultImageSpec;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

}
