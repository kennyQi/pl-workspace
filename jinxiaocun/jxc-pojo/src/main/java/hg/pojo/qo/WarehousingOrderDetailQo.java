package hg.pojo.qo;

@SuppressWarnings("serial")
public class WarehousingOrderDetailQo extends JxcBaseQo{
	private String purchaseOrderId;

	public String getWarehousingOrderId() {
		return purchaseOrderId;
	}

	public void setWarehousingOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
}
