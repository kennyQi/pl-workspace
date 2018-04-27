package hsl.pojo.command.traveler;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：修改游客
 * @类修改者：
 * @修改日期：2015-9-29下午3:07:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-29下午3:07:33
 */
@SuppressWarnings("serial")
public class ModifyTravelerCommand extends BaseCommand {

	/**
	 * 游客ID
	 */
	private String travelerId;

	/**
	 * 来自的用户(用于校验是否属于该用户)
	 */
	private String fromUserId;

	// 游客基本信息

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 游客类别
	 * 
	 * @see HSLConstants.Traveler
	 */
	private Integer type;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 证件类型
	 * 
	 * @see HSLConstants.Traveler
	 */
	private Integer idType;

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

}
