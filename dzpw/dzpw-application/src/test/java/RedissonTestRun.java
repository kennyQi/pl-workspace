import org.redisson.Config;
import org.redisson.Redisson;

public class RedissonTestRun {

	public static class TestClassA {
		public String a = "aaa";
		public String b = "bbb";
	}

	public static void main(String[] args) {
		Config config = new Config();
		// config.useClusterServers().addNodeAddress("127.0.0.1:6379");
		config.useSingleServer().setAddress("127.0.0.1:6379");
		Redisson r = null;
		try {
			r = Redisson.create(config);
			r.getList("hello").add(new TestClassA());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.shutdown();
		}
	}
}
