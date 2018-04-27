package pl.cms.domain.entity.image;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import pl.cms.domain.entity.M;

/**
 * @类功能说明：图片——一个Image代表一张内容相同的图片，以及它的一组大小不同的裁剪图集合
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:03:26
 */
@Embeddable
public class Image {
	
	/**
	 * 图片系统中的标识
	 */
	@Column(name = "IMG_IMAGE_ID", length = 64)
	private String imageId;
	
	/**
	 * 图片标题
	 */
	@Column(name = "IMG_TITLE", length = 512)
	private String title;

	/**
	 * 图片备注说明
	 */
	@Column(name = "IMG_REMARK", columnDefinition = M.TEXT_COLUM)
	private String remark;

	/**
	 * 图片访问相对地址，urlMaps
	 */
	@Column(name = "IMG_URL_JSON")
	private String urlMapsJSON;

	@Transient
	private Map<String, String> urlMaps;
	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrlMapsJSON() {
		if (StringUtils.isBlank(urlMapsJSON) && urlMaps != null) {
			setUrlMapsJSON(JSON.toJSONString(urlMaps));
		}
		return urlMapsJSON;
	}

	public void setUrlMapsJSON(String urlMapsJSON) {
		this.urlMapsJSON = urlMapsJSON;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getUrlMaps() {
		if (urlMaps == null && StringUtils.isNotBlank(urlMapsJSON)) {
			urlMaps = JSON.parseObject(urlMapsJSON, HashMap.class);
		}
		return urlMaps;
	}

	public void setUrlMaps(Map<String, String> urlMaps) {
		this.urlMaps = urlMaps;
	}

	

}