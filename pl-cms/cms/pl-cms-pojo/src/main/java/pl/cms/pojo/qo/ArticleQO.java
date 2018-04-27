package pl.cms.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

@QOConfig(daoBeanId = "articleDao")
@SuppressWarnings("serial")
public class ArticleQO extends BaseQo {

	@QOAttr(name = "baseInfo.title", ifTrueUseLike = "titleLike")
	private String title;

	@QOAttr(name = "baseInfo.author", ifTrueUseLike = "authorLike")
	private String author;

	private Boolean titleLike;

	private Boolean authorLike;

	private String showChannelId;

	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.LT)
	private Date ltCreateDate;

	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.GT)
	private Date gtCreateDate;
	
	@QOAttr(name = "showChannels", type = QOAttrType.FATCH_EAGER)
	private Boolean fetchChannel = false;

	@QOAttr(name = "baseInfo.titleImage", type = QOAttrType.FATCH_EAGER)
	private Boolean fetchImage = false;

	// 排序字段
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private int orderByCreateDate;

	public Boolean getAuthorLike() {
		return authorLike;
	}

	public void setAuthorLike(Boolean authorLike) {
		this.authorLike = authorLike;
	}

	public int getOrderByCreateDate() {
		return orderByCreateDate;
	}

	public void setOrderByCreateDate(int orderByCreateDate) {
		this.orderByCreateDate = orderByCreateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(Boolean titleLike) {
		this.titleLike = titleLike;
	}

	public String getShowChannelId() {
		return showChannelId;
	}

	public void setShowChannelId(String showChannelId) {
		this.showChannelId = showChannelId;
	}

	public Boolean getFetchChannel() {
		return fetchChannel;
	}

	public void setFetchChannel(Boolean fetchChannel) {
		this.fetchChannel = fetchChannel;
	}

	public Boolean getFetchImage() {
		return fetchImage;
	}

	public void setFetchImage(Boolean fetchImage) {
		this.fetchImage = fetchImage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
