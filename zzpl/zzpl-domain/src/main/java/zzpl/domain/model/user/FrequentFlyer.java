package zzpl.domain.model.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

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
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_ORDER + "FREQUENT_FLYER")
public class FrequentFlyer extends BaseModel {

	/**
	 * 用户编号
	 */
	@Column(name = "USER_ID", length = 64)
	private String userID;
	
	/***
	 * 乘客姓名
	 */
	@Column(name = "PASSENGER_NAME", length = 512)
	private String passengerName;

	/**
	 * 证件类型
	 * 0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证
	 */
	@Column(name = "ID_TYPE", length = 512)
	private String idType;

	/***
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 512)
	private String idNO;
	
	/**
	 * 联系人电话
	 */
	@Column(name = "TELEPHONE", length = 512)
	private String telephone;
	
	/***
	 * 乘客类型
	 * 1-成人，2-儿童
	 */
	@Column(name = "PASSENGER_TYPE", length = 512)
	private String passengerType;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 成本中心ID
	 */
	@Column(name = "COST_CENTER_ID", length = 512)
	private String costCenterID;
	
	/**
	 * 成本中心名称
	 */
	@Column(name = "COST_CENTER_NAME", length = 512)
	private String costCenterName;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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
	
	
}
