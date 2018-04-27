package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.common.util.QrCodeUtil;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.app.dao.DealerDao;
import hg.dzpw.app.dao.GroupTicketDao;
import hg.dzpw.app.dao.SingleTicketDao;
import hg.dzpw.app.dao.TicketOrderDao;
import hg.dzpw.app.dao.TicketPolicyDao;
import hg.dzpw.app.dao.TicketPolicySnapshotDao;
import hg.dzpw.app.dao.TouristDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.service.api.alipay.AliPayCaeChargeService;
import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.response.CloseTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.order.OrderStatus;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.pay.AliPayTransferRecord;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;
import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotChangeSingleTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformChangeGroupTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformCreateTouristCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformModifyTouristCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.dzpw.pojo.qo.TicketOrderListQo;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @类功能说明: 联票订单Service实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午6:38:48 
 * @版本：V1.0
 */
@Service
public class TicketOrderLocalService extends BaseServiceImpl<TicketOrder,TicketOrderQo,TicketOrderDao> {
	public static String modelName = "hg.dzpw.domain.model.order.TicketOrder";

	@Autowired
	private DomainEventRepository domainEventRepository;
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	@Autowired
	private TicketOrderDao dao;
	@Autowired
	private TouristDao touristDao;
	@Autowired
	private TicketPolicyDao ticketPolicyDao;
	@Autowired
	private DealerDao dealerDao;
	@Autowired
	private GroupTicketDao groupTicketDao;
	@Autowired
	private SingleTicketDao singleTicketDao;
	@Autowired
	private TicketPolicySnapshotDao ticketPolicySnapshotDao;
	@Autowired
	private TouristLocalService touristLocalService;
	@Autowired
	private GroupTicketLocalService groupTicketService;
	@Autowired
	private SingleTicketLocalService singleTicketService;
	@Autowired
	private TicketPolicyPriceCalendarLocalService calendarService;
//	@Autowired
//	private HJBTransferRecordLocalService hjbTransferRecordLocalService;
	@Autowired
	private UseRecordLocalService useRecordService;
	@Autowired
	private AliPayCaeChargeService aliPayCaeChargeService;
	@Autowired
	private AliPayTransferRecordLocalService aliPayTransferRecordLocalService;
	@Autowired
	private TicketPolicyLocalService ticketPolicyLocalService;
	
