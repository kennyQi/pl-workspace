package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteRecommendCommand extends BaseCommand {

	/**
	 * 推荐ID
	 */
	private String RecommendID;

	public String getRecommendID() {
		return RecommendID;
	}

	public void setRecommendID(String recommendID) {
		RecommendID = recommendID;
	}
	
}
