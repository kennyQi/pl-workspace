package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.app.dao.ScenicSpotDao;
import hg.dzpw.app.dao.TicketPolicyDao;
import hg.dzpw.app.dao.TicketPolicyPriceCalendarDao;
import hg.dzpw.app.dao.TicketPolicyPriceCalendarSnapshotDao;
import hg.dzpw.app.dao.TicketPolicySnapshotDao;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendarSnapshot;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotChangeSingleTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotModifySingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotRemoveSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformChangeGroupTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformCreateGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformModifyGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.SingleTicketPolicy;
import hg.dzpw.pojo.command.platform.ticketpolicy.CheckTicketPolicyUnPassCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCheckTicketPolicyPassCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCloseTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCreateTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformIssueTicketPolicyCommand;
import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：门票政策服务
 * @类修改者：
 * @修改日期：2014-11-12下午3:17:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-11-12下午3:17:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TicketPolicyLocalService extends BaseServiceImpl<TicketPolicy, TicketPolicyQo, TicketPolicyDao> {
	
	public static String modelName = "hg.dzpw.domain.model.policy.TicketPolicy";
	
	@Autowired
	private TicketPolicyDao dao;
	@Autowired
	private TicketPolicySnapshotDao ticketPolicySnapshotDao;
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Autowired
	private TicketPolicyPriceCalendarDao calendarDao;
	@Autowired
	private TicketPolicyPriceCalendarSnapshotDao calendarSnapshotDao;
	
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	
	@Autowired
	ScenicSpotLocalService scenicSpotService;
	
	@Override
	protected TicketPolicyDao getDao() {
		return dao;
	}

	/**
	 * @方法功能说明：创建门票政策
	 * @修改者名字：zzx
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：@see platformCreateGroupTicketPolicy
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Deprecated
	public void createTicketPolicy(PlatformCreateTicketPolicyCommand command) {
		TicketPolicy ticketPolicy = new TicketPolicy();
		ticketPolicy.create(command);
		TicketPolicySnapshot ticketPolicySnapshot = ticketPolicy.getLastSnapshot();
		ticketPolicySnapshotDao.save(ticketPolicySnapshot);
		getDao().save(ticketPolicy);
		//余票放入redis中
		ticketOrderManager.setTotalQty(ticketPolicy.getId(), ticketPolicy.getSellInfo().getRemainingQty());
        DomainEvent event = new DomainEvent(modelName,"createTicketPolicy",JSON.toJSONString(command));
        domainEventRepository.save(event);
		for (int i = 0; i < command.getScenicSpotIds().length; i++) {
			TicketPolicy singleTicketPolicy = new TicketPolicy();
			PlatformCreateSingleTicketPolicyCommand createSingleTicketPolicyCommand = new PlatformCreateSingleTicketPolicyCommand();
			createSingleTicketPolicyCommand.setScenicSpotId(command.getScenicSpotIds()[i]);
			createSingleTicketPolicyCommand.setScenicSpotPrice(command.getScenicSpotPrices()[i]);
			singleTicketPolicy.createSingleTicketPolicy(createSingleTicketPolicyCommand, ticketPolicy);
			TicketPolicySnapshot singleTicketPolicySnapshot = singleTicketPolicy.getLastSnapshot();
			ticketPolicySnapshotDao.save(singleTicketPolicySnapshot);
			getDao().save(singleTicketPolicy);
			DomainEvent singleEvent = new DomainEvent(modelName,"createTicketPolicy",JSON.toJSONString(createSingleTicketPolicyCommand));
	        domainEventRepository.save(singleEvent);
	        ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
	        scenicSpotQo.setId(createSingleTicketPolicyCommand.getScenicSpotId());
	        ScenicSpot scenicSpot =  scenicSpotDao.queryUnique(scenicSpotQo);
	        //计算景区的联票总数
	        scenicSpot.setGroupTicketNumber(scenicSpot.getGroupTicketNumber()+ticketPolicy.getSellInfo().getRemainingQty());
	        scenicSpotDao.update(scenicSpot);
		}
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：审核通过门票政策
	 * @修改者名字：zzx
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Deprecated
	public void checkTicketPolicyPass(PlatformCheckTicketPolicyPassCommand command)
			throws DZPWException {
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());
		ticketPolicy.checkPass();
		getDao().save(ticketPolicy);
		DomainEvent event = new DomainEvent(modelName,"checkTicketPolicyPass",JSON.toJSONString(command));
		domainEventRepository.save(event);
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：审核不通过门票政策
	 * @修改者名字：zzx
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Deprecated
	public void checkTicketPolicyUnPass(CheckTicketPolicyUnPassCommand command)
			throws DZPWException {
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());
		ticketPolicy.checkUnPass();
		getDao().save(ticketPolicy);
		DomainEvent event = new DomainEvent(modelName,"checkTicketPolicyUnPass",JSON.toJSONString(command));
		domainEventRepository.save(event);
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：发布门票政策
	 * @修改者名字：zzx
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：@see platformChangeGroupTicketPolicyStatus
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Deprecated
	public void issueTicketPolicy(PlatformIssueTicketPolicyCommand command)
			throws DZPWException {
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());
		ticketPolicy.issue();
		getDao().save(ticketPolicy);
		DomainEvent event = new DomainEvent(modelName,"issueTicketPolicy",JSON.toJSONString(command));
		domainEventRepository.save(event);
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明：下架门票政策
	 * @修改者名字：zzx
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：@see platformChangeGroupTicketPolicyStatus
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Deprecated
	public void closeTicketPolicy(PlatformCloseTicketPolicyCommand command)
			throws DZPWException {
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());
		ticketPolicy.close();
		getDao().save(ticketPolicy);
		DomainEvent event = new DomainEvent(modelName,"closeTicketPolicy",JSON.toJSONString(command));
		domainEventRepository.save(event);
	}

	/**
	 * 根据门票政策ID获取门票政策，并检查是否存在，不存在时抛出异常
	 * @param id
	 * @return
	 * @throws DZPWException
	 */
	public TicketPolicy getAndCheckTicketPolicy(String id) throws DZPWException {
		if (id == null)
			throw new DZPWException(DZPWException.TICKET_POLICY_ID_IS_NULL, "门票政策ID不能为空");
		
		TicketPolicy ticketPolicy = getDao().get(id);
		if (ticketPolicy == null)
			throw new DZPWException(DZPWException.TICKET_POLICY_ID_IS_NULL,	"门票政策不存在");
		
		return ticketPolicy;
	}
	
	/**
	 * @方法功能说明：创建独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-9下午4:01:28
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:String			门票政策ID
	 * @throws
	 */
	public String scenicspotCreateSingleTicketPolicy(ScenicspotCreateSingleTicketPolicyCommand command)	throws DZPWException {
		
		ScenicSpot scenicSpot = scenicSpotService.getAndCheckScenicSpot(command.getScenicSpotId());
		
		TicketPolicy ticketPolicy = new TicketPolicy();
		ticketPolicy.scenicspotCreateSingleTicketPolicy(command, scenicSpot);
		
		getDao().save(ticketPolicy);
		
		return ticketPolicy.getId();
	}

	/**
	 * @方法功能说明：修改独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12下午4:26:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotModifySingleTicketPolicy(ScenicspotModifySingleTicketPolicyCommand command) throws DZPWException{

		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command
				.getTicketPolicyId());

		checkSingleTicketPolicyModify(ticketPolicy, command, true);

		ticketPolicy.scenicspotModifySingleTicketPolicy(command);
		
		getDao().update(ticketPolicy);
		
		// 修改redis中库存数
		ticketOrderManager.setTotalQty(command.getTicketPolicyId(), command.getRemainingQty());
	}

	/**
	 * @方法功能说明：删除独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12下午4:26:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotRemoveSingleTicketPolicy(ScenicspotRemoveSingleTicketPolicyCommand command)
			throws DZPWException {
		
		if (command.getTicketPolicyId() == null || command.getTicketPolicyId().size() == 0)
			return;
		
		TicketPolicyQo qo = new TicketPolicyQo();
		qo.setIds(command.getTicketPolicyId());
		List<TicketPolicy> policies = getDao().queryList(qo);
		
		for (TicketPolicy policy : policies) {
			policy.scenicspotRemoveSingleTicketPolicy(command);
			getDao().update(policy);
		}
		
	}

	/**
	 * @方法功能说明：改变独立单票禁用启用状态（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12下午4:26:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotChangeSingleTicketPolicyStatus(ScenicspotChangeSingleTicketPolicyStatusCommand command) throws DZPWException {
		
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());

		checkSingleTicketPolicyModify(ticketPolicy, command, false);
		
		TicketPolicyPriceCalendarQo calendarQo = new TicketPolicyPriceCalendarQo();
		TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
		calendarQo.setTicketPolicyQo(ticketPolicyQo);
		calendarQo.setStandardPrice(true);
		ticketPolicyQo.setId(command.getTicketPolicyId());

		// 检查是否拥有基准价价格日历
		if (command.getActive() != null && command.getActive())
			if (calendarDao.queryCount(calendarQo) == 0)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "启用失败：门票政策未设置基准价价格日历。");
		

		boolean needSnapshot = ticketPolicy.scenicspotChangeSingleTicketPolicyStatus(command);
		getDao().update(ticketPolicy);
		getDao().flush();
		
		// 门票下架通知
		if (!ticketPolicy.getStatus().isIssue())
			DealerApiEventPublisher.publish(new PublishEventRequest(
					PublishEventRequest.EVENT_TICKET_POLICY_FINISH, ticketPolicy.getId()));

		// 不需要快照
		if (!needSnapshot) return;
		
		// 更新库存
		ticketOrderManager.setTotalQty(ticketPolicy.getId(), ticketPolicy.getSellInfo().getRemainingQty());

		// 启用后生成快照
		TicketPolicySnapshot snapshot = new TicketPolicySnapshot();
		snapshot.createSnapshot(ticketPolicy);
		ticketPolicySnapshotDao.save(snapshot);
		
		// 价格日历快照
		calendarQo.setStandardPrice(null);
		
		List<TicketPolicyPriceCalendar> calendars = calendarDao.queryList(calendarQo);
		for (TicketPolicyPriceCalendar calendar : calendars) {
			TicketPolicyPriceCalendarSnapshot calendarSnapshot = new TicketPolicyPriceCalendarSnapshot();
			calendarSnapshot.createSnapshot(calendar, snapshot);
			calendarSnapshotDao.save(calendarSnapshot);
		}
		
		getDao().update(ticketPolicy);
		getDao().flush();

		// 门票上架通知
		if (ticketPolicy.getStatus().isIssue())
			DealerApiEventPublisher.publish(new PublishEventRequest(
					PublishEventRequest.EVENT_TICKET_POLICY_ISSUE, ticketPolicy.getId()));

	}

	/**
	 * @方法功能说明：运营端创建联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18上午10:41:04
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public String platformCreateGroupTicketPolicy(PlatformCreateGroupTicketPolicyCommand command)throws DZPWException {

		if (command.getSingleTicketPolicies() == null || command.getSingleTicketPolicies().size() < 2)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "联票至少应该包含二个景区");
		
		//查询联票下景区
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setIds(new ArrayList<String>());
		for (SingleTicketPolicy stp : command.getSingleTicketPolicies())
			scenicSpotQo.getIds().add(stp.getScenicSpotId());
		
		List<ScenicSpot> scenicSpots = scenicSpotService.queryList(scenicSpotQo);

		Map<String, ScenicSpot> scenicSpotMap = new HashMap<String, ScenicSpot>();
		for (ScenicSpot scenicSpot : scenicSpots)
			scenicSpotMap.put(scenicSpot.getId(), scenicSpot);

		TicketPolicy ticketPolicy = new TicketPolicy();
		ticketPolicy.platformCreateGroupTicketPolicy(command, scenicSpotMap);

		getDao().save(ticketPolicy);

		return ticketPolicy.getId();

	}
	
	/**
	 * @方法功能说明：运营端修改联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18上午10:40:49
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void platformModifyGroupTicketPolicy(PlatformModifyGroupTicketPolicyCommand command) throws DZPWException {

		if (command.getSingleTicketPolicies() == null || command.getSingleTicketPolicies().size() < 2)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "联票至少应该包含二个景区");
		
		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());

		checkGroupTicketPolicyModify(ticketPolicy, true);
		
		//查询联票下景区
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setIds(new ArrayList<String>());
		for (SingleTicketPolicy stp : command.getSingleTicketPolicies())
			scenicSpotQo.getIds().add(stp.getScenicSpotId());
		
		List<ScenicSpot> scenicSpots = scenicSpotService.queryList(scenicSpotQo);

		Map<String, ScenicSpot> scenicSpotMap = new HashMap<String, ScenicSpot>();
		for (ScenicSpot scenicSpot : scenicSpots)
			scenicSpotMap.put(scenicSpot.getId(), scenicSpot);
		
		ticketPolicy.platformModifyGroupTicketPolicy(command, scenicSpotMap);
		
		getDao().update(ticketPolicy);
		
		// 修改redis中库存数
		ticketOrderManager.setTotalQty(command.getTicketPolicyId(), command.getRemainingQty());
	}

	
	/**
	 * @方法功能说明：检查独立单票政策是否可操作
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午5:20:45
	 * @修改内容：
	 * @参数：@param ticketPolicy				独立单票政策
	 * @参数：@param command
	 * @参数：@param checkIssue				是否需要检查上架状态
	 * @参数：@param message					不可编辑时的提示消息
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	void checkSingleTicketPolicyModify(TicketPolicy ticketPolicy, DZPWMerchantBaseCommand command, boolean checkIssue, String... message) throws DZPWException{

		// 权限检查
		if (ticketPolicy == null || ticketPolicy.getScenicSpot() == null
				|| !StringUtils.equals(command.getScenicSpotId(), ticketPolicy.getScenicSpot().getId()))
			throw new DZPWException(DZPWException.NO_AUTH, "无权操作");
		
		// 门票类型检查
		if (ticketPolicy.getType() == null
				|| ticketPolicy.getType() != TicketPolicy.TICKET_POLICY_TYPE_SINGLE.intValue())
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "此门票非独立单票类型");
		
		// 是否可编辑检查
		if (checkIssue && ticketPolicy.getStatus().isIssue())
			throw new DZPWException(DZPWException.SINGLE_TICKET_POLICY_ISSUE,
					message.length >= 1 ? message[0] : "启用状态的门票政策不能修改。");

	}
	
	
	/**
	 * @方法功能说明：检查联票政策是否可操作
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午5:21:43
	 * @修改内容：
	 * @参数：@param ticketPolicy			联票
	 * @参数：@param checkIssue			是否需要检查上架状态
	 * @参数：@param message				不可编辑时的提示消息
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	void checkGroupTicketPolicyModify(TicketPolicy ticketPolicy, boolean checkIssue, String... message) throws DZPWException{

		// 门票类型检查
		if (ticketPolicy.getType() == null
				|| ticketPolicy.getType() != TicketPolicy.TICKET_POLICY_TYPE_GROUP.intValue())
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "此门票非联票类型");
		
		// 是否可编辑检查
		if (checkIssue && ticketPolicy.getStatus().isIssue())
			throw new DZPWException(DZPWException.SINGLE_TICKET_POLICY_ISSUE,
					message.length >= 1 ? message[0] : "启用状态的联票政策不能修改。");
	}
	
	
	/**
	 * @方法功能说明：运营端启用禁用联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18上午11:06:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void platformChangeGroupTicketPolicyStatus(
			PlatformChangeGroupTicketPolicyStatusCommand command) throws DZPWException {

		TicketPolicy ticketPolicy = getAndCheckTicketPolicy(command.getTicketPolicyId());

		checkGroupTicketPolicyModify(ticketPolicy, false);

		boolean needSnapshot = ticketPolicy.platformChangeGroupTicketPolicyStatus(command);
		getDao().update(ticketPolicy);
		getDao().flush();
		
		// 门票下架通知
		if (!ticketPolicy.getStatus().isIssue())
			DealerApiEventPublisher.publish(new PublishEventRequest(
					PublishEventRequest.EVENT_TICKET_POLICY_FINISH, ticketPolicy.getId()));
		
		// 不需要快照
		if (!needSnapshot) return;
		
		// 更新库存
		ticketOrderManager.setTotalQty(ticketPolicy.getId(), ticketPolicy.getSellInfo().getRemainingQty());

		// 启用后生成快照
		TicketPolicySnapshot snapshot = new TicketPolicySnapshot();
		snapshot.createSnapshot(ticketPolicy);
		ticketPolicySnapshotDao.save(snapshot);
		
//		// 价格日历快照
//		TicketPolicyPriceCalendarQo calendarQo = new TicketPolicyPriceCalendarQo();
//		TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
//		calendarQo.setTicketPolicyQo(ticketPolicyQo);
//		ticketPolicyQo.setId(command.getTicketPolicyId());
//		calendarQo.setStandardPrice(null);
//		
//		List<TicketPolicyPriceCalendar> calendars = calendarDao.queryList(calendarQo);
//		for (TicketPolicyPriceCalendar calendar : calendars) {
//			TicketPolicyPriceCalendarSnapshot calendarSnapshot = new TicketPolicyPriceCalendarSnapshot();
//			calendarSnapshot.createSnapshot(calendar, snapshot);
//			calendarSnapshotDao.save(calendarSnapshot);
//		}
		
		getDao().update(ticketPolicy);
		getDao().flush();
		
		// 门票上架通知
		if (ticketPolicy.getStatus().isIssue())
			DealerApiEventPublisher.publish(new PublishEventRequest(
					PublishEventRequest.EVENT_TICKET_POLICY_ISSUE, ticketPolicy.getId()));

	}

	/**
	 * @方法功能说明：根据销售日期更新门票状态
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-1上午10:51:51
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void updateTicketPolicyStatus() {
		getDao().updateTicketPolicyStatus();
	}

	public Pagination queryPaginationApi(Pagination pagination) {
		return getDao().queryPaginationApi(pagination);
	}
}
