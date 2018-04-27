package pl.cms.pojo.command.article;

/**
 * 设置全部文章频道
 * 
 * @author yuxx
 * 
 */
public class SettingAllArticleChannelCommand {

	/**
	 * 逗号间隔的全部频道名称
	 */
	private String[] names;

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

}
