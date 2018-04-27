package hg.dzpw.pojo.command.platform.tourist;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.Date;

/**
 * @类功能说明：修改游客基本信息
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-12-06上午10:45:18
 */
public class PlatformModifyTouristCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	private String touristId;
	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 证件类型
	 */
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
	 * 户籍地址
	 */
	private String address;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 身份证照片
	 */
	private String imageUrl;

	/**
	 * 购买金额
	 */
	private Double buyAmountTotal;
	
	private String telephone;

	public Double getBuyAmountTotal() {
		return buyAmountTotal;
	}

	public void setBuyAmountTotal(Double buyAmountTotal) {
		this.buyAmountTotal = buyAmountTotal;
	}

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
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
		this.idNo = idNo == null ? null : idNo.trim();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation == null ? null : nation.trim();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl == null ? null : imageUrl.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}