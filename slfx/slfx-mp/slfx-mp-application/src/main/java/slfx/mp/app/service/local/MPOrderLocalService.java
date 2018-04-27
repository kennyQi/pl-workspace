package slfx.mp.app.service.local;

import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_DATE_SALE_PRICE_NEQ;
import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_INTER_FAIL;
import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_SALE_POLICY_CHANGE;
import static slfx.mp.spi.common.MpEnumConstants.KVConfigKey.KEY_SUPPLIER_TC;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_CANCEL;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_OUTOFDATE;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_PREPARED;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_USED;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.log.util.HgLogger;
import hg.system.cache.KVConfigCacheManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.mp.app.common.util.OrderNumberUtils;
import slfx.mp.app.component.manager.DateSalePriceCacheManager;
import slfx.mp.app.dao.MPOrderDAO;
import slfx.mp.app.dao.OrderUserInfoDAO;
import slfx.mp.app.dao.SalePolicySnapshotDAO;
import slfx.mp.app.dao.TCOrderDAO;
import slfx.mp.app.dao.TCSupplierPolicySnapshotDAO;
import slfx.mp.app.pojo.qo.MPOrderQO;
import slfx.mp.command.ModifyMPOrderStatusCommand;
import slfx.mp.command.SyncOrderCommand;
import slfx.mp.command.admin.AdminCancelOrderCommand;
import slfx.mp.domain.model.order.MPOrder;
import slfx.mp.domain.model.order.TCOrder;
import slfx.mp.domain.model.platformpolicy.DateSalePrice;
import slfx.mp.domain.model.platformpolicy.SalePolicySnapshot;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.tcclient.tc.domain.order.Guest;
import slfx.mp.tcclient.tc.domain.order.Order;
import slfx.mp.tcclient.tc.dto.order.CancelSceneryOrderDto;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderDetailDto;
import slfx.mp.tcclient.tc.dto.order.SubmitSceneryOrderDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.order.response.ResultCancelSceneryOrder;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderDetail;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSubmitSceneryOrder;
import slfx.mp.tcclient.tc.service.TcClientService;

