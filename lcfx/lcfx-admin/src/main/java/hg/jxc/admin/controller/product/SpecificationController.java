package hg.jxc.admin.controller.product;

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
import hg.pojo.command.CreateSpecificationCommand;
import hg.pojo.command.DeleteSpecificationCommand;
import hg.pojo.command.ImportBatchSpecificationCommand;
import hg.pojo.command.ModifySpecificationCommand;
import hg.pojo.command.ModifySpecificationStatusCommand;
import hg.pojo.dto.product.SpecificationDTO;
import hg.pojo.exception.JxcException;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.SkuProductQO;
import hg.pojo.qo.SpecDetailQO;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;
import hg.system.service.SecurityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxc.app.service.product.ProductCategoryService;
import jxc.app.service.product.ProductService;
import jxc.app.service.product.SkuProductService;
import jxc.app.service.product.SpecDetailService;
import jxc.app.service.product.SpecValueService;
import jxc.app.service.product.SpecificationService;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;
import jxc.domain.model.product.Specification;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/specification")
public class SpecificationController extends BaseController {
	@Autowired
	SecurityService securityService;

	@Autowired
	SpecificationService specificationService;

	@Autowired
	ProductCategoryService categoryService;

	@Autowired
	SpecValueService specValueService;

	@Autowired
	ProductService productService;
	
	@Autowired
	SkuProductService skuProductService;

	@Autowired
	SpecDetailService specDetailService;

