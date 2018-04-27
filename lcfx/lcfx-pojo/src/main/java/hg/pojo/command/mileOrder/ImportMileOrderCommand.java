package hg.pojo.command.mileOrder;

import java.util.ArrayList;
import java.util.List;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class ImportMileOrderCommand extends JxcCommand{
	public ImportMileOrderCommand() {
		list = new ArrayList<CreateMileOrderCommand>();
	}


	private List<CreateMileOrderCommand> list;

	public List<CreateMileOrderCommand> getList() {
		return list;
	}

	public void setList(List<CreateMileOrderCommand> list) {
		this.list = list;
	}
	
	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}


	private String distributorId;
	
}
