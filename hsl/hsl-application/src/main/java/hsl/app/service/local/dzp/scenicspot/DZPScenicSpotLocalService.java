package hsl.app.service.local.dzp.scenicspot;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotBaseInfoDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotContactInfoDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotPicDTO;
import hg.log.util.HgLogger;
import hsl.app.dao.dzp.region.DZPAreaDAO;
import hsl.app.dao.dzp.region.DZPCityDAO;
import hsl.app.dao.dzp.region.DZPProvinceDAO;
import hsl.app.dao.dzp.scenicspot.DZPScenicSpotPicDAO;
import hsl.app.dao.dzp.scenicspot.DZPScenicSpotDAO;
import hsl.domain.model.dzp.meta.DZPArea;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.domain.model.dzp.meta.DZPProvince;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpotBaseInfo;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpotContactInfo;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpotPic;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.dzp.region.DZPAreaQO;
import hsl.pojo.qo.dzp.region.DZPCityQO;
import hsl.pojo.qo.dzp.region.DZPProvinceQO;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**电子票务-景区服务
 * Created by huanggg on 2016/3/3.
 */
@Service
@Transactional
public class DZPScenicSpotLocalService extends BaseServiceImpl<DZPScenicSpot,DZPScenicSpotQO,DZPScenicSpotDAO> {

	@Override
	protected DZPScenicSpotDAO getDao() {

		return dzpwScenicspotDao;
	}

	private String    devName            = "hgg";

	@Autowired
	private DZPScenicSpotDAO             dzpwScenicspotDao;

	@Autowired
	private DealerApiClient 			   client;

	@Autowired
	private DZPScenicSpotPicDAO           scenicSpotPicDao;

	@Autowired
	private DZPAreaDAO ZPWAreaDao;

	@Autowired
	private DZPCityDAO DZPCityDao;
	@Autowired
	private DZPProvinceDAO DZPProviceDao;

	/**
	 * 电子票务-景区列表
	 * @return
	 */
	public List<DZPScenicSpot> list(DZPScenicSpotQO qo){

		List<DZPScenicSpot> scenicSpots = dzpwScenicspotDao.queryList(qo);
		if(CollectionUtils.isEmpty(scenicSpots)){
			return null;
		}

		return scenicSpots;
	}

	/**
	 * 景区详情
	 * @param qo
	 * @return
	 * @throws ShowMessageException
	 */
	public DZPScenicSpot detail(DZPScenicSpotQO qo) throws ShowMessageException {

		if(StringUtils.isBlank(qo.getId())){
			throw new ShowMessageException("传入参数错误");
		}

		return dzpwScenicspotDao.queryUnique(qo);
	}

	/**
	 * 同步景区
	 */
	public Integer syncScenicSpot(ScenicSpotQO qo){
		HgLogger.getInstance().info(devName,"同步电子票务开始");

		//调用电子票借口获取景区数据
		ScenicSpotResponse response = client.send(qo, ScenicSpotResponse.class);
		List<ScenicSpotDTO> scenicSpots = response.getScenicSpots();
		if(CollectionUtils.isEmpty(scenicSpots)){
			return -1;
		}

		for (ScenicSpotDTO scenicSpotDto : scenicSpots){
			saveScenicspot(scenicSpotDto);
		}

		HgLogger.getInstance().info(devName,"同步电子票务结束");

		return scenicSpots.size();
	}

	/**
	 * 保存或者更新景区
	 * @param scenicSpotDto
	 */
	private void saveScenicspot(ScenicSpotDTO scenicSpotDto){
		//景区ID
		String scenicSpotId = scenicSpotDto.getId();
		if(StringUtils.isNotBlank(scenicSpotId)){
			DZPScenicSpotQO qo = new DZPScenicSpotQO();
			qo.setId(scenicSpotId);
			DZPScenicSpot scenicSpot = dzpwScenicspotDao.queryUnique(qo);
			//【1】执行更新
			if(scenicSpot != null){
				updateScenicSpot(scenicSpotDto,scenicSpot);
			//【2】新增
			}else{
				createNewScenic(scenicSpotDto);
			}
		}
	}

