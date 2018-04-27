package hsl.app.service.local.mail.impl;

import hg.common.page.Pagination;
import hg.log.po.normal.HgMail;
import hsl.app.common.util.EntityConvertUtils;
import hsl.app.dao.MailRecordDao;
import hsl.app.service.local.mail.MailRecordService;
import hsl.pojo.dto.mail.MailRecordDTO;
import hsl.pojo.qo.mail.MailRecordQo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：邮件记录Service实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午3:10:20
 */
@Service
public class MailRecordServiceImpl implements MailRecordService {
	
	@Autowired
	private MailRecordDao dao;
	
	/**
	 * @方法功能说明：分页查询
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午5:07:35
	 * @修改内容：
	 * @param pagination
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public Pagination queryPagination(Pagination pagination) {
		Pagination pag = dao.queryPagination(pagination);
		
		//Model至DTO转换
		List<?> list = pag.getList();
		if(null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,MailRecordDTO.class);
		pag.setList(list);
		
		return pag;
	}

	/**
	 * @方法功能说明：查询
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午5:10:18
	 * @修改内容：
	 * @param cordQo
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public MailRecordDTO queryUnique(MailRecordQo cordQo) {
		HgMail mail = dao.queryUnique(cordQo);
		if(null == mail)
			return null;
		else
			return EntityConvertUtils.convertEntityToDto(mail,MailRecordDTO.class);
	}
}