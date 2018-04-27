package hg.pojo.command;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class ModifyAttentionCommand extends JxcCommand {
	private String attentionName;
	private String attentionDetail;
	private String id;

	public String getAttentionName() {
		return attentionName;
	}

	public void setAttentionName(String attentionName) {
		this.attentionName = attentionName;
	}

	public String getAttentionDetail() {
		return attentionDetail;
	}

	public void setAttentionDetail(String attentionDetail) {
		this.attentionDetail = attentionDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
