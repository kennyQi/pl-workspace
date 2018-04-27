package plfx.mp.app.service.spi;

import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.component.job.ScenicSpotUpdateJob;
import plfx.mp.app.service.local.ImageSpecLocalService;
import plfx.mp.app.service.local.ScenicSpotLocalService;
import plfx.mp.app.service.local.TCPolicyNoticeLocalService;
import plfx.mp.app.service.local.TCScenicSpotsLocalService;
import plfx.mp.domain.model.scenicspot.ImageSpecTemp;
import plfx.mp.domain.model.scenicspot.ScenicSpot;
import plfx.mp.domain.model.scenicspot.ScenicSpotsBaseInfo;
import plfx.mp.domain.model.scenicspot.ScenicSpotsGeographyInfo;
import plfx.mp.domain.model.scenicspot.TCScenicSpots;
import plfx.mp.domain.model.scenicspot.TCScenicSpotsTheme;
import plfx.mp.domain.model.supplierpolicy.TCPolicyNotice;
import plfx.mp.spi.inter.ScenicSpotUpdateService;
import plfx.mp.tcclient.tc.domain.ImagePath;
import plfx.mp.tcclient.tc.domain.ImageSize;
import plfx.mp.tcclient.tc.domain.Scenery;
import plfx.mp.tcclient.tc.domain.Theme;
import plfx.mp.tcclient.tc.dto.jd.SceneryDetailDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryImageListDto;
import plfx.mp.tcclient.tc.dto.jd.SceneryTrafficInfoDto;
import plfx.mp.tcclient.tc.pojo.Response;
import plfx.mp.tcclient.tc.pojo.ResultSceneryDetail;
import plfx.mp.tcclient.tc.pojo.ResultSceneryImageList;
import plfx.mp.tcclient.tc.pojo.ResultSceneryList;
import plfx.mp.tcclient.tc.pojo.ResultSceneryTrafficInfo;
import plfx.mp.tcclient.tc.service.TcClientService;
@Service("scenicSpotUpdateService")
public class ScenicSpotUpdateServiceImpl implements ScenicSpotUpdateService {
	private final static Logger logger = LoggerFactory.getLogger(ScenicSpotUpdateJob.class);

	@Autowired
	private TcClientService service;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	@Autowired
	private TCScenicSpotsLocalService tcScenicSpotsLocalService;
	@Autowired
	private ImageSpecLocalService imageSpecLocalService;
	@Autowired
	private TCPolicyNoticeLocalService tcPolicyNoticeLocalService;

	
	String devName = "zhurz";
	
