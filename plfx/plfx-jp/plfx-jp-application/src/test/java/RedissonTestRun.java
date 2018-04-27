import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.redisson.core.RList;
import org.redisson.core.RMap;

import com.alibaba.fastjson.JSON;

import plfx.jp.domain.model.dealer.Dealer;

public class RedissonTestRun {

	public static class TestClassA {
		public String a = "aaa";
		public String b = "bbb";
	}

	public static Redisson getRedisson() {
		Config config = new Config();
		// config.useClusterServers().addNodeAddress("127.0.0.1:6379");
		// config.useSingleServer().setAddress("127.0.0.1:6379");
		config.useSingleServer().setAddress("192.168.2.211:6379");
		return Redisson.create(config);
	}

	public static void test1() {
		Redisson r = null;
		try {
			r = getRedisson();
			r.getList("hello").add(new TestClassA());
			RMap<String, String> map = r.getMap("xxxx");
			map.put("xxx", "123456");
			RMap<String, Dealer> map2 = r.getMap("dealer_xxxxx");
			// Dealer dealer = new Dealer();
			// dealer.setCode("123");
			// dealer.setSecretKey("xxxx");
			// map2.put("123", dealer);
			map2.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.shutdown();
		}
	}

	public static void test2() {
		Redisson r = null;
		try {
			r = getRedisson();
			RList<Object> list = r.getList("hello");
			RMap<String, String> map = r.getMap("xxxx");
			RMap<String, Dealer> map2 = r.getMap("dealer_xxxxx");
			for (Object str : list)
				System.out.println(str);
			for (Entry<String, String> entry : map.entrySet())
				System.out.println(entry.getKey() + ":" + entry.getValue());
			for (Entry<String, Dealer> entry : map2.entrySet())
				System.out.println(entry.getKey() + ":"
						+ JSON.toJSONString(entry.getValue()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.shutdown();
		}
	}

	public static void test3() {
		Redisson r = null;
		try {
			r = getRedisson();
			RBucket<Dealer> b = r.getBucket("ffff:xx");
			Dealer dealer = new Dealer();
			dealer.setCode("233");
			b.set(dealer, 1, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.shutdown();
		}
	}


	public static void test4() {
		Redisson r = null;
		try {
			r = getRedisson();
			RBucket<Dealer> b = r.getBucket("ffff:xx");
//			Dealer dealer = new Dealer();
//			dealer.setCode("233");
//			b.set(dealer, 1, TimeUnit.MINUTES);
			System.out.println(b.get());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.shutdown();
		}
	}

	public static void main(String[] args) {
		test4();
	}
}
