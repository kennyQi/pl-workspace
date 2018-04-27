package zzpl.api.client.dto.user;

import java.util.Date;

/**
 * 
 * @类功能说明：常用乘机人
 * @类修改者：
 * @修改日期：2015年9月21日上午9:45:52
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月21日上午9:45:52
 */
public class FrequentFlyerDTO {

	/**
	 * id
	 */
	private String id;

	/**
	 * 用户编号
	 */
	private String userID;

	/***
	 * 乘客姓名
	 */
	private String passengerName;

	/**
	 * 证件类型 0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证
	 */
	private String idType;

	/***
	 * 证件号
	 */
	private String idNO;

	/**
	 * 联系人电话
	 */
	private String telephone;

	/***
	 * 乘客类型 1-成人，2-儿童
	 */
	private String passengerType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 成本中心ID
	 */
	private String costCenterID;

	/**
	 * 成本中心名称
	 */
	private String costCenterName;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNO() {
		return idNO;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
