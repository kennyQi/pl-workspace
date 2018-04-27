package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.MD5HashUtil;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.dao.ClientDeviceDao;
import hg.dzpw.app.dao.ScenicSpotDao;
import hg.dzpw.app.dao.TicketPolicyDao;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.pojo.vo.MerchantSessionUserVo;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.domain.model.scenicspot.ClientDevice;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.scenicspot.ScenicSpotBaseInfo;
import hg.dzpw.pojo.command.merchant.scenicspot.MerchantLoginCommand;
import hg.dzpw.pojo.command.merchant.scenicspot.ModifyLoginScenicspotCommand;
import hg.dzpw.pojo.command.merchant.scenicspot.ModifyLoginScenicspotPasswordCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformActivateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformAdminLoginCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformCreateScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformForbiddenScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformModifyScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformModifyTicketDefaultValidDaysCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformRemoveScenicSpotCommand;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformSettingCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.system.dao.AreaDao;
import hg.system.dao.CityDao;
import hg.system.dao.ProvinceDao;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：景区服务
 * @修改日期：2014-11-12下午3:17:57
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12下午3:17:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScenicSpotLocalService extends BaseServiceImpl<ScenicSpot, ScenicSpotQo, ScenicSpotDao> {
	public final static String DZPW_SCENIC_USETYPE = "景区图片";//电子票务的景区相册用途
	
	@Autowired
	private ScenicSpotDao dao;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private ClientDeviceDao deviceDao;
	@Autowired
	private TicketPolicyDao ticketPolicyDao;
	@Autowired
	private ClientDeviceLocalService clientDeviceLocalService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private AreaService areaService;

	@Override
	protected ScenicSpotDao getDao() {
		return dao;
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：修改当前登录的景区信息（景区用户在景区后台修改）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午9:46:49
	 * @参数：@param command
	 * @return:void
	 */
	public void modifyLoginScenicspot(ModifyLoginScenicspotCommand command,String[] imgNames,String[] imgSrcs)
			throws DZPWException {
		ScenicSpot scenicSpot = getAndCheckScenicSpot(command.getScenicspotId());
		Province province = null;
		City city = null;
		Area area = null;
		if (command.getProvinceId() != null)
			province = provinceService.get(command.getProvinceId());
		if (command.getCityId() != null)
			city = cityService.get(command.getCityId());
		if (command.getAreaId() != null)
			area = areaService.get(command.getAreaId());
		scenicSpot.modifyLoginScenicspot(command, province, city, area);
		getDao().update(scenicSpot);
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：修改登录景区商户的密码
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11下午3:33:19
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLoginScenicspotPassword(ModifyLoginScenicspotPasswordCommand command) throws DZPWException {
		if (command.getScenicspotId() == null)
			throw new RuntimeException("当前登录的景区已被删除");
		ScenicSpot scenicSpot = getDao().get(command.getScenicspotId());
		scenicSpot.modifyLoginScenicspotPassword(command);
		getDao().update(scenicSpot);
	}
	
	/**
	 * @方法功能说明：景区管理员登录检查
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:18:16
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ScenicSpot
	 * @throws
	 */
	public MerchantSessionUserVo loginCheck(MerchantLoginCommand command)
			throws UnknownAccountException, IncorrectCredentialsException,
			LockedAccountException, AuthenticationException {

		if (command == null || StringUtils.isBlank(command.getLoginName()) || StringUtils.isBlank(command.getPassword()))
			throw new AuthenticationException();

		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setAdminLoginName(command.getLoginName());
		qo.setAdminLoginNameLike(false);
		qo.setRemoved(null);

		ScenicSpot scenicSpot = getDao().queryUnique(qo);

		// 景区不存在
		if (scenicSpot == null)
			throw new UnknownAccountException();

		// 景区管理员密码不匹配
		if (!StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(command.getPassword()), scenicSpot.getSuperAdmin().getAdminPassword()))
			throw new IncorrectCredentialsException();

		// 景区未激活或已被删除
		if (!scenicSpot.getStatus().getActivated() || scenicSpot.getStatus().getRemoved())
			throw new LockedAccountException();

		return new MerchantSessionUserVo(scenicSpot);
	}
	
	/**
	 * @方法功能说明：登录名是否存在
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午2:43:34
	 * @修改内容：
	 * @参数：@param loginName
	 * @参数：@param scenicSpotId
	 * @参数：@return	排除scenicSpotId景区，检查登录名是否存在。
	 * @return:boolean
	 * @throws
	 */
	public boolean loginNameExists(String loginName, String scenicSpotId) {
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setAdminLoginName(loginName);
		if (StringUtils.isNotBlank(scenicSpotId))
			qo.setIdNotIn(new String[] { scenicSpotId });
		return getDao().queryCount(qo) > 0 ? true : false;
	}

	/**
	 * @方法功能说明：景区代码是否存在
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午2:43:34
	 * @修改内容：
	 * @参数：@param scenicSpotCode
	 * @参数：@param scenicSpotId
	 * @参数：@return	排除scenicSpotId景区，检查景区代码是否存在。
	 * @return:boolean
	 * @throws
	 */
	public boolean scenicSpotCodeExists(String scenicSpotCode, String scenicSpotId) {
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setCode(scenicSpotCode);
		if (StringUtils.isNotBlank(scenicSpotId))
			qo.setIdNotIn(new String[] { scenicSpotId });
		return getDao().queryCount(qo) > 0 ? true : false;
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：检查景区管理员、景区代码和设备编号是否存在
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午3:04:08
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@param loginName
	 * @参数：@param scenicSpotCode
	 * @参数：@param deviceIds
	 * @return:void
	 * @throws
	 */
	public void checkLoginNameAndDeviceIds(String scenicSpotId, String loginName, String scenicSpotCode,
			List<String> deviceIds) throws DZPWException {
		
		// 检查登录名是否重复
		if (loginNameExists(loginName, scenicSpotId))
			throw new DZPWException(DZPWException.SCENICSPOT_LOGINNAME_REPEAT,
					String.format("景区登录名(%s)重复", loginName));
		
		// 检查景区代码是否重复
//		if(scenicSpotCodeExists(scenicSpotCode, scenicSpotId))
//			throw new DZPWException(DZPWException.SCENICSPOT_CODE_EXISTS,
//					String.format("景区代码(%s)重复", scenicSpotCode));
		
		// 检查设备编号是否重复
		if (deviceIds != null)
			for (String deviceId : deviceIds)
				if (clientDeviceLocalService.deviceIdExists(deviceId, scenicSpotId))
					throw new DZPWException(
							DZPWException.SCENICSPOT_CLIENTDEVICE_NUMBER_REPEAT,
							String.format("入园设备编号(%s)重复", deviceId));
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：获取并检查景区，不存在时抛出异常
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午3:26:40
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@return
	 * @return:ScenicSpot
	 * @throws
	 */
	public ScenicSpot getAndCheckScenicSpot(String scenicSpotId) throws DZPWException {
		if (scenicSpotId == null)
			throw new DZPWException(DZPWException.SCENICSPOT_ID_IS_NULL, "景区ID不能为空");
		ScenicSpot scenicSpot = getDao().get(scenicSpotId);
		if (scenicSpot == null)
			throw new DZPWException(DZPWException.SCENICSPOT_NOT_EXISTS, "景区不存在");
		return scenicSpot;
	}

	/**
	 * @方法功能说明：创建景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改者名字：chenys
	 * @修改时间：2014-11-12下午3:23:52
	 * @修改内容：返回创建id
	 * @修改者名字：chenys
	 * @修改时间：2014-12-19下午4:24:56
	 * @修改内容：添加图片服务上的相册和图片
	 * @参数：@param command
	 * @return:void
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ImageException 
	 * @throws DZPWException 
	 */
	public String createScenicSpot(PlatformCreateScenicSpotCommand command) throws DZPWException, IOException, URISyntaxException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 检查登录名与入园设备编号
		checkLoginNameAndDeviceIds(null, command.getAdminLoginName(), command.getCode(), command.getDeviceIds());
		
		ScenicSpot scenicSpot = new ScenicSpot();
		Province province = null;
		City city = null;
		Area area = null;
		if (command.getProvinceId() != null)
			province = provinceDao.get(command.getProvinceId());
		if (command.getCityId() != null)
			city = cityDao.get(command.getCityId());
		if (command.getAreaId() != null)
			area = areaDao.get(command.getAreaId());
		if (province == null && city != null)
			city = null;
		if (province != null && city != null && !StringUtils.equals(city.getParentCode(), province.getCode()))
			city = null;
		if (city != null && area != null && !StringUtils.equals(area.getParentCode(), city.getCode()))
			area = null;
		scenicSpot.create(command, province, city, area);
		//添加景区编码
		scenicSpot.getBaseInfo().setCode(this.getNextCode());
		getDao().save(scenicSpot);
		for (ClientDevice device : scenicSpot.getDevices()) {
			deviceDao.save(device);
		}
		
		//返回景区id
		return scenicSpot.getId();
	}
	
	
	/**
	 * @方法功能说明：修改景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:20
	 * @修改内容：
	 * @修改者名字：chenys
	 * @修改时间：2014-12-19下午4:24:56
	 * @修改内容：修改图片服务上的相册和图片
	 * @参数：@param command
	 * @return:void
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ImageException 
	 * @throws DZPWException 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public void modifyScenicSpot(PlatformModifyScenicSpotCommand command) throws DZPWException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, URISyntaxException {
		// 查询并检查景区
		ScenicSpot scenicSpot = getAndCheckScenicSpot(command.getScenicSpotId());
		// 检查登录名与入园设备编号
		checkLoginNameAndDeviceIds(command.getScenicSpotId(), command.getAdminLoginName(), command.getCode(), command.getDeviceIds());

		Province province = null;
		City city = null;
		Area area = null;
		if (command.getProvinceId() != null)
			province = provinceDao.get(command.getProvinceId());
		if (command.getCityId() != null)
			city = cityDao.get(command.getCityId());
		if (command.getAreaId() != null)
			area = areaDao.get(command.getAreaId());
		
		if (province == null && city != null)
			city = null;
		if (province != null && city != null && !StringUtils.equals(city.getParentCode(), province.getCode()))
			city = null;
		if (city != null && area != null && !StringUtils.equals(area.getParentCode(), city.getCode()))
			area = null;

		List<ClientDevice> devices = scenicSpot.getDevices();
		Map<String, ClientDevice> deviceMap = new HashMap<String, ClientDevice>();
		for (ClientDevice d : devices) {
			deviceMap.put(d.getId(), d);
		}
		scenicSpot.modify(command, province, city, area);
		devices = scenicSpot.getDevices();
		for (ClientDevice d : devices) {
			if (deviceMap.containsKey(d.getId())) {
				deviceMap.remove(d.getId());
			} else {
				deviceDao.save(d);
			}
		}
		// 删除不存在的设备
		for (ClientDevice d : deviceMap.values()) {
			deviceDao.delete(d);
		}
		getDao().update(scenicSpot);
	}
	

	/**
	 * @方法功能说明：禁用景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:26
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void forbiddenScenicSpot(PlatformForbiddenScenicSpotCommand command) {
		if (command.getScenicSpotId() == null || command.getScenicSpotId().size() == 0)
			return;
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setIds(command.getScenicSpotId());
		List<ScenicSpot> scenicSpots = getDao().queryList(qo, 100);
		
		qo.setIds(null);
		
		StringBuffer sb = new StringBuffer();
		
//		for (ScenicSpot scenicSpot : scenicSpots) {
		for (int y=0; y<scenicSpots.size(); y++){
			// 要推送的景区ID
			sb.append(scenicSpots.get(y).getId());
			if (y < scenicSpots.size()-1)
				sb.append(",");
			
			//景区禁用
			scenicSpots.get(y).forbidden(command);
			getDao().update(scenicSpots.get(y));
			
			TicketPolicyQo tpqo = new TicketPolicyQo();
			qo.setId(scenicSpots.get(y).getId());
			tpqo.setScenicSpotQo(qo);
			tpqo.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);//查询单票
			
			//查询景区下的门票政策
			List<TicketPolicy> list = this.ticketPolicyDao.queryList(tpqo);
			
			//要推送的门票政策ID
			StringBuffer sb2 = new StringBuffer();
			
			for(int x=0; x<list.size(); x++){
				
				if(!list.get(x).getStatus().isIssue())
					continue;
				
				//下架已启用的门票政策
				list.get(x).getStatus().setCurrent(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
				for(TicketPolicy stp : list.get(x).getSingleTicketPolicies()){
					stp.getStatus().setCurrent(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
				}
				
				ticketPolicyDao.update(list.get(x));
				
				sb2.append(list.get(x).getId());
				if(x < list.size()-1)
					sb2.append(",");
			}
			
			if(sb2.length()>0){
				DealerApiEventPublisher.publish(new PublishEventRequest(
													PublishEventRequest.EVENT_TICKET_POLICY_FINISH, sb2.toString()));
			}
		}
		
		if (sb.length()>0){
			DealerApiEventPublisher.publish(new PublishEventRequest(
												PublishEventRequest.EVENT_SCENIC_SPOT_FINISH, sb.toString()));
		}
		
	}

	/**
	 * @方法功能说明：启用景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:48
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void activateScenicSpot(PlatformActivateScenicSpotCommand command) {
		if (command.getScenicSpotId() == null || command.getScenicSpotId().size() == 0)
			return;
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setIds(command.getScenicSpotId());
		List<ScenicSpot> scenicSpots = getDao().queryList(qo, 100);
		
		StringBuffer sb = new StringBuffer();
		
		for (int x=0; x<scenicSpots.size(); x++){
			// 要推送的景区ID
			sb.append(scenicSpots.get(x).getId());
			if (x < scenicSpots.size()-1)
				sb.append(",");
			
			scenicSpots.get(x).activate(command);
			getDao().update(scenicSpots.get(x));
		}
		
		if (sb.length()>0){
			DealerApiEventPublisher.publish(new PublishEventRequest(
												PublishEventRequest.EVENT_SCENIC_SPOT_ISSUE, sb.toString()));
		}
	}

	/**
	 * @方法功能说明：逻辑删除景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午3:24:56
	 * @修改内容：
	 * @修改者名字：chenys
	 * @修改时间：2014-12-19下午4:24:56
	 * @修改内容：删除图片服务上的相册
	 * @参数：@param command
	 * @return:void
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ImageException 
	 * @throws DZPWException 
	 */
	public void removeScenicSpot(PlatformRemoveScenicSpotCommand command,String authId) throws DZPWException, IOException, URISyntaxException {
		if (command.getScenicSpotId() == null || command.getScenicSpotId().size() == 0)
			return;
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setIds(command.getScenicSpotId());
		List<ScenicSpot> scenicSpots = getDao().queryList(qo, 100);
		
//		AlbumQo albumQo = null;
//		List<String> albumIds = new ArrayList<String>(1);
		for (ScenicSpot scenicSpot : scenicSpots) {
			/**
			 * 获取景区相册id
			 * 一个是图片服务的数据库，一个是电子票务的数据库，如果两边的数据因为程序之外的原因造成数据不对应、相册对不上id，导致一些莫名其妙的错误
			 * 所以这里做个判断，允许删除景区的时候，景区相册不存在，以增加程序的容错能力！！
			 */
//			albumQo = this.factoryAlbumQo(authId,scenicSpot.getId(),null,null,0);
//			AlbumDTO albumDto = albumSer.queryUniqueAlbum(albumQo);
//			if(null != albumDto)
//				albumIds.add(albumDto.getId());
			
			//删除景区
			refreshScenicSpotGroupTicketNumber(scenicSpot.getId());
			scenicSpot.remove(command);
			getDao().update(scenicSpot);
		}
//		//删除图片服务上的图片
//		if(albumIds.size() > 0){
//			DeleteAlbumCommand deleteCommand = new DeleteAlbumCommand();
//			deleteCommand.setAlbumIds(albumIds);
//			albumSer.deleteAlbum(deleteCommand);
//		}
	}

	/**
	 * @throws DZPWException 
	 * @方法功能说明：修改景区门票默认有效天数
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-12下午5:54:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyTicketDefaultValidDays(PlatformModifyTicketDefaultValidDaysCommand command) throws DZPWException {
		// 查询并检查景区
		ScenicSpot scenicSpot = getAndCheckScenicSpot(command.getScenicSpotId());
		scenicSpot.modifyTicketDefaultValidDays(command);
		getDao().update(scenicSpot);
	}

	/**
	 * @throws DZPWException 
	 * @方法功能说明：刷新景区联票数量
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午4:49:08
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @return:void
	 * @throws
	 */
	public void refreshScenicSpotGroupTicketNumber(String scenicSpotId)
			throws DZPWException {
		if (StringUtils.isBlank(scenicSpotId))
			return;
		TicketPolicyQo qo = new TicketPolicyQo();
		qo.setGroupTicketPolicyAble(false);
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setId(scenicSpotId);
		qo.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE_IN_GROUP);
		qo.setScenicSpotQo(scenicSpotQo);
		ScenicSpot scenicSpot = getAndCheckScenicSpot(scenicSpotId);
		scenicSpot.setGroupTicketNumber(ticketPolicyDao.queryCount(qo));
		getDao().update(scenicSpot);
	}
	
	/**
	 * @方法功能说明：管理员登录
	 * @修改者名字：zzx
	 * @修改时间：2014-11-25下午2:43:34
	 * @修改内容：
	 * @参数：@param command
	 * @return:String 景区简称
	 * @throws
	 */
	public ScenicSpot apiAdminLogin(PlatformAdminLoginCommand command) throws DZPWException{
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setAdminLoginName(command.getLoginName());
		qo.setRemoved(null);//全部景区访问查询    防止卖票后 景区被禁用或逻辑删除
		ScenicSpot scenicSpot =  dao.queryUnique(qo);
		if (scenicSpot == null || scenicSpot.getBaseInfo() == null) {
	           throw new DZPWException(-1, "帐号不存在");
		}
		if (!StringUtils.equals(scenicSpot.getSuperAdmin().getAdminPassword().toLowerCase(),
				MD5HashUtil.toMD5(command.getPassword()).toLowerCase())) {
			   throw new DZPWException(-2, "密码有误");
		}
		return scenicSpot;
	}
	
	/**
	 * @方法功能说明：设置
	 * @修改者名字：zzx
	 * @修改时间：2014-11-25下午2:43:34
	 * @修改内容：
	 * @参数：@param command
	 * @return:boolean
	 * @throws
	 */
	public void setting(PlatformSettingCommand command) throws DZPWException{
		ScenicSpot scenicSpot = getAndCheckScenicSpot(command.getScenicSpotId());
		scenicSpot.setManualCheckCertificate(command.isManualCheckCertificate());
		dao.update(scenicSpot);
	}
	/**
	 * 
	 * @描述： 获取下一个景区编码
	 * @author: guotx 
	 * @version: 2015-12-14 下午4:35:47
	 */
	public String getNextCode(){
		String hql="select scen.baseInfo.code as code FROM ScenicSpot as scen where scen.baseInfo.code like ? order by scen.baseInfo.code desc";
		List<Object> params=new ArrayList<Object>();
		params.add(ScenicSpotBaseInfo.SCEN_CODE_PREFIX+"%");
		List<ScenicSpotBaseInfo>scens=null;
		scens=getDao().hqlQueryList(ScenicSpotBaseInfo.class, hql, params);
		if (scens!=null&&scens.size()>0) {
			String maxCode=scens.get(0).getCode();
			maxCode=maxCode.substring(ScenicSpotBaseInfo.SCEN_CODE_PREFIX.length());
			Integer currentCodeNum=Integer.parseInt(maxCode);
			return String.format(ScenicSpotBaseInfo.SCEN_CODE_PREFIX+"%04d", currentCodeNum+1);
		}else{
			//没有已存在的code返回起始code
			return ScenicSpotBaseInfo.SCEN_CODE_PREFIX+"0001";
		}
	}
}