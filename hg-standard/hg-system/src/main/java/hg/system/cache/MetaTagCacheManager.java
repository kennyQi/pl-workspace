package hg.system.cache;

import hg.common.util.SysProperties;
import hg.system.model.seo.MetaTag;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;

@Component
public class MetaTagCacheManager {
	@Autowired
	private JedisPool jedisPool;
	
	public final String META_TAG_CACHE_KEY_PREFIX = "meta_tag_";
	
	private String getHKey() {
		String projectId = SysProperties.getInstance().get("projectId");
		String envId = SysProperties.getInstance().get("envId");
		return META_TAG_CACHE_KEY_PREFIX + projectId + "_" + envId;
	}
	
	public void clearAll() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(getHKey());
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public void delMetaTag(String uri) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(getHKey(), uri);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public void refreshMetaTag(MetaTag tag) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(getHKey(), tag.getUri(), JSON.toJSONString(tag));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public MetaTag getMetaTag(String uri) {
		Jedis jedis = jedisPool.getResource();
		try {
			String json = jedis.hget(getHKey(), uri);
			if(StringUtils.isNotBlank(json))
				return JSON.parseObject(json, MetaTag.class);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
}
