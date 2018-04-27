package pl.app.service;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import pl.app.dao.IndexLunboDao;
import pl.cms.domain.entity.image.Image;
import pl.cms.domain.entity.lunbo.IndexLunbo;
import pl.cms.pojo.command.lunbo.CreateIndexLunboCommand;
import pl.cms.pojo.command.lunbo.HideIndexLunboCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboImageCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboTitleCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboUrlCommand;
import pl.cms.pojo.command.lunbo.ModifyLunboCommand;
import pl.cms.pojo.command.lunbo.ShowIndexLunboCommand;
import pl.cms.pojo.qo.IndexLunboQO;
import hg.common.component.BaseServiceImpl;
import hg.common.component.RemoteConfigurer;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.service.ad.command.ad.ChangeAdImageCommand;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.HideAdCommand;
import hg.service.ad.command.ad.ModifyAdCommand;
import hg.service.ad.command.ad.ShowAdCommand;
import hg.service.ad.command.position.CreateAdPositionCommand;
import hg.service.ad.domain.model.ad.Ad;
import hg.service.ad.domain.model.position.AdPosition;
import hg.service.ad.domain.model.position.AdPositionBaseInfo;
import hg.service.ad.pojo.qo.ad.AdPositionQO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.spi.inter.ImageService_1;

