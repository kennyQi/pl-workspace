package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.pojo.dto.warehousing.WarehousingNoticeDetailDTO;
import hg.pojo.qo.WarehousingNoticeDetailQo;
import jxc.app.dao.warehousing.WarehousingNoticeDetailDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.warehouseing.notice.WarehousingNotice;
import jxc.domain.model.warehouseing.notice.WarehousingNoticeDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehousingNoticeDetailService extends BaseServiceImpl<WarehousingNoticeDetail, WarehousingNoticeDetailQo, WarehousingNoticeDetailDao> {
	@Autowired
	private WarehousingNoticeDetailDao warehousingNoticeDetailDao;

	@Override
	protected WarehousingNoticeDetailDao getDao() {
		return warehousingNoticeDetailDao;
	}

	@Autowired
	private JxcLogger logger;

	public void createDetail(List<WarehousingNoticeDetailDTO> detailList, WarehousingNotice warehousingNotice) {

		for (WarehousingNoticeDetailDTO warehousingNoticeDetailDTO : detailList) {
			WarehousingNoticeDetail warehousingNoticeDetail = new WarehousingNoticeDetail();
			warehousingNoticeDetail.create(warehousingNoticeDetailDTO, warehousingNotice);
			save(warehousingNoticeDetail);
		}

	}

	public void modifyDetail(List<WarehousingNoticeDetailDTO> detailList, WarehousingNotice warehousingNotice) {
		deteleDetail(warehousingNotice.getId());
		createDetail(detailList, warehousingNotice);
	}

	public void deteleDetail(String warehousingNoticeId) {

	}

	@Transactional(readOnly = true)
	public List<WarehousingNoticeDetailDTO> queryDtoList(WarehousingNoticeDetailQo detailQo) {
		List<WarehousingNoticeDetail> warehousingNoticeDetailList = queryList(detailQo);
		List<WarehousingNoticeDetailDTO> dtoList = new ArrayList<WarehousingNoticeDetailDTO>();
		StringBuffer sb = new StringBuffer();
		for (WarehousingNoticeDetail warehousingNoticeDetail : warehousingNoticeDetailList) {

			dtoList.add(warehousingNoticeDetail2Dto(warehousingNoticeDetail, sb));
		}

		return dtoList;
	}

	public WarehousingNoticeDetailDTO warehousingNoticeDetail2Dto(WarehousingNoticeDetail orderDetail, StringBuffer sb) {
		WarehousingNoticeDetailDTO dto = new WarehousingNoticeDetailDTO();
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
