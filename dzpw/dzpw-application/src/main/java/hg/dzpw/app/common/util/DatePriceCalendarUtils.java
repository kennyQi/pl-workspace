package hg.dzpw.app.common.util;

import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明：价格日历工具
 * @类修改者：
 * @修改日期：2015-4-24下午3:31:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-24下午3:31:24
 *
 */
public class DatePriceCalendarUtils {
	
	/**
	 * @方法功能说明：合并价格列表
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午3:20:32
	 * @修改内容：
	 * @参数：@param standardDatePrices			基准价价格日历
	 * @参数：@param dealerDatePrices				经销商价格日历
	 * @参数：@return
	 * @return:List<TicketPolicyDatePrice>
	 * @throws
	 */
	public static List<TicketPolicyDatePrice> merge(List<TicketPolicyDatePrice> standardDatePrices, List<TicketPolicyDatePrice> dealerDatePrices) {

		if (standardDatePrices == null)
			standardDatePrices = new ArrayList<TicketPolicyDatePrice>();
		if (dealerDatePrices == null)
			dealerDatePrices = new ArrayList<TicketPolicyDatePrice>();

		Map<String, TicketPolicyDatePrice> priceMap = new HashMap<String, TicketPolicyDatePrice>();
		
		for (TicketPolicyDatePrice standardPrice : standardDatePrices)
			priceMap.put(standardPrice.getDate(), standardPrice);
		for (TicketPolicyDatePrice dealerPrice : dealerDatePrices)
			priceMap.put(dealerPrice.getDate(), dealerPrice);
		
		List<String> dateList = new ArrayList<String>(priceMap.keySet());
		Collections.sort(dateList);

		List<TicketPolicyDatePrice> prices = new ArrayList<TicketPolicyDatePrice>();
		for (String date : dateList)
			prices.add(priceMap.get(date));

		return prices;
	}
	
	
}
