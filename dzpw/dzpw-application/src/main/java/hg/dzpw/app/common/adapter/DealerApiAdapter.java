package hg.dzpw.app.common.adapter;

import hg.common.page.Pagination;
import hg.common.util.SpringContextUtil;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.common.BaseClientPageQO;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：经销商接口适配器
 * @类修改者：
 * @修改日期：2015-4-24下午4:51:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-24下午4:51:21
 */
public class DealerApiAdapter {
	
	private static DealerCacheManager dealerCacheManager;
	private static ThreadLocal<String> requestDealerId = new ThreadLocal<String>();

	public static PriceCalendarDealerApiAdapter priceCalendar = new PriceCalendarDealerApiAdapter();
	public static ScenicSpotDealerApiAdapter scenicSpot = new ScenicSpotDealerApiAdapter();
	public static TicketOrderDealerApiAdapter ticketOrder = new TicketOrderDealerApiAdapter();
	public static TicketPolicyDealerApiAdapter ticketPolicy = new TicketPolicyDealerApiAdapter();
	public static GroupTicketDealerApiAdapter groupTicket = new GroupTicketDealerApiAdapter();
	
	private synchronized static void checkDealerCacheManager() {
		if (dealerCacheManager == null)
			dealerCacheManager = SpringContextUtil.getApplicationContext()
					.getBean(DealerCacheManager.class);
	}
	
	
	/**
	 * @方法功能说明：根据KEY从缓存中获取经销商ID并设置到当前线程里
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27下午4:20:12
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getDealerId(String dealerKey) {
		checkDealerCacheManager();
		// 查询对应的经销商ID
		String dealerId = dealerCacheManager.getDealerId(dealerKey);
		if (StringUtils.isNotBlank(dealerId))
			requestDealerId.set(dealerId);
		return dealerId;
	}
	
	/**
	 * @方法功能说明：获取当前线程请求接口的经销商ID
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27下午4:21:48
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getRequestDealerId() {
		return requestDealerId.get();
	}

	/**
	 * @方法功能说明：转换分页查询条件
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:52:30
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public static Pagination convertPaginationCondition(BaseClientPageQO QO) {
		Pagination pagination = new Pagination();
		if (QO instanceof TicketPolicyQO)
			pagination.setCondition(ticketPolicy.convertQo((TicketPolicyQO) QO));
		if (QO instanceof ScenicSpotQO)
			pagination.setCondition(scenicSpot.convertQo((ScenicSpotQO) QO));
		if (QO instanceof TicketOrderQO)
			pagination.setCondition(ticketOrder.convertQo((TicketOrderQO) QO));
		pagination.setPageNo(QO.getPageNo());
		pagination.setPageSize(QO.getPageSize());
		return pagination;
	}
}
