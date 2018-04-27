package pl.cms.domain.entity.comment;

import javax.persistence.Column;

public class CommentUser {
	/**
     * 昵称
     */
    @Column(name = "NICK_NAME", length = 64)
    private String nickName;
}
