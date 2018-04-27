package hg.service.image.pojo.dto;

import java.io.Serializable;

/**
 * @类功能说明：图片规格KEY的DTO
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午3:10:28
 */
public class ImageSpecKeyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片规格KEY
	 */
	private String key;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 图片宽PX
	 */
	private int width;

	/**
	 * 图片高PX
	 */
	private int height;

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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}