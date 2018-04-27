//package service.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import hsl.pojo.command.ConsumeCouponCommand;
//import hsl.pojo.dto.coupon.CouponDTO;
//import hsl.pojo.qo.coupon.HslCouponQO;
//import hsl.spi.inter.Coupon.CouponService;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class CouponServiceTest {
//	@Resource
//	private CouponService couponService;
//	
////	@Test
//	public void queryCoupon(){
//		HslCouponQO hslCouponQO = new HslCouponQO();
//		Object[] o = {4};
//		hslCouponQO.setUserId("d5a4121eca804077a84fda4d9c367ab7");
//		hslCouponQO.setStatusTypes(o);
//		List<CouponDTO> couponDTOList= couponService.queryList(hslCouponQO);
////		System.out.println(JSON.toJSONString(couponDTOList));
//		System.out.println(couponDTOList.size());
//	}
//	
//	@Test
//	public void cosumeCoupon(){
//		List<ConsumeCouponCommand> commandList = new ArrayList<ConsumeCouponCommand>();
//		ConsumeCouponCommand command = new ConsumeCouponCommand();
//		command.setCouponId("wss5b509de46bd1446d8e451bb1a25d7a87");
//	}
//
//}
