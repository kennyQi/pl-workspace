//package com.huigou.common.redis;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import com.alibaba.fastjson.JSONObject;
//import com.huigou.common.util.JsonUtil;
//import com.huigou.common.util.LogFlow;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-redis.xml" })
//public class JedisClientTest {
//
//	// @Resource
//	// private ShardedJedisPool shardedJedisPool;
//
//	@Resource
//	private JedisPool jedisPool;
//
//	private Logger logger = LoggerFactory.getLogger(JedisClientTest.class);
//
//	// private ShardedJedis jedis;
//
//	// public ShardedJedis getJedis() {
//	// if (jedis == null) {
//	// jedis = shardedJedisPool.getResource();
//	// }
//	// return jedis;
//	// }
//
////	 /**
////	 * 保存一个字符串
////	 */
//	 @Test
//	 public void setStr() {
//	 Jedis jedis = jedisPool.getResource();
//	 logger.info("set:" + jedis.set("test_set", "Hello World!"));
//	 }
////	
////	 /**
////	 * 读取一个字符串
////	 */
//	 @Test
//	 public void getStr() {
//	 Jedis jedis = jedisPool.getResource();
//	 jedis.flushAll();
//	 logger.info("get:" + jedis.get("test_set"));
//	 }
////	
////	 /**
////	 * 写入一个新字符串并读取它的旧值
////	 */
////	 @Test
////	 public void getSet() {
////	 Jedis jedis = jedisPool.getResource();
////	 logger.info("getSet:" + jedis.getSet("test_set", "Hello!"));
////	 }
////	
////	 /**
////	 * 移除给定的一个或多个key。如果key不存在，则忽略该命令。
////	 */
////	 @Test
////	 public void del() {
////	 Jedis jedis = jedisPool.getResource();
////	 logger.info("del:" + jedis.del("test_set").toString());
////	 }
////	
////	 /**
////	 * 返回给定key的剩余生存时间(time to live)(以秒为单位)
////	 */
////	 @Test
////	 public void ttl() {
////	 Jedis jedis = jedisPool.getResource();
////	 logger.info("ttl:" + jedis.ttl("test_set").toString());
////	 }
////	
////	 /**
////	 * 将一个到期的key恢复为正常的key
////	 */
////	 @Test
////	 public void persist() {
////	 Jedis jedis = jedisPool.getResource();
//////	 logger.info(jedis.persist());
////	 }
////	
////	 /**
////	 * 检查一个key是否存在
////	 */
////	 @Test
////	 public void exists() {
////	 Jedis jedis = jedisPool.getResource();
////	 logger.info("exists:" + jedis.exists("test_set").toString());
////	 }
////	
////	 /**
////	 * 返回key所储存的值的类型
////	 * none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
////	 */
////	 @Test
////	 public void type() {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.set("type_test", "ok");
////	 logger.info("type:" + jedis.type("type_test"));
////	 jedis.del("type_test");
////	 }
////	
////	 /**
////	 * 为给定key设置生存时间。当key过期时，它会被自动删除，key有值时返回1，无值返回0
////	 */
////	 @Test
////	 public void expire() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.set("expire_test", "ok");
////	 logger.info("expire_test:" + jedis.expire("expire_test", 3));
////	 logger.info("expire_test:" + jedis.get("expire_test"));
////	 Thread.sleep(3000);
////	 logger.info("expire_test:" + jedis.get("expire_test"));
////	 }
////	
////	 /**
////	 * 给value排序，仅限数字
////	 */
////	 @Test
////	 public void sort() {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.del("key");
////	 jedis.lpush("key", "1.0");
////	 jedis.lpush("key", "1.1");
////	 jedis.lpush("key", "2.3");
////	 jedis.lpush("key", "2.0");
////	 jedis.lpush("key", "1.7");
////	
////	 List list = jedis.sort("key");//默认是升序
////	
////	 for (Object value : list) {
////	 logger.info(value.toString());
////	 }
////	
////	 }
////	
////	 /**
////	 * 为key值设置value并设置过期时间
////	 */
////	 @Test
////	 public void setEx() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.set("expire_test", "ok");
////	 logger.info("setex_test:" + jedis.setex("setex_test", 3, "ok"));
////	 logger.info("setex_test:" + jedis.get("setex_test"));
////	 Thread.sleep(3000);
////	 logger.info("setex_test:" + jedis.get("setex_test"));
////	 }
////	
////	 /**
////	 * mutli多操作和事务
////	 */
////	 @Test
////	 public void multi() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 // 开启事务
////	 Transaction t = jedis.multi();
////	
////	 t.del("key1");
////	 t.del("key2");
////	 // 一次写入多个key
////	 t.mset("key1", "aaa", "key2", "bbb");
////	
////	 Response<String> result1 = t.get("key1");
////	 // 一次取出多个key
////	 Response<List<String>> result2 = t.mget("key1", "key2");
////	 t.exec(); // 在get结果前要先用exec关闭事务
////	 logger.info(result1.get());
////	 logger.info(result2.get().get(0) + result2.get().get(1));
////	
////	 }
////	
////	 /**
////	 * 清空所有的key，慎用
////	 */
////	 @Test
////	 public void flushAll() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.set("1", "1");
////	 jedis.set("2", "2");
////	 logger.info("1:" + jedis.get("1"));
////	 jedis.flushAll();
////	 logger.info("1:" + jedis.get("1"));
////	 logger.info("2:" + jedis.get("2"));
////	
////	 }
////	
////	 /**
////	 * 统计key的总数
////	 */
////	 @Test
////	 public void dbSize() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.flushAll();
////	 jedis.set("1", "1");
////	 jedis.set("2", "2");
////	 logger.info("count:" + jedis.dbSize());
////	 jedis.flushAll();
////	
////	 }
////	
////	 /**
////	 * 拼接字符串
////	 */
////	 @Test
////	 public void append() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.flushAll();
////	 jedis.set("1", "1");
////	 jedis.append("1", "2");
////	 jedis.append("2", "2");
////	 logger.info("1:" + jedis.get("1"));
////	 logger.info("2:" + jedis.get("2"));
////	 jedis.flushAll();
////	 }
////	
////	 /**
////	 * 数值操作
////	 */
////	 @Test
////	 public void decr() throws InterruptedException {
////	 Jedis jedis = jedisPool.getResource();
////	 jedis.flushAll();
////	 jedis.set("1", "10");
////	 logger.info("1:" + jedis.decr("1")); // 减1
////	 logger.info("1:" + jedis.decrBy("1", 2)); // 减n
////	 logger.info("1:" + jedis.incr("1")); // 加1
////	 logger.info("1:" + jedis.incrBy("1", 2)); // 加n
////	 jedis.flushAll();
////	 }
////	
////	
////	 /**
////	 * 哈希操作，在redis中建立一个Map并对其操作，推荐比直接set字符串优先使用，能节省很多内存，更便于分类
////	 */
////	 @Test
////	 public void map() throws InterruptedException {
////	 LogFlow logFlow = new LogFlow("redis哈希操作", JedisClientTest.class,
////	 LogFlow.LOG_LEVEL_INFO);
////	 Jedis jedis = jedisPool.getResource();
////	 logFlow.record("创建连接");
////	 Jedis jedis1 = jedisPool.getResource();
////	 logFlow.record("创建连接");
////	 Jedis jedis2 = jedisPool.getResource();
////	 logFlow.record("创建连接");
////	 jedis.flushAll();
////	 logFlow.record("清空");
////	 // 往Hash map1中存两个值
////	 jedis.hset("map1", "key1", "a");
////	 jedis.hset("map1", "key2", "b");
////	 logFlow.record("map1-key1:", jedis.hget("map1", "key1"));
////	 logFlow.record("map1-key2:", jedis.hget("map1", "key2"));
////	
////	 // 从Hash map1中取两个值
////	 Transaction t = jedis.multi();
////	 logFlow.record("开启事务");
////	 Response<List<String>> response = t.hmget("map1", "key1", "key2");
////	 logFlow.record("取两个值");
////	 t.exec();
////	 logFlow.record("关闭事务");
////	 logFlow.record("mget:", response.get().get(0) + response.get().get(1));
////	
////	 // 从Hash map1删掉一个值
////	 jedis.hset("map1", "key3", "0");
////	 jedis.hdel("map1", "key1");
////	 logFlow.record("改值");
////	 // 取出Hash map1中全部键值对
////	 Map<String,String> map = jedis.hgetAll("map1");
////	 for(Map.Entry entry: map.entrySet()) {
////	 logFlow.record(entry.getKey() + ":" + entry.getValue());
////	 }
////	 logFlow.record("hgetAll");
////	
////	 // 取长度
////	 logFlow.record("hlen:", jedis.hlen("map1").toString());
////	
////	 // 增量
////	 logFlow.record("hincrBy:", jedis.hincrBy("map1", "key3", 3).toString());
////	
////	 // 查key是否存在
////	 logFlow.record(jedis.hexists("map1", "key1").toString());
////	
////	 // 查map中全部的key
////	 Set<String> keys = jedis.hkeys("map1");
////	 for (String key : keys) {
////	 logFlow.record("keys:" , key);
////	 }
////	 logFlow.record("hkeys");
////	
////	 // 查map中全部的value
////	 List<String> values = jedis.hvals("map1");
////	 for (String value : values) {
////	 logFlow.record("values:" , value);
////	 }
////	 logFlow.record("hvals");
////	
////	 jedis.flushAll();
////	 logFlow.record("flushAll");
////	 logFlow.print();
////	 }
////	
////	 /**
////	 * 哈希操作，在redis中建立一个list并对其操作
////	 */
////	 @Test
////	 public void list() {
////	
////	 LogFlow logFlow = new LogFlow("redis列表操作", JedisClientTest.class,
////	 LogFlow.LOG_LEVEL_INFO);
////	 Jedis jedis = jedisPool.getResource();
////	 logFlow.record("创建连接");
////	 jedis.flushAll();
////	 // 存入3个值
////	 jedis.lpush("list1", "a", "b", "c");
////	 // 把位子下标1的值改为x
////	 jedis.lset("list1", 1, "x");
////	 // 再存一个值
////	 jedis.lpushx("list1", "d");
////	
////	 logFlow.record("llen", jedis.llen("list1").toString());
////	 // 查所有值，可以按下标范围查，可用作分页查询
////	 List<String> list = jedis.lrange("list1", 0, -1);
////	 // list是个栈，后进先出
////	 for (String value : list) {
////	 logFlow.record("value:",value);
////	 }
////	 // 查一个值，相当于list.get(i)
////	 logFlow.record("lindex-1", jedis.lindex("list1", 1));
////	
////	 // 从左边取出并移走一个元素，左边即队头，最后进的一个
////	 logFlow.record("lpop", jedis.lpop("list1"));
////	
////	 // 从左边取出并移走一个元素，左边即队尾，最先进的一个
////	 logFlow.record("rpop", jedis.rpop("list1"));
////	
////	 // 还剩下的
////	 list = jedis.lrange("list1", 0, -1);
////	 for (String value : list) {
////	 logFlow.record("value:",value);
////	 }
////	
////	 logFlow.print();
////	 jedis.flushAll();
////	 }
////
////	/**
////	 * 哈希操作，在redis中建立一个set并对其操作
////	 */
////	@Test
////	public void set() {
////		LogFlow logFlow = new LogFlow("redis列表操作", JedisClientTest.class,
////				LogFlow.LOG_LEVEL_INFO);
////		Jedis jedis = jedisPool.getResource();
////		logFlow.record("创建连接");
////		jedis.flushAll();
////		jedis.sadd("set1", "a", "b", "c");
////		jedis.sadd("set1", "4");
////		jedis.srem("set1", "b");
////
////		Set<String> set = jedis.smembers("set1");
////		for (String value : set) {
////			logFlow.record(value);
////		}
////
////		// set.contains;
////		logFlow.record(jedis.sismember("set1", "a").toString());
////
////		// count
////		logFlow.record(jedis.scard("set1").toString());
////
////		// 把一个值从集合1移到集合2
////		jedis.smove("set1", "set2", "a");
////
////		Set<String> set1 = jedis.smembers("set1");
////		for (String value : set1) {
////			logFlow.record("set1", value);
////		}
////
////		Set<String> set2 = jedis.smembers("set2");
////		for (String value : set2) {
////			logFlow.record("set2", value);
////		}
////
////		// 取交集
////		jedis.sadd("set1", "a");
////		jedis.sadd("set2", "b");
////		jedis.sadd("set2", "z");
////
////		Set<String> set3 = jedis.sinter("set1", "set2");
////		for (String value : set3) {
////			logFlow.record("sinter set3", value);
////		}
////
////		set1 = jedis.smembers("set1");
////		for (String value : set1) {
////			logFlow.record("set1", value);
////		}
////
////		set2 = jedis.smembers("set2");
////		for (String value : set2) {
////			logFlow.record("set2", value);
////		}
////
////		// 取交集并把结果保存到目标集合set4
////		logFlow.record(jedis.sinterstore("set4", "set1", "set2").toString());
////
////		set1 = jedis.smembers("set1");
////		for (String value : set1) {
////			logFlow.record("sinterstore set1", value);
////		}
////
////		set2 = jedis.smembers("set2");
////		for (String value : set2) {
////			logFlow.record("sinterstore set2", value);
////		}
////
////		Set<String> set4 = jedis.smembers("set4");
////		for (String value : set4) {
////			logFlow.record("sinterstore set4", value);
////		}
////
////		// 取并集
////		Set<String> set5 = jedis.sunion("set1", "set2");
////		for (String value : set5) {
////			logFlow.record("sinter set5", value);
////		}
////
////		// 取并集并保存到目标集合set5
////		jedis.sunionstore("set5", "set1", "set2");
////		set5 = jedis.smembers("set5");
////		for (String value : set5) {
////			logFlow.record("sinterstore set5", value);
////		}
////
////		// 取并集
////		Set<String> set6 = jedis.sunion("set1", "set2");
////		for (String value : set6) {
////			logFlow.record("sinter set6", value);
////		}
////
////		// 取并集并保存到目标集合set6
////		jedis.sunionstore("set6", "set1", "set2");
////		set6 = jedis.smembers("set6");
////		for (String value : set6) {
////			logFlow.record("sinterstore set6", value);
////		}
////		logFlow.print();
////	}
////	 @Test
////	 public void set() {
////			LogFlow logFlow = new LogFlow("redis列表操作", JedisClientTest.class,
////					LogFlow.LOG_LEVEL_INFO);
////			Jedis jedis = jedisPool.getResource();
////			logFlow.record("创建连接");
////			Map<String,String> map=new HashMap<String,String>();
////			Test1 t=new  JedisClientTest.Test1();
////			t.setName("2233");
////			t.setAge("23");
////			Test1 t1=new  JedisClientTest.Test1();
////			t1.setName("gggg3");
////			t1.setAge("25");
////			map.put("111",JsonUtil.parseObject(t, false));
////			map.put("1222",JsonUtil.parseObject(t1, false));
////			jedis.hmset("testMap", map);
////			logFlow.record(map.size()+"");
////			 Map<String,String> map1 = jedis.hgetAll("testMap");
////			 logFlow.record(map1.size()+"");
////			 for(Map.Entry entry: map1.entrySet()) {
////				 logFlow.record("where are you !");
////				 if("111".equals(entry.getKey())){
////					 JSONObject obj= (JSONObject) JSONObject.parse(entry.getValue().toString());
////					 
////					 Test1 ddd11=new  JedisClientTest.Test1();
////					 ddd11.setName(obj.getString("name"));
////					 ddd11.setAge(obj.getString("age"));
////					 logFlow.record(ddd11.getName()+"@@@@@"+ddd11.getAge());
////					 logFlow.record(entry.getKey() + ":" + entry.getValue());
////				 }
////				 logFlow.record(entry.getKey() + ":" + entry.getValue());
////			 }
////			 logFlow.print();
////	 }
////	 class Test1{
////		 String name;
////		 String age;
////		public String getName() {
////			return name;
////		}
////		public void setName(String name) {
////			this.name = name;
////		}
////		public String getAge() {
////			return age;
////		}
////		public void setAge(String age) {
////			this.age = age;
////		}
////		 
////	 } 
////	@Test
////	public void get(){
////		 LogFlow logFlow = new LogFlow("redis列表操作", JedisClientTest.class,
////					LogFlow.LOG_LEVEL_INFO);
////			Jedis jedis = jedisPool.getResource();
////			logFlow.record("创建连接");
////			Map<String,String> map=new HashMap<String,String>();
////
////			 Map<String,String> map1 = jedis.hgetAll("hashTest");
////			 logFlow.record(map1.size()+"");
////			 for(Map.Entry entry: map1.entrySet()) {
////				 logFlow.record(entry.getKey() + ":" + entry.getValue());
////			 }
////			 logFlow.print();
////	}
//}
