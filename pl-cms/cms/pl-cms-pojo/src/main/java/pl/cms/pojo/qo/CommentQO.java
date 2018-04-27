package pl.cms.pojo.qo;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class CommentQO extends BaseQo {
	
	private String articleId;
	//文章标题
	private String title;
	
	//作者
	private String author;
	
	private Boolean isChecked;
	
	private String beginTime;
	
	private String endTime;
		
	private Boolean fetchArticle;
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}


	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getFetchArticle() {
		return fetchArticle;
	}

	public void setFetchArticle(Boolean fetchArticle) {
		this.fetchArticle = fetchArticle;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
