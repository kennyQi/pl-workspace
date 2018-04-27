package hg.jxc.admin.controller.distributor;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.app.service.distributor.DistributorService;
import jxc.app.service.distributor.MileOrderService;
import jxc.domain.model.distributor.Distributor;
import jxc.domain.model.distributor.MileOrder;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.UUIDGenerator;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.mileOrder.CheckMileOrderCommand;
import hg.pojo.command.mileOrder.CreateMileOrderCommand;
import hg.pojo.command.mileOrder.ImportMileOrderCommand;
import hg.pojo.command.mileOrder.ModifyMileOrderCommand;
import hg.pojo.command.mileOrder.RemoveMileOrderCommand;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.MileOrderQo;
import hg.system.model.auth.AuthUser;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mileOrder")
public class MileOrderController extends BaseController {
	@Autowired
	MileOrderService mileOrderService;
	@Autowired
	DistributorService distributorService;

	final static String navTabRel = "mileOrder";
	@ResponseBody
	@RequestMapping("/noticeSend")
	public String noticeMileOrder(Model model, HttpServletRequest request,@RequestParam String id) {
		 mileOrderService.sendOrderByOrderCode(id);
		 return "1";
	}
	@RequestMapping("/list")
	public String queryMileOrderList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, MileOrderQo qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = mileOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		DistributorQo distributorQo = new DistributorQo();
		List<Distributor> distributorList = distributorService.queryList(distributorQo);
		model.addAttribute("distributorList", distributorList);

		model.addAttribute("qo", qo);

