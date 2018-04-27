package hsl.domain.model.dzp.scenicspot;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.*;

/**
 * 景区图片
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_DZP + "SCENIC_SPOT_PIC")
@SuppressWarnings("serial")
public class DZPScenicSpotPic extends BaseModel {

	/**
	 * 所属景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private DZPScenicSpot scenicSpot;

	/**
	 * 图片地址
	 */
	@Column(name = "URL", length = 256)
	private String url;
	
	/**
	 * 图片名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	public DZPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(DZPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
