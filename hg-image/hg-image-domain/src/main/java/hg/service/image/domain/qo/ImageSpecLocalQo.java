package hg.service.image.domain.qo;

import hg.service.image.domain.qo.base.ImageBaseLocalQo;

/**
 * @类功能说明：图片规格DTO——每个ImageSpec对应FastDFS中的一个文件
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年8月15日下午2:02:34
 */
public class ImageSpecLocalQo extends ImageBaseLocalQo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片
	 */
	private ImageLocalQo image;
	
	/**
	 * 该规格图片在同一张图中的规格key
	 */
	private String key;
	
	public ImageSpecLocalQo(){
	}
	public ImageSpecLocalQo(String projectId, String envName) {
		super(projectId, envName);
	}
	public ImageSpecLocalQo(String id) {
		super(id);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}
	public ImageLocalQo getImage() {
		return image;
	}
	public void setImage(ImageLocalQo image) {
		this.image = image;
	}
}