package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.common.util.EntityConvertUtils;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.DateSalePriceDAO;
import slfx.xl.app.dao.LineDAO;
import slfx.xl.app.dao.LineOrderDAO;
import slfx.xl.app.dao.LineOrderPaymentDAO;
import slfx.xl.app.dao.LineOrderTravelerDAO;
import slfx.xl.app.dao.LineSnapshotDAO;
import slfx.xl.app.dao.SalePolicyLineDAO;
import slfx.xl.app.dao.SalePolicySnapshotDAO;
import slfx.xl.domain.model.line.DateSalePrice;
import slfx.xl.domain.model.line.Line;
import slfx.xl.domain.model.line.LineSnapshot;
import slfx.xl.domain.model.order.LineOrder;
import slfx.xl.domain.model.order.LineOrderPayment;
import slfx.xl.domain.model.order.LineOrderTraveler;
import slfx.xl.domain.model.order.XLOrderStatus;
import slfx.xl.domain.model.salepolicy.SalePolicy;
import slfx.xl.domain.model.salepolicy.SalePolicySnapshot;
import slfx.xl.pojo.command.line.UpdateLineOrderStatusCommand;
import slfx.xl.pojo.command.order.CancleLineOrderCommand;
import slfx.xl.pojo.command.order.ChangeLineOrderStatusCommand;
import slfx.xl.pojo.command.order.CreateLineOrderCommand;
import slfx.xl.pojo.command.order.ModifyLineOrderTravelerCommand;
import slfx.xl.pojo.command.order.ModifyPaymentLineOrderCommand;
import slfx.xl.pojo.command.pay.BatchPayLineOrderCommand;
import slfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.DateSalePriceQO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.pojo.qo.LineSnapshotQO;
import slfx.xl.pojo.qo.SalePolicyLineQO;
import slfx.xl.pojo.qo.SalePolicySnapshotQO;
import slfx.xl.pojo.system.LineOrderConstants;
import slfx.xl.pojo.system.SalePolicyConstants;
import slfx.xl.pojo.system.XLOrderStatusConstant;

import com.alibaba.fastjson.JSON;

/**
 * 
 *@类功能说明：线路订单LOCALSERVICE(操作数据库)实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年12月9日下午3:22:46
 *
 */
@Service
@Transactional
public class LineOrderLocalService extends BaseServiceImpl<LineOrder, LineOrderQO, LineOrderDAO>{
	
	

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	private LineDAO lineDAO;
	
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	
	@Autowired
	private LineOrderDAO lineOrderDAO;
	
	@Autowired
	private LineOrderTravelerDAO lineOrderTravelerDAO;
	
	@Autowired
	private DateSalePriceDAO dateSalePriceDAO;
	
	@Autowired
	private SalePolicySnapshotDAO salePolicySnapshotDAO;
	
	@Autowired
	private LineOrderPaymentDAO lineOrderPaymentDAO;
	
	@Autowired
	private SalePolicyLineDAO salePolicyLineDAO;
	
	
	@Override
	protected LineOrderDAO getDao() {
		return lineOrderDAO;
	}
	
