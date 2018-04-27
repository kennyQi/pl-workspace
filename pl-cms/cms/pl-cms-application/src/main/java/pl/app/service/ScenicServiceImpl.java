package pl.app.service;
import java.util.ArrayList;
import java.util.List;
import hg.common.component.BaseServiceImpl;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.spi.inter.ImageService_1;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.dao.ScenicDao;
import pl.cms.domain.entity.image.Image;
import pl.cms.domain.entity.scenic.Scenic;
import pl.cms.pojo.command.scenic.CreateScenicCommand;
import pl.cms.pojo.command.scenic.DeleteScenicCommand;
import pl.cms.pojo.command.scenic.ModifyScenicCommand;
import pl.cms.pojo.qo.ScenicQO;
@Service
@Transactional
public class ScenicServiceImpl extends BaseServiceImpl<Scenic, ScenicQO, ScenicDao> {
    @Autowired
    private ScenicDao scenicDao;
    @Autowired
	private ImageService_1 imageService;
    @Autowired
	private ImageService imageLocalService;
	public void createScenic(CreateScenicCommand command) {
		//保存景区
		Scenic scenic=new Scenic();
		// 向图片服务创建景区标题图片
		Image titleImage = imageLocalService.createTitleImage(command.getTitleImageFileInfo(),
						command.getName(), command.getName(), "PL_CMS_SCENIC_TITLE_IMAGE", "PL_CMS_SCENIC_TITLE_IMAGE");
		scenic.createScenic(command,titleImage);
		scenicDao.save(scenic);
	}
	
	public void modifyScenic(ModifyScenicCommand command){
		Scenic scenic=scenicDao.get(command.getScenicId());
		Image image = scenic.getScenicSpotBaseInfo().getTitleImage();

		// 判断图片是否发生了变化
		if (checkScenicTitleImage(scenic, command.getTitleImageFileInfo())) {
			DeleteImageCommand deleteImageCommand = new DeleteImageCommand(
					SysProperties.getInstance().get("imageServiceProjectId"),
					SysProperties.getInstance().get("imageServiceProjectEnvName"));
			//	删除旧图片
			List<String> ids = new ArrayList<String>();
			ids.add(image.getImageId());
			deleteImageCommand.setImageIds(ids);
			
			imageService.deleteImage_1(deleteImageCommand);
			
			// 向图片服务保存最新图片
			image = imageLocalService.createTitleImage(command.getTitleImageFileInfo(),
					command.getName(), command.getName(), "PL_CMS_SCENIC_TITLE_IMAGE", "PL_CMS_SCENIC_TITLE_IMAGE");
		}
		scenic.modifyScenic(command,image);
		scenicDao.update(scenic);
	}
	
	public void deleteScenic(DeleteScenicCommand command) throws Exception {
		Scenic scenic=scenicDao.get(command.getScenicId());
		scenicDao.deleteById(command.getScenicId());
		List<String> imageIds=new ArrayList<>();
		DeleteImageCommand deleteImageCommand=new DeleteImageCommand(
				SysProperties.getInstance().get("imageServiceProjectId"),
				SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageIds.add(scenic.getScenicSpotBaseInfo().getTitleImage().getImageId());
		deleteImageCommand.setImageIds(imageIds);
		imageService.deleteImage_1(deleteImageCommand);
	}
	@Override
	protected ScenicDao getDao() {
		return scenicDao;
	}
	/**
	 * 
	 * @方法功能说明：判断文章标题图片是否有变化
	 * @修改者名字：yuxx
	 * @修改时间：2015年4月8日上午10:25:51
	 * @修改内容：
	 * @参数：@param article
	 * @参数：@param command
	 * @参数：@return true代表有变，false代表不变
	 * @return:boolean
	 * @throws
	 */
	private boolean checkScenicTitleImage(Scenic scenic,FdfsFileInfo tempFileInfo) {
		return !StringUtils.equals(scenic.getScenicSpotBaseInfo().getTitleImage().getImageId(), tempFileInfo.getImageId());
	}
}
