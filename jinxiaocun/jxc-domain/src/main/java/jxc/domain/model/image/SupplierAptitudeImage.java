package jxc.domain.model.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.supplier.Supplier;

import org.apache.commons.io.FileUtils;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.UploadSupplierAptitudeImageCommand;

@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SUPPLIER+"SUPPLIER_APTITUDE_IMAGE")
public class SupplierAptitudeImage extends BaseModel {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPPLIER_ID")
	private Supplier supplier;
	
	/**
	 * 图片类型
	 */
	@Column(name="IMAGE_TYPE",columnDefinition=M.NUM_COLUM)
	private Integer imageType;
	
	/**
	 * 文件服务器图片id
	 */
	@Column(name="IMAGE_ID")
	private  String imageId;
	
	/**
	 * 文件服务器图片地址
	 */
	@Column(name="IMAGE_URL")
	private String url;

	public String getImageId() {
		FileUtils.getTempDirectoryPath();
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public void uploadSupplierAptitudeImage(
			UploadSupplierAptitudeImageCommand command) {
		setId(UUIDGenerator.getUUID());
		setImageId(command.getImageId());
		setUrl(command.getUrl());
		supplier = new Supplier();
		supplier.setId(command.getSupplierId());
		setSupplier(supplier);
		setImageType(command.getImageType());
	}

	public void updateSupplierAptitudeImage(
			UploadSupplierAptitudeImageCommand command, Supplier supplier) {
		setId(command.getSupplierAptitudeImageId());
		setImageId(command.getImageId());
		setUrl(command.getUrl());
		setSupplier(supplier);
		setImageType(command.getImageType());
	}
	
	
}
