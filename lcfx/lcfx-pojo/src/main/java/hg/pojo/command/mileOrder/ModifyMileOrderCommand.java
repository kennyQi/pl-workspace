package hg.pojo.command.mileOrder;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class ModifyMileOrderCommand extends JxcCommand{
	
	private String id;
	
	private String name;

	private String linkMan;

	private String phone;

	public String getName() {
		return name;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
