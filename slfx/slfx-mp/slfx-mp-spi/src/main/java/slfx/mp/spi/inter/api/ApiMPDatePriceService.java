package slfx.mp.spi.inter.api;

import slfx.api.v1.request.qo.mp.MPDatePriceQO;
import slfx.api.v1.response.mp.MPQueryDatePriceResponse;

public interface ApiMPDatePriceService {
	
	/**
	 * 查询价格日历
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryDatePriceResponse apiQueryDateSalePrices(MPDatePriceQO qo);
	
}
