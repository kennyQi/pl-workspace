package pl.cms.domain.entity.article;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import pl.cms.domain.entity.M;
import pl.cms.domain.entity.image.Image;
@Embeddable
public class ArticleBaseInfo {

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "PRE_CONTENT", columnDefinition = M.TEXT_COLUM)
	private String preContent;
	
	@Column(name = "CONTENT", columnDefinition = M.TEXT_COLUM)
	private String content;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "SOURCE")
	private String source;
	
	/**
	 * 资讯首图
	 */
	private Image titleImage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPreContent() {
		return preContent;
	}

	public void setPreContent(String preContent) {
		this.preContent = preContent;
	}

	public Image getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(Image titleImage) {
		this.titleImage = titleImage;
	}

}
