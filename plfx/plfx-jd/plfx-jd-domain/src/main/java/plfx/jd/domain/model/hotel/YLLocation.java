package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "LOCATION")
public class YLLocation extends BaseModel {

	/**
	 * 是否有水印
	 */
	@Column(name = "WATERMARK")
	public int waterMark;

	/***
	 * 图片规格
	 */
	@Column(name = "SIZE")
	public String size;

	/***
	 * 图片url
	 */
	@Column(name = "URL")
	public String url;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "YL_HOTEL_IMAGE_ID")
	private YLHotelImage ylHotelImage;
	
	
	public YLHotelImage getYlHotelImage() {
		return ylHotelImage;
	}

	public void setYlHotelImage(YLHotelImage ylHotelImage) {
		this.ylHotelImage = ylHotelImage;
	}

	public int getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(int waterMark) {
		this.waterMark = waterMark;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
