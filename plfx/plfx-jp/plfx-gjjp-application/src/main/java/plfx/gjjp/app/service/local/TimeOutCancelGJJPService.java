package plfx.gjjp.app.service.local;

import java.util.List;

import hg.common.util.JsonUtil;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.jp.command.admin.gj.ApplyCancelNoPayGJJPOrderCommand;
import plfx.jp.pojo.exception.PLFXJPException;

/**
 * @date 2015-08-20
 * @author guotx
 * @描述 国际机票超时未支付自动取消订单
 */
@Service("timeOutCancelGJJPService")
public class TimeOutCancelGJJPService {
	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	
	
	public void timeoutCancelGJJPOrderTask(){
		HgLogger.getInstance().info("guotx", "TimeoutCancelGJJPOrderTask[检查国际机票订单超时定时任务开始执行]->时间："+new java.util.Date());
		GJJPOrderQo gjjpOrderQo=new GJJPOrderQo();
		gjjpOrderQo.setPayStatus(GJJPConstants.ORDER_PAY_STATUS_WAIT);
		//查询未支付订单
		List<GJJPOrder>noPayGjjpOrders=gjjpOrderLocalService.queryList(gjjpOrderQo);
		HgLogger.getInstance().info("guotx", "TimeoutCancelGJJPOrderTask[国际机票自动取消未支付订单]->查询到"+noPayGjjpOrders.size()+"条未支付订单");
		for (GJJPOrder gjjpOrder : noPayGjjpOrders) {
			ApplyCancelNoPayGJJPOrderCommand command=new ApplyCancelNoPayGJJPOrderCommand();
			command.setPlatformOrderId(gjjpOrder.getId());
			command.setCancelReasonType(6);	//其他原因
			command.setCancelOtherReason("分销平台超时自动取消");
			try {
				gjjpOrderLocalService.applyCancelNoPayOrder(command);
				HgLogger.getInstance().debug("guotx", "TimeoutCancelGJJPOrderTask[国际机票自动取消未支付订单]->"+JsonUtil.parseObject(command, false));
			} catch (PLFXJPException e) {
				HgLogger.getInstance().debug("guotx", "TimeoutCancelGJJPOrderTask[国际机票自动取消未支付订单]->操作失败---"+JsonUtil.parseObject(command, false));
				e.printStackTrace();
			}
		}
		
	}
}
