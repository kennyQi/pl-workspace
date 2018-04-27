package hsl.app.service.local.lineSalesPlan.order;
import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.SpringContextUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.api.base.ApiResponse;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.task.SysProperties;
import hsl.app.dao.lineSalesPlan.LineSalesPlanDao;
import hsl.app.dao.lineSalesPlan.order.LSPOrderDao;
import hsl.app.service.local.line.LineLocalService;
import hsl.app.service.local.lineSalesPlan.LineSalesPlanLocalService;
import hsl.domain.model.lineSalesPlan.LineSalesPlan;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.lineSalesPlan.order.LSPOrderBaseInfo;
import hsl.domain.model.lineSalesPlan.order.LSPOrderPayInfo;
import hsl.domain.model.lineSalesPlan.order.LSPOrderTraveler;
import hsl.pojo.command.lineSalesPlan.UpdateLSPSalesNumCommand;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.ModifyLSPOrderPayInfoCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plfx.api.client.api.v1.xl.request.command.XLCancelLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLModifyLineOrderTravelerApiCommand;
import plfx.api.client.api.v1.xl.response.XLCancelLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLCreateLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLModifyLineOrderTravelerResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.dto.line.LineSnapshotDTO;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import plfx.xl.pojo.dto.order.XLOrderStatusDTO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @类功能说明： 线路销售方案订单LocalService
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:46
 */
@Service
@Transactional
public class LSPOrderLocalService extends BaseServiceImpl<LSPOrder,LSPOrderQO,LSPOrderDao> {
	@Autowired
	private LSPOrderDao lspOrderDao;
	@Autowired
	private LineSalesPlanDao lineSalesPlanDao;
	@Autowired
	private LineSalesPlanLocalService lineSalesPlanLocalService;
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private JedisPool jedisPool;
	@Override
	protected LSPOrderDao getDao() {
		return lspOrderDao;
	}

