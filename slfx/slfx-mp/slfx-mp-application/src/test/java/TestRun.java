import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import slfx.mp.tcclient.tc.dto.order.CancelSceneryOrderDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.order.response.ResultCancelSceneryOrder;
import slfx.mp.tcclient.tc.service.TcClientService;

import com.alibaba.fastjson.JSON;

public class TestRun {
	
	@SuppressWarnings("resource")
	public static void appTestRun() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext-slfx-mp-test.xml");
		TcClientService service = ctx.getBean(TcClientService.class);

		// 取消订单
		CancelSceneryOrderDto dto = new CancelSceneryOrderDto();
		dto.setCancelReason(1);
		dto.setSerialId("st53u45c73210037a645dd");
		Response<ResultCancelSceneryOrder> response = service
				.cancelSceneryOrder(dto);
		System.out.println(JSON.toJSONString(response, true));
	}
	
	public static void main(String[] args) {
//		appTestRun();
//		TcClientService service = new TcClientServiceImpl();
//		SceneryDetailDto dto = new SceneryDetailDto();
//		dto.setSceneryId(sceneryId);
//		service.getSceneryDetail();
//		
//		// 景点列表
//		SceneryDto dto = new SceneryDto();
//		dto.setKeyword("西湖");
//		Response<ResultSceneryList> response = service.getSceneryList(dto);
//		System.out.println(JSON.toJSONString(response, true));
//
		// 交通指南
		// SceneryTrafficInfoDto dto = new SceneryTrafficInfoDto();
		// dto.setSceneryId("47441");
		// Response<ResultSceneryTrafficInfo> response = service
		// .getSceneryTrafficInfo(dto);
		// System.out.println(JSON.toJSONString(response, true));

		// 取消订单
//		CancelSceneryOrderDto dto = new CancelSceneryOrderDto();
//		dto.setCancelReason(1);
//		dto.setSerialId("st53u45c73210037a645dd");
//		Response<ResultCancelSceneryOrder> response = service
//				.cancelSceneryOrder(dto);
//		System.out.println(JSON.toJSONString(response, true));

		// // 查询景区政策
		// SceneryPriceDto dto = new SceneryPriceDto();
		// dto.setSceneryIds("10593");
		// Response<ResultSceneryPrice> response = service.getSceneryPrice(dto);
		// System.out.println(JSON.toJSONString(response, true));
	}
}
