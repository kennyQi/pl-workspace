package hg.dzpw.domain.model.scenicspot;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：景区图片
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-10上午11:17:43
 * @版本：
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SCENIC_PIC")
public class ScenicSpotPic extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	/**
	 * 图片地址
	 */
	@Column(name = "URL", length = 1024)
	private String url;

	/**
	 * 图片名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
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
