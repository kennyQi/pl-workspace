package hg.service.ad.command.position;

import javax.persistence.Column;

import hg.service.ad.base.BaseCommand;
import hg.system.model.M;

public class ModifyAdPositionCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	private String adPositionId;

	/**
	 * 广告位名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 显示条数
	 */
	@Column(name = "SHOW_NO", columnDefinition = M.NUM_COLUM)
	private Integer showNo;

	/**
	 * 加载条数
	 */
	@Column(name = "LOAD_NO", columnDefinition = M.NUM_COLUM)
	private Integer loadNo;

	/**
	 * 切换速度
	 */
	@Column(name = "CHANGE_SPEED_SECOND", columnDefinition = M.NUM_COLUM)
	private Integer changeSpeedSecond;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShowNo() {
		return showNo;
	}

	public void setShowNo(Integer showNo) {
		this.showNo = showNo;
	}

	public Integer getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	public Integer getChangeSpeedSecond() {
		return changeSpeedSecond;
	}

	public void setChangeSpeedSecond(Integer changeSpeedSecond) {
		this.changeSpeedSecond = changeSpeedSecond;
	}

	public String getAdPositionId() {
		return adPositionId;
	}

	public void setAdPositionId(String adPositionId) {
		this.adPositionId = adPositionId;
	}

}
