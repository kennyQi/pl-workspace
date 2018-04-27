package slfx.mp.app.service.local;

import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_ARGS_ERROR;
import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_SALE_POLICY_NONE;
import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.mp.app.component.manager.DateSalePriceCacheManager;
import slfx.mp.app.dao.DateSalePriceDAO;
import slfx.mp.app.dao.TCSupplierPolicySnapshotDAO;
import slfx.mp.app.pojo.qo.DateSalePriceQO;
import slfx.mp.domain.model.platformpolicy.DateSalePrice;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.tcclient.tc.dto.jd.PriceCalendarDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.ResultPriceCalendar;
import slfx.mp.tcclient.tc.service.TcClientService;

@Service("dateSalePriceLocalService_mp")
@Transactional
public class DateSalePriceLocalService extends BaseServiceImpl<DateSalePrice, DateSalePriceQO, DateSalePriceDAO> {
	
	@Autowired
	private DateSalePriceDAO dao;
	@Autowired
	private TCSupplierPolicySnapshotDAO tcSupplierPolicySnapshotDAO;

	@Autowired
	private TcClientService tcClientService;
	@Autowired
	private TCSupplierPolicySnapshotLocalService tcSupplierPolicySnapshotLocalService;
	@Autowired
	private SalePolicySnapshotLocalService salePolicySnapshotLocalService;
	
	@Autowired
	private DateSalePriceCacheManager dateSalePriceCacheManager;
	
	@Override
	protected DateSalePriceDAO getDao() {
		return dao;
	}
	
	public DateSalePrice getDateSalePrice(String supplierPolicyId, String dealerId, Date travelDate) throws SlfxMpException{
		HgLogger.getInstance().info("zhurz", "查询单个日期价格");
		if (travelDate == null) {
			HgLogger.getInstance().error("zhurz", "查询单个日期价格:缺少旅游日期");
			throw new SlfxMpException(EXCEPTION_CODE_ARGS_ERROR, "缺少旅游日期");
		}
		List<DateSalePrice> dateSalePrices = getDateSalePrice(supplierPolicyId, dealerId);
		if (dateSalePrices == null || dateSalePrices.size() == 0) {
			HgLogger.getInstance().error("zhurz", "查询单个日期价格:玩日期的价格政策不存在 ");
			throw new SlfxMpException(EXCEPTION_CODE_SALE_POLICY_NONE);
		}
		String travelDateStr = DateUtil.formatDateTime(travelDate, "yyyyMMdd");
		for(DateSalePrice dateSalePrice:dateSalePrices){
			String saleDateStr = DateUtil.formatDateTime(dateSalePrice.getSaleDate(), "yyyyMMdd");
			if(StringUtils.equals(travelDateStr, saleDateStr)){
				return dateSalePrice;
			}
		}
		HgLogger.getInstance().error("zhurz", "查询单个日期价格:玩日期的价格政策不存在 ");
		throw new SlfxMpException(EXCEPTION_CODE_SALE_POLICY_NONE);
	}

	/**
	 * @方法功能说明：转换接口对象到价格日历列表
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-3上午10:59:44
	 * @修改内容：
	 * @参数：@param calendar
	 * @参数：@param dealerId
	 * @参数：@param supplierPolicyId
	 * @参数：@param tcSupplierPolicySnapshot
	 * @参数：@return
	 * @return:List<DateSalePrice>
	 * @throws
	 */
	protected List<DateSalePrice> convertDateSalePrice(List<slfx.mp.tcclient.tc.domain.Calendar> calendar,
			String dealerId, String supplierPolicyId, TCSupplierPolicySnapshot tcSupplierPolicySnapshot) {

		List<DateSalePrice> list = new ArrayList<DateSalePrice>();

		if (calendar != null) {
			for (slfx.mp.tcclient.tc.domain.Calendar c : calendar) {
				DateSalePrice dateSalePrice = new DateSalePrice();
				dateSalePrice.setId(UUIDGenerator.getUUID());
				dateSalePrice.setDealerId(dealerId);
				dateSalePrice.setSalePrice(c.getTcPrice());
				dateSalePrice.setSale(true);
				dateSalePrice.setSaleDate(DateUtil.parseDateTime(c.getDate(), "yyyy-MM-dd"));
				dateSalePrice.setSupplierPolicyId(supplierPolicyId);
				dateSalePrice.setSupplierPlolicyName(tcSupplierPolicySnapshot.getName());
				dateSalePrice.setOriginalPrice(c.getTcPrice());
				dateSalePrice.setSupplierPolicySnapshot(tcSupplierPolicySnapshot);
				dateSalePrice.setStock(c.getStock() == null ? 0 : c.getStock());
				// 对应平台政策快照...
				dateSalePrice.setSalePolicySnapshot(null);
				list.add(dateSalePrice);
			}
		}

		return list;
	}
	
