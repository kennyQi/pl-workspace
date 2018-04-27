package hg.pojo.qo;

@SuppressWarnings("serial")
public class PurchaseOrderDetailQo extends JxcBaseQo{
	private String purchaseOrderId;
	
	private String skuProductCode;

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getSkuProductCode() {
		return skuProductCode;
	}

	public void setSkuProductCode(String skuProductCode) {
		this.skuProductCode = skuProductCode;
	}
}
