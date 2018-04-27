package jxc.domain.model.product;

import hg.common.component.BaseModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.supplier.Supplier;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "PRODUCT_SUPPLIER")
public class ProductSupplierCorrelation extends BaseModel {
	/**
	*supplier
	*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	/**
	*product
	*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


}
