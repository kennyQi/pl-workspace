package hg.pojo.command;

public class CheckPurchaseOrderCommand extends CreateCheckRecordCommand {
	private static final long serialVersionUID = 1L;

	private String purchaseOrderId;

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
}
