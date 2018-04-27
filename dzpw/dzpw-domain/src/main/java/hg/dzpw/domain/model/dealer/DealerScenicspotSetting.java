package hg.dzpw.domain.model.dealer;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.command.merchant.dealer.ScenicspotModifyDealerSettingCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

/**
 * @类功能说明：景区对经销商的设置
 * @类修改者：
 * @修改日期：2015-2-10上午9:24:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-10上午9:24:22
 */
@Entity
@Table(name = M.TABLE_PREFIX + "DEALER_SETTING")
@SuppressWarnings("serial")
public class DealerScenicspotSetting extends BaseModel {

	/**
	 * 所属景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	/**
	 * 所属经销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEALER_ID")
	private Dealer dealer;

	/**
	 * 押金数额
	 */
	@Column(name = "PLEDGE_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double pledgeAmount = 0d;
	
	/**
	 * 是否可用(默认可用)
	 */
	@Type(type = "yes_no")
	@Column(name = "USEABLE")
	private Boolean useable = true;

	/**
	 * @方法功能说明：创建景区对经销商设置
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12上午11:02:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param scenicSpot
	 * @参数：@param dealer
	 * @return:void
	 * @throws
	 */
	public void createDealerSetting(ScenicspotModifyDealerSettingCommand command, ScenicSpot scenicSpot, Dealer dealer) {
		if (command == null) return;
		
		if (scenicSpot == null)
			throw new RuntimeException("景区数据异常");
		if (dealer == null)
			throw new RuntimeException("经销商数据异常");
		
		setId(UUIDGenerator.getUUID());
		setScenicSpot(scenicSpot);
		setDealer(dealer);
		if (command.getPledgeAmount() != null)
			setPledgeAmount(command.getPledgeAmount());
		if (command.getUseable() != null)
			setUseable(command.getUseable());
	}
	
	/**
	 * @方法功能说明：修改景区对经销商设置
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12上午11:02:25
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyDealerSetting(ScenicspotModifyDealerSettingCommand command) {
		if (command == null
				|| !StringUtils.equals(command.getScenicSpotId(), scenicSpot.getId())
				|| !StringUtils.equals(command.getDealerId(), dealer.getId()))
			return;

		if (command.getPledgeAmount() != null)
			setPledgeAmount(command.getPledgeAmount());
		if (command.getUseable() != null)
			setUseable(command.getUseable());
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Double getPledgeAmount() {
		return pledgeAmount;
	}
	
	public void setPledgeAmount(Double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}

	public Boolean getUseable() {
		if (useable == null)
			useable = false;
		return useable;
	}

	public void setUseable(Boolean useable) {
		this.useable = useable;
	}

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

}