	@RequestMapping("/list")
	public String querySpecificationList(Model model, @ModelAttribute SpecificationQO specificationQO, @ModelAttribute DwzPaginQo dwzPaginQo) {
		specificationQO.setSpecNameLike(true);

		Pagination pagination = createPagination(dwzPaginQo, specificationQO);
		pagination = specificationService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		PermUtil.addPermAttr4List(securityService, model, getAuthUser());

		return "/product/specification/specification_list.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createSpecification(CreateSpecificationCommand createSpecificationCommand) {
		CommandUtil.SetOperator(getAuthUser(), createSpecificationCommand);
		boolean specValIsEmpty = true;
		for (String specVal : createSpecificationCommand.getSpecValueList()) {
			if (StringUtils.isNotBlank(specVal)) {
				specValIsEmpty = false;
				break;
			}
		}
		if (specValIsEmpty) {
			return JsonResultUtil.dwzErrorMsg("未填写规格值，添加失败");
		}
		if (!specificationService.checkUniqueSpecification(createSpecificationCommand.getSpecificationName(), createSpecificationCommand.getCategoryId())) {
			return JsonResultUtil.dwzErrorMsg("规格名称重复");
		}
		try {
			specificationService.createSpecification(createSpecificationCommand);
		} catch (JxcException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsg("添加成功", "specification_list");
	}

	@RequestMapping("/to_add")
	public String toAddSpecification(Model model) {

		return "product/specification/specification_add.html";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String deleteSpecification(DeleteSpecificationCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		List<String> ids = command.getSpecificationListId();
		for (String id : ids) {
			if (specificationService.checkSpecificationProduct(id) == 1) {
				return JsonResultUtil.dwzErrorMsg("该规格已被使用，不能删除");
			}

		}
		specificationService.deleteSpecification(command);

		return DwzJsonResultUtil.createJsonString("200", "删除成功", null, "specification_list");

	}

	@RequestMapping("/edit")
	public String editSpecification(Model model, SpecificationQO spQo) {

		SpecValueQO qo = new SpecValueQO();
		qo.setSpecificationId(spQo.getId());
		List<SpecValue> specValueList = specValueService.queryList(qo);

		Specification specification = specificationService.queryUnique(spQo);
		model.addAttribute("specification", specification);
		model.addAttribute("specValueList", specValueList);
		return "product/specification/specification_edit.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateSpecification(ModifySpecificationCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		// 判断全空
		boolean specValIsEmpty = true;
		for (String specVal : command.getSpecValueList()) {
			if (StringUtils.isNotBlank(specVal)) {
				specValIsEmpty = false;
				break;
			}
		}
		if (specValIsEmpty) {
			return JsonResultUtil.dwzErrorMsg("未填写规格值，添加失败");
		}
		Specification self = specificationService.get(command.getSpecificationId());
		SpecValueQO specValqo = new SpecValueQO();
		specValqo.setSpecificationId(command.getSpecificationId());
		List<SpecValue> oriSpecValList = specValueService.queryList(specValqo);
		List<String> nowSpecValList = command.getSpecValueList();
		for (int i = 0; i < oriSpecValList.size(); i++) {
			SpecValue specValue = oriSpecValList.get(i);
			for (String s : nowSpecValList) {
				if (s.equals(specValue.getSpecValue())) {
					oriSpecValList.set(i, null);
				}
			}
		}
		if (!specificationService.checkUpdateSpecValue(command)) {
			return JsonResultUtil.dwzErrorMsg("规格值已被使用修改失败");
		}
		if (!specificationService.checkUniqueSpecification(command.getSpecificationName(), command.getCategoryId())
				&& !command.getSpecificationName().equals(self.getSpecName())) {
			return JsonResultUtil.dwzErrorMsg("规格名称重复");
		}
		command.setCreateDate(self.getCreateDate());
		command.setSpecificationCode(self.getSpecCode());
		command.setUsing(self.getStatus().getUsing());
		try {
			specificationService.updateSpecification(command);
		} catch (JxcException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
				"specification_list");
	}

	@RequestMapping("/to_import")
	public String toImportBrand() {

		return "product/specification/specification_import.html";
	}

	@RequestMapping("/import")
	@ResponseBody
	public String importSpecification(@RequestParam MultipartFile file) {
		ClassPathTool.getInstance();
		String tempFilePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "upload" + UUIDGenerator.getUUID()
				+ file.getOriginalFilename();
		tempFilePath = tempFilePath.replaceAll("file:", "");

		String message;
		try {
			File tempFile = new File(tempFilePath);
			try {
				file.transferTo(tempFile);
			} catch (IllegalStateException e) {
				return JsonResultUtil.dwzErrorMsg("文件上传失败");
			} catch (IOException e) {
				return JsonResultUtil.dwzErrorMsg("文件上传失败");
			}

			List<List<String>> dataListList = ExcelUtils.getExecelStringValues(tempFile);

			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < dataListList.size(); i++) {
				if (StringUtils.isBlank(dataListList.get(i).get(0))) {
					sb.append("第").append(i + 1).append("行：规格名称未填&ltbr/&gt");
				}
				if (StringUtils.isBlank(dataListList.get(i).get(1))) {
					sb.append("第").append(i + 1).append("行：所属商品分类名称未填&ltbr/&gt");
				}
				if (StringUtils.isBlank(dataListList.get(i).get(2))) {
					sb.append("第").append(i + 1).append("行：状态未填&ltbr/&gt");
				}
				if (StringUtils.isBlank(dataListList.get(i).get(3))) {
					sb.append("第").append(i + 1).append("行：规格值未填&ltbr/&gt");
				}
			}

			if (sb.length() > 0) {
				return JsonResultUtil.dwzErrorMsg(sb.toString());
			}

			for (int i = 1; i < dataListList.size(); i++) {
				for (int j = 1; j < dataListList.size(); j++) {
					if (i != j) {
						List<String> dataLinei = dataListList.get(i);
						List<String> dateLinej = dataListList.get(j);
						if (dataLinei.get(1).equals(dateLinej.get(1))) {
							String specNamei = dataLinei.get(0);
							String specNamej = dateLinej.get(0);

							if (specNamei.equals(specNamej)) {
								sb.append("第").append(i + 1).append("行：在相同分类：").append(dataLinei.get(1)).append(" 下规格名称：").append(specNamei)
										.append(" 重复&ltbr/&gt");
							}
						}

					}

				}
			}
			for (int i = 1; i < dataListList.size(); i++) {
				String[] specValArr = dataListList.get(i).get(3).split(" ");

				for (int ii = 0; ii < specValArr.length; ii++) {
					for (int jj = 0; jj < specValArr.length; jj++) {
						String specValii = specValArr[ii];
						String specValjj = specValArr[jj];
						if (ii > jj && StringUtils.isNotBlank(specValii) && StringUtils.isNotBlank(specValjj) && specValjj.equals(specValii)) {
							sb.append("第").append(i + 1).append("行：在同一规格下规格值：").append(specValii).append(" 重复&ltbr/&gt");

						}
					}
				}

				for (int j = 1; j < dataListList.size(); j++) {
					if (i != j) {
						List<String> dataLinei = dataListList.get(i);
						List<String> dateLinej = dataListList.get(j);
						if (dataLinei.get(1).equals(dateLinej.get(1))) {
							String specNamei = dataLinei.get(0);
							String specNamej = dateLinej.get(0);

							if (specNamei.equals(specNamej)) {
								sb.append("第").append(i + 1).append("行：在相同分类：").append(dataLinei.get(1)).append(" 下规格名称：").append(specNamei)
										.append(" 重复&ltbr/&gt");
							}
						}

					}

				}
			}

			for (int i = 1; i < dataListList.size(); i++) {
				for (int j = 1; j < dataListList.size(); j++) {
					if (i != j) {
						List<String> dataLinei = dataListList.get(i);
						List<String> dateLinej = dataListList.get(j);
						if (dataLinei.get(1).equals(dateLinej.get(1))) {
							String[] specVali = dataLinei.get(3).split(" ");
							String[] specValj = dateLinej.get(3).split(" ");

							for (int ii = 0; ii < specVali.length; ii++) {
								for (int jj = 0; jj < specValj.length; jj++) {
									String specValStrii = specVali[ii];
									String specValStrjj = specValj[jj];
									if (specValStrii.equals(specValStrjj)) {
										sb.append("第").append(i + 1).append("行：在相同分类：").append(dataLinei.get(1)).append(" 下规格值：").append(specValStrii)
												.append(" 重复&ltbr/&gt");
									}
								}
							}
						}

					}

				}
			}
			if (sb.length() > 0) {
				return JsonResultUtil.dwzErrorMsg(sb.toString());
			}

			List<SpecificationDTO> dtos = new ArrayList<SpecificationDTO>();
			for (int i = 0; i < dataListList.size(); i++) {
				if (i == 0) {
					continue;
				}
				List<String> list = dataListList.get(i);

				SpecificationDTO dto = new SpecificationDTO();
				dto.setSpecName(list.get(0));
				dto.setCategoryName(list.get(1));
				dto.setUsing(list.get(2));
				dto.setSpecValueName(list.get(3));
				// TODO
				dtos.add(dto);
			}
			ImportBatchSpecificationCommand command = new ImportBatchSpecificationCommand();
			CommandUtil.SetOperator(getAuthUser(), command);
			command.setSpecificationList(dtos);
			specificationService.importSpecification(command);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtil.dwzErrorMsg("导入失败&ltbr/&gt" + e.getMessage());
		}
		return JsonResultUtil.dwzSuccessMsg("导入成功", "specification_list");

	}

	@RequestMapping("/export")
	public void exportSpecification(HttpServletResponse response, SpecificationQO qo) {
		qo.setSpecNameLike(true);

		try {
			outputExcel(specificationService.exportSpecification(qo), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/get_spec_table")
	@ResponseBody
	public String querySpecificationAjax(ProductQO qo, @RequestParam(required = true) String productId) {

		qo.setId(productId);
		Product product = productService.queryUnique(qo);
		SpecificationQO specificationQO = new SpecificationQO();
		specificationQO.setProductCategoryId(product.getProductCategory().getId());
		specificationQO.setUsing(true);
		List<Specification> SpecificationList = specificationService.queryList(specificationQO);
		if (SpecificationList.size() == 0) {
			return "该分类下没有规格";
		}

		// 查找选中的规格
		SkuProductQO skuProductQO = new SkuProductQO();
		skuProductQO.setProductId(productId);
		List<SkuProduct> skuProductList = skuProductService.queryList(skuProductQO);

		List<SpecDetail> sdList = new ArrayList<SpecDetail>();
		for (SkuProduct skuProduct : skuProductList) {
			SpecDetailQO specDetailQo = new SpecDetailQO();
			specDetailQo.setSkuProductId(skuProduct.getId());
			List<SpecDetail> specDetailList = specDetailService.queryList(specDetailQo);
			sdList.addAll(specDetailList);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<table border=\"0\" style=\"padding:10px;\" class=\"spec_sel_table\">");
		for (Specification spec : SpecificationList) {
			sb.append("<tr><td><a>规格名称：</a><b>");
			sb.append(spec.getSpecName());
			sb.append("</b><input type=\"hidden\" value=\"");
			sb.append(spec.getId());
			sb.append("\" name=\"specification\" />");
			sb.append("</td></tr>");
			sb.append("<tr><td><a style=\"width:64px\">规格值：</a>");

			SpecValueQO specValueQO = new SpecValueQO();
			specValueQO.setSpecificationId(spec.getId());
			List<SpecValue> specValues = specValueService.queryList(specValueQO);
			for (SpecValue specValue : specValues) {
				sb.append("<input name=");
				sb.append(spec.getId());
				sb.append(" type=\"checkbox\" value=");
				sb.append(specValue.getId());
				for (SpecDetail sd : sdList) {
					if (specValue.getId().equals(sd.getSpecValue().getId())) {
						sb.append(" checked=\"checked\"");
					}
				}
				sb.append(" />");
				sb.append("<label>");
				sb.append(specValue.getSpecValue());
				sb.append("</label>");
			}
			sb.append("</td></tr>");
			sb.append("<tr><td><div ></div></td></tr>");
		}

		sb.append("<table>");

		return sb.toString();

	}

	@RequestMapping("/change_status")
	@ResponseBody
	public String changeStatus(ModifySpecificationStatusCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		if (specificationService.checkSpecificationProduct(command.getSpecificationId()) == 1) {
			return JsonResultUtil.dwzErrorMsg("该规格已被使用，不能禁用");

		}
		specificationService.updateSpecificationStatus(command);
		return DwzJsonResultUtil.createJsonString("200", "状态修改成功", null, "specification_list");

	}

}