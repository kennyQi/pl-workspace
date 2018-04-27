package pl.cms.pojo.command.article;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import hg.common.component.BaseModel;
import hg.common.util.file.FdfsFileInfo;

/**
 * 新建文章
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class ModifyArticleCommand extends BaseModel {

	private String articleId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 正文
	 */
	private String content;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 文章显示频道
	 */
	private String[] showChannelIds;
	private String fdfsFileInfoJSON;
	/**
	 * 标题图片
	 */
	private FdfsFileInfo titleImageFileInfo;
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

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

	public String[] getShowChannelIds() {
		return showChannelIds;
	}

	public void setShowChannelIds(String[] showChannelIds) {
		this.showChannelIds = showChannelIds;
	}

	public FdfsFileInfo getTitleImageFileInfo() {
		if (titleImageFileInfo == null && StringUtils.isNotBlank(fdfsFileInfoJSON)) {
			setTitleImageFileInfo(JSON.parseObject(fdfsFileInfoJSON, FdfsFileInfo.class));
		}
		return titleImageFileInfo;
	}

	public void setTitleImageFileInfo(FdfsFileInfo titleImageFileInfo) {
		this.titleImageFileInfo = titleImageFileInfo;
	}

	public String getFdfsFileInfoJSON() {
		return fdfsFileInfoJSON;
	}

	public void setFdfsFileInfoJSON(String fdfsFileInfoJSON) {
		this.fdfsFileInfoJSON = fdfsFileInfoJSON;
	}

}