@Service
@Transactional
public class IndexLunboServiceImpl extends
		BaseServiceImpl<IndexLunbo, IndexLunboQO, IndexLunboDao> {

	@Autowired
	private AdServiceImpl adServiceImpl;
	@Autowired
	private AdPositionServiceImpl adPositionServiceImpl;
	@Autowired
	private ImageService imageLocalService;
	@Autowired
	private RemoteConfigurer remoteConfigurer;
	@Autowired
	private ImageService_1 imageSpiService;
	@Autowired
	private IndexLunboDao indexLunboDao;

	public void createIndexLunbo(CreateIndexLunboCommand command) {

		// 检查广告位是否已建立，没有则新建
		AdPositionQO adPositionQO = new AdPositionQO();
		adPositionQO.setClientType(AdPositionBaseInfo.CLIENT_TYPE_H5);
		adPositionQO.setId("H5_INDEX_LUNBO");
		AdPosition adPosition = adPositionServiceImpl.queryUnique(adPositionQO);

		if (adPosition == null) {
			CreateAdPositionCommand createAdPositionCommand = new CreateAdPositionCommand();
			createAdPositionCommand.setName("首页轮播图");
			createAdPositionCommand.setDescription("首页轮播图");
			createAdPositionCommand
					.setClientType(AdPositionBaseInfo.CLIENT_TYPE_H5);
			createAdPositionCommand.setImageUseTypeId("PL_CMS_INDEX_LUNBO");
			createAdPositionCommand.setLoadNo(5);
			createAdPositionCommand.setShowNo(5);
			createAdPositionCommand.setChangeSpeedSecond(5);

			adPosition = adPositionServiceImpl
					.createAdPosition(createAdPositionCommand);
		}

		// 向图片服务创建广告上传的图片
		Image image = imageLocalService.createTitleImage(
				command.getTitleImageFileInfo(), command.getTitle(),
				command.getTitle(), "PL_CMS_INDEX_LUNBO", "PL_CMS_INDEX_LUNBO");

		// 建立广告实体
		CreateAdCommand createAdCommand = new CreateAdCommand();
		createAdCommand.setImageUrl(image.getUrlMapsJSON());
		createAdCommand.setShow(false);
		createAdCommand.setPositionId(adPosition.getId());
		createAdCommand.setImageId(image.getImageId());

		// 根据现有轮播条数决定当前要插入的轮播优先级
		IndexLunboQO indexLunboQO = new IndexLunboQO();
		Integer indexLunboNo = indexLunboDao.queryCount(indexLunboQO);
		createAdCommand.setPriority(indexLunboNo + 1);

		createAdCommand.setRemark("首页轮播图");
		createAdCommand.setTitle(command.getTitle());
		createAdCommand.setUrl(command.getUrl());

		Ad ad = adServiceImpl.createAd(createAdCommand);

		// 建立轮播实体
		IndexLunbo indexLunbo = new IndexLunbo();
		indexLunbo.create(command, ad.getId(), image.getUrlMapsJSON());

		indexLunboDao.save(indexLunbo);
	}

	/**
	 * @方法功能说明：修改首页轮播
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月19日下午2:48:23
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyIndexLunbo(ModifyLunboCommand command) {
		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		AdQO adQO = new AdQO();
		adQO.setId(indexLunbo.getAdId());
		Ad ad = adServiceImpl.queryUnique(adQO);

		Image image = new Image();
		image.setImageId(ad.getImage().getImageId());
		// 判断图片是否发生了变化
		if (checkAdImage(ad, command.getTitleImageFileInfo())) {
			DeleteImageCommand deleteImageCommand = new DeleteImageCommand(
					SysProperties.getInstance().get("imageServiceProjectId"),
					SysProperties.getInstance().get(
							"imageServiceProjectEnvName"));
			// 删除旧图片
			List<String> ids = new ArrayList<String>();
			ids.add(ad.getImage().getImageId());
			deleteImageCommand.setImageIds(ids);

			imageSpiService.deleteImage_1(deleteImageCommand);

			// 向图片服务保存最新图片
			image = imageLocalService.createTitleImage(
					command.getTitleImageFileInfo(), command.getTitle(),
					command.getTitle(), "PL_CMS_INDEX_LUNBO",
					"PL_CMS_INDEX_LUNBO");

			// 修改广告中的图片信息
			ChangeAdImageCommand changeAdImageCommand = new ChangeAdImageCommand();
			changeAdImageCommand.setAdId(indexLunbo.getAdId());
			changeAdImageCommand.setImageId(image.getImageId());
			changeAdImageCommand.setImageUrl(image.getUrlMapsJSON());
			adServiceImpl.changeAdImage(changeAdImageCommand);
			command.setImageUrl(image.getUrlMapsJSON());
		}

		// // 修改广告
		// Ad ad = adServiceImpl.get(indexLunbo.getAdId());
		// ModifyAdCommand modifyAdCommand = new ModifyAdCommand();
		// modifyAdCommand.setAdId(ad.getId());
		// modifyAdCommand.setImageId(ad.getImage().getImageId());
		// modifyAdCommand.setImageUrl(ad.getImage().getUrl());
		// modifyAdCommand.setPriority(ad.getBaseInfo().getPriority());
		// modifyAdCommand.setRemark(ad.getBaseInfo().getRemark());
		// modifyAdCommand.setTitle(ad.getBaseInfo().getTitle());
		// modifyAdCommand.setUrl(ad.getBaseInfo().getUrl());
		// adServiceImpl.modifyAd(modifyAdCommand);

		indexLunbo.modify(command);

		indexLunboDao.update(indexLunbo);
	}

	/**
	 * 
	 * @方法功能说明：判断轮播图片是否有变化
	 * @修改者名字：yuxx
	 * @修改时间：2015年4月8日上午10:25:51
	 * @修改内容：
	 * @参数：@param article
	 * @参数：@param command
	 * @参数：@return true代表有变，false代表不变
	 * @return:boolean
	 * @throws
	 */
	private boolean checkAdImage(Ad ad, FdfsFileInfo tempFileInfo) {
		return !StringUtils.equals(ad.getImage().getImageId(),
				tempFileInfo.getImageId());
	}

	public void modifyIndexLunboTitle(ModifyIndexLunboTitleCommand command) {

		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		indexLunbo.modifyTitle(command);

		indexLunboDao.update(indexLunbo);
	}

	public void modifyIndexLunboImage(ModifyIndexLunboImageCommand command) {

		FdfsFileInfo fileInfo = JSON.parseObject(
				command.getTitleImageFileInfoJSON(), FdfsFileInfo.class);

		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		// 修改广告中的图片信息
		ChangeAdImageCommand changeAdImageCommand = new ChangeAdImageCommand();
		changeAdImageCommand.setAdId(indexLunbo.getAdId());
		changeAdImageCommand.setImageId(fileInfo.getImageId());
		changeAdImageCommand.setImageUrl(fileInfo.getUri());
		changeAdImageCommand.setImageInfo(command.getTitleImageFileInfoJSON());
		adServiceImpl.changeAdImage(changeAdImageCommand);

		// 修改轮播中的图片信息
		indexLunbo.modifyImage(fileInfo.getUri());

		indexLunboDao.update(indexLunbo);
	}

	public void modifyIndexLunboUrl(ModifyIndexLunboUrlCommand command) {

		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		// 修改广告
		Ad ad = adServiceImpl.get(indexLunbo.getAdId());
		ModifyAdCommand modifyAdCommand = new ModifyAdCommand();
		modifyAdCommand.setAdId(ad.getId());
		modifyAdCommand.setImageId(ad.getImage().getImageId());
		modifyAdCommand.setImageUrl(ad.getImage().getUrl());
		modifyAdCommand.setPriority(ad.getBaseInfo().getPriority());
		modifyAdCommand.setRemark(ad.getBaseInfo().getRemark());
		modifyAdCommand.setTitle(ad.getBaseInfo().getTitle());
		modifyAdCommand.setUrl(ad.getBaseInfo().getUrl());
		adServiceImpl.modifyAd(modifyAdCommand);

		indexLunbo.modifyUrl(command);

		indexLunboDao.update(indexLunbo);
	}

	public void showIndexLunbo(ShowIndexLunboCommand command) {
		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		ShowAdCommand showAdCommand = new ShowAdCommand();
		showAdCommand.setAdId(indexLunbo.getAdId());

		adServiceImpl.showAd(showAdCommand);

		// indexLunbo.show();

		indexLunboDao.update(indexLunbo);
	}

	public void hideIndexLunbo(HideIndexLunboCommand command) {
		IndexLunbo indexLunbo = indexLunboDao.load(command.getLunboId());

		HideAdCommand hideAdCommand = new HideAdCommand();
		hideAdCommand.setAdId(indexLunbo.getAdId());

		adServiceImpl.hideAd(hideAdCommand);

		// indexLunbo.hide();

		indexLunboDao.update(indexLunbo);
	}

	@Override
	protected IndexLunboDao getDao() {
		return indexLunboDao;
	}

}
