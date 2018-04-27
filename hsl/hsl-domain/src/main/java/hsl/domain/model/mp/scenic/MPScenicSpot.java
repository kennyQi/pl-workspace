package hsl.domain.model.mp.scenic;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 旅游景点
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_MP +"SCENICSPOT")
public class MPScenicSpot extends BaseModel{

	/**
	 * 景点基本信息
	 */
	@Embedded
	private MPScenicSpotsBaseInfo scenicSpotBaseInfo;
	/**
	 * 景点地理信息(省市区经纬度等)
	 */
	@Embedded
	private MPScenicSpotsGeographyInfo scenicSpotGeographyInfo;
	/**
	 * 景区图片
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "scenicSpot")
	private List<MPImageSpec> images = new ArrayList<MPImageSpec>();

	public MPScenicSpotsBaseInfo getScenicSpotBaseInfo() {
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(MPScenicSpotsBaseInfo scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public MPScenicSpotsGeographyInfo getScenicSpotGeographyInfo() {
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			MPScenicSpotsGeographyInfo scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}

	public List<MPImageSpec> getImages() {
		return images;
	}

	public void setImages(List<MPImageSpec> images) {
		this.images = images;
	}

}