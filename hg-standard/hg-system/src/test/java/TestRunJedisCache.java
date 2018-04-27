import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class TestRunJedisCache {
	
	
	public static class TestJedisThread implements Runnable {
		
		private Jedis jedis;
		
		public TestJedisThread(Jedis jedis){
			this.jedis = jedis;
		}

		@Override
		public void run() {
			String str = jedis.hget("test", "test");
			System.out.println(str);
		}
		
	}
	

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
	public static Jedis getJedis(JedisPool jedisPool, int time) {
		Jedis jedis = jedisPool.getResource();
		if (!jedis.isConnected()) {
			jedisPool.returnBrokenResource(jedis);
			throw new RuntimeException("未能与redis建立连接");
		}
		Timer timer=
		new Timer(time, new ReturnJedisAction(jedisPool, jedis));
		timer.setRepeats(false);
		timer.start();
		return jedis;
	}
	
	public static void main(String[] args) throws InterruptedException {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(300);
		config.setMaxIdle(100);
//		config.setMaxWait(2000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			sb.append((char) i);
		}

		jedisPool.getResource().hset("test", "test", sb.toString());
		
		System.out.println(sb.toString());
		System.out.println(sb.length());
		
		
		
//		Jedis jedis = jedisPool.getResource();
//		jedis.sync();
		for (int i = 0; i < 10000; i++) {
			new Thread(new TestJedisThread(getJedis(jedisPool, 5000))).start();
			Thread.sleep(10);
		}
		
	}
}
