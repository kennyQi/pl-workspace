package hg.dzpw.dealer.admin.component.aop;

import hg.dzpw.app.common.util.DealerAuthUtils;
import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.pojo.common.DZPWDealerBaseCommand;

import org.aspectj.lang.JoinPoint;

/**
 * @类功能说明：商户端服务切面处理
 * @类修改者：
 * @修改日期：2015-12-11上午11:09:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：guotx
 * @创建时间：2015-12-11上午11:09:10
 */
public class DealerServiceAspect {

	public void doBefore(JoinPoint jp) {
		
		DealerSessionUserVo sessionUser = DealerAuthUtils
				.getCurrentSessionUserVo();
		
		if (sessionUser == null)
			return;
		
		Object[] objs = jp.getArgs();
		for (Object obj : objs)
			if (obj instanceof DZPWDealerBaseCommand)
				((DZPWDealerBaseCommand) obj).setDealerId(sessionUser
						.getDealerId());

	}

}
