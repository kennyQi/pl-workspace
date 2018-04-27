package plfx.gjjp.app.component.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.stereotype.Component;

import plfx.yxgjclient.pojo.param.RewPolicyInfo;

/**
 * @类功能说明：有奖励的政策管理(易行天下国际接口)
 * @类修改者：
 * @修改日期：2015-7-16上午10:55:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16上午10:55:49
 */
@Component
public class RewPolicyInfoCacheManager {

	@Resource
	private Redisson redisson;

	public final static String PLFX_GJ_REW_POLICY_INFO_PREFIX = "PLFX:GJ_REW_POLICY_INFO:";

	/**
	 * @方法功能说明：缓存有奖励政策(30分钟过期)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16上午10:58:01
	 * @修改内容：
	 * @参数：@param rewPolicyInfo
	 * @return:void
	 * @throws
	 */
	public void cacheRewPolicyInfo(RewPolicyInfo rewPolicyInfo) {
		RBucket<RewPolicyInfo> bucket = redisson
				.getBucket(PLFX_GJ_REW_POLICY_INFO_PREFIX + DigestUtils.md5Hex(rewPolicyInfo.getEncryptString()));
		bucket.set(rewPolicyInfo, 30, TimeUnit.MINUTES);
	}

	/**
	 * @方法功能说明：获取有奖励政策
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16上午11:00:08
	 * @修改内容：
	 * @参数：@param encryptStringMd5
	 * @参数：@return
	 * @return:RewPolicyInfo
	 * @throws
	 */
	public RewPolicyInfo getAvailableJourneyCache(String encryptStringMd5) {
		RBucket<RewPolicyInfo> bucket = redisson
				.getBucket(PLFX_GJ_REW_POLICY_INFO_PREFIX + encryptStringMd5);
		bucket.expire(30, TimeUnit.MINUTES);
		return bucket.get();
	}
	
}