	@Override
	protected TicketOrderDao getDao() {
		return this.dao;
	}
	
	
	/**
	 * @方法功能说明：下单校验
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-17下午1:59:28
	 */
	private void checkTicketPolicyBeforeCreateOrder(CreateTicketOrderCommand command, String dealerId)throws DZPWDealerApiException{
		
		if(StringUtils.isBlank(dealerId))
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.DEALER_KEY_ERROR), 
												"缺少经销商ID", null);
		
		if(command.getBuyNum()==null || command.getBuyNum()<=0)
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.DEALER_PARAM_ERROR), 
												"缺少购买数量", null);
		
		TicketPolicy ticketPolicy = ticketPolicyDao.get(command.getTicketPolicyId());
		
		// 检查发布状态
		if(ticketPolicy==null || ticketPolicy.getStatus().getRemoved()
				|| !ticketPolicy.getStatus().isIssue())
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.TICKET_POLICY_NOT_EXISTS), 
												"门票政策已下架或已被删除", null);
		
		if(ticketPolicy.isSingleInGroup() || ticketPolicy.isSingleInSingle())
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.TICKET_POLICY_NOT_EXISTS), 
												"门票政策已下架或已被删除", null);
		
		// 检查版本号
		if (command.getTicketPolicyVersion() == null) command.setTicketPolicyVersion(1);
		if (ticketPolicy.getVersion() != null && !ticketPolicy.getVersion().equals(command.getTicketPolicyVersion()))
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.TICKET_POLICY_VERSION_ERROR),
											 	String.format("门票政策版本错误:预期%d,实际%d。", ticketPolicy.getVersion(), command.getTicketPolicyVersion()),
											 		null);
		
		Double price = null;//查询得到的票价
		// 单票检查
		if (ticketPolicy.isSingle()) {
			// 检查经销商
			// 单票时检查景区是否对经销商禁用
			DealerQo dealerQo = new DealerQo();
			if (ticketPolicy.isSingle()) {
				dealerQo.setScenicspotSettingQo(new DealerScenicspotSettingQo());
				dealerQo.getScenicspotSettingQo().setScenicSpotId(ticketPolicy.getScenicSpot().getId());
			}
			dealerQo.setId(dealerId);
			Dealer dealer = dealerDao.queryUnique(dealerQo);
			if (dealer == null
					|| !Dealer.DEALER_STATUS_USASBLE.equals(dealer.getBaseInfo().getStatus())
					|| (ticketPolicy.isSingle() && !dealer.getSetting().getUseable()))
				throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.DEALER_NO_AUTH_BUY), 
													"无权购买该景区单票", null);
			
			// 单票游玩日期检查
			if (command.getTravelDate() == null)
				throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.TRAVEL_DATE_ERROR), 
													"缺少出游日期。", null);
			
			else if (command.getTravelDate().getTime() < DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).getTime())
				throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.TRAVEL_DATE_ERROR), 
													"出游日期不能是当天之前日期。", null);
			
			//获取门票当天价格
			TicketPolicyDatePrice datePrice = calendarService.getTicketPolicyPriceCalendar(command.getTicketPolicyId(), dealer.getId(), command.getTravelDate());
			if(datePrice==null)
				throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.PRICE_ERROR), 
													"所选出游日期无有效价格信息", null);
			else
				price = datePrice.getPrice();
		}
		
		if(ticketPolicy.isGroup()){
			price = ticketPolicy.getBaseInfo().getPlayPrice();
		}
		
		//计算购买单价
		Double salePrice = command.getPrice()/command.getBuyNum();
		
		if (NumberUtils.compare(salePrice, price) != 0)
			throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.PRICE_ERROR), 
					                         	String.format("价格错误:[%s]的实际价格为 %.2f 元，而请求支付的单价却为 %.2f 元。", ticketPolicy.getBaseInfo().getName(), price, salePrice), 
					                         		null);
		
	}
	
	
	/**
	 * @方法功能说明：经销商端下单 并扣款 返回订单号
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-15下午5:08:41
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public String dealerAdminCreateTicketOrder(CreateTicketOrderCommand command, String dealerId) throws DZPWException, DZPWDealerApiException{
		
		// 下单校验
		checkTicketPolicyBeforeCreateOrder(command, dealerId);
		
		TicketPolicy policy = ticketPolicyDao.get(command.getTicketPolicyId());
		
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(dealerId);
		Dealer dealer = dealerDao.queryUnique(dealerQo);
		
		// 游客信息
		List<TouristDTO> touristDTOList = command.getTourists();
		
		// 门票单价
		Double ticketPrice = null;
		if(policy.getType()==TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
			// 获取门票当天价格
			TicketPolicyDatePrice datePrice = calendarService.getTicketPolicyPriceCalendar(command.getTicketPolicyId(), dealer.getId(), command.getTravelDate());
			if(datePrice!=null)
				ticketPrice = datePrice.getPrice();
			else
				throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.PRICE_ERROR), 
													"所选出游日期无有效价格信息", null);
		}else{
			ticketPrice = policy.getBaseInfo().getPlayPrice();
		}
		
		// 订单
		TicketOrder ticketOrder = new TicketOrder();
		List<GroupTicket> groupTickets = new ArrayList<GroupTicket>();
		
		// 订单ID生成  格式JX0001(经销商代码)+10位数字
		String ticketOrderNo = dealer.getClientInfo().getKey() + ticketOrderManager.getTicketOrderNO();
		
		// 生成门票   接口下单 门票状态为 出票成功
		setGroupTickets(ticketOrder, groupTickets, command, touristDTOList, 
							policy, ticketPrice, dealer, GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_WAIT);
						
		// 创建订单并保存
		ticketOrder.create(command, policy, dealer, groupTickets, ticketOrderNo, OrderStatus.ORDER_STATUS_PAY_WAIT);
		getDao().save(ticketOrder);
					
		// 库存
		Integer remainingQty = policy.getSellInfo().getRemainingQty();
		// 库存无限时不计算库存
		if (policy.getSellInfo().getRemainingQty()!=-1)
			remainingQty = ticketOrderManager.getRemainingQty(command.getTicketPolicyId(), command.getBuyNum(), policy.getSellInfo().getRemainingQty());
					
		// 售出数量
		Integer soldQty	 = ticketOrderManager.getSoldQty(command.getTicketPolicyId(), command.getBuyNum()) ;
		Integer oldRemainingQty = policy.getSellInfo().getRemainingQty();
		Integer oldSoldQty = policy.getSellInfo().getSoldQty();
				
		policy.getSellInfo().setRemainingQty(remainingQty);
		policy.getSellInfo().setSoldQty(soldQty);
		ticketPolicyDao.update(policy);
		
		// 下单后 库存为0 立即下架
		if (remainingQty==0){
			if (policy.getType() == TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
				ticketPolicyLocalService
						.scenicspotChangeSingleTicketPolicyStatus(new ScenicspotChangeSingleTicketPolicyStatusCommand(
								policy.getId(), false, policy.getScenicSpot()
										.getId()));
			}else{
				ticketPolicyLocalService.platformChangeGroupTicketPolicyStatus(new PlatformChangeGroupTicketPolicyStatusCommand(policy.getId(), false));
			}
		}
					
		HgLogger.getInstance().info("yangk", String.format("减库存: 门票政策库存更新[%s] (-1表示数量无限): 订单ID %s / 库存 更新前 %d | 更新后 %d / 售出数量 更新前 %d | 更新后 %d",
				policy.getId(), ticketOrder.getId(), oldRemainingQty, remainingQty, oldSoldQty, soldQty));
		
		CaeChargeParameter caeChargeParameter = new CaeChargeParameter();
		// 手续费 = 门票手续费*门票数量
		Double f = dealer.getAccountInfo()
						.getSettlementFee() 
							* command.getBuyNum();
		
		Double sum = MoneyUtil.add(command.getPrice(), f);
		// 代扣金额
		caeChargeParameter.setAmount(Double.toString(sum));
		// 订单创建时间
		caeChargeParameter.setGmt_out_order_create(DateUtil.formatDateTime(new Date()));
		
		// 商户订单号
		caeChargeParameter.setOut_order_no(ticketOrderNo);
		// 备注
		caeChargeParameter.setSubject(SystemConfig.alipaySubjectPrefix);
		// 支付账户
		caeChargeParameter.setTrans_account_out(dealer.getAccountInfo().getAccountNumber());
		// 调用支付且记录请求响应
		CaeChargeResponse response = payByAli(caeChargeParameter);
		
		if (response == null){
			throw new DZPWDealerApiException(
					PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
					PayToTicketOrderResponse.class);
		}
		
		// 支付 失败 抛异常
		if (!response.isSuccess()){
			if ("AVAILABLE_AMOUNT_NOT_ENOUGH".equals(response.getError())
					|| "USER_PAY_TYPE_MISMATCH".equals(response.getError()))
				throw new DZPWDealerApiException(
							PayToTicketOrderResponse.RESULT_MONEY_NOT_ENOUGH,
							"余额不足", PayToTicketOrderResponse.class);
			else
				throw new DZPWDealerApiException(
						PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
						PayToTicketOrderResponse.class);
		} else {
			if (!"TRADE_SUCCESS".equals(response.getStatus())
					&& !"TRADE_FINISHED".equals(response.getStatus())) {
				throw new DZPWDealerApiException(
						PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
						PayToTicketOrderResponse.class);
			}
		}
		
		
		// 支付成功后修改订单 门票状态 为出票成功 
		ticketOrder.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_OUT_SUCC);
		ticketOrder.getPayInfo().setPayDate(new Date());
		for (GroupTicket gt : ticketOrder.getGroupTickets()){
			gt.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);
			// 设置支付流水号
			gt.setPayTradeNo(response.getTrade_no());
		}
		
		getDao().update(ticketOrder);
		
		return ticketOrderNo;
	}
	
	
	/**
	 * @方法功能说明：经销商接口创建门票订单
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-28下午4:56:16
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public TicketOrderDTO createTicketOrder(CreateTicketOrderCommand command, String dealerId) throws DZPWException, DZPWDealerApiException {
		
		// 下单校验
		checkTicketPolicyBeforeCreateOrder(command, dealerId);
		
		// 查询门票政策
		TicketPolicy ticketPolicy = ticketPolicyDao.get(command.getTicketPolicyId());
		
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(dealerId);
		Dealer dealer = dealerDao.queryUnique(dealerQo);
		
		// 游客信息
	    List<TouristDTO> touristDTOList = command.getTourists();
		
	    // 门票单价
	    Double ticketPrice = null;
	    if(ticketPolicy.getType()==TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
	  	// 获取门票当天价格
	  	TicketPolicyDatePrice datePrice = calendarService.getTicketPolicyPriceCalendar(command.getTicketPolicyId(), dealer.getId(), command.getTravelDate());
	  	if(datePrice!=null)
	  		ticketPrice = datePrice.getPrice();
	  	else
	  		throw new DZPWDealerApiException(String.valueOf(DZPWDealerApiException.PRICE_ERROR), 
	  											"所选出游日期无有效价格信息", null);
	    }else{
	   	    ticketPrice = ticketPolicy.getBaseInfo().getPlayPrice();
	    }
		
	    // 订单ID生成  格式JX0001(经销商代码)+10位数字
	  	String ticketOrderNo = dealer.getClientInfo().getKey() + ticketOrderManager.getTicketOrderNO();
		
	  	// 订单
	 	TicketOrder ticketOrder = new TicketOrder();
	 	List<GroupTicket> groupTickets = new ArrayList<GroupTicket>();
		
	 	// 生成门票   接口下单 门票状态为 等待支付
	 	setGroupTickets(ticketOrder, groupTickets, command, touristDTOList, 
	 						ticketPolicy, ticketPrice, dealer, GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_WAIT);
		
		// 创建订单并保存
		ticketOrder.create(command, ticketPolicy, dealer, groupTickets, 
								ticketOrderNo, OrderStatus.ORDER_STATUS_PAY_WAIT);
		
		getDao().save(ticketOrder);
	 	
		// 库存
		Integer remainingQty = ticketPolicy.getSellInfo().getRemainingQty();
		
		//库存无限时不计算库存
		if (ticketPolicy.getSellInfo().getRemainingQty()!=-1)
			remainingQty = ticketOrderManager.getRemainingQty(command.getTicketPolicyId(), command.getBuyNum(), ticketPolicy.getSellInfo().getRemainingQty());
						
		// 售出数量
		Integer soldQty	 = ticketOrderManager.getSoldQty(command.getTicketPolicyId(), command.getBuyNum()) ;
		Integer oldRemainingQty = ticketPolicy.getSellInfo().getRemainingQty();
		Integer oldSoldQty = ticketPolicy.getSellInfo().getSoldQty();
				
		ticketPolicy.getSellInfo().setRemainingQty(remainingQty);
		ticketPolicy.getSellInfo().setSoldQty(soldQty);
		ticketPolicyDao.update(ticketPolicy);
		
		// 下单后 库存为0 立即下架
		if (remainingQty==0){
			if (ticketPolicy.getType() == TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
				ticketPolicyLocalService
						.scenicspotChangeSingleTicketPolicyStatus(new ScenicspotChangeSingleTicketPolicyStatusCommand(
								ticketPolicy.getId(), false, ticketPolicy
										.getScenicSpot().getId()));
			}else{
				ticketPolicyLocalService.platformChangeGroupTicketPolicyStatus(new PlatformChangeGroupTicketPolicyStatusCommand(ticketPolicy.getId(), false));
			}
		}

		HgLogger.getInstance().info("yangk", String.format("减库存: 门票政策库存更新[%s] (-1表示数量无限): 订单ID %s / 库存 更新前 %d | 更新后 %d / 售出数量 更新前 %d | 更新后 %d",
				ticketPolicy.getId(), ticketOrder.getId(), oldRemainingQty, remainingQty, oldSoldQty, soldQty));
		
        return DealerApiAdapter.ticketOrder.convertDTO(ticketOrder, null);
	}
	
	
	/**
	 * @方法功能说明：下单时生成groupTicket
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-29下午1:39:53
	 */
	private void setGroupTickets(TicketOrder ticketOrder, List<GroupTicket> groupTickets, 
									CreateTicketOrderCommand command, List<TouristDTO> touristDTOList, 
										TicketPolicy ticketPolicy, Double ticketPrice, Dealer dealer, Integer groupTicketStatus) throws DZPWException{
		
		for(int i=0; i<command.getBuyNum(); i++){
			
			Tourist tourist = null;
			TouristQo touristQo = new TouristQo();
			
			if(touristDTOList!=null && touristDTOList.size()>0){
				touristQo.setIdType(touristDTOList.get(i).getIdType());
				touristQo.setIdNo(touristDTOList.get(i).getIdNo());
				tourist = touristDao.queryUnique(touristQo);
				// 更新游客信息
				if (tourist == null) {
					tourist = new Tourist();
					PlatformCreateTouristCommand createTouristCommand = new PlatformCreateTouristCommand();
					createTouristCommand.setTouristId(DigestUtils.md5Hex(touristDTOList.get(i).getIdNo() + touristDTOList.get(i).getIdType()));
					createTouristCommand.setAddress(touristDTOList.get(i).getAddress());
					createTouristCommand.setAge(touristDTOList.get(i).getAge());
					createTouristCommand.setBirthday(touristDTOList.get(i).getBirthday());
					createTouristCommand.setCreateDate(new Date());
					createTouristCommand.setGender(touristDTOList.get(i).getGender());
					createTouristCommand.setIdNo(touristDTOList.get(i).getIdNo());
					createTouristCommand.setIdType(touristDTOList.get(i).getIdType());
					createTouristCommand.setName(touristDTOList.get(i).getName());
					createTouristCommand.setNation(touristDTOList.get(i).getNation());
					createTouristCommand.setBuyAmountTotal(ticketPrice);
					if (touristDTOList.get(i).getTelephone()!=null)
						createTouristCommand.setTelephone(touristDTOList.get(i).getTelephone());
					
					tourist.create(createTouristCommand);
					touristLocalService.save(tourist);
				} else {
					PlatformModifyTouristCommand modifyTouristCommand = new PlatformModifyTouristCommand();
					modifyTouristCommand.setAddress(touristDTOList.get(i).getAddress());
					modifyTouristCommand.setBirthday(touristDTOList.get(i).getBirthday());
					modifyTouristCommand.setCreateDate(new Date());
					modifyTouristCommand.setGender(touristDTOList.get(i).getGender());
					modifyTouristCommand.setIdNo(touristDTOList.get(i).getIdNo());
					modifyTouristCommand.setIdType(touristDTOList.get(i).getIdType());
					modifyTouristCommand.setName(touristDTOList.get(i).getName());
					modifyTouristCommand.setBuyAmountTotal(ticketPrice);
					modifyTouristCommand.setNation(touristDTOList.get(i).getNation());
					if (touristDTOList.get(i).getTelephone()!=null)
						modifyTouristCommand.setTelephone(touristDTOList.get(i).getTelephone());
					
					tourist.modify(modifyTouristCommand);
					touristLocalService.update(tourist);
				}
			}
			
			//创建
			GroupTicket groupTicket = new GroupTicket();
			
			String ticketNo = ticketOrderManager.getTicketNo();
			
			// 只有独立单票的基准价有价格日历，其他都为null
			Double dateStandardPrice = null;
			if (ticketPolicy.isSingle()) {
				TicketPolicyDatePrice policyDatePrice = calendarService.getTicketPolicyPriceCalendar(command.getTicketPolicyId(), null, command.getTravelDate());
				if (policyDatePrice != null)
					dateStandardPrice = policyDatePrice.getPrice();
			}
			
			Integer singleTicketStatus = tourist==null ? 
											SingleTicketStatus.SINGLE_TICKET_CURRENT_TOBE_ACTIVE : 
												SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE;
			
			// 设置门票二维码内容
			String content = ticketNo;
			BitMatrix bm = QrCodeUtil.getBitMatrix(content);
			// 上传二维码
			String qrCodeUrl = null;
			BufferedImage bfImage = MatrixToImageWriter.toBufferedImage(bm);
			
			// 用tomcat的temp文件夹
			String tempPath = SimpleFileUtil.getPathToRename("."+QrCodeUtil.IMAGE_TYPE);
			
			File file = new File(tempPath);
	    	try {
	    		// 次方法不创建目录
	    		ImageIO.write(bfImage, QrCodeUtil.IMAGE_TYPE, file);
				FdfsFileUtil.init();
		    	FdfsFileInfo info = FdfsFileUtil.upload(file, new HashMap<String, String>());
		    	qrCodeUrl = SystemConfig.imageHost + info.getUri();
		    	// 删除临时图片
		    	file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			groupTicket.create(ticketNo, ticketOrder, ticketPrice, 
							   command.getTravelDate(), tourist, ticketPolicy, dateStandardPrice, 
							   groupTicketStatus, singleTicketStatus, qrCodeUrl);
			
			// 设置门票的经销商手续费
			groupTicket.setDealerSettlementFee(dealer.getAccountInfo().getSettlementFee());
			groupTickets.add(groupTicket);
		}
	}
	
	
	/**
	 * @方法功能说明：关闭订单后续处理
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:48:07
	 * @参数：@param ticketOrder
	 */
	private void closeTicketOrderAfterProcess(TicketOrder ticketOrder) {
		
		// 修改被关闭订单下门票状态
		GroupTicketQo gtq = new GroupTicketQo();
		gtq.setTicketOrdeQo(new TicketOrderQo());
		gtq.getTicketOrdeQo().setId(ticketOrder.getId());
		List<GroupTicket> gl = groupTicketDao.queryList(gtq);
		for (GroupTicket gt : gl){
			gt.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE);
			// 修改门票下景区的状态
			SingleTicketQo stq = new SingleTicketQo();
			gtq.setTicketOrdeQo(null);
			gtq.setId(gt.getId());
			stq.setGroupTicketQo(gtq);
			
			List<SingleTicket> sl = singleTicketDao.queryList(stq);
			for (SingleTicket st : sl)
				st.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_USE_CURRENT_INVALID_II);
				
			this.singleTicketDao.updateList(sl);
		}
		groupTicketDao.updateList(gl);
		
		TicketPolicy ticketPolicy = ticketOrder.getTicketPolicy();
		
		Integer remainingQty = ticketPolicy.getSellInfo().getRemainingQty();
		// 库存返还
		//-1 无限库存时 不计算库存
		if (ticketPolicy.getSellInfo().getRemainingQty()!=-1)
		remainingQty = ticketOrderManager.returnRemainingQty(ticketPolicy.getId(), 
																		ticketOrder.getBaseInfo().getTicketNo(), 
																			ticketPolicy.getSellInfo().getRemainingQty());
		// 售出数量返还
		Integer soldQty = ticketOrderManager.returnSoldQty(ticketPolicy.getId(), 
																ticketOrder.getBaseInfo().getTicketNo());
		
		Integer oldRemainingQty = ticketPolicy.getSellInfo().getRemainingQty();
		Integer oldSoldQty = ticketPolicy.getSellInfo().getSoldQty();
		ticketPolicy.getSellInfo().setRemainingQty(remainingQty);
		ticketPolicy.getSellInfo().setSoldQty(soldQty);
		ticketPolicyDao.update(ticketPolicy);

		HgLogger.getInstance().info("zhurz", String.format("库存返还: 门票政策库存更新[%s] (-1表示数量无限): 订单ID %s / 库存 更新前 %d | 更新后 %d / 售出数量 更新前 %d | 更新后 %d",
			ticketPolicy.getId(), ticketOrder.getId(), oldRemainingQty, remainingQty, oldSoldQty, soldQty));

	}
	
	
	/**
	 * @方法功能说明：关闭订单(已付款的订单不能关闭)
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27上午10:52:52
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void closeTicketOrder(CloseTicketOrderCommand command, String dealerId) throws DZPWDealerApiException {

		TicketOrderQo qo = new TicketOrderQo();
		qo.setDealerId(dealerId);
		qo.setDealerOrderId(command.getDealerOrderId());
		TicketOrder ticketOrder = getDao().queryUnique(qo);
		
		if (ticketOrder == null)
			throw new DZPWDealerApiException(
								CloseTicketOrderResponse.RESULT_ORDER_NOT_EXISTS, "订单不存在",
								CloseTicketOrderResponse.class);
		
		switch (ticketOrder.close()) {
		// 成功
		case 0:
			ticketOrder.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_DEAL_CLOSE);
			getDao().update(ticketOrder);
			
			GroupTicketQo gto = new GroupTicketQo();
			gto.setTicketOrdeQo(new TicketOrderQo());
			gto.getTicketOrdeQo().setId(ticketOrder.getId());
			List<GroupTicket> list = groupTicketService.queryList(gto);
			for(GroupTicket gt : list){
				gt.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE);
				groupTicketService.update(gt);
			}
			
			closeTicketOrderAfterProcess(ticketOrder);
			break;
		// 已支付
		case 1:
			throw new DZPWDealerApiException(
								CloseTicketOrderResponse.RESULT_ORDER_PAID, "不能关闭已经支付的订单",
								CloseTicketOrderResponse.class);
		// 已关闭
		case 2:
			throw new DZPWDealerApiException(
								CloseTicketOrderResponse.RESULT_ORDER_CLOSED, "不能关闭已经 闭的订单",
								CloseTicketOrderResponse.class);
		}
	}
	
	
	/**
	 * @方法功能说明：为订单付款
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27上午10:53:09
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public DZPWDealerApiException payToTicketOrder(PayToTicketOrderCommand command, String dealerId){

		TicketOrderQo qo = new TicketOrderQo();
		qo.setDealerId(dealerId);
		qo.setDealerOrderId(command.getDealerOrderId());
		TicketOrder ticketOrder = getDao().queryUnique(qo);
		
		if (ticketOrder == null)
			return new DZPWDealerApiException(PayToTicketOrderResponse.RESULT_ORDER_NOT_EXISTS, "订单不存在",
											 PayToTicketOrderResponse.class);

		switch (ticketOrder.payTo(command)) {
			// 可以支付  
			case 0:
				CaeChargeParameter caeChargeParameter = new CaeChargeParameter();
				// 手续费 = 门票手续费*门票数量
				Double f = ticketOrder.getBaseInfo().getFromDealer()
								.getAccountInfo().getSettlementFee() 
									* ticketOrder.getBaseInfo().getTicketNo();
				
				Double sum = MoneyUtil.add(ticketOrder.getPayInfo().getPrice(), f);
				// 代扣金额
				caeChargeParameter.setAmount(Double.toString(sum));
						
				// 订单创建时间
				caeChargeParameter.setGmt_out_order_create(DateUtil
						.formatDateTime(ticketOrder.getBaseInfo().getCreateDate()));
				// 商户订单号
				caeChargeParameter.setOut_order_no(ticketOrder.getId());
				// 备注
				caeChargeParameter.setSubject(SystemConfig.alipaySubjectPrefix);
				// 支付账户
				caeChargeParameter.setTrans_account_out(ticketOrder.getBaseInfo()
						.getFromDealer().getAccountInfo().getAccountNumber());
				
				// 调用支付宝代扣接口
				CaeChargeResponse response = payByAli(caeChargeParameter);
				
				if (response == null){
					return new DZPWDealerApiException(
							PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
							PayToTicketOrderResponse.class);
				}
				
				// 支付 失败 抛异常
				if (!response.isSuccess()){
					
					dao.evict(ticketOrder);
					if ("AVAILABLE_AMOUNT_NOT_ENOUGH".equals(response.getError())
							|| "USER_PAY_TYPE_MISMATCH".equals(response.getError()))
						return new DZPWDealerApiException(
									PayToTicketOrderResponse.RESULT_MONEY_NOT_ENOUGH,
									"余额不足", PayToTicketOrderResponse.class);
					else
						return new DZPWDealerApiException(
								PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
								PayToTicketOrderResponse.class);
					
				} else {
					if (!"TRADE_SUCCESS".equals(response.getStatus())
							&& !"TRADE_FINISHED".equals(response.getStatus())) {
						dao.evict(ticketOrder);
						return new DZPWDealerApiException(
								PayToTicketOrderResponse.RESULT_PAY_ERROR, "支付失败",
								PayToTicketOrderResponse.class);
					}
				}
				
				// 代扣成功
				ticketOrder.getPayInfo().setPayDate(new Date());
				// 修改订单状态
				ticketOrder.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_OUT_SUCC);
				dao.update(ticketOrder);
				
				// 查询订单下的门票
				GroupTicketQo gtqo = new GroupTicketQo();
				gtqo.setTicketOrdeQo(new TicketOrderQo());
				gtqo.getTicketOrdeQo().setId(ticketOrder.getId());
				List<GroupTicket> lg = groupTicketDao.queryList(gtqo);
				
				// 修改门票状态
				for (GroupTicket gt : lg){
					gt.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);
					// 设支付流水
					gt.setPayTradeNo(response.getTrade_no());
				}
				groupTicketDao.saveList(lg);
				break;
			// 订单已支付
			case 1:
				return new DZPWDealerApiException(
						PayToTicketOrderResponse.RESULT_ORDER_PAID, "不能为已经付款的订单付款",
						PayToTicketOrderResponse.class);
			// 订单已关闭
			case 2:
				return new DZPWDealerApiException(
						PayToTicketOrderResponse.RESULT_ORDER_CLOSED, "不能为已经关闭的订单付款",
						PayToTicketOrderResponse.class);
		}
		return null;
	}
	
	/**
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void refundTicketOrder(DzpwTransferToDealerCommand command)throws DZPWDealerApiException, Exception{
		
		TicketOrderQo toq = new TicketOrderQo();
		toq.setOrderId(command.getOrderId());
		TicketOrder ticketOrder = getDao().queryUnique(toq);
		
		//检查订单对应票号有没有入园记录  否则退款失败
		GroupTicketQo gtq = new GroupTicketQo();
		gtq.setTicketOrdeQo(toq);
		List<GroupTicket> list = groupTicketService.queryList(gtq);
		
		UseRecordQo u = new UseRecordQo();
		for(GroupTicket gt : list){
			u.setGroupTicketId(gt.getId());
			Integer x = useRecordService.queryCount(u);
			if(x>0)
				throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ORDER_USE, "订单已消费", RefundResponse.class);
		}
		
		
//		HJBTransferResponseDto hjbDto = hjbTransferRecordLocalService.transferToDealer(command);
		HJBTransferResponseDto hjbDto = new HJBTransferResponseDto();
		
		if(hjbDto==null)
			throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ERROR, "网络异常", RefundResponse.class);

		if("1".equals(hjbDto.getStatus())){
			//更新订单状态为已退款
//			ticketOrder.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_REFUND);  1.4版本处理
//			ticketOrder.getPayInfo().setPaid(TicketOrderPayInfo.PAID_REFUND);
			getDao().update(ticketOrder);
		}else{
			getDao().evict(ticketOrder);
			if("err_015".equals(hjbDto.getErrCode()))
				throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_MONEY_ENOUGH, "付款方(DZPW)余额不足", RefundResponse.class);
			else
				throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ERROR, "退款失败", RefundResponse.class);
			
		}
		
	}
	*/

	
	/**
	 * @方法功能说明：关闭超过3小时未支付的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:51:11
	 */
	@Transactional(rollbackFor = Exception.class)
	public void closeTimeoutTicketOrder() {
		
		TicketOrderQo qo = new TicketOrderQo();
		qo.setDealerQo(new DealerQo());
		qo.setStatus(OrderStatus.ORDER_STATUS_PAY_WAIT);
		qo.setCreateEndDate(DateUtils.addHours(new Date(), -3));

		List<TicketOrder> ticketOrders = getDao().queryList(qo, 10000);
		
		int n = 0;
		GroupTicketQo gqo = new GroupTicketQo();
		
		for (TicketOrder ticketOrder : ticketOrders) {
			
			CloseTicketOrderCommand command = new CloseTicketOrderCommand();
			command.setDealerOrderId(ticketOrder.getBaseInfo().getDealerOrderId());
			
			// 是否关闭
			if (ticketOrder.close() == OrderStatus.ORDER_STATUS_PAY_WAIT) {
				ticketOrder.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_DEAL_CLOSE);
				getDao().update(ticketOrder);
				closeTicketOrderAfterProcess(ticketOrder);
				
				// 经销商通过接口下单的订单推送消息
				if (StringUtils.isNotBlank(ticketOrder.getBaseInfo().getDealerOrderId()))
					DealerApiEventPublisher.publish(new PublishEventRequest(
															PublishEventRequest.EVENT_TICKET_ORDER_CLOSE, 
																ticketOrder.getId(), ticketOrder.getBaseInfo().getFromDealer().getClientInfo().getKey()));
				
				// 更新groupTicket singleTicket状态
				qo.setStatus(null);
				qo.setCreateEndDate(null);
				qo.setId(ticketOrder.getId());
				gqo.setTicketOrdeQo(qo);
				gqo.setSingleTicketQo(new SingleTicketQo());
				
				// 关闭时间
				Date closeDate = new Date();
				
				// 更新订单下groupTicket状态为订单关闭
				List<GroupTicket> gl = groupTicketService.queryList(gqo);
				if (gl!=null){
					for (GroupTicket g : gl){
						g.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE);
						g.getStatus().setCloseDate(closeDate);
						groupTicketService.update(g);
						
						// 更新groupTicket下的singleTicket状态
						if (g.getSingleTickets()!=null){
							for (SingleTicket s : g.getSingleTickets()){
								s.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_USE_CURRENT_INVALID_II);//超时为支付失效
								singleTicketService.update(s);
							}
						}
					}
				}
				
				HgLogger.getInstance().info("zhurz", String.format("超时未支付关闭订单: [%s] || 且订单下门票失效", ticketOrder.getId()));
			}
			
			if (++n % 50 == 0) {
				getDao().flush();
				getDao().clear();
			}
		}

	}
	
	
	/**
	 * @方法功能说明：门票订单列表视图分页查询
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-2下午5:44:06
	 */
	@Transactional(rollbackFor = Exception.class)
	public Pagination queryListVoPagination(TicketOrderListQo qo) {
		return getDao().queryListVoPagination(qo);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public TicketOrder load(String id) {
		TicketOrder ticketOrder = dao.load(id);
		Hibernate.initialize(ticketOrder.getTicketPolicy());
		Hibernate.initialize(ticketOrder.getBaseInfo().getFromDealer());
		return ticketOrder;
	}

	
	/**
	 * @方法功能说明：调用支付且记录请求响应
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-18上午11:31:15
	 * @参数：@param ticketOrder
	 * @参数：@return
	 * @return:CaeChargeResponse
	 */
	public CaeChargeResponse payByAli(CaeChargeParameter caeChargeParameter){
		
		// 设置代扣请求的配置参数
		aliPayCaeChargeService.setConfigParameters(caeChargeParameter);
		
		// 记录请求
		AliPayTransferRecord record = aliPayTransferRecordLocalService.recordRequest(caeChargeParameter);
		
		// 请求代扣
		CaeChargeResponse response = aliPayCaeChargeService.chargeHttpRequest(caeChargeParameter);
		
		// 记录响应
		aliPayTransferRecordLocalService.recordResponse(response, record.getId());
		
		return response;
	}

}