	/**
	 * 根据供应商政策快照ID获取价格日历，
	 * 如果缓存没有，则去接口拉取
	 * 
	 * @param supplierPolicyId						供应商政策ID
	 * @param dealerId								经销商ID
	 * @return
	 */
	public List<DateSalePrice> getDateSalePrice(String supplierPolicyId, String dealerId) {
		
		HgLogger.getInstance().info("zhurz", "根据供应商政策快照ID获取价格日历 ");
		
		List<DateSalePrice> dateSalePrices = dateSalePriceCacheManager.getDateSalePrices(supplierPolicyId, dealerId);
		
		if (dateSalePrices == null || dateSalePrices.size() == 0) {
			
			dateSalePrices = new ArrayList<DateSalePrice>();
			
			// 查询当前供应商政策快照
			TCSupplierPolicySnapshot tcSupplierPolicySnapshot = tcSupplierPolicySnapshotLocalService.getLast(supplierPolicyId);
			
			// 查询这个月和下个月的价格日历
			Calendar nowCalendar = Calendar.getInstance();
			Date now = nowCalendar.getTime();
			nowCalendar.add(Calendar.MONTH, 1);
			
			String startDate = DateUtil.formatDateTime(now, "yyyy-MM-dd");
			String startDate2 = DateUtil.formatDateTime(nowCalendar.getTime(), "yyyy-MM-dd");
			
			PriceCalendarDto priceCalendarDto = new PriceCalendarDto();
			priceCalendarDto.setPolicyId(Integer.valueOf(tcSupplierPolicySnapshot.getPolicyId()));
			priceCalendarDto.setStartDate(startDate);

			PriceCalendarDto priceCalendarDto2 = new PriceCalendarDto();
			priceCalendarDto2.setPolicyId(priceCalendarDto.getPolicyId());
			priceCalendarDto2.setStartDate(startDate2);
			
			// 接口调用
			Response<ResultPriceCalendar> response = tcClientService.getPriceCalendar(priceCalendarDto);
			Response<ResultPriceCalendar> response2 = tcClientService.getPriceCalendar(priceCalendarDto2);
			
			// 如果接口查询返回结果为失败，则返回空的价格日历
			if (!"0".equals(response.getHead().getRspType())){
				HgLogger.getInstance().error("zhurz", "根据供应商政策快照ID获取价格日历:接口查询失败, 返回空的价格日历 ");
				return new ArrayList<DateSalePrice>();
			}
			
			ResultPriceCalendar result = response.getResult();
			
			List<slfx.mp.tcclient.tc.domain.Calendar> calendar = result.getCalendar();
			
			dateSalePrices.addAll(convertDateSalePrice(calendar, dealerId, supplierPolicyId, tcSupplierPolicySnapshot));
			
			if("0".equals(response2.getHead().getRspType())){
				ResultPriceCalendar result2 = response2.getResult();
				List<slfx.mp.tcclient.tc.domain.Calendar> calendar2 = result2.getCalendar();
				dateSalePrices.addAll(convertDateSalePrice(calendar2, dealerId, supplierPolicyId, tcSupplierPolicySnapshot));
			}
			
			HgLogger.getInstance().info("zhurz", "根据供应商政策快照ID获取价格日历:刷新价格日历 ");
			dateSalePriceCacheManager.refresh(dateSalePrices, supplierPolicyId, dealerId);
		}
		
		return dateSalePrices;
	}
	
	/**
	 * 价格日历查询
	 * 
	 * @param qo
	 * @return
	 */
	public List<DateSalePrice> getDateSalePrice(DateSalePriceQO qo) {
		return getDateSalePrice(qo.getPolicyId(), qo.getDealerId());
	}

}
