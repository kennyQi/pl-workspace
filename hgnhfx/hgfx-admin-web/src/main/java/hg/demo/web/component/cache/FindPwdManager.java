package hg.demo.web.component.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

@Component
public class FindPwdManager {
	
	@Autowired
	private JedisPool jedisPool;
//	private JedisSentinelPool jedisPool;
	
	private static final String FIND_PWD_TIMES = "find_pwd_times";
	
	
	/**
	 * 查询
	 * */
	public Integer getFindPwdTimes(){
		Jedis jedis = jedisPool.getResource();
		
		try {
			if (StringUtils.isBlank(jedis.get(FIND_PWD_TIMES)))
				setFindPwdTimes(10);
			
			return Integer.valueOf(jedis.get(FIND_PWD_TIMES));
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally{
			jedis.close();
		}
	}
	
	
	/**
	 * 设值
	 * */
	public void setFindPwdTimes(Integer num){
		
		Jedis jedis = jedisPool.getResource();
		
		try {
			if (num!=null && num>=0)
				jedis.set(FIND_PWD_TIMES, num.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
}
