package hg.pojo.command;


/**
 * 上传供应商资质图片
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class UploadProductImageCommand extends JxcCommand {
	/**
	 * 图片id
	 */
	private String productImageId;
	
	/**
	 * 文件服务器图片id
	 */
	private String imageId;
	
	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 文件服务器图片地址
	 */
	private String url;
	/**
	 * 图片位置
	 */
	private Integer imageType;
	
	
	
	public String getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(String productImageId) {
		this.productImageId = productImageId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
