package hg.jxc.admin.controller.setting;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.system.AttentionService;
import jxc.domain.model.system.Attention;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.Tools;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateAttentionCommand;
import hg.pojo.command.ModifyAttentionCommand;
import hg.pojo.command.RemoveAttentionCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.AttentionQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/attention")
public class AttentionController extends BaseController {
	@Autowired
	AttentionService attentionService;

	final static String navTabRel = "attention_list";

	@RequestMapping("/list")
	public String queryAttentionList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, AttentionQo qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = attentionService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return "setting/attention/attentionList.html";
	}

	@RequestMapping("/to_add")
	public String toAddAttention(Model model, HttpServletRequest request) {
		return "setting/attention/attentionAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createAttention(Model model, HttpServletRequest request, CreateAttentionCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			attentionService.createAttention(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editAttention(Model model, HttpServletRequest request, AttentionQo qo) {
		Attention attention = attentionService.queryUnique(qo);
		JsonResultUtil.AddFormData(model, attention);
		Tools.modelAddFlag("edit", model);
		return "setting/attention/attentionAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateAttention(Model model, HttpServletRequest request, ModifyAttentionCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			attentionService.modifyAttention(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("编辑成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeAttention(RemoveAttentionCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			attentionService.removeAttention(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);

	}

}
