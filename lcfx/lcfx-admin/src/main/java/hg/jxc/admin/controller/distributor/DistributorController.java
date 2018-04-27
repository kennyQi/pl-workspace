package hg.jxc.admin.controller.distributor;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.distributor.DistributorService;
import jxc.domain.model.distributor.Distributor;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.IpCountUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.distributor.ChangeStatusDistributorCommand;
import hg.pojo.command.distributor.CreateDistributorCommand;
import hg.pojo.command.distributor.ModifyDistributorCommand;
import hg.pojo.command.distributor.RemoveDistributorCommand;
import hg.pojo.exception.LcfxException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.DistributorQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/distributor")
public class DistributorController extends BaseController {
	@Autowired
	DistributorService distributorService;

	final static String navTabRel = "distributor";

	@RequestMapping("/list")
	public String queryDistributorList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, DistributorQo qo) {
		
		
		String ip = IpCountUtil.getRequestIp(request);
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = distributorService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		IpCountUtil.addIpCount(ip);
		if (IpCountUtil.getIpCount(ip) >= 4) {
			return "";
		}
		return "distributor/distributorList.html";
	}

	@RequestMapping("/to_add")
	public String toAddDistributor(Model model, HttpServletRequest request) {
		return "/distributor/distributorAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createDistributor(Model model, HttpServletRequest request, CreateDistributorCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			distributorService.create(command);
		} catch (LcfxException e) {
			e.printStackTrace();

			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editDistributor(Model model, HttpServletRequest request, DistributorQo qo) {
		Distributor distributor = distributorService.queryUnique(qo);
//		JsonResultUtil.AddFormData(model, distributor);
		Tools.modelAddFlag("edit", model);
		
		
		model.addAttribute("d", distributor);
		return "distributor/distributorAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateDistributor(Model model, HttpServletRequest request, ModifyDistributorCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

			try {
				distributorService.modify(command);
			} catch (LcfxException e) {
				e.printStackTrace();
				return JsonResultUtil.dwzExceptionMsg(e);
				
			}

		return JsonResultUtil.dwzSuccessMsg("编辑成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeDistributor(RemoveDistributorCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

			try {
				distributorService.remove(command);
			} catch (LcfxException e) {
				return JsonResultUtil.dwzExceptionMsg(e);
			}
		return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);

	}
	@RequestMapping("/changeStatus")
	@ResponseBody
	public String changeStatus(ChangeStatusDistributorCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		
		try {
			distributorService.changeStatus(command);
		} catch (LcfxException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		String message = "启用成功";
		if (command.getStatus() == Distributor.STATUS_DISABLE) {
			message = "禁用成功";
		}
		return JsonResultUtil.dwzSuccessMsgNoClose(message, navTabRel);
		
	}

}
