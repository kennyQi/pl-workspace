package hsl.jp;

import static org.junit.Assert.*;

import com.alibaba.dubbo.common.json.JSON;
import hsl.admin.common.ArraysUtil;
import hsl.admin.controller.api.ApiController;
import hsl.app.service.local.jp.FlightOrderService;
import hsl.domain.model.jp.FlightOrder;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.spi.inter.jp.JPService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class JPControllerTest {
	@Autowired
	private JPService jpService;
	@Autowired
	private FlightOrderService flightOrderService;
	@Test
	public void test1() {
		UpdateJPOrderStatusCommand command= new UpdateJPOrderStatusCommand();
		command.setPayTradeNo("2015112300001000260063695859");
		command.setPayStatus(26);
		command.setStatus(16);
		command.setBuyerEmail("18634402411");
		command.setDealerOrderCode("BB23164344000001");
		flightOrderService.updateOrderStatus(command);
	}
}
