package hg.pojo.command.mileOrder;

import hg.pojo.command.JxcCommand;

import java.util.List;

@SuppressWarnings("serial")
public class CheckMileOrderCommand extends JxcCommand {
	
	private List<String> ids;
	private String checkPersonId;
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}

}
