package hsl.pojo.dto.mp;

/**
 * 景点图片明细
 * 
 * @author Administrator
 */
public class ImageSpecDTO {

	/**
	 * 图片地址
	 */
	private String url;

	/**
	 * 图片标识
	 */
	private String albumId;

	/**
	 * 是否有水印
	 */
	private Boolean watermark;

	/**
	 * 尺寸代码
	 */
	private String sizeCode;

	/**
	 * 尺寸信息
	 */
	private String size;

	/**
	 * 宽PX
	 */
	private Integer width;

	/**
	 * 高PX
	 */
	private Integer height;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public Boolean getWatermark() {
		return watermark;
	}

	public void setWatermark(Boolean watermark) {
		this.watermark = watermark;
	}

	public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

}