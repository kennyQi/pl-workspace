package hg.dzpw.app.component.event;

import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.common.util.SpringContextUtil;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.app.component.manager.DealerRefundNotifyCacheManager;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.log.util.HgLogger;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @类功能说明：对经销商事件发布工具
 * @类修改者：
 * @修改日期：2015-4-29下午3:43:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-29下午3:43:45
 */
public class DealerApiEventPublisher {

	private static final Logger logger = LoggerFactory.getLogger(DealerApiEventPublisher.class);
	private static final ExecutorService exec = Executors.newFixedThreadPool(10);

	/**
	 * @方法功能说明：发布事件
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:33:34
	 * @修改内容：
	 * @参数：@param event
	 * @return:void
	 * @throws
	 */
	public static void publish(PublishEventRequest event) {
		logger.info("发布事件{}:{}", event.getEvent(), event.getMessage());
		exec.submit(new Runnable() {
			private PublishEventRequest event;

			@Override
			public void run() {
				DealerCacheManager dealerCacheManager = SpringContextUtil
						.getApplicationContext().getBean(DealerCacheManager.class);

				Map<String, String> map = dealerCacheManager.getDealerPushlishUrlMap();
				
				if (map == null || map.size() == 0)
					return;
				
				// 推送给指定经销商
				if (StringUtils.isNotBlank(event.getDealerKey())){
					try {//map.get(event.getDealerKey())
						HttpResponse response = HttpUtil.reqForPost(map.get(event.getDealerKey()), event.paramsString(), 15000);
						//对退款通知单独处理
						if (event.getEvent().equals(PublishEventRequest.EVENT_TICKET_REFUND_SUCCESS)) {
							DealerRefundNotifyCacheManager cacheManager=SpringContextUtil.getApplicationContext().getBean(DealerRefundNotifyCacheManager.class);
							if (response.getResult().equalsIgnoreCase("success")) {
								//如果是通知经销商退款成功，删除缓存记录
								HgLogger.getInstance().debug("guotx", "成功通知经销商退款成功");
								cacheManager.updateOrDel(event.getDealerKey(), event.getMessage(), true);
							}else {
								cacheManager.updateOrDel(event.getDealerKey(), event.getMessage(), false);
							}
						}
					} catch (Exception e) {
						if (event.getEvent().equals(PublishEventRequest.EVENT_TICKET_REFUND_SUCCESS)){
							//如果是通知退款成功发生异常，修改缓存
							DealerRefundNotifyCacheManager cacheManager=SpringContextUtil.getApplicationContext().getBean(DealerRefundNotifyCacheManager.class);
							cacheManager.updateOrDel(event.getDealerKey(), event.getMessage(), false);
						}
						e.printStackTrace();
					}
				}else{
					// 推送给所有
					for (String url : map.values())
						try {
							HttpUtil.reqForPost(url, event.paramsString(), 15000);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			}

			public Runnable e(PublishEventRequest event) {
				this.event = event;
				return this;
			}
			
		}.e(event));
	}

}
