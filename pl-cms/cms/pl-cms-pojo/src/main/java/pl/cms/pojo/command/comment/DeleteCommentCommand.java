package pl.cms.pojo.command.comment;

import pl.cms.pojo.command.AdminBaseCommand;

/**
 * 删除评论
 */
@SuppressWarnings("serial")
public class DeleteCommentCommand extends AdminBaseCommand {

	private String commentId;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
