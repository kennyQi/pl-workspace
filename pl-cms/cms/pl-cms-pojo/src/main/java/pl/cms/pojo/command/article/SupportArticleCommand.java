package pl.cms.pojo.command.article;

import hg.common.component.BaseModel;

/**
 * 给文章点赞
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class SupportArticleCommand extends BaseModel {

	private String articleId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

}
