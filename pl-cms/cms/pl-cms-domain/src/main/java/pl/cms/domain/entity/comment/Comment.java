package pl.cms.domain.entity.comment;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import pl.cms.domain.entity.M;
import pl.cms.domain.entity.article.Article;
import pl.cms.pojo.command.comment.CreateCommentCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Date;

/**
 * Title: <br>
 * Description: <br>
 */
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "COMMENT")
@SuppressWarnings("serial")
public class Comment extends BaseModel {

	/**
	 * 回复的新闻
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_ID")
	private Article article;

	/**
	 * 回复的评论
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_COMMENT_ID")
	private Comment parentComment;

	/**
	 * 评论正文
	 */
	@Column(name = "CONTENT", length = 1024)
	private String content;

	/**
	 * 评论时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	
	/**
	 * 评论类型 1 产品 2 新闻
	 */
	@Column(name = "TYPE", columnDefinition = M.NUM_COLUM)
	private Integer type;
	/**
	 * 是否审核
	 */
	@Column(name="ISCHECK")
	@Type(type = "yes_no")
	private Boolean isCheck;

	public Comment() {
	}
	/**
	 * @方法功能说明：创建评论
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月17日上午10:09:34
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createComment(CreateCommentCommand command){
        setId(UUIDGenerator.getUUID());
        Article article=new Article();
        article.setId(command.getArticleId());
        this.setArticle(article);
        this.setContent(command.getContent());
        this.setIsCheck(false);
        this.setType(2);
        this.setCreateDate(new Date());
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
}
