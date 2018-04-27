package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 乘客信息
 * @author guotx
 * 2015-07-07
 */
@XStreamAlias("passengerInfo")
public class PassengerInfo {
	/**
	 * 机票票号，必填
	 */
	private String ordDetEticketNo;
	/**
	 * 乘客姓名，必填
	 */
	private String passengerName;
	/**
	 * 乘客类型，必填
	 * 1—般成人 2—学生 3—青年
	 * 4---移民 5--劳务 6--海员
	 * 7--特殊身份 8--一般儿
	 */
	private String passengerType;
	/**
	 * 证件类型，必填
	 * 1-护照， 2-港澳通行证， 3-台胞证，
	 * 4-台胞通行证， 5-回乡证， 6-国际海员证
	 */
	private String idType;
	/**
	 * 证件号，非必填
	 */
	private String idNo;
	/**
	 * 签发国家，非必填
	 */
	private String issueCountry;
	/**
	 * 证件有效期，非必填
	 */
	private String expiryDate;
	/**
	 * 出生年月，非必填，如1956-10-14
	 */
	private String birthday;
	/**
	 * 性别，必填
	 * 1-男 0-女
	 */
	private String sex;
	/**
	 * 手机，非必填
	 */
	private String telephone;
	/**
	 * 旅客居住地址
	 */
	private String resiAddr;
	/**
	 * 旅客居住邮编
	 */
	private String resiPost;
	/**
	 * 旅客目的地址
	 */
	private String dstAddr;
	/**
	 * 旅客目的邮编
	 */
	private String dstPost;
	/**
	 * 旅客国籍
	 * 如：CN
	 */
	private String nationality;
	/**
	 * 机票状态
	 * 1.已订票 2.已出票 3.申请退票 4.退票完成
	 * 5.客户删除 6.自动出票失败 7.申请废票 8.申请取消
	 * 9.退废票被拒绝 10.退废票被锁定 11.再次申请退废票 12.申请退废票解锁
	 * 13.供应商拒绝退废票 14.必须退废票 15.退票时供应商退分润成功 16.取消时供应商退分润成功
	 * 17.客服申请退票 18.客服申请退票成功 19.供应商拒绝出票 20.拒绝出票
	 * 21.必须出票 22.拒绝出票时供应商退分润成功 24.废票完成
	 * 25.暂缓退款 26.退票理由审核拒绝 27.申请撤单
	 */
	private String ticketType;
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getOrdDetEticketNo() {
		return ordDetEticketNo;
	}
	public void setOrdDetEticketNo(String ordDetEticketNo) {
		this.ordDetEticketNo = ordDetEticketNo;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIssueCountry() {
		return issueCountry;
	}
	public void setIssueCountry(String issueCountry) {
		this.issueCountry = issueCountry;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getResiAddr() {
		return resiAddr;
	}
	public void setResiAddr(String resiAddr) {
		this.resiAddr = resiAddr;
	}
	public String getResiPost() {
		return resiPost;
	}
	public void setResiPost(String resiPost) {
		this.resiPost = resiPost;
	}
	public String getDstAddr() {
		return dstAddr;
	}
	public void setDstAddr(String dstAddr) {
		this.dstAddr = dstAddr;
	}
	public String getDstPost() {
		return dstPost;
	}
	public void setDstPost(String dstPost) {
		this.dstPost = dstPost;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
}
