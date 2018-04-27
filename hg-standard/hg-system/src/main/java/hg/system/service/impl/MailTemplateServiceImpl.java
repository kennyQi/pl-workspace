package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.mail.CreateMailTemplateCommand;
import hg.system.command.mail.DeleteMailTemplateCommand;
import hg.system.command.mail.ModifyMailTemplateCommand;
import hg.system.dao.MailTemplateDao;
import hg.system.model.mail.MailTemplate;
import hg.system.qo.MailTemplateQo;
import hg.system.service.MailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @类功能说明：邮件模板Service实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月28日 下午3:10:20
 */
@Service
public class MailTemplateServiceImpl extends BaseServiceImpl<MailTemplate,MailTemplateQo,MailTemplateDao>
	implements MailTemplateService {
	@Autowired
	private MailTemplateDao dao;
	
	@Override
	protected MailTemplateDao getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：创建邮件模板 
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:26:55
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	@Override
	public void createMailTemplate(CreateMailTemplateCommand command)
			throws Exception {
		MailTemplate template = new MailTemplate();
		template.createMailTemplate(command);
		
		//在保存邮件模板之前，如果是默认模板，则先更新数据库为非默认
		if(template.isDefAble()){
			String hql = "update MailTemplate set defAble = 'N'";
			dao.executeHql(hql);
		}
		//保存邮件模板
		dao.save(template);
	}

	/**
	 * @方法功能说明：删除邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:27:20
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	@Override
	public void deleteMailTemplate(DeleteMailTemplateCommand command)
			throws Exception {
		dao.deleteById(command.getItemId());
	}

	/**
	 * @方法功能说明：删除批量邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:25:20
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	@Override
	public void deleteMailTemplateList(DeleteMailTemplateCommand command)
			throws Exception {
		dao.deleteByIds(command.getItemIds());
	}
	
	/**
	 * @方法功能说明：修改邮件模板
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午9:27:32
	 * @修改内容：
	 * @param command
	 * @throws Exception
	 */
	@Override
	public void modifyMailTemplate(ModifyMailTemplateCommand command)
			throws Exception {
		MailTemplate template = dao.get(command.getItemId());
		if(null == template)
			throw new RuntimeException("邮件模板不存在或已被删除");
		template.modifyMailTemplate(command);
		//在更新邮件模板之前，如果是默认模板，则先更新数据库为非默认
		if(template.isDefAble()){
			String hql = "update MailTemplate set defAble = 'N' where id != ?";
			dao.executeHql(hql,template.getId());
		}
		//更新邮件模板
		dao.update(template);
	}
}