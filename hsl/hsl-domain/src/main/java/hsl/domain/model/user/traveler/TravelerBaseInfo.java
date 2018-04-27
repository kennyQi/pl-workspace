package hsl.domain.model.user.traveler;

import hg.common.util.DateUtil;
import hsl.domain.model.M;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.lang.time.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：游客基本信息
 * @类修改者：
 * @修改日期：2015-9-28下午5:21:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-28下午5:21:10
 */
@Embeddable
@SuppressWarnings("serial")
public class TravelerBaseInfo implements Serializable, HSLConstants.Traveler {

	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", length = 32)
	private String mobile;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;

	/**
	 * 证件类型
	 * 
	 * @see HSLConstants.Traveler
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;

	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 修改日期
	 */
	@Column(name = "MODIFY_DATE", columnDefinition = M.DATE_COLUM)
	private Date modifyDate;

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
			if (ID_TYPE_SFZ.equals(getIdType()))
				return checkTravelerTypeByIdno(idNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TYPE_ADULT;
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

}
