package hg.pojo.command.distributor;

import hg.pojo.command.JxcCommand;

import java.util.List;

@SuppressWarnings("serial")
public class ChangeStatusDistributorCommand extends JxcCommand {
	private Integer status;
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
