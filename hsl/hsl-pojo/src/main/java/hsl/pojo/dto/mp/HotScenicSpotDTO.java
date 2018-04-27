package hsl.pojo.dto.mp;
import hsl.pojo.dto.BaseDTO;
import java.util.Date;

@SuppressWarnings("serial")
public class HotScenicSpotDTO extends BaseDTO{
	
	/**
	 * 加入热门景区时间
	 */
	private Date createDate;
	
	/**
	 * 加入当前热门景区时间
	 */
	private Date openDate;
	/**
	 * 景点基本信息
	 */
	private ScenicSpotsBaseInfoDTO scenicSpotBaseInfo;
	/**
	 * 平台景点id
	 */
	private String scenicSpotId;


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ScenicSpotsBaseInfoDTO getScenicSpotBaseInfo() {
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(ScenicSpotsBaseInfoDTO scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	
	
}
