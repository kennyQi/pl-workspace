package plfx.yxgjclient.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.yxgjclient.component.cache.YXGJServiceCacheManager;
import plfx.yxgjclient.pojo.request.QueryAirRulesRQ;
import plfx.yxgjclient.pojo.request.QueryFlightRQ;
import plfx.yxgjclient.pojo.request.QueryMoreCabinsRQ;
import plfx.yxgjclient.pojo.request.QueryPoliciesRQ;
import plfx.yxgjclient.pojo.response.QueryAirRulesRS;
import plfx.yxgjclient.pojo.response.QueryFlightRS;
import plfx.yxgjclient.pojo.response.QueryMoreCabinsRS;
import plfx.yxgjclient.pojo.response.QueryPoliciesRS;
/**
 * 易行国际机票接口
 * @author guotx
 * 2015-07-02
 */
@Service
public class YIGJService extends YIGJBaseService {
	
	@Autowired
	private YXGJServiceCacheManager yxgjServiceCacheManager;
	
	/**
	 * 出票规则
	 * @param rq
	 * @return
	 */
	public QueryAirRulesRS queryAirRules(QueryAirRulesRQ rq, boolean useCache) {
		QueryAirRulesRS rs = null;
		if (useCache)
			rs = yxgjServiceCacheManager.getResultCache(rq.getQueryAirRulesParams(), QueryAirRulesRS.class);
		if (rs == null)
			rs = queryAirRules(rq);
		if (useCache)
			yxgjServiceCacheManager.cacheResult(rq.getQueryAirRulesParams(), rs);
		return rs;
	}
	
	/**
	 * 航班查询
	 * @param rq
	 * @return
	 */
	public QueryFlightRS queryFlight(QueryFlightRQ rq, boolean useCache) {
		QueryFlightRS rs = null;
		if (useCache)
			rs = yxgjServiceCacheManager.getResultCache(rq.getQueryFlightParams(), QueryFlightRS.class);
		if (rs == null)
			rs = queryFlight(rq);
		if (useCache)
			yxgjServiceCacheManager.cacheResult(rq.getQueryFlightParams(), rs);
		return rs;
	}
	
	/**
	 * 更多舱位
	 * @param rq
	 * @return
	 */
	public QueryMoreCabinsRS queryMoreCabins(QueryMoreCabinsRQ rq, boolean useCache) {
		QueryMoreCabinsRS rs = null;
		if (useCache)
			rs = yxgjServiceCacheManager.getResultCache(rq.getQueryMoreCabinsParams(), QueryMoreCabinsRS.class);
		if (rs == null)
			rs = queryMoreCabins(rq);
		if (useCache)
			yxgjServiceCacheManager.cacheResult(rq.getQueryMoreCabinsParams(), rs);
		return rs;
	}
	
	/**
	 * 获取政策
	 * @param rq
	 * @return
	 */
	public QueryPoliciesRS queryPolicies(QueryPoliciesRQ rq, boolean useCache) {
		QueryPoliciesRS rs = null;
		if (useCache)
			rs = yxgjServiceCacheManager.getResultCache(rq.getQueryPoliciesParams(), QueryPoliciesRS.class);
		if (rs == null)
			rs = queryPolicies(rq);
		if (useCache)
			yxgjServiceCacheManager.cacheResult(rq.getQueryPoliciesParams(), rs);
		return rs;
	}
}
