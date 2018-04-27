package pl.cms.pojo.command.article;

import hg.common.component.BaseModel;

/**
 * 显示文章命令
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class ShowArticleCommand extends BaseModel {

	private String articleId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

}
