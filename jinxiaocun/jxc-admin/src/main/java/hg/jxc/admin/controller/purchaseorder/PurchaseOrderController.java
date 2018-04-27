package hg.jxc.admin.controller.purchaseorder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.supplier.SupplierService;
import jxc.app.service.system.AttentionService;
import jxc.app.service.system.PaymentMethodService;
import jxc.app.service.system.ProjectService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.service.warehousing.PurchaseOrderDetailService;
import jxc.app.service.warehousing.PurchaseOrderService;
import jxc.domain.model.Constants;
import jxc.domain.model.purchaseorder.PurchaseOrder;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.Attention;
import jxc.domain.model.system.PaymentMethod;
import jxc.domain.model.system.Project;
import jxc.domain.model.warehouse.Warehouse;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.Tools;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CheckPurchaseOrderCommand;
import hg.pojo.command.CreatePurchaseOrderCommand;
import hg.pojo.command.ModifyPurchaseOrderCommand;
import hg.pojo.command.RemovePurchaseOrderCommand;
import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.exception.PurchaseOrderException;
import hg.pojo.qo.AttentionQo;
import hg.pojo.qo.PaymentMethodQo;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.PurchaseOrderDetailQo;
import hg.pojo.qo.PurchaseOrderQo;
import hg.pojo.qo.SupplierQO;
import hg.pojo.qo.WarehouseQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/purchase_order")
public class PurchaseOrderController extends BaseController {
	@Autowired
	PurchaseOrderService purchaseOrderService;
	@Autowired
	PurchaseOrderDetailService purchaseOrderDetailService;
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
	public String queryPurchaseOrderList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, PurchaseOrderQo qo) {
		qo.setCreateDateStart(Tools.strToDate(qo.getCreateDateStartStr()));
		qo.setCreateDateEnd(Tools.strToDate(qo.getCreateDateEndStr()));

		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = purchaseOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);

		SupplierQO supplierQO = new SupplierQO();
		List<Supplier> supplierList = supplierService.queryList(supplierQO);
		model.addAttribute("supplierList", supplierList);

		WarehouseQO warehouseQo = new WarehouseQO();
		List<Warehouse> warehouseList = warehouseService.queryList(warehouseQo);
		model.addAttribute("warehouseList", warehouseList);

		ProjectQO projectQo = new ProjectQO();
		List<Project> projectList = projectService.queryList(projectQo);
		model.addAttribute("projectList", projectList);

		JsonResultUtil.AddFormData(model, qo);

		return "purchaseOrder/purchaseOrderList.html";
	}

	@RequestMapping("/to_add")
	public String toAddPurchaseOrder(Model model, HttpServletRequest request) {
		addAttr2Model(model);
		return "purchaseOrder/purchaseOrderAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createPurchaseOrder(Model model, HttpServletRequest request, CreatePurchaseOrderCommand command) {

		if (command.getDetailList() == null || command.getDetailList().size() == 0) {
			return JsonResultUtil.dwzErrorMsg("未选择sku商品");
		}

		purchaseOrderService.createPurchaseOrder(command);

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editPurchaseOrder(Model model, HttpServletRequest request, PurchaseOrderQo qo) {
		PurchaseOrder purchaseOrder = purchaseOrderService.queryUnique4Edit(qo);
		model.addAttribute("purchaseOrder", purchaseOrder);
		Tools.modelAddFlag("edit", model);

		addAttr2Model(model);

		PurchaseOrderDetailQo detailQo = new PurchaseOrderDetailQo();
		detailQo.setPurchaseOrderId(qo.getId());
		List<PurchaseOrderDetailDTO> purchaseOrderDetailList = purchaseOrderDetailService.queryDtoList(detailQo);
		model.addAttribute("orderDetailList", purchaseOrderDetailList);
		return "purchaseOrder/purchaseOrderAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updatePurchaseOrder(Model model, HttpServletRequest request, ModifyPurchaseOrderCommand command) {

		try {
			purchaseOrderService.updatePurchaseOrder(command);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removePurchaseOrder(RemovePurchaseOrderCommand command) {
		try {
			purchaseOrderService.removePurchaseOrder(command);
		} catch (PurchaseOrderException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);
	}

	@RequestMapping("/to_check")
	public String toCheckPurchaseOrder(PurchaseOrderQo qo, Model model) {
		PurchaseOrder purchaseOrder = purchaseOrderService.queryUnique(qo);
		PurchaseOrderDetailQo detailQo = new PurchaseOrderDetailQo();
		detailQo.setPurchaseOrderId(qo.getId());
		List<PurchaseOrderDetailDTO> purchaseOrderDetailDtoList = purchaseOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("detailList", purchaseOrderDetailDtoList);

		Tools.modelAddFlag("check", model);
		return "purchaseOrder/purchaseOrderCheck.html";
	}

	@RequestMapping("/to_uncheck")
	public String toUncheckPurchaseOrder(PurchaseOrderQo qo, Model model) {
		PurchaseOrder purchaseOrder = purchaseOrderService.queryUnique(qo);
		PurchaseOrderDetailQo detailQo = new PurchaseOrderDetailQo();
		detailQo.setPurchaseOrderId(qo.getId());
		List<PurchaseOrderDetailDTO> purchaseOrderDetailDtoList = purchaseOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("detailList", purchaseOrderDetailDtoList);

		Tools.modelAddFlag("check", model);
		return "purchaseOrder/purchaseOrderUncheck.html";
	}

	@RequestMapping("/check")
	@ResponseBody
	public String checkPurchaseOrder(CheckPurchaseOrderCommand command) {
		// 防止注入参数
		command.setStatus(Constants.PURCHASE_ORDER_CHECK_PASS);
		try {
			purchaseOrderService.checkPass(command);
		} catch (PurchaseOrderException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsg("审核成功", navTabRel);
	}

	@RequestMapping("/uncheck")
	@ResponseBody
	public String uncheckPurchaseOrder(CheckPurchaseOrderCommand command) {
		// 防止注入参数
		command.setStatus(Constants.PURCHASE_ORDER_WAREHOUSING_CANCEL);
		try {
			purchaseOrderService.checkCancel(command);
		} catch (PurchaseOrderException e) {
			e.printStackTrace();
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsg("审核成功", navTabRel);
	}

	@RequestMapping("/to_view")
	public String toViewPurchaseOrder(PurchaseOrderQo qo, Model model) {
		PurchaseOrder purchaseOrder = purchaseOrderService.queryUnique(qo);
		PurchaseOrderDetailQo detailQo = new PurchaseOrderDetailQo();
		detailQo.setPurchaseOrderId(qo.getId());
		List<PurchaseOrderDetailDTO> purchaseOrderDetailDtoList = purchaseOrderDetailService.queryDtoList(detailQo);

		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("detailList", purchaseOrderDetailDtoList);

		return "purchaseOrder/purchaseOrderView.html";
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
