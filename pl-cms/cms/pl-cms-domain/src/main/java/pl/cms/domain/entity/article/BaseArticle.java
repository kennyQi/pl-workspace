package pl.cms.domain.entity.article;
import hg.common.component.BaseModel;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import pl.cms.domain.entity.M;
import pl.cms.domain.entity.article.ArticleStatus;

/**
 * 文章
 * 
 * @author yuxx
 * 
 */
//@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ARTICLE_TYPE")
@DiscriminatorValue("basearticle")
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "ARTICLE")
@SuppressWarnings("serial")
public class BaseArticle extends BaseModel {

	/**
	 * 文章基本信息
	 */
	protected ArticleBaseInfo baseInfo;

	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	protected Date createDate;

	
	/**
	 * 文章状态
	 */
	protected ArticleStatus status;

	public BaseArticle() {
		// TODO 自动生成的构造函数存根
	}


	public void show() {
		status.setCurrentValue(ArticleStatus.VALUE_SHOW);
	}

	public void hide() {
		status.setCurrentValue(ArticleStatus.VALUE_HIDE);
	}

	public ArticleBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ArticleBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}

}