	/**
	 * 新增景区
	 * @param scenicSpotDto
	 */
	private  void createNewScenic(ScenicSpotDTO scenicSpotDto){
		DZPScenicSpot scenicSpot = new DZPScenicSpot();
		scenicSpot.setId(scenicSpotDto.getId());
		//景区基本信息
		DZPScenicSpotBaseInfo baseInfo = scenicSpot.getBaseInfo();
		ScenicSpotBaseInfoDTO baseInfoDTO = scenicSpotDto.getBaseInfo();
		baseInfo.setShortName(baseInfoDTO.getShortName());
		baseInfo.setName(baseInfoDTO.getName());
		if(StringUtils.isNotBlank(baseInfoDTO.getLevel())){
			baseInfo.setLevel(baseInfoDTO.getLevel().length()-1);
		}
		baseInfo.setAliasName(baseInfoDTO.getAliasName());
		baseInfo.setBuildLevel(baseInfoDTO.getBuildLevel());
		baseInfo.setClassify(baseInfoDTO.getClassify());
		baseInfo.setCode(baseInfoDTO.getCode());
		baseInfo.setCreateDate(baseInfoDTO.getCreateDate());
		baseInfo.setFeature(baseInfoDTO.getFeature());
		baseInfo.setIntro(baseInfoDTO.getIntro());
		baseInfo.setModifyDate(baseInfoDTO.getModifyDate());
		baseInfo.setOpenTime(baseInfoDTO.getOpenTime());
		baseInfo.setPreNotice(baseInfoDTO.getPreNotice());
		baseInfo.setStreet(baseInfoDTO.getStreet());
		baseInfo.setTicketDefaultValidDays(baseInfoDTO.getTicketDefaultValidDays());
		baseInfo.setWebSite(baseInfoDTO.getWebSite());

		//景区联系信息
		DZPScenicSpotContactInfo contactInfo = scenicSpot.getContactInfo();
		ScenicSpotContactInfoDTO contactInfoDTO = scenicSpotDto.getContactInfo();
		contactInfo.setAddress(contactInfoDTO.getAddress());
		contactInfo.setEmail(contactInfoDTO.getEmail());
		contactInfo.setFax(contactInfoDTO.getFax());
		contactInfo.setLinkMan(contactInfoDTO.getLinkMan());
		contactInfo.setPostcode(contactInfoDTO.getPostcode());
		contactInfo.setQq(contactInfoDTO.getQq());
		contactInfo.setTelephone(contactInfoDTO.getTelephone());
		contactInfo.setTraffic(contactInfoDTO.getTraffic());

		//执行保存操作
		dzpwScenicspotDao.save(scenicSpot);

		//设置地区
		String areaId = baseInfoDTO.getAreaId();
		DZPAreaQO areaQo = new DZPAreaQO();
		areaQo.setId(areaId);
		DZPArea dzpArea = ZPWAreaDao.queryUnique(areaQo);
		if(dzpArea != null){
			scenicSpot.getBaseInfo().setArea(dzpArea);
		}
		//设置省份
		DZPProvinceQO provinceQo = new DZPProvinceQO();
		provinceQo.setId(baseInfoDTO.getProvinceId());
		DZPProvince dzpProvince = DZPProviceDao.queryUnique(provinceQo);
		if(dzpProvince != null){
			scenicSpot.getBaseInfo().setProvince(dzpProvince);
		}
		//设置城市
		DZPCityQO cityQo = new DZPCityQO();
		cityQo.setId(baseInfoDTO.getCityId());
		DZPCity dzpCity = DZPCityDao.queryUnique(cityQo);
		if(dzpCity != null){
			scenicSpot.getBaseInfo().setCity(dzpCity);
		}

		//其他信息
		List<ScenicSpotPicDTO> pics = scenicSpotDto.getPics();

		//创建或者修改景区图集
		saveOrUpdateScenicspotImage(pics, scenicSpot);

		//执行更新操作
		dzpwScenicspotDao.update(scenicSpot);
	}

