package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SupplierCheckQO extends BaseQo{
	private String supplierId;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
}