	private static ExecutorService executor;
	static {
		if(executor==null){
			int cpuCount = Runtime.getRuntime().availableProcessors();
//			//用于处理导出线程  cpu的数量
			int threadCount = cpuCount/2==0?1:cpuCount/2;
			executor=Executors.newFixedThreadPool(threadCount);
		}
	}
	/**
	 * 创建线路销售方案的订单(秒杀)
	 * --->>>秒杀活动是生成一个订单后直接向分销创建订单
	 * @param createLSPOrderCommand
	 * @return
	 */
	public LSPOrderDTO createLSPOrderOfSecKill(CreateLSPOrderCommand createLSPOrderCommand)throws LSPException {
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(createLSPOrderCommand.getLspId());
		lineSalesPlanQO.setFetchLine(true);
		/**秒杀活动查询时加锁--确保查询剩余人数不出现并发问题*/
		lineSalesPlanQO.setIsLock(true);
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);
		if(lineSalesPlan==null){
			throw new LSPException("线路销售方案不存在");
		}
		//查询活动关联的线路是否为空，以及关联的线路是否下架
		if(lineSalesPlan.getLine()==null||lineSalesPlan.getLine().getForSale()==2){
			throw new LSPException("该活动关联线路不符合");
		}
		/**
		 * 判断活动是否已经到达人数限制
		 */
		Integer pnum=lineSalesPlan.getLineSalesPlanSalesInfo().getProvideNum();
		Integer snum=lineSalesPlan.getLineSalesPlanSalesInfo().getSoldNum();
		if(pnum-snum<=0){
			throw new LSPException("该活动订单人数已满");
		}
		/**
		 * 判断添加游客是否符合当前剩余人数
		 */
		Integer num=createLSPOrderCommand.getTravelerList().size();
		if(pnum-(snum+num)<0){
			throw new LSPException("剩余人数不符合当前游客数量");
		}
		Date nowTime=new Date();
		if(nowTime.before(lineSalesPlan.getLineSalesPlanSalesInfo().getBeginDate())){
			throw new LSPException("该活动未开始销售");
		}
		/**
		 * 当前时间如果已经在活动的结束时间之后则活动已经结束
		 */
		if(nowTime.after(lineSalesPlan.getLineSalesPlanSalesInfo().getEndDate())){
			lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END);
			lineSalesPlanDao.update(lineSalesPlan);
			throw new LSPException("活动已经结束");
		}
		//向分销发起创建订单的请求，创建本地订单号
		//生成经销商订单号
		String lineDealerOrderId = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0);
		createLSPOrderCommand.setDealerOrderNo(lineDealerOrderId);
		XLCreateLineOrderApiCommand apiCommand = createLSPOrderCommand.createLineOrderApiCommand();
		String lineDealerId= SysProperties.getInstance().get("clientKey");
		apiCommand.setLineDealerID(lineDealerId);
		//补充一些没有映射到的数据
		//设置经销商订单号
		apiCommand.getBaseInfo().setDealerOrderNo(lineDealerOrderId);
		//设置关联线路ID
		apiCommand.setLineID(lineSalesPlan.getLine().getId());
		LineSnapshotDTO lineSnapshotDTO = new LineSnapshotDTO();
		lineSnapshotDTO.setId(lineSalesPlan.getLine().getLineSnapshotId());
		//设置快照ID
		apiCommand.setLineSnapshot(lineSnapshotDTO);
		HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->createLSPOrder（直销）创建线路订单：通知分销下单" + JSON.toJSONString(apiCommand));

		ApiRequest apiRequest = new ApiRequest("XLCreateLineOrder", ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		XLCreateLineOrderResponse response = slfxApiClient.send(apiRequest, XLCreateLineOrderResponse.class);

		HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->createLSPOrder（直销）创建线路订单：分销下单返回结果" + JSON.toJSONString(response));

		if(response!=null&& StringUtils.isNotBlank(response.getResult())) {
			if (ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())) {
				HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->createLSPOrder（直销）创建线路订单：分销下单失败！！！！");
				//创建失败。返回异常信息
				throw new LSPException(response.getMessage());
			} else {
				/**
				 * 本地保存活动订单
				 */
				LSPOrder lspOrder=new LSPOrder();
				lspOrder.createLSPOrder(createLSPOrderCommand, lineSalesPlan);
				lspOrderDao.save(lspOrder);
				UpdateLSPSalesNumCommand updateLSPSalesNumCommand=new UpdateLSPSalesNumCommand();
				updateLSPSalesNumCommand.setPlanId(lineSalesPlan.getId());
				updateLSPSalesNumCommand.setSalesNum(snum + num);
				lineSalesPlanLocalService.updateLSPSalesNum(updateLSPSalesNumCommand);
				LSPOrderDTO lspOrderDTO= BeanMapperUtils.getMapper().map(lspOrder,LSPOrderDTO.class);
				return  lspOrderDTO;
			}
		}
		return null;
	}
	/**
	 * 创建线路销售方案的订单(团购)
	 * --->>>由于团购活动是因为完全成团后才去分销创建订单。
	 * @param createLSPOrderCommand
	 * @return
	 */
	public LSPOrderDTO createLSPOrderOfGroupBuy(CreateLSPOrderCommand createLSPOrderCommand)throws LSPException {
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(createLSPOrderCommand.getLspId());
		lineSalesPlanQO.setFetchLine(true);
		/**团购活动查询时加锁--确保查询剩余人数不出现并发问题*/
		lineSalesPlanQO.setIsLock(true);
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);
		if(lineSalesPlan==null){
			throw new LSPException("线路销售方案不存在");
		}
		//查询活动关联的线路是否为空，以及关联的线路是否下架
		if(lineSalesPlan.getLine()==null||lineSalesPlan.getLine().getForSale()==2){
			throw new LSPException("该活动关联线路不符合");
		}
		/**
		 * 判断活动是否已经到达人数限制
		 */
		Integer pnum=lineSalesPlan.getLineSalesPlanSalesInfo().getProvideNum();
		Integer snum=lineSalesPlan.getLineSalesPlanSalesInfo().getSoldNum();
		if(lineSalesPlan.getLineSalesPlanStatus().getStatus()>= LineSalesPlanConstant.LSP_STATUS_END||pnum-snum<=0){
			throw new LSPException("该活动订单人数已满");
		}
		/**
		 * 判断添加游客是否符合当前剩余人数
		 */
		Integer num=createLSPOrderCommand.getTravelerList().size();
		if(pnum-(snum+num)<0){
			throw new LSPException("剩余人数不符合当前游客数量");
		}
		Date nowTime=new Date();
		if(nowTime.before(lineSalesPlan.getLineSalesPlanSalesInfo().getBeginDate())){
			throw new LSPException("该活动未开始销售");
		}
		/**
		 * 当前时间如果已经在活动的结束时间之后则活动已经结束
		 */
		if(nowTime.after(lineSalesPlan.getLineSalesPlanSalesInfo().getEndDate())){
			lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END_GROUP_FAIL);
			lineSalesPlanDao.update(lineSalesPlan);
			/**如果团购活动到期，则修改关联订单为下单成功组团失败等待退款*/
			LSPOrderLocalService self = SpringContextUtil.getApplicationContext().getBean(LSPOrderLocalService.class);
			self.modifyLSPOrderForGroupFail(lineSalesPlan.getId());
			throw new LSPException("活动已经结束");
		}
		//向分销发起创建订单的请求，创建本地订单号
		//生成经销商订单号
		String lineDealerOrderId = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0);
		createLSPOrderCommand.setDealerOrderNo(lineDealerOrderId);
		/**
		 * 本地保存活动订单
		 */
		LSPOrder lspOrder=new LSPOrder();
		lspOrder.createLSPOrder(createLSPOrderCommand, lineSalesPlan);
		lspOrderDao.save(lspOrder);
		LSPOrderDTO lspOrderDTO= BeanMapperUtils.getMapper().map(lspOrder, LSPOrderDTO.class);
		return  lspOrderDTO;
	}

	/**
	 * 检查活动是否组团成功---dealerOrderNo--经销商订单号
	 * @param dealerOrderNo
	 */
	public void checkLspISGroupSuc(String dealerOrderNo) throws LSPException {
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setDealerOrderNo(dealerOrderNo);
		LSPOrder lspOrder=lspOrderDao.queryUnique(lspOrderQO);
		if(lspOrder==null){
			throw new LSPException("查询订单为空");
		}
		/**单独活动查询加锁--确保查询剩余人数不出现并发问题*/
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(lspOrder.getLineSalesPlan().getId());
		lineSalesPlanQO.setIsLock(true);
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);
		if(lspOrder.getOrderBaseInfo().getOrderType().equals(LSPOrderBaseInfo.LSP_ORDER_TYPE_SECKILL)||lineSalesPlan==null){
			throw new LSPException("订单异常");
		}
		Integer pnum=lineSalesPlan.getLineSalesPlanSalesInfo().getProvideNum();
		Integer snum=lineSalesPlan.getLineSalesPlanSalesInfo().getSoldNum();
		Integer num=lspOrder.getTravelerList().size();
		HgLogger.getInstance().info("chenxy","检查团购是否成功-->ID-->"+lspOrder.getOrderBaseInfo().getDealerOrderNo()+"pnum-->"+pnum+"--snum-->"+snum+"--num-->"+num);
		if(pnum-(snum+num)==0){
			HgLogger.getInstance().info("chenxy","检查团购是否成功-->成功-->ID-->"+lspOrder.getOrderBaseInfo().getDealerOrderNo()+"pnum-->"+pnum+"--snum-->"+snum+"--num-->"+num);
			/**----修改团购活动组团成功已结束状态-----*/
			lineSalesPlan.getLineSalesPlanSalesInfo().setSoldNum(snum + num);
			lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END_GROUP_SUCC);
			lineSalesPlanDao.update(lineSalesPlan);
			/***------------------如果团购活动组团成功，则启动线程向分销创建订单--Start ----------------***/
			if(lineSalesPlan.getLineSalesPlanStatus().getStatus().equals(LineSalesPlanConstant.LSP_STATUS_END_GROUP_SUCC)){
				LSPOrderQO lspOrderQO1=new LSPOrderQO();
				lspOrderQO1.setPlanId(lineSalesPlan.getId());
				lspOrderQO1.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
				List<LSPOrder> lspOrders=lspOrderDao.queryList(lspOrderQO1);
				HgLogger.getInstance().info("chenxy","团购是否成功-->成功-->支付成功的订单数-->"+lspOrders.size());
				/**------将团购订单请求分销时修改为下单成功组团成功---------*/
				for (LSPOrder lspOrder1:lspOrders){
					lspOrder1.getOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_SUC);
					for (LSPOrderTraveler lspOrderTraveler:lspOrder1.getTravelers()){
						lspOrderTraveler.getLspOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_SUC);
					}
					lineSalesPlanDao.update(lspOrder1);
				}
				Future<Integer> future = executor.submit(new CreateFxLineOrderByGroup(lspOrders));
				/**
				 *　Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
				 *  必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
				 */
	//			try {
	//				future.get();
	//			} catch (InterruptedException e) {
	//				e.printStackTrace();
	//			} catch (ExecutionException e) {
	//				e.printStackTrace();
	//			}
			}
			/***------------------如果团购活动组团成功，则启动线程向分销创建订单--End ----------------***/
		}else if(pnum-(snum+num)>0){
			/**---仅仅修改团购活动的已售数量，活动状态是根据支付成功后判断组团成功与否-------*/
			lineSalesPlan.getLineSalesPlanSalesInfo().setSoldNum(snum + num);
			lineSalesPlanDao.update(lineSalesPlan);
		}else{
			/**如果已经 成团，但是用户且已经支付，则把订单状态修改为组团失败，直接退款*/
			lspOrder.getOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
			for (LSPOrderTraveler lspOrderTraveler:lspOrder.getTravelers()){
				lspOrderTraveler.getLspOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
			}
			lspOrderDao.update(lspOrder);
		}
	}
	/**
	 * 测试方法(实际项目中没用)
	 * @param id
	 */
	public  void  testFuture(String id){
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setPlanId(id);
//		lspOrderQO.setOrderType(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY);
		List<LSPOrder> lspOrders=lspOrderDao.queryList(lspOrderQO);
//		List<LSPOrder> lspOrders=new ArrayList<LSPOrder>();
//		int threadCount = ((ThreadPoolExecutor)executor).getActiveCount();
//		int corePoolSize = ((ThreadPoolExecutor)executor).getCorePoolSize();
//		BlockingQueue<Runnable> runnables = ((ThreadPoolExecutor)executor).getQueue();
//		System.out.println("当前活动的线程数--->>>>" + threadCount + "线程池的核心池大小------>>>>:" + corePoolSize + "当前队列数量----->>>>" + runnables.size() + "队列的可用大小----->>" + runnables.remainingCapacity());
		Future<Integer> future = executor.submit(new CreateFxLineOrderByGroup(lspOrders));
	}
	/**
	* @类功能说明：团购成团后，批量向分销请求创建订单
	* @类修改者：
	* @公司名称： 浙江票量云科技有限公司
	* @部门： 技术部
	* @作者： chenxy
	* @创建时间：  2015-12-05 12:00:37
	* @版本： V1.0
	*/
	private class CreateFxLineOrderByGroup implements Callable<Integer> {
		private List<LSPOrder> lspOrders;
		public CreateFxLineOrderByGroup(List<LSPOrder> lspOrders){
			this.lspOrders=lspOrders;
		}
		@Override
		public Integer call() throws Exception {
			return this.createFxLineOrderByGroup(lspOrders);
		}
		private Integer createFxLineOrderByGroup(List<LSPOrder> lspOrders){
			System.out.println("执行线程内部------->>>>>>>");
			Integer sucNum=0;
			try {
				for(LSPOrder lspOrder:lspOrders){
					/** ---将团购活动订单转化为请求分销的apiCommand---
					 * 但是请求时将apiCommand的订单状态修改为 下单成功未占位 @see LineSalesPlanConstant
					 * 因为分销不区分活动订单和正常订单，没有相应的组团的订单状态
					 */
					XLCreateLineOrderApiCommand apiCommand=lspOrder.convertXLCreateLineOrderApiCommand();
					String lineDealerId= SysProperties.getInstance().get("clientKey");
					apiCommand.setLineDealerID(lineDealerId);
					HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->CreateFxLineOrderByGroup（直销）创建线路订单：通知分销下单" + JSON.toJSONString(apiCommand));
					ApiRequest apiRequest = new ApiRequest("XLCreateLineOrder", ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
					XLCreateLineOrderResponse response = slfxApiClient.send(apiRequest, XLCreateLineOrderResponse.class);
					HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->CreateFxLineOrderByGroup（直销）创建线路订单：分销下单返回结果"+JSON.toJSONString(response));
					if(response!=null&& StringUtils.isNotBlank(response.getResult())) {
						if (ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())) {
							HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->CreateFxLineOrderByGroup（直销）创建线路订单：分销下单失败！！！！");
							//创建失败。返回异常信息
							/**
							 * 预留内容，失败后在订单创建标识位，设置是分销创建订单失败
							 */
						} else {
							sucNum++;
						}
					}
					updateStatusToApi(lspOrder);
				}
			}catch (Exception e){
				HgLogger.getInstance().error("chenxy","活动请求分销创建活动订单出错"+HgLogger.getStackTrace(e));
				e.printStackTrace();
			}
			System.out.println("执行线程内部------->>>>>>>完成");
			return sucNum;
		}
	}
	/**
	 * 修改LSP订单状态(全部修改订单状态，循环修改订单下所有游客的订单状态)
	 * @param updateLSPOrderStatusCommand
	 * @throws LSPException
	 */
	public void updateLSPOrderStatus(UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand) throws LSPException {
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setId(updateLSPOrderStatusCommand.getOrderId());
		LSPOrder lspOrder=lspOrderDao.queryUnique(lspOrderQO);
		if(lspOrder==null){
			throw new LSPException("订单为空");
		}
		if(updateLSPOrderStatusCommand.getStatus()!=null){
			lspOrder.getOrderStatus().setOrderStatus(updateLSPOrderStatusCommand.getStatus());
		}
		if(updateLSPOrderStatusCommand.getPayStatus()!=null){
			lspOrder.getOrderStatus().setPayStatus(updateLSPOrderStatusCommand.getPayStatus());
		}
		for (LSPOrderTraveler lspOrderTraveler:lspOrder.getTravelers()){
			if(updateLSPOrderStatusCommand.getStatus()!=null){
				lspOrderTraveler.getLspOrderStatus().setOrderStatus(updateLSPOrderStatusCommand.getStatus());
			}
			if(updateLSPOrderStatusCommand.getPayStatus()!=null){
				lspOrderTraveler.getLspOrderStatus().setPayStatus(updateLSPOrderStatusCommand.getPayStatus());
			}
		}
		/**当状态是取消状态以及订单类型是 秒杀活动时请求分销取消订单*/
		if(LSPOrderBaseInfo.LSP_ORDER_TYPE_SECKILL.equals(lspOrder.getOrderBaseInfo().getOrderType())
				&&LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL.equals(updateLSPOrderStatusCommand.getStatus())){
			/**
			 * 取消订单后，更新关联的销售方案的销售数量以及相应的活动状态,只修改秒杀订单
			 */
			HgLogger.getInstance().info("chenxy","updateLSPOrderStatus-->修改秒杀活动"+ JSON.toJSONString(lspOrder.getOrderBaseInfo().getDealerOrderNo()));
			Integer num=lspOrder.getTravelerList().size();
			Integer snum=lspOrder.getLineSalesPlan().getLineSalesPlanSalesInfo().getSoldNum()-num;
			UpdateLSPSalesNumCommand updateLSPSalesNumCommand=new UpdateLSPSalesNumCommand();
			updateLSPSalesNumCommand.setPlanId(lspOrder.getLineSalesPlan().getId());
			updateLSPSalesNumCommand.setSalesNum(snum);
			lineSalesPlanLocalService.updateLSPSalesNum(updateLSPSalesNumCommand);
			updateStatusToApi(lspOrder);
		}
		/**当支付状态是支付成功时以及订单类型是 秒杀活动时请求分销同步状态*/
		if(LSPOrderBaseInfo.LSP_ORDER_TYPE_SECKILL.equals(lspOrder.getOrderBaseInfo().getOrderType())
				&&LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS.equals(updateLSPOrderStatusCommand.getPayStatus())){
			updateStatusToApi(lspOrder);
		}
		lspOrderDao.update(lspOrder);
	}
	/**
	 * 修改订单支付信息
	 * 修改支付信息的时候，确保是支付成功后异步通知时修改。
	 * 此时直接把订单支付状态修改成支付成功
	 * @param modifyLSPOrderPayInfoCommand
	 */
	public void  modifyLSPOrderPayInfo(ModifyLSPOrderPayInfoCommand modifyLSPOrderPayInfoCommand)throws Exception{
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setId(modifyLSPOrderPayInfoCommand.getOrderId());
		LSPOrder lspOrder=lspOrderDao.queryUnique(lspOrderQO);
		if(lspOrder==null){
			throw new LSPException("订单为空");
		}
		LSPOrderPayInfo lspOrderPayInfo=lspOrder.getOrderPayInfo();
		if(null==lspOrderPayInfo){
			lspOrderPayInfo=new LSPOrderPayInfo();
		}
		if(StringUtils.isNotBlank(modifyLSPOrderPayInfoCommand.getBuyerEmail())){
			lspOrderPayInfo.setBuyerEmail(modifyLSPOrderPayInfoCommand.getBuyerEmail());
		}
		if(StringUtils.isNotBlank(modifyLSPOrderPayInfoCommand.getPayTradeNo())){
			lspOrderPayInfo.setPayTradeNo(modifyLSPOrderPayInfoCommand.getPayTradeNo());
		}
		if(modifyLSPOrderPayInfoCommand.getPayPrice()>0){
			lspOrderPayInfo.setPayPrice(modifyLSPOrderPayInfoCommand.getPayPrice());
		}
		lspOrderPayInfo.setPayDate(new Date());
		lspOrder.setOrderPayInfo(lspOrderPayInfo);
		lspOrder.getOrderStatus().setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
		for (LSPOrderTraveler lspOrderTraveler:lspOrder.getTravelers()){
			lspOrderTraveler.getLspOrderStatus().setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
		}
		HgLogger.getInstance().info("chenxy","支付成功修改支付信息--->"+JSON.toJSONString(lspOrder));
		lspOrderDao.update(lspOrder);
	}

	/**
	 * 组团失败修改活动关联的订单状态为 下单成功组团失败  lspId--活动ID
	 * 组团失败==团购人数不够以及时间到活动结束时间
	 * @param lspId
	 */
	public void modifyLSPOrderForGroupFail(String lspId){
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setPlanId(lspId);
		List<LSPOrder> lspOrders=lspOrderDao.queryList(lspOrderQO);
		for (LSPOrder lspOrder:lspOrders){
			lspOrder.getOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
			for (LSPOrderTraveler lspOrderTraveler:lspOrder.getTravelers()){
				lspOrderTraveler.getLspOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
			}
			lspOrderDao.update(lspOrder);
		}
	}

	/**
	 * 修改状态请求分销
	 * @param lspOrder
	 * @throws LSPException
	 */
	public void updateStatusToApi(LSPOrder lspOrder) throws LSPException {
		//通知分销修改订单状态
		XLModifyLineOrderTravelerApiCommand apiCommand=new XLModifyLineOrderTravelerApiCommand();
		apiCommand.setDealerOrderNo(lspOrder.getOrderBaseInfo().getDealerOrderNo());

		//由于分销和直销的线路定单状态命名不同,设置分销线路订单状态
		Set<LineOrderTravelerDTO> travelerSet=BeanMapperUtils.getMapper().mapAsSet(lspOrder.getTravelers(), LineOrderTravelerDTO.class);
		XLOrderStatusDTO xlOrderStatus=new XLOrderStatusDTO();
		//如果是团购订单则修改为 分销通用状态
		if(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY.equals(lspOrder.getOrderBaseInfo().getOrderType())
				&&LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_SUC.equals(lspOrder.getOrderStatus().getOrderStatus())){
				xlOrderStatus.setStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE);
				xlOrderStatus.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
		}else{
			xlOrderStatus.setStatus(lspOrder.getOrderStatus().getOrderStatus());
			xlOrderStatus.setPayStatus(lspOrder.getOrderStatus().getPayStatus());
		}

		List<LineOrderTravelerDTO> orderTravelerDTOs =new ArrayList<LineOrderTravelerDTO>();
		for(LineOrderTravelerDTO traveler:travelerSet){
			traveler.setXlOrderStatus(xlOrderStatus);
			traveler.setLineOrder(null);
			orderTravelerDTOs.add(traveler);
		}
		apiCommand.setTravelers(orderTravelerDTOs);

		HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->通知分销修改线路订单游客订单状态：" + JSON.toJSONString(apiCommand));
		ApiRequest apiRequest;
		if(lspOrder.getOrderStatus().getOrderStatus().equals(LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL)){
			List<LineOrderTravelerDTO> travels=apiCommand.getTravelers();
			XLCancelLineOrderApiCommand apiCommands=new XLCancelLineOrderApiCommand();
			StringBuilder lineOrderTravelers=new StringBuilder();
			//拼接XLCancelLineOrderApiCommand游客的ID
			for(LineOrderTravelerDTO travel:travels){
				if(StringUtils.isBlank(lineOrderTravelers.toString())){
					lineOrderTravelers.append(travel.getId());
				}else{
					lineOrderTravelers.append(","+travel.getId());
				}
			}
			apiCommands.setLineOrderID(lspOrder.getOrderBaseInfo().getDealerOrderNo());
			apiCommands.setLineOrderTravelers(lineOrderTravelers.toString());
			apiRequest = new ApiRequest("XLCancleLineOrder",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommands);
			XLCancelLineOrderResponse response = slfxApiClient.send(apiRequest, XLCancelLineOrderResponse.class);
			HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->通知分销取消线路订单游客状态：返回结果"+JSON.toJSONString(response));
			if(response!=null&&StringUtils.isNotBlank(response.getResult())){
				if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){
					//创建失败。返回异常信息
					throw new LSPException(response.getMessage());
				}
			}
		}else{
			apiRequest = new ApiRequest("XLModifyLineOrderTraveler",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
			XLModifyLineOrderTravelerResponse response = slfxApiClient.send(apiRequest, XLModifyLineOrderTravelerResponse.class);
			HgLogger.getInstance().info("chenxy", "LSPOrderLocalService->通知分销修改线路订单游客状态：返回结果"+JSON.toJSONString(response));
			if(response!=null&&StringUtils.isNotBlank(response.getResult())){
				if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){
					//创建失败。返回异常信息
					throw new LSPException(response.getMessage());
				}
			}
		}
	}

	/**
	 * 退款团购订单
	 * @param result_details
	 * @throws LSPException
	 */
	public void refundLSPOrder(String result_details)throws LSPException{
		if(StringUtils.isBlank(result_details))
			return ;
		String[] trades=result_details.split("#");
		for(String trade:trades) {
			String str = trade.split("\\$")[0];//获得交易退款数据集
			String[] data = str.split("\\^");//按照 "原付款支付宝交易号^退款总金额^退款状态" 格式分割为数组
			String tradeno = data[0];
			String sum = data[1];
			String status = data[2];
			HgLogger.getInstance().info("chenxy", "LSPOrderServiceImpl->refundLSPOrder->付款支付宝交易号:" + tradeno);
			HgLogger.getInstance().info("chenxy", "LSPOrderServiceImpl->refundLSPOrder->退款总金额:" + sum);
			HgLogger.getInstance().info("chenxy", "LSPOrderServiceImpl->refundLSPOrder->退款状态" + status);
			LSPOrderQO qo=new LSPOrderQO();
			qo.setPayTradeNo(tradeno);
			LSPOrder lspOrder=lspOrderDao.queryUnique(qo);
			if(lspOrder==null){
				throw new LSPException("订单为空");
			}
			LSPOrderPayInfo lspOrderPayInfo=lspOrder.getOrderPayInfo();
			Double refundPrice=Double.valueOf(sum);
			if(refundPrice>0){
				lspOrderPayInfo.setRefundPrice(refundPrice);
			}
			lspOrderPayInfo.setRefundDate(new Date());
			lspOrder.getOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_REFUND_SUCCESS);
			lspOrder.getOrderStatus().setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_REFUND_SUCCESS);
			for (LSPOrderTraveler lspOrderTraveler:lspOrder.getTravelers()){
				lspOrderTraveler.getLspOrderStatus().setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_REFUND_SUCCESS);
				lspOrderTraveler.getLspOrderStatus().setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_REFUND_SUCCESS);
			}
			lspOrderDao.update(lspOrder);
		}
	}
	/**
	 * @功能说明：从redis拿取序列号，也许该加个同步锁
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 chenxy
	 * @创建时间：2015年2月28日
	 */
	public int getOrderSequence() {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			String value = jedis.get("zzpl_lsp_sequence");
			String date = jedis.get("zzpl_lsp_sequence_date");
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(this.getDateString("yyyyMMdd"))) {
				value = "0";
			}
			int v = Integer.parseInt(value);
			if (v >= 9999) {
				v = 0;
			}
			v++;
			jedis.set("zzpl_lsp_sequence", String.valueOf(v));
			jedis.set("zzpl_lsp_sequence_date",this.getDateString("yyyyMMdd"));
			return v;
		} catch (Exception e){
			HgLogger.getInstance().error("chenxy","创建LSP订单流水号出错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			return 0;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
}
