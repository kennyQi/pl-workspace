package service.test;
import hg.common.util.SpringContextUtil;
import hsl.app.service.local.lineSalesPlan.order.LSPOrderLocalService;
import hsl.domain.model.lineSalesPlan.LineSalesPlanBaseInfo;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.lineSalesPlan.order.LSPOrderBaseInfo;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.ModifyLSPOrderPayInfoCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderTravelerDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/3 11:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestLSP {
	@Autowired
	private LSPOrderLocalService lspOrderLocalService;
	@Autowired
	private LineSalesPlanService lineSalesPlanService;
	@Autowired
	private LSPOrderService lspOrderService;

	/**
	 * 测试创建LSP订单
	 */
	@Test
	public void testCreateorder(){
		CreateLSPOrderCommand createLSPOrderCommand=new CreateLSPOrderCommand();
		createLSPOrderCommand.setLinkName("cccc");
		createLSPOrderCommand.setLinkMobile("18638236219");
		createLSPOrderCommand.setEmail("5950355@qq.com");
		createLSPOrderCommand.setAdultNo(1);
		createLSPOrderCommand.setAdultUnitPrice(99.00);
		createLSPOrderCommand.setOrderType(1);
		createLSPOrderCommand.setChildNo(0);
		createLSPOrderCommand.setChildUnitPrice(0.00);
		createLSPOrderCommand.setLoginName("admin");
		createLSPOrderCommand.setLspId("8585314cdadc43c3bf78cb27789dc632");
		createLSPOrderCommand.setOrderType(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY);
		createLSPOrderCommand.setMobile("18638236219");
		createLSPOrderCommand.setSalePrice(99.00);
		createLSPOrderCommand.setTravelDate(new Date());
		createLSPOrderCommand.setSupplierPrice(100.00);
		createLSPOrderCommand.setUserId("edcaa54d670042ec8663bba3ae10e5f2");
		List<LSPOrderTravelerDTO> lspOrderTravelerDTOs=new ArrayList<LSPOrderTravelerDTO>();
		LSPOrderTravelerDTO lspOrderTravelerDTO=new LSPOrderTravelerDTO();
		lspOrderTravelerDTO.setIdNo("411023198907202515");
		lspOrderTravelerDTO.setName("陈鑫亚");
		lspOrderTravelerDTO.setMobile("18638236219");
		lspOrderTravelerDTO.setSingleSalePrice(100.00);
		lspOrderTravelerDTO.setSingleBargainMoney(100.00);
		lspOrderTravelerDTOs.add(lspOrderTravelerDTO);
		createLSPOrderCommand.setTravelerList(lspOrderTravelerDTOs);
		XLCreateLineOrderApiCommand apiCommand = createLSPOrderCommand.createLineOrderApiCommand();
		System.out.println(apiCommand);
		try {
			lspOrderLocalService.createLSPOrderOfGroupBuy(createLSPOrderCommand);
		} catch (LSPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试线程
	 */
	@Test
	public void testFuture(){
		try {
			lspOrderLocalService.testFuture("e3debef146644fac90d20fb065a993a0");
//			int size=5;
//			for (int i=1;i<=size;i++){
////				if(i!=0){
////					try {
////						Thread.sleep(1000*1);
////					} catch (InterruptedException e) {
////						e.printStackTrace();
////					}
////				}
//				System.out.println("执行第"+i+"线程----------->>>>");
//				lspOrderLocalService.testFuture("7e7dcc372b924e1f93914e6a3112ed1f");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试查询线路销售方案
	 */
	@Test
	public void testqueryLSP(){
//		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
//		lineSalesPlanQO.setFetchLine(true);
		Integer[] statusArray=new Integer[]{LineSalesPlanConstant.LSP_STATUS_NOBEGIN,
				LineSalesPlanConstant.LSP_STATUS_SALESING,LineSalesPlanConstant.LSP_STATUS_END,
				LineSalesPlanConstant.LSP_STATUS_END_GROUP_FAIL,LineSalesPlanConstant.LSP_STATUS_END_GROUP_SUCC} ;

		LineSalesPlanQO groupQo=new LineSalesPlanQO();
		groupQo.setStatusArray(statusArray);
		groupQo.setShowStatus(LineSalesPlanConstant.LSP_SHOW_STATUS_SHOW);
		groupQo.setOrderByPriority(true);
		groupQo.setFetchLine(true);
		groupQo.setPlanType(LineSalesPlanBaseInfo.PLANTYPE_GROUP);

		List<LineSalesPlanDTO> groupList=this.lineSalesPlanService.queryList(groupQo);
//		List<LineSalesPlanDTO> lineSalesPlanDTOs=lineSalesPlanService.queryList(lineSalesPlanQO);
		System.out.println(groupList);
	}

	/**
	 * 测试查询lsp订单
	 */
	@Test
	public void testqueryLSPOrder(){
		LSPOrderQO lspOrderQO=new LSPOrderQO();
//		lspOrderQO.setId("7ad73e393bf34a74b8ca2a2fca29af7b");
//		lspOrderQO.setFetchTraveler(true);
//		lspOrderQO.setTravelerIdNo("331023199102060101");
		lspOrderQO.setPlanId("582b35896ec543e29bd8a9016dbd7daa");
		lspOrderQO.setOrderType(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY);
//		lspOrderQO.setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
//		lspOrderQO.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
		List<LSPOrder> lspOrders=lspOrderLocalService.queryList(lspOrderQO);
		System.out.println(lspOrders.size());
	}
	@Test
	 public  void testUpdateStatus(){
//		UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand=new UpdateLSPOrderStatusCommand();
//		updateLSPOrderStatusCommand.setOrderId("5c5f9f904fef4c04b2a075437f009c0f");
//		updateLSPOrderStatusCommand.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
//		try {
//			lspOrderService.updateLSPOrderStatus(updateLSPOrderStatusCommand);
//		} catch (LSPException e) {
//			e.printStackTrace();
//		}
		ModifyLSPOrderPayInfoCommand cmd=new ModifyLSPOrderPayInfoCommand();
		cmd.setOrderId("3464c092cff1414292a146ffed2e8fa9");
		cmd.setBuyerEmail("617682098@qq.com");
		cmd.setPayTradeNo("2015121600001000210067054502");
		cmd.setPayPrice(Double.valueOf("1"));
		/*try {
			lspOrderService.modifyLSPOrderPayInfo(cmd);
		} catch (LSPException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	@Test
	public  void testGetOrderSequence(){
		int size=5;
		for (int i=1;i<=size;i++){
			if(i!=0){
				try {
					Thread.sleep(1000*1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("执行第" + i + "线程----------->>>>");
			Thread thread=new Thread();
			thread.start();
			lspOrderLocalService.getOrderSequence();
		}
	}
}
