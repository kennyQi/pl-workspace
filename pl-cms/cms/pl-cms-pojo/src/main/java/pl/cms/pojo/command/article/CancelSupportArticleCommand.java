package pl.cms.pojo.command.article;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CancelSupportArticleCommand extends BaseCommand {
	
	private String articleId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
}
