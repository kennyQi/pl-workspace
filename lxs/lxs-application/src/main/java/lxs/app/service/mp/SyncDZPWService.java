package lxs.app.service.mp;

import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.dto.meta.RegionDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDatePriceDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotPicDTO;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lxs.app.dao.mp.DZPWAreaDAO;
import lxs.app.dao.mp.DZPWCityDAO;
import lxs.app.dao.mp.DZPWProvinceDAO;
import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.app.dao.mp.ScenicSpotPicDAO;
import lxs.app.service.app.CarouselService;
import lxs.app.service.app.RecommendService;
import lxs.app.service.app.SubjectService;
import lxs.domain.model.app.Subject;
import lxs.domain.model.mp.DzpwArea;
import lxs.domain.model.mp.DzpwCity;
import lxs.domain.model.mp.DzpwProvince;
import lxs.domain.model.mp.GroupTicket;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Transactional
public class SyncDZPWService {
	
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private DZPWService dzpwService;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	@Autowired
	private ScenicSpotPicDAO scenicSpotPicDAO;
	@Autowired
	private DZPWProvinceDAO dzpwProvinceDAO;
	@Autowired
	private DZPWCityDAO dzpwCityDAO;
	@Autowired
	private DZPWAreaDAO dzpwAreaDAO;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ScenicSpotSelectiveService scenicSpotSelectiveService;
	@Autowired
	private DZPWProvinceService dzpwProvinceService;
	@Autowired
	private DZPWCityService dzpwCityService;
	@Autowired
	private DZPWAreaService dzpwAreaService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CarouselService carouselService;
	@Autowired
	private GroupTicketService groupTicketService;
	/**同步锁 */
	private static Object LOCK = new Object();
	/**同步锁 */
	private static boolean FLAG = false;
	
