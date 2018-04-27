package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.dzpw.app.common.util.DatePriceCalendarUtils;
import hg.dzpw.app.dao.DealerDao;
import hg.dzpw.app.dao.TicketPolicyDao;
import hg.dzpw.app.dao.TicketPolicyPriceCalendarDao;
import hg.dzpw.app.dao.TicketPolicyPriceCalendarSnapshotDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarSnapshotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendarSnapshot;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotDeleteSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotSetSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformDeleteGroupTicketPolicyPriceCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformSetGroupTicketPolicyPriceCommand;
import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：门票政策价格日历服务
 * @类修改者：
 * @修改日期：2015-3-5下午2:24:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-5下午2:24:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TicketPolicyPriceCalendarLocalService extends BaseServiceImpl<TicketPolicyPriceCalendar, TicketPolicyPriceCalendarQo, TicketPolicyPriceCalendarDao> {

	@Autowired
	private TicketPolicyPriceCalendarSnapshotDao snapshotDao;
	
	@Autowired
	private TicketPolicyPriceCalendarDao dao;
	
	@Autowired
	private DealerDao dealerDao;
	@Autowired
	private TicketPolicyDao ticketPolicyDao;
	
	@Autowired
	private TicketPolicyLocalService ticketPolicyService;

	@Override
	protected TicketPolicyPriceCalendarDao getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：获取价格日历MAP
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午3:46:15
	 * @修改内容：
	 * @参数：@param dealerId
	 * @参数：@param ticketPolicyIds
	 * @参数：@return
	 * @return:Map<String,TicketPolicyPriceCalendar>
	 * @throws
	 */
	public Map<String, TicketPolicyPriceCalendar> getTicketPolicyPriceCalendarMap(
			String dealerId, String... ticketPolicyIds) {
		
		Map<String, TicketPolicyPriceCalendar> map = new HashMap<String, TicketPolicyPriceCalendar>();

		if (ticketPolicyIds == null || ticketPolicyIds.length == 0)
			return map;

		// 查询价格日历
		TicketPolicyPriceCalendarQo qo = new TicketPolicyPriceCalendarQo();
		qo.setIds(new ArrayList<String>());
		for(String ticketPolicyId:ticketPolicyIds){
			qo.getIds().add(TicketPolicyPriceCalendar.generateId(ticketPolicyId, null));
			if(StringUtils.isNotBlank(dealerId))
				qo.getIds().add(TicketPolicyPriceCalendar.generateId(ticketPolicyId, dealerId));
		}
		
		// 价格日历分类
		Map<String, TicketPolicyPriceCalendar> standardCalendarMap = new HashMap<String, TicketPolicyPriceCalendar>();
		Map<String, TicketPolicyPriceCalendar> dealerCalendarMap = new HashMap<String, TicketPolicyPriceCalendar>();

		List<TicketPolicyPriceCalendar> calendars = getDao().queryList(qo);
		
		for(TicketPolicyPriceCalendar calendar:calendars){
			TicketPolicy ticketPolicy = calendar.getTicketPolicy();
			String ticketPolicyId = null;
			if (ticketPolicy instanceof HibernateProxy) {
				ticketPolicyId = ((HibernateProxy) ticketPolicy)
						.getHibernateLazyInitializer().getIdentifier().toString();
			}else if(ticketPolicy!=null){
				ticketPolicyId = ticketPolicy.getId();
			}

			if (calendar.getStandardPrice() != null && calendar.getStandardPrice())
				standardCalendarMap.put(ticketPolicyId, calendar);
			else
				dealerCalendarMap.put(ticketPolicyId, calendar);
		}
		
		// 合并价格日历
		Dealer dealer=null;
		if (StringUtils.isNotBlank(dealerId)) {
			dealer = new Dealer();
			dealer.setId(dealerId);
		}
		for (String ticketPolicyId : ticketPolicyIds) {
			TicketPolicyPriceCalendar standardCalendar = standardCalendarMap.get(ticketPolicyId);
			TicketPolicyPriceCalendar dealerCalendar = dealerCalendarMap.get(ticketPolicyId);
			TicketPolicy ticketPolicy = new TicketPolicy();
			ticketPolicy.setId(ticketPolicyId);
			TicketPolicyPriceCalendar calendar = new TicketPolicyPriceCalendar();
			calendar.setDealer(dealer);
			calendar.setTicketPolicy(ticketPolicy);
			calendar.setPrices(DatePriceCalendarUtils.merge(
					standardCalendar == null ? null : standardCalendar.getPrices(), dealerCalendar == null ? null : dealerCalendar.getPrices()));
			map.put(ticketPolicyId, calendar);
		}
		
		return map;
	}
	
	/**
	 * @方法功能说明：获取当天价格(当取关于经销商的价格时如果)
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-23下午5:03:46
	 * @修改内容：
	 * @参数：@param ticketPolicyId			门票政策ID
	 * @参数：@param dealerId					经销商ID（为空时视为仅查询基准价）
	 * @参数：@param date						联票购买日期/单票游玩日期
	 * @参数：@return
	 * @return:TicketPolicyDatePrice
	 * @throws
	 */
	public TicketPolicyDatePrice getTicketPolicyPriceCalendar(
			String ticketPolicyId, String dealerId, Date date) {

		// 参数校验
		if (StringUtils.isBlank(ticketPolicyId) || date == null)
			return null;

		// 组装QO
		TicketPolicyPriceCalendarQo qo = new TicketPolicyPriceCalendarQo();
		qo.setIds(new ArrayList<String>());
		qo.getIds().add(TicketPolicyPriceCalendar.generateId(ticketPolicyId, null));
		if (StringUtils.isNotBlank(dealerId))
			qo.getIds().add(TicketPolicyPriceCalendar.generateId(ticketPolicyId, dealerId));
		qo.setDealerFetch(true);

		List<TicketPolicyPriceCalendar> calendars = getDao().queryList(qo);

		if (calendars.size() == 0)
			return null;

		TicketPolicyPriceCalendar standardCalendar = null;
		TicketPolicyPriceCalendar dealerCalendar = null;

		for (TicketPolicyPriceCalendar calendar : calendars) {
			if (calendar.getStandardPrice() != null && calendar.getStandardPrice())
				standardCalendar = calendar;
			else if (calendar.getDealer() != null && StringUtils.equals(calendar.getDealer().getId(), dealerId))
				dealerCalendar = calendar;
		}

		TicketPolicyDatePrice datePrice = null;
		String dateKey = DateUtil.formatDateTime(date, "yyyyMMdd");
		
		if (dealerCalendar != null && dealerCalendar.getPrices() != null) {
			for (TicketPolicyDatePrice price : dealerCalendar.getPrices())
				if (StringUtils.equals(dateKey, price.getDate())) {
					datePrice = price;
					break;
				}
		}

		if (datePrice == null && standardCalendar != null && standardCalendar.getPrices() != null) {
			for (TicketPolicyDatePrice price : standardCalendar.getPrices())
				if (StringUtils.equals(dateKey, price.getDate())) {
					datePrice = price;
					break;
				}
		}

		return datePrice;
	}
	
	/**
	 * @方法功能说明：景区端为独立单票设置经销商价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-9下午4:37:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:String				价格日历ID
	 * @throws
	 */
	public String scenicspotSetSingleTicketPolicyPrice(ScenicspotSetSingleTicketPolicyPriceCommand command) throws DZPWException{
		
		// 检查
		command.check();
		
		TicketPolicy ticketPolicy = ticketPolicyService.getAndCheckTicketPolicy(command.getTicketPolicyId());

		// 检查是否可操作
		checkSingleTicketPolicyModify(ticketPolicy, command);
		
		TicketPolicyPriceCalendarQo qo = new TicketPolicyPriceCalendarQo();
		TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
		DealerQo dealerQo = new DealerQo();
		qo.setDealerQo(dealerQo);
		qo.setStandardPrice(command.getStandardPrice());
		qo.setTicketPolicyQo(ticketPolicyQo);
		dealerQo.setId(command.getDealerId());
		ticketPolicyQo.setId(command.getTicketPolicyId());

		Dealer dealer = null;
		if (command.getDealerId() != null)
			dealer = dealerDao.get(command.getDealerId());

		TicketPolicyPriceCalendar calendar = getDao().queryUnique(qo);
		
		boolean hasCalendar = calendar == null ? false : true;
		
		if (!hasCalendar)
			calendar = new TicketPolicyPriceCalendar();
		
		calendar.scenicspotSetSingleTicketPolicyPrice(command, ticketPolicy, dealer);

		if (hasCalendar)
			getDao().update(calendar);
		else
			getDao().save(calendar);
		
		return calendar.getId();
		
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：景区端删除独立单票的经销商价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-5下午3:53:07
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotDeleteSingleTicketPolicyPrice(ScenicspotDeleteSingleTicketPolicyPriceCommand command) throws DZPWException{
		
		if (command.getCalendarId() == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少价格日历ID");

		TicketPolicyPriceCalendar calendar = getDao().get(command.getCalendarId());
		
		if (calendar == null)
			return;

		// 检查是否可操作
		checkSingleTicketPolicyModify(calendar.getTicketPolicy(), command);

		// 删除
		getDao().delete(calendar);

	}
	
	/**
	 * @方法功能说明：检查独立单票政策是否可操作
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-5下午4:27:26
	 * @修改内容：
	 * @参数：@param ticketPolicy
	 * @参数：@param command
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	private void checkSingleTicketPolicyModify(TicketPolicy ticketPolicy, DZPWMerchantBaseCommand command) throws DZPWException{
		ticketPolicyService.checkSingleTicketPolicyModify(ticketPolicy, command, true, "启用状态的门票政策不能修改、新增或删除价格日历，请禁止后操作。");
	}

	/**
	 * @方法功能说明：检查联票政策是否可操作
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午4:59:11
	 * @修改内容：
	 * @参数：@param ticketPolicy
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	private void checkGroupTicketPolicyModify(TicketPolicy ticketPolicy) throws DZPWException{
		ticketPolicyService.checkGroupTicketPolicyModify(ticketPolicy, true, "启用状态的联票政策不能修改、新增或删除价格日历，请禁止后操作。");
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：运营端删除联票政策价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午4:40:33
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void platformDeleteGroupTicketPolicyPrice(PlatformDeleteGroupTicketPolicyPriceCommand command) throws DZPWException{

		if (command.getCalendarId() == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少价格日历ID");

		TicketPolicyPriceCalendar calendar = getDao().get(command.getCalendarId());
		
		if (calendar == null)
			return;

		// 检查联票政策是否可操作
		checkGroupTicketPolicyModify(calendar.getTicketPolicy());
		
		// 删除
		getDao().delete(calendar);
	}

	/**
	 * @throws DZPWException 
	 * @方法功能说明：设置联票的价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18下午4:40:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String platformSetGroupTicketPolicyPrice(PlatformSetGroupTicketPolicyPriceCommand command) throws DZPWException{

		// 检查
		command.check();
		
		TicketPolicy ticketPolicy = ticketPolicyService.getAndCheckTicketPolicy(command.getTicketPolicyId());

		// 检查联票政策是否可操作
		checkGroupTicketPolicyModify(ticketPolicy);

		Dealer dealer = null;
		if (command.getDealerId() != null)
			dealer = dealerDao.get(command.getDealerId());
		if (dealer == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "经销商不存在或已被删除。");

		// 查询价格日历
		TicketPolicyPriceCalendarQo qo = new TicketPolicyPriceCalendarQo();
		TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
		DealerQo dealerQo = new DealerQo();
		qo.setDealerQo(dealerQo);
		qo.setTicketPolicyQo(ticketPolicyQo);
		dealerQo.setId(command.getDealerId());
		ticketPolicyQo.setId(command.getTicketPolicyId());
		TicketPolicyPriceCalendar calendar = getDao().queryUnique(qo);
		
		boolean hasCalendar = calendar == null ? false : true;
		
		if (!hasCalendar)
			calendar = new TicketPolicyPriceCalendar();
		
		calendar.platformSetGroupTicketPolicyPrice(command, ticketPolicy, dealer);

		if (hasCalendar)
			getDao().update(calendar);
		else
			getDao().save(calendar);
		
		return calendar.getId();
	}
	
	
	public TicketPolicyPriceCalendarSnapshot getTicketPolicyPriceCalendarSnapshot(TicketPolicyPriceCalendarSnapshotQo qo){
		return snapshotDao.queryUnique(qo);
	}
	
	
}
