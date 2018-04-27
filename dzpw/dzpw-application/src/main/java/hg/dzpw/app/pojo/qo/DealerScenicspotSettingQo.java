package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：景区经销商设置查询
 * @类修改者：
 * @修改日期：2015-2-10上午10:44:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-10上午10:44:12
 */
@SuppressWarnings("serial")
public class DealerScenicspotSettingQo extends BaseQo {

	/**
	 * 所属景区
	 */
	private String scenicSpotId;
	
	/**
	 * 是否查询景区
	 */
	private boolean scenicSpotAble = false;

	/**
	 * 所属经销商
	 */
	private DealerQo dealerQo;
	
	/**
	 * 押金数额
	 */
	private Double pledgeAmountMin;
	private Double pledgeAmountMax;

	/**
	 * 是否可用
	 */
	private Boolean useable;
	
	
	public boolean isScenicSpotAble() {
		return scenicSpotAble;
	}

	public void setScenicSpotAble(boolean scenicSpotAble) {
		this.scenicSpotAble = scenicSpotAble;
	}

	public boolean needQuery() {
		if (pledgeAmountMin != null || pledgeAmountMax != null
				|| useable != null)
			return true;
		return false;
	}
	
	public boolean onlyUseableFalse() {
		if (pledgeAmountMin == null && pledgeAmountMax == null
				&& useable != null && !useable)
			return true;
		return false;
	}
	
	public boolean onlyUseableTrue() {
		if (pledgeAmountMin == null && pledgeAmountMax == null
				&& useable != null && useable)
			return true;
		return false;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public DealerQo getDealerQo() {
		return dealerQo;
	}

	public void setDealerQo(DealerQo dealerQo) {
		this.dealerQo = dealerQo;
	}

	public Double getPledgeAmountMin() {
		return pledgeAmountMin;
	}

	public void setPledgeAmountMin(Double pledgeAmountMin) {
		this.pledgeAmountMin = pledgeAmountMin;
	}

	public Double getPledgeAmountMax() {
		return pledgeAmountMax;
	}

	public void setPledgeAmountMax(Double pledgeAmountMax) {
		this.pledgeAmountMax = pledgeAmountMax;
	}

	public Boolean getUseable() {
		return useable;
	}

	public void setUseable(Boolean useable) {
		this.useable = useable;
	}

}
