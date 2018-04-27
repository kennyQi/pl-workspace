package hg.pojo.command.distributor;

import hg.pojo.command.JxcCommand;

import java.util.List;

@SuppressWarnings("serial")
public class RemoveDistributorCommand extends JxcCommand {
	
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
