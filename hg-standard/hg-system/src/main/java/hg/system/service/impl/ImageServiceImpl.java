package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.common.util.file.FdfsFileInfo;
import hg.system.command.image.ImageCreateByUseTypeCommand;
import hg.system.command.image.ImageCreateCommand;
import hg.system.command.image.ImageDeleteCommand;
import hg.system.command.image.ImageModifyCommand;
import hg.system.command.imageSpec.ImageSpecCreateCommand;
import hg.system.command.imageSpec.ImageSpecModifyCommand;
import hg.system.common.util.ImageSpecKeyUtil;
import hg.system.dao.AlbumDao;
import hg.system.dao.ImageDao;
import hg.system.dao.ImageSpecDao;
import hg.system.exception.HGException;
import hg.system.model.image.Album;
import hg.system.model.image.Image;
import hg.system.model.image.ImageSpec;
import hg.system.qo.ImageQo;
import hg.system.qo.ImageSpecQo;
import hg.system.service.AlbumService;
import hg.system.service.ImageService;
import hg.system.service.ImageSpecService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：图片_service实现
 * @类修改者：zzb
 * @修改日期：2014年9月3日下午3:24:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日下午3:24:56
 */
@Service
@Transactional
public class ImageServiceImpl extends BaseServiceImpl<Image, ImageQo, ImageDao>
		implements ImageService {

	/**
	 * 图片Dao
	 */
	@Autowired
	private ImageDao imageDao;

	/**
	 * 相册Dao
	 */
	@Autowired
	private AlbumDao albumDao;

	/**
	 * 图片附件Dao
	 */
	@Autowired
	private ImageSpecDao imageSpecDao;
	
	/**
	 * 图片附件Service
	 */
	@Autowired
	private ImageSpecService imageSpecService;
	
	/**
	 * 相册Service
	 */
	@Autowired
	private AlbumService albumService;
	

	@Override
	public Image imageCreate(ImageCreateCommand command) throws HGException {
		
		// 1. 检查
		// 1.1. 非空
		if (command == null
				|| StringUtils.isBlank(command.getDefaultImageSpec().getKey()))
			throw new HGException(HGException.CODE_IMAGE_NOT_EXIST, "新增异常！");

		// 1.2. 检查别名是否符合规范
		if (!ImageSpecKeyUtil.vailKey(command.getDefaultImageSpec().getKey(),
				command.getImageSpecKeys()))
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_ERR, "别名不正确！");

		// 2. 保存image
		Album album = null;
		if (StringUtils.isNotEmpty(command.getAlbumId()))
			album = albumDao.get(command.getAlbumId());

		Image image = new Image();
		image.imageCreate(command, album);
		getDao().save(image);

		// 3. 保存默认原图imageSpec
		ImageSpec imageSpec = new ImageSpec();

		imageSpec.imageSpecCreate(command.getDefaultImageSpec(), image);
		imageSpecDao.save(imageSpec);

		// 4. 保存更多规格图片
		if (command.getImageSpecList() != null && command.getImageSpecList().size() > 0) {
			// 4.1. 获取原图url;
			FdfsFileInfo defautFileInfo = JSONObject.parseObject(imageSpec.getFileInfo(), FdfsFileInfo.class);
			String url = command.getImageStaticUrl() + defautFileInfo.getUri();

			for (ImageSpecCreateCommand imageSpecCommandMore : command.getImageSpecList()) {
				
				// 4.2 填充编辑command, 保存
				ImageSpecModifyCommand imageSpecModifyCommand = new ImageSpecModifyCommand();
				imageSpecModifyCommand.setImageId(image.getId());
				imageSpecModifyCommand.setImageSpecId(imageSpec.getId());
				imageSpecModifyCommand.setImgUrl(url);
				imageSpecModifyCommand.setKey(imageSpecCommandMore.getKey());
				imageSpecModifyCommand.setNewHeight(String.valueOf(imageSpecCommandMore.getHeight()));
				imageSpecModifyCommand.setNewWidth(String.valueOf(imageSpecCommandMore.getWidth()));
				imageSpecModifyCommand.setImageSpecKeys(command.getImageSpecKeys());
				
				imageSpecService.imageSpecModify(imageSpecModifyCommand);
			}
		}
		return image;

	}

	@Override
	public Image imageModify(ImageModifyCommand command) {

		Image image = getDao().get(command.getImageId());

		Album album = null;
		if (StringUtils.isNotEmpty(command.getAlbumId())) {
			album = albumDao.get(command.getAlbumId());
		}
		image.imageModify(command, album);
		getDao().update(image);
		return image;

	}

	@Override
	public void imageDeleteCheck(ImageDeleteCommand command) throws HGException {

		// 1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_IMAGE_NOT_EXIST,
					"请选择一条记录进行删除！");

		// 2. 将单个数据放到list中
		if (command.getImageIds() == null) {
			command.setImageIds(new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(command.getImageId())) {
			command.getImageIds().add(command.getImageId());
		}
		if (StringUtils.isNotEmpty(command.getImageIdsStr())) {
			String[] ids = command.getImageIdsStr().split(",");
			for (String id : ids) {
				command.getImageIds().add(id);
			}
		}

		// 3. 检查是否有子节点
		for (String imageId : command.getImageIds()) {
			ImageQo imageQo = new ImageQo();
			imageQo.setId(imageId);

			ImageSpecQo imageSpecQo = new ImageSpecQo();
			imageSpecQo.setImageQo(imageQo);
			List<ImageSpec> imageSpecList = imageSpecDao.queryList(imageSpecQo);
			if (imageSpecList != null && imageSpecList.size() > 0)
				throw new HGException(HGException.CODE_IMAGE_HAS_SPEC,
						"包含<span style='color:red;'>【附件】</span>, 是否确定删除？");
		}

	}

	@Override
	public void imageDelete(ImageDeleteCommand command) throws HGException {

		// 1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_IMAGE_NOT_EXIST,
					"请选择一条记录进行删除！");

		// 2. 将单个数据放到list中
		if (command.getImageIds() == null) {
			command.setImageIds(new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(command.getImageId())) {
			command.getImageIds().add(command.getImageId());
		}
		if (StringUtils.isNotEmpty(command.getImageIdsStr())) {
			String[] ids = command.getImageIdsStr().split(",");
			for (String id : ids) {
				command.getImageIds().add(id);
			}
		}

		// 3. 删除所有附件 及 图片
		try {
			for (String imageId : command.getImageIds()) {
				ImageQo imageQo = new ImageQo();
				imageQo.setId(imageId);

				ImageSpecQo imageSpecQo = new ImageSpecQo();
				imageSpecQo.setImageQo(imageQo);
				List<ImageSpec> imageSpecList = imageSpecDao
						.queryList(imageSpecQo);
				for (ImageSpec imageSpec : imageSpecList) {
					imageSpecDao.deleteById(imageSpec.getId());
				}

				getDao().deleteById(imageId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HGException(HGException.CODE_ALBUM_DEL_ERR, "删除异常");
		}

	}
	
	/*@Override
	public Image imageCreateInTemp(ImageCreateTempCommand command) {

		// 1. 检查临时相册是否存在, 不存在添加
		AlbumQo albumQo = new AlbumQo();
		albumQo.setOwnerId(command.getOwnerId());
		albumQo.setUseType(command.getUseType());
		
		Album album = albumDao.queryUnique(albumQo);
		if (album == null) {
			
			AlbumCreateCommand albumCreateCommand = new AlbumCreateCommand();
			albumCreateCommand.setTitle("临时相册");
			albumCreateCommand.setOwnerId(command.getOwnerId());
			albumCreateCommand.setUseType(command.getUseType());

			album = albumService.albumCreate(albumCreateCommand);
		}

		// 2. 插入图片
		Image image = new Image();
		image.imageCreateTemp(command, album);
		getDao().save(image);
		
		// 3. 插入附件
		ImageSpec imageSpec = new ImageSpec();
		imageSpec.imageSpecCreate(command.getDefaultImageSpec(), image);
		imageSpecDao.save(imageSpec);
		
		return image;
	}*/

	@Override
	public Image imageCreateByUseType(ImageCreateByUseTypeCommand command) throws HGException {
		
		// 1. 检查数据
		if(command == null || command.getImage() == null 
				|| command.getImageSpecKey() == null 
				|| command.getUseTypeImageKeys() == null)
			throw new HGException(HGException.CODE_IMAGE_DATA_NOT_ENOUGH, "数据不完整！");
		
		// 2. 查出default附件, url
		Image imageDefault = command.getImage();
		ImageQo imageQo = new ImageQo();
		imageQo.setId(imageDefault.getId());
		
		ImageSpecQo imageSpecQo = new ImageSpecQo();
		imageSpecQo.setImageQo(imageQo);
		imageSpecQo.setKey(ImageSpec.DEFAULT_KEY);
		
		ImageSpec imageSpecDefault = imageSpecService.queryUnique(imageSpecQo);
		FdfsFileInfo defautFileInfo = 
				JSONObject.parseObject(imageSpecDefault.getFileInfo(), FdfsFileInfo.class);
		String url = command.getImageStaticUrl() + defautFileInfo.getUri();
		
		// 2. 遍历可能的key, 裁减保存
		Map<String, JSONObject> useTypeImageKeys = command.getUseTypeImageKeys();
		Map<String, JSONObject> imageSpecKey = command.getImageSpecKey();
		
		JSONObject useTypeKeyObj = useTypeImageKeys.get(String.valueOf(command.getUseType()));
		if (useTypeKeyObj == null)
			throw new HGException(HGException.CODE_ALBUM_USETYPE_ERROR, "没有对应的useType类型！");
		
		String[] images = useTypeKeyObj.getString("images").split(",");
		for (int i = 0; i < images.length; i++) {
			String imageKeyEn = images[i];
			JSONObject imageSpecKeyObj = imageSpecKey.get(imageKeyEn);
			if (imageSpecKeyObj != null) {
				
				ImageSpecModifyCommand imageSpecModifyCommand = new ImageSpecModifyCommand();
				imageSpecModifyCommand.setImageId(imageDefault.getId());
				imageSpecModifyCommand.setImageSpecId(imageSpecDefault.getId());
				imageSpecModifyCommand.setImgUrl(url);
				imageSpecModifyCommand.setKey(imageSpecKeyObj.getString("en"));
				imageSpecModifyCommand.setNewHeight(imageSpecKeyObj.getString("height"));
				imageSpecModifyCommand.setNewWidth(imageSpecKeyObj.getString("width"));
				imageSpecModifyCommand.setImageSpecKeys(command.getImageSpecKeyJson());
				
				imageSpecService.imageSpecModify(imageSpecModifyCommand);
			}
		}
		
		return imageDefault;
	}
	
	@Override
	protected ImageDao getDao() {
		return imageDao;
	}

	

}
