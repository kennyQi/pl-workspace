package hg.demo.member.service.cache;

import java.util.List;

import hg.demo.member.service.dao.hibernate.fx.AbnormalRuleDAO;
import hg.fx.domain.AbnormalRule;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 异常订单规则缓存
 * */
@Component
public class AbnormalRuleCache {
	
	@Autowired
	private JedisPool jedisPool;
//	private JedisSentinelPool jedisPool;
	
	@Autowired
	private AbnormalRuleDAO dao;
	
	public static final String FX_ABNORMAL_RULE = "fx_abnormal_rule";
	
	
	
	/**
	 * 去缓存中第一个值
	 */
	public AbnormalRule getAbnormalRuleByKey(){
		
		Jedis jedis = jedisPool.getResource();
		
		List<String> l = jedis.hvals(FX_ABNORMAL_RULE);
		
		try {
			return JSON.parse(l.get(0), AbnormalRule.class);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			jedis.close();
		}
		
	}
	
	
	/**
	 * 获取缓存中规则
	 * */
	public AbnormalRule getAbnormalRule(String ruleId){
		
		Jedis jedis = jedisPool.getResource();
		
		// 缓存中不存在时
		if (StringUtils.isBlank(jedis.hget(FX_ABNORMAL_RULE, ruleId)));
			setAbnormalRule(dao.get(ruleId));
			
		String json = jedis.hget(FX_ABNORMAL_RULE, ruleId);
		
		if (StringUtils.isBlank(json))
			return null;
		
		try {
			// 解析成实体
			return JSON.parse(json, AbnormalRule.class);
		} catch (ParseException e) {
			e.printStackTrace();
		} finally{
			jedis.close();
		}
		return null;
	}
	
	
	/**
	 * 修改后 刷新缓存中的值
	 * @param rule修改后的实体
	 * */
	public void modifyAbnormalRule(AbnormalRule rule){
		
		Jedis jedis = jedisPool.getResource();
		try {
			if (rule!=null)
				jedis.hset(FX_ABNORMAL_RULE, rule.getId(), rule.toFormatJSONString());
		} finally {
			jedis.close();
		}
		
	}
	
	
	/**
	 * 缓存中设置规则
	 */
	public void setAbnormalRule(AbnormalRule rule){
		Jedis jedis = jedisPool.getResource();
		
		if (rule==null)
			return;
		
		try {
			// key=规则ID   value=实体json串
			jedis.hset(FX_ABNORMAL_RULE, rule.getId(), rule.toFormatJSONString());
		}finally {
			jedis.close();
		}
	}
	
	/**
	 * 判断缓存中是否存在
	 * */
	public Boolean isExistRule(){
		
		Boolean b = false;
		Jedis jedis = jedisPool.getResource();
		try {
			b = jedis.exists(FX_ABNORMAL_RULE);
			return b;
		} finally {
			jedis.close();
		}
	}
	
	
}
