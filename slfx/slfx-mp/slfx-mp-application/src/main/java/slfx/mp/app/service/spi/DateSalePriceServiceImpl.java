package slfx.mp.app.service.spi;

import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.mp.app.common.util.EntityConvertUtils;
import slfx.mp.app.service.local.DateSalePriceLocalService;
import slfx.mp.domain.model.platformpolicy.DateSalePrice;
import slfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.spi.inter.DateSalePriceService;

@Service
public class DateSalePriceServiceImpl implements DateSalePriceService {

	@Autowired
	private DateSalePriceLocalService dateSalePriceLocalService;
	@Override
	public DateSalePriceDTO getDateSalePrice(String supplierPolicyId, String dealerId, Date travelDate) throws SlfxMpException {
		HgLogger.getInstance().info("wuyg", "根据供应商政策快照ID获取单个价格日历， 如果缓存没有，则去接口拉取");
		DateSalePrice dateSalePrice = dateSalePriceLocalService.getDateSalePrice(supplierPolicyId, dealerId, travelDate);
		return EntityConvertUtils.convertEntityToDto(dateSalePrice, DateSalePriceDTO.class);
	}

	@Override
	public List<DateSalePriceDTO> getDateSalePrice(String supplierPolicyId, String dealerId) {
		HgLogger.getInstance().info("wuyg", "根据供应商政策快照ID获取价格日历， 如果缓存没有，则去接口拉取");
		List<DateSalePrice> dateSalePrices = dateSalePriceLocalService.getDateSalePrice(supplierPolicyId, dealerId);
		return EntityConvertUtils.convertEntityToDtoList(dateSalePrices, DateSalePriceDTO.class);
	}

}
