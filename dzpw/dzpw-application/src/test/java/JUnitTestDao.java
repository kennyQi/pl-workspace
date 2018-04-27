import hg.common.page.Pagination;
import hg.dzpw.app.dao.FinanceManagementDao;
import hg.dzpw.app.dao.TicketSaleStatisticsDao;
import hg.dzpw.app.dao.TicketUseStatisticsDao;
import hg.dzpw.pojo.qo.DealerSaleStatisticsQo;
import hg.dzpw.pojo.qo.GroupTicketSaleStatisticsQo;
import hg.dzpw.pojo.qo.GroupTicketUseStatisticsQo;
import hg.dzpw.pojo.qo.ReconciliationCollectOrderQo;
import hg.dzpw.pojo.qo.ScenicSpotUseStatisticsQo;
import hg.dzpw.pojo.qo.TicketOrderTouristDetailQo;
import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:application-dao-test.xml" })
public class JUnitTestDao {

	@Autowired
	private FinanceManagementDao financeManagementDao;
	@Autowired
	private TicketUseStatisticsDao ticketUseStatisticsDao;
	@Autowired
	private TicketSaleStatisticsDao ticketSaleStatisticsDao;

	@Test
	public void ticketSaleStatisticsDaoTest1() {
		GroupTicketSaleStatisticsQo qo = new GroupTicketSaleStatisticsQo();
		// qo.setTicketPolicyId("2");
		qo.setTicketPolicyName("测试测试");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo.setOrderDateBegin(c.getTime());
		qo.setOrderDateEnd(new Date());

		Pagination pagination = ticketSaleStatisticsDao
				.queryGroupTicketSaleStatistics(qo, true);

		System.out.println("--->>联票销售统计查询");
		System.out.println(JSON.toJSONString(pagination, true));

	}

	@Test
	public void ticketSaleStatisticsDaoTest2() {
		DealerSaleStatisticsQo qo = new DealerSaleStatisticsQo();
		qo.setDealerId("13cf68e78b224bfea9cddb2f7681bca6");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo.setOrderDateBegin(c.getTime());
		qo.setOrderDateEnd(new Date());

		Pagination pagination = ticketSaleStatisticsDao
				.queryDealerSaleStatistics(qo, false);

		System.out.println("--->>经销商销售统计查询");
		System.out.println(JSON.toJSONString(pagination, true));

	}

	@Test
	public void ticketSaleStatisticsDaoTest3() {
		TicketOrderTouristDetailQo qo = new TicketOrderTouristDetailQo();
//		qo.setDealerId("13cf68e78b224bfea9cddb2f7681bca6");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo.setOrderDateBegin(c.getTime());
		qo.setOrderDateEnd(new Date());

		Pagination pagination = ticketSaleStatisticsDao
				.queryTicketOrderTouristDetail(qo, true);

		System.out.println("--->>门票订单里的用户查询");
		System.out.println(JSON.toJSONString(pagination, true));

	}

	@Test
	public void financeManagementDaoTest() {
		ReconciliationCollectOrderQo qo = new ReconciliationCollectOrderQo();
		qo.setOrderDateBegin(new Date());
		qo.setOrderDateEnd(new Date());
		Pagination pagination = financeManagementDao
				.queryReconciliationCollectOrder(qo, true);
		System.out.println("--->>汇总对账单查询");
		System.out.println(JSON.toJSONString(pagination, true));

	}

	@Test
	public void ticketUseStatisticsDaoTest1() {
		ScenicSpotUseStatisticsQo qo = new ScenicSpotUseStatisticsQo();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo.setOrderDateBegin(c.getTime());
		qo.setOrderDateEnd(new Date());
		Pagination pagination = ticketUseStatisticsDao
				.queryScenicSpotUseStatistics(qo, true);
		System.out.println("--->>景区入园统计查询");
		System.out.println(JSON.toJSONString(pagination, true));

	}

	@Test
	public void ticketUseStatisticsDaoTest2() {
		GroupTicketUseStatisticsQo qo2 = new GroupTicketUseStatisticsQo();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo2.setOrderDateBegin(c.getTime());
		qo2.setOrderDateEnd(new Date());
		Pagination pagination2 = ticketUseStatisticsDao
				.queryGroupTicketUseStatistics(qo2, true);

		System.out.println("--->>联票入园统计查询");
		System.out.println(JSON.toJSONString(pagination2, true));
	}

	@Test
	public void ticketUseStatisticsDaoTest3() {

		TicketUsedTouristDetailQo qo3 = new TicketUsedTouristDetailQo();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		qo3.setOrderDateBegin(c.getTime());
		qo3.setOrderDateEnd(new Date());
		// qo3.setScenicSpotId("2");
		// qo3.setTicketPolicyId("2");
		qo3.setCerNo("232323232");
		qo3.setName("zxs");
		Pagination pagination3 = ticketUseStatisticsDao
				.queryTicketUsedTouristDetail(qo3, true);

		System.out.println("--->>入园用户明细查询");
		System.out.println(JSON.toJSONString(pagination3, true));

	}
}
