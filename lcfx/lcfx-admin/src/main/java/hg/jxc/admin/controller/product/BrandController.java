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
import hg.pojo.command.CreateBrandCommand;
import hg.pojo.command.DeleteBrandCommand;
import hg.pojo.command.ImportBatchBrandCommand;
import hg.pojo.command.ModifyBrandCommand;
import hg.pojo.dto.product.BrandDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.BrandQO;
import hg.system.service.SecurityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxc.app.service.product.BrandService;
import jxc.domain.model.product.Brand;

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
@RequestMapping("/brand")
public class BrandController extends BaseController {
	@Autowired
	BrandService brandService;

	@Autowired
	SecurityService securityService;

	/**
	 * 分页查询品牌列表
	 * 
	 * @param model
	 * @param dwzPaginQo
	 * @param brandQO
	 * @return
	 */
	@RequestMapping("/list")
	public String queryBrandList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo, @ModelAttribute BrandQO brandQO) {
		// 模糊查询
		brandQO.setChineseNameLike(true);
		brandQO.setEnglishNameLike(true);

		Pagination pagination = createPagination(dwzPaginQo, brandQO);
		pagination = brandService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		PermUtil.addPermAttr4List(securityService, model, getAuthUser());

		return "/product/brand/brand_list.html";
	}

	/**
	 * 跳转增加商品页面
	 * 
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddBrand() {
		return "product/brand/brand_add.html";
	}

	/**
	 * 新增品牌
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createBrand(CreateBrandCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);
		try {

			brandService.createBrand(command);
			return DwzJsonResultUtil
					.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "品牌添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "brand_list");

		} catch (ProductException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

	}

	/**
	 * 删除商品品牌
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteBrand(DeleteBrandCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			brandService.removeBrand(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "商品品牌删除成功", null, "brand_list");

		} catch (ProductException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

	}

	/**
	 * 跳转商品品牌编辑页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editBrand(Model model, @RequestParam(value = "id", required = true) String id) {
		Brand brand = brandService.get(id);
		model.addAttribute("brand", brand);

		return "product/brand/brand_edit.html";
	}

	/**
	 * 更新商品品牌
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateBrand(ModifyBrandCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			brandService.updateBrand(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "商品品牌修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
					"brand_list");

		} catch (ProductException e) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

	}

	/**
	 * 跳转到商品品牌导入页面
	 * 
	 * @return
	 */
	@RequestMapping("/to_import")
	public String toImportBrand() {

		return "product/brand/brand_import.html";
	}

	/**
	 * 商品导入
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public String importBrand(@RequestParam MultipartFile file) {
		String tempFilePath = ClassPathTool.getInstance().getWebRootPath() + File.separator + "excel" + File.separator + "upload" + UUIDGenerator.getUUID()
				+ file.getOriginalFilename();
		tempFilePath = tempFilePath.replaceAll("file:", "");

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
			tempFile.delete();
			StringBuffer sb = new StringBuffer();
			// 判断中文名填写
			for (int i = 1; i < dataListList.size(); i++) {
				if (StringUtils.isBlank(dataListList.get(i).get(0))) {
					sb.append("第").append(i + 1).append("行：品牌中文名未填写&ltbr/&gt");
				}

			}
			for (int i = 1; i < dataListList.size(); i++) {
				for (int j = 1; j < dataListList.size(); j++) {
					String str1 = dataListList.get(i).get(0);
					String str2 = dataListList.get(j).get(0);
					if (i!=j&&StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2) && str1.equals(str2)) {
						sb.append("第").append(i + 1).append("行：品牌中文名重复&ltbr/&gt");
					}
				}

			}
			//
			if (dataListList.size() < 2 || dataListList.get(0).size() != 3) {
				return JsonResultUtil.dwzErrorMsg("文件格式错误");
			}
			List<BrandDTO> dtos = new ArrayList<BrandDTO>();
			for (int i = 0; i < dataListList.size(); i++) {
				if (i == 0) {
					continue;
				}
				List<String> list = dataListList.get(i);

				BrandDTO dto = new BrandDTO();

				dto.setChineseName(list.get(0));
				dto.setEnglishName(list.get(1));
				dto.setRemark(list.get(2));

				dtos.add(dto);

			}

			if (sb.length() > 0) {
				return JsonResultUtil.dwzErrorMsg("导入失败&ltbr/&gt" + sb.toString());
			}
			ImportBatchBrandCommand command = new ImportBatchBrandCommand();
			CommandUtil.SetOperator(getAuthUser(), command);
			command.setBrandList(dtos);

			String message = brandService.importBrand(command);

			if (StringUtils.isBlank(message)) {
				return JsonResultUtil.dwzSuccessMsg("导入成功", "brand_list");
			}

			message = message.replace(",", "&ltbr/&gt");
			message = message.replace("{", "");
			message = message.replace("}", "");
			message = message.replace("]", "");
			message = message.replace("[", "");
			message = message.replace("\"", "");
			message = message.replace(";", "；");
			return JsonResultUtil.dwzErrorMsg(message);

		} catch (Exception e) {
			return JsonResultUtil.dwzErrorMsg("导入失败：" + e.getMessage());
		}
	}

	/**
	 * 商品品牌导出
	 * 
	 * @param response
	 * @param qo
	 */
	@RequestMapping("/export")
	public void exportBrand(HttpServletResponse response, BrandQO qo) {
		// 模糊查询
		qo.setChineseNameLike(true);
		qo.setEnglishNameLike(true);

		try {
			outputExcel(brandService.exportBrand(qo), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}