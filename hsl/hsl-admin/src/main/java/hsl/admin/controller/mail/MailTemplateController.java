package hsl.admin.controller.mail;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.command.mail.CreateMailTemplateCommand;
import hg.system.command.mail.DeleteMailTemplateCommand;
import hg.system.command.mail.ModifyMailTemplateCommand;
import hg.system.model.mail.MailTemplate;
import hg.system.qo.MailTemplateQo;
import hg.system.service.MailTemplateService;
import hsl.admin.controller.BaseController;
import hsl.app.common.util.EntityConvertUtils;
import hsl.pojo.dto.mail.MailTemplateDTO;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @类功能说明：邮件模板管理Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月28日上下午9:55:18
 */
@Controller
@RequestMapping(value = "/mail-template")
public class MailTemplateController extends BaseController {
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	@Autowired
	private MailTemplateService temSer;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(@ModelAttribute("temQo") MailTemplateQo temQo,@ModelAttribute DwzPaginQo dwzQo,Model model) {
		try {
			//创建分页查询对象
			Pagination pag = createPagination(dwzQo, temQo);
			//执行查询
			pag = temSer.queryPagination(pag);
			
			//Model至DTO转换
			List<?> list = pag.getList();
			if(null == list || list.size() < 1)
				list = null;
			else
				list = EntityConvertUtils.convertEntityToDtoList(list,MailTemplateDTO.class);
			pag.setList(list);
			
			//数据对象返回
			model.addAttribute("pagination", pag);
		}catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailTemplateController.class,"hsl-admin","[list] 列表："+e.getMessage(),e);
		}
		return "/mail-template/list.html";
	}
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(){
		return "/mail-template/add.html";
	}
	
	/**
	 * 新增提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSubmit", method = { RequestMethod.POST })
	public String addSubmit(
			@ModelAttribute CreateMailTemplateCommand command,
			@RequestParam(value="navTabid",required=false) String navTabid,
			Model model
		) {
		try {
			//添加数据
			temSer.createMailTemplate(command);
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailTemplateController.class,"hsl-admin","[addSubmit] 新增提交："+e.getMessage(),e);
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "新增失败:"+e.getMessage());
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "新增成功",	DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,navTabid);
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(@ModelAttribute MailTemplateQo temQo,Model model) {
		try {
			if (StringUtils.isBlank(temQo.getId()))
				throw new RuntimeException("邮件模板ID不能为空");
			MailTemplate tem = temSer.queryUnique(temQo);
			if(null == tem)
				throw new RuntimeException("邮件模板不存在或已被删除");
			else
				model.addAttribute("tem",EntityConvertUtils.convertEntityToDto(tem,MailTemplateDTO.class));//Model至DTO转换
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailTemplateController.class,"hsl-admin","[edit] 编辑："+e.getMessage(),e);
		}
		return "/mail-template/edit.html";
	}

	/**
	 * 编辑提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editSubmit", method = { RequestMethod.POST })
	public String editSubmit(
			@ModelAttribute ModifyMailTemplateCommand command,
			@RequestParam(value="navTabid",required=false) String navTabid,
			Model model
		) {
		try {
			if(StringUtils.isBlank(command.getItemId()))
				throw new RuntimeException("邮件模板ID不能为空");
			//修改数据
			temSer.modifyMailTemplate(command);
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailTemplateController.class,"hsl-admin","[editSubmit] 修改提交："+e.getMessage(),e);
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败:"+e.getMessage());
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",	DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,navTabid);
	}

	/**
	 * 删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(
			@ModelAttribute DeleteMailTemplateCommand command,
			@RequestParam(value="navTabid",required=false) String navTabid,
			Model model
		) {
		try {
			if(StringUtils.isBlank(command.getItemId()))
				throw new RuntimeException("邮件模板ID不能为空");
			//删除数据
			temSer.deleteMailTemplate(command);
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(MailTemplateController.class,"hsl-admin","[delete] 表达式删除："+e.getMessage(),e);
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败:"+e.getMessage());
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,navTabid);
	}
}