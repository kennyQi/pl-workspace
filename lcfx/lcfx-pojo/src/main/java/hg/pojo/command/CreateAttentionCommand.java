package hg.pojo.command;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class CreateAttentionCommand extends JxcCommand {
	private String attentionName;
	private String attentionDetail;

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

}
