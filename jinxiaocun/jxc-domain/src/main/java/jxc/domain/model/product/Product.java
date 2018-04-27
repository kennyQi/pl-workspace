package jxc.domain.model.product;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateProductBaseInfoCommand;
import hg.pojo.command.ModifyProductCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.system.Unit;
import jxc.domain.util.CodeUtil;

/**
 * 商品快照表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "PRODUCT")
public class Product extends JxcBaseModel {
	/**
	 * 商品编码
	 */
	@Column(name = "PRODUCT_CODE", length = 7)
	private String productCode;

	/**
	 * 商品名称
	 */
	@Column(name = "PRODUCT_NAME", length = 30)
	private String productName;

	/**
	 * 商品品牌
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRAND_ID")
	private Brand productBrand;

	/**
	 * 商品类别
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID")
	private ProductCategory productCategory;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 255)
	private String remark;

	/**
	 * 商品描述
	 */
	@Column(name = "PRODUCT_DESCRIBE", columnDefinition = M.TEXT_COLUM)
	private String productDescribe;

	/**
	 * 尺寸
	 */
	@Column(name = "SIZE_WIDTH", columnDefinition = M.DOUBLE_COLUM)
	private Double sizeWidth;
	@Column(name = "SIZE_HIGH", columnDefinition = M.DOUBLE_COLUM)
	private Double sizeHigh;
	@Column(name = "SIZE_LONG", columnDefinition = M.DOUBLE_COLUM)
	private Double sizeLong;
	/**
	 * 出库尺寸
	 */
	@Column(name = "OUTSTOCK_SIZE_WIDTH", columnDefinition = M.DOUBLE_COLUM)
	private Double outstockSizeWidth;
	@Column(name = "OUTSTOCK_SIZE_HIGH", columnDefinition = M.DOUBLE_COLUM)
	private Double outstockSizeHigh;
	@Column(name = "OUTSTOCK_SIZE_LONG", columnDefinition = M.DOUBLE_COLUM)
	private Double outstockSizeLong;

	/**
	 * 重量
	 */
	@Column(name = "PRODUCT_WEIGHT", columnDefinition = M.DOUBLE_COLUM)
	private Double weight;

	/**
	 * 出库重量
	 */
	@Column(name = "PRODUCT_OUTSTOCK_WEIGHT", columnDefinition = M.DOUBLE_COLUM)
	private Double outStockWeight;

	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;

	/**
	 * 商品属性
	 */
	@Column(name = "ATTRIBUTE", columnDefinition = M.NUM_COLUM)
	private Integer attribute;

	/**
	 * 商品状态
	 */
	@Embedded
	private ProductStatus status;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Brand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(Brand productBrand) {
		this.productBrand = productBrand;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductDescribe() {
		return productDescribe;
	}

	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getOutStockWeight() {
		return outStockWeight;
	}

	public void setOutStockWeight(Double outStockWeight) {
		this.outStockWeight = outStockWeight;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param command
	 */
	public void createProductCommand(CreateProductBaseInfoCommand command) {
		// 品牌
		productBrand = new Brand();
		productBrand.setId(command.getBrandId());
		// 分类
		productCategory = new ProductCategory();
		productCategory.setId(command.getCategoryId());
		//
		status = new ProductStatus();
		status.setUsing(true);
		// 单位
		unit = new Unit();
		unit.setId(command.getUnitId());

		setId(UUIDGenerator.getUUID());

		setOutStockWeight(command.getOutStockWeight());
		setProductCode(CodeUtil.createProductCode());
		setProductDescribe(command.getProductDescribe());
		setProductName(command.getProductName());
		setRemark(command.getRemark());
		setAttribute(command.getAttribute());
		setCreateDate(new Date());

		setWeight(command.getWeight());
		// 设置尺寸
		setSizeHigh(command.getSizeHigh());
		setSizeLong(command.getSizeLong());
		setSizeWidth(command.getSizeWidth());
		// 设置出库尺寸
		setOutstockSizeHigh(command.getOutstockSizeHigh());
		setOutstockSizeLong(command.getOutstockSizeLong());
		setOutstockSizeWidth(command.getOutstockSizeWidth());
		
		setCreateDate(new Date());

		if (((command.getOutstockSizeLong() != null) && (command.getOutstockSizeWidth() != null) && (command.getOutstockSizeHigh() != null))
				|| (command.getOutStockWeight() != null)) {
			status.setSettingOutStockParam(true);
		} else {
			status.setSettingOutStockParam(false);
		}
		setStatusRemoved(false);
	}

	public void modifyProductCommand(ModifyProductCommand command) {
		productBrand = new Brand();
		productBrand.setId(command.getBrandId());

		unit = new Unit();
		unit.setId(command.getUnitId());

		status = new ProductStatus();
		status.setUsing(command.getUsing());

		productCategory = new ProductCategory();
		productCategory.setId(command.getCategoryId());

		setOutStockWeight(command.getOutStockWeight());
		setProductCode(command.getProductCode());
		setProductDescribe(command.getProductDescribe());
		setProductName(command.getProductName());
		setRemark(command.getRemark());

		setCreateDate(command.getCreateDate());
		setWeight(command.getWeight());
		setAttribute(command.getAttribute());

		// 设置尺寸
		setSizeHigh(command.getSizeHigh());
		setSizeLong(command.getSizeLong());
		setSizeWidth(command.getSizeWidth());
		// 设置出库尺寸
		setOutstockSizeHigh(command.getOutstockSizeHigh());
		setOutstockSizeLong(command.getOutstockSizeLong());
		setOutstockSizeWidth(command.getOutstockSizeWidth());

		if (((command.getOutstockSizeLong() != null) && (command.getOutstockSizeWidth() != null) && (command.getOutstockSizeHigh() != null))
				|| (command.getOutStockWeight() != null)) {
			status.setSettingOutStockParam(true);
		} else {
			status.setSettingOutStockParam(false);
		}
	}

	public Double getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public Double getSizeHigh() {
		return sizeHigh;
	}

	public void setSizeHigh(Double sizeHigh) {
		this.sizeHigh = sizeHigh;
	}

	public Double getSizeLong() {
		return sizeLong;
	}

	public void setSizeLong(Double sizeLong) {
		this.sizeLong = sizeLong;
	}

	public Double getOutstockSizeWidth() {
		return outstockSizeWidth;
	}

	public void setOutstockSizeWidth(Double outstockSizeWidth) {
		this.outstockSizeWidth = outstockSizeWidth;
	}

	public Double getOutstockSizeHigh() {
		return outstockSizeHigh;
	}

	public void setOutstockSizeHigh(Double outstockSizeHigh) {
		this.outstockSizeHigh = outstockSizeHigh;
	}

	public Double getOutstockSizeLong() {
		return outstockSizeLong;
	}

	public void setOutstockSizeLong(Double outstockSizeLong) {
		this.outstockSizeLong = outstockSizeLong;
	}

}
