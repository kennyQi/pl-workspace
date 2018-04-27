package jxc.domain.model.image;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.UploadProductImageCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.product.Product;

@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_PRODUCT+"PRODUCT_IMAGE")
public class ProductImage extends BaseModel {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
	/**
	 * 文件服务器图片id
	 */
	@Column(name="IMAGE_ID")
	private  String imageId;
	
	/**
	 * 图片类型
	 */
	@Column(name="IMAGE_TYPE",columnDefinition=M.NUM_COLUM)
	private Integer imageType;
	
	/**
	 * 文件服务器图片地址
	 */
	@Column(name="IMAGE_URL")
	private String url;

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public void uploadProductImage(UploadProductImageCommand command) {
		setId(UUIDGenerator.getUUID());
		setImageId(command.getImageId());
		setUrl(command.getUrl());
		product = new Product();
		product.setId(command.getProductId());
		setProduct(product);
		setImageType(command.getImageType());
	}

	public void updateProductImage(UploadProductImageCommand command,Product product) {
		setId(command.getProductImageId());
		setImageId(command.getImageId());
		setUrl(command.getUrl());
		setProduct(product);
		setImageType(command.getImageType());
	}
	
	
}
