package pl.cms.pojo.command.comment;

import pl.cms.pojo.command.AdminBaseCommand;

/**
 * 审核通过评论
 */
@SuppressWarnings("serial")
public class ApproveCommentCommand extends AdminBaseCommand {

	private String commentId;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
