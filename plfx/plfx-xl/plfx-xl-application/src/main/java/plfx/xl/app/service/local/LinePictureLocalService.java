package plfx.xl.app.service.local;
//package slfx.xl.app.service.local;
//
//import hg.common.component.BaseServiceImpl;
//import hg.common.util.DateUtil;
//import hg.common.util.SysProperties;
//import hg.log.po.domainevent.DomainEvent;
//import hg.log.repository.DomainEventRepository;
//import hg.service.image.command.album.CreateAlbumCommand;
//import hg.service.image.command.album.DeleteAlbumCommand;
//import hg.service.image.command.image.CreateImageCommand;
//import hg.service.image.pojo.dto.AlbumDTO;
//import hg.service.image.pojo.dto.ImageUseTypeDTO;
//import hg.service.image.pojo.exception.ImageException;
//import hg.service.image.pojo.qo.AlbumQo;
//import hg.service.image.pojo.qo.ImageUseTypeQo;
//import hg.service.image.spi.inter.AlbumService_1;
//import hg.service.image.spi.inter.ImageService_1;
//import hg.service.image.spi.inter.ImageUseTypeService_1;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import slfx.xl.app.common.ApplicationConfig;
//import slfx.xl.app.component.Assert;
//import slfx.xl.app.dao.LinePictureDAO;
//import slfx.xl.domain.model.line.LinePicture;
//import slfx.xl.domain.model.line.LinePictureFolder;
//import slfx.xl.pojo.command.line.BatchCreateLineImageCommand;
//import slfx.xl.pojo.command.line.BatchCreateLinePictureCommand;
//import slfx.xl.pojo.command.line.BatchDeleteLinePictureCommand;
//import slfx.xl.pojo.command.line.CreateLineImageCommand;
//import slfx.xl.pojo.command.line.CreateLinePictureCommand;
//import slfx.xl.pojo.exception.SlfxXlException;
//import slfx.xl.pojo.qo.LinePictureFolderQO;
//import slfx.xl.pojo.qo.LinePictureQO;
//import com.alibaba.fastjson.JSON;
//
///**
// * @类功能说明：线路图片LocalService
// * @公司名称：浙江汇购科技有限公司
// * @部门：技术部
// * @作者：chenyanshuo
// * @创建时间：2014年12月18日上午9:40:29
// * @版本：V1.0
// */
//@Service
//@Transactional
//public class LinePictureLocalService extends BaseServiceImpl<LinePicture,LinePictureQO,LinePictureDAO> {
//	public final static String SLFX_LINE_USETYPE = "线路图片";//商旅分销的图片用途
//	
//	@Autowired
//	private LinePictureDAO dao;
//	@Autowired
//	private LinePictureFolderLocalService folderSer;
//	@Autowired
//	private AlbumService_1 albumSer;
//	@Autowired
//	private ImageService_1 imgSer;
//	@Autowired
//	private ImageUseTypeService_1 useSer;
//	@Autowired
//	private DomainEventRepository domainEvent;
//	
//	@Override
//	protected LinePictureDAO getDao() {
//		return dao;
//	}
//
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午2:38:07
//	 * @param command
//	 * @throws SlfxXlException 
//	 */
//	public String createPicture(CreateLinePictureCommand command) throws SlfxXlException {
//		Assert.notCommand(command,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"创建线路图片指令");
//		Assert.notEmpty(command.getFolderId(),SlfxXlException.FOLDER_ID_IS_NULL,"线路图片文件夹Id");
//		
//		//线路图片文件夹
//		LinePictureFolderQO folderQO = new LinePictureFolderQO(command.getFolderId());
//		LinePictureFolder folder = folderSer.queryUnique(folderQO);
//		Assert.notModel(folder,SlfxXlException.FOLDER_RESULT_NOT_FOUND,"线路图片文件夹");
//		
//		//创建线路图片
//		LinePicture picture = new LinePicture();
//		picture.create(command, folder);
//		dao.save(picture);
//		
//		//领域日志
//		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LinePicture","createPicture",JSON.toJSONString(command));
//		domainEvent.save(event);
//		
//		//返回数据id
//		return picture.getId();
//	}
//	
//	/**
//	 * @方法功能说明：批量创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午2:38:07
//	 * @param command
//	 * @throws SlfxXlException
//	 */
//	public List<String> createPicture(BatchCreateLinePictureCommand command) throws SlfxXlException{
//		Assert.notCommand(command,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"创建线路图片指令");
//		Assert.notList(command.getCommands(),SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"线路图片command");
//		
//		List<CreateLinePictureCommand> commandList = command.getCommands();
//		List<String> idList = new ArrayList<String>(0);
//		for (CreateLinePictureCommand createPicture : commandList) {
//			idList.add(this.createPicture(createPicture));
//		}
//		return idList;
//	}
//	
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-23下午3:16:18
//	 * @param command
//	 * @throws SlfxXlException
//	 * @throws ImageException
//	 * @throws IOException
//	 */
//	public void createLineImage(CreateLineImageCommand command) throws SlfxXlException, ImageException, IOException{
//		if(StringUtils.isBlank(command.getFromId()) || StringUtils.isBlank(command.getFromName()))
//			return;
//		
//		SysProperties sysp = SysProperties.getInstance();
//		//判断图片用途
//		ImageUseTypeQo useQo = new ImageUseTypeQo();
//		useQo.setProjectId(sysp.get("projectId_image"));
//		useQo.setEnvName(sysp.get("envId_image"));
//		useQo.setTitle(SLFX_LINE_USETYPE);
//		useQo.setTitleLike(false);
//		ImageUseTypeDTO useDto = useSer.queryUniqueImageSpecKeyUseType_1(useQo);
//		if(null == useDto)
//			throw new SlfxXlException(SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"线路图片用途不存在或已删除！");
//		
//		//判断相册，没有则创建
//		AlbumQo albumQo = this.factoryAlbumQo(null,null,command.getFromName(),"/"+ApplicationConfig.authName+"/",0);
//		if(albumSer.queryCount_1(albumQo) < 1){
//			CreateAlbumCommand createAlbum = this.factoryCreateAlbum(
//					ApplicationConfig.authId,command.getFromId(),command.getFromName(),"/"+ApplicationConfig.authName+"/",
//					DateUtil.formatDateTime(new Date())+"时创建",useDto.getId(),0
//				);
//			try{
//				albumSer.createAlbum_1(createAlbum);				
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//		//上传到图片服务(这里只是上传一张原图，没有剪裁没有压缩，所以没有图片规格，只是系统会默认的根据图片宽高比创建一个默认的图片规格)
//		CreateImageCommand createImage = this.factoryCreateImage(
//				ApplicationConfig.authId,command.getFromId(),command.getTitle(),"/"+ApplicationConfig.authName+"/"+command.getFromName()+"/",
//				DateUtil.formatDateTime(new Date())+"时创建",JSON.toJSONString(command.getFileInfo(),true),
//				command.getFileName(),useDto.getId(),0
//			);
//		
//		//领域日志
//		DomainEvent event = new DomainEvent("hg.service.image.domain.model.Image","createLineImage",JSON.toJSONString(command));
//		domainEvent.save(event);
//		try{
//			imgSer.createImage_1(createImage);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-23下午3:16:18
//	 * @param command
//	 * @throws SlfxXlException
//	 * @throws ImageException
//	 * @throws IOException
//	 */
//	public void createLineImage(BatchCreateLineImageCommand command) throws SlfxXlException, ImageException, IOException{
//		Assert.notCommand(command,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"创建图片指令");
//		Assert.notList(command.getCommands(),SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"图片command");
//		
//		List<CreateLineImageCommand> commandList = command.getCommands();
//		for (CreateLineImageCommand createImg : commandList) {
//			this.createLineImage(createImg);
//		}
//	}
//	
//	/**
//	 * @方法功能说明：删除线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午2:46:57
//	 * @param command
//	 * @throws SlfxXlException
//	 * @throws IOException 
//	 * @throws ImageException 
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void deletePicture(BatchDeleteLinePictureCommand command) throws SlfxXlException,ImageException,IOException{
//		Assert.notCommand(command,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"删除线路图片指令");
//		Assert.notList(command.getPictureIds(),SlfxXlException.PICTURE_ID_IS_NULL,"线路图片ID");
//		
//		List<String> idList = command.getPictureIds();
//		//删除图片服务上的图片
//		AlbumQo albumQo = null;
//		List<String> albumIds = new ArrayList<String>(1);
//		for (String id : idList) {
//			/**
//			 * 获取线路图片相册id
//			 * 一个是图片服务的数据库，一个是商旅分销的数据库，如果两边的数据因为程序之外的原因造成数据不对应、相册对不上id，导致一些莫名其妙的错误
//			 * 所以这里做个判断，允许删除景区的时候，线路图片相册不存在，以增加程序的容错能力！！
//			 */
//			albumQo = this.factoryAlbumQo(ApplicationConfig.authId,id,null,null,0);
//			try{
//				AlbumDTO albumDto = albumSer.queryUniqueAlbum_1(albumQo);
//				if(null != albumDto){
//					albumIds.add(albumDto.getId());
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		if(albumIds.size() > 0){
//			DeleteAlbumCommand deleteCommand = new DeleteAlbumCommand();
//			deleteCommand.setAlbumIds(albumIds);
//			try{
//				albumSer.deleteAlbum_1(deleteCommand);				
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//		//领域日志
//		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LinePicture","deletePicture",JSON.toJSONString(command));
//		domainEvent.save(event);
//		
//		//删除线路图片
//		dao.deleteByIds((List)idList);
//	}
//	
//	/**
//	 * @方法功能说明: 图片服务-相册Qo的简单工厂方法 
//	 * @param ownerId
//	 * @param fromId
//	 * @param isRefuse
//	 * @return
//	 */
//	public AlbumQo factoryAlbumQo(String ownerId,String fromId,String title,String waypath,int isRefuse){
//		SysProperties sysp = SysProperties.getInstance();
//		
//		AlbumQo albumQo = new AlbumQo();
//		albumQo.setProjectId(sysp.get("projectId_image"));
//		albumQo.setEnvName(sysp.get("envId_image"));
//		albumQo.setOwnerId(ownerId);
//		albumQo.setFromId(fromId);
//		albumQo.setTitle(title);
//		albumQo.setWaypath(waypath);
//		albumQo.setIsRefuse(isRefuse);
//		return albumQo;
//	}
//	/**
//	 * @方法功能说明: 图片服务-图片创建Command的简单工厂方法  
//	 * @param albumId
//	 * @param fromId
//	 * @param title
//	 * @param remark
//	 * @param fileInfo
//	 * @param fileName
//	 * @return
//	 */
//	public CreateImageCommand factoryCreateImage(String ownerId,String fromId,String title,
//			String waypath,String remark,String fileInfo,String fileName,String useType,int isRefuse){
//		SysProperties sysp = SysProperties.getInstance();
//		
//		CreateImageCommand createImage = new CreateImageCommand();
//		createImage.setFromProjectId(sysp.get("projectId_image"));
//		createImage.setFromProjectEnvName(sysp.get("envId_image"));
////		createImage.setOwnerId(ownerId);
////		createImage.setFromId(fromId);
//		createImage.setTitle(title);
////		createImage.setWaypath(waypath);
//		createImage.setRemark(remark);
//		createImage.setFileInfo(fileInfo);
//		createImage.setFileName(fileName);
//		createImage.setUseTypeId(useType);
////		createImage.setIsRefuse(isRefuse);
//		return createImage;
//	}
//	/**
//	 * @方法功能说明: 图片服务-相册创建Command的简单工厂方法  
//	 * @param ownerId
//	 * @param fromId
//	 * @param title
//	 * @param remark
//	 * @param isRefuse
//	 * @return
//	 */
//	public CreateAlbumCommand factoryCreateAlbum(String ownerId,String fromId,String title,
//			String waypath,String remark,String useType,int isRefuse){
//		SysProperties sysp = SysProperties.getInstance();
//		
//		CreateAlbumCommand createAlbum = new CreateAlbumCommand();
//		createAlbum.setFromProjectId(sysp.get("projectId_image"));
//		createAlbum.setFromProjectEnvName(sysp.get("envId_image"));
////		createAlbum.setOwnerId(ownerId);
////		createAlbum.setFromId(fromId);
//		createAlbum.setTitle(title);
////		createAlbum.setWaypath(waypath);
//		createAlbum.setRemark(remark);
////		createAlbum.setIsRefuse(isRefuse);
////		createAlbum.setUseTypeId(useType);
//		return createAlbum;
//	}
//}