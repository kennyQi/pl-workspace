package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;
import hg.pojo.qo.PurchaseOrderDetailQo;
import jxc.app.dao.warehousing.PurchaseOrderDetailDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.purchaseorder.PurchaseOrder;
import jxc.domain.model.purchaseorder.PurchaseOrderDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderDetailService extends BaseServiceImpl<PurchaseOrderDetail, PurchaseOrderDetailQo, PurchaseOrderDetailDao> {
	@Autowired
	private PurchaseOrderDetailDao purchaseOrderDetailDao;

	@Override
	protected PurchaseOrderDetailDao getDao() {
		return purchaseOrderDetailDao;
	}

	@Autowired
	private JxcLogger logger;

	public void createDetail(List<PurchaseOrderDetailDTO> detailList, PurchaseOrder purchaseOrder) {

		for (PurchaseOrderDetailDTO purchaseOrderDetailDTO : detailList) {
			PurchaseOrderDetail orderDetail = new PurchaseOrderDetail();
			orderDetail.create(purchaseOrderDetailDTO, purchaseOrder);
			save(orderDetail);
		}

	}

	public void modifyDetail(List<PurchaseOrderDetailDTO> detailList, PurchaseOrder purchaseOrder) {
		deteleDetail(purchaseOrder.getId());
		createDetail(detailList, purchaseOrder);
	}

	public void deteleDetail(String purchaseOrderId) {

	}

	@Transactional(readOnly = true)
	public List<PurchaseOrderDetailDTO> queryDtoList(PurchaseOrderDetailQo detailQo) {
		List<PurchaseOrderDetail> purchaseOrderDetailList = queryList(detailQo);
		List<PurchaseOrderDetailDTO> dtoList = new ArrayList<PurchaseOrderDetailDTO>();
		StringBuffer sb = new StringBuffer();
		for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {

			dtoList.add(purchaseOrderDetail2Dto(purchaseOrderDetail, sb));
		}

		return dtoList;
	}

	public PurchaseOrderDetailDTO purchaseOrderDetail2Dto(PurchaseOrderDetail orderDetail, StringBuffer sb) {
		PurchaseOrderDetailDTO dto = new PurchaseOrderDetailDTO();
		dto.setId(orderDetail.getId());

		dto.setProductCode(orderDetail.getSkuProduct().getProduct().getProductCode());
		dto.setProductName(orderDetail.getSkuProduct().getProduct().getProductName());
		dto.setSkuCode(orderDetail.getSkuProduct().getId());

		List<SpecDetail> specDetails = orderDetail.getSkuProduct().getSpecDetails();
		for (SpecDetail specDetail : specDetails) {
			sb.append(specDetail.getSpecValue().getSpecValue());
			sb.append(" ");
		}

		dto.setSpecificationsName(sb.toString());
		dto.setUnit(orderDetail.getSkuProduct().getProduct().getUnit().getUnitName());

		dto.setTotalPriceInclTax(orderDetail.getTotalPriceInclTax());
		dto.setTotalPriceExclTax(orderDetail.getTotalPriceExclTax());
		dto.setUnitPriceInclTax(orderDetail.getUnitPriceInclTax());
		dto.setUnitPriceExclTax(orderDetail.getUnitPriceExclTax());
		dto.setLogisticCost(orderDetail.getLogisticCost());
		dto.setQuantity(orderDetail.getQuantity());
		dto.setTaxRate(orderDetail.getTaxRate());

		sb.setLength(0);
		return dto;

	}

}
