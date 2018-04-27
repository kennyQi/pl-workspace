package hg.system.cache;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisCacheManager {

	@Autowired
	private JedisPool jedisPool;
	
	
	public static class ReturnJedisAction implements ActionListener {
		
		private JedisPool jedisPool;
		private Jedis jedis;
		

		public ReturnJedisAction(JedisPool jedisPool, Jedis jedis) {
			this.jedisPool = jedisPool;
			this.jedis = jedis;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (jedis.isConnected())
				jedisPool.returnResource(jedis);
			else
				jedisPool.returnBrokenResource(jedis);
		}
		
	}
	
	/**
	 * @方法功能说明：获取jedis会话
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-17下午6:34:33
	 * @修改内容：
	 * @参数：@param time			最大获取时间
	 * @参数：@return
	 * @return:Jedis
	 * @throws
	 */
	public Jedis getJedis(int time) {
		
		Jedis jedis = jedisPool.getResource();
		
		if (!jedis.isConnected()) {
			jedisPool.returnBrokenResource(jedis);
			throw new RuntimeException("未能与redis建立连接");
		}
		
		Timer timer = new Timer(time, new ReturnJedisAction(jedisPool, jedis));
		timer.setRepeats(false);
		timer.start();
		
		return jedis;
	}
	

	public Jedis getJedis() {
		return getJedis(60 * 1000);
	}

}
