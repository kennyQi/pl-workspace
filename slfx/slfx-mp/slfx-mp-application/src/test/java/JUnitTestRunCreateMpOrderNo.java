//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.mp.app.common.util.OrderNumberUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-mp-test.xml" })
//public class JUnitTestRunCreateMpOrderNo {
//
//	/**
//	 * @方法功能说明：测试订单号创建
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-9-1下午5:34:56
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void test() {
//		String dealerId = "F1001";
//		String sourceType = OrderNumberUtils.SourceType.PC;
//		for(int i=0;i<9999;i++){
//			String no = OrderNumberUtils.createMPPlatformOrderCode(dealerId,
//					sourceType);
//			System.out.println(no);
//		}
//	}
//
//}
