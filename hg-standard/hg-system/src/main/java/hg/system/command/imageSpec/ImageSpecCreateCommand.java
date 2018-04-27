package hg.system.command.imageSpec;

import hg.common.component.BaseCommand;

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
public class ImageSpecCreateCommand extends BaseCommand {

	/**
	 * 所属图片
	 */
	private String imageId;

	/**
	 * 该规格图片在同一张图中的规格key(必传)
	 */
	private String key;

	/**
	 * FdfsFileInfo JSON(单个中必传)
	 */
	private String fileInfo;

	/**
	 * 图片附件key所有值
	 */
	private JSONArray imageSpecKeys;
	
	/**
	 * 宽(list中必传)
	 */
	private Integer width;

	/**
	 * 高(list中必传)
	 */
	private Integer height;
	
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

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public JSONArray getImageSpecKeys() {
		return imageSpecKeys;
	}

	public void setImageSpecKeys(JSONArray imageSpecKeys) {
		this.imageSpecKeys = imageSpecKeys;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public ImageSpecCreateCommand(){}
	
	public ImageSpecCreateCommand(String key, Integer width, Integer height){
		this.key = key;
		this.width = width;
		this.height = height;
	}
	
}
