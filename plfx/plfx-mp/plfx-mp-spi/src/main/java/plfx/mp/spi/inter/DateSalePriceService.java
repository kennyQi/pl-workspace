package plfx.mp.spi.inter;

import java.util.Date;
import java.util.List;
import plfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;
import plfx.mp.spi.exception.SlfxMpException;

public interface DateSalePriceService {

	/**
	 * 根据供应商政策快照ID获取单个价格日历，
	 * 如果缓存没有，则去接口拉取
	 * 
	 * @param supplierPolicyId						供应商政策ID
	 * @param dealerId								经销商ID
	 * @param travelDate							旅游日期
	 * @return
	 * @throws SlfxMpException
	 */
	public DateSalePriceDTO getDateSalePrice(String supplierPolicyId, String dealerId, Date travelDate) throws SlfxMpException;
	
	/**
	 * 根据供应商政策快照ID获取价格日历，
	 * 如果缓存没有，则去接口拉取
	 * 
	 * @param supplierPolicyId						供应商政策ID
	 * @param dealerId								经销商ID
	 * @return
	 */
	public List<DateSalePriceDTO> getDateSalePrice(String supplierPolicyId, String dealerId);
	
}
