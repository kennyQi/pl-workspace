package hsl.pojo.command;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class UpdateHeadImageCommand extends BaseCommand{

	/**
	 * 头像路径
	 */
	private String imagePath;
	
	/**
	 * 用户ID
	 */
	private String userId;

	

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
