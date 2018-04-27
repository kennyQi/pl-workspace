package hsl.line;

import java.util.Date;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.service.local.line.LineLocalService;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.order.LineOrder;
import hsl.pojo.command.line.InitLineCommand;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.spi.inter.line.HslLineService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.xl.XLLineApiQO;
import slfx.api.v1.response.xl.XLQueryLineResponse;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.pojo.system.LineMessageConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LineTest {
	@Autowired
	private LineLocalService lineLocalService;
	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private HslLineService hslLineService;
	
	@Autowired
	private SlfxApiClient slfxApiClient;
	
//	@Test
	public void initLine(){
		try {
			lineLocalService.initLineData(new InitLineCommand());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getOne(){
		XLLineApiQO qo = new XLLineApiQO();
		qo.setId("0712856fff8347e99492c636c505818b");
		ApiRequest apiRequest = new ApiRequest("XLQueryLine",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
		XLQueryLineResponse response = slfxApiClient.send(apiRequest, XLQueryLineResponse.class);
		System.out.println(1);
	}
	
//	@Test
	public void getList(){
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(5);
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setGetDateSalePrice(true);
		hslLineQO.setGetPictures(true);
//		hslLineQO.setDayCount(10);
//		hslLineQO.setScope(1);
//		hslLineQO.setMaxPrice(15);
//		hslLineQO.setMinPrice(5);
//		hslLineQO.setLevel("2");
//		hslLineQO.setBeginDateTime(new Date());
		pagination.setCondition(hslLineQO);
		pagination.setCheckPassLastPageNo(false);
		pagination = hslLineService.queryPagination(pagination);
		System.out.println("查询的线路数量："+pagination.getList().size());
		for (Object lineDTO : pagination.getList()) {
			LineDTO line=(LineDTO) lineDTO;
			System.out.println("线路基本信息"+line.getBaseInfo());
			System.out.println("线路价格日历"+line.getDateSalePriceList().size());
			System.out.println("线路图片列表"+line.getFolderList().size());
			System.out.println("线路最低价格"+line.getMinPrice());
			System.out.println("线路支付信息"+line.getPayInfo());
			System.out.println("线路行程信息"+line.getRouteInfo());
		}
	}
	
//	@Test
//	@Transactional
	public void getListLocal(){
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(10);
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId("0712856fff8347e99492c636c505818b");
		pagination.setCondition(hslLineQO);
		pagination = lineLocalService.queryPagination(pagination);
		Line line = (Line)pagination.getList().get(0);
		System.out.println(1);
	}
	
//	@Test
	public void deleteLine(){
		lineLocalService.deleteById("0712856fff8347e99492c636c505818b");
	}
	
//	@Test
	public void notSale() throws Exception{
		XLUpdateLineMessageApiCommand command = new XLUpdateLineMessageApiCommand();
		command.setLineId("2b3053f216854008b8a0a1054628f02d");
		command.setStatus(LineMessageConstants.AUDIT_DOWN);
		hslLineService.updateLineData(command);
	}
//	@Test
	public void queryLineOrder() throws Exception{
		HslLineOrderQO qo=new HslLineOrderQO();
		qo.setLineNumber("0051");
		List<LineOrder> lineOrders=lineOrderLocalService.queryList(qo);
		for(LineOrder l:lineOrders){
			System.out.println("年哈   滴答滴答滴答滴答"+l.getLineSnapshot().getLine().getBaseInfo().getNumber());
		}
		
	}
}
