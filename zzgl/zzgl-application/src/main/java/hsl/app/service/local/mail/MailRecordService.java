package hsl.app.service.local.mail;

import hg.common.page.Pagination;
import hsl.pojo.dto.mail.MailRecordDTO;
import hsl.pojo.qo.mail.MailRecordQo;

/**
 * @类功能说明：邮件记录管理Service接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午3:09:08
 */
public interface MailRecordService {
	/**
	 * @方法功能说明：分页查询
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午5:07:35
	 * @修改内容：
	 * @param pagination
	 * @return
	 */
	public Pagination queryPagination(Pagination pagination);
	
	/**
	 * @方法功能说明：查询
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午5:10:18
	 * @修改内容：
	 * @param cordQo
	 * @return
	 */
	public MailRecordDTO queryUnique(MailRecordQo cordQo);
}