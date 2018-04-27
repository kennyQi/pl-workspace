package lxs.api.v1.dto.mp;

import java.util.Date;
import java.util.List;

import lxs.api.v1.dto.BaseDTO;

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
public class TouristDTO extends BaseDTO {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 证件类型(1、 身份证；2、 军官证；3、 驾驶证；4、 护照)
	 * */
	private Integer idType;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 出生年月
	 */
	private Date birthday;

	/**
	 * 电话(必须手机号码)
	 */
	private String telephone;

	/**
	 * 户籍地址
	 */
	private String address;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 当前状态 只在订单详情接口使用此字段
	 */
	private Integer current;

	/**
	 * 票号 只在订单详情接口使用此字段
	 */
	private String ticketNo;

	/**
	 * 门票二维码图片地址
	 */
	private String qrCodeUrl;

	private Integer localStatus;

	private List<UseRecordDTO> useRecordDTOs;

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
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

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
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

	public List<UseRecordDTO> getUseRecordDTOs() {
		return useRecordDTOs;
	}

	public void setUseRecordDTOs(List<UseRecordDTO> useRecordDTOs) {
		this.useRecordDTOs = useRecordDTOs;
	}

}