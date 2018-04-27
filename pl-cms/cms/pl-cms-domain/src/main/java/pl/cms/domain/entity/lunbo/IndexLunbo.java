package pl.cms.domain.entity.lunbo;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import pl.cms.domain.entity.M;
import pl.cms.pojo.command.lunbo.CreateIndexLunboCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboTitleCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboUrlCommand;
import pl.cms.pojo.command.lunbo.ModifyLunboCommand;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午4:02:33
 * 
 */
@Entity
@Table(name = "CMS_INDEX_LUNBO")
@SuppressWarnings("serial")
public class IndexLunbo extends BaseModel {

	@Column(name = "AD_ID", length = 64)
	private String adId;

	@Column(name = "URL", length = 512)
	private String url;

	@Column(name = "IMAGE_URL", length = 512)
	private String imageUrl;

	@Transient
	private Map<String, String> urlMaps;

	@Column(name = "TITLE", length = 512)
	private String title;

	@Type(type = "yes_no")
	@Column(name = "STATUS_SHOW")
	private Boolean isShow;
	
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public void create(CreateIndexLunboCommand command, String adId,
			String imageUrl) {
		setId(UUIDGenerator.getUUID());

		setAdId(adId);
		setCreateDate(new Date());
		setUrl(command.getUrl());
		setImageUrl(imageUrl);
		setTitle(command.getTitle());
		setIsShow(command.getIsShow());
	}

	public void modify(ModifyLunboCommand command) {
		setTitle(command.getTitle());
		setIsShow(command.getIsShow());
		setImageUrl(command.getImageUrl());
		setUrl(command.getUrl());
	}

	public void modifyTitle(ModifyIndexLunboTitleCommand command) {
		setTitle(command.getTitle());
	}

	public void modifyImage(String imageUrl) {
		setImageUrl(imageUrl);
	}

	public void modifyUrl(ModifyIndexLunboUrlCommand command) {
		setUrl(command.getUrl());
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getUrlMaps() {
		if (urlMaps == null && StringUtils.isNotBlank(imageUrl)) {
			urlMaps = JSON.parseObject(imageUrl, HashMap.class);
		}
		return urlMaps;
	}

	public void setUrlMaps(Map<String, String> urlMaps) {
		this.urlMaps = urlMaps;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
