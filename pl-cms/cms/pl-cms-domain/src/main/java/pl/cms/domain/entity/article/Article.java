package pl.cms.domain.entity.article;
import hg.common.util.UUIDGenerator;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import pl.cms.domain.entity.M;
import pl.cms.domain.entity.article.ArticleStatus;
import pl.cms.domain.entity.image.Image;
import pl.cms.pojo.command.article.CreateArticleCommand;
import pl.cms.pojo.command.article.ModifyArticleCommand;

/**
 * 文章
 * 
 * @author yuxx
 * 
 */
@DiscriminatorValue("article")
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "ARTICLE")
@SuppressWarnings("serial")
public class Article extends BaseArticle {


	/**
	 * 要显示该文章的频道
	 */
	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE })
	@JoinTable(name = "ARTICLE_SHOW_CHANNELS", joinColumns = { @JoinColumn(name = "ARTICLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "CHANNEL_ID") })
	private Set<ArticleChannel> showChannels;
	

	public Article() {
		// TODO 自动生成的构造函数存根
	}

	public Article(CreateArticleCommand command, 
			Set<ArticleChannel> showChannels, Image titleImage) {

		setId(UUIDGenerator.getUUID());
		
		ArticleBaseInfo baseInfo = new ArticleBaseInfo();
		baseInfo.setAuthor(command.getAuthor());
  		baseInfo.setContent(command.getContent());
		baseInfo.setTitle(command.getTitle());
		setBaseInfo(baseInfo);

		setCreateDate(new Date());

		setShowChannels(showChannels);
		
		getBaseInfo().setTitleImage(titleImage);
//		if(StringUtils.isNotBlank(command.getFdfsFileInfoJSON())){
//			FdfsFileInfo fileInfo =JSON.parseObject(command.getFdfsFileInfoJSON(), FdfsFileInfo.class);
//			Image image = new Image();
//			image.setTitle(fileInfo.getMetaMap().get("title"));
//			image.setUrl(fileInfo.getUri());
//			baseInfo.setTitleImage(image);
//		}
		
		ArticleStatus status = new ArticleStatus();
		status.setCurrentValue(ArticleStatus.VALUE_HIDE);
		status.setCommentNo(0);
		status.setViewNo(0);
		status.setSupportCount(0);
		status.setOpposeCount(0);
		setStatus(status);
	}

	public void modify(ModifyArticleCommand command, 
			Set<ArticleChannel> showChannels, Image titleImage) {
		baseInfo.setAuthor(command.getAuthor());
		baseInfo.setContent(command.getContent());
		baseInfo.setTitle(command.getTitle());

		getBaseInfo().setTitleImage(titleImage);
		
		setShowChannels(showChannels);
	}

	public void show() {
		status.setCurrentValue(ArticleStatus.VALUE_SHOW);
	}

	public void hide() {
		status.setCurrentValue(ArticleStatus.VALUE_HIDE);
	}

	public void support() {
		getStatus().setSupportCount(getStatus().getSupportCount()+1);
	}
	public void cancelSupport() {
		if(getStatus().getSupportCount()>0){
			getStatus().setSupportCount(getStatus().getSupportCount()-1);
		}
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

	public Set<ArticleChannel> getShowChannels() {
		return showChannels;
	}

	public void setShowChannels(Set<ArticleChannel> showChannels) {
		this.showChannels = showChannels;
	}

	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}


}
