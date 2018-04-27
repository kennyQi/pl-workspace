package hsl.dzpw;

import hsl.app.service.local.dzp.DZPSyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hgg on 2016/3/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DzpwApiTest {

	@Autowired
	private DZPSyncService service;

	/**
	 * 同步门票政策
	 */
	@Test
	public void testTicketPolicy() {
		service.syncTicketPolicy();
	}

	/**
	 * 同步景区
	 */
	@Test
	public void testScenicSpot() {
		service.syncScenicSpot();
	}

	/**
	 * 同步省市区
	 */
	@Test
	public void testRegion() {
//		service.syncRegion();
		service.testUpdateRegion();
	}
}
