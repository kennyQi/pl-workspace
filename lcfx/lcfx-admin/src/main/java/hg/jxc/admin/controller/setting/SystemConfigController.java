package hg.jxc.admin.controller.setting;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.system.PaymentMethodService;
import jxc.app.service.system.SystemConfigService;
import jxc.domain.model.system.PaymentMethod;
import jxc.domain.model.system.SystemConfigSet;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.ModifySystemConfigCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.PaymentMethodQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system_config")
public class SystemConfigController extends BaseController {
	@Autowired
	SystemConfigService systemConfigService;

	final static String navTabRel = "system_config_edit";

	@RequestMapping("/edit")
	public String editPaymentMethod(Model model, HttpServletRequest request, PaymentMethodQo qo) {
		SystemConfigSet systemConfig = systemConfigService.querySystemConfig();
		model.addAttribute("systemConfig", systemConfig);
		return "setting/systemConfig/systemConfigEdit.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updatePaymentMethod(ModifySystemConfigCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		systemConfigService.modifySystemConfig(command);
		return JsonResultUtil.dwzSuccessMsgNoClose("编辑成功", navTabRel);
	}

}
