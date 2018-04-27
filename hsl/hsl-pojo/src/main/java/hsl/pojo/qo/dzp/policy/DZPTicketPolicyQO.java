package hsl.pojo.qo.dzp.policy;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import hsl.pojo.util.HSLConstants;

/**
 * 电子票务-门票政策QO
 * Created by huanggg on 2016/3/3.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpTicketPolicyDAO")
public class DZPTicketPolicyQO extends BaseQo {

	/**
	 * 票务名称
	 */
	@QOAttr(name = "baseInfo.name", type = QOAttrType.LIKE_ANYWHERE)
	private String name;

	/**
	 * 政策类型
	 *
	 * @see HSLConstants.DZPWTicketPolicyType
	 */
	@QOAttr(name = "type", type = QOAttrType.IN)
	private Integer[] type;

	/**
	 * 直接关联的景区
	 */
	@QOAttr(name = "scenicSpot.id")
	private String scenicSpotId;

	/**
	 * 间接关联的景区（比如联票含多个景区，有1个符合就算）
	 */
	private String[] singleScenicSpotIds;

	/**
	 * 是否获取价格日历
	 */
	private boolean priceFetch;

	/**
	 * 是否获取门票政策下的政策详情
	 */
	private boolean singleTicketPoliciesFetch;

	/**
	 * 是否已被电子票务下架
	 */
	@QOAttr(name = "status.finished")
	private Boolean finished;

	/**
	 * 是否已被本平台关闭
	 */
	@QOAttr(name = "status.closed")
	private Boolean closed;

	/**
	 * 版本
	 */
	@QOAttr(name = "version")
	private Integer version;

	public Integer[] getType() {
		return type;
	}

	public void setType(Integer... type) {
		this.type = type;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String[] getSingleScenicSpotIds() {
		return singleScenicSpotIds;
	}

	public void setSingleScenicSpotIds(String... singleScenicSpotIds) {
		this.singleScenicSpotIds = singleScenicSpotIds;
	}

	public boolean isPriceFetch() {
		return priceFetch;
	}

	public void setPriceFetch(boolean priceFetch) {
		this.priceFetch = priceFetch;
	}

	public boolean isSingleTicketPoliciesFetch() {
		return singleTicketPoliciesFetch;
	}

	public void setSingleTicketPoliciesFetch(boolean singleTicketPoliciesFetch) {
		this.singleTicketPoliciesFetch = singleTicketPoliciesFetch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
