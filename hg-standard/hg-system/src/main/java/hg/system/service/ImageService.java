package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.image.ImageCreateByUseTypeCommand;
import hg.system.command.image.ImageCreateCommand;
import hg.system.command.image.ImageDeleteCommand;
import hg.system.command.image.ImageModifyCommand;
import hg.system.exception.HGException;
import hg.system.model.image.Image;
import hg.system.qo.ImageQo;

/**
 * 
 * @类功能说明：图片_service层接口
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午9:05:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午9:05:02
 *
 */
public interface ImageService extends BaseService<Image, ImageQo> {

	/**
	 * @throws HGException 
	 * 
	 * @方法功能说明：图片添加_接口
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日上午10:37:06
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	public Image imageCreate(ImageCreateCommand command) throws HGException;

	
	/**
	 * 
	 * @方法功能说明：图片编辑_接口
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日上午11:02:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	public Image imageModify(ImageModifyCommand command);

	/**
	 * 
	 * @方法功能说明：图片删除检查_接口
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日下午1:40:14
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws UCException
	 * @return:void
	 * @throws
	 */
	public void imageDeleteCheck(ImageDeleteCommand command) throws HGException;
	
	/**
	 * 
	 * @方法功能说明：图片删除_接口
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日上午11:11:19
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void imageDelete(ImageDeleteCommand command) throws HGException;
	
	/**
	 * 
	 * @方法功能说明：图片添加到临时相册
	 * @修改者名字：zzb
	 * @修改时间：2014年9月25日下午5:18:16
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws HGException
	 * @return:Image
	 * @throws
	 */
	//public Image imageCreateInTemp(ImageCreateTempCommand command);
	
	
	/**
	 * 
	 * @方法功能说明：根据useType创建图片附件
	 * @修改者名字：zzb
	 * @修改时间：2014年9月29日上午8:59:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	public Image imageCreateByUseType(ImageCreateByUseTypeCommand command) throws HGException;

}
