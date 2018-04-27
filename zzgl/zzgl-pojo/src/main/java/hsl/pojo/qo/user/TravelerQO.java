package hsl.pojo.qo.user;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import hsl.pojo.util.HSLConstants;

import java.util.Date;

/**
 * @类功能说明：游客查询对象
 * @类修改者：
 * @修改日期：2015-9-28下午5:45:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-28下午5:45:56
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "travelerDao")
public class TravelerQO extends BaseQo {

	/**
	 * 来自的用户
	 */
	@QOAttr(name = "fromUser.id")
	private String fromUserId;
	
	/**
	 * 姓名
	 */
	@QOAttr(name = "baseInfo.name", ifTrueUseLike = "nameLike")
	private String name;
	private Boolean nameLike;

	/**
	 * 手机号
	 */
	@QOAttr(name = "baseInfo.mobile", ifTrueUseLike = "mobileLike")
	private String mobile;
	private Boolean mobileLike;

	/**
	 * 游客类别
	 * 
	 * @see HSLConstants.Traveler
	 */
	@QOAttr(name = "baseInfo.type")
	private Integer type;

	/**
	 * 证件号
	 */
	@QOAttr(name = "baseInfo.idNo", ifTrueUseLike = "idNoLike")
	private String idNo;
	private Boolean idNoLike;

	/**
	 * 证件类型
	 * 
	 * @see HSLConstants.Traveler
	 */
	@QOAttr(name = "baseInfo.idType")
	private Integer idType;

	/**
	 * 创建日期
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.GE)
	private Date createDateBegin;
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.LE)
	private Date createDateEnd;
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.ORDER)
	private Integer createDateOrder = 0;

	/**
	 * 修改日期
	 */
	@QOAttr(name = "baseInfo.modifyDate", type = QOAttrType.GE)
	private Date modifyDateBegin;
	@QOAttr(name = "baseInfo.modifyDate", type = QOAttrType.LE)
	private Date modifyDateEnd;
	@QOAttr(name = "baseInfo.modifyDate", type = QOAttrType.ORDER)
	private Integer modifyDateOrder = 0;
	
	

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getMobileLike() {
		return mobileLike;
	}

	public void setMobileLike(Boolean mobileLike) {
		this.mobileLike = mobileLike;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Boolean getIdNoLike() {
		return idNoLike;
	}

	public void setIdNoLike(Boolean idNoLike) {
		this.idNoLike = idNoLike;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

}
