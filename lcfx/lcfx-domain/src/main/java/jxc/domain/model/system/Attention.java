package jxc.domain.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateAttentionCommand;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyAttentionCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemoveAttentionCommand;
import hg.pojo.command.RemoveUnitCommand;

/**
 * 结算方式
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "ATTENTION")
public class Attention extends JxcBaseModel {
	
	/**
	*
	*/
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	@Column(name = "ATTENTION_NAME", length = 50)
	private String attentionName;

	@Column(name = "ATTENTION_DETAIL", length = 255)
	private String attentionDetail;
	
	public String getAttentionName() {
		return attentionName;
	}

	public void setAttentionName(String attentionName) {
		this.attentionName = attentionName;
	}

	public String getAttentionDetail() {
		return attentionDetail;
	}

	public void setAttentionDetail(String attentionDetail) {
		this.attentionDetail = attentionDetail;
	}

	public void createAttention(CreateAttentionCommand command) {
		createDate = new Date();
		
		setId(UUIDGenerator.getUUID());
		setAttentionName(command.getAttentionName());
		setAttentionDetail(command.getAttentionDetail());
		setStatusRemoved(false);
	}

	public void modifyAttention(ModifyAttentionCommand command) {
		setAttentionName(command.getAttentionName());
		setAttentionDetail(command.getAttentionDetail());
	}

	public void removeAttention(RemoveAttentionCommand command) {
		setId(command.getId());
		setStatusRemoved(true);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
