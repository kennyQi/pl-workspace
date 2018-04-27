//import java.util.Timer;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPubSub;
//
//
//public class TestRedis {
//
////	<bean id="jedisPool" class="redis.clients.jedis.JedisPool">
////		<constructor-arg index="0" value="192.168.2.211" />
////		<constructor-arg index="1" value="6379" />
////	</bean>
//
//	static JedisPubSub jps = new JedisPubSub() {
//		
//		@Override
//		public void onUnsubscribe(String channel, int subscribedChannels) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onSubscribe(String channel, int subscribedChannels) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onPUnsubscribe(String pattern, int subscribedChannels) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onPSubscribe(String pattern, int subscribedChannels) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onPMessage(String pattern, String channel, String message) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onMessage(String channel, String message) {
//			System.out.println("--onMessage--");
//			System.out.println(channel);
//			System.out.println(message);
//		}
//	};
//	
//	public static void main(String[] args) throws InterruptedException {
//		JedisPool jp = new JedisPool("127.0.0.1");
//		for(;;){
//			try {
//				Jedis j = jp.getResource();
//				System.out.println("监听");
//				j.subscribe(jps, "hello");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("重试...");
////			Thread.sleep(1000);
//		}
//	}
//}
