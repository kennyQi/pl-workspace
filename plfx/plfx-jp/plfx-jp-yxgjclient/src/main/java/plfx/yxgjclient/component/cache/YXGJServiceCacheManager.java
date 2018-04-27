package plfx.yxgjclient.component.cache;

import hg.log.util.HgLogger;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.stereotype.Component;

import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.yxgjclient.pojo.param.BaseParam;
import plfx.yxgjclient.pojo.response.BaseResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：易行国际接口缓存管理
 * @类修改者：
 * @修改日期：2015-7-28下午1:34:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-28下午1:34:55
 */
@Component
public class YXGJServiceCacheManager {

	@Resource
	private Redisson redisson;

	public final static String PLFX_YXGJ_SERVICE_CACHE_PREFIX = "PLFX:YXGJ_SERVICE_CACHE:";

	private final static String devName = "zhurz";

	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}

	/**
	 * @方法功能说明：得到缓存KEY
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-28下午1:35:26
	 * @修改内容：
	 * @参数：@param requestParam
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String getKey(BaseParam requestParam) {
		String className = requestParam.getClass().getSimpleName();
		JSONObject jsonObject = (JSONObject) JSON.toJSON(requestParam);
		jsonObject.remove("userName");
		jsonObject.remove("signUserName");
		jsonObject.remove("serviceName");
		String requestParamJson = jsonObject.toJSONString();
		String requestMd5 = DigestUtils.md5Hex(requestParamJson);
		return PLFX_YXGJ_SERVICE_CACHE_PREFIX + className + ":" + requestMd5;
	}

	/**
	 * @方法功能说明：缓存结果3分钟
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-28下午1:35:35
	 * @修改内容：
	 * @参数：@param requestParam
	 * @参数：@param result
	 * @return:void
	 * @throws
	 */
	public void cacheResult(BaseParam requestParam, Object result) {
		if (requestParam == null || result == null)
			return;
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();
		String cacheKey = getKey(requestParam);
		getHgLogger().info(getClass(), devName, String.format("(%s}->缓存易行国际接口调用结果3分钟->%s", trackingToken, cacheKey), trackingToken);
		RBucket<Object> bucket = redisson.getBucket(cacheKey);
		bucket.set(result);
		bucket.expire(3, TimeUnit.MINUTES);
	}

	/**
	 * @方法功能说明：获取缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-28下午1:35:48
	 * @修改内容：
	 * @参数：@param requestParam
	 * @参数：@param clazz
	 * @参数：@return
	 * @return:T
	 * @throws
	 */
	public <T extends BaseResult> T getResultCache(BaseParam requestParam, Class<T> clazz) {
		if (requestParam == null)
			return null;
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();
		String cacheKey = getKey(requestParam);
		getHgLogger().info(getClass(), devName, String.format("(%s}->获取易行国际接口调用结果缓存->%s", trackingToken, cacheKey), trackingToken);
		RBucket<T> bucket = redisson.getBucket(cacheKey);
		T result = bucket.get();
		if (result != null)
			result.setCache(true);
		getHgLogger().info(getClass(), devName, String.format("(%s}->获取易行国际接口调用结果缓存->%s->%s", trackingToken, cacheKey, result != null ? "成功" : "无缓存"), trackingToken);
		return result;
	}

}
