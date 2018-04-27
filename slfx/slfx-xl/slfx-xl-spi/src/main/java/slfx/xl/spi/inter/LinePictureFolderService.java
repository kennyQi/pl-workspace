package slfx.xl.spi.inter;

import hg.common.page.Pagination;
import hg.service.image.pojo.exception.ImageException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import slfx.xl.pojo.command.line.BatchCreateLineImageCommand;
import slfx.xl.pojo.command.line.BatchCreateLinePictureCommand;
import slfx.xl.pojo.command.line.BatchDeleteLinePictureCommand;
import slfx.xl.pojo.command.line.CreateLineImageCommand;
import slfx.xl.pojo.command.line.CreateLinePictureCommand;
import slfx.xl.pojo.command.line.CreateLinePictureFolderCommand;
import slfx.xl.pojo.dto.line.LinePictureDTO;
import slfx.xl.pojo.dto.line.LinePictureFolderDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.LinePictureFolderQO;
import slfx.xl.pojo.qo.LinePictureQO;

/**
 * @类功能说明：图片Service接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午2:42:58
 * @版本：V1.0
 */
public interface LinePictureFolderService {
	/**
	 * @方法功能说明：创建图片文件夹
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午2:58:02
	 * @param command
	 * @return
	 * @throws SlfxXlException
	 */
	public LinePictureFolderDTO createFolder(CreateLinePictureFolderCommand command) throws SlfxXlException;
	
	/**
	 * @方法功能说明：查询数量
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午3:00:46
	 * @param qo
	 * @return
	 */
	public Integer queryCountFolder(LinePictureFolderQO qo);
	
	/**
	 * 查询图片文件夹
	 * @param qo
	 * @return
	 */
	public LinePictureFolderDTO queryUniqueFolder(LinePictureFolderQO qo);

	/**
	 * 查询图片文件夹列表
	 * @param qo
	 * @return
	 */
	public List<LinePictureFolderDTO> queryListFolder(LinePictureFolderQO qo);

	/**
	 * 图片文件夹分页查询
	 * @param pagination
	 * @return
	 */
	public Pagination queryPaginationFolder(Pagination pagination);
	
	//------------------------- 线路图片  ----------------------------------
	/**
	 * @方法功能说明：创建线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午3:02:10
	 * @param command
	 * @return
	 * @throws SlfxXlException
	 */
	public String createPicture(CreateLinePictureCommand command)	throws SlfxXlException;
	/**
	 * @方法功能说明：创建线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午3:02:10
	 * @param command
	 * @return
	 * @throws SlfxXlException
	 */
	public List<String> createPicture(BatchCreateLinePictureCommand command) throws SlfxXlException;
	
	/**
	 * @方法功能说明：创建线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-23下午3:16:18
	 * @param command
	 * @throws SlfxXlException
	 * @throws ImageException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void createLineImage(CreateLineImageCommand command) throws SlfxXlException, ImageException, IllegalAccessException,
		InvocationTargetException, NoSuchMethodException, IOException, URISyntaxException;
	
	/**
	 * @方法功能说明：创建线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-23下午3:16:18
	 * @param command
	 * @throws SlfxXlException
	 * @throws ImageException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void createLineImage(BatchCreateLineImageCommand command) throws SlfxXlException, ImageException, IllegalAccessException,
		InvocationTargetException, NoSuchMethodException, IOException, URISyntaxException;
	
	/**
	 * @方法功能说明：批量删除线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午3:03:18
	 * @param command
	 * @throws SlfxXlException
	 */
	public void deletePicture(BatchDeleteLinePictureCommand command) throws SlfxXlException,
		ImageException, IOException, URISyntaxException;
	
	/**
	 * @方法功能说明：查询数量
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午3:00:46
	 * @param qo
	 * @return
	 */
	public Integer queryCountPicture(LinePictureQO qo);
	
	/**
	 * 查询线路图片
	 * @param qo
	 * @return
	 */
	public LinePictureDTO queryUniquePicture(LinePictureQO qo);

	/**
	 * 查询线路图片列表
	 * @param qo
	 * @return
	 */
	public List<LinePictureDTO> queryListPicture(LinePictureQO qo);

	/**
	 * 线路图片分页查询
	 * @param pagination
	 * @return
	 */
	public Pagination queryPaginationPicture(Pagination pagination);
}