//import hg.common.page.Pagination;
//import hg.common.util.DateUtil;
//import hg.common.util.UUIDGenerator;
//import hg.log.util.HgLogger;
//import hg.system.cache.AddrProjectionCacheManager;
//import hg.system.model.meta.AddrProjection;
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
//import java.util.List;
//
//import oracle.net.aso.e;
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
//@ContextConfiguration(locations = { "classpath*:applicationContext-log-test.xml" })
//public class TestLog {
//	
//	@Autowired
//	private HgLogger hgLogger;
//	
//	@Test
//	public void test() {
//		hgLogger.debug("yuxx", "成功");
//		try {
//			HgLogger hgLogger = null;
//			hgLogger.error("", "");
//		} catch (Exception e) {
//			hgLogger.error(TestLog.class, "yuxx", "成功", e, "机票下单", "接口调用");
//		}
//				
//	}
//}
