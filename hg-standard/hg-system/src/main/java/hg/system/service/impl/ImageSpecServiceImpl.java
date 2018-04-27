package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.Md5FileUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.system.command.imageSpec.ImageSpecCreateCommand;
import hg.system.command.imageSpec.ImageSpecDeleteCommand;
import hg.system.command.imageSpec.ImageSpecModifyCommand;
import hg.system.command.imageSpec.ImageSpecPubCommand;
import hg.system.command.imageSpec.ImageSpecUploadCommand;
import hg.system.common.util.ImageCutCommand;
import hg.system.common.util.ImageCutUtil;
import hg.system.common.util.ImageSpecKeyUtil;
import hg.system.dao.ImageDao;
import hg.system.dao.ImageSpecDao;
import hg.system.exception.HGException;
import hg.system.model.image.Image;
import hg.system.model.image.ImageSpec;
import hg.system.qo.ImageQo;
import hg.system.qo.ImageSpecQo;
import hg.system.service.ImageSpecService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：图片附件_service实现
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午12:33:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午12:33:50
 *
 */
@Service
@Transactional
public class ImageSpecServiceImpl extends BaseServiceImpl<ImageSpec, ImageSpecQo, ImageSpecDao> implements ImageSpecService {
	
	/**
	 * 图片附件Dao
	 */
	@Autowired
	private ImageSpecDao imageSpecDao;
	
	/**
	 * 图片Dao
	 */
	@Autowired
	private ImageDao imageDao;
	
