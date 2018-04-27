package plfx.gjjp.app.component.listener;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;

import plfx.gjjp.app.common.message.YXGJJPAmqpMessage;
import plfx.gjjp.app.service.local.GJJPProcessNotifyLocalService;
import plfx.jp.common.ThreadTrackingTokenGenerator;

/**
 * @类功能说明：易行国际结果通知监听器
 * @类修改者：
 * @修改日期：2015-7-16下午4:56:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午4:56:47
 */
public class YXGJNotifyMessageListener {

	@Autowired
	private GJJPProcessNotifyLocalService processNotifyLocalService;

	public void listen(Object message) {
		//System.out.println("YXGJNotifyMessageListener--------------->>");
		//System.out.println(message.getClass());
		//System.out.println(JSON.toJSONString(message, true));
		if (!(message instanceof YXGJJPAmqpMessage))
			return;

		ThreadTrackingTokenGenerator.newget();
		
		YXGJJPAmqpMessage msg = (YXGJJPAmqpMessage) message;
		
		switch (msg.getType()) {
		// 1：支付成功通知
		case 1:
			processNotifyLocalService.processPaySuccessNotify(msg);
			break;
		// 2：取消成功通知
		case 2:
			processNotifyLocalService.processCancelOrderNotify(msg);
			break;
		// 3：出票成功通知
		case 3:
			processNotifyLocalService.processOutTicketSuccessNotify(msg);
			break;
		// 4：退废票通知
		case 4:
			processNotifyLocalService.processRefundTicketNotify(msg);
			break;
		// 5：拒绝出票通知
		case 5:
			processNotifyLocalService.processOutTicketFailureNotify(msg);
			break;
		// 6：客服订单审核成功通知
		case 6:
			processNotifyLocalService.processOrderAuditEndNotify(msg);
			break;
		}
	}
}
