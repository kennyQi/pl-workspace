package hg.service.image.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.file.FdfsFileInfo;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.service.image.app.common.Assert;
import hg.service.image.app.dao.ImageDao;
import hg.service.image.app.dao.ImageSpecDao;
import hg.service.image.app.dao.ImageUseTypeDao;
import hg.service.image.command.image.spec.CreateImageSpecCommand;
import hg.service.image.command.image.spec.ModifyImageSpecCommand;
import hg.service.image.command.image.spec.DeleteImageSpecCommand;
import hg.service.image.domain.model.Image;
import hg.service.image.domain.model.ImageSpec;
import hg.service.image.domain.model.ImageSpecKey;
import hg.service.image.domain.qo.ImageSpecLocalQo;
import hg.service.image.pojo.exception.ImageException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：图片规格本地Service
 * @修改者名字：chenys
 * @修改时间：2014年12月15日 下午6:22:47
 * @修改内容：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:58:26
 */
@Service
@Transactional
public class ImageSpecLocalService extends BaseServiceImpl<ImageSpec,ImageSpecLocalQo,ImageSpecDao> {
	@Autowired
	private ImageSpecDao imageSpecDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ImageUseTypeDao imageUseTypeDao;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Override
	protected ImageSpecDao getDao() {
		return imageSpecDao;
	}
	
	/**
	 * @方法功能说明：创建图片规格
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午10:14:59
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public void createImageSpec(CreateImageSpecCommand command) throws ImageException,IOException {
		Assert.notCommand(command,ImageException.NEED_IMAGESPEC_WITHOUTPARAM,"创建图片规格指令");
		Assert.notPorjectandEnv(command.getFromProjectId(),command.getFromProjectEnvName());
		Assert.notEmpty(command.getImageId(),ImageException.NEED_IMAGE_WITHOUTPARAM,"图片id");
		Assert.notEmpty(command.getKey(),ImageException.NEED_IMAGESPEC_WITHOUTPARAM,"图片规格Key");
		
		
		Image image = imageDao.load(command.getImageId());
		List<ImageSpecKey> imageSpecKeys = image.getUseType().getImageSpecKeys();
		
		for (ImageSpecKey imageSpecKey : imageSpecKeys) {
			if (StringUtils.equals(imageSpecKey.getKey(), command.getKey())) {
				// 规格图片在产生真实文件前的占位预处理
				ImageSpec imageSpec = new ImageSpec(command,
						imageSpecKey);
				
				// 获取原始图的fdfs信息
				FdfsFileInfo imageSpecFileInfo = JSON.parseObject(
						command.getFileInfo(), FdfsFileInfo.class);
				
				imageSpec.addFileInfo(image, imageSpecFileInfo);
				
				imageSpecDao.save(imageSpec);
			}
		}
		
	}
	
	/**
	 * @方法功能说明：修改图片下的规格图片，一般是重新剪裁了新的规格图
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午10:14:59
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 * @throws MalformedURLException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public void modifyImageSpec(ModifyImageSpecCommand command) throws ImageException, IOException {
		Assert.notCommand(command,ImageException.NEED_IMAGESPEC_WITHOUTPARAM,"创建图片规格指令");
		Assert.notEmpty(command.getImageSpecId(),ImageException.NEED_IMAGE_WITHOUTPARAM,"图片id");
		
		ImageSpec imageSpec = imageSpecDao.get(command.getImageSpecId());
		
		imageSpec.modifyImageSpec(command);
		
		imageSpecDao.update(imageSpec);
		
		//领域日志
		DomainEvent event = new DomainEvent("hg.service.image.domain.model.ImageSpec","createImageSpec",JSON.toJSONString(command));
		domainEvent.save(event);
	}
	
	/**
	 * @方法功能说明：删除图片规格
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午10:15:03
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 */
	public void deleteImageSpec(DeleteImageSpecCommand command) throws ImageException {
		Assert.notCommand(command,ImageException.NEED_IMAGESPEC_WITHOUTPARAM,"删除图片规格指令");
		
		//如果图片规格id不为空，根据id删除图片规格
		if(StringUtils.isNotBlank(command.getImageSpecId())){
			deleteById(command.getImageSpecId());
			return;
		}
		
		//领域日志
		DomainEvent event = new DomainEvent("hg.service.image.domain.model.ImageSpec","deleteImageSpec",JSON.toJSONString(command));
		domainEvent.save(event);
	}
	
}