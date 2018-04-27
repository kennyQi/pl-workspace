package hsl.admin.controller.mail;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.mail.MailRecordService;
import hsl.pojo.dto.mail.MailRecordDTO;
import hsl.pojo.qo.mail.MailRecordQo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @类功能说明：邮件记录管理Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午3:55:18
 */
@Controller
@RequestMapping(value = "/mail-record")
public class MailRecordController extends BaseController {
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	@Autowired
	private MailRecordService cordSer;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(@ModelAttribute("cordQo") MailRecordQo cordQo,@ModelAttribute DwzPaginQo dwzQo,Model model) {
		try {
			cordQo.setCreateDateDesc(true);//倒序
			//添加时间查询条件
			if(!StringUtils.isBlank(cordQo.getTimeBefore()))
				cordQo.setCreateDateBegin(DateUtil.parseDateTime(cordQo.getTimeBefore()));
			if(!StringUtils.isBlank(cordQo.getTimeAfter()))
				cordQo.setCreateDateEnd(DateUtil.parseDateTime(cordQo.getTimeAfter()));
			
			//创建分页查询对象
			Pagination pagination = super.createPagination(dwzQo, cordQo);
			//执行查询
			pagination = cordSer.queryPagination(pagination);
			//数据对象返回
			model.addAttribute("pagination", pagination);
		}catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailRecordController.class,"hsl-admin","[list] 列表："+e.getMessage(),e);
		}
		return "/mail-record/list.html";
	}
	
	/**
	 * 查看记录
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String show(@ModelAttribute("cordQo") MailRecordQo cordQo,Model model) {
		try {
			if (StringUtils.isBlank(cordQo.getId()))
				throw new RuntimeException("ID不能为空");
			MailRecordDTO cordDto = cordSer.queryUnique(cordQo);
			if (null == cordDto)
				throw new RuntimeException("记录不存在或已被删除");
			model.addAttribute("mail", cordDto);
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailRecordController.class,"hsl-admin","[show] 查看记录："+e.getMessage(),e);
		}
		return "/mail-record/show.html";
	}
}