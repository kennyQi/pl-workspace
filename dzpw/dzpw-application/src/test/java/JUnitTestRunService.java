//import hg.common.component.CommonDao;
//import hg.dzpw.app.pojo.qo.HJBTransferRecordQo;
//import hg.dzpw.app.service.local.HJBTransferRecordLocalService;
//import hg.dzpw.domain.model.pay.HJBTransferRecord;
//import hg.system.model.meta.City;
//import hg.system.qo.CityQo;
//import hg.system.service.CityService;
//
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
//public class JUnitTestRunService {
//
//	@Autowired
//	private HJBTransferRecordLocalService hjbTransferRecordLocalService;
//
//	// 行政区查询测试
//	@Test
//	public void testQueryHjbTransferRecord() {
//		Date now = new Date();
//		HJBTransferRecordQo qo = new HJBTransferRecordQo();
//		qo.setType(1);
//		qo.setTargetId("111");
//		qo.setRecordDateBegin(now);
//		qo.setRecordDateEnd(now);
//		qo.setRecordDateOrder(-1);
//		qo.setHasResponse(true);
//		qo.setPayCstNo("111");
//		qo.setRcvCstNo("222");
//		qo.setTrxAmountBegin(0);
//		qo.setTrxAmountEnd(1000);
//		qo.setUserId("513131");
//		qo.setStatus("1");
//		qo.setHjbOrderNo("222");
//		qo.setErrorCode("ffff");
//		qo.setMessage("123132");
//		List<HJBTransferRecord> list = hjbTransferRecordLocalService
//				.queryList(qo);
//		list = hjbTransferRecordLocalService
//				.queryList(qo);
//		list = hjbTransferRecordLocalService
//				.queryList(qo);
//		list = hjbTransferRecordLocalService
//				.queryList(qo);
//		System.out.println(JSON.toJSONString(list, true));
//	}
//	
//	@Autowired
//	private CityService cityService;
//	@Autowired
//	private CommonDao commonDao;
//	
//	@Test
//	public void test() {
//		CityQo qo = new CityQo();
//		qo.setProjectionPropertys(new String[] { "id" });
//		commonDao.forEntity(City.class);
//		List<City> list = cityService.queryList(qo);
//		System.out.println(JSON.toJSONString(list, true));
//	}
//
//	// 行政区查询测试
//	@Test
//	public void testSaveQueryHjbTransferRecord() {
//		HJBTransferRecord record = new HJBTransferRecord();
//		record.setId("asdfadf");
//		hjbTransferRecordLocalService.save(record);
//	}
//
//}
