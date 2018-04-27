package slfx.mp.domain.model.scenicspot;

import hg.common.component.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;

import slfx.mp.domain.model.M;

/**
 * 旅游景点
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SCENIC_SPOT")
public class ScenicSpot extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 同程景区信息
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TC_SCENIC_SPOT_ID")
	private TCScenicSpots tcScenicSpotInfo;
	/**
	 * 景点基本信息
	 */
	@Embedded
	private ScenicSpotsBaseInfo scenicSpotBaseInfo;
	/**
	 * 景点地理信息(省市区经纬度等)
	 */
	@Embedded
	private ScenicSpotsGeographyInfo scenicSpotGeographyInfo;
	
	/**
	 * 景区图片
	 */
	@Where(clause="SIZE_ = 3")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scenicSpot")
	private List<ImageSpecTemp> images = new ArrayList<ImageSpecTemp>();

	public TCScenicSpots getTcScenicSpotInfo() {
		return super.getProperty(tcScenicSpotInfo, TCScenicSpots.class);
	}

	public void setTcScenicSpotInfo(TCScenicSpots tcScenicSpotInfo) {
		this.tcScenicSpotInfo = tcScenicSpotInfo;
	}

	public ScenicSpotsBaseInfo getScenicSpotBaseInfo() {
		if (scenicSpotBaseInfo == null) {
			scenicSpotBaseInfo = new ScenicSpotsBaseInfo();
		}
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(ScenicSpotsBaseInfo scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public ScenicSpotsGeographyInfo getScenicSpotGeographyInfo() {
		if (scenicSpotGeographyInfo == null) {
			scenicSpotGeographyInfo = new ScenicSpotsGeographyInfo();
		}
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			ScenicSpotsGeographyInfo scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}

	@SuppressWarnings("unchecked")
	public List<ImageSpecTemp> getImages() {
		return super.getProperty(images, ArrayList.class);
	}

	public void setImages(List<ImageSpecTemp> images) {
		this.images = images;
	}

}