	@Transactional
	public void updateScenicSpot(Scenery scenery, String baseImgPath){
		
		// 有水印的图片尺寸代码
		List<String> watermarkSizeCode = Arrays.asList(new String[] {"300_225", "350_263", "448_228", "740_350" });

		boolean existScenicSpot = true;
		boolean existTCScenicSpot = true;
		
		TCScenicSpots tss = tcScenicSpotsLocalService.get(scenery.getSceneryId());
		ScenicSpot ss = scenicSpotLocalService.getByTcScenicId(scenery.getSceneryId());
		TCPolicyNotice tpn = tcPolicyNoticeLocalService.get(scenery.getSceneryId());
		
		if (tss == null)
			existTCScenicSpot = false;
		if (ss == null) {
			existScenicSpot = false;
			ss = new ScenicSpot();
			
			if (!existTCScenicSpot) {
				tss = new TCScenicSpots();
				tss.setId(scenery.getSceneryId());
			}
			tss.setScenicSpot(ss);

			ss.setId(UUIDGenerator.getUUID());
			ss.setTcScenicSpotInfo(tss);
		} else {
			tss = ss.getTcScenicSpotInfo();
		}
		if (tpn == null) {
			tpn = new TCPolicyNotice();
			tpn.setId(tss.getId());
			tcPolicyNoticeLocalService.save(tpn);
		}
		
		// 同程景点主题
		List<Theme> themes = scenery.getThemeList();
		if (themes != null) {
			tss.setThemes(BeanMapperUtils.getMapper().mapAsList(themes, TCScenicSpotsTheme.class));
			tss.getThemesIds();
			tss.getThemesJson();
		}

		// 获取景点图片列表
		SceneryImageListDto sceneryImageListDto = new SceneryImageListDto();
		sceneryImageListDto.setSceneryId(scenery.getSceneryId());
		sceneryImageListDto.setPage(1);
		sceneryImageListDto.setPageSize(100);
		Response<ResultSceneryImageList> response3 = service.getSceneryImageList(sceneryImageListDto);
		ResultSceneryImageList resultSceneryImageList = response3.getResult();
		List<ImagePath> imagePaths = resultSceneryImageList.getImage();
		List<ImageSize> imageSizes = null;
		String imgBaseUrl = resultSceneryImageList.getImageBaseUrl();
		if (resultSceneryImageList.getSizeCodeList() != null) {
			imageSizes = resultSceneryImageList.getSizeCodeList().getSizeCode();
		}

		// 获取景点详细信息
		SceneryDetailDto sceneryDetailDto = new SceneryDetailDto();
		sceneryDetailDto.setSceneryId(scenery.getSceneryId());
		Response<ResultSceneryDetail> response2 = service.getSceneryDetail(sceneryDetailDto);
		ResultSceneryDetail result2 = response2.getResult();

		ScenicSpotsBaseInfo scenicSpotsBaseInfo = ss.getScenicSpotBaseInfo();
		ScenicSpotsGeographyInfo scenicSpotGeographyInfo = ss.getScenicSpotGeographyInfo();

		// 平台景点
		scenicSpotsBaseInfo.setName(scenery.getSceneryName());
		scenicSpotsBaseInfo.setIntro(scenery.getScenerySummary());
		scenicSpotsBaseInfo.setImage(baseImgPath + scenery.getImgPath());
		scenicSpotsBaseInfo.setGrade(result2.getGrade());
		if (scenery.getNaList() != null && scenery.getNaList().size() > 0) {
			scenicSpotsBaseInfo.setAlias(scenery.getNaList().get(0).getName());
		}

		// 景点地理信息
		scenicSpotGeographyInfo.setAddress(scenery.getSceneryAddress());
		scenicSpotGeographyInfo.setProvinceCode(String.valueOf(scenery.getProvinceId()));
		scenicSpotGeographyInfo.setCityCode(String.valueOf(scenery.getCityId()));
		scenicSpotGeographyInfo.setAreaCode(String.valueOf(scenery.getCountyId()));
		scenicSpotGeographyInfo.setLongitude(scenery.getLon());
		scenicSpotGeographyInfo.setLatitude(scenery.getLat());
		
		// 交通指南
		SceneryTrafficInfoDto dto = new SceneryTrafficInfoDto();
		dto.setSceneryId(scenery.getSceneryId());
		Response<ResultSceneryTrafficInfo> response4 = service.getSceneryTrafficInfo(dto);
		ResultSceneryTrafficInfo trafficInfo = response4.getResult();
		if ("0".equals(response4.getHead().getRspType()) && trafficInfo != null) {
			scenicSpotGeographyInfo.setTraffic(trafficInfo.getTraffic());
		}

		// 图片信息
		List<ImageSpecTemp> imageSpecs = new ArrayList<ImageSpecTemp>();
		if (imagePaths != null && imageSizes != null) {
			// 只取默认尺寸
			ImageSize defaultImageSize = null;
			Set<String> sizeSet = new HashSet<String>();
			for (ImageSize imageSize : imageSizes) {
				if (sizeSet.contains(imageSize.getSizeCode()))
					continue;
				sizeSet.add(imageSize.getSizeCode());
				if (imageSize.getIsDefault() != null && imageSize.getIsDefault())
					defaultImageSize = imageSize;
			}
			if (defaultImageSize != null) {
				imageSizes.clear();
				imageSizes.add(defaultImageSize);
			} else {
				// 没有默认尺寸则去除重复
				sizeSet.clear();
				List<ImageSize> imageSizes2 = new ArrayList<ImageSize>();
				for (ImageSize imageSize : imageSizes) {
					if (sizeSet.contains(imageSize.getSizeCode()))
						continue;
					sizeSet.add(imageSize.getSizeCode());
					imageSizes2.add(imageSize);
				}
				imageSizes = imageSizes2;
			}
			
			for (ImagePath imgPath : imagePaths) {
				// 每种图片对应多种尺寸
				for (ImageSize imageSize : imageSizes) {
					ImageSpecTemp imageSpec = new ImageSpecTemp();
					imageSpec.setId(UUIDGenerator.getUUID());
					imageSpec.setScenicSpot(ss);
					imageSpec.setUrl(imgBaseUrl + imgPath.getImagePath());
					//imageSpec.setUrl(imgBaseUrl + imageSize.getSizeCode() + "/" + imgPath.getImagePath());
					imageSpec.setAlbumId(imgPath.getImagePath().replaceAll("[\\D]", ""));
					if (watermarkSizeCode.indexOf(imageSpec.getSizeCode()) != -1) {
						imageSpec.setWatermark(true);
					} else {
						imageSpec.setWatermark(false);
					}
					imageSpec.setSizeCode(imageSize.getSizeCode());
					imageSpec.setSize(imageSize.getSize());
					String[] size = imageSpec.getSizeCode().split("_");
					if (size.length >= 2) {
						imageSpec.setWidth(Integer.valueOf(size[0]));
						imageSpec.setHeight(Integer.valueOf(size[1]));
					}
					imageSpecs.add(imageSpec);
				}
			}
		}

		// 同程景点
		tss.setCommentCount(scenery.getCommentCount());
		tss.setQuestionCount(scenery.getQuestionCount());
		tss.setBlogCount(scenery.getBlogCount());
		tss.setViewCount(scenery.getViewCount());
		tss.setBookFlag(scenery.getBookFlag().intValue() == 1 ? true : false);
		tss.setAmountAdvice(scenery.getAmountAdv().doubleValue());
		if (result2.getIfUseCard() != null)
			tss.setIfUseCard(result2.getIfUseCard().intValue() == 1 ? true : false);
		tpn.setBuyNotie(result2.getBuyNotice());
		tss.setPayMode(result2.getPayMode());

		// 保存入库
		tpn.getNoticeJson();
		tcPolicyNoticeLocalService.update(tpn);
		// 保存或更新同程景点
		if (existTCScenicSpot) {
			tcScenicSpotsLocalService.update(tss);
		} else {
			tcScenicSpotsLocalService.save(tss);
		}
		if (existScenicSpot) {
			// 保存图片
			imageSpecLocalService.deleteByScenicSpotId(ss.getId());
			ImageSpecTemp[] imageSpecs2 = new ImageSpecTemp[imageSpecs.size()];
			imageSpecs.toArray(imageSpecs2);
			imageSpecLocalService.saveArray(imageSpecs2);
			// 更新平台景点
			scenicSpotLocalService.update(ss);
		} else {
			// 保存图片
			ImageSpecTemp[] imageSpecs2 = new ImageSpecTemp[imageSpecs.size()];
			imageSpecs.toArray(imageSpecs2);
			imageSpecLocalService.saveArray(imageSpecs2);
			// 保存平台景点
			scenicSpotLocalService.save(ss);
		}
		
	}
	
