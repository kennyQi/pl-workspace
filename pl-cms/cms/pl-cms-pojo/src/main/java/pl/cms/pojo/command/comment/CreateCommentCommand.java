package pl.cms.pojo.command.comment;

import pl.cms.pojo.command.WXBaseCommand;

/**
 * 
 */
@SuppressWarnings("serial")
public class CreateCommentCommand extends WXBaseCommand {
	
	private String articleId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 评论
	 */
	private String content;

	/**
	 * 父id
	 */
	private String parentCommentId;
	
	

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(String parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

}