	/**
	 * @方法功能说明：取消订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日上午10:55:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean cancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException{
		
		LineOrder lineOrder = lineOrderDAO.get(command.getLineOrderID());
		
		if(lineOrder == null){
			throw new SlfxXlException(SlfxXlException.RESULT_LINEORDER_NOT_FOUND,"线路订单不存在");
		}
		Line line = lineDAO.get(lineOrder.getLineSnapshot().getLine().getId());
		if(StringUtils.isBlank(command.getLineOrderTravelers())){
			throw new SlfxXlException(SlfxXlException.CANCLE_LINEORDER_WITHOUT_PARAM,"请提供要取消订单的游玩人");
		}
		List<LineOrderTraveler> lineOrderTravelerList = new ArrayList<LineOrderTraveler>();
		String[] travellerArr = command.getLineOrderTravelers().split(",");
		double bargainMoney = lineOrder.getBaseInfo().getBargainMoney();
		double salePrice = lineOrder.getBaseInfo().getSalePrice();
		Integer lineSales = line.getBaseInfo().getSales();
		for(int i=0;i<travellerArr.length;i++){
			LineOrderTraveler lineOrderTraveler = lineOrderTravelerDAO.get(travellerArr[i]);
			//已取消订单的游客不能重复取消，防止团期人数重复累加
			if(lineOrderTraveler != null 
					&& !XLOrderStatusConstant.SLFX_ORDER_CANCEL.equals(lineOrderTraveler.getXlOrderStatus().getStatus() + "")){
				//取消订单，修改游玩人状态
				lineOrderTraveler.setXlOrderStatus(lineOrderTraveler.getXlOrderStatus().cancelOrderStatus());
				lineOrderTravelerList.add(lineOrderTraveler);
				lineSales --;
				//取消订单后修改订单的定金和总金额
				bargainMoney = bargainMoney - lineOrderTraveler.getSingleBargainMoney();
				salePrice = salePrice - lineOrderTraveler.getSingleSalePrice();
			}
		}
		//判断所有用户是否都已取消订单，防止有-0.0000000123的情况出现
		boolean flag = true;
		for(int i=0;i<travellerArr.length;i++){
			LineOrderTraveler lineOrderTraveler = lineOrderTravelerDAO.get(travellerArr[i]);
			if(lineOrderTraveler != null 
					&& !XLOrderStatusConstant.SLFX_ORDER_CANCEL.equals(lineOrderTraveler.getXlOrderStatus().getStatus() + "")){
				flag = false;
			}
		}
		if(!flag){
			bargainMoney = 0.0;
			salePrice = 0.0;
		}
		lineOrderTravelerDAO.updateList(lineOrderTravelerList);
		lineOrder.getBaseInfo().setBargainMoney(bargainMoney);
		lineOrder.getBaseInfo().setSalePrice(salePrice);
		lineOrderDAO.update(lineOrder);
		line.getBaseInfo().setSales(lineSales);
		lineDAO.update(line);
		//修改团期人数
		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		dateSalePriceQO.setLineID(lineOrder.getLineSnapshot().getLine().getId());
		String travelDateStr = DateUtil.formatDate(lineOrder.getBaseInfo().getTravelDate());
		dateSalePriceQO.setSaleDate(travelDateStr);
		DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
		if(dateSalePrice == null){
			throw new SlfxXlException(SlfxXlException.RESULT_DATESALEPRICE_NOT_FOUND,"线路团期信息不存在");
		}
		//团期人数加上取消订单的人数
		dateSalePrice.setNumber(dateSalePrice.getNumber() + lineOrderTravelerList.size());
		dateSalePriceDAO.update(dateSalePrice);
		
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","cancleLineOrder",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		return true;
		
	}
	
    /****
     * 
     * @方法功能说明：修改支付尾款数目(实际上是修改单人总价-单人定金=尾款)(批量修改也支持)
     * @修改者名字：yaosanfeng
     * @修改时间：2015年5月28日下午3:55:51
     * @修改内容：
     * @参数：@param command
     * @参数：@return
     * @参数：@throws SlfxXlException
     * @return:Boolean
     * @throws
     */
	public Boolean modifyPaymentLineOrder(ModifyPaymentLineOrderCommand command) throws SlfxXlException{
		HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->command:"+JSON.toJSONString(command));
		try{
				LineOrder lineOrder = lineOrderDAO.get(command.getLineOrderId());
				HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->lineOrder:订单信息"+JSON.toJSONString(lineOrder));
				if(lineOrder == null){
					throw new SlfxXlException(SlfxXlException.RESULT_LINEORDER_NOT_FOUND,"线路订单不存在");
				}
				List<LineOrderTraveler> lineOrderTravelerList = new ArrayList<LineOrderTraveler>();
				double salePrice=0.0 ;  //销售总价
		
				if(command.getTravelerId() != null){//修改单人总价
					LineOrderTraveler lineOrderTraveler = lineOrderTravelerDAO.get(command.getTravelerId());
					HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->lineOrderTraveler:" + JSON.toJSONString(lineOrderTraveler));
					if(lineOrderTraveler.getXlOrderStatus().getPayStatus()==Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY)){
						//已支付定金的才能修改支付尾款金额,修改尾款是通过修改设置单人总金额实现的
						lineOrderTraveler.setSingleSalePrice(command.getSingleSalePrice());//修改单人全款
						lineOrderTraveler.setRemark(command.getRemark());//添加备注
						lineOrderTravelerList.add(lineOrderTraveler);
					}	
				}
//				else{//批量修改单人总价
////					    Set<LineOrderTraveler> travelset=lineOrder.getTravelers();
//						for(LineOrderTraveler travels:lineOrder.getTravelers()){
//							if(travels.getXlOrderStatus().getPayStatus()==Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY)){
//								//已支付定金的才能修改支付尾款金额,修改尾款是通过修改设置单人总金额实现的
//								travels.setSingleSalePrice(command.getSingleSalePrice());//批量修改单人全款
//								travels.setRemark(command.getRemark());//添加备注
//							    lineOrderTravelerList.add(travels); 
//							}
//						}
//				   
//				}
				HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->lineOrderTravelerList:" + JSON.toJSONString(lineOrderTravelerList));
				//更新游客信息
				lineOrderTravelerDAO.updateList(lineOrderTravelerList);
				//计算订单总价
				LineOrder newLineOrder = lineOrderDAO.get(command.getLineOrderId());
				HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->newLineOrder:" + JSON.toJSONString(newLineOrder));
				Set<LineOrderTraveler> travels=newLineOrder.getTravelers();
				for(LineOrderTraveler travel:travels){
					salePrice+=travel.getSingleSalePrice();
				}
				HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->salePrice:" + salePrice);
				//lineOrder.getBaseInfo().setBargainMoney(bargainMoney);
				newLineOrder.getBaseInfo().setSalePrice(salePrice);
				HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->保存lineOrder:" + JSON.toJSONString(lineOrder));
				lineOrderDAO.update(newLineOrder);
				HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder->保存lineOrder结束:");
				LineOrder lll = lineOrderDAO.get(command.getLineOrderId());
				HgLogger.getInstance().info("yaosanfeng", "lll:" + JSON.toJSONString(lll));
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","modifyPaymentLineOrder",JSON.toJSONString(command));
				domainEventRepository.save(event);
				return true;
		}catch(SlfxXlException e){
			e.printStackTrace();
			HgLogger.getInstance().error("yaosanfeng", "LineOrderLocalService->modifyPaymentLineOrder-->修改全款异常:" + HgLogger.getStackTrace(e));
			return false;
		}
	}

	
	/**
	 * 
	 * @方法功能说明：修改订单状态
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:07:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean changeLineOrderStatus(ChangeLineOrderStatusCommand command) throws SlfxXlException{
		
		LineOrder lineOrder = lineOrderDAO.get(command.getLineOrderID());
		if(lineOrder == null){
			throw new SlfxXlException(SlfxXlException.RESULT_LINEORDER_NOT_FOUND,"线路订单不存在");
		}
		if(StringUtils.isBlank(command.getLineOrderTravelers())){
			throw new SlfxXlException(SlfxXlException.CHANGE_LINEORDER_WITHOUT_PARAM,"请提供要更改订单状态的游玩人");
		}
		List<LineOrderTraveler> LineOrderTravelerList = new ArrayList<LineOrderTraveler>();
		String[] travellerArr = command.getLineOrderTravelers().split(",");
		for(int i=0;i<travellerArr.length;i++){
			LineOrderTraveler lineOrderTraveler = lineOrderTravelerDAO.get(travellerArr[i]);
			HgLogger.getInstance().info("yuqz", "LineOrderLocalService->changeLineOrderStatus->lineOrderTraveler=" + JSON.toJSONString(lineOrderTraveler));
//			当订单状态为下单成功未订位， 支付状态为已支付订金时才可更改订单状态
//			if(lineOrderTraveler != null && XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE .equals(lineOrderTraveler.getXlOrderStatus().getStatus() + "")
//					&& XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY.equals(lineOrderTraveler.getXlOrderStatus().getPayStatus() + "")){
			
			if(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE .equals(lineOrderTraveler.getXlOrderStatus().getStatus() + "")){
				//未定位  已支付订金 --> 已定位  付尾款
				if(XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY.equals(lineOrderTraveler.getXlOrderStatus().getPayStatus() + "")){
					//更改各游玩人的状态
					lineOrderTraveler.setXlOrderStatus(lineOrderTraveler.getXlOrderStatus().changeOrderStatus());
				}
				//未定位   全款支付  ---> 预定成功  全额付款
				if(XLOrderStatusConstant.SLFX_PAY_SUCCESS.equals(lineOrderTraveler.getXlOrderStatus().getPayStatus() + "")){
					//更改各游玩人的状态
					lineOrderTraveler.setXlOrderStatus(lineOrderTraveler.getXlOrderStatus().changeOrderStatusPayAll());
				}
				LineOrderTravelerList.add(lineOrderTraveler);
			}
		}
		lineOrderTravelerDAO.updateList(LineOrderTravelerList);
		
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","changeLineOrderStatus",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = lineOrderDAO.queryPagination(pagination);
		List<LineOrder> lineOrderList = (List<LineOrder>) pagination2.getList();
		for (LineOrder lineOrder : lineOrderList) {
			Hibernate.isInitialized(lineOrder.getTravelers());
			lineOrder.getTravelers().size();
		}
		pagination2.setList(lineOrderList);
		return pagination2;
	}
	
	
/*---------------------admin使用上面，shop使用下面----------------------*/	

	/**
	 * 
	 * @方法功能说明：线路订单持久化到数据库
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月16日下午3:26:18
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO shopCreateLineOrder(CreateLineOrderCommand command) throws SlfxXlException{
		LineOrderDTO lineOrderDTO=null;
		//查询最新线路快照
		Line line = lineDAO.get(command.getLineID());
		LineSnapshotQO lineSnapshotQO = new LineSnapshotQO();
		lineSnapshotQO.setLineID(command.getLineID());
		lineSnapshotQO.setIsNew(true);
		LineSnapshot lineSnapshot = lineSnapshotDAO.queryUnique(lineSnapshotQO);
		
		//查询订单线路团期
		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		dateSalePriceQO.setLineID(command.getLineID());
		String travelDateStr = DateUtil.formatDate(command.getBaseInfo().getTravelDate());
		dateSalePriceQO.setSaleDate(travelDateStr);
		DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
		
		//查询订单适用成人价格政策
		SalePolicyLineQO salePolicyLineQO = new SalePolicyLineQO();
		salePolicyLineQO.setLineDealerID(command.getLineDealerID());
		salePolicyLineQO.setLineID(command.getLineID());
		salePolicyLineQO.setPriceType(SalePolicyConstants.GROW_PRICE);
		salePolicyLineQO.setSalePolicyLineStatus(SalePolicyConstants.SALE_START); //政策状态为已开始
		salePolicyLineQO.setMaxPriority(true);
		salePolicyLineQO.setIsHide(false);
		SalePolicy adultSalePolicy = salePolicyLineDAO.queryUnique(salePolicyLineQO);
		//查询该成人价格政策的最新快照
		SalePolicySnapshot adultSalePolicySnapshot = null;
		if(adultSalePolicy  != null){
			SalePolicySnapshotQO salePolicySnapshotQO = new SalePolicySnapshotQO();
			salePolicySnapshotQO.setSalePolicyID(adultSalePolicy.getId());
			salePolicySnapshotQO.setIsNew(true);
			adultSalePolicySnapshot = salePolicySnapshotDAO.queryUnique(salePolicySnapshotQO);
		}
		
		
		//查询订单适用儿童价格政策
		salePolicyLineQO.setPriceType(SalePolicyConstants.YOUNG_PRICE);
		SalePolicy childSalePolicy = salePolicyLineDAO.queryUnique(salePolicyLineQO);
		//查询该儿童价格政策的最新快照
		SalePolicySnapshot childSalePolicySnapshot = null;
		if(childSalePolicy != null){
			SalePolicySnapshotQO salePolicySnapshotQO = new SalePolicySnapshotQO();
			salePolicySnapshotQO.setSalePolicyID(childSalePolicy.getId());
			salePolicySnapshotQO.setIsNew(true);
			childSalePolicySnapshot = salePolicySnapshotDAO.queryUnique(salePolicySnapshotQO);
		}
		
		
		//TODO 价格调整
//		SalePolicySnapshot adultSalePolicySnapshot = salePolicySnapshotDAO.get(command.getAdultSalePolicySnapshot().getId());
//		SalePolicy adultSalePrice = JSON.parseObject(adultSalePolicySnapshot.getAllInfoSalePolicyJSON(), SalePolicy.class);
		//TODO 价格调整
//		SalePolicySnapshot childSalePolicySnapshot = salePolicySnapshotDAO.get(command.getChildSalePolicySnapshot().getId());
//		SalePolicy childSalePrice = JSON.parseObject(childSalePolicySnapshot.getAllInfoSalePolicyJSON(), SalePolicy.class);
	
		
//		Line line = JSON.parseObject(lineSnapshot.getAllInfoLineJSON(),Line.class);
		
		
		Double supplierAdultUnitPrice = 0.0; //供应商成人价格
		Double supplierUnitChildPrice = 0.0; //供应商儿童价格
		if(dateSalePrice == null){
			throw new SlfxXlException(SlfxXlException.RESULT_DATESALEPRICE_NOT_FOUND,"线路" + travelDateStr + "团期信息不存在");
		}
		supplierAdultUnitPrice = dateSalePrice.getAdultPrice(); 
		supplierUnitChildPrice = dateSalePrice.getChildPrice(); 
		
		//更新团期剩余人数
		ModifyDateSalePriceCommand modifyDateSalePriceCommand = new ModifyDateSalePriceCommand();
		//command的人数是累加，下订单团期要减去订单里的游客人数，所以这里command的人数是负数
		modifyDateSalePriceCommand.setNumber
						(- (command.getBaseInfo().getAdultNo() + command.getBaseInfo().getChildNo()));
		dateSalePrice.modifyDateSalePrice(modifyDateSalePriceCommand);
		dateSalePriceDAO.update(dateSalePrice);
		
		DomainEvent event3 = new DomainEvent("slfx.xl.domain.model.line.DateSalePrice","modifyDateSalePrice",JSON.toJSONString(modifyDateSalePriceCommand));
		domainEventRepository.save(event3);
		
		//计算各价格
		List<LineOrderTravelerDTO> travelerDTOList =command.getTravelerList();
		List<LineOrderTraveler> travelerList = new ArrayList<LineOrderTraveler>();
		Double orderSalePrice = 0.0; //订单销售总价
		Double bargainMoney = 0.0; ////订单定金
		Double audltUnitPrice = 0.0; //成人单人全款金额
		Double childUnitPrice = 0.0; //儿童单人全款金额
		
		if(travelerDTOList != null && travelerDTOList.size() > 0){
			for (LineOrderTravelerDTO dto : travelerDTOList) {
				
				LineOrderTraveler lineOrderTraveler = new LineOrderTraveler();
				Double singleSalePrice = 0.0; //单人全款金额
				Double singleBargainMoney = 0.0; //单人定金
				
				if(dto.getType() == LineOrderConstants.TYPE_ADULT){ //成人游客
					if(adultSalePolicy != null){
						singleSalePrice = adultSalePolicy.countSalePrice(supplierAdultUnitPrice); 
					}else{
						singleSalePrice = supplierAdultUnitPrice; //不存在适合的价格政策 ，销售价格和供应商提供的成人价格一致
					}
					
					audltUnitPrice = singleSalePrice;
				}else if(dto.getType() == LineOrderConstants.TYPE_CHILD){  //儿童游客
					if(childSalePolicy != null){
						singleSalePrice = childSalePolicy.countSalePrice(supplierUnitChildPrice); 
					}else{
						singleSalePrice = supplierUnitChildPrice; //不存在适合的价格政策 ，销售价格和供应商提供的儿童价格一致
					}
					
					childUnitPrice = singleSalePrice;
				}
				
				singleBargainMoney = line.countBargainMoney(singleSalePrice);//单人定金
				dto.setSingleSalePrice(singleSalePrice);
				dto.setSingleBargainMoney(singleBargainMoney);
				lineOrderTraveler.createLineOrderTraveler(dto);
				orderSalePrice = orderSalePrice + singleSalePrice;
				
				travelerList.add(lineOrderTraveler);
				
			}
			
			bargainMoney = line.countBargainMoney(orderSalePrice);//订单总销售定金
			
			//持久化订单
			LineOrder lineOrder = new LineOrder();
			command.getBaseInfo().setSupplierAdultUnitPrice(supplierAdultUnitPrice);
			command.getBaseInfo().setSupplierUnitChildPrice(supplierUnitChildPrice);
			command.getBaseInfo().setSupplierPrice(command.getBaseInfo().getAdultNo()*supplierAdultUnitPrice + command.getBaseInfo().getChildNo()*supplierUnitChildPrice);
			command.getBaseInfo().setAdultUnitPrice(audltUnitPrice);
			command.getBaseInfo().setChildUnitPrice(childUnitPrice);
			command.getBaseInfo().setBargainMoney(bargainMoney);
			command.getBaseInfo().setSalePrice(orderSalePrice);
			lineOrder.createLineOrder(command,adultSalePolicySnapshot,childSalePolicySnapshot,lineSnapshot);
			lineOrderDAO.save(lineOrder);
			
			DomainEvent event1 = new DomainEvent("slfx.xl.domain.model.order.LineOrder","shopCreateLineOrder",JSON.toJSONString(command));
			domainEventRepository.save(event1);
			Integer lineSales = line.getBaseInfo().getSales();
			//持久化游玩人
			for(LineOrderTraveler traveler:travelerList){
				traveler.setLineOrder(lineOrder);
				if(lineSales==null){
					lineSales=0;
				}
				lineSales ++;
			}
			line.getBaseInfo().setSales(lineSales);
			lineDAO.update(line);
			lineOrderTravelerDAO.saveList(travelerList);
			DomainEvent event2 = new DomainEvent("slfx.xl.domain.model.order.LineOrderTraveler","createLineOrderTraveler",JSON.toJSONString(travelerDTOList));
			domainEventRepository.save(event2);
			
			//创建成功返回数据
			lineOrderDTO = BeanMapperUtils.getMapper().map(lineOrder,LineOrderDTO.class);
			Set<LineOrderTravelerDTO> travelerDTOLists = new HashSet<LineOrderTravelerDTO>();
			for(LineOrderTraveler lineOrderTraveler : travelerList){
				LineOrderTravelerDTO dto = EntityConvertUtils.convertEntityToDto(lineOrderTraveler, LineOrderTravelerDTO.class);
				travelerDTOLists.add(dto);
			}
			lineOrderDTO.setTravelers(travelerDTOLists);
		}
		
		return lineOrderDTO;
	}
	
	/**
	 * @方法功能说明：商城取消订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日上午10:55:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean ShopCancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException{
		
		LineOrder lineOrder = lineOrderDAO.get(command.getLineOrderID());
		if(lineOrder == null){
			throw new SlfxXlException(SlfxXlException.RESULT_LINEORDER_NOT_FOUND,"线路订单不存在");
		}
		if(StringUtils.isBlank(command.getLineOrderTravelers())){
			throw new SlfxXlException(SlfxXlException.CANCLE_LINEORDER_WITHOUT_PARAM,"请提供要取消订单的游玩人");
		}
		
		List<LineOrderTraveler> lineOrderTravelerList = new ArrayList<LineOrderTraveler>();
		String[] travellerArr = command.getLineOrderTravelers().split(",");
		for(int i=0;i<travellerArr.length;i++){
			LineOrderTraveler lineOrderTraveler = lineOrderTravelerDAO.get(travellerArr[i]);
			if(lineOrderTraveler == null){
				throw new SlfxXlException(SlfxXlException.RESULT_TRAVELER_NOT_FOUND,"游玩人" + travellerArr[i] + "不存在");
			}
			//已取消订单的游客不能重复取消，防止团期人数重复累加
			if(!XLOrderStatusConstant.SLFX_ORDER_CANCEL.equals(lineOrderTraveler.getXlOrderStatus().getStatus() + "")){
				//取消订单，修改游玩人状态
				lineOrderTraveler.setXlOrderStatus(lineOrderTraveler.getXlOrderStatus().cancelOrderStatus());
				lineOrderTravelerList.add(lineOrderTraveler);
			}
		}
		lineOrderTravelerDAO.updateList(lineOrderTravelerList);
		
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","ShopCancleLineOrder",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		//修改团期人数
		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		dateSalePriceQO.setLineID(lineOrder.getLineSnapshot().getLine().getId());
		String travelDateStr = DateUtil.formatDate(lineOrder.getBaseInfo().getTravelDate());
		dateSalePriceQO.setSaleDate(travelDateStr);
		DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
		if(dateSalePrice == null){
			throw new SlfxXlException(SlfxXlException.RESULT_DATESALEPRICE_NOT_FOUND,"线路" + travelDateStr + "团期信息不存在");
		}
		//团期人数加上取消订单的人数
		ModifyDateSalePriceCommand modifyDateSalePriceCommand = new ModifyDateSalePriceCommand();
		modifyDateSalePriceCommand.setNumber(lineOrderTravelerList.size());
		dateSalePrice.modifyDateSalePrice(modifyDateSalePriceCommand);
		dateSalePriceDAO.update(dateSalePrice);
		
		DomainEvent event1 = new DomainEvent("slfx.xl.domain.model.line.DateSalePrice","modifyDateSalePrice",JSON.toJSONString(modifyDateSalePriceCommand));
		domainEventRepository.save(event1);
		
		return true;
		
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月29日上午9:22:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws SlfxXlException
	 * @return:Boolean
	 * @throws
	 */
	public Boolean shopPayLineOrder(BatchPayLineOrderCommand command)throws SlfxXlException{
		
		String lineOrderID = command.getLineOrderID();
		if(StringUtils.isBlank(lineOrderID)){
			throw new SlfxXlException(SlfxXlException.PAY_LINEORDER_WITHOUT_PARAM,"请提供线路订单的编号");
		}
		LineOrder lineOrder = lineOrderDAO.get(lineOrderID);
		if(lineOrder == null){
			throw new SlfxXlException(SlfxXlException.RESULT_LINEORDER_NOT_FOUND,"线路订单不存在");
		}
		String lineOrderTravelers = command.getLineOrderTravelers();
		if(StringUtils.isBlank(lineOrderTravelers)){
			throw new SlfxXlException(SlfxXlException.PAY_LINEORDER_WITHOUT_PARAM,"请提供支付线路订单的游玩人信息");
		}
		String[] travelerArr = lineOrderTravelers.split(",");
		
		LineOrderTraveler lineOrderTraveler = null;
		for(String travelerID:travelerArr){
			lineOrderTraveler = lineOrderTravelerDAO.get(travelerID);
			//判断付款类型：LineOrderConstants
			if(lineOrderTraveler != null && command.getShopPayType() != null){
				
				if(LineOrderConstants.SHOP_PAY_TYPE_BARGAIN_MONEY.equals(command.getShopPayType())){
					//定金
					lineOrderTraveler = lineOrderTraveler.modifyLineOrderTravelerStatusBargainMoney(lineOrderTraveler);
				}else if(LineOrderConstants.SHOP_PAY_TYPE_TAIL_MONEY.equals(command.getShopPayType())){
					//尾款
					lineOrderTraveler = lineOrderTraveler.modifyLineOrderTravelerStatusTailMoney(lineOrderTraveler);
				}else if(LineOrderConstants.SHOP_PAY_TYPE_ALL_MONEY.equals(command.getShopPayType())){
					//全款
					lineOrderTraveler = lineOrderTraveler.modifyLineOrderTravelerStatusAllMoney(lineOrderTraveler);
				}
				lineOrderTravelerDAO.update(lineOrderTraveler);
				
			}else{
				throw new SlfxXlException(SlfxXlException.RESULT_TRAVELER_NOT_FOUND,"游玩人" + travelerID + "不存在,或付款类型不明：1定金，2尾款，3全款");
			}
		}
		
		List<LineOrderPayment> lineOrderPaymentList = new ArrayList<LineOrderPayment>();
		LineOrderPayment lineOrderPayment = new LineOrderPayment();
		lineOrderPaymentList = lineOrderPayment.batchCreate(command);
		
		lineOrderPaymentDAO.saveList(lineOrderPaymentList);
		
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","shopPayLineOrder",JSON.toJSONString(command));
		domainEventRepository.save(event);
		
		return true;
			
		
		
	}
	

	/****
	 * 
	 * @方法功能说明：修改游玩人状态
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年4月30日下午7:05:28
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO modifyLineOrderTraveler(ModifyLineOrderTravelerCommand command){
		HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->ModifyLineOrderTraveler->ModifyLineOrderTravelerCommand:" + JSON.toJSONString(command));
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setDealerOrderNo(command.getDealerOrderNo());
		LineOrder lineOrder = lineOrderDAO.queryUnique(lineOrderQO);
		if(null==lineOrder){
			HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->ModifyLineOrderTraveler:线路订单不存在");
		}
		List<LineOrderTraveler> LineOrderTravelerList = new ArrayList<LineOrderTraveler>();
		List<LineOrderTravelerDTO> travelerList = command.getTravelers();
		getDao().evict(lineOrder);
		for(LineOrderTravelerDTO dto : travelerList){
			LineOrderTraveler lineOrderTraveler = EntityConvertUtils.convertDtoToEntity(dto, LineOrderTraveler.class);
			XLOrderStatus xlOrderStatus = new XLOrderStatus();
			xlOrderStatus.setStatus(lineOrderTraveler.getXlOrderStatus().getStatus() + 1000);
			xlOrderStatus.setPayStatus(lineOrderTraveler.getXlOrderStatus().getPayStatus() + 1000);
			lineOrderTraveler.setXlOrderStatus(xlOrderStatus);
			lineOrderTraveler.setLineOrder(lineOrder);
			LineOrderTravelerList.add(lineOrderTraveler);
		}
		HgLogger.getInstance().info("yuqz","LineOrderLocalService->ModifyLineOrderTraveler->LineOrderTravelerList:" + JSON.toJSONString(LineOrderTravelerList));
		lineOrderTravelerDAO.updateList(LineOrderTravelerList);
//		lineOrderDAO.update(lineOrder);
		LineOrder newLineOrder = lineOrderDAO.queryUnique(lineOrderQO);
		HgLogger.getInstance().info("yaosanfeng", "LineOrderLocalService->ModifyLineOrderTraveler->LineOrder:" + JSON.toJSONString(newLineOrder));
		//添加日志
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrder","ModifyLineOrderTraveler",JSON.toJSONString(command));
	    domainEventRepository.save(event);
		return EntityConvertUtils.convertEntityToDto(newLineOrder, LineOrderDTO.class);	
	}

	/**
	 * @方法功能说明：定时清理过期线路订单
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月14日下午5:57:41
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public boolean updateLineOrderStatus(UpdateLineOrderStatusCommand command) {
		if(null == command.getDealerOrderCode()){
			HgLogger.getInstance().info("yuqz", "LineOrderLocalService->updateLineOrderStatus:经销商订单号为空");
			return false;
		}
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setDealerOrderNo(command.getDealerOrderCode());
		LineOrder lineOrder = getDao().queryUnique(lineOrderQO);
		if(null == lineOrder){
			HgLogger.getInstance().info("yuqz", "LineOrderLocalService->updateLineOrderStatus:线路订单不存在");
			return false;
		}
		if(null == lineOrder.getTravelers()){
			HgLogger.getInstance().info("yuqz", "LineOrderLocalService->updateLineOrderStatus:游客信息列表set为空");
			return false;
		}
		Set<LineOrderTraveler> lineOrderTravelers = lineOrder.getTravelers();
		HgLogger.getInstance().info("yuqz", "LineOrderLocalService->lineOrderTravelers:" + lineOrderTravelers);
		Set<LineOrderTraveler> travelers = new HashSet<LineOrderTraveler>();
		for(LineOrderTraveler lineOrderTraveler : lineOrderTravelers){
			XLOrderStatus xlOrderStatus = new XLOrderStatus();
			xlOrderStatus.setPayStatus(command.getPayStatus());
			xlOrderStatus.setStatus(command.getOrderStatus());
			lineOrderTraveler.setXlOrderStatus(xlOrderStatus);
			travelers.add(lineOrderTraveler);
		}
		HgLogger.getInstance().info("yuqz", "LineOrderLocalService->travelers:" + travelers);
		lineOrder.setTravelers(travelers);
		HgLogger.getInstance().info("yuqz", "LineOrderLocalService->lineOrder:" + lineOrder);
		this.getDao().update(lineOrder);
		return true;
	}

	public Boolean batchModifyPaymentLineOrder(ModifyPaymentLineOrderCommand command) {
		try{
			LineOrder lineOrder = lineOrderDAO.get(command.getLineOrderId());
			Set<LineOrderTraveler> travelers = lineOrder.getTravelers();
			List<LineOrderTraveler> newTravelers = new ArrayList<LineOrderTraveler>();
			HgLogger.getInstance().info("yuqz", "travelers:" + JSON.toJSONString(travelers));
			for(LineOrderTraveler lineOrderTraveler : travelers){
				if(lineOrderTraveler.getXlOrderStatus().getPayStatus() == Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY)){
					lineOrderTraveler.setSingleSalePrice(command.getSingleSalePrice());
					lineOrderTraveler.setRemark(command.getRemark());
					newTravelers.add(lineOrderTraveler);
				}
			}
			HgLogger.getInstance().info("yuqz", "newTravelers:" + JSON.toJSONString(newTravelers));
			lineOrderTravelerDAO.updateList(newTravelers);
			LineOrder newLineOrder = lineOrderDAO.get(command.getLineOrderId());
			Set<LineOrderTraveler> travelerSet = newLineOrder.getTravelers();
			double salePrice = 0.0d;
			for(LineOrderTraveler lineOrderTraveler : travelerSet){
				salePrice = salePrice + lineOrderTraveler.getSingleSalePrice();
			}
			HgLogger.getInstance().info("yuqz", "salePrice:" + salePrice);
			newLineOrder.getBaseInfo().setSalePrice(salePrice);
			lineOrderDAO.update(newLineOrder);
			LineOrder lll = lineOrderDAO.get(command.getLineOrderId());
			HgLogger.getInstance().info("yuqz", "lll:" + JSON.toJSONString(lll));
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
