package hg.jxc.admin.controller.warehousing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.supplier.SupplierService;
import jxc.app.service.system.AttentionService;
import jxc.app.service.system.PaymentMethodService;
import jxc.app.service.system.ProjectService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.service.warehousing.WarehousingOrderDetailService;
import jxc.app.service.warehousing.WarehousingOrderService;
import jxc.domain.model.Constants;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.Attention;
import jxc.domain.model.system.PaymentMethod;
import jxc.domain.model.system.Project;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouseing.order.WarehousingOrder;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.warehousingorder.CreateWarehousingOrderCommand;
import hg.pojo.command.warehousingorder.ModifyWarehousingOrderCommand;
import hg.pojo.command.warehousingorder.RemoveWarehousingOrderCommand;
import hg.pojo.dto.warehousing.WarehousingOrderDetailDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.AttentionQo;
import hg.pojo.qo.PaymentMethodQo;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.WarehousingOrderDetailQo;
import hg.pojo.qo.WarehousingOrderQo;
import hg.pojo.qo.SupplierQO;
import hg.pojo.qo.WarehouseQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/warehousing_order")
public class WarehousingOrderController extends BaseController {
	@Autowired
	WarehousingOrderService warehousingOrderService;
	@Autowired
	WarehousingOrderDetailService warehousingOrderDetailService;
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
	final static String navTabRel = "warehousing_order_list";

	@RequestMapping("/list")
	public String queryWarehousingOrderList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, WarehousingOrderQo qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = warehousingOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return "warehousingOrder/warehousingOrderList.html";
	}

	@RequestMapping("/to_add")
	public String toAddWarehousingOrder(Model model, HttpServletRequest request) {
		addAttr2Model(model);
		return "warehousingOrder/warehousingOrderAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createWarehousingOrder(Model model, HttpServletRequest request, CreateWarehousingOrderCommand command) {

		try {
			warehousingOrderService.createWarehousingOrder(command);
		} catch (ProductException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editWarehousingOrder(Model model, HttpServletRequest request, WarehousingOrderQo qo) {
		WarehousingOrder warehousingOrder = warehousingOrderService.queryUnique4Edit(qo);
		model.addAttribute("warehousingOrder", warehousingOrder);
		Tools.modelAddFlag("edit", model);

		addAttr2Model(model);

		WarehousingOrderDetailQo detailQo = new WarehousingOrderDetailQo();
		detailQo.setWarehousingOrderId(qo.getId());
		List<WarehousingOrderDetailDTO> warehousingOrderDetailList = warehousingOrderDetailService.queryDtoList(detailQo);
		model.addAttribute("orderDetailList", warehousingOrderDetailList);
		return "warehousingOrder/warehousingOrderAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateWarehousingOrder(Model model, HttpServletRequest request, ModifyWarehousingOrderCommand command) {

		try {
			warehousingOrderService.updateWarehousingOrder(command);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeWarehousingOrder(RemoveWarehousingOrderCommand command) {
		warehousingOrderService.removeWarehousingOrder(command);

		return JsonResultUtil.dwzSuccessMsg("删除成功", navTabRel);
	}

	@RequestMapping("/to_check")
	public String toCheckWarehousingOrder(WarehousingOrderQo qo, Model model) {
		WarehousingOrder warehousingOrder = warehousingOrderService.queryUnique(qo);
		WarehousingOrderDetailQo detailQo = new WarehousingOrderDetailQo();
		detailQo.setWarehousingOrderId(qo.getId());
		List<WarehousingOrderDetailDTO> warehousingOrderDetailDtoList = warehousingOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("warehousingOrder", warehousingOrder);
		model.addAttribute("detailList", warehousingOrderDetailDtoList);

		Tools.modelAddFlag("check", model);
		return "warehousingOrder/warehousingOrderCheck.html";
	}

	@RequestMapping("/to_uncheck")
	public String toUncheckWarehousingOrder(WarehousingOrderQo qo, Model model) {
		WarehousingOrder warehousingOrder = warehousingOrderService.queryUnique(qo);
		WarehousingOrderDetailQo detailQo = new WarehousingOrderDetailQo();
		detailQo.setWarehousingOrderId(qo.getId());
		List<WarehousingOrderDetailDTO> warehousingOrderDetailDtoList = warehousingOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("warehousingOrder", warehousingOrder);
		model.addAttribute("detailList", warehousingOrderDetailDtoList);

		Tools.modelAddFlag("check", model);
		return "warehousingOrder/warehousingOrderUncheck.html";
	}

	@RequestMapping("/to_view")
	public String toViewWarehousingOrder(WarehousingOrderQo qo, Model model) {
		WarehousingOrder warehousingOrder = warehousingOrderService.queryUnique(qo);
		WarehousingOrderDetailQo detailQo = new WarehousingOrderDetailQo();
		detailQo.setWarehousingOrderId(qo.getId());
		List<WarehousingOrderDetailDTO> warehousingOrderDetailDtoList = warehousingOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("warehousingOrder", warehousingOrder);
		model.addAttribute("detailList", warehousingOrderDetailDtoList);

		return "warehousingOrder/warehousingOrderView.html";
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
