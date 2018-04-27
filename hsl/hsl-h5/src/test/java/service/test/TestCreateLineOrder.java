package service.test;

import hg.common.util.DateUtil;
import hsl.app.component.cache.LineOrderTokenCacheManager;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.app.service.local.user.TravelerLocalService;
import hsl.domain.model.xl.order.LineOrder;
import hsl.pojo.command.line.order.HslH5CreateLineOrderCommand;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.exception.LineException;

import java.util.Arrays;
import java.util.List;

import hsl.pojo.qo.line.HslLineOrderQO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestCreateLineOrder {

	@Autowired
	TravelerLocalService travelerLocalService;
	@Autowired
	LineOrderLocalService lineOrderLocalService;
	@Autowired
	LineOrderTokenCacheManager tokenCacheManager;
	
	@Test
	public void testTokenCheck() {
		System.out.println("testTokenCheck----------->>");
		String token = tokenCacheManager.generateToken();
		System.out.println(token);
		try {
			System.out.println(tokenCacheManager.hasLineOrderTokenValue("s31231sss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertFalse(tokenCacheManager.hasLineOrderTokenValue(token));
		tokenCacheManager.setLineOrderToken(token, "sssssssssssss");
		Assert.assertTrue(tokenCacheManager.hasLineOrderTokenValue(token));
		tokenCacheManager.setLineOrderToken("ccc", "xxxc");
		System.out.println("testTokenCheck-----------<<");
	}

	@Test
	public void testLineOrderPaySuccess() {
		String orderNo = "BA28144509010000";
		System.out.println("---->支付成功(" + orderNo + ")");
		lineOrderLocalService.lineOrderPaySuccess(orderNo, false, 0d);
	}

	//	@Test
	public void testCreateLineOrder() throws LineException {
		HslH5CreateLineOrderCommand command = new HslH5CreateLineOrderCommand();
		command.setUserId("edcaa54d670042ec8663bba3ae10e5f2");
		command.setLineId("7a8a77a648364c1cb2f8f58c73b4ceae");
		command.setAdultNo(1);
		command.setChildNo(0);
		command.setTravelDate(DateUtil.parseDateTime("2015-12-12", "yyyy-MM-dd"));
		command.setTravelerIds(Arrays.asList("1"));
		command.setLinkName("呵呵");
		command.setLinkMobile("18626890576");
		command.setLinkEmail("zhurz@ply365.com");
		LineOrderDTO lineOrderDTO = lineOrderLocalService.createLineOrder(command);
		System.out.println("线路订单创建完毕------>>");
		System.out.println(JSON.toJSONString(lineOrderDTO, true));
	}

	@Test
	public void testQueryLineOrder() {
		List<LineOrder> list =
				lineOrderLocalService.queryList("edcaa54d670042ec8663bba3ae10e5f2", 0, 20);
		System.out.println("testQueryLineOrder------>>");
		System.out.println(list.size());
	}

}
