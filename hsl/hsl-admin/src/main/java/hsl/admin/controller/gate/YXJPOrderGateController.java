package hsl.admin.controller.gate;

import com.alibaba.fastjson.JSONObject;
import hg.log.util.HgLogger;
import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.pojo.dto.yxjp.notify.ApplyRefundTicketNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketRefuseNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketSuccessNotify;
import hsl.pojo.dto.yxjp.notify.RefundTicketNotify;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 易行机票订单通知控制器
 *
 * @author zhurz
 * @since 1.7
 */
@Controller
@RequestMapping(value = "/gate/yxjp")
public class YXJPOrderGateController {

	@Autowired
	private YXJPOrderLocalService orderLocalService;

	private ExecutorService exec = Executors.newFixedThreadPool(10);

	/**
	 * 分销通知入口
	 *
	 * @param msg 请求参数（JSON）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/notify")
	public String fxNotify(@RequestParam("msg") final String msg) {

		final JSONObject json = (JSONObject) JSONObject.parse(msg);
		final String msgMd5 = DigestUtils.md5Hex(msg);
		HgLogger.getInstance().info("zhurz", String.format("接收分销通知->msgMd5:%s->%s", msgMd5, msg));

		exec.execute(new Runnable() {
			@Override
			public void run() {
				HgLogger.getInstance().info("zhurz", "开始处理分销通知->" + msgMd5);
				String type = json.getString("type");
				// 1：出票成功通知
				if ("1".equals(type)) {
					orderLocalService.processOutTicketSuccessNotify(new OutTicketSuccessNotify(json));
				}
				// 4：退废票结果通知
				else if ("4".equals(type)) {
					orderLocalService.processRefundTicketNotify(new RefundTicketNotify(json));
				}
				// 5：分销申请退废票通知
				else if ("5".equals(type)) {
					orderLocalService.processApplyRefundTicketNotify(new ApplyRefundTicketNotify(json));
				}
				// 6：拒绝出票通知
				else if ("6".equals(type)) {
					orderLocalService.processOutTicketRefuseNotify(new OutTicketRefuseNotify(json));
				}
			}
		});

		return "success";
	}

	/**
	 * 检查订单并做相应处理
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check-order")
	public String checkOrder() {
		exec.execute(new Runnable() {
			@Override
			public void run() {
				// 检查退废票成功且不需要退款的订单改变状态为退废成功已退款
				try {
					orderLocalService.checkRefundSuccessRebackWaitPassengerToRebackSuccess(100);
				} catch (Exception e) {
					HgLogger.getInstance().error(YXJPOrderGateController.class, "zhurz", "检查退废票成功且不需要退款的订单改变状态为退废成功已退款出现错误", e);
				}
				// 取消支付超时订单
				try {
					orderLocalService.cancelTimeoutOrder(100, 20);
				} catch (Exception e) {
					HgLogger.getInstance().error(YXJPOrderGateController.class, "zhurz", "取消支付超时订单出现错误", e);
				}
			}
		});
		return "success";
	}

}
