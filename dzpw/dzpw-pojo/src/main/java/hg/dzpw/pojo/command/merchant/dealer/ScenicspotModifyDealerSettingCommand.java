package hg.dzpw.pojo.command.merchant.dealer;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：景区对经销商设置（景区后台）
 * @类修改者：
 * @修改日期：2015-2-9下午6:13:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-9下午6:13:57
 */
@SuppressWarnings("serial")
public class ScenicspotModifyDealerSettingCommand extends DZPWMerchantBaseCommand {

	/**
	 * 经销商ID(不能为空)
	 */
	private String dealerId;
	
	/**
	 * 押金数额
	 */
	private Double pledgeAmount;
	
	/**
	 * 是否可用
	 */
	private Boolean useable;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Double getPledgeAmount() {
		return pledgeAmount;
	}

	public void setPledgeAmount(Double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}

	public Boolean getUseable() {
		return useable;
	}

	public void setUseable(Boolean useable) {
		this.useable = useable;
	}

}
