package service.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.pojo.qo.yxjp.YXJPOrderFlightQO;
import hsl.pojo.qo.yxjp.YXJPOrderPassengerQO;
import hsl.pojo.qo.yxjp.YXJPOrderQO;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author zhurz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestQueryYxjpOrder {

	@Autowired
	YXJPOrderLocalService service;

	private void printJson(Object obj) {
		System.out.println(JSON.toJSONString(obj, new ValueFilter() {
			@Override
			public Object process(Object source, String name, Object value) {
				if (Hibernate.isInitialized(value))
					return value;
				return null;
			}
		}, SerializerFeature.PrettyFormat));
	}

	@Test
	public void testQueryList() {
		System.out.println("testQueryList---->>");
		YXJPOrderQO qo = new YXJPOrderQO();
		YXJPOrderFlightQO flightQO = new YXJPOrderFlightQO();
		YXJPOrderPassengerQO passengerQO = new YXJPOrderPassengerQO();
		qo.setOrderNo("1234");
		qo.setCreateDateBegin(new Date());
		qo.setFlightQo(flightQO);
		qo.setPassengerQO(passengerQO);
		flightQO.setOrgCity("SHA");
		flightQO.setDstCity("CAN");
		passengerQO.setIdNo("1234");
		passengerQO.setStatus(1);
		qo.setFetchPassengers(true);
		printJson(service.queryList(qo));
	}
}
