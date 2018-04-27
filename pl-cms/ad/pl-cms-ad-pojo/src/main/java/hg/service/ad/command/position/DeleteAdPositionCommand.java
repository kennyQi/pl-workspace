package hg.service.ad.command.position;

import hg.service.ad.base.BaseCommand;

public class DeleteAdPositionCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	private String adPositionId;

	public String getAdPositionId() {
		return adPositionId;
	}

	public void setAdPositionId(String adPositionId) {
		this.adPositionId = adPositionId;
	}

}
