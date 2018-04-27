package hg.pojo.command.warehousingorder;

import java.util.List;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class RemoveWarehousingOrderCommand extends JxcCommand {
	private List<String> idList;

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

}
