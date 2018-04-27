package hg.service.ad.command.position;

import javax.persistence.Column;

import hg.service.ad.base.BaseCommand;
import hg.system.model.M;

public class CreateAdPositionCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	/**
	 * 广告位名称
	 */
	private String name;

	/**
	 * 客户端类型
	 */
	private Integer clientType;

	public static int CLIENT_TYPE_COMMON = 0; // 客户端类型通用
	public static int CLIENT_TYPE_PC = 1; // 客户端类型 pc端
	public static int CLIENT_TYPE_H5 = 2; // 客户端类型 H5端
	public static int CLIENT_TYPE_ANDROID = 3; // 客户端类型 安卓端
	public static int CLIENT_TYPE_IOS = 4; // 客户端类型 IOS端

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图片服务用途
	 */
	private String imageUseTypeId;

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

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUseTypeId() {
		return imageUseTypeId;
	}

	public void setImageUseTypeId(String imageUseTypeId) {
		this.imageUseTypeId = imageUseTypeId;
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

}
