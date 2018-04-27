package pl.cms.pojo.command.article;

import hg.common.component.BaseCommand;

/**
 * 隐藏文章命令
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class HideArticleCommand extends BaseCommand {
	
	private String articleId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
}
