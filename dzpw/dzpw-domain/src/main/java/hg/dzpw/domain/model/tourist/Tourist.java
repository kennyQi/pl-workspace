package hg.dzpw.domain.model.tourist;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;
import hg.dzpw.pojo.command.platform.tourist.PlatformCreateTouristCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformModifyTouristCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

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
@Entity
@Table(name = M.TABLE_PREFIX + "TOURIST")
@SuppressWarnings("serial")
public class Tourist extends BaseModel {

	/**
	 * 身份证
	 */
	public static final Integer TOURIST_ID_TYPE_SFZ = 1;
	/**
	 * 军官证
	 */
	public static final Integer TOURIST_ID_TYPE_JGZ = 2;
	/**
	 * 驾驶证
	 */
	public static final Integer TOURIST_ID_TYPE_JSZ = 3;
	/**
	 * 护照
	 */
	public static final Integer TOURIST_ID_TYPE_HZ = 4;
	
	
	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	/**
	 * 年龄
	 */
	@Column(name = "AGE", columnDefinition = M.NUM_COLUM)
	private Integer age;
	/**
	 * 证件类型(1、	身份证；2、	军官证；3、	驾驶证；4、	护照)
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;
	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;
	/**
	 * 性别(0.男，1.女)
	 */
	@Column(name = "GENDER", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer gender;
	/**
	 * 出生年月
	 */
	@Column(name = "BIRTHDAY", columnDefinition = M.DATE_COLUM)
	private Date birthday;
	/**
	 * 电话
	 */
	@Column(name = "TELEPHONE", length = 32)
	private String telephone;
	/**
	 * 户籍地址
	 */
	@Column(name = "ADDRESS", length = 128)
	private String address;
	/**
	 * 首次购买时间
	 */
	@Column(name = "FIRST_BUY_DATE", columnDefinition = M.DATE_COLUM)
	private Date firstBuyDate;
	/**
	 * 购买次数
	 */
	@Column(name = "BUY_TIMES", columnDefinition = M.NUM_COLUM)
	private Integer buyTimes;
	/**
	 * 购买总金额
	 */
	@Column(name = "BUY_AMOUNT_TOTAL", columnDefinition = M.DOUBLE_COLUM)
	private Double buyAmountTotal;
	/**
	 * 民族
	 */
	@Column(name = "NATION", length = 64)
	private String nation;
	
	/**
	 * 身份证照片
	 */
	@Column(name = "IMAGE_URL", length = 512)
	private String imageUrl;
	
	/**
	 * @方法功能说明: 创建游客信息 
	 * @param command
	 */
	public void create(PlatformCreateTouristCommand command) {
		setId(command.getTouristId());
		setName(command.getName());
		setAge(command.getAge());
		setIdType(command.getIdType());
		setIdNo(command.getIdNo());
		setGender(command.getGender());
		setBirthday(command.getBirthday());
		setAddress(command.getAddress());
		setNation(command.getNation());
		setFirstBuyDate(new Date());
		setBuyAmountTotal(command.getBuyAmountTotal());
		setBuyTimes(1);
		if (StringUtils.isNotBlank(command.getTelephone()))
			setTelephone(command.getTelephone());
	}
	
	/**
	 * @方法功能说明: 修改游客信息 
	 * @param command
	 */
	public void modify(PlatformModifyTouristCommand command) {
		if (StringUtils.isBlank(this.getName()))
			setName(command.getName());
		
		if (null == this.getGender())
			setGender(command.getGender());
		
		if (null == this.getBirthday())
			setBirthday(command.getBirthday());
		
		if (StringUtils.isBlank(this.getAddress()))
			setAddress(command.getAddress());
		
		if (null == this.getBirthday())
			setNation(command.getNation());
		
		if (StringUtils.isBlank(this.getImageUrl()))
			setImageUrl(command.getImageUrl());
		
		if (StringUtils.isNotBlank(command.getTelephone()))
			setTelephone(command.getTelephone());
		
		setTelephone(command.getTelephone());
		setBuyAmountTotal(getBuyAmountTotal() + command.getBuyAmountTotal());
		setBuyTimes(getBuyTimes() + 1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getFirstBuyDate() {
		return firstBuyDate;
	}
	public void setFirstBuyDate(Date firstBuyDate) {
		this.firstBuyDate = firstBuyDate;
	}
	public Integer getBuyTimes() {
		return buyTimes;
	}
	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}
	public Double getBuyAmountTotal() {
		return buyAmountTotal;
	}
	public void setBuyAmountTotal(Double buyAmountTotal) {
		this.buyAmountTotal = buyAmountTotal;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}