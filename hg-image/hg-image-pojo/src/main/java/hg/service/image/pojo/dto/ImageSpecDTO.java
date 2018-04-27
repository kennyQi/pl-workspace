package hg.service.image.pojo.dto;

import hg.service.image.base.BaseDTO;

/**
 * @类功能说明：图片规格DTO——每个ImageSpec对应FastDFS中的一个文件
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年8月15日下午2:02:34
 */
public class ImageSpecDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属图片
	 */
	private ImageDTO image;

	/**
	 * 该规格图片在同一张图中的规格key
	 */
	private String key;
	
	/**
	 * FdfsFileInfo JSON
	 */
	private String fileInfo;

	/**
	 * 图大小(单位byte)
	 */
	private int fileSize;

	/**
	 * 宽
	 */
	private int width;

	/**
	 * 高
	 */
	private int height;

	public ImageDTO getImage() {
		return image;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
}