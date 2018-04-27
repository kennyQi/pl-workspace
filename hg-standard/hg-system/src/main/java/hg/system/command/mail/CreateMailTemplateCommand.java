package hg.system.command.mail;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建邮件模板指令
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月28日 上午9:31:38
 */
public class CreateMailTemplateCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**模板名称*/
	private String name;
	/**内容*/
	private String content;
	/**是否默认*/
	private boolean defAble = true;
	
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
}