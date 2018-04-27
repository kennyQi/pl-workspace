package hg.pojo.command;

@SuppressWarnings("serial")
public class ModifyPaymentMethodCommand extends JxcCommand{
	private String paymentMethodName;
	private String id;

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
