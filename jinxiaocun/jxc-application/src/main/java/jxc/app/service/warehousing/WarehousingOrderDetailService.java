package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.pojo.dto.warehousing.WarehousingOrderDetailDTO;
import hg.pojo.qo.WarehousingOrderDetailQo;
import jxc.app.dao.warehousing.WarehousingOrderDetailDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.warehouseing.order.WarehousingOrder;
import jxc.domain.model.warehouseing.order.WarehousingOrderDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehousingOrderDetailService extends BaseServiceImpl<WarehousingOrderDetail, WarehousingOrderDetailQo, WarehousingOrderDetailDao> {
	@Autowired
	private WarehousingOrderDetailDao warehousingOrderDetailDao;

	@Override
	protected WarehousingOrderDetailDao getDao() {
		return warehousingOrderDetailDao;
	}

	@Autowired
	private JxcLogger logger;

	public void createDetail(List<WarehousingOrderDetailDTO> detailList, WarehousingOrder warehousingOrder) {

		for (WarehousingOrderDetailDTO warehousingOrderDetailDTO : detailList) {
			WarehousingOrderDetail orderDetail = new WarehousingOrderDetail();
			orderDetail.create(warehousingOrderDetailDTO, warehousingOrder);
			save(orderDetail);
		}

	}

	public void modifyDetail(List<WarehousingOrderDetailDTO> detailList, WarehousingOrder warehousingOrder) {
		deteleDetail(warehousingOrder.getId());
		createDetail(detailList, warehousingOrder);
	}

	public void deteleDetail(String warehousingOrderId) {

	}

	@Transactional(readOnly = true)
	public List<WarehousingOrderDetailDTO> queryDtoList(WarehousingOrderDetailQo detailQo) {
		List<WarehousingOrderDetail> warehousingOrderDetailList = queryList(detailQo);
		List<WarehousingOrderDetailDTO> dtoList = new ArrayList<WarehousingOrderDetailDTO>();
		StringBuffer sb = new StringBuffer();
		for (WarehousingOrderDetail warehousingOrderDetail : warehousingOrderDetailList) {

			dtoList.add(warehousingOrderDetail2Dto(warehousingOrderDetail, sb));
		}

		return dtoList;
	}

	public WarehousingOrderDetailDTO warehousingOrderDetail2Dto(WarehousingOrderDetail orderDetail, StringBuffer sb) {
		WarehousingOrderDetailDTO dto = new WarehousingOrderDetailDTO();
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

//		dto.setTotalPriceInclTax(orderDetail.getTotalPriceInclTax());
//		dto.setTotalPriceExclTax(orderDetail.getTotalPriceExclTax());
//		dto.setUnitPriceInclTax(orderDetail.getUnitPriceInclTax());
//		dto.setUnitPriceExclTax(orderDetail.getUnitPriceExclTax());
//		dto.setLogisticCost(orderDetail.getLogisticCost());
//		dto.setQuantity(orderDetail.getQuantity());
//		dto.setTaxRate(orderDetail.getTaxRate());

		sb.setLength(0);
		return dto;

	}

}
