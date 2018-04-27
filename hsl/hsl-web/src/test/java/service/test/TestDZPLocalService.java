package service.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import hsl.app.service.local.dzp.scenicspot.DZPScenicSpotLocalService;
import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
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
import java.util.List;

/**
 * @author zhurz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestDZPLocalService {

	@Autowired
	DZPScenicSpotLocalService service;

	@Test
	public void testQueryList() {
		DZPScenicSpotQO qo = new DZPScenicSpotQO();
//		qo.setNameOrPolicyName("华山");
		qo.setQueryTicketPolicy(true);
		long a = System.currentTimeMillis();
		List<DZPScenicSpot> list = service.queryList(qo);
		long b = System.currentTimeMillis();
		System.out.println(b - a);
		list = service.queryList(qo);
		System.out.println(System.currentTimeMillis() - b);

		String json = JSON.toJSONString(list, new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (Hibernate.isInitialized(value))
					return value;
				return null;
			}
		}, SerializerFeature.PrettyFormat);
		System.out.println(json);
	}

}
