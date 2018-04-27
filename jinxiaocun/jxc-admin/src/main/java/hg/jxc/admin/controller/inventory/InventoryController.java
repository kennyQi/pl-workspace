package hg.jxc.admin.controller.inventory;

import jxc.app.service.inventory.InventoryService;

import hg.common.page.Pagination;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.qo.InventoryQo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/inventory")
public class InventoryController extends BaseController {

	@Autowired
	private InventoryService service;

	public final static String LIST_VIEW = "/inventory/inventoryList.html";
	public final static String REL = "inventory_list";

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer numPerPage,
			@ModelAttribute InventoryQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNum);
		pagination.setPageSize(numPerPage);
		pagination.setCondition(qo);
		model.addAttribute("pagination", service.queryPagination(pagination));
		model.addAttribute("qo", qo);
		return LIST_VIEW;
	}

}
