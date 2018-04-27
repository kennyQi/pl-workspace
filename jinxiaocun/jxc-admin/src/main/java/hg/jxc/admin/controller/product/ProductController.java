package hg.jxc.admin.controller.product;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.JsonUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.PermUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateProductBaseInfoCommand;
import hg.pojo.command.DeleteDealerProductMappingCommand;
import hg.pojo.command.DeleteProductCommand;
import hg.pojo.command.ImportBatchProductCommand;
import hg.pojo.command.ModifyDealerProductMappingCommand;
import hg.pojo.command.ModifyProductCommand;
import hg.pojo.command.ModifyProductDescribeCommand;
import hg.pojo.command.ModifyProductSkuCommand;
import hg.pojo.command.ModifyProductStatusCommand;
import hg.pojo.command.UploadProductImageCommand;
import hg.pojo.dto.product.DealerProductMappingDTO;
import hg.pojo.dto.product.ProductDTO;
import hg.pojo.dto.product.SkuProductDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.BrandQO;
import hg.pojo.qo.ProductImageQO;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.SkuProductQO;
import hg.pojo.qo.SpecDetailQO;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;
import hg.pojo.qo.UnitQO;
import hg.system.service.SecurityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.app.service.image.ProductImageService;
import jxc.app.service.product.BrandService;
import jxc.app.service.product.DealerProductMappingService;
import jxc.app.service.product.ProductCategoryService;
import jxc.app.service.product.ProductService;
import jxc.app.service.product.SkuProductService;
import jxc.app.service.product.SpecDetailService;
import jxc.app.service.product.SpecValueService;
import jxc.app.service.product.SpecificationService;
import jxc.app.service.system.ProjectService;
import jxc.app.service.system.UnitService;
import jxc.app.service.warehousing.PurchaseOrderService;
import jxc.domain.model.image.ProductImage;
import jxc.domain.model.product.Brand;
import jxc.domain.model.product.DealerProductMapping;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductCategory;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;
import jxc.domain.model.product.Specification;
import jxc.domain.model.system.Project;
import jxc.domain.model.system.Unit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
	@Autowired
	ProductService productService;

	@Autowired
	BrandService brandService;

	@Autowired
	ProductCategoryService categoryService;

	@Autowired
	UnitService unitService;

	@Autowired
	SkuProductService skuProductService;

	@Autowired
	SpecDetailService specDetailService;

	@Autowired
	SpecificationService specificationService;

	@Autowired
	SpecValueService specValueService;

	@Autowired
	ProjectService projectService;

	@Autowired
	DealerProductMappingService dealerProductMappingService;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	SecurityService securityService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	/**
	 * 商品列表
	 * 
	 * @param model
	 * @param dwzPaginQo
	 * @param productQO
	 * @return
	 */
	@RequestMapping("/list")
	public String queryProductList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo, @ModelAttribute ProductQO productQO) {
		productQO.setNameLike(true);
		productQO.setProductCodeLike(true);
		String categoryId = productQO.getProductCategoryId();
		if (StringUtils.isNotBlank(categoryId)) {
			Set<ProductCategory> categorys = categoryService.findEndCategory(productQO.getProductCategoryId(), null);
			StringBuffer category = new StringBuffer();
			if (categorys != null && categorys.size() > 0) {
				for (ProductCategory productCategory : categorys) {
					category.append(productCategory.getId()).append(",");
				}
				if (category.length() > 0) {
					category.deleteCharAt(category.length() - 1);
				}
				productQO.setProductCategoryId(category.toString());
			}

		}
		Pagination pagination = createPagination(dwzPaginQo, productQO);
		pagination = productService.queryPagination(pagination);
		List<Brand> brandList = brandService.queryList(new BrandQO());
		productQO.setProductCategoryId(categoryId);
		model.addAttribute("pagination", pagination);
		model.addAttribute("brandList", brandList);

		PermUtil.addPermAttr4List(securityService, model, getAuthUser());
		return "/product/product_list.html";

	}

	/**
	 * 保存商品
	 * 
	 * @param command
	 * @param request
	 * @param extraUnitName
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createProduct(CreateProductBaseInfoCommand command, HttpServletRequest request) {
		CommandUtil.SetOperator(getAuthUser(), command);
		JsonResultUtil jru = new JsonResultUtil();
		if (productService.productNameIsExisted(command.getProductName(), null, true)) {
			jru.setResultError();
			jru.addAttr("message", "保存失败，商品名重复");
			return jru.outputJsonString();
		}
		Product product = productService.createProduct(command);
		jru.addAttr("id", product.getId());
		jru.addAttr("code", product.getProductCode());
		jru.setResultSuccess();
		return jru.outputJsonString();
	}

	/**
	 * 跳转到“添加商品”界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddProduct(Model model) {
		List<Unit> unitList = unitService.queryList(new UnitQO());
		model.addAttribute("unitList", unitList);
		List<Brand> brandList = brandService.queryList(new BrandQO());
		model.addAttribute("brandList", brandList);

		return "product/product_add.html";
	}

	/**
	 * 删除商品
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteProduct(DeleteProductCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		try {
			productService.deleteProduct(command);
		} catch (ProductException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功", null, "product_list");
	}

	/**
	 * 跳转到编辑
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editProduct(Model model, @RequestParam(value = "id", required = true) String id) {
		List<Unit> unitList = unitService.queryList(new UnitQO());
		model.addAttribute("unitList", unitList);
		List<Brand> brandList = brandService.queryList(new BrandQO());
		model.addAttribute("brandList", brandList);
		ProductQO productQo = new ProductQO();
		productQo.setId(id);
		Product product = productService.queryUnique(productQo);
		model.addAttribute("product", product);

		Set<Integer> seqList = productService.findSequenceByProductId(id);
		model.addAttribute("seqList", seqList);

		ProductImageQO qo = new ProductImageQO();
		qo.setProductId(id);
		List<ProductImage> imgList = productImageService.queryList(qo);
		for (ProductImage i : imgList) {
			model.addAttribute("imgId" + i.getImageType(), i.getId());
			model.addAttribute("imgSrc" + i.getImageType(), i.getUrl());
		}
		return "product/product_add.html";
	}

	/**
	 * 修改商品
	 * 
	 * @param command
	 * @param extraUnitName
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateProduct(ModifyProductCommand command) {
		JsonResultUtil jsonResult = new JsonResultUtil();
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			productService.updateProduct(command);
		} catch (ProductException e) {
			jsonResult.setResultError();
			jsonResult.addAttr("message", e.getMessage());
			return jsonResult.outputJsonString();
		}
		jsonResult.setResultSuccess();
		return jsonResult.outputJsonString();
	}

	/**
	 * 跳转到导出
	 * 
	 * @return
	 */
	@RequestMapping("/to_import")
	public String toImportProduct() {

		return "product/product_import.html";
	}

	/**
	 * 导入商品
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public String importProduct(@RequestParam MultipartFile file) {
		ClassPathTool.getInstance();

		String oriFileName = file.getOriginalFilename();
		String filePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "upload" + File.separator + new Date().getTime() + "."
				+ oriFileName.substring(oriFileName.lastIndexOf(".") + 1);
		filePath = filePath.replaceAll("file:", "");
		File tempFile = new File(filePath);

		try {
			file.transferTo(tempFile);
		} catch (Exception e) {
			return JsonResultUtil.dwzErrorMsg("文件上传失败");
		}
		List<List<String>> ed = ExcelUtils.getExecelStringValues(tempFile);
		if (ed == null || ed.size() <= 1 || ed.get(0).size() != 16) {
			return JsonResultUtil.dwzErrorMsg("文件格式不正确或者数据不完整，解析失败");
		}

		StringBuffer sb = new StringBuffer("");

		// 删除临时文件
		tempFile.delete();

		for (int i = 1; i < ed.size(); i++) {
			for (int j = 0; j < ed.get(i).size(); j++) {
				ed.get(i).set(j, ed.get(i).get(j).trim());
			}
		}

		// 判断必填项
		for (int i = 1; i < ed.size(); i++) {
			if (StringUtils.isBlank(ed.get(i).get(0)) || StringUtils.isBlank(ed.get(i).get(1)) || StringUtils.isBlank(ed.get(i).get(2))
					|| StringUtils.isBlank(ed.get(i).get(5)) || StringUtils.isBlank(ed.get(i).get(6)) || StringUtils.isBlank(ed.get(i).get(4))) {
				sb.append("第").append(i + 1).append("行，缺少必填项&ltbr/&gt");

			}
		}

		for (int i = 1; i < ed.size(); i++) {
			boolean sizeAllEmpty = (StringUtils.isBlank(ed.get(i).get(9)) && StringUtils.isBlank(ed.get(i).get(10)) && StringUtils.isBlank(ed.get(i).get(11)));
			boolean sizeAllNotEmpty = (StringUtils.isNotBlank(ed.get(i).get(9)) && StringUtils.isNotBlank(ed.get(i).get(10)) && StringUtils.isNotBlank(ed
					.get(i).get(11)));
			boolean outSizeAllEmpty = (StringUtils.isBlank(ed.get(i).get(12)) && StringUtils.isBlank(ed.get(i).get(12)) && StringUtils.isBlank(ed.get(i)
					.get(14)));
			boolean outSizeAllNotEmpty = (StringUtils.isNotBlank(ed.get(i).get(12)) && StringUtils.isNotBlank(ed.get(i).get(13)) && StringUtils.isNotBlank(ed
					.get(i).get(14)));

			if (!((sizeAllEmpty || sizeAllNotEmpty) && (outSizeAllEmpty || outSizeAllNotEmpty))) {
				sb.append("第").append(i + 1).append("行，商品尺寸、出库尺寸必须都填或都不填&ltbr/&gt");
			}
		}

		for (int i = 1; i < ed.size(); i++) {
			if (!brandService.brandNameIsExisted(ed.get(i).get(2), null)) {
				sb.append("第").append(i + 1).append("行，商品品牌不存在&ltbr/&gt");
			}
		}
		for (int i = 1; i < ed.size(); i++) {
			if (!unitService.checkNameIsExisted(ed.get(i).get(4), null)) {
				sb.append("第").append(i + 1).append("行，商品单位不存在&ltbr/&gt");
			}
		}
		for (int i = 1; i < ed.size(); i++) {
			SpecificationQO specifictionQo = new SpecificationQO();
			specifictionQo.setCategoryName(ed.get(i).get(0));
			Specification specifiction = specificationService.queryUnique(specifictionQo);

			if (specifiction == null) {
				sb.append("第").append(i + 1).append("行，商品分类不存在&ltbr/&gt");
			} else {
				for (int ii = 1; ii < ed.size(); ii++) {
					String[] specValueList = ed.get(ii).get(3).split(" ");
					for (int k = 0; k < specValueList.length; k++) {
						SpecValueQO qo = new SpecValueQO();
						qo.setName(specValueList[k]);
						SpecValue specValue = specValueService.queryUniqueWithSpec(qo);
						if (specValue == null) {
							sb.append("第").append(ii + 1).append("行，商品规格不存在&ltbr/&gt");
							break;
						} else {
							if (!(specifiction.getProductCategory().getId()).equals(specValue.getSpecification().getProductCategory().getId())) {
								sb.append("第").append(ii + 1).append("行，商品规格:").append(specValueList[k]).append(" 不存于该分类&ltbr/&gt");
								break;
							}
						}
					}
				}
			}
		}
		for (int i = 1; i < ed.size(); i++) {
			if (productService.productNameIsExisted(ed.get(i).get(1), null, true)) {
				sb.append("第").append(i + 1).append("行，商品名称已存在&ltbr/&gt");
			}
		}

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (int i = 1; i < ed.size(); i++) {
			List<String> list = ed.get(i);

			ProductDTO productDTO = new ProductDTO();

			productDTO.setProductName(list.get(1));
			productDTO.setBrandName(list.get(2));
			productDTO.setCategoryName(list.get(0));
			productDTO.setRemark(list.get(15));
			try {
				productDTO.setWeight(Double.valueOf(list.get(7)));
				productDTO.setOutStockWeight(Double.valueOf(list.get(8)));

			} catch (Exception e) {
				sb.append("第").append(i).append("行，商品重量、出库重量填写不正确&ltbr/&gt");
			}
			productDTO.setUnitName(list.get(4));
			productDTO.setAttribute(list.get(6));
			productDTO.setSpecValue(list.get(3));
			productDTO.setUsing(list.get(5));
			try {
				productDTO.setSizeWidth(Double.valueOf(list.get(10)));
				productDTO.setSizeHigh(Double.valueOf(list.get(11)));
				productDTO.setSizeLong(Double.valueOf(list.get(9)));
				productDTO.setOutstockSizeWidth(Double.valueOf(list.get(13)));
				productDTO.setOutstockSizeHigh(Double.valueOf(list.get(14)));
				productDTO.setOutstockSizeLong(Double.valueOf(list.get(12)));
			} catch (Exception e) {
				sb.append("第").append(i).append("行，商品尺寸、出库尺寸填写不正确&ltbr/&gt");
			}

			productDTOs.add(productDTO);

		}
		if (sb.length() != 0) {
			return JsonResultUtil.dwzErrorMsg(sb.toString());

		}

		ImportBatchProductCommand command = new ImportBatchProductCommand();
		CommandUtil.SetOperator(getAuthUser(), command);
		command.setProductList(productDTOs);
		String message;
		message = productService.importProduct(command);
		if (StringUtils.isBlank(message)) {
			return JsonResultUtil.dwzSuccessMsg("导入成功", "product_list");
		}
		if ("0".equals(message)) {
			return JsonResultUtil.dwzErrorMsg("导入失败,格式不正确");
		}
		message = message.replace(",", "&ltbr/&gt");
		message = message.replace("{", "");
		message = message.replace("}", "");
		message = message.replace("]", "");
		message = message.replace("[", "");
		message = message.replace("\"", "");
		message = message.replace(";", "；");
		return JsonResultUtil.dwzErrorMsg(message);
	}

	/**
	 * 导出
	 * 
	 * @param response
	 * @param qo
	 */
	@RequestMapping("/export")
	public void exportProduct(HttpServletResponse response, ProductQO qo) {
		qo.setNameLike(true);
		qo.setProductCodeLike(true);
		String categoryId = qo.getProductCategoryId();
		if (StringUtils.isNotBlank(categoryId)) {
			Set<ProductCategory> categorys = categoryService.findEndCategory(qo.getProductCategoryId(), null);
			StringBuffer category = new StringBuffer();
			if (categorys != null && categorys.size() > 0) {
				for (ProductCategory productCategory : categorys) {
					category.append(productCategory.getId()).append(",");
				}
				if (category.length() > 0) {
					category.deleteCharAt(category.length() - 1);
				}
				qo.setProductCategoryId(category.toString());
			}

		}
		try {
			outputExcel(productService.exportProduct(qo), response);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 输出sku列表
	 * 
	 * @param response
	 * @param qo
	 * @return sku列表的html代码
	 */
	@RequestMapping("/sku_table")
	@ResponseBody
	public String getSkuTable(HttpServletResponse response, SkuProductQO qo) {
		// 获得sku商品
		List<SkuProduct> skuProdList = skuProductService.queryList(qo);
		if (skuProdList.size() == 0) {
			return "<a name=\"no_sku_record\">没有商品sku信息<a>";
		}

		int tableHeight = skuProdList.size() + 1;
		int tableWidth = 0;

		List<SpecDetail> skuDetailLong = new ArrayList<SpecDetail>();
		// 查询对应sku详情信息
		for (SkuProduct skuProduct : skuProdList) {
			SpecDetailQO specDetailQO = new SpecDetailQO();
			specDetailQO.setSkuProductId(skuProduct.getId());
			List<SpecDetail> skuDetail = specDetailService.queryList(specDetailQO);
			for (SpecDetail specDetail : skuDetail) {
				boolean have = false;
				for (SpecDetail skuSpecDetail : skuDetailLong) {
					if (skuSpecDetail.getSpecification().getId().equals(specDetail.getSpecification().getId())) {
						have = true;
						break;
					}
				}
				if (!have) {
					skuDetailLong.add(specDetail);
				}
			}
			skuProduct.setSpecDetails(skuDetail);
		}
		tableWidth = skuDetailLong.size() + 1;
		String[][] data = new String[tableHeight][tableWidth];

		for (int i = 0; i < skuDetailLong.size(); i++) {
			data[0][i] = skuDetailLong.get(i).getSpecification().getSpecName();
		}

		// 设置sku编码
		data[0][tableWidth - 1] = "sku编码";

		for (int i = 0; i < skuProdList.size(); i++) {
			data[i + 1][tableWidth - 1] = skuProdList.get(i).getId();
		}

		for (int i = 0; i < skuProdList.size(); i++) {
			List<SpecDetail> specDetailList = skuProdList.get(i).getSpecDetails();
			for (SpecDetail specDetail : specDetailList) {
				for (int j = 0; j < tableWidth; j++) {
					String dataStr = data[0][j];
					if (dataStr != null && dataStr.equals(specDetail.getSpecification().getSpecName())) {
						data[i + 1][j] = specDetail.getSpecValue().getSpecValue();
					}
				}
			}
		}

		StringBuffer sb = new StringBuffer();
		// 创建 sku html表
		sb.append("<table border=\"0\" style=\"padding:10px;\" name=\"skuDisplayTable\" class=\"table_a\"><tbody>");

		for (int i = 0; i < tableHeight; i++) {
			sb.append("<tr>");
			for (int j = 0; j < tableWidth; j++) {
				sb.append("<td>");
				if (data[i][j] != null) {
					sb.append(data[i][j]);
				}
				sb.append("</td>");

			}
			sb.append("</tr>");
		}

		sb.append("</tbody></table>");

		return sb.toString();
	}

	/**
	 * 
	 * @param response
	 * @param qo
	 * @param ptdzId
	 * @param mode
	 *            （！）1为只读模式，用于商品view； （！）没有或者0为编辑模式；
	 * @param tableSeq
	 *            对照表的序号 DealerProductMapping.java的sequence
	 * @param productId
	 * @return
	 */
	@RequestMapping("/get_dealer_product_table")
	@ResponseBody
	public String getDealerProductTable(HttpServletResponse response, SkuProductQO qo, @RequestParam int ptdzId, @RequestParam int mode,
			@RequestParam int tableSeq, @RequestParam String productId) {
		boolean readOnly = false;
		if (mode == 1) {
			readOnly = true;
		}

		int idLength = 0;
		List<Project> projList = projectService.queryList(new ProjectQO());
		// 没有项目
		if (projList == null || projList.size() == 0) {
			return "err#1";
		}

		Set<DealerProductMapping> dealerProductMappingList = productService.findDealerProductMappingByProductIdAndSequence(productId, tableSeq);
		List<DealerProductMapping> dpmList = new ArrayList<DealerProductMapping>();
		for (DealerProductMapping dealerProductMapping : dealerProductMappingList) {
			if (dealerProductMapping.getSequence() == tableSeq) {
				dpmList.add(dealerProductMapping);
			}
		}
		Project dpProj = productService.findProjectByProductIdAndSequence(productId, tableSeq);
		// 获得sku商品
		List<SkuProduct> skuProdList = skuProductService.queryList(qo);
		// 没有sku
		if (skuProdList.size() == 0) {
			return "err#0";
		}

		int tableHeight = skuProdList.size() + 1;
		int tableWidth = 0;

		List<SpecDetail> skuDetailLong = new ArrayList<SpecDetail>();
		// 查询对应sku详情信息
		for (SkuProduct skuProduct : skuProdList) {
			SpecDetailQO specDetailQO = new SpecDetailQO();
			specDetailQO.setSkuProductId(skuProduct.getId());
			List<SpecDetail> skuDetail = specDetailService.queryList(specDetailQO);
			skuProduct.setSpecDetails(skuDetail);
			for (SpecDetail specDetail : skuDetail) {
				boolean have = false;
				for (SpecDetail skuSpecDetail : skuDetailLong) {
					if (skuSpecDetail.getSpecification().getId().equals(specDetail.getSpecification().getId())) {
						have = true;
						break;
					}
				}
				if (!have) {
					skuDetailLong.add(specDetail);
				}
			}
		}
		StringBuffer sb = new StringBuffer();

		// 项目下拉框
		idLength = skuProdList.size();
		sb.append("<td rowspan=\"");
		sb.append(skuProdList.size());
		sb.append("\">");

		if (readOnly) {
			sb.append(dpProj.getName());
		} else {
			sb.append("<select onchange=\"projectOnChange(this)\" startId=\"" + ptdzId + "\" endId=\"" + (idLength + ptdzId) + "\" name=\"\" id=\"select\"> ");
			for (Project p : projList) {
				sb.append("<option  value=\"");
				sb.append(p.getId());
				sb.append("\"");
				if (dpProj != null && dpProj.getId().equals(p.getId())) {
					sb.append(" selected=\"selected\" ");
				}
				sb.append(">");
				sb.append(p.getName());
				sb.append("</option>");

			}
			for (int i = ptdzId; i < ptdzId + idLength; i++) {
				sb.append("<input id=\"projectId" + i + "\" type=\"hidden\" name=\"dealerProductMappingList[" + i + "].projectId\" value=\"");
				if (dpProj != null) {
					sb.append(dpProj.getId());
				} else {
					sb.append(projList.get(0).getId());
				}
				sb.append("\"/>");
				sb.append("<input type=\"hidden\" name=\"dealerProductMappingList[" + i + "].sequence\" value=\"" + tableSeq + "\"/>");
			}
			sb.append("</select>");
		}
		sb.append("</td>");
		String projectSelectHtml = sb.toString();

		sb.setLength(0);

		sb.append("<td rowspan=\"");
		sb.append(skuProdList.size());
		sb.append("\"><input name=\"\" type=\"button\"   onclick=\"deletePtdzTable(");
		sb.append(ptdzId);
		sb.append(",");
		sb.append(idLength + ptdzId);
		sb.append(")\" value=\"删除对照表\"/></td>");

		String deleteDpTableBtnHtml = sb.toString();
		sb.setLength(0);

		tableWidth = skuDetailLong.size() + 1;
		String[][] data = new String[tableHeight][tableWidth];

		for (int i = 0; i < skuDetailLong.size(); i++) {
			data[0][i] = skuDetailLong.get(i).getSpecification().getSpecName();
		}

		// 设置sku编码
		data[0][tableWidth - 1] = "sku编码";
		for (int i = 0; i < skuProdList.size(); i++) {

			sb.append(skuProdList.get(i).getId()).append("<input type=\"hidden\" name=\"dealerProductMappingList[").append(ptdzId + i)
					.append("].skuProductId\" id=\"ptdzId").append(ptdzId + i).append("\" value=\"").append(skuProdList.get(i).getId()).append("\"/>");
			data[i + 1][tableWidth - 1] = sb.toString();
			sb.setLength(0);

		}

		for (int i = 0; i < skuProdList.size(); i++) {
			List<SpecDetail> specDetailList = skuProdList.get(i).getSpecDetails();
			for (SpecDetail specDetail : specDetailList) {
				for (int j = 0; j < tableWidth; j++) {
					String dataStr = data[0][j];
					if (dataStr != null && dataStr.equals(specDetail.getSpecification().getSpecName())) {
						data[i + 1][j] = specDetail.getSpecValue().getSpecValue();
					}
				}
			}
		}

		// 创建 sku html表
		sb.setLength(0);
		sb.append("<table border=\"0\" id=\"PtdzTable").append(ptdzId).append("\" saved=0 tableSeq=\"").append(tableSeq).append("\" class=\"table_a\">");

		for (int i = 0; i < tableHeight; i++) {
			sb.append("<tr>");
			if (i == 0) {
				sb.append("<td>项目</td>");
			}
			if (i == 1) {
				sb.append(projectSelectHtml);
			}

			for (int j = 0; j < tableWidth; j++) {
				sb.append("<td>");
				if (data[i][j] != null) {
					sb.append(data[i][j]);
				}
				sb.append("</td>");

			}

			if (i == 0) {
				sb.append("<td>平台编码</td>");
			} else {
				sb.append("<td>");

				SkuProduct skuProduct = skuProdList.get(i - 1);
				if (readOnly) {
					for (DealerProductMapping dpm : dpmList) {
						if (dpm.getSkuProduct().getId().equals(skuProduct.getId())) {
							sb.append(dpm.getDealerProductCode());
						}
					}
				} else {
					sb.append("<input name=\"dealerProductMappingList[" + (ptdzId + i - 1)
							+ "].dealerProductCode\" type=\"text\" onchange=\"removeAllEmpty(this)\" maxlength=\"64\" ");
					for (DealerProductMapping dpm : dpmList) {
						if (dpm.getSkuProduct().getId().equals(skuProduct.getId())) {
							sb.append(" value=\"" + dpm.getDealerProductCode() + "\" ");
						}
					}

					sb.append(" >");
				}
				//
				sb.append("<input type=\"hidden\" name=\"dealerProductMappingList[" + (ptdzId + i - 1)
						+ "].dealerProductMappingId\" id=\"dealerProductMappingId" + (ptdzId + i - 1) + "\"  ");
				for (DealerProductMapping dpm : dpmList) {
					if (dpm.getSkuProduct().getId().equals(skuProduct.getId())) {
						sb.append(" value=\"" + dpm.getId() + "\" ");
					}
				}
				sb.append(" />");

				sb.append("</td>");
			}

			if (!readOnly) {
				if (i == 0) {
					sb.append("<td>操作</td>");
				}
				if (i == 1) {
					sb.append(deleteDpTableBtnHtml);
				}
			}
			sb.append("</tr>");
		}

		sb.append("</tbody></table>");

		return sb.toString();
	}

	/**
	 * 保存sku编码
	 * 
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping("/create_sku")
	@ResponseBody
	public String createSku(HttpServletResponse response, ModifyProductSkuCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		//
		Map<String, String> retMap = new HashMap<String, String>();
		String[] specIdArr = command.getSpecificationIdList().split(",");

		// 检测规格状态
		for (int i = 0; i < specIdArr.length; i++) {
			String specificationId = specIdArr[i];
			if (!specificationService.checkSpecificationStatus(specificationId)) {
				return JsonResultUtil.errorResult("添加失败，商品规格异常", null);
			}
		}

		SkuProductQO qo = new SkuProductQO();
		qo.setProductId(command.getProductId());
		List<SkuProduct> skuProdList = skuProductService.queryList(qo);
		try {
			// 添加sku
			if (skuProdList.size() == 0) {
				skuProductService.createSkuProduct(command);
			} else {
				List<String> skuList = new ArrayList<String>();
				for (SkuProduct skuProduct : skuProdList) {
					skuList.add(skuProduct.getId());
				}

				// 修改sku
				command.setSkuList(skuList);

				skuProductService.modifySkuProduct(command);
			}
			retMap.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("result", "error#0");
		}

		return JsonUtil.parseObject(retMap, false);

	}

	/**
	 * 修改商品描述
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update_product_describe")
	@ResponseBody
	public String updateProductDescribe(ModifyProductDescribeCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		productService.updateProductDescribe(command);
		return JsonResultUtil.successResult(null, null);

	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update_product_images")
	@ResponseBody
	public String updateProductImages(UploadProductImageCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		productImageService.uploadProductImage(command);

		return JsonResultUtil.successResult(null, null);

	}

	/**
	 * 修改商品状态 启用禁用
	 * 
	 * @return
	 */
	@RequestMapping("/change_status")
	@ResponseBody
	public String changeStatus(ModifyProductStatusCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		String msg;
		if (command.getUsing() == true) {
			msg = "商品启用成功";
		} else {
			msg = "商品禁用成功";

		}

		productService.updateProductStatus(command);
		return DwzJsonResultUtil.createJsonString("200", msg, null, "product_list");
	}

	@RequestMapping("/view")
	public String viewProduct(Model model, String id) {
		Product product = productService.get(id);
		String productDescribe = product.getProductDescribe();
		if (StringUtils.isNotBlank(productDescribe)) {
			productDescribe = productDescribe.replace("&lt;", "<");
			productDescribe = productDescribe.replace("&gt;", ">");
			productDescribe = productDescribe.replace("<p>", "");
			productDescribe = productDescribe.replace("</p>", "<br><br>");
			product.setProductDescribe(productDescribe);
		}

		model.addAttribute("product", product);

		Set<Integer> seqList = productService.findSequenceByProductId(id);
		model.addAttribute("seqList", seqList);

		ProductImageQO qo = new ProductImageQO();
		qo.setProductId(id);
		List<ProductImage> imgList = productImageService.queryList(qo);
		for (ProductImage i : imgList) {
			model.addAttribute("imgId" + i.getImageType(), i.getId());
			model.addAttribute("imgSrc" + i.getImageType(), i.getUrl());
		}

		return "/product/product_view.html";

	}

	@RequestMapping("/update_dealer_product_mapping")
	@ResponseBody
	public String updateDealerProductMapping(ModifyDealerProductMappingCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		// 判断完全没填对照信息
		List<DealerProductMappingDTO> dealerMappingList = command.getDealerProductMappingList();
		if (dealerMappingList == null) {
			return JsonResultUtil.errorResult("无平台商品对照信息，保存失败", null);
		}

		// < 判断未填表
		int seq = dealerMappingList.get(0).getSequence();
		int firstSeq = dealerMappingList.get(0).getSequence();
		int count = 0;
		for (DealerProductMappingDTO dealerProductMappingDTO : dealerMappingList) {
			if (seq != dealerProductMappingDTO.getSequence()) {
				if (count == 0) {
					return JsonResultUtil.errorResult("其中一个对照表未填写任何信息，保存失败", null);
				}
				seq = dealerProductMappingDTO.getSequence();
			}
			if (StringUtils.isNotBlank(dealerProductMappingDTO.getDealerProductCode())) {
				count++;
			}

		}
		if (firstSeq == seq && count == 0) {
			return JsonResultUtil.errorResult("其中一个对照表未填写任何信息，保存失败", null);
		}
		// >
		// 判断项目重复
		for (int i = 0; i < dealerMappingList.size(); i++) {
			for (int j = 0; j < dealerMappingList.size(); j++) {
				if (i != j && dealerMappingList.get(i).getProjectId().equals(dealerMappingList.get(j).getProjectId())
						&& dealerMappingList.get(i).getSkuProductId().equals(dealerMappingList.get(j).getSkuProductId())) {
					return JsonResultUtil.errorResult("项目重复，保存失败", null);
				}
			}
		}

		List<DealerProductMapping> dpmList = dealerProductMappingService.createDealerProductMapping(command);
		JsonResultUtil jru = new JsonResultUtil();
		for (int i = 0; i < dpmList.size(); i++) {
			DealerProductMapping d = dpmList.get(i);
			if (d.getId() != null) {
				jru.addAttr("dealerProductMappingList[" + i + "].dealerProductMappingId", d.getId());
			}
		}
		jru.setResultSuccess();
		return jru.outputJsonString();

	}

	@RequestMapping("/delete_dealer_product_mapping")
	@ResponseBody
	public String deleteDealerProductMapping(DeleteDealerProductMappingCommand command) {
		boolean allIdIsEmpty = true;
		List<String> idList = command.getDealerProductMappingId();
		for (String id : idList) {
			if (StringUtils.isNotEmpty(id)) {
				allIdIsEmpty = false;
				break;
			}
		}
		if (allIdIsEmpty) {// 该对照表未保存
			return JsonResultUtil.errorResult("err#0", null);
		}
		CommandUtil.SetOperator(getAuthUser(), command);
		try {
			dealerProductMappingService.deleteDealerProductMapping(command);
			return JsonResultUtil.successResult(null, null);

		} catch (Exception e) {
			// TODO: handle exception
			return JsonResultUtil.errorResult(null, null);
		}

	}

	@RequestMapping("/sku_product_lookup")
	public String skuProductLookup(@ModelAttribute DwzPaginQo dwzPaginQo, Model model) {
		SkuProductQO qo = new SkuProductQO();

		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = skuProductService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "/product/sku_product_lookup.html";

	}

	@RequestMapping("/add_purchase_order_detail")
	public String addPurchaseOrderDetail(String[] skuIdList, Model model) {

		List<SkuProductDTO> skuProductDtoList = purchaseOrderService.queryPurchaseOrderDetailList(skuIdList);
		model.addAttribute("orderDetailList", skuProductDtoList);
		return "/purchaseOrder/purchaseOrderDetailLine.html";

	}

	@RequestMapping("/query_dealer_product_mapping_seq_list")
	@ResponseBody
	public String queryDealerProductMappingSeqList(Model model, String productId) {
		Set<Integer> seqList = productService.findSequenceByProductId(productId);
		return JSON.toJSONString(seqList);

	}

}
