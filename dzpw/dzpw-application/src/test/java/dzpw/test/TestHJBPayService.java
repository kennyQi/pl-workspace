//package dzpw.test;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import hg.dzpw.app.service.api.hjb.HJBPayService;
//import hg.dzpw.pojo.api.hjb.HJBRegisterRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBRegisterResponseDto;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryResponseDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
//public class TestHJBPayService {
//	@Autowired
//	private HJBPayService hjbPayService ;
//
//	@Test
//	public void testRegister(){
//		HJBRegisterRequestDto dto=new HJBRegisterRequestDto();
//		dto.setVersion("1.0");
//		dto.setMerchantId("zy01835");
//		dto.setCstName("哈哈4");
//		dto.setAddress("上海");
//		dto.setCallerIp("192.168.14.25");
//		dto.setCstVersion("53");
//		dto.setMobile("18888888885");
//		dto.setPhone("100-4567498786");
//		dto.setEmail("afgd4@fds.com");
//		dto.setOperatorName("hfhf4");
//		dto.setOperatorEmail("fds4@fds.com");
//		dto.setOperatorMobile("15512456854");
//		dto.setLoginName("ididi4");
//		dto.setSplitAccount("123456479644");
//		HJBRegisterResponseDto  hjbregiRequ=hjbPayService.register(dto);
//		System.out.println(hjbregiRequ.getErrorMessage());
//	}
//	void testSignQuery() {
//		HJBSignQueryRequestDto dto=new HJBSignQueryRequestDto();
//		dto.setVersion("1.0");
//		dto.setMerchantId("M100000894");
//		dto.setCallerIp("192.168.1.45");
//		dto.setOrderNo("ZZ201505061000083099");
//		HJBSignQueryResponseDto hjbsq=hjbPayService.signQuery(dto);
//		System.out.println("转账时间："+hjbsq.getCreateTime());
//	}
//	
//	void testSignTransfer(){
//
//		HJBTransferRequestDto dto=new HJBTransferRequestDto();
//		dto.setVersion("1.0");
//		dto.setMerchantId("M100000894");
//		dto.setCallerIp("192.168.22.2");
//		dto.setPayCstNo("C100001085");
//		dto.setRcvCstNo("C100000642");
//		dto.setTrxAmount("100");
//		dto.setUserId("100486");
//		dto.setRemark("");
//		dto.setOriginalOrderNo("201504291218");
//		HJBTransferResponseDto hjbtrResponseDto=hjbPayService.signTransfer(dto);
//		System.out.println(hjbtrResponseDto.getOrderNo());
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		TestHJBPayService service=new TestHJBPayService();
//		//测试注册
////		service.testRegister();
//		//测试查询
////		service.testSignQuery();
//		
//		//测试转账
//		service.testSignTransfer();
//	}
//
//}
