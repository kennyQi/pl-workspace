package plfx.gjjp.app.component.listener;

import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import plfx.gjjp.app.common.message.PlatformAmqpMessage;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.domain.model.dealer.Dealer;

/**
 * @类功能说明：平台通知监听器
 * @类修改者：
 * @修改日期：2015-7-16下午5:19:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午5:19:23
 */
public class PlatformNotifyMessageListener {

	@Autowired
	private GJJPOrderLocalService orderLocalService;

	@Autowired
	private DealerCacheManager dealerCacheManager;

	public void listen(Object message) {

		if (!(message instanceof PlatformAmqpMessage))
			return;

		PlatformAmqpMessage msg = (PlatformAmqpMessage) message;

		Dealer dealer = dealerCacheManager.getDealer(msg.getToDealerCode());

		if (dealer == null || !StringUtils.startsWith(dealer.getNotifyUrl(), "http://"))
			return;

		
		// 通知经销商
		String content = msg.getContent().paramsString(dealer.getSecretKey());
		HttpResponse response = HttpUtil.reqForPost(dealer.getNotifyUrl(), content, 60000);
		
		String log = String.format("通知经销商[%s][%s]参数[%s]\n响应\n", dealer.getName(), dealer.getNotifyUrl(), content, JSON.toJSONString(response, true));
		HgLogger.getInstance().info("zhurz", log);
	}
}
