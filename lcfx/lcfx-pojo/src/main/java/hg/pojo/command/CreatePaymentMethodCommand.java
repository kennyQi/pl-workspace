package hg.pojo.command;

@SuppressWarnings("serial")
public class CreatePaymentMethodCommand extends JxcCommand{
	private String paymentMethodName;

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
}
