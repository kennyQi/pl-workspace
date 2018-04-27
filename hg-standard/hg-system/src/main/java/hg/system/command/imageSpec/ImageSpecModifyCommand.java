package hg.system.command.imageSpec;

import hg.common.component.BaseCommand;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @类功能说明：图片附件_编辑command
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午1:59:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午1:59:37
 * 
 */
@SuppressWarnings("serial")
public class ImageSpecModifyCommand extends BaseCommand {

	/**
	 * 所属图片id
	 */
	private String imageId;

	/**
	 * 图片附件id
	 */
	private String imageSpecId;

	/**
	 * 图片链接
	 */
	private String imgUrl;

	/**
	 * 裁剪图片的宽度 (px)
	 */
	private Integer width;

	/**
	 * 裁剪图片的高度 (px)
	 */
	private Integer height;

	/**
	 * 裁剪图片的左边距 (px)
	 */
	private Integer left;

	/**
	 * 裁剪图片的上边距 (px)
	 */
	private Integer top;

	/**
	 * 别名
	 */
	private String key;

	/**
	 * 新宽度
	 */
	private String newWidth;

	/**
	 * 新高度
	 */
	private String newHeight;

	/**
	 * 图片附件key所有值
	 */
	private JSONArray imageSpecKeys;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageSpecId() {
		return imageSpecId;
	}

	public void setImageSpecId(String imageSpecId) {
		this.imageSpecId = imageSpecId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getNewWidth() {
		return newWidth;
	}

	public void setNewWidth(String newWidth) {
		this.newWidth = newWidth;
	}

	public String getNewHeight() {
		return newHeight;
	}

	public void setNewHeight(String newHeight) {
		this.newHeight = newHeight;
	}
	
	public ImageSpecModifyCommand(){}
	
	public ImageSpecModifyCommand(String imageId, String imageSpecId, String imgUrl, Integer width, Integer height, String key, JSONArray imageSpecKeys){
		this.imageId = imageId;
		this.imageSpecId = imageSpecId;
		this.newHeight = String.valueOf(height);
		this.newWidth = String.valueOf(width);
		this.imgUrl = imgUrl;
		this.key = key;
		this.imageSpecKeys = imageSpecKeys;
	}

	public JSONArray getImageSpecKeys() {
		return imageSpecKeys;
	}

	public void setImageSpecKeys(JSONArray imageSpecKeys) {
		this.imageSpecKeys = imageSpecKeys;
	}

	
}
