//import hg.system.cache.KVConfigCacheManager;
//import hg.system.model.config.KVConfig;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
//public class TestRedisCache {
//	
//	@Autowired
//	private KVConfigCacheManager cacheManager;
//	
//	
//	@Test
//	public void hello(){
//		KVConfig kv=new KVConfig();
//		kv.setDataKey("hello");
//		kv.setDataValue("world");
//		cacheManager.putKV(kv);
//		System.out.println(cacheManager.getKV("hello"));
//	}
//}