	@Override
	public void imageSpecDelete(ImageSpecDeleteCommand command) throws HGException {
		
		// 1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_IMAGESPEC_NOT_EXIST, "请选择一条记录进行删除！");

		// 3. 将单个数据放到list中
		if (command.getImageSpecIds() == null) {
			command.setImageSpecIds(new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(command.getImageSpecId())) {
			command.getImageSpecIds().add(command.getImageSpecId());
		}
		
		// 2. 删除的数量要小于剩余数量
		ImageQo qo = new ImageQo();
		qo.setId(command.getImageId());
		
		ImageSpecQo imageSpecQo = new ImageSpecQo();
		imageSpecQo.setImageQo(qo);
		List<ImageSpec> imageSpecList = this.queryList(imageSpecQo);
		if (imageSpecList != null && (imageSpecList.size() <= command.getImageSpecIds().size())) {
			throw new HGException(HGException.CODE_IMAGESPEC_DEL_ERR, "不能删除全部记录, 请留一条记录！");
		}

		// 4. 删除附件
		try {
			for (String imageSpecId : command.getImageSpecIds()) {
				
				// 4.1 检查是否是原图
				ImageSpecQo imageSpecDelQo = new ImageSpecQo();
				imageSpecDelQo.setId(imageSpecId);
				ImageSpec imageSpecDel = this.queryUnique(imageSpecDelQo);
				if (imageSpecDel.getKey().equals("default")) {
					throw new HGException(HGException.CODE_IMAGESPE_DEFAULT_ERROR, "原图不能删除！");
				}
				getDao().delete(imageSpecDel);
			}
		} catch (Exception e) {
			throw new HGException(HGException.CODE_IMAGESPEC_DEL_ERR, "删除异常");
		}

	}

	@Override
	public ImageSpec imageSpecModify(ImageSpecModifyCommand command) throws HGException {
		
		// 1. 获取编辑的附件对象
		ImageSpecQo imageSpecQo = new ImageSpecQo();
		imageSpecQo.setId(command.getImageSpecId());
		ImageSpec imageSpec = this.queryUnique(imageSpecQo);
		
		// 2. 检查
		try {
			imageSpecModifyCheck(command);
		} catch  (HGException e) {
			// 2.1. 捕捉别名重复异常, 删除重复记录
			ImageQo imageQo = new ImageQo();
			imageQo.setId(command.getImageId());
			
			ImageSpecQo imageSpecKeyQo = new ImageSpecQo();
			imageSpecKeyQo.setKey(command.getKey());
			imageSpecKeyQo.setImageQo(imageQo);
			ImageSpec imageSpecKey = this.queryUnique(imageSpecKeyQo);
			
			ImageSpecDeleteCommand imageSpecDeleteCommand = new ImageSpecDeleteCommand();
			imageSpecDeleteCommand.setImageSpecId(imageSpecKey.getId());
			imageSpecDelete(imageSpecDeleteCommand);
		}

		// 3. 判断对图片进行哪种操作
		String filePath = null;
		String imageType = command.getImgUrl().substring(command.getImgUrl().lastIndexOf(".") + 1);
		
		ImageCutCommand imageCutCommand = BeanMapperUtils.map(command, ImageCutCommand.class);
		if (imageCutCommand.needCut()) {
			// 3.1  裁剪图片&压缩图片 并 保存本地
			filePath = ImageCutUtil.abscut(imageCutCommand);
		} else if(imageCutCommand.needCompre()) {
			// 3.2  压缩图片 并 保存本地
			filePath = ImageCutUtil.scale(imageCutCommand);
		}

		FdfsFileInfo fileInfo = null;
		// 4. 判断下需不需要重新上传
		if (filePath != null) {
			
			// 4.1 上传
			ImageSpecUploadCommand imageSpecUploadCommand = new ImageSpecUploadCommand();
			imageSpecUploadCommand.setImageId(command.getImageId());
			imageSpecUploadCommand.setFilePath(filePath);
			imageSpecUploadCommand.setExtName(imageType);
			fileInfo = imageSpecUpload(imageSpecUploadCommand);
			
			// 4.2  删除本地temp图片
			File tempFile = new File(filePath);
			tempFile.delete();
		}

		// 5. 保存
		ImageSpecCreateCommand imageSpecCreateCommand = new ImageSpecCreateCommand();
		imageSpecCreateCommand.setKey(command.getKey());
		imageSpecCreateCommand.setImageId(command.getImageId());
		imageSpecCreateCommand.setFileInfo(fileInfo == null ? imageSpec.getFileInfo() 
				: JSON.toJSONString(fileInfo));
		imageSpecCreateCommand.setImageSpecKeys(command.getImageSpecKeys());
		
		ImageSpec newImageSpec = imageSpecCreate(imageSpecCreateCommand);

		return newImageSpec;
	}
	
	
	@Override
	public void imageSpecModifyCheck(ImageSpecModifyCommand command) throws HGException {
		
		// 1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_IMAGESPEC_NOT_EXIST, "请选择一条记录进行裁剪！");
		
		// 2. 检查别名是否为空
		if (!StringUtils.isNotEmpty(command.getKey()))
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_NOT_EXIST, "别名不能为空！");
		
		// 3. 检查路径是否存在
		if (!StringUtils.isNotEmpty(command.getImgUrl()))
			throw new HGException(HGException.CODE_IMAGESPEC_URI_NOT_EXIST, "图片不存在！");
		
		// 4. 检查别名是否符合规范
		if (!ImageSpecKeyUtil.vailKey(command.getKey(), command.getImageSpecKeys()))
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_ERR, "别名不正确！");
		
		// 5. 检查别名是否存在
		ImageQo imageQo = new ImageQo();
		imageQo.setId(command.getImageId());
		
