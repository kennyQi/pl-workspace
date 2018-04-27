package hg.system.model.mail;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.mail.CreateMailTemplateCommand;
import hg.system.command.mail.ModifyMailTemplateCommand;
import hg.system.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：邮件模板
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月27日 下午4:19:48
 */
@Entity
@Table(name = "SYS_MAIL_TEMPLATE")
public class MailTemplate extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**模板名称*/
	@Column(name="NAME", length = 64)
	private String name;
	/**内容*/
	@Column(name="CONTENT",columnDefinition=M.TEXT_COLUM)
	private String content;
	/**是否默认*/
	@Type(type = "yes_no")
	private boolean defAble;
	/**创建时间*/
	private Date createDate;

	/**
	 * @方法功能说明：创建邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午10:38:26
	 * @修改内容：
	 * @param command
	 * @param environment
	 */
	public void createMailTemplate(CreateMailTemplateCommand command){
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setContent(command.getContent());
		setDefAble(command.isDefAble());
		setCreateDate(new Date());
	}
	
	/**
	 * @方法功能说明：修改邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午10:38:26
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyMailTemplate(ModifyMailTemplateCommand command){
		setName(command.getName());
		setContent(command.getContent());
		setDefAble(command.isDefAble());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isDefAble() {
		return defAble;
	}
	public void setDefAble(boolean defAble) {
		this.defAble = defAble;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}