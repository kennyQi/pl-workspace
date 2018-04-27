package hg.fx.command.mileOrder;


import hg.framework.common.base.BaseSPICommand;

import java.util.List;

@SuppressWarnings("serial")
public class RemoveMileOrderCommand extends BaseSPICommand {
	
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
