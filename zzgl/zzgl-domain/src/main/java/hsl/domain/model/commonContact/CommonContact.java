package hsl.domain.model.commonContact;
import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.user.User;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @类功能说明：常用联系人
 * @类修改者：
 * @修改日期：2015年9月6日上午9:41:04
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年9月6日上午9:41:04
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_USER+"COMMONCONTACT")
public class CommonContact extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 联系人姓名
	 */
	@Column(name="NAME",length=64)
	private String name;
	/**
	 * 证件类型 1为 身份证 2 为护照
	 */
	@Column(name="CARDTYPE",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer cardType;
	/**
	 * 证件号码
	 */
	@Column(name="CARDNO",length=64)
	private String cardNo;
	/**
	 * 手机号
	 */
	@Column(name="MOBILE",length=64)
	private String mobile;
	/**
	 * 乘客类型 1 为成人 2为儿童
	 */
	@Column(name="TYPE",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer type;
	/**
	 * 创建日期
	 */
	@Column(name="CREATE_DATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;
	/**
	 * 修改日期
	 */
	@Column(name="MODIFY_DATE",columnDefinition=M.DATE_COLUM)
	private Date modifyDate;
	/**
	 * 所属用户
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	/**
	 * 游客类型：成人
	 */
	@Transient
	private Integer TYPE_ADULT = 1;
	/**
	 * 游客类型：儿童
	 */
	@Transient
	private Integer TYPE_CHILD = 2;
	
	/**
	 * 证件类型：身份证
	 */
	@Transient
	private Integer ID_TYPE_SFZ = 1;
	
	public void createCommonContact(CreateCommonContactCommand command,User n_user){
		this.setId(UUIDGenerator.getUUID());
		this.setName(command.getName());
		this.setMobile(command.getMobile());
		this.setCardNo(command.getCardNo());
		this.setCardType(command.getCardType());
		this.setType(this.getType());
		this.setCreateDate(new Date());
		this.setModifyDate(new Date());
		this.setUser(n_user);
	}
	public void updateCommonContact(UpdateCommonContactCommand command){
		if(StringUtils.isNotBlank(command.getName())){
			this.setName(command.getName());
		}
		if(StringUtils.isNotBlank(command.getMobile())){
			this.setMobile(command.getMobile());
		}
		if(StringUtils.isNotBlank(command.getCardNo())){
			this.setCardNo(command.getCardNo());
		}
		if(command.getCardType()!=null){
			this.setCardType(command.getCardType());
		}
		if(command.getType()!=null){
			this.setType(this.getType());
		}
		this.setModifyDate(new Date());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	/**
	 * @方法功能说明：判断游客身份证号返回游客类型
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-10下午4:40:08
	 * @修改内容：
	 * @参数：@param idno
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	private Integer checkTravelerTypeByIdno(String idno) {
		Date birthday;
		if (idno.length() == 18)
			birthday = DateUtil.parseDateTime(idno.substring(6, 14), "yyyyMMdd");
		else
			birthday = DateUtil.parseDateTime("19" + idno.substring(6, 12), "yyyyMMdd");
		if (birthday == null)
			throw new ShowMessageException("身份证号码不合法");
		// 判断是否>=12岁
		if (DateUtils.truncatedCompareTo(DateUtils.addYears(new Date(), -12), birthday, Calendar.DATE) >= 0)
			return TYPE_ADULT;
		return TYPE_CHILD;
	}

	/**
	 * 得到游玩人类型
	 * @see HSLConstants.Traveler
	 * @return
	 */
	public Integer getType() {
		try {
			if (ID_TYPE_SFZ.equals(getCardType()))
				return checkTravelerTypeByIdno(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TYPE_ADULT;
	}
}
