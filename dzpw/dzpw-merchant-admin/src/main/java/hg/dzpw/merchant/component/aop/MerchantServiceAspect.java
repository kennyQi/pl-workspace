package hg.dzpw.merchant.component.aop;

import hg.dzpw.app.common.util.MerchantAuthUtils;
import hg.dzpw.app.pojo.vo.MerchantSessionUserVo;
import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

import org.aspectj.lang.JoinPoint;

/**
 * @类功能说明：商户端服务切面处理
 * @类修改者：
 * @修改日期：2015-3-11上午11:09:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-11上午11:09:10
 */
public class MerchantServiceAspect {

	public void doBefore(JoinPoint jp) {
		
		MerchantSessionUserVo sessionUser = MerchantAuthUtils
				.getCurrentSessionUserVo();
		
		if (sessionUser == null)
			return;
		
		Object[] objs = jp.getArgs();
		for (Object obj : objs)
			if (obj instanceof DZPWMerchantBaseCommand)
				((DZPWMerchantBaseCommand) obj).setScenicSpotId(sessionUser
						.getScenicSpotId());

	}

}
