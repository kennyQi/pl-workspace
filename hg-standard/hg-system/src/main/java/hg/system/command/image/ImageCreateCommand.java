package hg.system.command.image;

import hg.common.component.BaseCommand;
import hg.system.command.imageSpec.ImageSpecCreateCommand;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

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
public class ImageCreateCommand extends BaseCommand {

	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 图片备注说明
	 */
	private String remark;

	/**
	 * 相册
	 */
	private String albumId;

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
	private JSONArray imageSpecKeys;
	
	/**
	 * 图片静态路径
	 */
	private String imageStaticUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
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

	public ImageSpecCreateCommand getDefaultImageSpec() {
		return defaultImageSpec;
	}

	public void setDefaultImageSpec(ImageSpecCreateCommand defaultImageSpec) {
		this.defaultImageSpec = defaultImageSpec;
	}

	public JSONArray getImageSpecKeys() {
		return imageSpecKeys;
	}

	public void setImageSpecKeys(JSONArray imageSpecKeys) {
		this.imageSpecKeys = imageSpecKeys;
	}

	public String getImageStaticUrl() {
		return imageStaticUrl;
	}

	public void setImageStaticUrl(String imageStaticUrl) {
		this.imageStaticUrl = imageStaticUrl;
	}

}
