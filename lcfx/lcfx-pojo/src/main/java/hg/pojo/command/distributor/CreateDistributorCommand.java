package hg.pojo.command.distributor;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class CreateDistributorCommand extends JxcCommand{
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	
}
