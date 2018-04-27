package pl.cms.pojo.command.scenic;

import pl.cms.pojo.command.AdminBaseCommand;

@SuppressWarnings("serial")
public class DeleteScenicCommand extends AdminBaseCommand {

	private String scenicId;

	public String getScenicId() {
		return scenicId;
	}

	public void setScenicId(String scenicId) {
		this.scenicId = scenicId;
	}

}
