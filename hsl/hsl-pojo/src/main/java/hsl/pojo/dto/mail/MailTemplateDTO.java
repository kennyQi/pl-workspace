package hsl.pojo.dto.mail;

import java.util.Date;
import hsl.pojo.dto.BaseDTO;

/**
 * @类功能说明：Mail邮件模板Dto
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午4:43:12
 */
@SuppressWarnings("serial")
public class MailTemplateDTO extends BaseDTO {
	/**模板名称*/
	private String name;
	/**内容*/
	private String content;
	/**是否默认*/
	private boolean defAble;
	/**创建时间*/
	private Date createDate;
	
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