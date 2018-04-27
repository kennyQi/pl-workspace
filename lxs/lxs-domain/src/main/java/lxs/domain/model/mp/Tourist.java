package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

/**
 * @类功能说明：游客信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:14:32
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_MP + "TICKET_ORDER_TOURIST")
public class Tourist extends BaseModel {

	/**
	 * 订单ID
	 */
	@Column(name = "ORDER_ID", length = 512)
	private String orderID;

	/**
	 * 票号
	 */
	@Column(name = "TICKET_NO", length = 512)
	private String ticketNo;

	/**
	 * 门票二维码图片地址
	 */
	@Column(name = "QRCode_URL", length = 512)
	private String qrCodeUrl;

	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 512)
	private String name;

	/**
	 * 年龄
	 */
	@Column(name = "AGE")
	private Integer age;

	/**
	 * 证件类型(1、 身份证；2、 军官证；3、 驾驶证；4、 护照)
	 * */
	@Column(name = "IDTYPE")
	private Integer idType;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 512)
	private String idNo;

	/**
	 * 性别
	 */
	@Column(name = "GENDER")
	private Integer gender;

	/**
	 * 出生年月
	 */
	@Column(name = "BIRTHDAY")
	private Date birthday;

	/**
	 * 电话(必须手机号码)
	 */
	@Column(name = "TELEPHONE", length = 512)
	private String telephone;

	/**
	 * 户籍地址
	 */
	@Column(name = "ADDRESS", length = 512)
	private String address;

	/**
	 * 民族
	 */
	@Column(name = "NATION", length = 512)
	private String nation;

	/** 等待支付 */
	public final static Integer GROUP_TICKET_CURRENT_PAY_WAIT = 0;
	/** 支付成功 */
	public final static Integer GROUP_TICKET_CURRENT_PAY_SUCC = 1;
	/** 出票成功 (支付成功后状态直接变成出票成功) */
	public final static Integer GROUP_TICKET_CURRENT_OUT_SUCC = 2;
	/** 交易成功 */
	public final static Integer GROUP_TICKET_CURRENT_DEAL_SUCC = 3;
	/**
	 * 交易关闭 1.由于票务在规定之间内未支付或者有经销商自主发起的取消订单操作。取消订单为同一订单号所有票务统一取消
	 * 2.当退款状态为“退款成功”，标记退款状态为交易关闭
	 **/
	public final static Integer GROUP_TICKET_CURRENT_DEAL_CLOSE = 4;

	/**
	 * 退款成功(当票务中所有景区的状态为已退款)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_ALL_SUCC = 1;
	/**
	 * 部分退款成功(当票务中所有景区的状态为已退款和已结算)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_SOME_SUCC = 2;
	/**
	 * 退款待处理(：当景区状态中存在待退款状态时显示)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE = 3;
	/**
	 * 当前状态
	 */
	@Column(name = "CURRENT")
	private Integer current;
	/**
	 * 退款状态
	 */
	@Column(name = "REFUND_CURRENT")
	private Integer refundCurrent;
	/**
	 * 退款时间
	 */
	@Column(name = "REFUND_DATE")
	private Date refundDate;
	/**
	 * 订单关闭时间 （超时为支付 或者 经销商取消订单）
	 */
	@Column(name = "CLOSE_DATE")
	private Date closeDate;

	/** 无退款操作 */
	public final static Integer NOTHING = 0;
	/** 等待退款 系统触发 */
	public final static Integer WAIT_TO_REFUND_SYS = 1;
	/** 退款成功 系统触发 */
	public final static Integer REFUNDED_SYS = 2;
	/** 等待退款 用户触发 */
	public final static Integer WAIT_TO_REFUND_USER = 3;
	/** 退款成功 用户触发 */
	public final static Integer REFUNDED_USER = 4;

	/**
	 * 本地订单状态
	 */
	@Column(name = "LOCAL_PAY_STATUS")
	private Integer localStatus;

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getRefundCurrent() {
		return refundCurrent;
	}

	public void setRefundCurrent(Integer refundCurrent) {
		this.refundCurrent = refundCurrent;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

}