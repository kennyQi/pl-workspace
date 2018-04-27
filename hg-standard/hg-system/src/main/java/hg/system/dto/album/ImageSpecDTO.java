package hg.system.dto.album;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：图片附件_DTO
 * @类修改者：zzb
 * @修改日期：2014年11月10日下午3:31:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月10日下午3:31:48
 */
@SuppressWarnings("serial")
public class ImageSpecDTO extends EmbeddDTO {

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 所属图片
	 */
	private ImageDTO image;

	/**
	 * key
	 */
	private String key;

	/**
	 * FdfsFileInfo JSON
	 */
	private String fileInfo;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
		this.key = key;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

}