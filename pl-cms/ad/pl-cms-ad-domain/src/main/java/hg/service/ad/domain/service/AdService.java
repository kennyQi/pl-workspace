package hg.service.ad.domain.service;

import hg.service.ad.domain.model.ad.Ad;

import org.springframework.stereotype.Component;

@Component
public class AdService {
	
	/**
	 * 
	 * @方法功能说明：将两条广告优先级互换
	 * @修改者名字：yuxx
	 * @修改时间：2015年3月12日下午4:00:53
	 * @修改内容：
	 * @参数：@param targetAd	操作的目标广告
	 * @参数：@param lastAd		要被交换的广告
	 * @参数：@param nextAd
	 * @参数：@return
	 * @return:Ad
	 * @throws
	 */
	public void exchangePriority(Ad targetAd, Ad sourceAd) {
		Integer priority1 = targetAd.getBaseInfo().getPriority();
		Integer priority2 = sourceAd.getBaseInfo().getPriority();
		
		targetAd.modifyPriority(priority2);
		sourceAd.modifyPriority(priority1);
	}
	
}