	public void updateScenicSpot() {
		
		HgLogger.getInstance().info(this.getClass(), devName, "开始更新景点数据", "定时任务 景点更新");
		
		SceneryDto sceneryDto = new SceneryDto();
		Integer totalPage = 1;
		Integer currentPage = 1;

		while (currentPage <= totalPage) {

			// 查询景点列表
			sceneryDto.setPage(currentPage);
			Response<ResultSceneryList> response = service.getSceneryList(sceneryDto);
			ResultSceneryList result = response.getResult();
			List<Scenery> sceneries = result.getScenery();
			String baseImgPath = result.getImgbaseURL();

			for (Scenery scenery : sceneries) {
				try {
					updateScenicSpot(scenery, baseImgPath);
				} catch (Exception e) {
					e.printStackTrace();
					HgLogger.getInstance().error(this.getClass(), devName, "更新失败>>sceneryId:"+scenery.getSceneryId(), e, "定时任务 景点更新");
				}
			}

			currentPage = result.getPage();
			totalPage = result.getTotalPage();
			
			logger.info("当前景点更新进度{}/{}", currentPage, totalPage);
			HgLogger.getInstance().info(this.getClass(), devName, String.format("当前景点更新进度%d/%d", currentPage, totalPage), "定时任务 景点更新");
			
			currentPage++;
		}

		HgLogger.getInstance().info(this.getClass(), devName, "景点数据更新完毕", "定时任务 景点更新");
		
	}
	@Override
	public void scenicSpotUpdateJob() {
		updateScenicSpot();
	}

}
