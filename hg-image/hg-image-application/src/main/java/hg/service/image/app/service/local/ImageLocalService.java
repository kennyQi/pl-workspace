package hg.service.image.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.file.FdfsFileInfo;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;
import hg.service.image.app.common.Assert;
import hg.service.image.app.common.ImageFileUtil;
import hg.service.image.app.dao.AlbumDao;
import hg.service.image.app.dao.ImageDao;
import hg.service.image.app.dao.ImageSpecDao;
import hg.service.image.app.dao.ImageUseTypeDao;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.BatchCreateImageCommand;
import hg.service.image.command.image.CreateTempImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.command.image.ModifyImageCommand;
import hg.service.image.command.image.spec.CreateImageSpecCommand;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.domain.model.Album;
import hg.service.image.domain.model.Image;
import hg.service.image.domain.model.ImageSpec;
import hg.service.image.domain.model.ImageSpecKey;
import hg.service.image.domain.model.ImageUseType;
import hg.service.image.domain.qo.AlbumLocalQo;
import hg.service.image.domain.qo.ImageLocalQo;
import hg.service.image.domain.qo.ImageUseTypeLocalQo;
import hg.service.image.pojo.exception.ImageException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：图片本地Service类
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
public class ImageLocalService extends
		BaseServiceImpl<Image, ImageLocalQo, ImageDao> {
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private AlbumDao albumDao;
	@Autowired
	private ImageSpecDao imageSpecDao;
	@Autowired
	private ImageUseTypeDao imageUseTypeDao;
	@Autowired
	private DomainEventRepository domainEvent;

	@Autowired
	private ImageUseTypeLocalService imageUseTypeLocalService;
	@Autowired
	private AlbumLocalService albumLocalService;

	@Override
	protected ImageDao getDao() {
		return imageDao;
	}

	/**
	 * @throws ImageException
	 * 
	 * @方法功能说明：创建一张临时图片，不裁图，只暂存原图
	 * @修改者名字：yuxx
	 * @修改时间：2015年3月18日上午9:59:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Image createTempImage(CreateTempImageCommand command)
			throws ImageException {
		if (command != null) {
			// 判断有没有临时相册
			AlbumLocalQo albumQo = new AlbumLocalQo();
			albumQo.setProjectId(command.getFromProjectId());
			albumQo.setEnvName(command.getFromProjectEnvName());
			albumQo.setTitle("临时图片相册");
			Album album = albumDao.queryUnique(albumQo);

			// 没有临时相册新建一个
			if (album == null) {
				CreateAlbumCommand createAlbumCommand = new CreateAlbumCommand();
				createAlbumCommand.setFromProjectId(command.getFromProjectId());
				createAlbumCommand.setFromProjectEnvName(command
						.getFromProjectEnvName());
				createAlbumCommand.setTitle("临时图片相册");
				album = new Album(createAlbumCommand);
				albumDao.save(album);
			}

			// 获取临时图片这个用途
			ImageUseTypeLocalQo imageUseTypeLocalQo = new ImageUseTypeLocalQo();
			imageUseTypeLocalQo.setProjectId(command.getFromProjectId());
			imageUseTypeLocalQo.setEnvName(command.getFromProjectEnvName());
			imageUseTypeLocalQo.setId(command.getFromProjectId()
					+ "_TEMP_IMAGE");
			ImageUseType tempImageUseType = imageUseTypeDao
					.queryUnique(imageUseTypeLocalQo);

			// 没有临时图片用途，新建一个
			if (tempImageUseType == null) {
				CreateImageUseTypeCommand createImageUseTypeCommand = new CreateImageUseTypeCommand();
				createImageUseTypeCommand.setFromProjectId(command
						.getFromProjectId());
				createImageUseTypeCommand.setFromProjectEnvName(command
						.getFromProjectEnvName());
				createImageUseTypeCommand.setUseTypeId(command
						.getFromProjectId() + "_TEMP_IMAGE");
				createImageUseTypeCommand.setTitle("临时图片");

				imageUseTypeLocalService
						.createImageSpecKeyUseType(createImageUseTypeCommand);
			}

			// 给这张临时图片创建一个默认规格图
			CreateImageSpecCommand createImageSpecCommand = new CreateImageSpecCommand(
					command.getFromProjectId(), command.getFromProjectEnvName());
			createImageSpecCommand.setFileInfo(command.getFileInfo());
			createImageSpecCommand.setKey("default");

			// 获取原始图的fdfs信息
			FdfsFileInfo defaultImageSpecFileInfo = JSON.parseObject(
					command.getFileInfo(), FdfsFileInfo.class);

			// 创建默认的ImageSpecKey
			ImageSpecKey defaultImageSpecKey = new ImageSpecKey();
			defaultImageSpecKey.setWidth(new Integer(defaultImageSpecFileInfo
					.getMetaMap().get("width")));
			defaultImageSpecKey.setHeight(new Integer(defaultImageSpecFileInfo
					.getMetaMap().get("height")));
			defaultImageSpecKey.setKey("default");
			defaultImageSpecKey.setRemark("默认");

			ImageSpec defaultImageSpec = new ImageSpec(createImageSpecCommand,
					defaultImageSpecKey);

			Image image = new Image(command, album, tempImageUseType,
					defaultImageSpec);
			imageDao.save(image);

			return image;
		}
		return null;
	}

	/**
	 * @方法功能说明：创建图片
	 * 
	 *              1 获取用途 2 获取相册 3 按用途key的所有规格定义，创建全部规格图片实体 4 创建默认规格图片实体 5
	 *              创建图片实体 6 自动裁剪所有规格图片，并将文件信息补全到每个规格图片实体中 7
	 *              将已提前上传好的默认规格图片信息补全到默认规格图片实体中 8 保存全部
	 * 
	 * 
	 * 
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午6:22:47
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Image createImage(CreateImageCommand command) throws ImageException,
			IOException {
		Assert.notCommand(command, ImageException.NEED_IMAGE_WITHOUTPARAM,
				"创建图片指令");
		if (StringUtils.isBlank(command.getFileInfo()))
			throw new ImageException(ImageException.NEED_IMAGE_WITHOUTPARAM,
					"图片原始名称或FileJSON字符不能为空");
		// ===================== 1 =======================
		// 获取用途
		ImageUseType imageUseType = imageUseTypeDao
				.load(command.getUseTypeId());
		if (imageUseType == null) {
			throw new ImageException(ImageException.NEED_IMAGE_WITHOUTPARAM,
					"用途");
		}

		// ===================== 2 =======================
		// 获取相册
		Album album = null;
		if (command.getAlbumId() == null) {
			// 没有传相册，取用途默认相册
			AlbumLocalQo qo = new AlbumLocalQo();
			qo.setProjectId(command.getFromProjectId());
			qo.setEnvName(command.getFromProjectEnvName());
			qo.setId(command.getFromProjectId() + "_"
					+ command.getFromProjectEnvName() + "_"
					+ command.getUseTypeId());
			album = albumDao.queryUnique(qo);

			// 用途默认相册不存在，新建一个
			if (album == null) {
				CreateAlbumCommand createAlbumCommand = new CreateAlbumCommand();
				createAlbumCommand.setFromProjectId(command.getFromProjectId());
				createAlbumCommand.setFromProjectEnvName(command
						.getFromProjectEnvName());
				createAlbumCommand.setAlbumId(command.getFromProjectId() + "_"
						+ command.getFromProjectEnvName() + "_"
						+ command.getUseTypeId());
				createAlbumCommand.setTitle(imageUseType.getTitle() + "默认相册");
				albumLocalService.createAlbum(createAlbumCommand);
			}
		} else {
			// 按传的相册取
			album = albumDao.get(command.getAlbumId());
			if (album == null) {
				CreateAlbumCommand createAlbumCommand = new CreateAlbumCommand();
				createAlbumCommand.setFromProjectId(command.getFromProjectId());
				createAlbumCommand.setFromProjectEnvName(command
						.getFromProjectEnvName());
				createAlbumCommand.setAlbumId(command.getAlbumId());
				if (StringUtils.isNotBlank(command.getAlbumTitle())) {
					createAlbumCommand.setTitle(command.getAlbumTitle());
				} else {
					createAlbumCommand.setTitle(command.getAlbumId());
				}
				
				album = albumLocalService.createAlbum(createAlbumCommand);
			}
		}

		// ===================== 3 =======================
		// 获取用途的所有规格key
		List<ImageSpecKey> imageSpecKeys = imageUseType.getImageSpecKeys();
		List<ImageSpec> imageSpecs = new ArrayList<ImageSpec>();

		// 根据用途的所有规格key创建规格图片
		if (imageSpecKeys != null) {
			for (ImageSpecKey imageSpecKey : imageSpecKeys) {
				// 自动裁剪并创建ImageSpec

				CreateImageSpecCommand createImageSpecCommand = new CreateImageSpecCommand();
				createImageSpecCommand.setKey(imageSpecKey.getKey());
				createImageSpecCommand.setFromProjectId(command
						.getFromProjectId());
				createImageSpecCommand.setFromProjectEnvName(command
						.getFromProjectEnvName());

				// 规格图片在产生真实文件前的占位预处理
				ImageSpec imageSpec = new ImageSpec(createImageSpecCommand,
						imageSpecKey);
				imageSpecs.add(imageSpec);
			}
		}

		// ===================== 4 =======================
		// 创建默认的ImageSpec,删掉临时图片记录，临时图转为默认图，真实文件在调用本方法前已上传到fdfs
		CreateImageSpecCommand createImageSpecCommand = new CreateImageSpecCommand(
				command.getFromProjectId(), command.getFromProjectEnvName());
		createImageSpecCommand.setFileInfo(command.getFileInfo());
		createImageSpecCommand.setKey("default");

		// 获取原始图的fdfs信息
		FdfsFileInfo defaultImageSpecFileInfo = JSON.parseObject(
				command.getFileInfo(), FdfsFileInfo.class);

		// 删除临时图片记录，避免被清掉文件
		getDao().deleteById(defaultImageSpecFileInfo.getImageId());
		defaultImageSpecFileInfo.setImageId(null);

		// 创建默认的ImageSpecKey
		ImageSpecKey defaultImageSpecKey = new ImageSpecKey();

		String tempWidth = defaultImageSpecFileInfo.getMetaMap().get("width") == null ? "0"
				: defaultImageSpecFileInfo.getMetaMap().get("width");
		String tempHeight = defaultImageSpecFileInfo.getMetaMap().get("height") == null ? "0"
				: defaultImageSpecFileInfo.getMetaMap().get("height");

		defaultImageSpecKey.setWidth(new Integer(tempWidth));
		defaultImageSpecKey.setHeight(new Integer(tempHeight));
		defaultImageSpecKey.setKey("default");
		defaultImageSpecKey.setRemark("默认");

		ImageSpec defaultImageSpec = new ImageSpec(createImageSpecCommand,
				defaultImageSpecKey);
		defaultImageSpec.setFileMd5(String.valueOf(defaultImageSpecFileInfo.getCrc32()));
		
		// ===================== 5 =======================
		Image image = new Image(command, album, imageUseType, defaultImageSpec,
				imageSpecs);

		// ===================== 6 =======================
		// 自动裁剪出所有的规格图片真实文件并上传fdfs
		ImageFileUtil.autoHandleImageSpec(image);

		// ===================== 7 =======================
		defaultImageSpec.addFileInfo(image, defaultImageSpecFileInfo);

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Image", "create",
				JSON.toJSONString(command));
		domainEvent.save(event);

		imageDao.save(image);
		imageSpecDao.save(defaultImageSpec);

		for (ImageSpec imageSpec : imageSpecs) {
			imageSpecDao.save(imageSpec);
		}
		return image;
	}

	/**
	 * @方法功能说明：批量创建图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-20下午6:36:30
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public void batchCreateImage(BatchCreateImageCommand command)
			throws ImageException, IOException {
		Assert.notCommand(command, ImageException.NEED_IMAGE_WITHOUTPARAM,
				"创建图片指令");
		Assert.notList(command.getCommands(),
				ImageException.NEED_IMAGE_WITHOUTPARAM, "图片command");

		List<CreateImageCommand> commandList = command.getCommands();
		for (CreateImageCommand createCommand : commandList) {
			this.createImage(createCommand);
		}
	}

	public void deleteImage(DeleteImageCommand command) {
		for (String imageId : command.getImageIds()) {
			imageDao.deleteById(imageId);
		}

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Image", "delete",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：修改图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午1:30:50
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void modifyImage(ModifyImageCommand command) throws ImageException,
			IOException {
		Assert.notCommand(command, ImageException.NEED_IMAGE_WITHOUTPARAM,
				"修改图片指令");

		Album album = albumDao.get(command.getAlbumId());
		if (album == null) {
			throw new ImageException(ImageException.NEED_IMAGE_WITHOUTPARAM,
					"相册");
		}

		Image image = imageDao.get(command.getImageId());
		image.modify(command, album);

		imageDao.update(image);

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Image", "modifyImage",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	public Image queryUnique(ImageLocalQo qo) {
		HgLogger.getInstance().info("chenxy", "图片查询QO>>>"+JSON.toJSONString(qo));
		Image image = getDao().queryUnique(qo);
		HgLogger.getInstance().info("chenxy", "图片JSON"+JSON.toJSONString(image));
		Hibernate.initialize(image.getImageSpecMap());
		Hibernate.initialize(image.getDefaultImageSpec());
		return image;
	}
}