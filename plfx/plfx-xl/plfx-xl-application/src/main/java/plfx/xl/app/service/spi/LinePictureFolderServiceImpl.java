package plfx.xl.app.service.spi;
//package slfx.xl.app.service.spi;
//
//import hg.common.page.Pagination;
//import hg.common.util.EntityConvertUtils;
//import hg.service.image.pojo.exception.ImageException;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.net.URISyntaxException;
//import java.util.List;
//
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import slfx.xl.app.service.local.LinePictureFolderLocalService;
//import slfx.xl.app.service.local.LinePictureLocalService;
//import slfx.xl.domain.model.line.LinePicture;
//import slfx.xl.domain.model.line.LinePictureFolder;
//import slfx.xl.pojo.command.line.BatchCreateLineImageCommand;
//import slfx.xl.pojo.command.line.BatchCreateLinePictureCommand;
//import slfx.xl.pojo.command.line.BatchDeleteLinePictureCommand;
//import slfx.xl.pojo.command.line.CreateLineImageCommand;
//import slfx.xl.pojo.command.line.CreateLinePictureCommand;
//import slfx.xl.pojo.command.line.CreateLinePictureFolderCommand;
//import slfx.xl.pojo.dto.line.LinePictureDTO;
//import slfx.xl.pojo.dto.line.LinePictureFolderDTO;
//import slfx.xl.pojo.exception.SlfxXlException;
//import slfx.xl.pojo.qo.LinePictureFolderQO;
//import slfx.xl.pojo.qo.LinePictureQO;
//import plfx.xl.spi.inter.LinePictureFolderService;
//
///**
// * @类功能说明：图片Service接口实现类
// * @公司名称：浙江汇购科技有限公司
// * @部门：技术部
// * @作者：chenyanshou
// * @创建时间：2014年12月18日下午2:42:58
// * @版本：V1.0
// */
//@Service("linePictureFolderService")
//public class LinePictureFolderServiceImpl implements LinePictureFolderService {
//	@Autowired
//	private LinePictureLocalService pictureSer;
//	@Autowired
//	private LinePictureFolderLocalService folderSer;
//	
//	/**
//	 * @方法功能说明：创建图片文件夹
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午2:58:02
//	 * @param command
//	 * @return
//	 * @throws SlfxXlException
//	 */
//	@Override
//	public LinePictureFolderDTO createFolder(CreateLinePictureFolderCommand command) throws SlfxXlException{
//		return folderSer.createFolder(command);
//	}
//	
//	/**
//	 * @方法功能说明：查询数量
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:00:46
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public Integer queryCountFolder(LinePictureFolderQO qo){
//		return folderSer.queryCount(qo);
//	}
//	
//	/**
//	 * 查询图片文件夹
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public LinePictureFolderDTO queryUniqueFolder(LinePictureFolderQO qo){
//		LinePictureFolder folder = folderSer.queryUnique(qo);
//		if(null != folder){
//			Hibernate.initialize(folder.getLine());
//			return EntityConvertUtils.convertEntityToDto(folder,LinePictureFolderDTO.class);
//		}
//		return null;
//	}
//
//	/**
//	 * 查询图片文件夹列表
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public List<LinePictureFolderDTO> queryListFolder(LinePictureFolderQO qo){
//		List<LinePictureFolder> folderList = folderSer.queryList(qo);
//		if(null != folderList && folderList.size() > 0)
//			return EntityConvertUtils.convertEntityToDtoList(folderList,LinePictureFolderDTO.class);
//		return null;
//	}
//
//	/**
//	 * 图片文件夹分页查询
//	 * @param pagination
//	 * @return
//	 */
//	@Override
//	public Pagination queryPaginationFolder(Pagination pagination){
//		// Model至DTO转换
//		List<?> list = pagination.getList();
//		if (null != list && list.size() > 0){
//			list = EntityConvertUtils.convertEntityToDtoList(list,LinePictureFolderDTO.class);
//			pagination.setList(list);
//		}
//		return pagination;
//	}
//	
//	//------------------------- 线路图片  ----------------------------------
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:02:10
//	 * @param command
//	 * @return
//	 * @throws SlfxXlException
//	 */
//	@Override
//	public String createPicture(CreateLinePictureCommand command) throws SlfxXlException{
//		return pictureSer.createPicture(command);
//	}
//	
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:02:10
//	 * @param command
//	 * @return
//	 * @throws SlfxXlException
//	 */
//	@Override
//	public List<String> createPicture(BatchCreateLinePictureCommand command) throws SlfxXlException{
//		return pictureSer.createPicture(command);
//	}
//	
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-23下午3:16:18
//	 * @param command
//	 * @throws SlfxXlException
//	 * @throws ImageException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 * @throws NoSuchMethodException
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 */
//	public void createLineImage(CreateLineImageCommand command) throws SlfxXlException, ImageException, IllegalAccessException,
//		InvocationTargetException, NoSuchMethodException, IOException, URISyntaxException{
//		pictureSer.createLineImage(command);
//	}
//	
//	/**
//	 * @方法功能说明：创建线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-23下午3:16:18
//	 * @param command
//	 * @throws SlfxXlException
//	 * @throws ImageException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 * @throws NoSuchMethodException
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 */
//	public void createLineImage(BatchCreateLineImageCommand command) throws SlfxXlException, ImageException, IllegalAccessException,
//		InvocationTargetException, NoSuchMethodException, IOException, URISyntaxException{
//		pictureSer.createLineImage(command);
//	}
//	
//	/**
//	 * @方法功能说明：批量删除线路图片
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:03:18
//	 * @param command
//	 * @throws SlfxXlException
//	 */
//	@Override
//	public void deletePicture(BatchDeleteLinePictureCommand command) throws SlfxXlException,
//		ImageException, IOException, URISyntaxException{
//		pictureSer.deletePicture(command);
//	}
//	
//	/**
//	 * @方法功能说明：查询数量
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:00:46
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public Integer queryCountPicture(LinePictureQO qo){
//		return pictureSer.queryCount(qo);
//	}
//	
//	/**
//	 * 查询线路图片
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public LinePictureDTO queryUniquePicture(LinePictureQO qo){
//		LinePicture picture = pictureSer.queryUnique(qo);
//		if(null != picture)
//			return EntityConvertUtils.convertEntityToDto(picture,LinePictureDTO.class);
//		return null;
//	}
//
//	/**
//	 * 查询线路图片列表
//	 * @param qo
//	 * @return
//	 */
//	@Override
//	public List<LinePictureDTO> queryListPicture(LinePictureQO qo){
//		List<LinePicture> pictureList = pictureSer.queryList(qo);
//		if(null != pictureList && pictureList.size() > 0)
//			return EntityConvertUtils.convertEntityToDtoList(pictureList,LinePictureDTO.class);
//		return null;
//	}
//
//	/**
//	 * 线路图片分页查询
//	 * @param pagination
//	 * @return
//	 */
//	@Override
//	public Pagination queryPaginationPicture(Pagination pagination){
//		// Model至DTO转换
//		List<?> list = pagination.getList();
//		if (null != list && list.size() > 0){
//			list = EntityConvertUtils.convertEntityToDtoList(list,LinePictureDTO.class);
//			pagination.setList(list);
//		}
//		return pagination;
//	}
//}