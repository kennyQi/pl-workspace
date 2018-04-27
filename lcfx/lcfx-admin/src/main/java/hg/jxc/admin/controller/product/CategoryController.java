package hg.jxc.admin.controller.product;

import hg.common.model.DwzTreeNode;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.HtmlSelectTreeUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.JxcDwzTreeUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateCategoryCommand;
import hg.pojo.command.DeleteCategoryCommand;
import hg.pojo.command.ModifyCategoryCommand;
import hg.pojo.exception.JxcException;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.CategoryQO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.product.ProductCategoryService;
import jxc.domain.model.product.ProductCategory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
	@Autowired
	ProductCategoryService categoryService;

	/**
	 * 分页查询分类列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String queryCategoryList(Model model, HttpServletRequest request) {
		List<DwzTreeNode> nodeList = new ArrayList<DwzTreeNode>();
		List<ProductCategory> categoryList = categoryService.queryList(new CategoryQO());
		for (ProductCategory category : categoryList) {
			DwzTreeNode node = new DwzTreeNode();
			node.setId(category.getId());
			node.setDisplayName(category.getName());
			node.setRel("rel");
			if (category.getStatus() != null) {

				node.setChecked(category.getStatus().getUsing());
			}
			if (category.getParentCategory() != null && category.getParentCategory().getId() != null) {
				node.setParentId(category.getParentCategory().getId());
			}
			nodeList.add(node);
		}

		String dwzTreeHTML = JxcDwzTreeUtil.createDwzTreeListHtml(nodeList, null);
		model.addAttribute("dwzTreeHTML", dwzTreeHTML);
		return "/product/category/category_list.html";
	}

	/**
	 * 跳转到增加分类页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/to_add")
	public String addCategory(Model model, @RequestParam(required = false) String id) {

		// 传递上级分类
		ProductCategory category = new ProductCategory();
		if (StringUtils.isNotBlank(id)) {
			category = categoryService.get(id);
		}
		model.addAttribute("category", category);
		return "product/category/category_add.html";
	}

	/**
	 * 创建分类
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createCategory(CreateCategoryCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			categoryService.createCategory(command);
		} catch (ProductException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "category_list");
	}

	/**
	 * 删除分类
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteCategory(DeleteCategoryCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			categoryService.deleteCategory(command);
		} catch (JxcException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功", null, "category_list");

	}

	/**
	 * 跳转分类编辑页面
	 * 
	 * @param qo
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String editCategory(CategoryQO qo, Model model) {

		ProductCategory category = categoryService.queryUnique4edit(qo);
		model.addAttribute("category", category);

		return "product/category/category_edit.html";
	}

	/**
	 * 更新分类
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateCategory(ModifyCategoryCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			categoryService.updateCategory(command);
		} catch (ProductException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "category_list");

	}

	/**
	 * 查找分类
	 * 
	 * @param model
	 * @param inputName
	 * @param mode
	 * @param findUsing
	 * @return
	 */
	@RequestMapping("/lookup")
	public String queryCategoryListForLookup(Model model, @RequestParam(required = true) String inputName, String mode,
			@RequestParam(required = false) Integer findUsing, @RequestParam(required = false) Integer onlyShowUsing) {
		if (findUsing == null) {
			findUsing = 0;
		}
		if (onlyShowUsing == null) {
			onlyShowUsing = 0;
		}
		List<ProductCategory> categoryList = categoryService.queryList(new CategoryQO());
		HtmlSelectTreeUtil htmlTree = new HtmlSelectTreeUtil(inputName, mode, findUsing);
		for (ProductCategory c : categoryList) {
			if (onlyShowUsing == 1) {
				if (c.getStatus().getUsing() == true) {
					if (c.getParentCategory() != null) {
						htmlTree.addList(c.getParentCategory().getId(), c.getId(), c.getName(), c.getStatus().getUsing());
					} else {
						htmlTree.addList(null, c.getId(), c.getName(), c.getStatus().getUsing());
					}
				}
			} else {
				if (c.getParentCategory() != null) {
					htmlTree.addList(c.getParentCategory().getId(), c.getId(), c.getName(), c.getStatus().getUsing());
				} else {
					htmlTree.addList(null, c.getId(), c.getName(), c.getStatus().getUsing());
				}
			}
		}
		model.addAttribute("categoryTreeHTML", htmlTree.getHTML());
		model.addAttribute("inputName", inputName);
		return "product/category/category_lookup.html";
	}

	/**
	 * 判断是不是叶子节点
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/check_final_category")
	@ResponseBody
	public String checkFinalCategory(@RequestParam String id) {

		if (categoryService.isEndCategory(id)) {
			return JsonResultUtil.successResult(null, null);
		}
		return JsonResultUtil.errorResult(null, null);

	}

}