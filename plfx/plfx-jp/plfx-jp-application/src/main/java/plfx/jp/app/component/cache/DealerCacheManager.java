package plfx.jp.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.pojo.system.DealerConstants;

/**
 * @类功能说明：经销商缓存管理
 * @类修改者：
 * @修改日期：2015-7-14下午3:53:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14下午3:53:10
 */
@Component
public class DealerCacheManager {

	@Resource
	private Redisson redisson;

	public static final String PLFX_DEALER_MAP = "PLFX:DEALER_MAP";

	/**
	 * @方法功能说明：刷新经销商缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:43
	 * @修改内容：
	 * @参数：@param dealers
	 * @return:void
	 * @throws
	 */
	public void reflushDealerMap(List<Dealer> dealers) {
		RMap<String, Dealer> map = redisson.getMap(PLFX_DEALER_MAP);
		Map<String, Dealer> dealerMap = new HashMap<String, Dealer>();
		for (Dealer dealer : dealers)
			if (StringUtils.equals(DealerConstants.USE, dealer.getStatus()))
				dealerMap.put(dealer.getCode(), dealer);
		map.clear();
		map.putAll(dealerMap);
	}
	
	/**
	 * @方法功能说明：获取经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:52
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:Dealer
	 * @throws
	 */
	public Dealer getDealer(String code) {
		if (code == null)
			return null;
		RMap<String, Dealer> map = redisson.getMap(PLFX_DEALER_MAP);
		return map.get(code);
	}
	
}
