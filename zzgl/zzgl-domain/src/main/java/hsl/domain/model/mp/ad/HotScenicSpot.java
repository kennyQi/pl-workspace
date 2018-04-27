package hsl.domain.model.mp.ad;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.mp.scenic.MPScenicSpot;
import hsl.domain.model.mp.scenic.MPScenicSpotsBaseInfo;
import hsl.domain.model.mp.scenic.MPScenicSpotsGeographyInfo;
import hsl.pojo.command.ad.CreatePCHotSecnicSpotCommand;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @类功能说明：PC端
 * @类修改者：
 * @修改日期：2014年12月12日上午10:05:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:05:27
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_MP+"PC_HOT_SCENICSPOT")
public class HotScenicSpot extends BaseModel{
	/**
	 * 关联的景区
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SCENICSPOT_ID")
	private MPScenicSpot scenicSpot;
	/**
	 * 广告ID
	 */
	@Column(name = "AD_ID", length = 64)
	private String adId;

	public MPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(MPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public void create(CreatePCHotSecnicSpotCommand command) {
		this.setId(UUIDGenerator.getUUID());
		scenicSpot=new MPScenicSpot();
		scenicSpot.setId(command.getScenicSpotId());
		MPScenicSpotsBaseInfo baseInfo=new MPScenicSpotsBaseInfo();
		MPScenicSpotsGeographyInfo geographyInfo=new MPScenicSpotsGeographyInfo();
		baseInfo.setAlias(command.getAlias());
		baseInfo.setGrade(command.getGrade());
		baseInfo.setImage(command.getImage());
		baseInfo.setIntro(command.getIntro());
		baseInfo.setName(command.getName());
		scenicSpot.setScenicSpotBaseInfo(baseInfo);
		geographyInfo.setAddress(command.getAddress());
		geographyInfo.setCityCode(command.getCity());
		geographyInfo.setProvinceCode(command.getProvince());
		geographyInfo.setTraffic(command.getTraffic());
		scenicSpot.setScenicSpotGeographyInfo(geographyInfo);
		this.setAdId(command.getAdId());
	}

}