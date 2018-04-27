package slfx.jp.app.service.spi;

import hg.common.page.Pagination;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.FlightPolicyLocalService;
import slfx.jp.app.service.local.JPOrderLocalService;
import slfx.jp.app.service.local.JPOrderLogLocalService;
import slfx.jp.app.service.local.policy.PolicySnapshotLocalService;
import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.ApplyRefundCommand;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.command.admin.jp.JPAskOrderTicketSpiCommand;
import slfx.jp.command.admin.jp.JPCancelTicketSpiCommand;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.PlatformOrderDetailDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.pojo.system.OrderLogConstants;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.api.JPOrderSpiQO;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.PlatformGetRefundActionTypeService;
import slfx.jp.spi.inter.WebFlightService;
import slfx.jp.spi.inter.dealer.DealerService;
import slfx.jp.spi.inter.policy.PolicyService;

/**
 * 
 * @类功能说明：平台订单SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年7月31日下午4:47:08
 * @版本：V1.0
 * 
 */
@Service("jpPlatformOrderService")
public class JPPlatformOrderServiceImpl extends BaseJpSpiServiceImpl<JPOrderDTO, PlatformOrderQO, JPOrderLocalService> implements JPPlatformOrderService {

	@Resource
	private JPOrderLocalService jpOrderLocalService;

//	@Resource
//	private LocalPlatformAskOrderTicketService platformAskOrderTicketlocalService;
//
//	@Resource
//	private PassengerLocalService passengerlocalService;

	@Resource
	private FlightPolicyLocalService flightPolicyLocalService;

	@Resource
	private PolicySnapshotLocalService policySnapshotLocalService;

	@Resource
	private WebFlightService webFlightService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private DealerService dealerService;

	@Autowired
	private PlatformGetRefundActionTypeService jpPlatformGetRefundActionTypeService;
	@Autowired
	private JPOrderLogLocalService jpOrderLogLocalService;

	@Override
	protected Class<JPOrderDTO> getDTOClass() {
		return JPOrderDTO.class;
	}

	@Override
	protected JPOrderLocalService getService() {
		return jpOrderLocalService;
	}

	

	@Override
	public List<JPOrderDTO> queryErrorJPOrderList(PlatformOrderQO platformOrderQO) {
		return this.queryList(platformOrderQO);
	}

	@Override
	public List<JPOrderDTO> queryJPOrderList(PlatformOrderQO platformOrderQO) {
		return this.queryList(platformOrderQO);
	}

	@Override
	public Pagination queryFJPOrderList(Pagination pagination) {
		return queryPagination(pagination);
	}
	@Override
	public JPOrderDTO shopCreateOrder(JPOrderCreateSpiCommand jPOrderCreateSpiCommand) throws JPOrderException {
		
		JPOrderDTO jpOrderDTO = jpOrderLocalService.shopCreateOrder(jPOrderCreateSpiCommand);
		JPOrder jpOrder = jpOrderLocalService.get(jpOrderDTO.getId());
		if(jpOrder != null){
			//添加操作日志
			CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
			logCommand.setLogName(OrderLogConstants.CRATE_LOG_NAME);
			logCommand.setWorker(OrderLogConstants.ZX_WORKER);
			logCommand.setLogInfo(OrderLogConstants.CRATE_LOG_INFO);
			logCommand.setJpOrderId(jpOrderDTO.getId());
			jpOrderLogLocalService.create(logCommand,jpOrder);
		}
		return jpOrderDTO;
	}
	
	
	@Override
	public PlatformOrderDetailDTO queryOrderDetail(PlatformOrderQO platformOrderQO) {
		return jpOrderLocalService.queryOrderDetail(platformOrderQO);
	}

	@Override
	public Pagination queryOrderList(Pagination pagination) {
		return jpOrderLocalService.queryOrderList(pagination);
	}

