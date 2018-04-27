package hg.pojo.command.mileOrder;

import hg.pojo.command.JxcCommand;

import java.util.List;

@SuppressWarnings("serial")
public class RemoveMileOrderCommand extends JxcCommand {
	
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
