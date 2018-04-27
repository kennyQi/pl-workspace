package pl.cms.pojo.command.comment;

import pl.cms.pojo.command.AdminBaseCommand;

/**
 * 审核不通过评论
 */
@SuppressWarnings("serial")
public class DisapproveCommentCommand extends AdminBaseCommand {

	private String commentId;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
