package hg.pojo.command;

import java.util.List;

@SuppressWarnings("serial")
public class RemovePaymentMethodCommand extends JxcCommand {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
