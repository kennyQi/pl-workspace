package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hg.common.component.BaseModel;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.pojo.command.CheckPurchaseOrderCommand;
import hg.pojo.command.CreatePurchaseOrderCommand;
import hg.pojo.command.ModifyPurchaseOrderCommand;
import hg.pojo.command.RemovePurchaseOrderCommand;
import hg.pojo.dto.product.SkuProductDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.exception.PurchaseOrderException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.PurchaseOrderQo;
import hg.pojo.qo.SkuProductQO;
import jxc.app.dao.warehousing.PurchaseOrderDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.service.system.CheckRecordService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.Constants;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.purchaseorder.PurchaseOrder;
import jxc.domain.model.purchaseorder.PurchaseOrderDetail;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
public class PurchaseOrderService extends BaseServiceImpl<PurchaseOrder, PurchaseOrderQo, PurchaseOrderDao> {
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private SkuProductService skuProductService;

	@Autowired
	private PurchaseOrderDetailService purchaseOrderDetailService;

	@Autowired
	CheckRecordService checkRecordService;

	@Override
	protected PurchaseOrderDao getDao() {
		return purchaseOrderDao;
	}

	@Autowired
	private JxcLogger logger;

	@Override
	public Pagination queryPagination(Pagination pagination) {

		pagination = super.queryPagination(pagination);

		List<PurchaseOrder> list = (List<PurchaseOrder>) pagination.getList();
		for (PurchaseOrder purchaseOrder : list) {
			Hibernate.initialize(purchaseOrder.getSupplier());
			Hibernate.initialize(purchaseOrder.getWarehouse());
			Hibernate.initialize(purchaseOrder.getProject());
		}
		return pagination;
	}

	@Override
	public PurchaseOrder queryUnique(PurchaseOrderQo qo) {
		PurchaseOrder ret = super.queryUnique(qo);
		Hibernate.initialize(ret.getProject());
		Hibernate.initialize(ret.getSupplier());
		Hibernate.initialize(ret.getWarehouse());
		Hibernate.initialize(ret.getPaymentMethod());
		Hibernate.initialize(ret.getAttention());
		return ret;
	}

	public PurchaseOrder queryUnique4Edit(PurchaseOrderQo qo) {
		PurchaseOrder purchaseOrder = super.queryUnique(qo);

		getProxyId(purchaseOrder.getAttention());
		getProxyId(purchaseOrder.getWarehouse());
		getProxyId(purchaseOrder.getProject());
		getProxyId(purchaseOrder.getPaymentMethod());
		getProxyId(purchaseOrder.getAttention());
		getProxyId(purchaseOrder.getSupplier());

		return purchaseOrder;

	}

	public void getProxyId(BaseModel obj) {
		HibernateProxy proxy = (HibernateProxy) obj;
		obj.setId((String) proxy.getHibernateLazyInitializer().getIdentifier());
	}

	@Transactional(rollbackFor = Exception.class)
	public void createPurchaseOrder(CreatePurchaseOrderCommand command) {

		PurchaseOrder purchaseOrder = new PurchaseOrder();

		purchaseOrder.createPurchaseOrder(command);
		save(purchaseOrder);
		purchaseOrderDetailService.createDetail(command.getDetailList(), purchaseOrder);

		// logger.debug(this.getClass(), "wkq", "添加采购单:" + command.get,
		// command.getOperatorName(), command.getOperatorType(),
		// command.getOperatorAccount(), "");

	}

	public List<SkuProductDTO> queryPurchaseOrderDetailList(String[] skuIdArr) {
		List<SkuProductDTO> skuProductDtoList = new ArrayList<SkuProductDTO>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < skuIdArr.length; i++) {
			SkuProductQO qo = new SkuProductQO();
			qo.setId(skuIdArr[i]);
			SkuProduct skuProd = skuProductService.queryUnique(qo);

			List<SpecDetail> specDetails = skuProd.getSpecDetails();
			for (SpecDetail specDetail : specDetails) {
				sb.append(specDetail.getSpecValue().getSpecValue());
				sb.append(" ");
			}
			SkuProductDTO skuProductDTO = new SkuProductDTO();
			skuProductDTO.setSkuCode(skuProd.getId());
			skuProductDTO.setProductCode(skuProd.getProduct().getProductCode());
			skuProductDTO.setProductName(skuProd.getProduct().getProductName());
			skuProductDTO.setSpecificationsName(sb.toString());
			sb.setLength(0);
			skuProductDTO.setUnit(skuProd.getProduct().getUnit().getUnitName());
			skuProductDtoList.add(skuProductDTO);

			// purchaseOrderDetailList.add(purchaseOrderDetail);
		}

		return skuProductDtoList;

	}

	public void removePurchaseOrder(RemovePurchaseOrderCommand command) throws PurchaseOrderException {
		List<String> idList = command.getIdList();
		for (String id : idList) {
			PurchaseOrder purchaseOrder = get(id);
			if (purchaseOrder.getStatus() != (int) Constants.PURCHASE_ORDER_NO_CHECK) {
				throw new PurchaseOrderException(null, "输入异常");
			}

			purchaseOrder.setStatusRemoved(true);
			update(purchaseOrder);
		}

	}

	public void updatePurchaseOrder(ModifyPurchaseOrderCommand command) {
		PurchaseOrderQo qo = new PurchaseOrderQo();
		qo.setId(command.getId());
		PurchaseOrder purchaseOrder = queryUnique(qo);
		purchaseOrder.modify(command);
		update(purchaseOrder);
		purchaseOrderDetailService.modifyDetail(command.getDetailList(), purchaseOrder);

	}

	@Transactional(rollbackFor = Exception.class)
	public void checkPass(CheckPurchaseOrderCommand command) throws PurchaseOrderException {
		command.setBelongTo(command.getPurchaseOrderId());
		PurchaseOrderQo qo = new PurchaseOrderQo();
		qo.setId(command.getPurchaseOrderId());
		PurchaseOrder purchaseOrder = queryUnique(qo);
		if (purchaseOrder.getStatus() != (int) Constants.PURCHASE_ORDER_NO_CHECK) {
			throw new PurchaseOrderException(null, "输入异常");
		}
		purchaseOrder.check(command);

		update(purchaseOrder);
		checkRecordService.createCheckRecord(command);
	}

	@Transactional(rollbackFor = Exception.class)
	public void checkCancel(CheckPurchaseOrderCommand command) throws PurchaseOrderException {
		command.setBelongTo(command.getPurchaseOrderId());
		PurchaseOrderQo qo = new PurchaseOrderQo();
		qo.setId(command.getPurchaseOrderId());
		PurchaseOrder purchaseOrder = queryUnique(qo);
		if (purchaseOrder.getStatus() != (int) Constants.PURCHASE_ORDER_CHECK_PASS) {
			throw new PurchaseOrderException(null, "输入异常");
		}
		purchaseOrder.check(command);

		update(purchaseOrder);
		checkRecordService.createCheckRecord(command);

	}

}