		ImageSpecQo imageSpecQo = new ImageSpecQo();
		imageSpecQo.setImageQo(imageQo);
		imageSpecQo.setKey(command.getKey());
		List<ImageSpec> imageSpecList = imageSpecDao.queryList(imageSpecQo);
		if(imageSpecList != null && imageSpecList.size() > 0) {
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_HAS_EXIST, "别名已存在！");
		}
	}
	
	@Override
	public ImageSpec imageSpecCreate(ImageSpecCreateCommand command)
			throws HGException {
		
		// 1. 检查
		// 1.1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_IMAGESPEC_NOT_EXIST, "请选择一条记录进行裁剪！");
		
		// 1.2. 检查别名是否为空
		if (!StringUtils.isNotEmpty(command.getKey()))
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_NOT_EXIST, "别名不能为空！");
		
		// 1.3. 检查别名是否符合规范
		if (!ImageSpecKeyUtil.vailKey(command.getKey(), command.getImageSpecKeys()))
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_ERR, "别名不正确！");
		
		// 1.4. 检查别名是否存在
		ImageQo imageQo = new ImageQo();
		imageQo.setId(command.getImageId());
		
		ImageSpecQo imageSpecQo = new ImageSpecQo();
		imageSpecQo.setKey(command.getKey());
		imageSpecQo.setImageQo(imageQo);
		List<ImageSpec> imageSpecList = imageSpecDao.queryList(imageSpecQo);
		if (imageSpecList != null && imageSpecList.size() > 0) {
			throw new HGException(HGException.CODE_IMAGESPEC_KEY_HAS_EXIST, "别名已存在！");
		}
		
		// 2. 保存
		Image image = imageDao.queryUnique(imageQo);
		
		ImageSpec imageSpec = new ImageSpec();
		imageSpec.imageSpecCreate(command, image);
		imageSpecDao.save(imageSpec);

		return imageSpec;
	}
	
	
	@Override
	protected ImageSpecDao getDao() {
		return imageSpecDao;
	}

	@Override
	public FdfsFileInfo imageSpecUpload(ImageSpecUploadCommand command)
			throws HGException {
		
		// 1. md5检查文件是否重复(同一个image下附件不能重复)
		String md5 = null;
		try {
			md5 = Md5FileUtil.getMD5(new FileInputStream(command.getFilePath()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new HGException(HGException.CODE_IMAGESPE_MD5_ERROR, "图片MD5解析异常！");
		}

		if (StringUtils.isNotBlank(command.getImageId())) {
			
			ImageQo imageQo = new ImageQo();
			imageQo.setId(command.getImageId());
			
			ImageSpecQo imageSpecQo = new ImageSpecQo();
			imageSpecQo.setMd5(md5);
			imageSpecQo.setImageQo(imageQo);
			ImageSpec imageSpec = this.queryUnique(imageSpecQo);
			if(imageSpec != null) {
				return JSONObject.parseObject(imageSpec.getFileInfo(), FdfsFileInfo.class);
				// throw new HGException(HGException.CODE_IMAGESPE_HAS_EXIST, "图片已存在！");
			}
		}

		// 2. 插入md5数据
		Map<String, String> metaMap = new HashMap<String, String>();
		metaMap.put("md5", md5);

		// 3. 上传
		FdfsFileInfo fileInfo = null;
		FdfsFileUtil.init();
		try {
			fileInfo = FdfsFileUtil.upload(new FileInputStream(command.getFilePath()),
					command.getExtName(), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new HGException(HGException.CODE_IMAGESPE_UPLOAD_ERROR, "图片上传异常！");
		}
		fileInfo.setMetaMap(metaMap);

		return fileInfo;
	}

	@Override
	public String imageSpecQuePubUrl(ImageSpecPubCommand command) throws HGException {
		
		// 1. 检查
		if (command == null || !StringUtils.isNotBlank(command.getImageId()) 
				|| !StringUtils.isNotBlank(command.getKey()))
			throw new HGException(HGException.CODE_IMAGESPEC_NOT_EXIST,
					"查询信息不正确！请传入图片id和key!");
		
		// 2. 查询
		ImageSpecQo queryQo = new ImageSpecQo();
		ImageQo imageQo = new ImageQo();
		imageQo.setId(command.getImageId());
		queryQo.setImageQo(imageQo);
		queryQo.setKey(command.getKey());
		ImageSpec imageSpec = this.queryUnique(queryQo);
		
		if (imageSpec == null && !command.getKey().equals("default")) {
			queryQo.setKey("default");
			imageSpec = this.queryUnique(queryQo);
		}
		
		// 3. 设置外网url返回
		String pubUrl = null;
		if (imageSpec != null) {
			
			FdfsFileInfo fileInfo = JSONObject.parseObject(imageSpec.getFileInfo(),
						FdfsFileInfo.class);
			
			pubUrl = command.getPubUrl() + 
					File.separator + fileInfo.getGroupName() + 
					File.separator + fileInfo.getRemoteFileName();
		}
		return pubUrl;
	}

}