		return "mileOrder/mileOrderList.html";
	}

	@RequestMapping("/to_add")
	public String toAddMileOrder(Model model, HttpServletRequest request) {
		return "/mileOrder/mileOrderAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createMileOrder(Model model, HttpServletRequest request, CreateMileOrderCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		// mileOrderService.create(command);

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editMileOrder(Model model, HttpServletRequest request, MileOrderQo qo) {
		MileOrder mileOrder = mileOrderService.queryUnique(qo);
		JsonResultUtil.AddFormData(model, mileOrder);
		Tools.modelAddFlag("edit", model);
		return "mileOrder/mileOrderAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateMileOrder(Model model, HttpServletRequest request, ModifyMileOrderCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		mileOrderService.modify(command);

		return JsonResultUtil.dwzSuccessMsg("编辑成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeMileOrder(RemoveMileOrderCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		boolean allDelete = mileOrderService.remove(command);
		if (allDelete) {
			return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);
		}else {
			return JsonResultUtil.dwzSuccessMsgNoClose("操作完成，部分里程订单已提交南航或处理完成，无法删除", navTabRel);
		}

	}

	@RequestMapping("/check")
	@ResponseBody
	public String checkMileOrder(CheckMileOrderCommand command) {

		AuthUser checkPerson = getAuthUser();
		command.setCheckPersonId(checkPerson.getId());
		CommandUtil.SetOperator(getAuthUser(), command);

		mileOrderService.check(command);
		return JsonResultUtil.dwzSuccessMsgNoClose("审核成功", navTabRel);

	}

	@RequestMapping("/view")
	public String view(Model model, HttpServletRequest request, String id) {
		MileOrder mileOrder = mileOrderService.get(id);
		model.addAttribute("mileOrder", mileOrder);

		return "/mileOrder/mileOrderView.html";
	}

	@RequestMapping("/to_import")
	public String toImport(Model model, HttpServletRequest request) {
		DistributorQo qo = new DistributorQo();
		qo.setStatus(Distributor.STATUS_ENABLE);
		List<Distributor> distributorList = distributorService.queryList(qo);
		model.addAttribute("distributorList", distributorList);
		return "/mileOrder/mileOrderImport.html";
	}

	@RequestMapping("/import")
	@ResponseBody
	public String importExcel(@RequestParam MultipartFile importFile, HttpServletRequest request, @RequestParam(required = false) String distributorId) {
		if (StringUtils.isBlank(distributorId)) {
			return JsonResultUtil.dwzErrorMsg("未选择分销商");
		}

		if (importFile == null) {
			return JsonResultUtil.dwzErrorMsg("请上传excel文件");
		}

		ClassPathTool.getInstance();
		String tempFilePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "upload" + UUIDGenerator.getUUID()
				+ importFile.getOriginalFilename();
		tempFilePath = tempFilePath.replace("file:", "");
		File tempFile = new File(tempFilePath);
		List<List<String>> dataListList = null;
		try {
			importFile.transferTo(tempFile);
			dataListList = ExcelUtils.getExecelStringValues(tempFile);
			tempFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtil.dwzErrorMsg("文件解析失败，请上传正确的excel文件");
		}
		if (dataListList == null || dataListList.size() <= 1 || dataListList.get(0).size() != 5) {
			return JsonResultUtil.dwzErrorMsg("文件内容不正确");
		}
		// 解析文件-----------------------------------
		ImportMileOrderCommand importMileOrderCommand = new ImportMileOrderCommand();
		importMileOrderCommand.setDistributorId(distributorId);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < dataListList.size(); i++) {
			CreateMileOrderCommand createCommand = new CreateMileOrderCommand();

			List<String> t = dataListList.get(i);
			String orderCode = t.get(0);
			if (StringUtils.isBlank(orderCode)) {
				sb.append("第" + (i + 1) + "行，订单号必填&ltbr/&gt");
				
			}
			createCommand.setOrderCode(orderCode);
			
			String csairCard = t.get(1);
			if (StringUtils.isBlank(csairCard)) {
				sb.append("第" + (i + 1) + "行，南航卡号必填&ltbr/&gt");
				
			}
			createCommand.setCsairCard(csairCard);
			
			String csairName = t.get(2);
			if (StringUtils.isBlank(csairName)) {
				sb.append("第" + (i + 1) + "行，南航姓名必填&ltbr/&gt");
				
			}
			createCommand.setCsairName(csairName);
			//添加限制
			try {
				String strNum = t.get(3);
				int num = Integer.valueOf(strNum);
				createCommand.setNum(num);
			} catch (Exception e) {
				sb.append("第" + (i + 1) + "行，数量不正确&ltbr/&gt");
			}

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String strDate = t.get(4);
				Date date = sdf.parse(strDate);
				createCommand.setPayDate(date);
			} catch (Exception e) {
				sb.append("第" + (i + 1) + "行，日期格式不正确&ltbr/&gt");
			}

			System.out.println("第" + (i + 1) + "行，南航姓名csairName：" + t.get(2));
			importMileOrderCommand.getList().add(createCommand);
		}
		//订单编号排重
		for (int i = 1; i < dataListList.size(); i++) {
			for (int j = 1; j < dataListList.size(); j++) {
				String str1 = dataListList.get(i).get(0);
				String str2 = dataListList.get(j).get(0);
				if (i!=j&&StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2) && str1.equals(str2)) {
					sb.append("第").append(i + 1).append("行：订单编号重复&ltbr/&gt");
				}
			}

		}


		if (sb.length() > 0) {
			return JsonResultUtil.dwzErrorMsg("导入失败&ltbr/&gt" + sb.toString());
		}
		for (int i = 1; i < dataListList.size(); i++) {
			List<String> t = dataListList.get(i);
			MileOrderQo qo = new MileOrderQo();
			qo.setDistributorId(distributorId);
			qo.setOrderCode(t.get(0));
			qo.setQueryOrderCodeLike(false);
			if (mileOrderService.queryCount(qo) > 0) {
				sb.append("第" + (i + 1) + "行，订单号在该分销商下已存在&ltbr/&gt");
			}
		}
		if (sb.length() > 0) {
			return JsonResultUtil.dwzErrorMsg("导入失败&ltbr/&gt" + sb.toString());
		}

		mileOrderService.importExcel(importMileOrderCommand);

		//
		// List<String> title = dataListList.get(0);
		// if (!(title.get(0).equals("员工姓名") && title.get(1).equals("身份证号") &&
		// title.get(2).equals("手机号码"))) {
		// return JsonResultUtil.dwzErrorMsg("文件内容不正确");
		// }
		//
		// // 循环必须分开！！！！！！！！！！
		// for (int i = 1; i < dataListList.size(); i++) {
		// List<String> t = dataListList.get(i);
		// if (StringUtils.isBlank(t.get(0)) || StringUtils.isBlank(t.get(1)) ||
		// StringUtils.isBlank(t.get(2))) {
		// return JsonResultUtil.dwzErrorMsg("导入失败，第" + (i + 1) + "行有信息未填写");
		// }
		// }
		//
		// for (int i = 1; i < dataListList.size(); i++) {
		// if ((i + 1) < dataListList.size()) {
		// for (int j = i + 1; j < dataListList.size(); j++) {
		// if (dataListList.get(i).get(1).equals(dataListList.get(j).get(1)) ||
		// dataListList.get(i).get(2).equals(dataListList.get(j).get(2))) {
		// return JsonResultUtil.dwzErrorMsg("导入失败，第" + (i + 1) + "行和第" + (j +
		// 1) + "行有信息相同");
		// }
		// }
		// }
		// }
		//
		// StringBuffer sb = new StringBuffer();
		// for (int i = 1; i < dataListList.size(); i++) {
		// // 根据公司id和电话查询
		// EmployeeQO qo = new EmployeeQO();
		// qo.setCompanyId(companyId);
		// qo.setPhone(dataListList.get(i).get(2));
		// Employee employee = employeeService.queryUnique(qo);
		//
		// if (employee != null) {
		// sb.append("&ltbr/&gt第").append(i +
		// 1).append("条数据:").append(dataListList.get(i).get(0)).append(" 已存在，未添加");
		// dataListList.set(i, null);
		// }
		// }
		//
		// for (int i = 1; i < dataListList.size(); i++) {
		// if (dataListList.get(i) == null) {
		// continue;
		// }
		// CreateEmployeeCommand command = new CreateEmployeeCommand();
		// command.setCompanyId(companyId);
		// command.setName(dataListList.get(i).get(0));
		// command.setIdCard(dataListList.get(i).get(1));
		// command.setPhone(dataListList.get(i).get(2));
		// if (!employeeService.checkFormat(command)) {
		// return JsonResultUtil.dwzErrorMsg("导入失败，第" + (i + 1) + "行，" +
		// "员工手机号或身份证号格式错误");
		// }
		// }
		//
		// for (int i = 1; i < dataListList.size(); i++) {
		// if (dataListList.get(i) == null) {
		// continue;
		// }
		//
		// CreateEmployeeCommand command = new CreateEmployeeCommand();
		// command.setCompanyId(companyId);
		// command.setName(dataListList.get(i).get(0));
		// command.setIdCard(dataListList.get(i).get(1));
		// command.setPhone(dataListList.get(i).get(2));
		// try {
		// employeeService.CreateEmployee(command);
		// } catch (EmployeeException e) {
		// // 为了一次完全判断在上面的循环判重，这里只是抛异常
		// return JsonResultUtil.dwzErrorMsg("导入失败，第" + (i + 1) + "行，" +
		// e.getMessage());
		// }
		// }
		// return JsonResultUtil.dwzSuccessMsg("导入完成" + sb.toString(),
		// navTabName);
		return JsonResultUtil.dwzSuccessMsg("导入完成", navTabRel);
	}

	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request, Model model, @ModelAttribute MileOrderQo qo, HttpServletResponse response) {
		HSSFWorkbook excel = mileOrderService.export(qo);
		try {
			outputExcel(excel, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/test1")
	@ResponseBody
	public String test1(String ids) {
		mileOrderService.testStatus(ids,MileOrder.STATUS_TO_CSAIR);
		return JsonResultUtil.dwzSuccessMsgNoClose("ok", navTabRel);
	}
	@RequestMapping(value = "/test2")
	@ResponseBody
	public String test2(String ids) {
		mileOrderService.testStatus(ids,MileOrder.STATUS_CSAIR_SUCCEED);
		return JsonResultUtil.dwzSuccessMsgNoClose("ok", navTabRel);
	}
	@RequestMapping(value = "/test3")
	@ResponseBody
	public String test3(String ids) {
		mileOrderService.testStatus(ids,MileOrder.STATUS_CSAIR_ERROR);
		return JsonResultUtil.dwzSuccessMsgNoClose("ok", navTabRel);
	}

}