	@Override
	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand) {
		return jpOrderLocalService.saveErrorJPOrder(jpOrderCommand);
	}

	
	@Override
	public boolean shopAskOrderTicket(JPAskOrderTicketSpiCommand command) throws JPOrderException{
		return jpOrderLocalService.shopAskOrderTicket(command);
	}

	@Override
	public boolean shopCancelOrder(JPCancelTicketSpiCommand jpCancelTicketSpiCommand) throws JPOrderException{
		return jpOrderLocalService.shopCancelOrder(jpCancelTicketSpiCommand);
	}
	

	@Override
	public Pagination shopQueryOrderList(JPOrderSpiQO jpOrderQO) throws JPOrderException{
		return jpOrderLocalService.shopQueryOrderList(jpOrderQO);
	}
	
	@Override
	public boolean shopRefundOrder(JPCancelTicketSpiCommand jpCancelTicketCommand) throws JPOrderException{
		return jpOrderLocalService.shopRefundOrder(jpCancelTicketCommand);
	}

	@Override
	public boolean updateOrderStatus(JPOrderCommand command) {
		return jpOrderLocalService.updateOrderStatus(command);
	}
	
	@Override
	public boolean getTicketNoFailure(JPOrderCommand command) {
		return jpOrderLocalService.getTicketNoFailure(command);
	}

	/*@Override
	public boolean cancelOrderNotify(JPOrderCommand command) {
		return jpOrderLocalService.cancelOrderNotify(command);
	}*/
	
	@Override
	public boolean backOrDiscardTicketNotify(JPOrderCommand command){
		return jpOrderLocalService.backOrDiscardTicketNotify(command);
	}

	@Override
	public boolean adminCancelOrder(AdminCancelOrderCommand adminCancelOrderCommand) {
		return jpOrderLocalService.adminCancelOrder(adminCancelOrderCommand);

	}

	@Override
	public void adminRefundOrder(ApplyRefundCommand applyRefundCommand) {
		jpOrderLocalService.adminRefundOrder(applyRefundCommand);
	}

	@Override
	public YGRefundActionTypesDTO getRefundActionType(String platCode){
		return jpPlatformGetRefundActionTypeService.getAdminRefundActionType(platCode);
	}
	/*
	 * @Override public List<FlightPassengerDTO> shopAskOrderTicket(
	 * JPAskOrderTicketCommand jpAskOrderTicketCommand) {
	 * if(jpAskOrderTicketCommand == null){
	 * HgLogger.getInstance().info("tandeng","商城向平台请求出票 异常：读取参数失败。"); return
	 * null; }
	 * 
	 * List<FlightPassengerDTO> flightPassengerDTOList = new
	 * ArrayList<FlightPassengerDTO>();
	 * 
	 * YGAskOrderTicketCommand ygAskOrderTicketCommand = new
	 * YGAskOrderTicketCommand(); //经销商订单号
	 * ygAskOrderTicketCommand.setOrderNo(jpAskOrderTicketCommand.getOrderId());
	 * 
	 * //平台数据库查询该订单的成库列表 YGAskOrderTicketDTO ygAskOrderTicketDTO=
	 * platformAskOrderTicketService.askOrderTicket(ygAskOrderTicketCommand);
	 * if(ygAskOrderTicketDTO != null){ TicketDTO ticketDTO = new TicketDTO();
	 * ticketDTO.setOrderId(ygAskOrderTicketDTO.getPlatOrderNo());
	 * ticketDTO.setStatus(1); //设置状态为已出票
	 * HgLogger.getInstance().info("tandeng","商城向平台请求出票  成功。"); }
	 * 
	 * flightPassengerDTOList=
	 * this.localPlatformAskOrderTicketService.queryFlightPassengerDTO(
	 * ygAskOrderTicketCommand.getOrderNo()); return flightPassengerDTOList; }
	 */
	/*
	 * @Override public void addRefundOrder(ApplyRefundCommand
	 * applyRefundCommand) { YGQueryOrderDTO ygQueryOrderDTO =
	 * jpOrderLocalService.queryYgOrder(applyRefundCommand.getRefundOrderNo());
	 * PlatformOrderQO qo = new PlatformOrderQO();
	 * qo.setId(applyRefundCommand.getOrderNo()); JPOrder jpOrder =
	 * jpOrderLocalService.queryPaltformOrder(qo);
	 * 
	 * jpOrderLocalService.saveRefundJPOrderInfo(jpOrder);
	 * 
	 * }
	 */

	@Override
	public boolean refund(String resultDetails) {
		return jpOrderLocalService.refund(resultDetails);
	}
	

}
