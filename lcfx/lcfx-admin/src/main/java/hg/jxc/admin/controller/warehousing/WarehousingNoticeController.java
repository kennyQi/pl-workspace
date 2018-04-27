package hg.jxc.admin.controller.warehousing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.supplier.SupplierService;
import jxc.app.service.system.AttentionService;
import jxc.app.service.system.PaymentMethodService;
import jxc.app.service.system.ProjectService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.service.warehousing.WarehousingNoticeDetailService;
import jxc.app.service.warehousing.WarehousingNoticeService;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.Attention;
import jxc.domain.model.system.PaymentMethod;
import jxc.domain.model.system.Project;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouseing.notice.WarehousingNotice;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.warehousingnotice.CreateWarehousingNoticeCommand;
import hg.pojo.command.warehousingnotice.ModifyWarehousingNoticeCommand;
import hg.pojo.command.warehousingnotice.RemoveWarehousingNoticeCommand;
import hg.pojo.dto.warehousing.WarehousingNoticeDetailDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.AttentionQo;
import hg.pojo.qo.PaymentMethodQo;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.WarehousingNoticeDetailQo;
import hg.pojo.qo.WarehousingNoticeQo;
import hg.pojo.qo.SupplierQO;
import hg.pojo.qo.WarehouseQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/warehousing_notice")
public class WarehousingNoticeController extends BaseController {
	@Autowired
	WarehousingNoticeService warehousingNoticeService;
	@Autowired
	WarehousingNoticeDetailService warehousingNoticeDetailService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	PaymentMethodService paymentMethodService;
	@Autowired
	ProjectService projectService;
	@Autowired
	AttentionService attentionService;
	final static String navTabRel = "purchase_order_list";

	@RequestMapping("/list")
	public String queryWarehousingNoticeList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, WarehousingNoticeQo qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = warehousingNoticeService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);
		return "warehousingNotice/warehousingNoticeList.html";
	}

	@RequestMapping("/to_add")
	public String toAddWarehousingNotice(Model model, HttpServletRequest request) {
		addAttr2Model(model);
		return "warehousingNotice/warehousingNoticeAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createWarehousingNotice(Model model, HttpServletRequest request, CreateWarehousingNoticeCommand command) {

		try {
			warehousingNoticeService.createWarehousingNotice(command);
		} catch (ProductException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editWarehousingNotice(Model model, HttpServletRequest request, WarehousingNoticeQo qo) {
		WarehousingNotice warehousingNotice = warehousingNoticeService.queryUnique4Edit(qo);
		model.addAttribute("warehousingNotice", warehousingNotice);
		Tools.modelAddFlag("edit", model);

		addAttr2Model(model);

		WarehousingNoticeDetailQo detailQo = new WarehousingNoticeDetailQo();
		detailQo.setWarehousingNoticeId(qo.getId());
		List<WarehousingNoticeDetailDTO> warehousingNoticeDetailList = warehousingNoticeDetailService.queryDtoList(detailQo);
		model.addAttribute("orderDetailList", warehousingNoticeDetailList);
		return "warehousingNotice/warehousingNoticeAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateWarehousingNotice(Model model, HttpServletRequest request, ModifyWarehousingNoticeCommand command) {

		try {
			warehousingNoticeService.updateWarehousingNotice(command);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeWarehousingNotice(RemoveWarehousingNoticeCommand command) {
		warehousingNoticeService.removeWarehousingNotice(command);

		return JsonResultUtil.dwzSuccessMsg("删除成功", navTabRel);
	}

	@RequestMapping("/to_view")
	public String toViewWarehousingNotice(WarehousingNoticeQo qo, Model model) {
		WarehousingNotice warehousingNotice = warehousingNoticeService.queryUnique(qo);
		WarehousingNoticeDetailQo detailQo = new WarehousingNoticeDetailQo();
		detailQo.setWarehousingNoticeId(qo.getId());
		List<WarehousingNoticeDetailDTO> warehousingNoticeDetailDtoList = warehousingNoticeDetailService.queryDtoList(detailQo);

		model.addAttribute("warehousingNotice", warehousingNotice);
		model.addAttribute("detailList", warehousingNoticeDetailDtoList);

		return "warehousingNotice/warehousingNoticeView.html";
	}

	private void addAttr2Model(Model model) {
		SupplierQO supplierQO = new SupplierQO();
		List<Supplier> supplierList = supplierService.queryList(supplierQO);
		model.addAttribute("supplierList", supplierList);

		WarehouseQO warehouseQo = new WarehouseQO();
		List<Warehouse> warehouseList = warehouseService.queryList(warehouseQo);
		model.addAttribute("warehouseList", warehouseList);

		PaymentMethodQo paymentMethodQo = new PaymentMethodQo();
		List<PaymentMethod> paymentMethodList = paymentMethodService.queryList(paymentMethodQo);
		model.addAttribute("paymentMethodList", paymentMethodList);

		ProjectQO projectQo = new ProjectQO();
		List<Project> projectList = projectService.queryList(projectQo);
		model.addAttribute("projectList", projectList);

		AttentionQo attentionQo = new AttentionQo();
		List<Attention> attentionList = attentionService.queryList(attentionQo);
		model.addAttribute("attentionList", attentionList);
	}
}