@Service
@Transactional
public class MPOrderLocalService extends
		BaseServiceImpl<MPOrder, MPOrderQO, MPOrderDAO> {

	@Autowired
	private MPOrderDAO dao;
	@Autowired
	private TCSupplierPolicySnapshotDAO tcSupplierPolicySnapshotDAO;
	@Autowired
	private OrderUserInfoDAO orderUserInfoDAO;
	@Autowired
	private SalePolicySnapshotDAO salePolicySnapshotDAO;
	@Autowired
	private TCOrderDAO tcOrderDAO;

	@Autowired
	private TcClientService tcClientService;
	@Autowired
	private DateSalePriceLocalService dateSalePriceLocalService;
	@Autowired
	private SalePolicySnapshotLocalService salePolicySnapshotLocalService;
	@Autowired
	private TCSupplierPolicySnapshotLocalService tcSupplierPolicySnapshotLocalService;

	@Autowired
	private DateSalePriceCacheManager dateSalePriceCacheManager;
	@Autowired
	private KVConfigCacheManager kvConfigCacheManager;
	@Override
	protected MPOrderDAO getDao() {
		return dao;
	}

	/**
	 * 管理员取消订单
	 * 
	 * @param command
	 * @throws SlfxMpException
	 */
	public Boolean adminCancelOrder(AdminCancelOrderCommand command) throws SlfxMpException {
		HgLogger.getInstance().info("wuyg", "管理员取消门票订单");
		MPOrder mpOrder = getDao().get(command.getId());
		if (mpOrder == null)
			return false;
		// 调用取消订单接口
		CancelSceneryOrderDto cancelSceneryOrderDto = new CancelSceneryOrderDto();
		cancelSceneryOrderDto.setSerialId(mpOrder.getSupplierOrderCode());
		cancelSceneryOrderDto.setCancelReason(Integer.valueOf(command.getCancelRemark()));
		Response<ResultCancelSceneryOrder> response = tcClientService.cancelSceneryOrder(cancelSceneryOrderDto);
		ResultCancelSceneryOrder result = response.getResult();
		
		// 判断结果是否未空
		if (result == null || result.getIsSuc() == null)
			return false;
		
		if ("0".equals(response.getHead().getRspType()) && result.getIsSuc().intValue() == 1) {
			// 成功
			HgLogger.getInstance().info("wuyg", "管理员取消门票订单:成功");
			mpOrder.adminCancelOrder(command);
			getDao().update(mpOrder);
			return true;
		}
//		else {
//			// 失败后同步订单状态
//			syncOrder(new SyncOrderCommand(command.getId()));
//			return mpOrder.getStatus().getCancel();
//		}
		throw new SlfxMpException(EXCEPTION_CODE_INTER_FAIL, result.getErrMsg());
	}
	
	/**
	 * API 取消订单
	 * 
	 * @param command
	 * @throws SlfxMpException
	 */
	public Boolean apiCancelOrder(MPOrderCancelCommand command)throws SlfxMpException {
		HgLogger.getInstance().info("wuyg", "API 取消订单");
		AdminCancelOrderCommand command2 = new AdminCancelOrderCommand();
		command2.setId(command.getOrderId());
		command2.setCancelRemark(String.valueOf(command.getCancelReason()));
		command2.setCancel(true);
		return adminCancelOrder(command2);
	}
	
	public void syncOrder(String id) throws SlfxMpException{
		syncOrder(new SyncOrderCommand(id));
	}

	/**
	 * 生成订单DTO
	 * 
	 * @param command
	 * @param sceneryId
	 *            同程景区ID
	 * @param policyId
	 *            政策ID
	 * @return
	 */
	protected SubmitSceneryOrderDto createSubmitSceneryOrderDto(
			MPOrderCreateCommand command, String sceneryId, String policyId) {
		SubmitSceneryOrderDto dto = new SubmitSceneryOrderDto();
		dto.setSceneryId(sceneryId);
		// 设置预订人
		dto.setbMan(command.getOrderUserInfo().getName());
		dto.setbMobile(command.getOrderUserInfo().getMobile());
		dto.setbAddress(command.getOrderUserInfo().getAddress());
		dto.setbPostCode(command.getOrderUserInfo().getPostcode());
		dto.setbEmail(command.getOrderUserInfo().getEmail());

		// 设置取票人
		dto.settName(command.getTakeTicketUserInfo().getName());
		dto.settMobile(command.getTakeTicketUserInfo().getMobile());
		
		dto.setPolicyId(Integer.valueOf(policyId));
		dto.setTickets(command.getNumber());
		dto.setTravelDate(command.getTravelDate());
		dto.setOrderIP(command.getBookManIP());
		dto.setIdCard(command.getOrderUserInfo().getIdCardNo());
		List<Guest> guests = new ArrayList<Guest>();
		dto.setOtherGuest(guests);
		String tname = command.getTakeTicketUserInfo().getName();
		String tmobile = command.getTakeTicketUserInfo().getMobile();
		if (command.getNumber() >= 2) {
			Guest guest = new Guest();
			guest.setGName(tname);
			guest.setGMobile(tmobile);
			guests.add(guest);
		}
		if (command.getTraveler() != null) {
			for (MPOrderUserInfoDTO travelerDTO : command.getTraveler()) {
				if (command.getNumber() >= 2
						&& StringUtils.equals(travelerDTO.getMobile(), tmobile)
						&& StringUtils.equals(travelerDTO.getName(), tname)) {
					continue;
				}
				Guest guest = new Guest();
				guest.setGMobile(travelerDTO.getMobile());
				guest.setGName(travelerDTO.getName());
				guests.add(guest);
			}
		}
		return dto;
	}
	
	/**
	 * @方法功能说明：检查经销商用户KEY的来源，默认为PC
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-1下午5:59:34
	 * @修改内容：
	 * @参数：@param clientUserKey
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	protected String checkClientUserKey(String clientUserKey) {
		if (clientUserKey != null) {
			String[] str = clientUserKey.split("_");
			if (str.length >= 2)
				return str[0];
		}
		return OrderNumberUtils.SourceType.PC;
	}

	/**
	 * API 订票
	 * 
	 * @param command
	 * @throws SlfxMpException
	 */
	public String apiOrderTicket(MPOrderCreateCommand command) throws SlfxMpException {
		HgLogger.getInstance().info("wuyg", " API 订票");
		// 获取当前价格日历
		DateSalePrice dateSalePrice = dateSalePriceLocalService
				.getDateSalePrice(command.getPolicyId(), command.getFromClientKey(),
						command.getTravelDate());

		// 按照规则创建分销平台订单号
		String platformOrderCode = OrderNumberUtils.createMPPlatformOrderCode(
				command.getFromClientKey(), checkClientUserKey(command.getClientUserKey()));

		// 得到供应商最新政策快照
		TCSupplierPolicySnapshot supplierPolicySnapshot = tcSupplierPolicySnapshotLocalService
				.getLast(command.getPolicyId());

		// 获取对应平台政策快照
		SalePolicySnapshot salePolicySnapshot = dateSalePrice.getSalePolicySnapshot();
		if (salePolicySnapshot != null) {
			// 最新的价格快照
			SalePolicySnapshot salePolicySnapshot2 = salePolicySnapshotLocalService
					.getLast(salePolicySnapshot.getPolicyId());
			// 如果平台政策变动，则抛出异常
			if (!StringUtils.equals(command.getPolicyId(),salePolicySnapshot2.getId())) {
				HgLogger.getInstance().error("wuyg", "API 订票：下单前平台政策变动 ");
				throw new SlfxMpException(EXCEPTION_CODE_SALE_POLICY_CHANGE);
			}
		}

		// 如果平台价格与价格日历中的平台价格对不上，则抛出异常。
		if (command.getPrice().doubleValue()/command.getNumber() != dateSalePrice.getSalePrice().doubleValue()) {
			HgLogger.getInstance().error("wuyg", "API 订票：与最新价格不符  ");
			throw new SlfxMpException(EXCEPTION_CODE_DATE_SALE_PRICE_NEQ,
					String.format("与最新价格不符，最新价格为%.2f。",dateSalePrice.getSalePrice()*command.getNumber()));
		}

		// 创建平台订单，默认供应商为同程
		MPOrder mpOrder = new MPOrder();
		String supplierId = kvConfigCacheManager.getKV(KEY_SUPPLIER_TC);
		mpOrder.apiOrderTicket(command, salePolicySnapshot,
				supplierPolicySnapshot, platformOrderCode, supplierId);

		// 调用同程下单接口
		String supplierPolicyId = supplierPolicySnapshot.getPolicyId();
		String sceneryId = supplierPolicySnapshot.getScenicSpotSnapshot()
				.getTcScenicSpotsId();
		SubmitSceneryOrderDto sceneryOrderDto = createSubmitSceneryOrderDto(
				command, sceneryId, supplierPolicyId);
		Response<ResultSubmitSceneryOrder> response = tcClientService
				.submitSceneryOrder(sceneryOrderDto);
		ResultSubmitSceneryOrder result = response.getResult();

		// 接口调用失败
		if (!StringUtils.equals(response.getHead().getRspType(), "0")) {
			HgLogger.getInstance().error("wuyg", "API 订票：接口调用错误   ");
			throw new SlfxMpException(EXCEPTION_CODE_INTER_FAIL, response
					.getHead().getRspDesc());
		}

		// 获取供应商订单流水号
		String serialId = result.getSerialId();
		mpOrder.submitOver(serialId);
		TCOrder tcOrder = new TCOrder();
		tcOrder.apiOrderTicket(command, platformOrderCode, supplierPolicyId);

		// 保存入库
		orderUserInfoDAO.save(mpOrder.getOrderUserInfo());
		getDao().save(mpOrder);
		tcOrderDAO.save(tcOrder);
		HgLogger.getInstance().info("wuyg", "API 订票：成功");
		return mpOrder.getId();
	}

	/**
	 * 同步单个订单
	 * 
	 * @param command
	 * @throws SlfxMpException 
	 */
	public MPOrder syncOrder(SyncOrderCommand command) throws SlfxMpException {
		HgLogger.getInstance().info("wuyg", "同步单个订单");
		if (command.getOrderId() == null)
			return null;
		
		MPOrder mpOrder = getDao().get(command.getOrderId());
		
		if (mpOrder == null)
			return null;

		SceneryOrderDetailDto dto = new SceneryOrderDetailDto();
		dto.setSerialIds(mpOrder.getSupplierOrderCode());
		Response<ResultSceneryOrderDetail> response = tcClientService
				.getSceneryOrderDetail(dto);
		
		// 不成功则抛出异常
		if (!"0".equals(response.getHead().getRspType())) {
			HgLogger.getInstance().error("wuyg", "同步单个订单：接口调用错误 ");
			throw new SlfxMpException(EXCEPTION_CODE_INTER_FAIL, response
					.getHead().getRspDesc());
		}
		
		ResultSceneryOrderDetail result = response.getResult();
		Order remoteOrder = result.getOrder().get(0);

		ModifyMPOrderStatusCommand modifyCommand = new ModifyMPOrderStatusCommand();
		modifyCommand.setCancelAble(remoteOrder.getEnableCancel() == 0 ? false : true);
		
		// 1:新建（待游玩）订单
		// 2:取消订单
		// 3:游玩过订单
		// 4:预订未游玩订单
		switch (remoteOrder.getOrderStatus()) {
		case 1:
			modifyCommand.setOrderStatus(ORDER_STATUS_PREPARED);
			break;
		case 2:
			modifyCommand.setOrderStatus(ORDER_STATUS_CANCEL);
			break;
		case 3:
			modifyCommand.setOrderStatus(ORDER_STATUS_USED);
			break;
		default:
			modifyCommand.setOrderStatus(ORDER_STATUS_OUTOFDATE);
			break;
		}

		mpOrder.modifyMPOrderStatus(modifyCommand);
		getDao().update(mpOrder);
		HgLogger.getInstance().info("wuyg", "同步单个订单：成功");
		return mpOrder;
	}

	/**
	 * 查询平台订单 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param pagination
	 * @return
	 */
	public Pagination queryOrderPagination(Pagination pagination) {
		return getDao().queryOrderPagination(pagination);
	}

	/**
	 * 查询平台订单 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param qo
	 * @return
	 */
	public List<Object[]> queryOrderList(MPOrderQO qo) {
		return getDao().queryOrderList(qo);
	}

	/**
	 * 查询平台订单 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param qo
	 * @return
	 */
	public Object[] queryOrderUnique(MPOrderQO qo) {
		return getDao().queryOrderUnique(qo);
	}

}
