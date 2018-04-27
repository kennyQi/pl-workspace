//import hg.common.page.Pagination;
//import hg.common.util.DateUtil;
//import hg.common.util.UUIDGenerator;
//import hg.log.util.HgLogger;
//import hg.system.cache.AddrProjectionCacheManager;
//import hg.system.model.meta.AddrProjection;
//import hg.system.model.meta.City;
//import hg.system.qo.AreaQo;
//import hg.system.qo.CityQo;
//import hg.system.qo.ProvinceQo;
//import hg.system.service.AddrProjectionService;
//import hg.system.service.AreaService;
//import hg.system.service.CityService;
//import hg.system.service.ProvinceService;
//import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotEnum.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
//import slfx.mp.app.component.job.MPOrderSyncJob;
//import slfx.mp.app.component.job.ScenicSpotUpdateJob;
//import slfx.mp.app.component.manager.TCSupplierPolicySnapshotManager;
//import slfx.mp.app.service.local.DateSalePriceLocalService;
//import slfx.mp.app.service.local.MPOrderLocalService;
//import slfx.mp.app.service.local.TCSupplierPolicySnapshotLocalService;
//import slfx.mp.app.service.local.TestService;
//import slfx.mp.domain.model.platformpolicy.DateSalePrice;
//import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
//import slfx.mp.pojo.dto.order.MPOrderDTO;
//import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;
//import slfx.mp.pojo.dto.order.TravelerDTO;
//import slfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;
//import slfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
//import slfx.mp.spi.command.admin.CreatePlatformPolicyCommand;
//import slfx.mp.spi.exception.SlfxMpException;
//import slfx.mp.spi.inter.AdminSalePolicyService;
//import slfx.mp.spi.inter.DateSalePriceService;
//import slfx.mp.spi.inter.PlatformOrderService;
//import slfx.mp.spi.inter.PlatformSpotService;
//import slfx.mp.spi.qo.PlatformOrderQO;
//import slfx.mp.spi.qo.PlatformPolicyQO;
//import slfx.mp.spi.qo.PlatformSpotsQO;
//
//import com.alibaba.fastjson.JSON;
//
//@SuppressWarnings("unused")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-mp-test.xml" })
//public class JUnitTestRun {
//
//	@Autowired
//	ProvinceService provinceService;
//	@Autowired
//	CityService cityService;
//	@Autowired
//	AreaService areaService;
//	@Autowired
//	AddrProjectionService addrProjectionService;
//	@Autowired
//	TestService testService;
//
//	@Autowired
//	ScenicSpotUpdateJob scenicSpotUpdateJob;
//
//	@Autowired
//	PlatformOrderService platformOrderService;
//
//	@Autowired
//	DateSalePriceLocalService dateSalePriceLocalService;
//
//	@Autowired
//	TCSupplierPolicySnapshotLocalService tcSupplierPolicySnapshotLocalService;
//
//	@Autowired
//	AddrProjectionCacheManager cacheManager;
//	@Autowired
//	TCSupplierPolicySnapshotManager tcSupplierPolicySnapshotManager;
//	
//	@Autowired
//	MPOrderLocalService mpOrderLocalService;
//	
//	@Autowired
//	DateSalePriceService dateSalePriceService;
//	@Autowired
//	AdminSalePolicyService adminSalePolicyService;
//	@Autowired
//	PlatformSpotService platformSpotService;
//	
//	@Autowired
//	MPOrderSyncJob mpOrderSyncJob;
//	
//	@Autowired
//	HgLogger hgLogger;
//	
//	/**
//	 * 测试同步订单状态
//	 */
//	@Test
//	public void testSyncOrderStatus(){
//		mpOrderSyncJob.syncOrder();
//	}
//	
//	/**
//	 * 景点查询
//	 */
//	@Test
//	public void queryPlatformSpot() {
//		Pagination pagination = new Pagination();
//		PlatformSpotsQO qo = new PlatformSpotsQO();
//		pagination.setCondition(qo);
//		Pagination pagination2 = platformSpotService
//				.queryPagination(pagination);
//		System.out.println(JSON.toJSONString(pagination2, true));
//		hgLogger.debug("yuxx", JSON.toJSONString(pagination2, true));
//	}
//
//	/**
//	 * 测试平台政策查询
//	 */
//	@Test
//	public void testQuerySalePolicy() {
//		PlatformPolicyQO qo = new PlatformPolicyQO();
//		qo.setCreateUserName("鹳狸猿2");
//		List<SalePolicySnapshotDTO> list = adminSalePolicyService.queryList(qo);
//		System.out.println(JSON.toJSONString(list, true));
//	}
//	
//	/**
//	 * 平台政策创建
//	 */
//	@Test
//	public void createSalePolicy() {
//		CreatePlatformPolicyCommand command = new CreatePlatformPolicyCommand();
//		command.setSupplierId("1");
//		command.setFilterType(FILTER_TYPE_PRICE);
//		command.setBeginDate(new Date());
//		command.setEndDate(DateUtil.dateStr2EndDate("2014-08-30"));
//		command.setModifyPriceType(MODIFY_PRICE_TYPE_ADD);
//		command.setModifyProceValue(10d);
//		command.setRemark("测试平台政策2");
//		command.setHighPrice(200d);
//		command.setLowPrice(100d);
//		command.setOperatorName("鹳狸猿2");
//		command.setPolicyName("测试平台政策");
//		command.setLevel(22);
//		adminSalePolicyService.createPlatformPolicy(command);
//	}
//	
//	/**
//	 * 价格日确查询
//	 */
//	@Test
//	public void testQueryDateSaleCalendar() {
//		List<DateSalePriceDTO> list = dateSalePriceService.getDateSalePrice(
//				"53846", "1");
//		System.out.println(JSON.toJSONString(list, true));
//	}
//
//	/**
//	 * 测试订单查询
//	 */
//	@Test
//	public void testQueryMporderPagination() {
//		Pagination pagination = new Pagination();
//		PlatformOrderQO qo = new PlatformOrderQO();
//		pagination.setCondition(qo);
//		Pagination p = platformOrderService.queryPagination(pagination);
//		System.out.println(JSON.toJSONString(p, true));
//	}
//	
//	/**
//	 * 测试订单查询
//	 */
//	@Test
//	public void testQueryMporder() {
//		PlatformOrderQO qo = new PlatformOrderQO();
//		List<MPOrderDTO> list = platformOrderService.queryList(qo);
//		System.out.println(JSON.toJSONString(list, true));
//	}
//	
//	/**
//	 * 测试下单
//	 * @throws SlfxMpException 
//	 */
//	@Test
//	public void testOrderTicket() throws SlfxMpException{
//		MPOrderCreateCommand command = new MPOrderCreateCommand();
//		command.setFromClientKey("hsl");;
//		command.setDealerOrderId("007");
//		command.setPrice(120d);
//		command.setPolicyId("53846");
//		command.setNumber(1);
//		MPOrderUserInfoDTO orderUserInfo = new MPOrderUserInfoDTO();
//		orderUserInfo.setChannelUserId("007");
//		orderUserInfo.setIdCardNo("522324197508045617");
//		orderUserInfo.setName("杨思剑");
//		orderUserInfo.setMobile("13634153082");
//		orderUserInfo.setAddress("杭州");
//		orderUserInfo.setPostcode("310000");
//		orderUserInfo.setEmail("zhurz@hgtech365.com");
//		MPOrderUserInfoDTO takeTicketUserInfo = new MPOrderUserInfoDTO();
//		takeTicketUserInfo.setChannelUserId("007");
//		takeTicketUserInfo.setIdCardNo("522324197508045617");
//		takeTicketUserInfo.setName("杨思剑");
//		takeTicketUserInfo.setMobile("13634153082");
//		takeTicketUserInfo.setAddress("杭州");
//		takeTicketUserInfo.setPostcode("310000");
//		takeTicketUserInfo.setEmail("zhurz@hgtech365.com");
//		command.setOrderUserInfo(orderUserInfo);
//		command.setTakeTicketUserInfo(takeTicketUserInfo);
//		command.setTravelDate(DateUtil.parseDateTime("2014-07-31", "yyyy-MM-dd"));
//		command.setBookManIP("127.0.0.1");
//		command.setTraveler(new ArrayList<MPOrderUserInfoDTO>());
//		
//		mpOrderLocalService.apiOrderTicket(command);
//	}
//
//	@Test
//	public void testRedis() {
//		String tcScenicId = "47441";
//		List<TCSupplierPolicySnapshot> list = tcSupplierPolicySnapshotManager
//				.getTcSupplierPolicySnapshots(tcScenicId);
//	}
//
//	@Test
//	public void testQueryUniqueTcPolicy() {
//		TCSupplierPolicySnapshot policySnapshot = tcSupplierPolicySnapshotLocalService
//				.getLast("53846");
//		System.out.println(JSON.toJSONString(policySnapshot, true));
//	}
//
//	/**
//	 * 测试同程政策获取
//	 */
//	@Test
//	public void testQueryTcPolicy() {
//		List<TCSupplierPolicySnapshot> list = tcSupplierPolicySnapshotLocalService
////				.getListByScenicSpotId("e976eb25df6b41e8918257913616392c");
////		.getListByScenicSpotId("feb5549d71554f7784e7b7dfc2473952");
//		.getListByScenicSpotId("1244c040c9f1479d85a31a198fcd9c84");
//		System.out.println(JSON.toJSONString(list, true));
//	}
//
//	/**
//	 * 获取价格日历
//	 */
//	@Test
//	public void testQueryDateSalePrice() {
//		List<DateSalePrice> list=
//		 dateSalePriceLocalService.getDateSalePrice("53846", "1");
//		System.out.println(JSON.toJSONString(list, true));
//		System.out.println(list.size());
//	}
//
//	@Test
//	public void testUpdateScenicSpot() {
//		scenicSpotUpdateJob.updateScenicSpot();
//	}
//
//	@Test
//	public void testHql() {
//		Pagination p = testService.test2();
//		System.out.println(JSON.toJSONString(p, true));
//	}
//
//	@Test
//	public void hello() {
//		System.out.println("hello world");
//	}
//
//	// @Test
//	public void testProjection() {
//		List<hg.system.model.meta.Province> plist = provinceService
//				.queryList(new ProvinceQo());
//		List<hg.system.model.meta.City> clist = cityService
//				.queryList(new CityQo());
//		List<hg.system.model.meta.Area> alist = areaService
//				.queryList(new AreaQo());
//
//		List<AddrProjection> list = new ArrayList<AddrProjection>();
//		for (hg.system.model.meta.Province p : plist) {
//			AddrProjection ap = new AddrProjection();
//			ap.setAddrType(AddrProjection.ADDR_TYPE_PROV);
//			ap.setId(UUIDGenerator.getUUID());
//			ap.setAddrName(p.getName());
//			ap.setAddrCode(p.getCode());
//			ap.setChannelType(AddrProjection.CHANNEL_TYPE_TC);
//			ap.setChannelAddrCode(p.getCode());
//			list.add(ap);
//		}
//		for (hg.system.model.meta.City c : clist) {
//			AddrProjection ap = new AddrProjection();
//			ap.setAddrType(AddrProjection.ADDR_TYPE_CITY);
//			ap.setId(UUIDGenerator.getUUID());
//			ap.setAddrName(c.getName());
//			ap.setAddrCode(c.getCode());
//			ap.setChannelType(AddrProjection.CHANNEL_TYPE_TC);
//			ap.setChannelAddrCode(c.getCode());
//			list.add(ap);
//		}
//		for (hg.system.model.meta.Area a : alist) {
//			AddrProjection ap = new AddrProjection();
//			ap.setAddrType(AddrProjection.ADDR_TYPE_AREA);
//			ap.setId(UUIDGenerator.getUUID());
//			ap.setAddrName(a.getName());
//			ap.setAddrCode(a.getCode());
//			ap.setChannelType(AddrProjection.CHANNEL_TYPE_TC);
//			ap.setChannelAddrCode(a.getCode());
//			list.add(ap);
//		}
//
//		AddrProjection[] addrProjections = new AddrProjection[list.size()];
//		list.toArray(addrProjections);
//		addrProjectionService.saveArray(addrProjections);
//	}
//
////	北京 PEK 沈阳 SHE 福州 FOC 广州 CAN 深圳 SZX
////	上海 SHA 海口 HAK 襄樊 XFN 长沙 CSX 常德 CGD
////	浦东 PVG 丹东 DDG 锦州 JUZ 杭州 HGH 宁波 NGB
////	天津 TSN 南昌 KHN 郑州 CGO 重庆 CKG 长春 CGQ
////	昆明 KMG 青岛 TAO 烟台 YNT 常州 CZX 成都 CTU
////	贵阳 KWE 温州 WNZ 厦门 XMN 太原 TYN 南京 NKG
////	大连 DLX 宜昌 YIH 北海 BHY 晋江 JJN 三亚 SYX
////	合肥 HFE 西安 SIA 武汉 WUH 徐州 XUZ 湛江 ZHA
////	济南 TNA 广汉 GHN 大同 DAT 黄山 TXN 桂林 KWL
////	兰州 LHW 延吉 YNJ 延安 ENY 九江 JIU 安康 AKA
////	南宁 NNG 伯力 KHV 汉中 HZG 长治 CIU 榆林 UYN
////	黄岩 HYN 安庆 AQG 汕头 SWA 赣州 KOW 朝阳 CHG
////	万县 WXN 包头 BAV 南阳 NNY 沙市 SHS 吉林 JIL
////	西昌 XIC 银川 INC 珠海 ZUH 黑河 HEK 衡阳 HNY
////	庐山 LUZ 铜仁 TEN 拉萨 LXA 洛阳 LYA 汉城 SEL
////	西宁 XNN 衢州 JUZ 香港 HKG 临沂 LYI 南充 NAO
////	南通 NTG 达县 DAX 恩施 ENH 澳门 MFM 台北 TPE
////	柳州 LZH 丹山 HSN 宜宾 YBP 梁平 LIA 丽江 LJG
////	赤峰 CIF 绵阳 MIG 广元 GYS 无锡 WUX 吉安 KNC
////	@Test
//	public void aircodeupdate(){
//		String str = "北京 PEK 沈阳 SHE 福州 FOC 广州 CAN 深圳 SZX 上海 SHA 海口 HAK " +
//				"襄樊 XFN 长沙 CSX 常德 CGD 浦东 PVG 丹东 DDG 锦州 JUZ 杭州 HGH 宁波 NGB " +
//				"天津 TSN 南昌 KHN 郑州 CGO 重庆 CKG 长春 CGQ 昆明 KMG 青岛 TAO 烟台 YNT " +
//				"常州 CZX 成都 CTU 贵阳 KWE 温州 WNZ 厦门 XMN 太原 TYN 南京 NKG 大连 DLX " +
//				"宜昌 YIH 北海 BHY 晋江 JJN 三亚 SYX 合肥 HFE 西安 SIA 武汉 WUH 徐州 XUZ " +
//				"湛江 ZHA 济南 TNA 广汉 GHN 大同 DAT 黄山 TXN 桂林 KWL 兰州 LHW 延吉 YNJ " +
//				"延安 ENY 九江 JIU 安康 AKA 南宁 NNG 伯力 KHV 汉中 HZG 长治 CIU 榆林 UYN " +
//				"黄岩 HYN 安庆 AQG 汕头 SWA 赣州 KOW 朝阳 CHG 万县 WXN 包头 BAV 南阳 NNY " +
//				"沙市 SHS 吉林 JIL 西昌 XIC 银川 INC 珠海 ZUH 黑河 HEK 衡阳 HNY 庐山 LUZ " +
//				"铜仁 TEN 拉萨 LXA 洛阳 LYA 汉城 SEL 西宁 XNN 衢州 JUZ 香港 HKG 临沂 LYI " +
//				"南充 NAO 南通 NTG 达县 DAX 恩施 ENH 澳门 MFM 台北 TPE 柳州 LZH 丹山 HSN " +
//				"宜宾 YBP 梁平 LIA 丽江 LJG 赤峰 CIF 绵阳 MIG 广元 GYS 无锡 WUX 吉安 KNC";
//
//		String[] strs = str.split(" ", 0);
//		Map<String, String> map = new HashMap<String, String>();
//		for (int i = 0; i < strs.length; i++) {
//			map.put(strs[i], strs[++i]);
//		}
//		for (Entry<String, String> entry : map.entrySet()) {
//			CityQo qo = new CityQo();
//			qo.setName(entry.getKey());
//			City city = cityService.queryUnique(qo);
//			if (city == null)
//				continue;
//			city.setAirCode(entry.getValue());
//			city.setCityAirCode(entry.getValue());
//			cityService.update(city);
//
//			System.out.println(String.format("更新城市(%s)三字码:%s", entry.getKey(),
//					entry.getValue()));
//		}
//	}
//}
