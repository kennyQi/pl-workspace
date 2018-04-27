package hg.pojo.qo;

@SuppressWarnings("serial")
public class ProdSuppCorrelationQo extends JxcBaseQo{
	private static final long serialVersionUID = 1L;
	
	private String productId;
	private String supplierId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	}
