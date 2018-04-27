package hg.service.ad.domain.model.position;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.service.ad.command.position.CreateAdPositionCommand;
import hg.service.ad.command.position.ModifyAdPositionCommand;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：广告位
 * @类修改者：
 * @修改日期：2014年12月11日下午3:55:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午3:55:36
 * 
 */
@Entity
@Table(name = "AD_POSITION")
public class AdPosition extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 广告位的基本信息
	 */
	@Embedded
	private AdPositionBaseInfo baseInfo;
	/**
	 * 广告位的展示信息
	 */
	@Embedded
	private AdPositionShowInfo showInfo;

	public void create(CreateAdPositionCommand command) {

		setId(UUIDGenerator.getUUID());

		setBaseInfo(new AdPositionBaseInfo());
		getBaseInfo().setClientType(command.getClientType());
		getBaseInfo().setDescription(command.getDescription());
		getBaseInfo().setImageUseTypeId(command.getImageUseTypeId());
		getBaseInfo().setName(command.getName());

		setShowInfo(new AdPositionShowInfo());
		getShowInfo().setChangeSpeedSecond(command.getChangeSpeedSecond());
		getShowInfo().setLoadNo(command.getLoadNo());
		getShowInfo().setShowNo(command.getShowNo());

	}

	public void modify(ModifyAdPositionCommand command) {

		setId(UUIDGenerator.getUUID());

		getBaseInfo().setDescription(command.getDescription());
		getBaseInfo().setName(command.getName());

		getShowInfo().setChangeSpeedSecond(command.getChangeSpeedSecond());
		getShowInfo().setLoadNo(command.getLoadNo());
		getShowInfo().setShowNo(command.getShowNo());

	}

	public AdPositionBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(AdPositionBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public AdPositionShowInfo getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(AdPositionShowInfo showInfo) {
		this.showInfo = showInfo;
	}

}