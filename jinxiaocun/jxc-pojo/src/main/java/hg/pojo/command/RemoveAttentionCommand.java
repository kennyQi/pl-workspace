package hg.pojo.command;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class RemoveAttentionCommand extends JxcCommand {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
