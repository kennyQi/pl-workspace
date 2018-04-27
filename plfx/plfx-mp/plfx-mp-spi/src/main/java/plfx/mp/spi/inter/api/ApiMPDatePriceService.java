package plfx.mp.spi.inter.api;

import plfx.api.client.api.v1.mp.request.qo.MPDatePriceQO;
import plfx.api.client.api.v1.mp.response.MPQueryDatePriceResponse;

public interface ApiMPDatePriceService {
	
	/**
	 * 查询价格日历
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryDatePriceResponse apiQueryDateSalePrices(MPDatePriceQO qo);
	
}
