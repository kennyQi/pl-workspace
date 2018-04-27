package hg.jxc.admin.controller.supplier;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.UUIDGenerator;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.PermUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateSupplierCommand;
import hg.pojo.command.DeleteSupplierCommand;
import hg.pojo.command.DeleteSupplierLinkManCommand;
import hg.pojo.command.ImportBatchSupplierCommand;
import hg.pojo.command.ModifySupplierCommand;
import hg.pojo.command.ModifySupplierLinkManCommand;
import hg.pojo.dto.supplier.SupplierDTO;
import hg.pojo.dto.supplier.SupplierLinkManDTO;
import hg.pojo.exception.SupplierException;
import hg.pojo.qo.LinkManQO;
import hg.pojo.qo.SupplierAptitudeImageQO;
import hg.pojo.qo.SupplierQO;
import hg.system.service.SecurityService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.app.service.image.SupplierAptitudeImageService;
import jxc.app.service.supplier.SupplierLinkManService;
import jxc.app.service.supplier.SupplierPriorityService;
import jxc.app.service.supplier.SupplierService;
import jxc.domain.model.image.SupplierAptitudeImage;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierLinkMan;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseController {
	@Autowired
	SupplierService supplierService;

	@Autowired
	SupplierLinkManService linkManService;

	@Autowired
	SupplierAptitudeImageService aptitudeImageService;

	@Autowired
	SupplierPriorityService supplierPriorityService;

	@Autowired
	SecurityService securityService;

	/**
	 * 分页查询供应商列表
	 * 
	 * @param model
	 * @param dwzPaginQo
	 * @param qo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String querySupplierList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo, @ModelAttribute SupplierQO qo) {

		qo.setSupplierNameLike(true);
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = supplierService.queryPagination(pagination);

		model.addAttribute("pagination", pagination);

		List<Supplier> suppliers = (List<Supplier>) pagination.getList();
		for (Supplier supplier : suppliers) {
			LinkManQO LinkManQO = new LinkManQO();
			LinkManQO.setSupplierId(supplier.getId());
			SupplierLinkMan linkMan = linkManService.queryUnique(LinkManQO);
			supplier.setLinkManList(new ArrayList<SupplierLinkMan>());
			if (linkMan != null) {
				supplier.getLinkManList().add(linkMan);
			}
		}
		PermUtil.addPermAttr4List(securityService, model, getAuthUser());

		return "/supplier/supplier_list.html";
	}

	/**
	 * 跳转供应商新增页面
	 * 
	 * @return
	 */
	@RequestMapping("to_add")
	public String toAddSupplier() {
		return "/supplier/supplier_add.html";
	}

	/**
	 * 创建供应商基本信息
	 * 
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createSupplier(CreateSupplierCommand command, HttpServletRequest request) {

		CommandUtil.SetOperator(getAuthUser(), command);
		JsonResultUtil resultUtil = new JsonResultUtil();

		try {

			String supplierId = supplierService.createSupplier(command);
			resultUtil.setResultSuccess();
			resultUtil.addAttr("supplierId", supplierId);

		} catch (SupplierException e) {

			resultUtil.setResultError();
			resultUtil.addAttr("message", e.getMessage());
		}

		return resultUtil.outputJsonString();
	}

	/**
	 * 删除供应商
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteSupplier(DeleteSupplierCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			supplierService.deleteSupplier(command);
			return DwzJsonResultUtil.createJsonString("200", "供应商删除成功", DwzJsonResultUtil.FLUSH_FORWARD, "supplier_list");

		} catch (SupplierException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

	}

	/**
	 * 跳转编辑供应商页面
	 * 
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping("/edit")
	public String editSupplier(Model model, SupplierQO qo) {
		Supplier supplier = supplierService.queryUnique(qo);
		LinkManQO linkManQO = new LinkManQO();
		linkManQO.setSupplierId(supplier.getId());
		List<SupplierLinkMan> linkmanList = linkManService.queryList(linkManQO);

		model.addAttribute("supplier", supplier);
		model.addAttribute("linkmanList", linkmanList);
		SupplierAptitudeImageQO imageQo = new SupplierAptitudeImageQO();
		imageQo.setSupplierId(supplier.getId());
		List<SupplierAptitudeImage> imageList = aptitudeImageService.queryList(imageQo);
		for (SupplierAptitudeImage image : imageList) {
			model.addAttribute("imgId" + image.getImageType(), image.getId());
			model.addAttribute("imgSrc" + image.getImageType(), image.getUrl());
		}

		return "/supplier/supplier_add.html";
	}

	/**
	 * 更新供应商基本信息
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateSupplier(ModifySupplierCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);
		JsonResultUtil resultUtil = new JsonResultUtil();

		try {

			supplierService.updateSupplier(command);
			resultUtil.setResultSuccess();
		} catch (SupplierException e) {

			e.printStackTrace();
			resultUtil.setResultError();
			resultUtil.addAttr("message", e.getMessage());
		}

		return resultUtil.outputJsonString();
	}

	/**
	 * 跳转导入供应商页面
	 * 
	 * @return
	 */
	@RequestMapping("to_import")
	public String toImportSupplier() {

		return "/supplier/supplier_import.html";
	}

	/**
	 * 导入供应商
	 * 
	 * @param file
	 * @return
	 * @throws SupplierException
	 */
	@RequestMapping("import")
	@ResponseBody
	public String importSupplier(@RequestParam MultipartFile file) {
		List<List<String>> excelDataListList;
		try {
			ClassPathTool.getInstance();

			String oriFileName = file.getOriginalFilename();
			String filePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "upload" + File.separator + new Date().getTime()
					+ "." + oriFileName.substring(oriFileName.lastIndexOf(".") + 1);
			filePath = filePath.replaceAll("file:", "");
			File tempFile = new File(filePath);

			file.transferTo(tempFile);

			excelDataListList = ExcelUtils.getExecelStringValues(tempFile);
			tempFile.delete();
		} catch (Exception e) {
			return JsonResultUtil.dwzErrorMsg("文件解析失败");
		}

		if (excelDataListList == null || excelDataListList.get(0).size() != 22) {
			return JsonResultUtil.dwzErrorMsg("文件格式不正确");
		}

		for (int i = 1; i < excelDataListList.size(); i++) {
			for (int j = 0; j < excelDataListList.get(i).size(); j++) {
				excelDataListList.get(i).set(j, excelDataListList.get(i).get(j).trim());
			}
		}

		List<SupplierLinkManDTO> supplierLinkManList = Lists.newArrayList();
		List<SupplierDTO> supplierList = Lists.newArrayList();

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < excelDataListList.size(); i++) {
			if (StringUtils.isBlank(excelDataListList.get(i).get(0))) {
				sb.append("第").append(i + 1).append("行，供应商名称未填写&ltbr/&gt");
			} else {
				if (supplierService.supplierNameIsExisted(excelDataListList.get(i).get(0), null, true)) {
					sb.append("第").append(i + 1).append("行，供应商名称已存在&ltbr/&gt");
				}
			}

		}
		for (int i = 1; i < excelDataListList.size(); i++) {
			String supplierTypeStr = excelDataListList.get(i).get(1);
			if (!("经销".equals(supplierTypeStr) || "代销".equals(supplierTypeStr))) {
				sb.append("第").append(i + 1).append("行，供应商类型不正确&ltbr/&gt");
			}

		}

		for (int i = 1; i < excelDataListList.size(); i++) {
			for (int j = 1; j < excelDataListList.size(); j++) {
				String str1 = excelDataListList.get(i).get(0);
				String str2 = excelDataListList.get(j).get(0);
				if (i != j && StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2) && str1.equals(str2)) {
					sb.append("第").append(i + 1).append("行：供应商名称重复&ltbr/&gt");
				}
			}

		}

		for (int i = 1; i < excelDataListList.size(); i++) {

			SupplierDTO supplierDTO = new SupplierDTO();

			SupplierLinkManDTO linkManDTO = new SupplierLinkManDTO();
			List<String> list = excelDataListList.get(i);

			String supplierName = list.get(0);
			if (supplierName.length() > 200) {
				sb.append("第").append(i + 1).append("行，供应商名称过长&ltbr/&gt");
			} else {
				supplierDTO.setName(supplierName);
			}
			supplierDTO.setTypeName(list.get(1));

			String bank = list.get(2);
			if (bank.length() > 50) {
				sb.append("第").append(i + 1).append("行，开户银行过长&ltbr/&gt");
			} else {
				supplierDTO.setBank(bank);
			}
			
			supplierDTO.setAccount(list.get(3));
			
			String phone = list.get(4);
			if (phone.length()>15) {
				sb.append("第").append(i + 1).append("行，电话过长&ltbr/&gt");
			}else {
				supplierDTO.setPhone(phone);
			}
			supplierDTO.setAddress(list.get(5));
			supplierDTO.setPostCode(list.get(6));
			String email = list.get(7);
			if(email.length()>100){
				sb.append("第").append(i + 1).append("行，邮箱过长&ltbr/&gt");
				
			}else {
				supplierDTO.setEmail(email);
				
			}
			supplierDTO.setFax(list.get(8));
			supplierDTO.setURL(list.get(9));
			supplierDTO.setLegalPerson(list.get(10));
			supplierDTO.setTax(list.get(11));
			String registeredCapitalStr = list.get(12);

			if (StringUtils.isNotBlank(registeredCapitalStr)) {
				try {
					Double registeredCapital = Double.valueOf(registeredCapitalStr);
					supplierDTO.setRegisteredCapital(registeredCapital);
				} catch (Exception e) {
					sb.append("第").append(i + 1).append("行，注册资金填写不正确&ltbr/&gt");
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			if (StringUtils.isNotBlank(list.get(13))) {
				try {
					supplierDTO.setEstablishDate(sdf.parse(list.get(13)));
				} catch (Exception e) {
					sb.append("第").append(i + 1).append("行，日期格式填写不正确，请按“年/月/日”填写&ltbr/&gt例如：2012/01/01&ltbr/&gt");
				}
			}
			supplierDTO.setRemark(list.get(14));
			linkManDTO.setName(list.get(15));
			linkManDTO.setPost(list.get(16));
			String mobile = list.get(17);
			if (mobile.length()>11) {
				sb.append("第").append(i + 1).append("行，联系人手机号过长&ltbr/&gt");
			}else {
				linkManDTO.setMobile(mobile);
			}
			String phone2 = list.get(18);
			if (phone2.length()>15) {
				sb.append("第").append(i + 1).append("行，联系人电话过长&ltbr/&gt");
				
			}else {
				linkManDTO.setPhone(phone2);
				
			}
			linkManDTO.setQQ(list.get(19));
			linkManDTO.setEmail(list.get(20));
			supplierDTO.setSupplierCode(list.get(21));
			supplierList.add(supplierDTO);
			supplierLinkManList.add(linkManDTO);
		}
		if (sb.length() > 0) {
			return JsonResultUtil.dwzErrorMsg(sb.toString());
		}

		ImportBatchSupplierCommand command = new ImportBatchSupplierCommand();
		CommandUtil.SetOperator(getAuthUser(), command);
		command.setSupplierList(supplierList);
		command.setSupplierLinkManList(supplierLinkManList);

		try {
			supplierService.importSupplier(command, sb);
		} catch (SupplierException e) {
			e.printStackTrace();

			return JsonResultUtil.dwzErrorMsg("导入失败：&ltbr/&gt" + e.getMessage());
		}

		if (sb.length() > 0) {
			return JsonResultUtil.dwzErrorMsg(sb.toString());
		}
		return JsonResultUtil.dwzSuccessMsg("供应商导入成功", "supplier_list");
	}

	/**
	 * 供应商导出
	 * 
	 * @param response
	 * @param qo
	 */
	@RequestMapping("/export")
	public void exportSupplier(HttpServletResponse response, SupplierQO qo) {
		try {
			outputExcel(supplierService.exportSupplier(qo), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @RequestMapping("to_check")
	// public String toCheckSupplier(Model model, SupplierQO qo) {
	// Supplier supplier = supplierService.get(qo.getId());
	// model.addAttribute("supplier", supplier);
	//
	// SupplierCheckQO scQo = new SupplierCheckQO();
	// scQo.setSupplierId(supplier.getId());
	// List<SupplierCheck> supplierCheckList =
	// supplierCheckService.queryList(scQo);
	//
	// LinkManQO qo2 = new LinkManQO();
	// qo2.setSupplierId(supplier.getId());
	// List<LinkMan> linkmanList = linkManService.queryList(qo2);
	//
	// model.addAttribute("linkmanList", linkmanList);
	// model.addAttribute("supplierCheckList", supplierCheckList);
	//
	// SupplierAptitudeImageQO imgQo = new SupplierAptitudeImageQO();
	// imgQo.setSupplierId(supplier.getId());
	// List<SupplierAptitudeImage> imgList =
	// aptitudeImageService.queryList(imgQo);
	// for (SupplierAptitudeImage i : imgList) {
	// model.addAttribute("imgId" + i.getImageType(), i.getId());
	// model.addAttribute("imgSrc" + i.getImageType(), i.getUrl());
	// }
	// return "/supplier/supplier_check.html";
	//
	// }
	//
	// @RequestMapping("check")
	// @ResponseBody
	// public String checkSupplier(ModifySupplierStatusCommand command) {
	// if (command.getStatus() == 2 &&
	// StringUtils.isBlank(command.getContent())) {
	// return JsonResultUtil.dwzErrorMsg("审核不通过时审核意见必填");
	// }
	// CommandUtil.SetOperator(getAuthUser(), command);
	// Supplier supplier = supplierService.updateSupplierStatus(command);
	// supplierPriorityService.updateSupplierStatus(supplier);
	// return
	// DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200,
	// "供应商审核成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
	// "supplier_list");
	// }

	/**
	 * 查看供应商
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/view")
	public String viewSupplier(Model model, String id) {
		Supplier supplier = supplierService.get(id);
		model.addAttribute("supplier", supplier);

		LinkManQO linkManQO = new LinkManQO();
		linkManQO.setSupplierId(supplier.getId());
		List<SupplierLinkMan> linkmanList = linkManService.queryList(linkManQO);

		model.addAttribute("linkmanList", linkmanList);

		SupplierAptitudeImageQO imageQO = new SupplierAptitudeImageQO();
		imageQO.setSupplierId(supplier.getId());
		List<SupplierAptitudeImage> imageList = aptitudeImageService.queryList(imageQO);
		for (SupplierAptitudeImage image : imageList) {
			model.addAttribute("imgId" + image.getImageType(), image.getId());
			model.addAttribute("imgSrc" + image.getImageType(), image.getUrl());
		}

		return "/supplier/supplier_view.html";
	}

	/**
	 * 更新供应商联系人
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/linkman/update")
	@ResponseBody
	public String updateSupplierLinkman(ModifySupplierLinkManCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);
		List<SupplierLinkMan> linkMans = linkManService.updateSupplierLinkMan(command);

		JsonResultUtil resultUtil = new JsonResultUtil();
		for (SupplierLinkMan linkMan : linkMans) {
			resultUtil.linkString(linkMan.getId());
		}

		resultUtil.addAttr("ids", resultUtil.getLinkedString());
		resultUtil.setResultSuccess();

		return resultUtil.outputJsonString();
	}

	/**
	 * 删除供应商联系人
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/linkman/delete")
	@ResponseBody
	public String deleteLinkman(DeleteSupplierLinkManCommand command) {
		try {

			CommandUtil.SetOperator(getAuthUser(), command);
			linkManService.deleteSupplierLinkMan(command);

			return JsonResultUtil.successResult(null, null);
		} catch (Exception e) {
			return JsonResultUtil.errorResult(null, null);
		}
	}

	@RequestMapping("lookup")
	public String supplierLookup(Model model) {
		SupplierQO qo = new SupplierQO();
		List<Supplier> supplierList = supplierService.queryList(qo);

		model.addAttribute("supplierList", supplierList);
		return "/supplier/supplier_lookup.html";
	}

}