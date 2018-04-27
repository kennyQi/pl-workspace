package hg.pojo.command;


/**
 * 上传供应商资质图片
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class UploadSupplierAptitudeImageCommand extends JxcCommand {
	/**
	 * 供应商图片id
	 */
	private String supplierAptitudeImageId;
	/**
	 * 文件服务器图片id
	 */
	private String imageId;
	
	/**
	 * 文件服务器图片地址
	 */
	private String url;
	/**
	 * 供应商id
	 */
	private String supplierId;
	/**
	 * 图片位置
	 */
	private Integer imageType;

	
	public String getSupplierAptitudeImageId() {
		return supplierAptitudeImageId;
	}

	public void setSupplierAptitudeImageId(String supplierAptitudeImageId) {
		this.supplierAptitudeImageId = supplierAptitudeImageId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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
