package lxs.pojo.command.mp;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CreateScenicSpotSelectiveCommand extends BaseCommand {
	
	private String scenicSpotId;
	
	private String type;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

}
