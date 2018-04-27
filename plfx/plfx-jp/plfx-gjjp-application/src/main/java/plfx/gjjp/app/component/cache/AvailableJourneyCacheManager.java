package plfx.gjjp.app.component.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.stereotype.Component;

import plfx.yxgjclient.pojo.param.AvailableJourney;

/**
 * @类功能说明：可用航班组合缓存管理(易行天下国际接口)
 * @类修改者：
 * @修改日期：2015-7-16上午10:55:30
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16上午10:55:30
 */
@Component
public class AvailableJourneyCacheManager {

	@Resource
	private Redisson redisson;

	public final static String PLFX_GJ_AVAILABLE_JOURNEY_PREFIX = "PLFX:GJ_AVAILABLE_JOURNEY:";

	/**
	 * @方法功能说明：缓存可用航班组合(30分钟)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-15上午10:44:08
	 * @修改内容：
	 * @参数：@param availableJourney
	 * @return:void
	 * @throws
	 */
	public void cacheAvailableJourney(AvailableJourney availableJourney) {
		RBucket<AvailableJourney> bucket = redisson
				.getBucket(PLFX_GJ_AVAILABLE_JOURNEY_PREFIX
						+ DigestUtils.md5Hex(availableJourney.getEncryptString()));
		bucket.set(availableJourney, 30, TimeUnit.MINUTES);
	}

	/**
	 * @方法功能说明：获取可用航班缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-15上午10:44:18
	 * @修改内容：
	 * @参数：@param encryptStringMd5
	 * @参数：@return
	 * @return:AvailableJourney
	 * @throws
	 */
	public AvailableJourney getAvailableJourneyCache(String encryptStringMd5) {
		RBucket<AvailableJourney> bucket = redisson
				.getBucket(PLFX_GJ_AVAILABLE_JOURNEY_PREFIX + encryptStringMd5);
		bucket.expire(30, TimeUnit.MINUTES);
		return bucket.get();
	}
	
}
