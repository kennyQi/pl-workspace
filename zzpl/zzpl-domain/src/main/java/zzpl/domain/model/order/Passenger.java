package zzpl.domain.model.order;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_ORDER + "PASSENGER")
public class Passenger extends BaseModel {

	/***
	 * 乘客姓名
	 */
	@Column(name = "PASSENGER_NAME", length = 512)
	private String passengerName;

	/***
	 * 乘客类型
	 * 1-成人，2-儿童
	 */
	@Column(name = "PASSENGER_TYPE", length = 512)
	private String passengerType;

	/**
	 * 国籍二字码
	 */
	@Column(name = "NATIONALITY", length = 512)
	private String nationality;

	/**
	 * 出生年月
	 */
	@Column(name = "BIRTHDAY", columnDefinition = M.DATE_COLUM)
	private Date birthday;

	/**
	 * 乘客性别
	 * 
	 * 1-男0-女
	 */
	@Column(name = "SEX", columnDefinition = M.NUM_COLUM)
	private Integer sex;

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
	 * 证件有效期
	 */
	@Column(name = "EXPIRY_DATE", columnDefinition = M.DATE_COLUM)
	private Date expiryDate;

	/**
	 * 电话
	 */
	@Column(name = "TELEPHONE", length = 512)
	private String telephone;

	/**
	 * 票号
	 */
	@Column(name = "AIR_ID", length = 512)
	private String airID;

	/**
	 * 状态
	 */
	@Column(name = "STATUS", length = 512)
	private String status;

	/**
	 * ---------------------------------取消订单信息----------------------------------
	 * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	@Column(name = "REFUND_TYPE", length = 512)
	private String refundType;

	/***
	 * 申请理由
	 */
	@Column(name = "REFUND_MEMO", length = 512)
	private String refundMemo;

	/**
	 * 订单类型 1：国内机票 2：国际机票
	 */
	@Column(name = "ORDER_TYPE", length = 512)
	private String orderType;

	public final static String GN_ORDER = "1";

	public final static String GJ_ORDER = "2";

	/**
	 * 关联国内机票
	 */
	@ManyToOne
	@JoinColumn(name = "FLIGHT_ORDER_ID")
	private FlightOrder flightOrder;

	/**
	 * 关联国际机票
	 */
	@ManyToOne
	@JoinColumn(name = "GJ_FLIGHT_ORDER_ID")
	private GJFlightOrder gjFlightOrder;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public FlightOrder getFlightOrder() {
		return flightOrder;
	}

	public void setFlightOrder(FlightOrder flightOrder) {
		this.flightOrder = flightOrder;
	}

	public GJFlightOrder getGjFlightOrder() {
		return gjFlightOrder;
	}

	public void setGjFlightOrder(GJFlightOrder gjFlightOrder) {
		this.gjFlightOrder = gjFlightOrder;
	}

}
