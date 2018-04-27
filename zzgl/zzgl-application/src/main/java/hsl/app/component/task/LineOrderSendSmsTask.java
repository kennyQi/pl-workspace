package hsl.app.component.task;

import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.domain.model.xl.order.LineOrder;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.qo.line.HslLineOrderQO;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 线路订单发送短信任务
 * 检查10分钟后线路订单的状态是否支付，
 * 如果仍未支付则发送提醒短信到联系人。
 * </pre>
 *
 * @author zhurz
 */
@Component
public class LineOrderSendSmsTask implements Runnable {

	/**
	 * 线路订单ID MAP<线路订单ID, 创建时间+10分钟>
	 */
	private Map<String, Date> lineOrderMap = new HashMap<String, Date>();

	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private LineOrderLocalService lineOrderLocalService;

	@Override
	public void run() {
		// 循环处理创建时间十分钟后的订单
		while (true) {

			CHECK:
			try {
				Map<String, Date> lineOrderMap = new HashMap<String, Date>();
				synchronized (this) {
					if (this.lineOrderMap.size() == 0) {
						break CHECK;
					}
					lineOrderMap.putAll(this.lineOrderMap);
				}
				Date now = new Date();
				Set<String> sendSmsLineOrderSet = new HashSet<String>();

				for (Map.Entry<String, Date> entry : lineOrderMap.entrySet()) {
					if (now.compareTo(entry.getValue()) >= 0) {
						sendSmsLineOrderSet.add(entry.getKey());
						HslLineOrderQO qo = new HslLineOrderQO();
						qo.setId(entry.getKey());
						LineOrder lineOrder = lineOrderLocalService.queryUnique(qo);

						if (lineOrder == null) continue;

						try {
							// 检查线路订单十分钟后的支付状态是否依然是未支付且下单成功未定位，并发送短信提示
							if (lineOrder.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)
									&& lineOrder.travelerHasOrderStatus(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE)) {

								String toMobile = lineOrder.getLinkInfo().getLinkMobile();
								String dealerOrderNo = lineOrder.getBaseInfo().getDealerOrderNo();
								String smsSign = SysProperties.getInstance().get("sms_sign", "中智票量");

								HgLogger.getInstance().info("zhurz", String.format("检查到线路订单[%s]需要发送提醒短信", dealerOrderNo));

								smsUtils.sendSms(toMobile, String.format("【%s】亲，您的订单[%s]已提交成功！请尽快完成支付，以免耽误行程。票量，开启旅行新时代！客服：010—65912283",smsSign , dealerOrderNo));

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				// 移除处理过的
				synchronized (this) {
					for (String lineOrderId : sendSmsLineOrderSet) {
						this.lineOrderMap.remove(lineOrderId);
					}
				}
			} catch (Exception e) {
				// 移除所有
				synchronized (this) {
					HgLogger.getInstance().info("zhurz", "循环检查线路订单遇到异常，清空所有待检查订单ID。");
					this.lineOrderMap.clear();
				}
			}

			// 10秒后进行下一次检查
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@PostConstruct
	public void checkLineOrder() {
		new Thread(this).start();
	}

	public void putCheckLineOrder(String lineOrderId, Date createDate) {
		synchronized (this) {
			lineOrderMap.put(lineOrderId, DateUtils.addMinutes(createDate, 10));
		}
	}

	public void removeCheckLineOrder(String lineOrderId) {
		synchronized (this) {
			lineOrderMap.remove(lineOrderId);
		}
	}

}
