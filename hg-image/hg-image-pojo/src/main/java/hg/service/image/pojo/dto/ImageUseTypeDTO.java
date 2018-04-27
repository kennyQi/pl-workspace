package hg.service.image.pojo.dto;

import hg.service.image.base.BaseDTO;
import java.util.List;

/**
 * @类功能说明：图片规格用途集合DTO
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:03:22
 */
public class ImageUseTypeDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 用途名称
	 */
	private String title;

	/**
	 * 用途备注
	 */
	private String remark;

	/**
	 * 图片规格KEY用途 使用JSON保存
	 */
	private List<ImageSpecKeyDTO> imageSpecKeys;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public List<ImageSpecKeyDTO> getImageSpecKeys() {
		return imageSpecKeys;
	}
	public void setImageSpecKeys(List<ImageSpecKeyDTO> imageSpecKeys) {
		this.imageSpecKeys = (imageSpecKeys == null || imageSpecKeys.size() < 1)?null:imageSpecKeys;
	}
}