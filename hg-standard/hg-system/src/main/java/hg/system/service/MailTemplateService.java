package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.mail.CreateMailTemplateCommand;
import hg.system.command.mail.DeleteMailTemplateCommand;
import hg.system.command.mail.ModifyMailTemplateCommand;
import hg.system.model.mail.MailTemplate;
import hg.system.qo.MailTemplateQo;

/**
 * @类功能说明：邮件模板管理Service接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午3:09:08
 */
public interface MailTemplateService extends BaseService<MailTemplate,MailTemplateQo>{
	/**
	 * @方法功能说明：创建邮件模板 
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:24:55
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	void createMailTemplate(CreateMailTemplateCommand command) throws Exception;
	
	/**
	 * @方法功能说明：删除邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:25:20
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	void deleteMailTemplate(DeleteMailTemplateCommand command) throws Exception;
	
	/**
	 * @方法功能说明：删除批量邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:25:20
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	void deleteMailTemplateList(DeleteMailTemplateCommand command) throws Exception;
	
	/**
	 * @方法功能说明：修改邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:25:32
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	void modifyMailTemplate(ModifyMailTemplateCommand command) throws Exception;
}