	/**
	 * 更新景区
	 * @param scenicSpotDto
	 * @param scenicSpot
	 */
	private void updateScenicSpot(ScenicSpotDTO scenicSpotDto , DZPScenicSpot scenicSpot){

		//景区基本信息
		DZPScenicSpotBaseInfo baseInfo = scenicSpot.getBaseInfo();
		ScenicSpotBaseInfoDTO baseInfoDTO = scenicSpotDto.getBaseInfo();
		baseInfo.setShortName(baseInfoDTO.getShortName());
		baseInfo.setName(baseInfoDTO.getName());
		if(StringUtils.isNotBlank(baseInfoDTO.getLevel())){
			baseInfo.setLevel(baseInfoDTO.getLevel().length()-1);
		}
		baseInfo.setAliasName(baseInfoDTO.getAliasName());
		//设置地区
		String areaId = baseInfoDTO.getAreaId();
		DZPAreaQO areaQo = new DZPAreaQO();
		areaQo.setId(areaId);
		DZPArea dzpArea = ZPWAreaDao.queryUnique(areaQo);
		if(dzpArea != null){
			baseInfo.setArea(dzpArea);
		}
		//设置省份
		DZPProvinceQO provinceQo = new DZPProvinceQO();
		provinceQo.setId(baseInfoDTO.getProvinceId());
		DZPProvince dzpProvince = DZPProviceDao.queryUnique(provinceQo);
		if(dzpProvince != null){
			baseInfo.setProvince(dzpProvince);
		}
		//设置城市
		DZPCityQO cityQo = new DZPCityQO();
		cityQo.setId(baseInfoDTO.getCityId());
		DZPCity dzpCity = DZPCityDao.queryUnique(cityQo);
		if(dzpCity != null){
			baseInfo.setCity(dzpCity);
		}

		baseInfo.setBuildLevel(baseInfoDTO.getBuildLevel());
		baseInfo.setClassify(baseInfoDTO.getClassify());
		baseInfo.setCode(baseInfoDTO.getCode());
		baseInfo.setCreateDate(baseInfoDTO.getCreateDate());
		baseInfo.setFeature(baseInfoDTO.getFeature());
		baseInfo.setIntro(baseInfoDTO.getIntro());
		baseInfo.setModifyDate(baseInfoDTO.getModifyDate());
		baseInfo.setOpenTime(baseInfoDTO.getOpenTime());
		baseInfo.setPreNotice(baseInfoDTO.getPreNotice());
		baseInfo.setStreet(baseInfoDTO.getStreet());
		baseInfo.setTicketDefaultValidDays(baseInfoDTO.getTicketDefaultValidDays());
		baseInfo.setWebSite(baseInfoDTO.getWebSite());

		//景区联系信息
		DZPScenicSpotContactInfo contactInfo = scenicSpot.getContactInfo();
		ScenicSpotContactInfoDTO contactInfoDTO = scenicSpotDto.getContactInfo();
		contactInfo.setAddress(contactInfoDTO.getAddress());
		contactInfo.setEmail(contactInfoDTO.getEmail());
		contactInfo.setFax(contactInfoDTO.getFax());
		contactInfo.setLinkMan(contactInfoDTO.getLinkMan());
		contactInfo.setPostcode(contactInfoDTO.getPostcode());
		contactInfo.setQq(contactInfoDTO.getQq());
		contactInfo.setTelephone(contactInfoDTO.getTelephone());
		contactInfo.setTraffic(contactInfoDTO.getTraffic());

		//其他信息
		List<ScenicSpotPicDTO> pics = scenicSpotDto.getPics();
		//修改或者创建景区图集
		saveOrUpdateScenicspotImage(pics, scenicSpot);

		//执行更新操作
		dzpwScenicspotDao.update(scenicSpot);
	}

	/**
	 * 创建或者修改景区图集
	 * @param pics
	 * @param scenicSpot
	 */
	private void saveOrUpdateScenicspotImage(List<ScenicSpotPicDTO> pics,DZPScenicSpot scenicSpot){
		List<DZPScenicSpotPic> scenicSpotPics = new ArrayList<DZPScenicSpotPic>();
		//【1】设置景区图片
		List<DZPScenicSpotPic> dzpScenicSpotPics = scenicSpot.getPics();//本地的景区图片
		DZPScenicSpotPic currentCover = scenicSpot.getCover();//当前景区封面
		//如果有本地的景区图片,先删除，再新增
		if(CollectionUtils.isNotEmpty(dzpScenicSpotPics)){
			scenicSpot.setCover(new DZPScenicSpotPic());//先删除景区关联的封面
			dzpwScenicspotDao.update(scenicSpot);
			for (DZPScenicSpotPic dzpScenicSpotPic : dzpScenicSpotPics){
				if(!StringUtils.equals(dzpScenicSpotPic.getId(),currentCover.getId())){
					scenicSpotPicDao.delete(dzpScenicSpotPic);
				}
			}
		}
		//新增图片
		for(ScenicSpotPicDTO pic : pics){
			DZPScenicSpotPic scenicSpotPic = new DZPScenicSpotPic();
			scenicSpotPic.setId(UUIDGenerator.getUUID());
			scenicSpotPic.setName(pic.getName());
			scenicSpotPic.setUrl(pic.getUrl());
			scenicSpotPic.setScenicSpot(scenicSpot);
			scenicSpotPicDao.save(scenicSpotPic);
			scenicSpotPics.add(scenicSpotPic);
		}

		scenicSpot.setPics(scenicSpotPics);

		//【2】设置景区封面
		if(CollectionUtils.isNotEmpty(scenicSpotPics)){
			//新景区封面
			scenicSpot.setCover(scenicSpotPics.get(0));
			if(currentCover != null){
				scenicSpotPicDao.delete(currentCover);
			}
		}
	}
}
