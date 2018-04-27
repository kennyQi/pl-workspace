package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.system.Unit;

/**
 * 商品快照表
 */
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "PRODUCT_SNAPSHOT")
public class ProductSnapshot extends BaseModel {
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 快照日期
	 */
	@Column(name = "SNAP_DATE", columnDefinition = M.DATE_COLUM)
	private Date snapDate;

	/**
	 * 商品id
	 */
	@Column(name = "PRODUCT_ID", length = 64)
	private String productId;

	public void create(Product p) {
		productId = p.getId();
		setId(UUIDGenerator.getUUID());
		snapDate = new Date();

		setProductCode(p.getProductCode());
		setProductName(p.getProductName());
		setProductBrand(p.getProductBrand());
		setProductCategory(p.getProductCategory());
		setRemark(p.getRemark());
		setProductDescribe(p.getProductDescribe());
		setSizeWidth(p.getSizeWidth());
		setSizeHigh(p.getSizeHigh());
		setSizeLong(p.getSizeLong());
		setOutstockSizeWidth(p.getOutstockSizeWidth());
		setOutstockSizeHigh(p.getOutstockSizeHigh());
		setOutstockSizeLong(p.getOutstockSizeLong());
		setWeight(p.getWeight());
		setOutStockWeight(p.getOutstockSizeWidth());
		setUnit(p.getUnit());
		setAttribute(p.getAttribute());
		setStatus(p.getStatus());
		setCreateDate(p.getCreateDate());

	}

	public Date getSnapDate() {
		return snapDate;
	}

	public void setSnapDate(Date snapDate) {
		this.snapDate = snapDate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

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

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