	/**
	 * 
	 * @方法功能说明：景区同步
	 * @修改者名字：cangs
	 * @修改时间：2016年3月3日下午1:26:55
	 * @修改内容：
	 * @参数：@param versionNO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String syncScenicSpot(){
		//需要考虑请求远程失败的情况
		
		//获取电子票务景区信息
		try{
			if(FLAG){
				return "正在同步中";
			}
			synchronized (LOCK) {
				int versionNO= this.getScenicSpotVersion();
				//新建主题list
				List<Subject> subjects = new ArrayList<Subject>();
				FLAG=true;
				int pageNO = 1;
				int pageSize = 30;
				Map<String,GroupTicket> groupTicketMap = new HashMap<String, GroupTicket>();
				for(pageNO=1;pageNO>0;pageNO++){
					ScenicSpotQO scenicSpotQO = new ScenicSpotQO();
					scenicSpotQO.setPageNo(pageNO);
					scenicSpotQO.setPageSize(pageSize);
					ScenicSpotResponse scenicSpotResponse = dzpwService.queryScenicSpot(scenicSpotQO);
					if(scenicSpotResponse!=null&&scenicSpotResponse.getScenicSpots()!=null){
						List<ScenicSpotDTO> scenicSpotDTOs = scenicSpotResponse.getScenicSpots();
						for (ScenicSpotDTO scenicSpotDTO : scenicSpotDTOs) {
							//保存远程景区
							groupTicketMap=this.saveRemoteScenicSpot(scenicSpotDTO, versionNO,groupTicketMap);
							//获取主题
							if(scenicSpotDTO.getBaseInfo()!=null&&StringUtils.isNotBlank(scenicSpotDTO.getBaseInfo().getTheme())){
								String[] themes = scenicSpotDTO.getBaseInfo().getTheme().split("\\s");
								for (String theme : themes) {
									boolean flag = false;
									int i = 0;
									for(Subject subject:subjects){
										if(StringUtils.equals(subject.getSubjectName(), theme)){
											flag=true;
											break;
										}
										i++;
									}
									if(flag){
										//老主题
										subjects.get(i).setProductSUM(String.valueOf((Integer.valueOf(subjects.get(i).getProductSUM())+1)));
									}else{
										//新主题
										if(StringUtils.isNotBlank(theme)){
											Subject subject = new Subject();
											subject.setId(UUIDGenerator.getUUID());
											subject.setProductSUM("1");
											subject.setSubjectName(theme);
											subject.setSubjectType(Subject.SUNGECT_TYPE_MENPIAO);
											subject.setCreateDate(new Date());
											subject.setSort(0);
											subjects.add(subject);
										}
									}
								}
							}
						}
					}else{
						return "获取远程景区失败";
					}
					if(pageSize*pageNO>=scenicSpotResponse.getTotalCount())
						break;
				}
				//更新推荐
				recommendService.refresh();
				//更新轮播
				carouselService.refresh();
				//更新主题
				subjectService.saveSubjectList(subjects);
				//更新精选
				scenicSpotSelectiveService.delSelectiveByNullScenicSpot();
				//删除老版本联票
				groupTicketService.deleteOldGroupTicket(versionNO+1);
				//更新联票
				groupTicketService.saveGroupTicketMap(groupTicketMap);
				//删除老版本图片
				scenicSpotService.deleteOldScenicSpot(versionNO+1);
//				ScenicSpotPicQO scenicSpotPicQO = new ScenicSpotPicQO();
//				scenicSpotPicQO.setVersionNO(versionNO+1);
//				scenicSpotPicQO.setVersionType(ScenicSpotPicQO.LESS_THAN);
//				List<ScenicSpotPic> theOldScenicSpotPicDTOs = scenicSpotPicDAO.queryList(scenicSpotPicQO);
//				for (ScenicSpotPic scenicSpotPic : theOldScenicSpotPicDTOs) {
//					scenicSpotPicDAO.deleteById(scenicSpotPic.getId());
//				}
				//删除老版本景区
				scenicSpotPicService.deleteOldScenicSpotPic(versionNO+1);
//				lxs.mp.pojo.base.qo.ScenicSpotQO localSpotQO = new lxs.mp.pojo.base.qo.ScenicSpotQO();
//				localSpotQO.setVersionNO(versionNO+1);
//				localSpotQO.setVersionType(lxs.mp.pojo.base.qo.ScenicSpotQO.LESS_THAN);
//				List<ScenicSpot> theOldScenicSpots = scenicSpotDAO.queryList(localSpotQO);
//				for (ScenicSpot scenicSpot : theOldScenicSpots) {
//					scenicSpotDAO.deleteById(scenicSpot.getId());
//				}
				FLAG=false;
			}
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			FLAG=false;
			return "同步失败";
		}
		return "success";
	}
	
	/**
	 * 
	 * @方法功能说明：保存远程景区
	 * @修改者名字：cangs
	 * @修改时间：2016年3月8日下午3:17:36
	 * @修改内容：
	 * @参数：@param scenicSpotDTO
	 * @参数：@param versionNO
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	public Map<String,GroupTicket> saveRemoteScenicSpot(ScenicSpotDTO scenicSpotDTO,int versionNO,Map<String,GroupTicket> groupTicketMap) throws Exception{
		try{
			List<ScenicSpotPicDTO> scenicSpotPicDTOs = scenicSpotDTO.getPics();
			for (ScenicSpotPicDTO scenicSpotPicDTO : scenicSpotPicDTOs) {
				ScenicSpotPic scenicSpotPic = BeanMapperUtils.getMapper().map(scenicSpotPicDTO, ScenicSpotPic.class);
				scenicSpotPic.setId(UUIDGenerator.getUUID());
				scenicSpotPic.setScenicSpotID(scenicSpotDTO.getId());
				scenicSpotPic.setVersionNO(versionNO+1);
				scenicSpotPic.setSyncTime(new Date());
				scenicSpotPicDAO.save(scenicSpotPic);
			}
			//初始化销量，显示状态
			int sales=0,localStatus = 0;
			ScenicSpot theAllreadyExistedScenicSpot = scenicSpotDAO.get(scenicSpotDTO.getId());
			//获取老版本景区销量，显示状态
			if(theAllreadyExistedScenicSpot!=null&&theAllreadyExistedScenicSpot.getSales()!=null){
				sales=theAllreadyExistedScenicSpot.getSales();
			}
			if(theAllreadyExistedScenicSpot!=null&&theAllreadyExistedScenicSpot.getLocalStatus()!=null){
				localStatus=theAllreadyExistedScenicSpot.getLocalStatus();
			}
			//初始化排序
			int sort =0;
			//获取老版本排序
			if(theAllreadyExistedScenicSpot!=null&&theAllreadyExistedScenicSpot.getSort()!=null){
				sort=theAllreadyExistedScenicSpot.getSort();
			}
			//删除老版本景区
			scenicSpotDAO.deleteById(scenicSpotDTO.getId());
			//初始化 票价 游玩价
			double rackRate = 0.0,playPrice=0.0;
			TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
			ticketPolicyQO.setScenicSpotId(scenicSpotDTO.getId());
			ticketPolicyQO.setCalendarFetch(true);
			ticketPolicyQO.setSingleTicketPoliciesFetch(true);
			TicketPolicyResponse ticketPolicyResponse = dzpwService.queryTicketPolicy(ticketPolicyQO);
			//初始化景区政策数量
			int policySUM = 0;
			if(ticketPolicyResponse.getTicketPolicies()!=null){
				List<TicketPolicyDTO> ticketPolicyDTOs = ticketPolicyResponse.getTicketPolicies();
				policySUM=ticketPolicyDTOs.size();
				//票价 游玩价 初始化标志
				boolean initRackRate = true;
				boolean initPlayPrice = true;
				for (TicketPolicyDTO ticketPolicyDTO : ticketPolicyDTOs) {
					if(initRackRate){
						//初始化未成功
						if(ticketPolicyDTO.getBaseInfo()!=null&&ticketPolicyDTO.getBaseInfo().getRackRate()!=null&ticketPolicyDTO.getBaseInfo().getRackRate()!=0.0){
							rackRate=ticketPolicyDTO.getBaseInfo().getRackRate();
							initRackRate=false;
						}
					}else{
						//寻找最小价格
						if(ticketPolicyDTO.getBaseInfo()!=null&&ticketPolicyDTO.getBaseInfo().getRackRate()!=null&ticketPolicyDTO.getBaseInfo().getRackRate()!=0.0){
							if(ticketPolicyDTO.getBaseInfo().getRackRate()<rackRate){
								rackRate=ticketPolicyDTO.getBaseInfo().getRackRate();
							}
						}
					}
					if(initPlayPrice){
						//初始化未成功
						if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_SINGLE){
							//如果是单票 查询价格日历
							List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs = ticketPolicyDTO.getCalendar().getPrices();
							double theFirstMinPlayPrice = 0.0;
							boolean flag = true;
							for (TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO : ticketPolicyDatePriceDTOs) {
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
								String today = simpleDateFormat.format(new Date());
								if(Integer.valueOf(ticketPolicyDatePriceDTO.getDate())>=Integer.valueOf(today)){
									if(flag){
										theFirstMinPlayPrice=ticketPolicyDatePriceDTO.getPrice();
										flag=false;
									}else if(ticketPolicyDatePriceDTO.getPrice()<theFirstMinPlayPrice){
										theFirstMinPlayPrice=ticketPolicyDatePriceDTO.getPrice();
									}
								}
							}
							if(theFirstMinPlayPrice!=0.0){
								playPrice=theFirstMinPlayPrice;
								initPlayPrice = false;
							}
						}else{
							//如果是联票 直接从政策里获取
							if(ticketPolicyDTO.getBaseInfo()!=null&&ticketPolicyDTO.getBaseInfo().getPlayPrice()!=null&&ticketPolicyDTO.getBaseInfo().getPlayPrice()!=0.0){
								playPrice=ticketPolicyDTO.getBaseInfo().getPlayPrice();
								initPlayPrice = false;
							}
						}
					}else{
						//寻找最小价格
						if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_SINGLE){
							//如果是单票 查询价格日历
							List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs = ticketPolicyDTO.getCalendar().getPrices();
							for (TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO : ticketPolicyDatePriceDTOs) {
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
								String today = simpleDateFormat.format(new Date());
								if(Integer.valueOf(ticketPolicyDatePriceDTO.getDate())>=Integer.valueOf(today)){
									if(ticketPolicyDatePriceDTO.getPrice()<playPrice){
										playPrice=ticketPolicyDatePriceDTO.getPrice();
									}
								}
							}
						}else{
							//如果是联票 直接从政策里获取
							if(ticketPolicyDTO.getBaseInfo()!=null&&ticketPolicyDTO.getBaseInfo().getPlayPrice()!=null&&ticketPolicyDTO.getBaseInfo().getPlayPrice()!=0.0){
								if(ticketPolicyDTO.getBaseInfo().getPlayPrice()<playPrice){
									playPrice=ticketPolicyDTO.getBaseInfo().getPlayPrice();
								}
							}
						}
					}
					//更新联票操作
					if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_GROUP&&ticketPolicyDTO.getSellInfo().getRemainingQty()!=0){
						GroupTicket groupTicket = BeanMapperUtils.getMapper().map(ticketPolicyDTO, GroupTicket.class);
						groupTicket.setScenicSpotID(scenicSpotDTO.getId());
						if(scenicSpotPicDTOs.size()>1){
							groupTicket.setUrl(scenicSpotPicDTOs.get(0).getUrl());
						}
						groupTicket.setVersionNO(versionNO+1);
						groupTicket.setSyncTime(new Date());
						groupTicketMap.put(groupTicket.getId(), groupTicket);
					}
				}
			}
			ScenicSpot scenicSpot = BeanMapperUtils.getMapper().map(scenicSpotDTO, ScenicSpot.class);
			scenicSpot.setSales(sales);
			scenicSpot.setLocalStatus(localStatus);
			scenicSpot.setPolicySUM(policySUM);
			scenicSpot.setSort(sort);
			scenicSpot.setPlayPrice(playPrice);
			scenicSpot.setRackRate(rackRate);
			scenicSpot.setVersionNO(versionNO+1);
			scenicSpot.setSyncTime(new Date());
			scenicSpotDAO.save(scenicSpot);
			return groupTicketMap;
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 
	 * @方法功能说明：同步省市区域
	 * @修改者名字：cangs
	 * @修改时间：2016年3月8日下午3:11:27
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String syncRegion(){
		//需要考虑请求远程失败的情况
		try{
			if(FLAG){
				return "正在同步中";
			}
			synchronized (LOCK) {
				FLAG=true;
				//清空三个表
				dzpwProvinceService.deleteALL();
				dzpwCityService.deleteALL();
				dzpwAreaService.deleteALL();
//				List<DzpwProvince> dzpwProvinces = dzpwProvinceDAO.queryList(new DZPWProvinceQO());
//				List<DzpwCity> dzpwCities = dzpwCityDAO.queryList(new DZPWCityQO());
//				List<DzpwArea> dzpwAreas = dzpwAreaDAO.queryList(new DZPWAreaQO());
//				long begin = System.currentTimeMillis();
//				for (DzpwProvince dzpwProvince : dzpwProvinces) {
//					dzpwProvinceDAO.delete(dzpwProvince);
//				}
//				long end = System.currentTimeMillis();
//				System.out.println("耗时：1" +"|"+ (end - begin));
//				begin = System.currentTimeMillis();
//				for (DzpwCity dzpwCity : dzpwCities) {
//					dzpwCityDAO.delete(dzpwCity);
//				}
//				end = System.currentTimeMillis();
//				System.out.println("耗时：2" +"|"+ (end - begin));
//				begin = System.currentTimeMillis();
//				for (DzpwArea dzpwArea : dzpwAreas) {
//				}
//				dzpwAreaService.deleteALL();
//				end = System.currentTimeMillis();
//				System.out.println("耗时：3"+"|" + (end - begin));
//				begin = System.currentTimeMillis();
				//更新省份表
				List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
				RegionQO regionQO = new RegionQO(); 
				regionQO.setType(RegionQO.TYPE_PROVINCE);
				RegionResponse regionResponse = dzpwService.queryRegion(regionQO);
				if(regionResponse.getRegions()!=null){
					regionDTOs=regionResponse.getRegions();
					for (RegionDTO regionDTO : regionDTOs) {
						DzpwProvince dzpwProvince = BeanMapperUtils.getMapper().map(regionDTO,DzpwProvince.class);
						dzpwProvinceDAO.save(dzpwProvince);
					}
				}
				
				//更新城市表
				regionQO = new RegionQO(); 
				regionQO.setType(RegionQO.TYPE_CITY);
				regionResponse = dzpwService.queryRegion(regionQO);
				if(regionResponse.getRegions()!=null){
					regionDTOs=regionResponse.getRegions();
					for (RegionDTO regionDTO : regionDTOs) {
						DzpwCity dzpwCity = BeanMapperUtils.getMapper().map(regionDTO,DzpwCity.class);
						dzpwCityDAO.save(dzpwCity);
					}
				}
				
				//更新地区表
				regionQO = new RegionQO(); 
				regionQO.setType(RegionQO.TYPE_AREA);
				regionResponse = dzpwService.queryRegion(regionQO);
				if(regionResponse.getRegions()!=null){
					regionDTOs=regionResponse.getRegions();
					for (RegionDTO regionDTO : regionDTOs) {
						DzpwArea dzpwArea = BeanMapperUtils.getMapper().map(regionDTO,DzpwArea.class);
						dzpwCityDAO.save(dzpwArea);
					}
				}
//				end = System.currentTimeMillis();
//				System.out.println("耗时：4" +"|"+ (end - begin));
				FLAG=false;
			}
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			FLAG=false;
			return "同步失败";
		}
		return "success";
	}
	/**
	 * 
	 * @方法功能说明：获取景区版本号
	 * @修改者名字：cangs
	 * @修改时间：2016年3月3日下午1:40:51
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int getScenicSpotVersion(){
		int scenicSpotVersion = 0;
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String redisScenicSpotVersion=jedis.get("SUIXINYOU_"+"SCENICSPOT_"+"VERSION");
		if(!StringUtils.isBlank(redisScenicSpotVersion)){
			scenicSpotVersion = Integer.parseInt(redisScenicSpotVersion);
		}
		int theNextScenicSpotVersion = scenicSpotVersion+1;
		jedis.set("SUIXINYOU_"+"SCENICSPOT_"+"VERSION",String.valueOf(theNextScenicSpotVersion));
		jedisPool.returnResource(jedis);
		return scenicSpotVersion;
	}
}
