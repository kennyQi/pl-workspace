package pl.cms.domain.entity.apply;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import pl.cms.domain.entity.M;
import pl.cms.pojo.command.apply.CreateMemberApplyCommand;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

/**
 * 
 * @类功能说明：入会申请
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月18日下午5:43:02
 * 
 */
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "MEMBER_APPLY")
@SuppressWarnings("serial")
public class MemberApply extends BaseModel {

	@Column(name = "SCENIC_NAME", length = 128)
	private String scenicName;

	@Column(name = "MOBILE", length = 32)
	private String mobile;

	@Column(name = "LINK_MAN", length = 64)
	private String linkMan;

	@Column(name = "REMARK", length = 512)
	private String remark;

	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public void create(CreateMemberApplyCommand command) {
		setId(UUIDGenerator.getUUID());
		setLinkMan(command.getLinkMan());
		setMobile(command.getMobile());
		setRemark(command.getRemark());
		setScenicName(command.getScenicName());
		setCreateDate(new Date());
		
	}
	
	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
